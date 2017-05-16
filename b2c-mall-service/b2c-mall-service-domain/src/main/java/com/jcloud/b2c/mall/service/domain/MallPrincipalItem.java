package com.jcloud.b2c.mall.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by issuser on 2017/3/1.
 */
public class MallPrincipalItem implements Serializable{
    /**
     * 主键id
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 主体id,
     * 首页为0
     */
    private Long principalId;

    /**
     *商品id
     */
    private Long itemId;

    /**
     * 客户端类型
     * 1、PC 2、H5 3、Android 4、IOS
     */
    private Integer clientType;

    /**
     * 主体类型
     * 1、类目 2、频道 3、楼层
     */
    private Integer principalType;

    /**
     * 状态
     * -1,删除  1,正常
     */
    private Integer state;

    /**
     * 序号
     */
    private Integer sortNum;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    private Integer pageIndex;

    private Integer pageSize;

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

    public Long getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(Long principalId) {
        this.principalId = principalId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getPrincipalType() {
        return principalType;
    }

    public void setPrincipalType(Integer principalType) {
        this.principalType = principalType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "MallPrincipalItem{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", principalId=" + principalId +
                ", itemId=" + itemId +
                ", clientType=" + clientType +
                ", principalType=" + principalType +
                ", state=" + state +
                ", sortNum=" + sortNum +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
