package com.jcloud.b2c.mall.service.domain;

import java.util.Date;
import java.util.List;

/**
 * @Method:
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
public class MallRole {

    /**
     * '用户角色表ID，主键
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 角色名称（10字以内）
     */
    private String name;

    /**
     * 角色描述（200字以内）
     */
    private String description;

    /**
     * 角色状态：0：不可用，1：可用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 用户关联
     */
    private List<MallOperator> operatorList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public List<MallOperator> getOperatorList() {
        return operatorList;
    }

    public void setOperatorList(List<MallOperator> operatorList) {
        this.operatorList = operatorList;
    }

    @Override
    public String toString() {
        return "MallRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
