package org.apache.rocketmq.gateway.common.service.impl;

import org.apache.rocketmq.gateway.common.dao.SecKillProductMapper;
import org.apache.rocketmq.gateway.common.dao.dataobject.SecKillProductDO;
import org.apache.rocketmq.gateway.common.dao.dataobject.SecKillProductDobj;
import org.apache.rocketmq.gateway.common.service.SecKillProductService;
import org.apache.rocketmq.gateway.common.util.LogExceptionWapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 获取秒杀商品列表
     * @return
     */
    @Override
    public List<SecKillProductDobj> querySecKillProductList() {
        return secKillProductMapper.querySecKillProductList();
    }

    /**
     * 根据产品id查询产品信息
     * @param prodId
     * @return
     */
    @Override
    public SecKillProductDobj queryProdById(String prodId) {
        // 查缓存
        SecKillProductDobj secKillProductDobj = (SecKillProductDobj) redisTemplate.opsForValue().get(prodId);
        LOGGER.info("根据prodId=[{}],从缓存中获取产品信息:[{}]", prodId, secKillProductDobj);
        if (secKillProductDobj == null) {
            // 查库
            LOGGER.info("根据prodId=[{}],从缓存中获取产品信息为空,从数据库中查询", prodId);
            secKillProductDobj = secKillProductMapper.queryProdById(prodId);
            // 回写缓存
            redisTemplate.opsForValue().set(prodId, secKillProductDobj, 86400, TimeUnit.SECONDS);
        }
        LOGGER.info("根据prodId=[{}],查询到产品信息为:[{}]", prodId, secKillProductDobj);
        return secKillProductDobj;
    }

    /**
     * 修改产品信息
     * @param updateProdData
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateProdInfo(SecKillProductDO updateProdData) {
        String prodId = updateProdData.getProdId();
        LOGGER.info("更新产品信息开始,入参:[{}]", updateProdData.toString());
        int updateCount = 0;
        try {
            updateCount = secKillProductMapper.updateProdInfo(updateProdData);
        } catch (Exception e) {
            LOGGER.error("更新商品信息[异常],事务回滚,prodId={},e={}", prodId, LogExceptionWapper.getStackTrace(e));
            String message =
                    String.format("更新商品信息异常,商品id=%s,事务回滚", prodId);
            throw new RuntimeException(message);
        }
        if (updateCount != 1) {
            LOGGER.error("更新商品信息[失败],事务回滚,prodId={}", prodId);
            String message =
                    String.format("更新商品信息失败,商品id=%s,事务回滚", prodId);
            throw new RuntimeException(message);
        }
        // 更新完成缓存失效
        if (redisTemplate.delete(prodId)) {
            // 发送告警日志，通知缓存失效失败
            LOGGER.warn("商品信息缓存失效[失败],请关注!,prodId={}", prodId);
        }
        LOGGER.info("更新产品信息成功,刷新缓存完成.prodId={}", prodId);
    }
}
