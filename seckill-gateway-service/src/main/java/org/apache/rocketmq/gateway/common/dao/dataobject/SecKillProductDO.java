package org.apache.rocketmq.gateway.common.dao.dataobject;

import java.math.BigDecimal;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 14:47
 * @className SecKillProductDO
 * @desc 秒杀商品数据库映射实体
 */
public class SecKillProductDO {

    private int id;
    private String prodId;
    private String prodName;
    private int prodStock;
    private BigDecimal prodPrice;
    private int prodStatus;

    public int getProdStatus() {
        return prodStatus;
    }

    public SecKillProductDO setProdStatus(int prodStatus) {
        this.prodStatus = prodStatus;
        return this;
    }

    public BigDecimal getProdPrice() {
        return prodPrice;
    }

    public SecKillProductDO setProdPrice(BigDecimal prodPrice) {
        this.prodPrice = prodPrice;
        return this;
    }

    public int getId() {
        return id;
    }

    public SecKillProductDO setId(int id) {
        this.id = id;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public SecKillProductDO setProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    public String getProdName() {
        return prodName;
    }

    public SecKillProductDO setProdName(String prodName) {
        this.prodName = prodName;
        return this;
    }

    public int getProdStock() {
        return prodStock;
    }

    public SecKillProductDO setProdStock(int prodStock) {
        this.prodStock = prodStock;
        return this;
    }

    @Override
    public String toString() {
        return "SecKillProductDobj{" +
                "id=" + id +
                ", prodId='" + prodId + '\'' +
                ", prodName='" + prodName + '\'' +
                ", prodStock=" + prodStock +
                ", prodPrice=" + prodPrice +
                ", prodStatus=" + prodStatus +
                '}';
    }
}
