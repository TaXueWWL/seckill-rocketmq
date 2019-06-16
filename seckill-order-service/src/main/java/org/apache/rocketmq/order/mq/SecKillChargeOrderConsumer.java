package org.apache.rocketmq.order.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.message.constant.MessageProtocolConst;
import org.apache.rocketmq.order.common.util.LogExceptionWapper;
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
        defaultMQPushConsumer = new DefaultMQPushConsumer(MessageProtocolConst.SECKILL_CHARGE_ORDER_TOPIC.getConsumerGroup());
        defaultMQPushConsumer.setNamesrvAddr(nameSrvAddr);
        // 从头开始消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 消费模式:集群模式
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册监听器
        defaultMQPushConsumer.registerMessageListener(messageListener);
        // 订阅所有消息
        try {
            defaultMQPushConsumer.subscribe(MessageProtocolConst.SECKILL_CHARGE_ORDER_TOPIC.getTopic(), "*");
            // 启动消费者
            defaultMQPushConsumer.start();
        } catch (MQClientException e) {
            LOGGER.error("[秒杀下单消费者]--SecKillChargeOrderConsumer加载异常!e={}", LogExceptionWapper.getStackTrace(e));
            throw new RuntimeException("[秒杀下单消费者]--SecKillChargeOrderConsumer加载异常!", e);
        }
        LOGGER.info("[秒杀下单消费者]--SecKillChargeOrderConsumer加载完成!");
    }
}
