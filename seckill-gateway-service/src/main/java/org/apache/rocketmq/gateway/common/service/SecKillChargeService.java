package org.apache.rocketmq.gateway.common.service;

import org.apache.rocketmq.common.request.QueryOrderRequest;
import org.apache.rocketmq.gateway.common.dto.Result;
import org.apache.rocketmq.gateway.common.dto.request.ChargeOrderRequest;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 23:04
 * @className SecKillChargeService
 * @desc 秒杀下单service
 */
public interface SecKillChargeService {

    /**
     * 秒杀下单前置参数校验
     * @param chargeOrderRequest
     * @param sessionId
     * @return
     */
    boolean checkParamsBeforeSecKillCharge(ChargeOrderRequest chargeOrderRequest, String sessionId);

    /**
     * 秒杀下单前置商品校验
     * @param prodId
     * @param sessionId
     * @return
     */
    boolean checkProdConfigBeforeKillCharge(String prodId, String sessionId);

    /**
     * 秒杀订单入队
     * @param chargeOrderRequest
     * @param sessionId
     * @return
     */
    Result secKillOrderEnqueue(ChargeOrderRequest chargeOrderRequest, String sessionId);

    /**
     * 秒杀查询前置参数校验
     * @param queryOrderRequest
     * @param sessionId
     * @return
     */
    boolean checkParamsBeforeSecKillQuery(QueryOrderRequest queryOrderRequest, String sessionId);

    /**
     * 执行订单查询业务
     * @param queryOrderRequest
     * @param sessionId
     * @return
     */
    Result queryOrder(QueryOrderRequest queryOrderRequest, String sessionId);
}
