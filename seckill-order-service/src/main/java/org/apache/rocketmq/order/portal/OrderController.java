package org.apache.rocketmq.order.portal;

import org.apache.rocketmq.common.request.QueryOrderRequest;
import org.apache.rocketmq.order.common.dao.dataobject.OrderInfoDO;
import org.apache.rocketmq.order.common.dto.Result;
import org.apache.rocketmq.order.common.service.SecKillOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/10 16:13
 * @className OrderController
 * @desc 订单充值接口
 */
@Controller
@RequestMapping("api")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    SecKillOrderService secKillOrderService;

    /**
     * 平台内部查单接口
     * @param queryOrderRequest
     * @return
     */
    @RequestMapping(value = "query.do", method = {RequestMethod.POST})
    public @ResponseBody Result queryOrder(@ModelAttribute QueryOrderRequest queryOrderRequest) {
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        orderInfoDO.setProdId(queryOrderRequest.getProdId())
                .setUserPhoneNo(queryOrderRequest.getUserPhoneNum());
        // 查询订单
        return secKillOrderService.queryOrder(orderInfoDO);
    }

}
