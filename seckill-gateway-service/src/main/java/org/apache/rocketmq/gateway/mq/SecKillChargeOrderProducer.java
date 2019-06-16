package org.apache.rocketmq.gateway.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/15 10:43
 * @className SecKillChargeOrderProducer
 * @desc 秒杀订单生产者
 */
@Component
public class SecKillChargeOrderProducer implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillChargeOrderProducer.class);

    @Value("${rocketmq.nameServer}")
    String nameSrvAddr;

    private DefaultMQProducer defaultMQProducer;

    public DefaultMQProducer getProducer() {
        return defaultMQProducer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // 初始化秒杀下单生产者

        // 设置nameSrvAddr

        // 设置发送重试次数=3

        // 启动生产者

        LOGGER.info("[秒杀订单生产者]--SecKillChargeOrderProducer加载完成!");
    }
}
