package com.jcloud.b2c.mall.service.client.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MallOperatorVo implements Serializable {
	
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
     * 状态：0、不可用，1、可用
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
	 * 角色
	 */
	private List<MallRoleVo> roleList;
	
	/**
	 * 所有角色
	 * @author cyy
	 * @date 2017年5月19日
	 * @return
	 */
	private List<MallFunctionTypeVo> functionList;
	

	public List<MallRoleVo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<MallRoleVo> roleList) {
		this.roleList = roleList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserErp() {
		return userErp;
	}

	public void setUserErp(String userErp) {
		this.userErp = userErp;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public List<MallFunctionTypeVo> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<MallFunctionTypeVo> functionList) {
		this.functionList = functionList;
	}
	
}
