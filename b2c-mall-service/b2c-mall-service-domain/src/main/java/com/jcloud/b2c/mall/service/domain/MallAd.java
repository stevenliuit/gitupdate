/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAd.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 首页广告
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 10:52
 * @lastdate:
 */

public class MallAd implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 客户端类型 1、PC 2、H5 3、Android 4、IOS
     * com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum
     */
    private Integer clientType;

    /**
     * 类型
     * com.jcloud.b2c.mall.service.client.enums.AdTypeEnum
     */
    private Integer adType;

    /**
     * 广告词
     */
    private String adWords;

    /**
     * 商品ID
     */
    private Long itemId;

    /**
     * 链接
     */
    private String link;

    /**
     * 图片地址
     */
    private String imgSrc;

    /**
     * -1、删除 1、上架 2、下架
     * com.jcloud.b2c.mall.service.client.enums.StateEnum
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

    /**
     * 排序号
     */
    private Integer sortNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    public String getAdWords() {
        return adWords;
    }

    public void setAdWords(String adWords) {
        this.adWords = adWords;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
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

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "MallAd{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", clientType=" + clientType +
                ", adType=" + adType +
                ", adWords='" + adWords + '\'' +
                ", itemId=" + itemId +
                ", link='" + link + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", state=" + state +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
