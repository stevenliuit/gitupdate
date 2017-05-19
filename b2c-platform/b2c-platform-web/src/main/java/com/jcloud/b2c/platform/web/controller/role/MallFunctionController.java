package com.jcloud.b2c.platform.web.controller.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.mall.service.client.enums.FunctionTypeEnum;
import com.jcloud.b2c.platform.service.role.MallFunctionService;

/**
 * 
 * @author cyy 
 * @date 2017-05-16
 *
 */
@Controller
@RequestMapping("/function")
public class MallFunctionController {
	
	@Resource
	private MallFunctionService functionService;

	/**
	 * 跳转到 功能列表页面
	 * @author cyy
	 * @date 2017年5月16日
	 * @return
	 */
	@RequestMapping("/toList")
	public ModelAndView toList(){
		Long tenantId = ControllerContext.getTenantId();
		ModelAndView view = new ModelAndView("role/functionList");
		BaseResponseVo<List<MallFunctionVo>> funcData = functionService.querySelective(tenantId);
		if(CollectionUtils.isNotEmpty(funcData.getData())){
			for(MallFunctionVo item : funcData.getData()){
				FunctionTypeEnum funcType = FunctionTypeEnum.getFunctionType(item.getFuncTypeID().intValue());
				item.setFuncTypeName(funcType == null ? "" : funcType.getName());
				item.setStateName(item.getState() == YesNoEnum.YES.getValue() ? YesNoEnum.YES.getName() : YesNoEnum.NO.getName());
			}
		}
		view.addObject("funcTypes",FunctionTypeEnum.values());
		view.addObject("list",funcData.getData());
		return view;
	}
	/**
	 * 增加或者修改权限
	 * @author cyy
	 * @date 2017年5月16日
	 * @param functionId 权限Id 如果值表示修改该权限信息，否则增加权限信息
	 * @param typeId 如果增加则必填，并且是大于0的值，修改值小于0 表示不修改
	 * @param name 权限名称
	 * @param description 权限描述
	 * @param code 权限编码
	 * @param functionUrl 权限url
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	public @ResponseBody Map<String,String> addOrUpdate(@RequestParam("functionId")Long functionId,@RequestParam("typeId")int typeId,@RequestParam("name")String name,@RequestParam(value="description")String description,
			@RequestParam(value="code")String code,@RequestParam("functionUrl")String functionUrl){
		Map<String, String> result = new HashMap<String,String>();
		result.put("code","0");
		int status = -1;
		if(functionId == null || functionId <= 0){
			// 增加权限校验
			if(!FunctionTypeEnum.include(typeId)){
				return setError(result,"权限分类错误");
			}
			if(StringUtils.isEmpty(name)){
				return setError(result,"权限名称不能为空");
			}
			if(StringUtils.isEmpty(code)){
				return setError(result,"权限编码不能为空");
			}
			if(StringUtils.isEmpty(functionUrl)){
				return setError(result, "权限地址不能为空");
			}
			status = YesNoEnum.YES.getValue();
		}
		Long tenantId = ControllerContext.getTenantId();
		BaseResponseVo<Boolean> updateResult = functionService.addOrUpdate(tenantId,functionId, typeId, name, description, code, functionUrl,status);
		if(!updateResult.isSuccess() || updateResult.getData() == null || !updateResult.getData()){
			return setError(result, updateResult.getMessage());
		}
		return setSuccess(result);
	}
	
	/**
	 * 启用或停用功能权限
	 * 如果当前状态为启用，则停用，如果当前状态为停用，则启用
	 * @author cyy
	 * @date 2017年5月16日
	 * @param functionId
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public @ResponseBody Map<String,String> updateStatus(@RequestParam(value="functionId")Long functionId){
		Map<String, String> result = new HashMap<String,String>();
		if(functionId == null || functionId <= 0){
			return setError(result,"功能不存在");
		}
		Long tenantId = ControllerContext.getTenantId();
		BaseResponseVo<MallFunctionVo> funData = functionService.queryById(tenantId, functionId);
		if(null == funData.getData()){
			return setError(result,"功能不存在");
		}
		int status = funData.getData().getState() == YesNoEnum.YES.getValue() ? YesNoEnum.NO.getValue() : YesNoEnum.YES.getValue();
		
		BaseResponseVo<Boolean> updateResult = functionService.addOrUpdate(tenantId,funData.getData().getId(),-1,"","","","", status);
		if(!updateResult.isSuccess() || updateResult.getData() == null || !updateResult.getData()){
			return setError(result,updateResult.getMessage());
		}
		
		
		
		return setSuccess(result);
	}
	
	
	private Map<String, String> setError(Map<String, String> result,String errorMsg){
		result.put("code","-1");
		result.put("msg", errorMsg);
		return result;
	}
	private Map<String,String> setSuccess(Map<String,String> result){
		result.put("code","0");
		result.put("msg","ok");
		return result;
	}
	
}
