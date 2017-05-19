package com.jcloud.b2c.platform.service.role.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import com.jcloud.b2c.mall.service.client.vo.MallOperatorVo;
import com.jcloud.b2c.mall.service.client.vo.MallRoleVo;
import org.springframework.stereotype.Component;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.platform.service.feign.MallRoleClient;
import com.jcloud.b2c.platform.service.role.MallRoleService;

@Component
public class MallRoleServiceImpl implements MallRoleService{
	
	@Resource
	private MallRoleClient client;
	
	/**
	 * 查询所有的 角色
	 * @param tenantId
	 * @return
	 */
	@Override
	public BaseResponseVo<List<MallRoleVo>> querySelective(Long tenantId){
		BaseResponseVo<List<MallRoleVo>> result = new BaseResponseVo<>();
		if(null==tenantId){
			result = new BaseResponseVo<>(false);
			return result;
		}
		return client.querySelective(tenantId);
	}
	
	/**
	 * 根据 角色Id 查询角色
	 * @param tenantId
	 * @param roleId
	 * @return
	 */
	public BaseResponseVo<MallRoleVo> queryByRoleId(Long tenantId,Long roleId){
		return client.queryByRoleId(tenantId, roleId);
	}
	
	/**
	 * 查询 拥有该角色的所有操作员
	 */
	public BaseResponseVo<List<MallOperatorVo>> queryRoleOperator(Long tenantId, Long roleId){
		return client.queryRoleOperator(tenantId,roleId);
	}
	
	/**
	 * 修改 角色信息
	 * @param tenantId 
	 * @param roleId 需要修改的角色Id，不可为空，
	 * @param roleName 角色名称，可为空，为则不修改该项
	 * @param description 角色描述，可为空，为则不修改该项
	 * @param status 角色状态 ，小于 0  ，为则不修改该项
	 * @return
	 */
	public BaseResponseVo<Boolean> updateRole(Long tenantId,Long roleId,String roleName,String description,int status){
		return client.updateRole(tenantId, roleId, roleName, description,status);
	}
	
	/**
	 * 增加 角色信息
	 * @param tenantId
	 * @param roleName 角色名称，可为空，为则不修改该项
	 * @param description 角色描述，可为空，为则不修改该项
	 * @return
	 */
	public BaseResponseVo<Boolean> insertRole(Long tenantId,String roleName,String description){
		return client.insertRole(tenantId,roleName, description);
	}

	/**
	 * 角色赋权
	 * @param tenantId
	 * @param roleId  角色ID 
	 * @param functionIds 权限ID串，多个权限ID之间使用英文逗号（,）分割
	 * @return
	 */
	@Override
	public BaseResponseVo<Boolean> updateRoleFunction(Long tenantId, Long roleId, String functionIds) {
		return client.updateRoleFunction(tenantId, roleId, functionIds);
	}

	/**
	 * 查询当前角色拥有的 功能。
	 * @author cyy
	 * @date 2017年5月18日
	 * @param roleId 当前角色ID
	 * @return
	 */
	public BaseResponseVo<List<MallFunctionVo>> queryRoleFunction(Long tenantId, Long roleId){
		return client.getByOrleFunction(tenantId,roleId);
	}
	/**
	 * 增加角色
	 * @param tenantId
	 * @param roleName 角色名称
	 * @param description 角色描述
	 * @return
	 */
	/*@Override
	public BaseResponseVo<Boolean> insertRole(Long tenantId, String roleName, String description) {
		return client.insertRole(tenantId,roleName,description);
	}*/

	/**
	 * 根据 权限角色  修改角色信息
	 * 角色的启用，禁用功能也使用该 api
	 * @param tenantId 租户Id 必填
	 * @param roleId 角色Id 必填
	 * @param roleName 角色名称，选填 为空则不跟新该属性值
	 * @param description 角色描述，选填，为空不更新该属性值
	 * @param state 角色状态，选填,如果 为空，或小于0 则不跟新 角色状态，
	 * @return
	 */
	/*@Override
	public BaseResponseVo<Boolean> updateRole(Long tenantId, Long roleId, String roleName, String description, int
			state) {
		BaseResponseVo<Boolean> result = new BaseResponseVo<>();
		if(null==tenantId||null==roleId){
			result = new BaseResponseVo<>(false);
			return result;
		}
		return client.updateRole(tenantId,roleId,roleName,description,state);
	}*/


}
