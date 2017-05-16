/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallCategory.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/16
 */

package com.jcloud.b2c.mall.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 类目
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-16 11:34
 * @lastdate:
 */

public class MallCategory implements Serializable,Comparable {
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
     * 父ID
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否高亮 0、否 1、是
     * com.jcloud.b2c.mall.service.client.enums.YesNoEnum
     */
    private Integer heighlight;

    /**
     * 链接
     */
    private String link;

    /**
     * 图片地址
     */
    private String imgSrc;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHeighlight() {
        return heighlight;
    }

    public void setHeighlight(Integer heighlight) {
        this.heighlight = heighlight;
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
        return "MallCategory{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", clientType=" + clientType +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", heighlight=" + heighlight +
                ", link='" + link + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", channelId=" + channelId +
                ", sortNum=" + sortNum +
                ", state=" + state +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        MallCategory o1=this;
        MallCategory o2= (MallCategory) o;
        if(o1.getSortNum()!=null&&o2.getSortNum()!=null){
            if(o1.getSortNum()-o2.getSortNum()>0){
                return 1;
            }
            if(o1.getSortNum()-o2.getSortNum()<0){
                return -1;
            }
        }
        if(o1.getModified()!=null&&o2.getModified()!=null){
            return o1.getModified().getTime()>o2.getModified().getTime()?-1:1;
        }
        return 0;
    }
}
