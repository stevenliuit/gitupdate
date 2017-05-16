package com.jcloud.b2c.mall.service.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.service.MallFunctionService;

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

    
    @RequestMapping("/queryById")
    public BaseResponseVo<MallFunction> queryById(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="functionId")Long functionId){
    	BaseResponseVo<MallFunction> result = new BaseResponseVo<MallFunction>();
        MallFunction mallFunction = new MallFunction();
        mallFunction.setId(functionId);
        mallFunction.setTenantId(tenantId);
        MallFunction function = mallFunctionService.getByFunctionKey(mallFunction);
        if(function == null){
            result.setFail("-1","operator id is not exist.");
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
        if (list != null){
            result.setIsSuccess(true);
            result.setData(list);
            return result;
        }
        result.setIsSuccess(false);
        return result;
    }

    @RequestMapping(value = "/insertFunction" ,method = RequestMethod.POST)
    public BaseResponseVo<?> insertFunction(@RequestParam(value="trenantId")String trenantId,@RequestParam(value="name")String name,
                                            @RequestParam(value="code") String code,@RequestParam(value="funcUrl") String funcUrl, @RequestParam(value="funcTypeID") String funcTypeID,
                                            @RequestParam(value="description") String description, @RequestParam(value="status") String status) {
    	//  增加业务逻辑
    	BaseResponseVo<List<MallFunction>> result = new BaseResponseVo<List<MallFunction>>();
    	
    	result.setIsSuccess(true);
    	
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
	@RequestMapping("/function/addOrUpdate")
	public BaseResponseVo<Boolean> addOrUpdate(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="functionId")Long functionId,@RequestParam(value="functionTypeId")int typeId,@RequestParam(value="name")String name,@RequestParam(value="description")String description,@RequestParam(value="code")String code,@RequestParam(value="functionUrl")String functionUrl,@RequestParam(value="status")int status){
		
		// 需要增加业务逻辑
		
		BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
		
		return result;
	}
    
	
	
    

}
