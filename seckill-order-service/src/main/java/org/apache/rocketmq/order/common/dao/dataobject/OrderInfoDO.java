package org.apache.rocketmq.order.common.dao.dataobject;

import java.util.Date;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/11 19:50
 * @className OrderInfoDO
 * @desc 订单查询入参实体
 */
public class OrderInfoDO {

    private Integer id;
    private String orderId;
    private Integer orderStatus;
    private String userPhoneNo;
    private String prodId;
    private String prodName;
    private String chargeMoney;
    private Date chargeTime;
    private Date finishTime;

    public String getProdName() {
        return prodName;
    }

    public OrderInfoDO setProdName(String prodName) {
        this.prodName = prodName;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public OrderInfoDO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderInfoDO setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public OrderInfoDO setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public OrderInfoDO setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public OrderInfoDO setProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public OrderInfoDO setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
        return this;
    }

    public Date getChargeTime() {
        return chargeTime;
    }

    public OrderInfoDO setChargeTime(Date chargeTime) {
        this.chargeTime = chargeTime;
        return this;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public OrderInfoDO setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    @Override
    public String toString() {
        return "OrderInfoDO{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
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
