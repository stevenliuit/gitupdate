/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdPrincipal.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.domain;

import java.util.Date;

/**
 * @description: 广告主体
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 18:19
 * @lastdate:
 */

public abstract class MallAdPrincipal {
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
     * 广告ID
     */
    private Long adId;

    /**
     * 序号
     */
    private Integer sortNum;

    /**
     * 广告信息
     */
    private MallAd mallAd;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

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

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
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

    public MallAd getMallAd() {
        return mallAd;
    }

    public void setMallAd(MallAd mallAd) {
        this.mallAd = mallAd;
    }

    @Override
    public String toString() {
        return "MallAdPrincipal{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", clientType=" + clientType +
                ", adId=" + adId +
                ", sortNum=" + sortNum +
                ", mallAd=" + mallAd +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
