package org.apache.rocketmq.common.request;

import java.io.Serializable;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/16 10:49
 * @className QueryOrderRequest
 * @desc 平台查单请求实体
 */
public class QueryOrderRequest implements Serializable {

    private static final long serialVersionUID = 7143860158400568786L;

    /**用户下单手机号*/
    private String userPhoneNum;
    /**商品id*/
    private String prodId;

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public QueryOrderRequest setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public QueryOrderRequest setProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    @Override
    public String toString() {
        return "QueryOrderRequest{" +
                "userPhoneNum='" + userPhoneNum + '\'' +
                ", prodId='" + prodId + '\'' +
                '}';
    }
}
