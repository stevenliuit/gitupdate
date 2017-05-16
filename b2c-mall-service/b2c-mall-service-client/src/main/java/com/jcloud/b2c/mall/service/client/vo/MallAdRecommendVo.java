package com.jcloud.b2c.mall.service.client.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 推荐广告关联实体
 */
public class MallAdRecommendVo implements Serializable {
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
     * 广告id
     */
    private Long adId;
    /**
     * 推荐位类型 1、推荐位 2、通栏推荐
     */
    private Integer recommendType;
    /**
     * 排序
     */
    private Integer sortNum;
    /**
     * 广告主体
     */
    private MallAdVo mallAd;
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

    public Integer getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(Integer recommendType) {
        this.recommendType = recommendType;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public MallAdVo getMallAd() {
        return mallAd;
    }

    public void setMallAd(MallAdVo mallAd) {
        this.mallAd = mallAd;
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
        return "MallAdRecommend{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", clientType=" + clientType +
                ", adId=" + adId +
                ", recommendType=" + recommendType +
                ", sortNum=" + sortNum +
                ", mallAd=" + mallAd +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}