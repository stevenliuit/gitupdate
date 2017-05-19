package com.jcloud.b2c.platform.service.role;

import java.util.List;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;


/**
 * @author cyy
 * @date 2017-05-16
 *
 */
public interface MallFunctionService {

	/**
	 * 根据Id 获取功能信息
	 * @author cyy
	 * @date 2017年5月16日
	 * @param tenantId
	 * @param functionId
	 * @return
	 */
	public BaseResponseVo<MallFunctionVo> queryById(Long tenantId, Long functionId);
	
	
	
	
	/**
	 * 查询出所有的功能权限
	 * @param tenantId
	 * @return
	 */
	public BaseResponseVo<List<MallFunctionVo>> querySelective(Long tenantId);
	
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
	 * @param status 如果小于0则 不修改
	 * @return
	 */
	public BaseResponseVo<Boolean> addOrUpdate(Long tenantId,Long functionId,int typeId,String name,String description,String code,String functionUrl,int status);
	
}
