package com.jcloud.b2c.platform.web.controller.role;

import java.util.*;

import javax.annotation.Resource;

import com.jcloud.b2c.mall.service.client.vo.MallOperatorVo;
import com.jcloud.b2c.mall.service.client.vo.MallRoleVo;
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
import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.platform.service.role.MallOperatorService;
import com.jcloud.b2c.platform.service.role.MallRoleService;
import com.jcloud.b2c.platform.util.RoleXmlParser;

@Controller
@RequestMapping("/operator")
public class MallOperatorController {

	Logger logger = LoggerFactory.getLogger(MallOperatorController.class);

	@Resource
	private MallOperatorService operatorService;
	@Resource
	private MallRoleService roleService;
	@Resource
	private RoleXmlParser roleXmlParser;
	
	/**
	 * 跳转到操作员列表页面
	 * @return
	 */
	@RequestMapping("/toList")
	public ModelAndView toOperatorList(Integer page, Integer pageSize){
		ModelAndView view = new ModelAndView("role/operatorList");
		Long traneId = ControllerContext.getTenantId();
		/*if(page==null||page<=0){
			page = 1;
		}
		if(pageSize==null || pageSize<=0){
			pageSize=10;
		}*/
		BaseResponseVo<List<MallOperatorVo>>  operators = operatorService.querySelective(traneId.toString(),page,pageSize);
		if(operators.getData() != null && operators.getData().size() > 0){
			for(MallOperatorVo item : operators.getData()){
				BaseResponseVo<List<MallRoleVo>> data = operatorService.queryOperatorRole(item.getId());
				if(data.getData() != null){
					item.setRoleList(data.getData());
				}
			}
		}
		view.addObject("list",operators.getData());
		// 所有权限
		BaseResponseVo<List<MallRoleVo>> roles = roleService.querySelective(traneId);
		if(roles.getData() != null && roles.getData().size() > 0){
			Iterator<MallRoleVo> it = roles.getData().iterator();
			while(it.hasNext()){
				MallRoleVo item = it.next();
				if(item.getState() == YesNoEnum.NO.getValue()){
					it.remove();
				}
			}
		}
		view.addObject("roles",roles.getData());
		
		return view;
	}

	
	/**
	 * 操作员启用停用操作
	 * 如果操作员是启用状态，则停用，如果是停用状态则启用
	 * @param operatorId
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public @ResponseBody Map<String,String> updateStatus(@RequestParam(value="operatorId") Long operatorId){
		Long traneId = ControllerContext.getTenantId();
		BaseResponseVo<MallOperatorVo> data = operatorService.getByOperatorId(traneId, operatorId);
		
		Map<String,String> result = new HashMap<String,String>();
		result.put("code","0");
		if(null == data.getData()){
			result.put("code", "-1");
			result.put("msg","操作员不存在！");
			return result;
		}
		MallOperatorVo operator = data.getData();
		
		Integer status = data.getData().getState() == YesNoEnum.YES.getValue() ? YesNoEnum.NO.getValue():YesNoEnum.YES.getValue();
		
		BaseResponseVo<Boolean> updateResult = operatorService.updateOperatorStatus(operator.getTenantId(),operator.getId(),status);
		if(!updateResult.isSuccess() || !updateResult.getData()){
			result.put("code", "-1");
			result.put("msg",updateResult.getMessage());
			return result;
		}
		result.put("msg","ok");
		return result;
	}
	
	@RequestMapping("/addOrUpdate")
	public @ResponseBody Map<String,String> addOrUpdate(@RequestParam(value="operatorId")Long operatorId,@RequestParam(value="realName")String realName,@RequestParam(value="userErp")String userErp){
		Map<String,String> result = new HashMap<String,String>();
		if(StringUtils.isBlank(realName)){
			result.put("code", "-1");
			result.put("msg","用户名称不能为空");
			return result;
		}
		if(StringUtils.isBlank(userErp)){
			result.put("code", "-1");
			result.put("msg","用户ERP不能为空");
			return result;
		}
		Long tenantId = ControllerContext.getTenantId();
		BaseResponseVo<Boolean> updateResult = null;
		if(operatorId == null || operatorId <= 0){
			updateResult = operatorService.insertOperator(tenantId,userErp, realName);
		}else{
			updateResult = operatorService.updateOperator(tenantId, operatorId, realName, userErp);
		}
		if(updateResult.getData() == null || !updateResult.getData()){
			result.put("code", "-1");
			result.put("msg",updateResult.getMessage());
			return result;
		}
		result.put("code", "0");
		result.put("msg","ok");
		return result;
	}
	
	/**
	 * 操作员赋值角色 操作
	 * @return
	 */
	@RequestMapping("/updateOperatorRole")
	public @ResponseBody Map<String,String> updateOperatorRole(@RequestParam(value="operatorId") Long operatorId,@RequestParam(value="roleIds") String roleIds){
		Long tenantId = ControllerContext.getTenantId();
		Map<String,String> result = new HashMap<String,String>();
		result.put("code","0");
		if(StringUtils.isBlank(roleIds)){
			result.put("code","-1");
			result.put("msg","角色Id串不可为空");
			return result;
		}
		BaseResponseVo<Boolean> updateResult = operatorService.updateOperatorRole(tenantId, operatorId, roleIds);
		if(!updateResult.isSuccess() || !updateResult.getData()){
			result.put("code", "-1");
			result.put("msg",updateResult.getMessage());
			return result;
		}
		result.put("msg","ok");
		return result;
	}

	/**
	 * 保存操作员信息
	 * @param oper
	 * @return
	 */
	/*@RequestMapping("/insertOperator")
	public @ResponseBody Map<String,String> insertOperator(MallOperatorVo oper){
		Long tenantId = ControllerContext.getTenantId();
		Map<String,String> result = new HashMap<String,String>();
		result.put("code","0");
		if(StringUtils.isBlank(oper.getRealName()) ){
			result.put("code", "-1");
			result.put("msg","数据不能为空");
			return result;
		}
		BaseResponseVo<Boolean> insertResult = operatorService.insertOperator(tenantId.toString(),oper.getUserErp(),
				oper.getRealName());
		if(!insertResult.isSuccess() || !insertResult.getData()){
			result.put("code", "-1");
			result.put("msg",insertResult.getMessage());
			return result;
		}
		result.put("msg","ok");
		return result;
	}*/

}
