package org.apache.rocketmq.order.common.dao;


import org.apache.rocketmq.order.common.dao.dataobject.SecKillProductDO;
import org.apache.rocketmq.order.common.dao.dataobject.SecKillProductDobj;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 14:45
 * @className SecKillProductMapper
 * @desc 秒杀商品Mapper接口
 */
public interface SecKillProductMapper {

    /**
     * 获取秒杀商品列表
     * @return
     */
    List<SecKillProductDobj> querySecKillProductList();

    /**
     * 根据商品id查询商品信息
     * @param productDO
     * @return
     */
    SecKillProductDobj querySecKillProductByProdId(SecKillProductDO productDO);

    /**
     * 商品扣减库存
     * @param productDO
     * @return
     */
    int decreaseProdStock(SecKillProductDO productDO);
}
