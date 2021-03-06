package com.jcloud.b2c.mall.service.client.vo;

import java.io.Serializable;
import java.util.Date;

public class MallFunctionVo implements Serializable {

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
    
    private String funcTypeName;

    /**
     * 功能描述
     */
    private String description;

    /**
     * 功能状态：0：不可用，1：不可用
     */
    private Integer state;
    
    private String stateName;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;
    
    /**
     * 当前角色是否已经拥有该角色
     */
    private boolean checked;

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

	public String getFuncTypeName() {
		return funcTypeName;
	}

	public void setFuncTypeName(String funcTypeName) {
		this.funcTypeName = funcTypeName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
