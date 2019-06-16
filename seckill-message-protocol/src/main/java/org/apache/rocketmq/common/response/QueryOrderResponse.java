package org.apache.rocketmq.common.response;

import java.io.Serializable;
import java.util.Date;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/16 10:52
 * @className QueryOrderResponse
 * @desc 平台订单查询返回实体
 */
public class QueryOrderResponse implements Serializable {

    private static final long serialVersionUID = 8752405981800372807L;

    private String orderId;
    private Integer orderStatus;
    private String userPhoneNo;
    private String prodId;
    private String prodName;
    private String chargeMoney;
    private Date chargeTime;
    private Date finishTime;

    public String getOrderId() {
        return orderId;
    }

    public QueryOrderResponse setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public QueryOrderResponse setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public QueryOrderResponse setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public QueryOrderResponse setProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    public String getProdName() {
        return prodName;
    }

    public QueryOrderResponse setProdName(String prodName) {
        this.prodName = prodName;
        return this;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public QueryOrderResponse setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
        return this;
    }

    public Date getChargeTime() {
        return chargeTime;
    }

    public QueryOrderResponse setChargeTime(Date chargeTime) {
        this.chargeTime = chargeTime;
        return this;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public QueryOrderResponse setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    @Override
    public String toString() {
        return "QueryOrderResponse{" +
                "orderId='" + orderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", userPhoneNo='" + userPhoneNo + '\'' +
                ", prodId='" + prodId + '\'' +
                ", prodName='" + prodName + '\'' +
                ", chargeMoney='" + chargeMoney + '\'' +
                ", chargeTime=" + chargeTime +
                ", finishTime=" + finishTime +
                '}';
    }
}
