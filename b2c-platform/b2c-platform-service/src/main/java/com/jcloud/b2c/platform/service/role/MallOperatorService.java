package com.jcloud.b2c.platform.service.role;

import java.util.List;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import com.jcloud.b2c.mall.service.client.vo.MallOperatorVo;
import com.jcloud.b2c.mall.service.client.vo.MallRoleVo;

public interface MallOperatorService {

	public BaseResponseVo<Boolean> insertOperator(Long trenantId,String userErp,String realName) ;
	
	public BaseResponseVo<MallOperatorVo> getByOperatorId(Long traneId, Long operatorId);
	
	public BaseResponseVo<MallOperatorVo> queryOperatorByErp(Long tenantId,String userErp);
	
	public BaseResponseVo<List<MallOperatorVo>> querySelective(String traneId,Integer page,Integer pageSize);
	
	public BaseResponseVo<List<MallFunctionVo>> queryFunction(Long tenantId, String userErp, int page, int pageSize);
	
	public BaseResponseVo<List<MallRoleVo>> queryOperatorRole(Long operatorId);
	
	public BaseResponseVo<Boolean> updateOperatorStatus(Long tenantId,Long operatorId,int status);
	
	public BaseResponseVo<Boolean> updateOperator(Long tenantId,Long operatorId,String realName,String userErp);
	
	public BaseResponseVo<Boolean> updateOperatorRole(Long tenantId,Long operatorId,String roleIds);

	public BaseResponseVo<Boolean> addOperatorRole(Long operatorId, Long trenantId,String roleIds);
}
