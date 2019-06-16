package org.apache.rocketmq.order.common.dao;

import org.apache.rocketmq.order.common.dao.dataobject.OrderInfoDO;
import org.apache.rocketmq.order.common.dao.dataobject.OrderInfoDobj;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/15 11:11
 * @className SecKillOrderMapper
 * @desc 秒杀订单Mapper接口
 */
public interface SecKillOrderMapper {

    /**
     * 查询订单明细
     * @param orderInfoDO
     * @return
     */
    OrderInfoDobj queryOrderInfoById(OrderInfoDO orderInfoDO);

    /**
     * 秒杀订单入库
     * @param orderInfoDO
     * @return
     */
    int insertSecKillOrder(OrderInfoDO orderInfoDO);

    /**
     * 修改订单状态到处理中
     * @param orderInfoDO
     * @return
     */
    int updateOrderStatusDealing(OrderInfoDO orderInfoDO);

    /**
     * 外部查询订单信息
     * @param orderInfoDO
     * @return
     */
    List<OrderInfoDobj> queryOrderInfoByCond(OrderInfoDO orderInfoDO);
}
