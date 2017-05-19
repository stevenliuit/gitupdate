package com.jcloud.b2c.platform.web.controller.role;

import java.util.ArrayList;
/**
 * Created by lenovo on 2017/5/17.
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.jcloud.b2c.mall.service.client.vo.MallFunctionTypeVo;
import com.jcloud.b2c.mall.service.client.vo.MallFunctionVo;
import com.jcloud.b2c.mall.service.client.vo.MallRoleVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.FunctionTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.platform.service.feign.CacheFeignClient;
import com.jcloud.b2c.platform.service.role.MallFunctionService;
import com.jcloud.b2c.platform.service.role.MallRoleService;
import com.jcloud.b2c.platform.util.RoleXmlParser;


@Controller
@RequestMapping("/role")
public class MallRoleController {
    Logger logger = LoggerFactory.getLogger(MallRoleController.class);
	@Resource
	private MallRoleService roleService;
	@Resource
	private MallFunctionService functionService;
	@Resource
	private CacheFeignClient cacheFeignClient;
	@Resource
	private RoleXmlParser roleXmlParser;
	 /**
	 * 跳转到角色列表页面
	 */
	@RequestMapping("/toList")
	public ModelAndView toList(){
		ModelAndView view = new ModelAndView("role/roleList");
		Long tenantId = ControllerContext.getTenantId();
		BaseResponseVo<List<MallRoleVo>> roleDate = roleService.querySelective(tenantId);
		if(roleDate.getData() != null){
			for(MallRoleVo item : roleDate.getData()){
				item.setStateName(item.getState() == YesNoEnum.YES.getValue() ? YesNoEnum.YES.getName() : YesNoEnum.NO.getName());
			}
			view.addObject("list",roleDate.getData());
		}
		return view;
	}
	/**
	 * 跳转到 角色赋权限页面
	 * @param roleId 角色ID
	 * @return
	 */
	@RequestMapping("/toUpdateFunction")
	public ModelAndView toUpdateFunction(@RequestParam(value="roleId")Long roleId){
		ModelAndView view = new ModelAndView("role/updateRoleFunction");
		Long tenantId = ControllerContext.getTenantId();
		BaseResponseVo<List<MallFunctionVo>> funcData = functionService.querySelective(tenantId);
		if(!funcData.isSuccess() || CollectionUtils.isEmpty(funcData.getData())){
			view.setViewName("error");
			view.addObject("message","没有任何权限");
			return view;
		}
		
		// 查询当前前角色拥有的  权限
		BaseResponseVo<List<MallFunctionVo>> roleFunc = roleService.queryRoleFunction(tenantId,roleId);
		
		Map<Integer,List<MallFunctionVo>> data = new HashMap<Integer, List<MallFunctionVo>>();
		for(MallFunctionVo item : funcData.getData()){
			if(!data.containsKey(item.getFuncTypeID().intValue())){
				data.put(item.getFuncTypeID().intValue(),new ArrayList<MallFunctionVo>());
			}
			boolean ishave = false;
			if(roleFunc.getData() != null && roleFunc.getData().size() > 0){
				for(MallFunctionVo fitem : roleFunc.getData()){
					if(fitem.getId() == item.getId().intValue()){
						ishave = true;
					}
				}
			}
			item.setChecked(ishave);
			data.get(item.getFuncTypeID().intValue()).add(item);
		}
		
		
		List<MallFunctionTypeVo> result = new ArrayList<MallFunctionTypeVo>();
		for(Map.Entry<Integer,List<MallFunctionVo>> item : data.entrySet()){
			MallFunctionTypeVo tvo = new MallFunctionTypeVo();
			tvo.setFunctionTypeId(item.getKey());
			FunctionTypeEnum funcType = FunctionTypeEnum.getFunctionType(item.getKey());
			tvo.setFunctionTypeName(funcType == null ? "" : funcType.getName());
			tvo.setIcon(funcType == null ? "" : funcType.getIcon());
			tvo.setFunctions(item.getValue());
			result.add(tvo);
		}
		view.addObject("list",result);
		view.addObject("roleId",roleId);
		return view;
	}
	
	/**
	 * 修改角色状态
	 * 如果角色当前状态为可用，则 禁用，
	 * 如果角色当前状态为禁用，则启用
	 * @param roleId 角色Id
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public @ResponseBody Map<String,String> updateStatus(@RequestParam("roleId")Long roleId){
		
		Long tenantId = ControllerContext.getTenantId();
		BaseResponseVo<MallRoleVo> roleData = roleService.queryByRoleId(tenantId, roleId);
		Map<String,String> result = new HashMap<String,String>();
		if(roleData.getData() == null){
			return setError(result, "角色不存在");
		}
		
		int status = roleData.getData().getState() == YesNoEnum.YES.getValue() ? YesNoEnum.NO.getValue() : YesNoEnum.YES.getValue();
		
		BaseResponseVo<Boolean> updateResult = roleService.updateRole(tenantId, roleId,"","", status);
		
		if(!updateResult.isSuccess() || updateResult.getData() == null || !updateResult.getData()){
			return setError(result, "修改失败");
		}
		
		roleXmlParser.clearCache(tenantId, roleId);
		
		return setSuccess(result);
	}
	
	/**
	 * 修改角色 信息
	 */
	@RequestMapping("/addOrUpdateRole")
	public @ResponseBody Map<String,String> addOrUpdateRole(@RequestParam(value="roleId")Long roleId,@RequestParam(value="roleName")String roleName,@RequestParam(value="descirption")String description){
		Long tenantId = ControllerContext.getTenantId();
		Map<String,String> result = new HashMap<String,String>();
		if(StringUtils.isBlank(roleName)){
			return setError(result, "角色名称不能为空");
		}
		BaseResponseVo<Boolean> updateResult = null;
		if(roleId == null || roleId <= 0){
			updateResult = roleService.insertRole(tenantId,roleName, description);
		}else{
			updateResult = roleService.updateRole(tenantId, roleId, roleName, description,-1);
		}
		if(updateResult.getData() == null || !updateResult.getData()){
			return setError(result,"修改错误");
		}
		return setSuccess(result);
	}
	
	/**
	 * 角色赋权限
	 * @param roleId 角色ID
	 * @param functionIds 权限ID串，多个权限ID之间用英文逗号（,）分割。
	 */
	@RequestMapping("/updateRoleFunction")
	public @ResponseBody Map<String,String> updateRoleFunction(@RequestParam(value="roleId")Long roleId,@RequestParam(value="functionIds")String functionIds){
		Map<String,String> result = new HashMap<String,String>();
		if(StringUtils.isBlank(functionIds)){
			return setError(result,"权限Id串不可为空");
		}
		Long tenantId = ControllerContext.getTenantId();
		BaseResponseVo<Boolean> updateResult = roleService.updateRoleFunction(tenantId, roleId, functionIds);
		if(!updateResult.isSuccess() || updateResult.getData() == null || !updateResult.getData()){
			return setError(result,updateResult.getMessage());
		}
		
		roleXmlParser.clearCache(tenantId, roleId);
		
		return setSuccess(result);
	}
	
	
	private Map<String,String> setError(Map<String,String> result,String msg){
		result.put("code","-1");
		result.put("msg",msg);
		return result;
	}
	private Map<String,String> setSuccess(Map<String,String> result){
		result.put("code","0");
		result.put("msg","ok");
		return result;
	}
}
