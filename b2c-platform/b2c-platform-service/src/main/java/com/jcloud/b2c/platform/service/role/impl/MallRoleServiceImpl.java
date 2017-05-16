package com.jcloud.b2c.platform.service.role.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.platform.domain.vo.MallRoleVo;
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
		return client.querySelective(tenantId);
	}

}
