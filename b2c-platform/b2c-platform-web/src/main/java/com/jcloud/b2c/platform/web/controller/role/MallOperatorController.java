package com.jcloud.b2c.platform.web.controller.role;

import java.nio.channels.FileChannel.MapMode;
import java.util.List;

import javax.annotation.Resource;

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
import com.jcloud.b2c.platform.domain.vo.MallOperatorVo;
import com.jcloud.b2c.platform.domain.vo.MallRoleVo;
import com.jcloud.b2c.platform.service.role.MallOperatorService;
import com.jcloud.b2c.platform.service.role.MallRoleService;

@Controller
@RequestMapping("/operator")
public class MallOperatorController {

	Logger logger = LoggerFactory.getLogger(MallOperatorController.class);

	@Resource
	private MallOperatorService operatorService;
	@Resource
	private MallRoleService roleService;
	
	
	@RequestMapping("/toList")
	public ModelAndView toOperatorList(){
		ModelAndView view = new ModelAndView("role/operatorList");
		Long traneId = ControllerContext.getTenantId();
//		BaseResponseVo<List<MallOperatorVo>>  operators = operatorService.querySelective(traneId.toString());
//		if(operators.isSuccess()){
//			view.addObject("list",operators.getData());
//		}
		return view;
	}
	/**
	 * 跳转到 操作员赋角色页面
	 * @return
	 */
	@RequestMapping("/toUpdateRole")
	public ModelAndView toUpdateRole(@RequestParam(value="operatorId") Long operatorId){
		ModelAndView view = new ModelAndView("role/operatorUpdateRole");
		Long traneId = ControllerContext.getTenantId();
//		BaseResponseVo<MallOperatorVo> opData = operatorService.getByOperatorId(traneId, operatorId);
//		if(opData.getData() == null){
//			view.setViewName("error");
//			view.addObject("message","操作员不存在");
//			return view;
//		}
//		view.addObject("operator",opData.getData());
//		BaseResponseVo<List<MallRoleVo>> roleData = roleService.querySelective(traneId);
//		if(roleData.getData() == null || roleData.getData().size() == 0){
//			view.setViewName("error");
//			view.addObject("message","系统没有任何可用角色,请先添加角色。");
//			return view;
//		}
//		view.addObject("roles",roleData.getData());
		
		return view;
	}
	
	/**
	 * 操作员启用停用操作
	 * 如果操作员是启用状态，则停用，如果是停用状态则启用
	 * @param operatorId
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public @ResponseBody BaseResponseVo<Boolean> updateStatus(@RequestParam(value="operatorId") Long operatorId){
		Long traneId = ControllerContext.getTenantId();
//		BaseResponseVo<MallOperatorVo> data = operatorService.getByOperatorId(traneId, operatorId);
//		BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
//		if(null == data.getData()){
//			result.setFail("-1","该ID操作员不存在！");
//			return result;
//		}
//		MallOperatorVo operator = data.getData();
//		Integer status = data.getData().getStatus() == YesNoEnum.YES.getValue() ? YesNoEnum.NO.getValue():YesNoEnum.YES.getValue();
//		BaseResponseVo<Boolean> updateResult = operatorService.updateOperatorStatus(operator.getTenantId(),operator.getId(),status);

		BaseResponseVo<Boolean> updateResult = new BaseResponseVo<>();
		return updateResult;
	}
	
	/**
	 * 操作员赋值角色 操作
	 * @return
	 */
	@RequestMapping("/updateOperatorRole")
	public @ResponseBody BaseResponseVo<Boolean> updateOperatorRole(MapMode model , @RequestParam(value="operatorId") Long operatorId,@RequestParam(value="roleIds") String roleIds){
		Long tenantId = ControllerContext.getTenantId();
//		BaseResponseVo<Boolean> result = operatorService.updateOperatorRole(tenantId, operatorId, roleIds);
		BaseResponseVo<Boolean> result = new BaseResponseVo<>();
		return result;
	}
}
