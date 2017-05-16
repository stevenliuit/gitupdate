/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallBanner.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.client.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 轮播图+广告位 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 10:42
 * @lastdate:
 */

public class MallBannerVo implements Serializable {
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
     * 名称
     */
    private String name;

    /**
     * 链接
     */
    private String link;

    /**
     * 图片地址
     */
    private String imgSrc;

    /**
     * 类型1、轮播图 2、广告位
     * com.jcloud.b2c.mall.service.client.enums.BannerTypeEnum
     */
    private Integer bannerType;

    /**
     * 频道id
     */
    private Long channelId;

    /**
     * 序号
     */
    private Integer sortNum;

    /**
     * -1、删除 1、上架 2、下架
     * com.jcloud.b2c.mall.service.client.enums.StateEnum
     */
    private Integer state;
    /**
     * 闪屏开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date beginTime;
    /**
     * 闪屏结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date endTime;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 专题skuIds
     */
    private List<Long> skuIds;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getBannerType() {
        return bannerType;
    }

    public void setBannerType(Integer bannerType) {
        this.bannerType = bannerType;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public List<Long> getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(List<Long> skuIds) {
        this.skuIds = skuIds;
    }

    @Override
    public String toString() {
        return "MallBannerVo{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", clientType=" + clientType +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", bannerType=" + bannerType +
                ", channelId=" + channelId +
                ", sortNum=" + sortNum +
                ", state=" + state +
                ", created=" + created +
                ", modified=" + modified +
                ", skuIds=" + skuIds +
                '}';
    }
}
