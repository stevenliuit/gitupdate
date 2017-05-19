package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.domain.MallOperator;
import com.jcloud.b2c.mall.service.domain.MallRole;
import com.jcloud.b2c.mall.service.service.MallOperatorService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Method: 用户管理
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
@RestController
@RequestMapping("/operator")
public class MallOperatorController {

    @Resource
    private MallOperatorService mallOperatorService;

    /**
     * 更具 操作员ID 查询操作员
     * @param tenantId
     * @param operatorId
     * @return
     */
    @RequestMapping(value="/getByOperatorId",method={RequestMethod.GET,RequestMethod.POST})
    public BaseResponseVo<MallOperator> getByOperatorId(@RequestParam(value="tenantId")Long tenantId,
														@RequestParam(value="operatorId") Long operatorId){
    	MallOperator search = new MallOperator();
    	search.setTenantId(tenantId);
    	search.setId(operatorId);
    	MallOperator operator = mallOperatorService.getByOperatorKey(search);
    	BaseResponseVo<MallOperator> result = new BaseResponseVo<MallOperator>();
    	if(operator == null){
    		result.setFail("-1","operator id is not exist.");
    		return result;
    	}
    	result.setIsSuccess(true);
    	result.setData(operator);
    	return result;
    }
    
    @RequestMapping(value="/queryOperatorByErp")
    public BaseResponseVo<MallOperator> queryOperatorByErp(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="userErp")String userErp){
    	MallOperator search = new MallOperator();
    	search.setTenantId(tenantId);
    	search.setUserErp(userErp);
    	MallOperator operator = mallOperatorService.getByOperatorKey(search);
    	BaseResponseVo<MallOperator> result = new BaseResponseVo<MallOperator>();
    	if(operator == null){
    		result.setFail("-1","operator userErp is not exist.");
    		return result;
    	}
    	result.setIsSuccess(true);
    	result.setData(operator);
    	return result;
    }
    
    
    /**
     * 查询 用户所拥有的全部 角色
     * @author cyy
     * @date 2017年5月19日
     * @param operatorId
     * @return
     */
    @RequestMapping("/queryOperatorRole")
    public BaseResponseVo<List<MallRole>> queryOperatorRole(@RequestParam(value="operatorId")Long operatorId){
    	
    	MallOperator search = new MallOperator();
    	search.setId(operatorId);
    	search.setState(YesNoEnum.YES.getValue());
    	
    	List<MallRole> roles = mallOperatorService.queryOperatorRole(search);
    	BaseResponseVo<List<MallRole>> result = new BaseResponseVo<List<MallRole>>();
    	result.setIsSuccess(true);
    	result.setData(roles);
    	return result;
    }
    
    
    /**
     * 修改操作员的状态
     * @param tenantId
     * @param operatorId
     * @param status
     * @return
     */
    @RequestMapping(value="/updateOperatorStatus",method={RequestMethod.GET,RequestMethod.POST})
    public BaseResponseVo<Boolean> updateOperatorStatus(@RequestParam(value="tenantId") Long tenantId,@RequestParam("operatorId") Long operatorId,
														@RequestParam(value="status")int status){
    	MallOperator update = new MallOperator();
    	update.setId(operatorId);
    	update.setTenantId(tenantId);
    	update.setState(status);
    	boolean updateResult = mallOperatorService.updateByOperatorKey(update);
    	BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
    	if(!updateResult){
			result.setFail("-1","update error.");
		}
		result.setData(updateResult);
    	return result;
    }
    
    @RequestMapping("/updateOperator")
    public BaseResponseVo<Boolean> updateOperator(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="operatorId")Long operatorId,@RequestParam(value="realName")String realName,@RequestParam(value="userErp")String userErp){
    	MallOperator search = new MallOperator();
    	search.setId(operatorId);
    	search.setTenantId(tenantId);
    	BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
    	
    	MallOperator update = mallOperatorService.getByOperatorKey(search);
    	if(update == null){
    		result.setFail("-1","操作员不存在");
    		return result;
    	}
    	
    	update.setRealName(realName);
    	update.setUserErp(userErp);
    	boolean ok = mallOperatorService.updateByOperatorKey(update);
    	if(!ok){
    		result.setFail("-1","update is error.");
    		return result;
    	}
    	result.setIsSuccess(true);
    	result.setData(Boolean.TRUE);
    	
    	return result;
    }
    
    
    /**
     * 修改操作员角色
     * @param tenantId 承租人Id
     * @param operatorId 操作员ID
     * @param roleIds  角色Id串，并且Id之间用英文逗号（,）分割
     * @return
     */
    @RequestMapping(value="/updateOperatorRole",method={RequestMethod.GET,RequestMethod.POST})
   	public BaseResponseVo<Boolean> updateOperatorRole(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="operatorId")Long operatorId,
														 @RequestParam(value="roleIds")String roleIds){

		BaseResponseVo<Boolean> result = new  BaseResponseVo<Boolean>();
		boolean update =  mallOperatorService.updateOperatorRole(operatorId,tenantId,roleIds);
		if(!update){
			result.setFail("-1","update error.");
			return result;
		}
		result.setIsSuccess(true);
		result.setData(update);
		return result;
    }

    /**
     * 查询所有的 操作员
     * @param
     * @return
	 * 分页
     */
    @RequestMapping(value = "/querySelective" ,method={RequestMethod.GET,RequestMethod.POST})
    public BaseResponseVo<List<MallOperator>> querySelective(@RequestParam(value="tenantId") String tenantIdStr) {

		Long tenantId = NumberUtils.toLong(tenantIdStr);
    	MallOperator operator = new MallOperator();
    	operator.setTenantId(tenantId);
    	List<MallOperator> list = mallOperatorService.querySelective(operator);
    	BaseResponseVo<List<MallOperator>> result = new BaseResponseVo<List<MallOperator>>();
    	result.setIsSuccess(true);
    	result.setData(list);
        return result;
    }

    /**
     * 保存 操作员用户详情
     * @param trenantIdStr
     * @param userErp
     * @param realName
     */
    @RequestMapping(value = "/insertOperator")
    public BaseResponseVo<Boolean> insertOperator(@RequestParam(value="tenantId")String trenantIdStr,@RequestParam(value="userErp")String userErp,
												  @RequestParam(value="realName") String realName) {
    	
    	Long trenantId  = NumberUtils.toLong(trenantIdStr);
    	BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
    	MallOperator search = new MallOperator();
    	search.setTenantId(trenantId);
		search.setUserErp(userErp);
    	MallOperator operator = mallOperatorService.getByOperatorKey(search);
    	if(operator != null){
    		// 已存在无需增加
    		result.setFail("-1","usererp is exist.");
    		return result;
    	}
    	//  不存在则保存
    	operator = new MallOperator();
    	operator.setCreated(new Date());
    	operator.setModified(new Date());
    	operator.setState(YesNoEnum.YES.getValue());
    	operator.setTenantId(trenantId);
    	operator.setUserErp(userErp);
    	operator.setRealName(realName);
    	boolean saveResult = mallOperatorService.insertOperator(operator);
    	if(!saveResult){
    		result.setFail("-1","save is error.");
    		return result;
    	}
    	result.setIsSuccess(true);
    	result.setData(Boolean.TRUE);
    	return result;
    }

    /**
     * 查询用户所拥有的权限
     * @param trenantId
     * @param userErp 用户erp标识
     * @return
	 *
     */
    @RequestMapping(value="/queryFunction")
    public BaseResponseVo<List<MallFunction>> queryFunction(@RequestParam(value="tenantId")Long trenantId,
															@RequestParam(value="userErp")String userErp,
															@RequestParam(value="page")Integer page,
															@RequestParam(value="pageSize")Integer pageSize){

    	BaseResponseVo<List<MallFunction>> result = new BaseResponseVo<List<MallFunction>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("trenantId",trenantId);
		map.put("userErp",userErp);
		List<MallFunction> function = mallOperatorService.queryFunction(map);
		
		result.setIsSuccess(Boolean.TRUE);
		if(function != null){
			result.setData(function);
		}
		// result.setIsSuccess(Boolean.FALSE);
		return result;
	}

    /**
     * 给用户添加角色
     * @param operatorId
     * @param roleIds
     * @return
     */
    @RequestMapping(value = "/addOperatorRole" ,method={RequestMethod.GET,RequestMethod.POST})
    public BaseResponseVo<Boolean> addOperatorRole(@RequestParam(value="operatorId")Long operatorId,@RequestParam(value="trenantId")Long trenantId,
												   @RequestParam(value="roleIds")String roleIds) {
		BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
		boolean flag = mallOperatorService.addOperatorRole(operatorId,roleIds);
		if(!flag){
			result.setFail("-1","addOperatorRole error.");
		}
		result.setIsSuccess(true);
		result.setData(flag);
		return result;
    }
}
