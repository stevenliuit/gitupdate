package com.jcloud.b2c.mall.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/17 15:16
 * @lasdate
 */
public class MallHotWords implements Serializable {

    /**
     * 主键
     */
    private long id;

    /**
     * 租户ID
     */
    private long tenantId;

    /**
     * 1、PC 2、H5 3、Android 4、IOS
     */
    private Integer clientType;

    /**
     * 热词
     */
    private String name;

    /**
     * 商品数量
     */
    private Integer totalItem;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * 1、顶部热词 2、默认搜索热词
     */
    private Integer wordsType;

    /**
     * -1、删除 1、上架 2、下架
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getWordsType() {
        return wordsType;
    }

    public void setWordsType(Integer wordsType) {
        this.wordsType = wordsType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "MallHotWords{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", clientType=" + clientType +
                ", name='" + name + '\'' +
                ", totalItem=" + totalItem +
                ", sortNum=" + sortNum +
                ", wordsType=" + wordsType +
                ", state=" + state +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
