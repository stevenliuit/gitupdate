package com.jcloud.b2c.platform.service.role;

import java.util.List;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import com.jcloud.b2c.mall.service.client.vo.MallOperatorVo;
import com.jcloud.b2c.mall.service.client.vo.MallRoleVo;

public interface MallRoleService {
	
	/**
	 * 查询所有的 角色
	 * @param tenantId
	 * @return
	 */
	public BaseResponseVo<List<MallRoleVo>> querySelective(Long tenantId);
	
	
	/**
	 * 根据 角色Id 查询角色
	 * @param tenantId
	 * @param roleId
	 * @return
	 */
	public BaseResponseVo<MallRoleVo> queryByRoleId(Long tenantId,Long roleId);

	/**
	 * 查询当前角色拥有的 功能。
	 * @author cyy
	 * @date 2017年5月18日
	 * @param roleId 当前角色ID
	 * @return
	 */
	public BaseResponseVo<List<MallFunctionVo>> queryRoleFunction(Long tenantId, Long roleId);
	
	/**
	 * 查询 拥有该角色的所有操作员
	 */
	public BaseResponseVo<List<MallOperatorVo>> queryRoleOperator(Long tenantId, Long roleId);

	/**
	 * 增加角色
	 * @param tenantId
	 * @param roleName 角色名称
	 * @param description 角色描述
	 * @return
	 */
	public BaseResponseVo<Boolean> insertRole(Long tenantId,String roleName,String description);

	/**
	 * 根据 权限角色  修改角色信息
	 * 角色的启用，禁用功能也使用该 api
	 * @param tenantId 租户Id 必填
	 * @param roleId 角色Id 必填
	 * @param roleName 角色名称，选填 为空则不跟新该属性值
	 * @param description 角色描述，选填，为空不更新该属性值
	 * @param status 角色状态，选填,如果 为空，或小于0 则不跟新 角色状态，
	 * @return
	 */
	public BaseResponseVo<Boolean> updateRole(Long tenantId,Long roleId,String roleName,String description, int status);

	
	/**
	 * 角色赋权
	 * @param tenantId
	 * @param roleId  角色ID 
	 * @param functionIds 权限ID串，多个权限ID之间使用英文逗号（,）分割
	 * @return
	 */
	public BaseResponseVo<Boolean> updateRoleFunction(Long tenantId,Long roleId,String functionIds);
	
	
}
