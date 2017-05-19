package com.jcloud.b2c.mall.service.domain;

import java.util.Date;
import java.util.List;

/**
 * @Method:
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
public class MallFunction {

    /**
     * 功能权限表ID，主键
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 功能名称
     */
    private String name;

    /**
     * 权限代码,用于在 代码中进行具体的权限比较
     */
    private String code;

    /**
     * 功能对应的 URL
     */
    private String funcUrl;

    /**
     * 权限分类
     */
    private Long funcTypeID;

    /**
     * 功能描述
     */
    private String description;

    /**
     * 功能状态：0：不可用，1：可用
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
     * 关联角色
     */
    private List<MallRole> roleList;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFuncUrl() {
        return funcUrl;
    }

    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    public Long getFuncTypeID() {
        return funcTypeID;
    }

    public void setFuncTypeID(Long funcTypeID) {
        this.funcTypeID = funcTypeID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public List<MallRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<MallRole> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "MallFunction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", funcUrl='" + funcUrl + '\'' +
                ", funcTypeID=" + funcTypeID +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
