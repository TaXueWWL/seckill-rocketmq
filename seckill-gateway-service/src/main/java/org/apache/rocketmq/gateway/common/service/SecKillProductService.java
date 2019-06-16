package org.apache.rocketmq.gateway.common.service;

import org.apache.rocketmq.gateway.common.dao.dataobject.SecKillProductDobj;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 14:52
 * @className SecKillProductService
 * @desc 秒杀商品service接口
 */
public interface SecKillProductService {

    /**
     * 获取秒杀商品列表
     * @return
     */
    List<SecKillProductDobj> querySecKillProductList();
}
