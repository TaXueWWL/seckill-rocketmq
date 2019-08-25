package org.apache.rocketmq.message.constant;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/11 14:45
 * @className MessageProtocolConst
 * @desc 消息协议常量
 */
public enum MessageProtocolConst {

    /**SECKILL_CHARGE_ORDER_TOPIC 秒杀下单消息协议*/
    SECKILL_CHARGE_ORDER_TOPIC("SECKILL_CHARGE_ORDER_TOPIC", "GID_SNOWALKE_TEST", "GID_SNOWALKE_TEST", "秒杀下单消息协议"),
    ;
    /**消息主题*/
    private String topic;
    /**生产者组*/
    private String producerGroup;
    /**消费者组*/
    private String consumerGroup;
    /**消息描述*/
    private String desc;

    MessageProtocolConst(String topic, String producerGroup, String consumerGroup, String desc) {
        this.topic = topic;
        this.producerGroup = producerGroup;
        this.consumerGroup = consumerGroup;
        this.desc = desc;
    }

    public String getTopic() {
        return topic;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public String getDesc() {
        return desc;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }}
