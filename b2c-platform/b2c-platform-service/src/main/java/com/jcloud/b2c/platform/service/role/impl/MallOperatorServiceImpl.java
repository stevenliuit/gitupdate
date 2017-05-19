package com.jcloud.b2c.platform.service.role.impl;

import java.util.List;

import javax.annotation.Resource;

import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import com.jcloud.b2c.mall.service.client.vo.MallOperatorVo;
import com.jcloud.b2c.mall.service.client.vo.MallRoleVo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.platform.service.feign.MallOperatorClient;
import com.jcloud.b2c.platform.service.role.MallOperatorService;

@Component
public class MallOperatorServiceImpl implements MallOperatorService{
	
	@Resource
	private MallOperatorClient client;
	
	@Override
	public BaseResponseVo<List<MallOperatorVo>> querySelective(String traneId,Integer page, Integer pageSize) {
		return client.querySelective(traneId/*,page,pageSize*/);
	}

	@Override
	public BaseResponseVo<Boolean> updateOperatorStatus(Long tenantId,Long operatorId,int status){
		return client.updateOperatorStatus(tenantId, operatorId, status);
	}
	
	@Override
	public BaseResponseVo<Boolean> updateOperator(Long tenantId,Long operatorId,String realName,String userErp){
		return client.updateOperator(tenantId, operatorId, realName, userErp);
	}
	

	@Override
	public BaseResponseVo<Boolean> insertOperator(Long trenantIdStr, String userErp,String realName) {
		return client.insertOperator(trenantIdStr,userErp,realName);
	}

	@Override
	public BaseResponseVo<MallOperatorVo> queryOperatorByErp(Long tenantId,String userErp){
		return client.queryOperatorByErp(tenantId, userErp);
	}
	
	@Override
	public BaseResponseVo<MallOperatorVo> getByOperatorId(Long traneId, Long operatorId){
		return client.getByOperatorId(traneId, operatorId);
	}

	@Override
	public BaseResponseVo<List<MallFunctionVo>> queryFunction(Long tenantId, String userErp, int page, int pageSize){
		return client.queryFunction(tenantId, userErp,page,pageSize);
	}
	@Override
	public BaseResponseVo<List<MallRoleVo>> queryOperatorRole(Long operatorId){
		return client.queryOperatorRole(operatorId);
	}
	
	@Override
	public BaseResponseVo<Boolean> updateOperatorRole(Long tenantId,Long operatorId,String roleIds){
		return client.updateOperatorRole(tenantId, operatorId, roleIds);
	}

	@Override
	public BaseResponseVo<Boolean> addOperatorRole(@RequestParam(value = "operatorId") Long operatorId, @RequestParam
			(value = "trenantId") Long trenantId, @RequestParam(value = "roleIds") String roleIds) {
		return client.addOperatorRole(operatorId,trenantId,roleIds);
	}
}
