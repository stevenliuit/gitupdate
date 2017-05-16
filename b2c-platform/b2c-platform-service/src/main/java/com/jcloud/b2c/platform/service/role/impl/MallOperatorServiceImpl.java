package com.jcloud.b2c.platform.service.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.platform.domain.vo.MallFunctionVo;
import com.jcloud.b2c.platform.domain.vo.MallOperatorVo;
import com.jcloud.b2c.platform.service.feign.MallOperatorClient;
import com.jcloud.b2c.platform.service.role.MallOperatorService;

import lombok.val;

@Component
public class MallOperatorServiceImpl implements MallOperatorService{
	
	@Resource
	private MallOperatorClient client;
	
	@Override
	public BaseResponseVo<List<MallOperatorVo>> querySelective(String traneId) {
		return client.querySelective(traneId);
	}

	@Override
	public BaseResponseVo<Boolean> updateOperatorStatus(Long tenantId,Long operatorId,int status){
		return client.updateOperatorStatus(tenantId, operatorId, status);
	}
	
	@Override
	public BaseResponseVo<MallOperatorVo> getByOperatorId(Long traneId,Long operatorId){
		return client.getByOperatorId(traneId, operatorId);
	}


	/*@Override
	public BaseResponseVo<Boolean> addAndNotExist(String tenantId, String userErp, String realName) {
		return client.add(tenantId, userErp, realName);
	}*/

	@Override
	public BaseResponseVo<List<MallFunctionVo>> queryFunction(String tenantId,String userErp){
		return client.queryFunction(tenantId, userErp);
	}
	
	@Override
	public BaseResponseVo<Boolean> updateOperatorRole(Long tenantId,Long operatorId,String roleIds){
		return client.updateOperatorRole(tenantId, operatorId, roleIds);
	}
}
