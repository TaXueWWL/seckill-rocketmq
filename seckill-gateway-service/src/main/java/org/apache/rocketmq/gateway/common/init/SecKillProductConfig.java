package org.apache.rocketmq.gateway.common.init;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import org.apache.rocketmq.gateway.common.dao.dataobject.SecKillProductDobj;
import org.apache.rocketmq.gateway.common.service.SecKillProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 14:43
 * @className SecKillProductConfig
 * @desc 秒杀商品初始化加载，正式生产环境是有一个商品服务的，这里简化处理了
 */
@Component
public class SecKillProductConfig implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillProductConfig.class);

    private static final Map<String, SecKillProductDobj> PRODUCT_CONFIG_MAP =
            new ConcurrentHashMap<>(16);

    @Resource(name = "secKillProductService")
    SecKillProductService secKillProductService;

    @Override
    public void afterPropertiesSet() throws Exception {

        List<SecKillProductDobj> killProductList = secKillProductService.querySecKillProductList();
        if (killProductList == null) {
            throw new RuntimeException("请确保数据库中存在秒杀商品配置!");
        }
        killProductList.stream().forEach((secKillProductDobj -> {
            String prodId = secKillProductDobj.getProdId();
            PRODUCT_CONFIG_MAP.put(prodId, secKillProductDobj);
        }));
        LOGGER.info("[SecKillProductConfig]初始化秒杀配置完成,商品信息=[{}]", JSON.toJSONString(PRODUCT_CONFIG_MAP));
    }

    /**
     * 预减库存
     * @param prodId
     * @return
     */
    public boolean preReduceProdStock(String prodId) {
        Preconditions.checkNotNull(prodId, "请确保prodId非空!");
        synchronized (this) {
            SecKillProductDobj productDobj = getProductConfigMap().get(prodId);
            int prodStock = productDobj.getProdStock();
            if (prodStock <= 0) {
                LOGGER.info("prodId={},prodStock={},当前秒杀商品库存已不足!", prodId, prodStock);
                return false;
            }

            int afterPreReduce = prodStock - 1;
            if (afterPreReduce < 0) {
                // 预减库存后小于0,说明库存预减失败,库存已不足
                LOGGER.info("prodId={} 预减库存失败,当前库存已不足!", prodId);
                return false;
            }
            // 预减库存成功,回写库存
            LOGGER.info("prodId={} 预减库存成功,当前扣除后剩余库存={}!", prodId, afterPreReduce);
            productDobj.setProdStock(afterPreReduce);
            getProductConfigMap().put(prodId, productDobj);
            return true;
        }
    }

    /**
     * 根据商品id获取商品配置信息
     * @param prodId
     * @return
     */
    public SecKillProductDobj getProductConfig(String prodId) {
        return getProductConfigMap().get(prodId);
    }

    public static Map<String, SecKillProductDobj> getProductConfigMap() {
        return PRODUCT_CONFIG_MAP;
    }
}
