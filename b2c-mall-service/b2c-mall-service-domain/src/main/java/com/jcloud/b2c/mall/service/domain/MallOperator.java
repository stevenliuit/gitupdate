package com.jcloud.b2c.mall.service.domain;

import java.util.Date;
import java.util.List;

/**
 * @Method:
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
public class MallOperator {
    /**
     * 操作员ID，主键
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 京东用户ID
     */
    private String userErp;

    /**
     * 用户的真实姓名
     */
    private String realName;

    /**
     * 状态：0、不可用，1、可禁用
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
     * 修改时间
     */
    private List<MallRole> roleList;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserErp(String userErp) {
        this.userErp = userErp;
    }

    public String getUserErp() {
        return userErp;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getModified() {
        return modified;
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
        return "MallOperator{" +
                "id=" + id +
                ", userErp='" + userErp + '\'' +
                ", realName='" + realName + '\'' +
                ", state=" + state +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
