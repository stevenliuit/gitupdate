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
public interface MallOperatorClient {
	
	/**
	 * 增加用户
	 */
	@RequestMapping("/operator/insertOperator")
	BaseResponseVo<Boolean> insertOperator(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="userErp")String userErp,
												  @RequestParam(value="realName") String realName) ;
	/**
	 * 更新用户的状态
	 * @param tenantId
	 * @param operatorId 用户erp
	 * @param status 更新的状态
	 * @return
	 */
	@RequestMapping("/operator/updateOperatorStatus")
	BaseResponseVo<Boolean> updateOperatorStatus(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="operatorId")Long operatorId,@RequestParam(value="status")int status);
	

	@RequestMapping("/operator/updateOperator")
	BaseResponseVo<Boolean> updateOperator(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="operatorId")Long operatorId,@RequestParam(value="realName")String realName,@RequestParam(value="userErp")String userErp);
	
	
	/**
	 * 根据操作员ID查询用户
	 * @param traneId
	 * @param operatorId 操作员ID
	 * @return
	 */
	@RequestMapping("/operator/getByOperatorId")
	BaseResponseVo<MallOperatorVo> getByOperatorId(@RequestParam(value="tenantId")Long traneId, @RequestParam(value="operatorId")Long operatorId);
	
	@RequestMapping("/operator/queryOperatorByErp")
	BaseResponseVo<MallOperatorVo> queryOperatorByErp(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="userErp")String userErp);
	
	/**
	 * 查询全部用户
	 */
	@RequestMapping("/operator/querySelective")
	BaseResponseVo<List<MallOperatorVo>> querySelective(@RequestParam(value="tenantId") String tenantId/*,@RequestParam
			(value="page") Integer page,
														@RequestParam(value="pageSize") Integer pageSize*/);
	
	@RequestMapping("/operator/queryOperatorRole")
	BaseResponseVo<List<MallRoleVo>> queryOperatorRole(@RequestParam(value="operatorId")Long operatorId);
	
	/**
	 * 查询用户的权限
	 * @param tenantId
	 * @param userErp
	 * @return
	 */
	@RequestMapping("/operator/queryFunction")
	BaseResponseVo<List<MallFunctionVo>> queryFunction(@RequestParam(value="tenantId") Long tenantId, @RequestParam(value="userErp")String userErp, @RequestParam(value="page")int page, @RequestParam(value="pageSize")int pageSize);
	
	/**
	 * 修改操作员 的角色
	 * @param tenantId
	 * @param operatorId
	 * @param roleIds
	 */
	@RequestMapping("/operator/updateOperatorRole")
	BaseResponseVo<Boolean> updateOperatorRole(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="operatorId")Long operatorId,@RequestParam(value="roleIds")String roleIds);

	@RequestMapping("/operator/addOperatorRole")
	public BaseResponseVo<Boolean> addOperatorRole(@RequestParam(value="operatorId")Long operatorId, @RequestParam(value="trenantId")Long trenantId,
												   @RequestParam(value="roleIds")String roleIds);

}
