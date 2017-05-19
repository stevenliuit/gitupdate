package com.jcloud.b2c.platform.service.feign;

import java.util.List;

import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;

/**
 * 
 * @author cyy 2015-05-15
 *
 */
@FeignClient(name="${b2c-mall-service}")
//@FeignClient(name = "b2c-mall-service")
public interface MallFunctionClient {
	
	/**
	 * 根据Id 获取功能信息
	 * @author cyy
	 * @date 2017年5月16日
	 * @param tenantId
	 * @param functionId
	 * @return
	 */
	@RequestMapping("/function/queryById")
	public BaseResponseVo<MallFunctionVo> queryById(@RequestParam(value="tenantId")Long tenantId, @RequestParam(value="functionId")Long functionId);
	
	/**
	 * 查询所有的权限
	 * @param tenantId
	 * @return
	 */
	@RequestMapping("/function/querySelective")
	public BaseResponseVo<List<MallFunctionVo>> querySelective(@RequestParam(value="tenantId")Long tenantId);
	
	
	/**
	 * 修改或增加权限信息
	 * 如果 functionId 的值大于0则修改对应权限信息，否则增加
	 * @author cyy
	 * @date 2017年5月16日
	 * @param functionId 权限Id
	 * @param typeId 权限分类Id，如果是增加值必须在 FunctionTypeEnum 枚举之内
	 * @param name 权限名称
	 * @param description 权限描述
	 * @param code 权限编码
	 * @param functionUrl 权限url
	 * @return
	 */
	@RequestMapping("/function/addOrUpdate")
	public BaseResponseVo<Boolean> addOrUpdate(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="functionId")Long functionId,@RequestParam(value="functionTypeId")int typeId,@RequestParam(value="name")String name,@RequestParam(value="description")String description,@RequestParam(value="code")String code,@RequestParam(value="functionUrl")String functionUrl,@RequestParam(value="status")int status);
	
}
