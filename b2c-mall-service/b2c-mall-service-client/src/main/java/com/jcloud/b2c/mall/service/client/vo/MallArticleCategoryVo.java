package com.jcloud.b2c.mall.service.client.vo;

import java.util.Date;

/**
 * 文章类目主体
 */
public class MallArticleCategoryVo {
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
    private Byte clientType;
    /**
     * 父类目id
     */
    private Long parentId;
    /**
     * 类目名称
     */
    private String name;
    /**
     * 序号
     */
    private Integer sortNum;
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

    public Byte getClientType() {
        return clientType;
    }

    public void setClientType(Byte clientType) {
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

    @Override
    public String toString() {
        return "MallArticleCategoryVo{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", clientType=" + clientType +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", sortNum=" + sortNum +
                ", state=" + state +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}