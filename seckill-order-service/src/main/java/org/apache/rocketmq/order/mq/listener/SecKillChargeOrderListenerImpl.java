package org.apache.rocketmq.order.mq.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.order.common.service.SecKillOrderService;
import org.apache.rocketmq.order.common.service.SecKillProductService;
import org.apache.rocketmq.order.common.util.LogExceptionWapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/15 11:25
 * @className SecKillChargeOrderListenerImpl
 * @desc 秒杀订单消费监听回调
 */
@Component
public class SecKillChargeOrderListenerImpl implements MessageListenerConcurrently {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillChargeOrderListenerImpl.class);

    @Resource(name = "secKillOrderService")
    SecKillOrderService secKillOrderService;

    @Autowired
    SecKillProductService secKillProductService;

    /**
     * 秒杀核心消费逻辑
     * @param msgs
     * @param context
     * @return
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            for (MessageExt msg : msgs) {
                // 消息解码
                String message = new String(msg.getBody());
                int reconsumeTimes = msg.getReconsumeTimes();
                String msgId = msg.getMsgId();
                String logSuffix = ",msgId=" + msgId + ",reconsumeTimes=" + reconsumeTimes;
                LOGGER.info("[秒杀订单消费者]-SecKillChargeOrderConsumer-接收到消息,message={},{}", message, logSuffix);

                // 反序列化协议实体

                // 消费幂等:查询orderId对应订单是否已存在

                // 业务幂等:同一个prodId+同一个userPhoneNo只有一个秒杀订单 Result result = secKillOrderService.queryOrder(orderInfoDO);

                // 秒杀订单入库

                // 查产品取库存校验 <= 0 则商品已售罄，直接消费成功

                // 正式下单

                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        } catch (Exception e) {
            LOGGER.error("[秒杀订单消费者]消费异常,e={}", LogExceptionWapper.getStackTrace(e));
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
}
