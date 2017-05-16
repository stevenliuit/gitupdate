/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: Test.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/13
 */
package com.jcloud.b2c.mall.service.client.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 楼层
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-13 21:18
 * @lastdate:
 */
public class MallFloorVo implements Serializable {
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
     * 楼层短标题
     */
    private String shortName;

    /**
     * 子名称
     */
    private String subName;

    /**
     * 类型 1、普通 2、活动 3、新品
     * com.jcloud.b2c.mall.service.client.enums.FloorTypeEnum
     */
    private Integer floorType;

    /**
     * 更多链接
     */
    private String moreLink;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getFloorType() {
        return floorType;
    }

    public void setFloorType(Integer floorType) {
        this.floorType = floorType;
    }

    public String getMoreLink() {
        return moreLink;
    }

    public void setMoreLink(String moreLink) {
        this.moreLink = moreLink;
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

    @Override
    public String toString() {
        return "MallFloorVo{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", clientType=" + clientType +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", subName='" + subName + '\'' +
                ", floorType=" + floorType +
                ", moreLink='" + moreLink + '\'' +
                ", channelId=" + channelId +
                ", sortNum=" + sortNum +
                ", state=" + state +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
