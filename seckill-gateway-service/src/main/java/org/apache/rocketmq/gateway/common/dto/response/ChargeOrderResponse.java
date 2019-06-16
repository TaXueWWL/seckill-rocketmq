package org.apache.rocketmq.gateway.common.dto.response;

import java.io.Serializable;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/10 16:03
 * @className ChargeOrderResponse
 * @desc 下单sdk接口返回参数
 */
public class ChargeOrderResponse implements Serializable {

    private static final long serialVersionUID = -5685058946404699059L;

    /**秒杀订单号*/
    private String orderId;
    /**用户下单手机号*/
    private String userPhoneNo;
    /**商品id*/
    private String prodId;
    /**用户交易金额*/
    private String chargeMoney;

    public String getOrderId() {
        return orderId;
    }

    public ChargeOrderResponse setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public ChargeOrderResponse setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public ChargeOrderResponse setProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public ChargeOrderResponse setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
        return this;
    }

    @Override
    public String toString() {
        return "ChargeOrderResponse{" +
                "orderId='" + orderId + '\'' +
                ", userPhoneNo='" + userPhoneNo + '\'' +
                ", prodId='" + prodId + '\'' +
                ", chargeMoney='" + chargeMoney + '\'' +
                '}';
    }
}
