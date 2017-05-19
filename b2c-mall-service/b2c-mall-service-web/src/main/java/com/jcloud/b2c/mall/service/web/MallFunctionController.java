package com.jcloud.b2c.mall.service.web;


import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.enums.FunctionTypeEnum;
import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.service.MallFunctionService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Method: 权限管理
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
@RestController
@RequestMapping("/function")
public class MallFunctionController {

    @Autowired
    private MallFunctionService mallFunctionService;

    /**
     * 以ID查询权限
     * @param tenantId
     * @param functionId
     * @return
     */
    @RequestMapping("/queryById")
    public BaseResponseVo<MallFunction> queryById(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="functionId")Long functionId){
    	BaseResponseVo<MallFunction> result = new BaseResponseVo<MallFunction>();
        MallFunction mallFunction = new MallFunction();
        mallFunction.setId(functionId);
        mallFunction.setTenantId(tenantId);
        MallFunction function = mallFunctionService.getByFunctionKey(mallFunction);
        if(function == null){
            result.setFail("-1","没有该权限");
            result.setIsSuccess(false);
            return result;
        }
        result.setIsSuccess(true);
        result.setData(function);
        return result;
    }

    /**
     * 查询所有权限
     * @author cyy
     * @date 2017年5月16日
     * @param tenantId
     * @return
     */
    @RequestMapping(value = "/querySelective")
    public BaseResponseVo<List<MallFunction>> querySelective(@RequestParam(value="tenantId")Long tenantId) {
        BaseResponseVo<List<MallFunction>> result = new BaseResponseVo<List<MallFunction>>();
        MallFunction mallFunction = new MallFunction();
        mallFunction.setTenantId(tenantId);
        List<MallFunction> list = mallFunctionService.querySelective(mallFunction);
        if (list == null  || list.size() == 0){
            result.setIsSuccess(false);
            return result;
        }
        result.setIsSuccess(true);
        result.setData(list);
        return result;
    }

    
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
	@RequestMapping("/addOrUpdate" )
	public BaseResponseVo<Boolean> addOrUpdate(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="functionId")Long functionId,
                                               @RequestParam(value="functionTypeId")Long typeId,@RequestParam(value="name")String name,
                                               @RequestParam(value="description")String description,@RequestParam(value="code")String code,
                                               @RequestParam(value="functionUrl")String functionUrl,@RequestParam(value="status")int status){

		BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
        MallFunction mallFunction = new MallFunction();
        mallFunction.setId(functionId);
        mallFunction.setTenantId(tenantId);
        if(StringUtils.isNotBlank(code)){
        	mallFunction.setCode(code);
        }
        if(StringUtils.isNotBlank(description)){
        	mallFunction.setDescription(description);
        }
        if(StringUtils.isNotBlank(name)){
        	mallFunction.setName(name);
        }
        if(StringUtils.isNotBlank(functionUrl)){
        	mallFunction.setFuncUrl(functionUrl);
        }
        if(status >= 0){
        	mallFunction.setState(status);
        }
        if(FunctionTypeEnum.include(typeId.intValue())){
        	mallFunction.setFuncTypeID(typeId);
        }
        mallFunction.setModified(new Date());
        
        boolean flag = false;
        if(functionId <= 0 ){
        	mallFunction.setCreated(new Date());
            flag = mallFunctionService.insertFunction(mallFunction);
        }else{
            flag = mallFunctionService.updateByFunctionKey(mallFunction);
        }
        if (!flag){
            result.setFail("-1","add or update error");
            return result;
        }
        result.setIsSuccess(true);
        result.setData(Boolean.TRUE);
        return result;
	}
}
