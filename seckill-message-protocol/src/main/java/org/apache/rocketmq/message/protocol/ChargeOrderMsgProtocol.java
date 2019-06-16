package org.apache.rocketmq.message.protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.apache.rocketmq.message.constant.MessageProtocolConst;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/12 11:55
 * @className OrderNofityProtocol
 * @desc 订单结果通知协议
 */
public class ChargeOrderMsgProtocol extends BaseMsg implements Serializable {

    private static final long serialVersionUID = 73717163386598209L;

    /**订单号*/
    private String orderId;
    /**用户下单手机号*/
    private String userPhoneNo;
    /**商品id*/
    private String prodId;
    /**用户交易金额*/
    private String chargeMoney;

    private Map<String, String> header;
    private Map<String, String> body;

    @Override
    public String encode() {
        // 组装消息协议头
        ImmutableMap.Builder headerBuilder = new ImmutableMap.Builder<String, String>()
                .put("version", this.getVersion())
                .put("topicName", MessageProtocolConst.SECKILL_CHARGE_ORDER_TOPIC.getTopic());
        header = headerBuilder.build();

        body = new ImmutableMap.Builder<String, String>()
                .put("orderId", this.getOrderId())
                .put("userPhoneNo", this.getUserPhoneNo())
                .put("prodId", this.getProdId())
                .put("chargeMoney", this.getChargeMoney())
                .build();

        ImmutableMap<String, Object> map = new ImmutableMap.Builder<String, Object>()
                .put("header", header)
                .put("body", body)
                .build();

        // 返回序列化消息Json串
        String ret_string = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ret_string = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("ChargeOrderMsgProtocol消息序列化json异常", e);
        }
        return ret_string;
    }

    @Override
    public void decode(String msg) {
        Preconditions.checkNotNull(msg);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(msg);
            // header
            this.setVersion(root.get("header").get("version").asText());
            this.setTopicName(root.get("header").get("topicName").asText());
            // body
            this.setOrderId(root.get("body").get("orderId").asText());
            this.setUserPhoneNo(root.get("body").get("userPhoneNo").asText());
            this.setChargeMoney(root.get("body").get("chargeMoney").asText());
            this.setProdId(root.get("body").get("prodId").asText());
        } catch (IOException e) {
            throw new RuntimeException("ChargeOrderMsgProtocol消息反序列化异常", e);
        }
    }

    public String getOrderId() {
        return orderId;
    }

    public ChargeOrderMsgProtocol setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public ChargeOrderMsgProtocol setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public ChargeOrderMsgProtocol setProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public ChargeOrderMsgProtocol setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
        return this;
    }

    @Override
    public String toString() {
        return "ChargeOrderMsgProtocol{" +
                "orderId='" + orderId + '\'' +
                ", userPhoneNo='" + userPhoneNo + '\'' +
                ", prodId='" + prodId + '\'' +
                ", chargeMoney='" + chargeMoney + '\'' +
                ", header=" + header +
                ", body=" + body +
                "} " + super.toString();
    }
}
