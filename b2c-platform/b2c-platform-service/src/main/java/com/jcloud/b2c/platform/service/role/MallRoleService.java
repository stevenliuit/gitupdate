package com.jcloud.b2c.platform.service.role;

import java.util.List;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.platform.domain.vo.MallRoleVo;

public interface MallRoleService {
	
	/**
	 * 查询所有的 角色
	 * @param tenantId
	 * @return
	 */
	public BaseResponseVo<List<MallRoleVo>> querySelective(Long tenantId);

}
