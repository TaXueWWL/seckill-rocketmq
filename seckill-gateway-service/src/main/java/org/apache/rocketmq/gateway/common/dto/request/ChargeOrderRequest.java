package org.apache.rocketmq.gateway.common.dto.request;

import java.io.Serializable;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/10 14:33
 * @className ChargeOrderRequest
 * @desc 下单接口请求参数
 */
public class ChargeOrderRequest implements Serializable {

    private static final long serialVersionUID = 2596328097263464531L;

    /**充值手机号*/
    private String userPhoneNum;
    /**充值金额*/
    private String chargePrice;
    /**商品id*/
    private String prodId;

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public ChargeOrderRequest setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
        return this;
    }

    public String getChargePrice() {
        return chargePrice;
    }

    public ChargeOrderRequest setChargePrice(String chargePrice) {
        this.chargePrice = chargePrice;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public ChargeOrderRequest setProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    @Override
    public String toString() {
        return "ChargeOrderRequest{" +
                "userPhoneNum='" + userPhoneNum + '\'' +
                ", chargePrice='" + chargePrice + '\'' +
                ", prodId='" + prodId + '\'' +
                '}';
    }
}
