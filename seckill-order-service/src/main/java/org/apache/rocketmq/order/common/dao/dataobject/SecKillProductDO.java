package org.apache.rocketmq.order.common.dao.dataobject;

/**
 * @author snowalker
 * @version 1.0
 * @date 2019/6/14 14:47
 * @className SecKillProductDobj
 * @desc 秒杀商品数据库查询实体
 */
public class SecKillProductDO {

    private String prodId;

    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public SecKillProductDO setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getProdId() {
        return prodId;
    }

    public SecKillProductDO setProdId(String prodId) {
        this.prodId = prodId;
        return this;
    }

    @Override
    public String toString() {
        return "SecKillProductDO{" +
                "prodId='" + prodId + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
