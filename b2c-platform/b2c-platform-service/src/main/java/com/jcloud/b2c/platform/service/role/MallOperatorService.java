package com.jcloud.b2c.platform.service.role;

import java.util.List;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.platform.domain.vo.MallFunctionVo;
import com.jcloud.b2c.platform.domain.vo.MallOperatorVo;

public interface MallOperatorService {
	
	// public BaseResponseVo<Boolean> addAndNotExist(String tenantId,String userErp,String realName);
	
	public BaseResponseVo<MallOperatorVo> getByOperatorId(Long traneId,Long operatorId);
	
	public BaseResponseVo<List<MallOperatorVo>> querySelective(String traneId);
	
	public BaseResponseVo<List<MallFunctionVo>> queryFunction(String tenantId,String userErp);
	
	public BaseResponseVo<Boolean> updateOperatorStatus(Long tenantId,Long operatorId,int status);
	
	public BaseResponseVo<Boolean> updateOperatorRole(Long tenantId,Long operatorId,String roleIds);
	
}
