package com.jcloud.b2c.platform.service.feign;

import java.util.List;

import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import com.jcloud.b2c.mall.service.client.vo.MallOperatorVo;
import com.jcloud.b2c.mall.service.client.vo.MallRoleVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;

@FeignClient(name="${b2c-mall-service}")
//@FeignClient(name = "b2c-mall-service")
public interface MallRoleClient {
	
	@RequestMapping("/role/querySelective")
	public BaseResponseVo<List<MallRoleVo>> querySelective(@RequestParam(value="tenantId")Long tenantId);
	
	/**
	 * 根据 角色Id 查询角色
	 * @param tenantId
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/role/queryByRoleId")
	public BaseResponseVo<MallRoleVo> queryByRoleId(@RequestParam(value="tenantId")Long tenantId, @RequestParam(value="roleId")Long roleId);
	
	/**
	 * 查询当前角色拥有的 功能。
	 * @author cyy
	 * @date 2017年5月18日
	 * @param roleId 当前角色ID
	 * @return
	 */
	@RequestMapping("/role/getByOrleFunction")
	public BaseResponseVo<List<MallFunctionVo>> getByOrleFunction(@RequestParam(value="tenantId")Long tenantId, @RequestParam(value="roleId")Long roleId);
	
	
	/**
	 * 查询 拥有该角色的所有操作员
	 */
	@RequestMapping("/role/getByRoleAllOperator")
	public BaseResponseVo<List<MallOperatorVo>> queryRoleOperator(@RequestParam(value="tenantId")Long tenantId, @RequestParam(value="roleId")Long roleId);
	/**
	 * 修改 角色信息
	 * @param tenantId 
	 * @param roleId 需要修改的角色Id，不可为空，
	 * @param roleName 角色名称，可为空，为则不修改该项
	 * @param description 角色描述，可为空，为则不修改该项
	 * @param status 角色状态 ，可为空，为则不修改该项
	 * @return
	 */
	@RequestMapping("/role/updateRole")
	 public BaseResponseVo<Boolean> updateRole(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="roleId")Long roleId,@RequestParam(value="roleName")String roleName,@RequestParam(value="description") String description,@RequestParam(value="status") int status);

	/**
	 * 增加 角色信息
	 * @param tenantId
	 * @param roleName 角色名称，可为空，为则不修改该项
	 * @param description 角色描述，可为空，为则不修改该项
	 * @return
	 */
	@RequestMapping("/role/insertRole")
	public BaseResponseVo<Boolean> insertRole(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="roleName")String roleName,@RequestParam(value="description")String description);
	
	/**
	 * 角色赋权
	 * @param tenantId
	 * @param roleId  角色ID 
	 * @param functionIds 权限ID串，多个权限ID之间使用英文逗号（,）分割
	 * @return
	 */
	@RequestMapping("/role/updateRoleFunction")
	public BaseResponseVo<Boolean> updateRoleFunction(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="roleId")Long roleId,@RequestParam(value="functionIds")String functionIds);

	
}
