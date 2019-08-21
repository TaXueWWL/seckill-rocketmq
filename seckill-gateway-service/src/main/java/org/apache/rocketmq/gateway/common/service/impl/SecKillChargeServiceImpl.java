package org.apache.rocketmq.gateway.common.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.request.QueryOrderRequest;
import org.apache.rocketmq.common.response.QueryOrderResponse;
import org.apache.rocketmq.gateway.common.dao.dataobject.SecKillProductDobj;
import org.apache.rocketmq.gateway.common.dto.CodeMsg;
import org.apache.rocketmq.gateway.common.dto.Result;
import org.apache.rocketmq.gateway.common.dto.request.ChargeOrderRequest;
import org.apache.rocketmq.gateway.common.dto.response.ChargeOrderResponse;
import org.apache.rocketmq.gateway.common.init.SecKillProductConfig;
import org.apache.rocketmq.gateway.common.manager.OrderQueryManager;
import org.apache.rocketmq.gateway.common.service.SecKillChargeService;
import org.apache.rocketmq.gateway.common.util.LogExceptionWapper;
import org.apache.rocketmq.gateway.mq.SecKillChargeOrderProducer;
import org.apache.rocketmq.message.constant.MessageProtocolConst;
import org.apache.rocketmq.message.protocol.ChargeOrderMsgProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 23:04
 * @className SecKillChargeServiceImpl
 * @desc 秒杀下单service实现
 */
@Service(value = "secKillChargeService")
public class SecKillChargeServiceImpl implements SecKillChargeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillChargeServiceImpl.class);

    @Autowired
    SecKillProductConfig productConfig;

    @Autowired
    SecKillChargeOrderProducer secKillChargeOrderProducer;

    @Autowired
    OrderQueryManager orderQueryManager;

    /**
     * 秒杀下单前置参数校验
     * @param chargeOrderRequest
     * @param sessionId
     * @return
     */
    @Override
    public boolean checkParamsBeforeSecKillCharge(ChargeOrderRequest chargeOrderRequest, String sessionId) {
        // 入参校验
        if (chargeOrderRequest == null) {
            LOGGER.info("sessionId={},下单请求参数chargeOrderRequest为空,返回下单失败", sessionId);
            return false;
        }
        LOGGER.info("sessionId={},下单开始,下单请求参数chargeOrderRequest=[{}].", sessionId, JSON.toJSONString(chargeOrderRequest));
        String userPhoneNum = chargeOrderRequest.getUserPhoneNum();
        String chargePrice = chargeOrderRequest.getChargePrice();
        String prodId = chargeOrderRequest.getProdId();

        if (StringUtils.isBlank(prodId) || StringUtils.isBlank(chargePrice) || StringUtils.isBlank(userPhoneNum)) {
            LOGGER.info("sessionId={},下单必要参数为空,返回下单失败", sessionId);
            return false;
        }
        // 价格合法性校验 是否>0?
        BigDecimal chargePriceDecimal = new BigDecimal(chargePrice);
        if (chargePriceDecimal.longValue() < 0) {
            LOGGER.info("sessionId={},商品交易金额小于0,价格非法,返回下单失败", sessionId);
            return false;
        }
        return true;
    }

    /**
     * 秒杀下单前置商品校验
     * @param prodId
     * @param sessionId
     * @return
     */
    @Override
    public boolean checkProdConfigBeforeKillCharge(String prodId, String sessionId) {
        // 商品校验
        SecKillProductDobj productDobj = (SecKillProductDobj) productConfig.getRedisTemplate().opsForValue().get(prodId);
        if (productDobj == null) {
            LOGGER.info("sessionId={},prodId={},对应的商品信息不存在,返回下单失败", sessionId, prodId);
            return false;
        }
        return true;
    }

    /**
     * 秒杀订单入队
     * @param chargeOrderRequest
     * @param sessionId
     * @return
     */
    @Override
    public Result secKillOrderEnqueue(ChargeOrderRequest chargeOrderRequest, String sessionId) {

        // 订单号生成,组装秒杀订单消息协议
        String orderId = UUID.randomUUID().toString();
        String phoneNo = chargeOrderRequest.getUserPhoneNum();

        ChargeOrderMsgProtocol msgProtocol = new ChargeOrderMsgProtocol();
        msgProtocol.setUserPhoneNo(phoneNo)
                .setProdId(chargeOrderRequest.getProdId())
                .setChargeMoney(chargeOrderRequest.getChargePrice())
                .setOrderId(orderId);
        String msgBody = msgProtocol.encode();
        LOGGER.info("秒杀订单入队,消息协议={}", msgBody);

        DefaultMQProducer mqProducer = secKillChargeOrderProducer.getProducer();
        // 组装RocketMQ消息体
        Message message = new Message(MessageProtocolConst.SECKILL_CHARGE_ORDER_TOPIC.getTopic(), msgBody.getBytes());
        try {
            // 消息发送
            SendResult sendResult = mqProducer.send(message);
            // TODO 判断SendStatus
            if (sendResult == null) {
                LOGGER.error("sessionId={},秒杀订单消息投递失败,下单失败.msgBody={},sendResult=null", sessionId, msgBody);
                return Result.error(CodeMsg.BIZ_ERROR);
            }
            if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
                LOGGER.error("sessionId={},秒杀订单消息投递失败,下单失败.msgBody={},sendResult=null", sessionId, msgBody);
                return Result.error(CodeMsg.BIZ_ERROR);
            }
            ChargeOrderResponse chargeOrderResponse = new ChargeOrderResponse();
            BeanUtils.copyProperties(msgProtocol, chargeOrderResponse);
            LOGGER.info("sessionId={},秒杀订单消息投递成功,订单入队.出参chargeOrderResponse={},sendResult={}", sessionId, chargeOrderResponse.toString(), JSON.toJSONString(sendResult));
            return Result.success(CodeMsg.ORDER_INLINE, chargeOrderResponse);
        } catch (Exception e) {
            int sendRetryTimes = mqProducer.getRetryTimesWhenSendFailed();
            LOGGER.error("sessionId={},sendRetryTimes={},秒杀订单消息投递异常,下单失败.msgBody={},e={}", sessionId, sendRetryTimes, msgBody, LogExceptionWapper.getStackTrace(e));
        }
        return Result.error(CodeMsg.BIZ_ERROR);
    }

    /**
     * 查单前参数校验
     * @param queryOrderRequest
     * @param sessionId
     * @return
     */
    @Override
    public boolean checkParamsBeforeSecKillQuery(QueryOrderRequest queryOrderRequest, String sessionId) {
        // 入参校验
        if (queryOrderRequest == null) {
            LOGGER.info("sessionId={},查询请求参数queryOrderRequest为空,返回查询失败", sessionId);
            return false;
        }
        LOGGER.info("sessionId={},查询开始,查询请求参数queryOrderRequest=[{}].", sessionId, JSON.toJSONString(queryOrderRequest));

        String userPhoneNum = queryOrderRequest.getUserPhoneNum();
        String prodId = queryOrderRequest.getProdId();

        if (StringUtils.isBlank(prodId) || StringUtils.isBlank(userPhoneNum)) {
            LOGGER.info("sessionId={},查询必要参数为空,返回查询失败", sessionId);
            return false;
        }
        return true;
    }

    /**
     * 执行订单查询业务
     * @param queryOrderRequest
     * @param sessionId
     * @return
     */
    @Override
    public Result<QueryOrderResponse> queryOrder(QueryOrderRequest queryOrderRequest, String sessionId) {
        return orderQueryManager.queryOrder(queryOrderRequest, sessionId);
    }

}
