package com.jcloud.b2c.platform.service.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.platform.domain.vo.MallRoleVo;

@FeignClient(name="${b2c-mall-service}")
public interface MallRoleClient {
	
	@RequestMapping("/role/querySelective")
	public BaseResponseVo<List<MallRoleVo>> querySelective(@RequestParam(value="tenantId")Long tenantId);

}
