package org.apache.rocketmq.order.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/15 11:16
 * @className SecKillChargeOrderConsumer
 * @desc 秒杀下单消费者
 */
@Component
public class SecKillChargeOrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillChargeOrderConsumer.class);

    @Value("${rocketmq.nameServer}")
    String nameSrvAddr;

    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Resource(name = "secKillChargeOrderListenerImpl")
    private MessageListenerConcurrently messageListener;

    @PostConstruct
    public void init() {
        // 初始化defaultMQPushConsumer

        // 设置nameSrvAddr

        // 从头开始消费

        // 消费模式:集群模式

        // 注册监听器

        // 订阅所有消息
        LOGGER.info("[秒杀下单消费者]--SecKillChargeOrderConsumer加载完成!");
    }
}
