package org.apache.rocketmq.order.mq.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.message.protocol.ChargeOrderMsgProtocol;
import org.apache.rocketmq.order.common.dao.dataobject.OrderInfoDO;
import org.apache.rocketmq.order.common.dao.dataobject.OrderInfoDobj;
import org.apache.rocketmq.order.common.dao.dataobject.SecKillProductDobj;
import org.apache.rocketmq.order.common.dto.CodeMsg;
import org.apache.rocketmq.order.common.dto.Result;
import org.apache.rocketmq.order.common.service.SecKillOrderService;
import org.apache.rocketmq.order.common.service.SecKillProductService;
import org.apache.rocketmq.order.common.util.LogExceptionWapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/15 11:25
 * @className SecKillChargeOrderListenerImpl
 * @desc 秒杀订单消费监听回调
 */
@Component
public class SecKillChargeOrderListenerImpl implements MessageListenerConcurrently {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillChargeOrderListenerImpl.class);

    @Resource(name = "secKillOrderService")
    SecKillOrderService secKillOrderService;

    @Autowired
    SecKillProductService secKillProductService;

    /**
     * 秒杀核心消费逻辑
     * @param msgs
     * @param context
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            for (MessageExt msg : msgs) {
                // 消息解码
                String message = new String(msg.getBody());
                int reconsumeTimes = msg.getReconsumeTimes();
                String msgId = msg.getMsgId();
                String logSuffix = ",msgId=" + msgId + ",reconsumeTimes=" + reconsumeTimes;
                LOGGER.info("[秒杀订单消费者]-SecKillChargeOrderConsumer-接收到消息,message={},{}", message, logSuffix);

                // 反序列化协议实体
                ChargeOrderMsgProtocol chargeOrderMsgProtocol = new ChargeOrderMsgProtocol();
                chargeOrderMsgProtocol.decode(message);
                LOGGER.info("[秒杀订单消费者]-SecKillChargeOrderConsumer-反序列化为秒杀入库订单实体chargeOrderMsgProtocol={},{}", chargeOrderMsgProtocol.toString(), logSuffix);

                // 消费幂等:查询orderId对应订单是否已存在
                String orderId = chargeOrderMsgProtocol.getOrderId();
                OrderInfoDobj orderInfoDobj = secKillOrderService.queryOrderInfoById(orderId);
                if (orderInfoDobj != null) {
                    LOGGER.info("[秒杀订单消费者]-SecKillChargeOrderConsumer-当前订单已入库,不需要重复消费!,orderId={},{}", orderId, logSuffix);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                // 业务幂等:同一个prodId+同一个userPhoneNo只有一个秒杀订单
                OrderInfoDO orderInfoDO = new OrderInfoDO();
                orderInfoDO.setProdId(chargeOrderMsgProtocol.getProdId())
                        .setUserPhoneNo(chargeOrderMsgProtocol.getUserPhoneNo());
                Result result = secKillOrderService.queryOrder(orderInfoDO);
                if (result != null && result.getCode().equals(CodeMsg.SUCCESS.getCode())) {
                    LOGGER.info("[秒杀订单消费者]-SecKillChargeOrderConsumer-当前用户={},秒杀的产品={}订单已存在,不得重复秒杀,orderId={}",
                            orderInfoDO.getUserPhoneNo(), orderInfoDO.getProdId(), orderId);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                // 秒杀订单入库
                OrderInfoDO orderInfoDODB = new OrderInfoDO();
                BeanUtils.copyProperties(chargeOrderMsgProtocol, orderInfoDODB);

                // 库存校验
                String prodId = chargeOrderMsgProtocol.getProdId();
                SecKillProductDobj productDobj = secKillProductService.querySecKillProductByProdId(prodId);
                // 取库存校验
                int currentProdStock = productDobj.getProdStock();
                if (currentProdStock <= 0) {
                    LOGGER.info("[decreaseProdStock]当前商品已售罄,消息消费成功!prodId={},currStock={}", prodId, currentProdStock);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                // 正式下单
                if (secKillOrderService.chargeSecKillOrder(orderInfoDODB)) {
                    LOGGER.info("[秒杀订单消费者]-SecKillChargeOrderConsumer-秒杀订单入库成功,消息消费成功!,入库实体orderInfoDO={},{}", orderInfoDO.toString(), logSuffix);
                    // 模拟订单处理，直接修改订单状态为处理中
                    secKillOrderService.updateOrderStatusDealing(orderInfoDODB);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        } catch (Exception e) {
            LOGGER.info("[秒杀订单消费者]消费异常,e={}", LogExceptionWapper.getStackTrace(e));
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
}
