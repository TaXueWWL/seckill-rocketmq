package org.apache.rocketmq.order.common.dao.dataobject;

import java.util.Date;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/11 19:50
 * @className OrderInfoDO
 * @desc 订单数据库映射实体
 */
public class OrderInfoDobj {

    private Integer id;
    private Date gmtCreate;
    private Date gmtUpdate;
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

    public OrderInfoDobj setProdName(String prodName) {
        this.prodName = prodName;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public OrderInfoDobj setId(Integer id) {
        this.id = id;
        return this;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public OrderInfoDobj setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public OrderInfoDobj setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderInfoDobj setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public OrderInfoDobj setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public OrderInfoDobj setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public OrderInfoDobj setProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public OrderInfoDobj setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
        return this;
    }

    public Date getChargeTime() {
        return chargeTime;
    }

    public OrderInfoDobj setChargeTime(Date chargeTime) {
        this.chargeTime = chargeTime;
        return this;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public OrderInfoDobj setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    @Override
    public String toString() {
        return "OrderInfoDobj{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtUpdate=" + gmtUpdate +
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
