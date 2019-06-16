package org.apache.rocketmq.gateway.common.dao;

import org.apache.rocketmq.gateway.common.dao.dataobject.SecKillProductDobj;

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
}
