package org.apache.rocketmq.gateway.mq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/9/27 0:22
 * @className ChargeOrderBootProducer
 * @desc 秒杀订单生产者Spring版本
 */
@Component
public class ChargeOrderBootProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargeOrderBootProducer.class);

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public DefaultMQProducer getProducer() {
        return rocketMQTemplate.getProducer();
    }
}
