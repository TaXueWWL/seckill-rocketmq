package org.apache.rocketmq.order.common.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.response.QueryOrderResponse;
import org.apache.rocketmq.order.common.dao.SecKillOrderMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/15 11:12
 * @className SecKillOrderServiceImpl
 * @desc 秒杀订单本地service实现
 */
@Service(value = "secKillOrderService")
public class SecKillOrderServiceImpl implements SecKillOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillOrderServiceImpl.class);

    @Autowired
    SecKillOrderMapper secKillOrderMapper;

    @Autowired
    SecKillProductService secKillProductService;

    /**
     * 根据订单号查询订单明细
     * @param orderId
     * @return
     */
    @Override
    public OrderInfoDobj queryOrderInfoById(String orderId) {
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        orderInfoDO.setOrderId(orderId);
        return secKillOrderMapper.queryOrderInfoById(orderInfoDO);
    }

    /**
     * 秒杀订单入库
     * @param orderInfoDO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean chargeSecKillOrder(OrderInfoDO orderInfoDO) {
        int insertCount = 0;
        String orderId = orderInfoDO.getOrderId();
        String prodId = orderInfoDO.getProdId();

        // 减库存
        if (!secKillProductService.decreaseProdStock(prodId)) {
            LOGGER.info("[insertSecKillOrder]orderId={},prodId={},下单前减库存失败,下单失败!", orderId, prodId);
            // TODO 此处可给用户发送通知，告知秒杀下单失败，原因：商品已售罄
            return false;
        }
        // 设置产品名称
        SecKillProductDobj productInfo = secKillProductService.querySecKillProductByProdId(prodId);
        orderInfoDO.setProdName(productInfo.getProdName());
        try {
            insertCount = secKillOrderMapper.insertSecKillOrder(orderInfoDO);
        } catch (Exception e) {
            LOGGER.error("[insertSecKillOrder]orderId={},秒杀订单入库[异常],事务回滚,e={}", orderId, LogExceptionWapper.getStackTrace(e));
            String message =
                    String.format("[insertSecKillOrder]orderId=%s,秒杀订单入库[异常],事务回滚", orderId);
            throw new RuntimeException(message);
        }
        if (insertCount != 1) {
            LOGGER.error("[insertSecKillOrder]orderId={},秒杀订单入库[失败],事务回滚,e={}", orderId);
            String message =
                    String.format("[insertSecKillOrder]orderId=%s,秒杀订单入库[失败],事务回滚", orderId);
            throw new RuntimeException(message);
        }
        return true;
    }

    /**
     * 修改订单状态到处理中
     * @param orderInfoDO
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderStatusDealing(OrderInfoDO orderInfoDO) {
        int updateCount = 0;
        String orderId = orderInfoDO.getOrderId();
        try {
            updateCount = secKillOrderMapper.updateOrderStatusDealing(orderInfoDO);
        } catch (Exception e) {
            LOGGER.error("[updateOrderStatusDealing]orderId={},订单状态修改为处理中[异常],事务回滚,e={}", orderId, LogExceptionWapper.getStackTrace(e));
            String message =
                    String.format("[updateOrderStatusDealing]orderId=%s,订单状态修改为处理中[异常],事务回滚", orderId);
            throw new RuntimeException(message);
        }
        if (updateCount != 1) {
            LOGGER.error("[updateOrderStatusDealing]orderId={},订单状态修改为处理中[失败],事务回滚,e={}", orderId);
            String message =
                    String.format("[updateOrderStatusDealing]orderId=%s,订单状态修改为处理中[失败],事务回滚", orderId);
            throw new RuntimeException(message);
        }
        LOGGER.info("[updateOrderStatusDealing]orderId={},订单状态修改为处理中[成功]", orderId);
    }

    /**
     * 内部订单查询
     * @return
     */
    @Override
    public Result queryOrder(OrderInfoDO orderInfoDO) {

        LOGGER.info("[queryOrder]-内部订单查询开始,入参={}", orderInfoDO);
        // 查询
        OrderInfoDobj orderInfo;
        try {
            List<OrderInfoDobj> orderInfoDobjs = secKillOrderMapper.queryOrderInfoByCond(orderInfoDO);
            LOGGER.info("[queryOrder]-orderInfoDobjs={}", orderInfoDobjs);
            if (orderInfoDobjs == null || orderInfoDobjs.size() <= 0) {
                return Result.error(CodeMsg.BIZ_ERROR);
            }
            orderInfo = orderInfoDobjs.get(0);
        } catch (Exception e) {
            LOGGER.error("[queryOrder]-内部订单查询异常,e={}", LogExceptionWapper.getStackTrace(e));
            return Result.error(CodeMsg.SERVER_ERROR);
        }
        QueryOrderResponse queryOrderResponse = new QueryOrderResponse();
        BeanUtils.copyProperties(orderInfo, queryOrderResponse);
        Result result = Result.success(CodeMsg.SUCCESS, queryOrderResponse);
        LOGGER.info("[queryOrder]-内部订单查询,出参={}", JSON.toJSONString(result));
        return result;
    }

}
