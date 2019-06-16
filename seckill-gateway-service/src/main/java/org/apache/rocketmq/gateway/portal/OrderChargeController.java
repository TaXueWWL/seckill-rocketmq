package org.apache.rocketmq.gateway.portal;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.common.request.QueryOrderRequest;
import org.apache.rocketmq.gateway.common.dto.CodeMsg;
import org.apache.rocketmq.gateway.common.dto.Result;
import org.apache.rocketmq.gateway.common.dto.request.ChargeOrderRequest;
import org.apache.rocketmq.gateway.common.init.SecKillProductConfig;
import org.apache.rocketmq.gateway.common.service.SecKillChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/10 16:13
 * @className OrderChargeController
 * @desc 订单充值接口
 */
@Controller
@RequestMapping("api")
public class OrderChargeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderChargeController.class);

    @Resource(name = "secKillChargeService")
    SecKillChargeService secKillChargeService;

    @Autowired
    SecKillProductConfig secKillProductConfig;

    /**
     * 平台下单接口
     * @param chargeOrderRequest
     * @return
     */
    @RequestMapping(value = "charge.do", method = {RequestMethod.POST})
    public @ResponseBody Result chargeOrder(@ModelAttribute ChargeOrderRequest chargeOrderRequest) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionId = attributes.getSessionId();
        // 下单前置参数校验
        if (!secKillChargeService.checkParamsBeforeSecKillCharge(chargeOrderRequest, sessionId)) {
            return Result.error(CodeMsg.PARAM_INVALID);
        }
        // 前置商品校验
        String prodId = chargeOrderRequest.getProdId();
        if (!secKillChargeService.checkProdConfigBeforeKillCharge(prodId, sessionId)) {
            return Result.error(CodeMsg.PRODUCT_NOT_EXIST);
        }
        // 前置预减库存
        if (!secKillProductConfig.preReduceProdStock(prodId)) {
            return Result.error(CodeMsg.PRODUCT_STOCK_NOT_ENOUGH);
        }
        // 秒杀订单入队
        return secKillChargeService.secKillOrderEnqueue(chargeOrderRequest, sessionId);
    }

    /**
     * 平台查单接口
     * @param queryOrderRequest
     * @return
     */
    @RequestMapping(value = "query.do", method = {RequestMethod.GET})
    public @ResponseBody String queryOrder(@ModelAttribute QueryOrderRequest queryOrderRequest) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionId = attributes.getSessionId();
        // 查询前置参数校验
        if (!secKillChargeService.checkParamsBeforeSecKillQuery(queryOrderRequest, sessionId)) {
            return JSON.toJSONString(Result.error(CodeMsg.PARAM_INVALID));
        }
        // 查询订单
        return JSON.toJSONString(secKillChargeService.queryOrder(queryOrderRequest, sessionId));
    }

}
