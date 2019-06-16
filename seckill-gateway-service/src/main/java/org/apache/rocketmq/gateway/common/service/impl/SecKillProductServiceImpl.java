package org.apache.rocketmq.gateway.common.service.impl;

import org.apache.rocketmq.gateway.common.dao.SecKillProductMapper;
import org.apache.rocketmq.gateway.common.dao.dataobject.SecKillProductDobj;
import org.apache.rocketmq.gateway.common.service.SecKillProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 14:53
 * @className SecKillProductServiceImpl
 * @desc 秒杀商品服务实现
 */
@Service(value = "secKillProductService")
public class SecKillProductServiceImpl implements SecKillProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillProductServiceImpl.class);

    @Autowired
    SecKillProductMapper secKillProductMapper;

    /**
     * 获取秒杀商品列表
     * @return
     */
    @Override
    public List<SecKillProductDobj> querySecKillProductList() {
        return secKillProductMapper.querySecKillProductList();
    }
}
