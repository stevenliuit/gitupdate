package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.domain.MallOperator;
import com.jcloud.b2c.mall.service.domain.MallRole;
import com.jcloud.b2c.mall.service.service.MallRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Method: 角色管理
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
@RestController
@RequestMapping("/role")
public class MallRoleController {

    @Autowired
    private MallRoleService  mallRoleService;

    /**
     * 查询所有角色
     * @param tenantId
     */
    @RequestMapping(value = "/querySelective")
    public BaseResponseVo<List<MallRole>> querySelective(@RequestParam(value="tenantId") Long tenantId) {

        MallRole search = new MallRole();
    	search.setTenantId(tenantId);
    	List<MallRole> roles = mallRoleService.querySelective(search);
    	BaseResponseVo<List<MallRole>> result = new BaseResponseVo<List<MallRole>>();
        if(roles == null || roles .size() == 0){
            result.setIsSuccess(false);
            return result;
        }
        result.setIsSuccess(true);
        result.setData(roles);
        return result;
    }

    /**
     * 以角色ID查询权限
     * @param tenantId
     */
    @RequestMapping(value = "/getByOrleFunction")
    public BaseResponseVo<List<MallFunction>> getByOrleFunction(@RequestParam(value="tenantId") Long tenantId,
                                                            @RequestParam(value="roleId") Long orleId) {
        MallRole search = new MallRole();
        search.setTenantId(tenantId);
        search.setId(orleId);
        List<MallFunction> functionList = mallRoleService.getByRoleKey(search);
        BaseResponseVo<List<MallFunction>> result = new BaseResponseVo<List<MallFunction>>();
        if(functionList == null || functionList .size() == 0){
        	result.setFail("-1","没有权限");
            return result;
        }
        result.setIsSuccess(true);
        result.setData(functionList);
        return result;
    }

    /**
     * 根据 RoleId 查询角色
     * @param tenantId
     * @param roleId
     * @return
     */
    @RequestMapping(value="/queryByRoleId")
    public BaseResponseVo<MallRole> queryByRoleId(@RequestParam("tenantId")Long tenantId,@RequestParam(value="roleId")Long roleId){
    	BaseResponseVo<MallRole> result = new BaseResponseVo<MallRole>();
    	
    	MallRole search = new MallRole();
    	search.setTenantId(tenantId);
    	search.setId(roleId);
    	
    	List<MallRole> role = mallRoleService.querySelective(search);
    	if(role == null ||  role.size() == 0){
    		result.setFail("-1","角色不存在");
    		return result;
    	}
    	result.setIsSuccess(Boolean.TRUE);
    	result.setData(role.get(0));
    	
    	return result;
    }

    /**
     * 增加角色
     * @param tenantId
     * @param roleName 角色名称
     * @param description 角色描述
     * @return
     */
    @RequestMapping(value = "/insertRole")
    public BaseResponseVo<Boolean> insertRole(@RequestParam(value="tenantId") Long tenantId,
                                              @RequestParam(value="roleName")String roleName,
                                              @RequestParam(value="description") String description) {

        BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
        MallRole mallRole = new MallRole();
        mallRole.setTenantId(tenantId);
        mallRole.setName(roleName);
        mallRole.setDescription(description);
        mallRole.setState(YesNoEnum.YES.getValue());
        mallRole.setCreated(new Date());
        mallRole.setModified(new Date());
        boolean flag = mallRoleService.insertRole(mallRole);
        if(!flag){
            result.setFail("-1","insertRole error.");
            return result;
        }
        result.setIsSuccess(true);
        result.setData(flag);
        return result;
    }

    /**
     * 角色赋权
     * @param
     * @return
     */
    @RequestMapping(value = "/updateRoleFunction")
    public BaseResponseVo<Boolean> updateRoleFunction(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="roleId") Long roleId,@RequestParam(value="functionIds")String functionIds) {

        BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
        boolean flag = mallRoleService.updateRoleFunction(roleId,tenantId,functionIds);
        if (!flag){
            result.setFail("-1","updateRoleFunction error");
            return result;
        }
    	result.setIsSuccess(true);
    	result.setData(Boolean.TRUE);
        return result;
    }
    
    /**
     * 根据 权限角色  修改角色信息
     * 角色的启用，禁用功能也使用该 api
     * @param roleId 角色Id 必填
     * @param roleName 角色名称，选填 为空则不跟新该属性值
     * @param description 角色描述，选填，为空不更新该属性值
     * @param status 角色状态，选填,如果 为空，或小于0 则不跟新 角色状态，
     * @return
     */
    @RequestMapping(value="/updateRole")
    public BaseResponseVo<Boolean> updateRole(@RequestParam(value="tenantId")Long tenantId,@RequestParam(value="roleId")Long roleId,@RequestParam(value="roleName")String roleName,@RequestParam(value="description") String description,@RequestParam(value="status") int status){

    	BaseResponseVo<Boolean> result = new BaseResponseVo<Boolean>();
        MallRole mallRole = new MallRole();
        mallRole.setId(roleId);
        if(StringUtils.isNotBlank(roleName)){
        	mallRole.setName(roleName);
        }
        if(StringUtils.isNotBlank(description)){
        	mallRole.setDescription(description);
        }
        if(status >= 0){
        	mallRole.setState(status);
        }
        mallRole.setModified(new Date());
        boolean flag = mallRoleService.updateRole(mallRole);
        if (!flag){
            result.setFail("-1","updateRole error");
            result.setIsSuccess(false);
            return result;
        }
        result.setIsSuccess(true);
        result.setData(Boolean.TRUE);
        return result;
    }

    /**
     * 根据角色id查询拥有这个角色的所有操作员
     * @param
     * @return
     */
    @RequestMapping(value = "/getByRoleAllOperator")
    public BaseResponseVo<List<MallOperator>> getByRoleAllOperator(@RequestParam(value="tenantId")Long tenantId, @RequestParam(value="roleId") Long roleId) {

        BaseResponseVo<List<MallOperator>> result = new BaseResponseVo<List<MallOperator>>();
        MallRole mallRole = new MallRole();
        mallRole.setId(roleId);
        mallRole.setTenantId(tenantId);
        List<MallOperator> operatorList = mallRoleService.getByRoleAllOperator(mallRole);
        if (operatorList != null){
            result.setIsSuccess(true);
            result.setData(operatorList);
        }
        result.setIsSuccess(Boolean.FALSE);
        return result;
    }
}
