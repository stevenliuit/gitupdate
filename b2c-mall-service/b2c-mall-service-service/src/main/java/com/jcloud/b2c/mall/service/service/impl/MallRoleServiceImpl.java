package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.domain.MallOperator;
import com.jcloud.b2c.mall.service.domain.MallRole;
import com.jcloud.b2c.mall.service.mapper.MallRoleMapper;
import com.jcloud.b2c.mall.service.service.MallRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Method:
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
@Service("mallRoleService")
public class MallRoleServiceImpl implements MallRoleService {

    @Autowired
    private MallRoleMapper mallRoleMapper;

    /**
     * 以角色id查询权限
     */
    @Override
    public List<MallFunction> getByRoleKey(MallRole mallRole){
        return mallRoleMapper.getByRoleKey(mallRole);
    }

    /**
     * 查询所有角色
     */
    @Override
    public List<MallRole> querySelective(MallRole mallRole){
        return mallRoleMapper.querySelective(mallRole);
    }

    /**
     * 增加角色
     */
    @Override
    public  boolean insertRole(MallRole mallRole) {
        Date time = new Date();
        mallRole.setCreated(time);
        mallRole.setModified(time);
        return mallRoleMapper.insertRole(mallRole) == 1 ? true : false;
    }

    /**
     *角色赋权
     */
    @Override
    public  boolean updateRoleFunction(Long roleId,Long tenantId,String functionIds) {
        MallRole mallRole = new MallRole();
        mallRole.setId(roleId);
        mallRole.setTenantId(tenantId);
        int flag = mallRoleMapper.deleteByRoleFunction(mallRole);
        if (flag >= 0){
            String[] functionIdss = functionIds.split(",");
            List<Long> list = new ArrayList<Long>();
            for (int i = 0; i <functionIdss.length ; i++) {
                list.add(Long.valueOf(functionIdss[i]));
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("roleId",roleId);
            map.put("functionIds",list);
            
            int row = mallRoleMapper.addOrUpdateRoleFunction(map);
            
            return  row == functionIdss.length;
        }
        return false;
    }

    /**
     * 修改角色
     */
    @Override
    public  boolean updateRole(MallRole mallRole) {
        Date time = new Date();
        mallRole.setModified(time);
        return mallRoleMapper.updateRole(mallRole) == 1 ? true : false;
    }

    /**
     * 根据角色id查询拥有这个角色的所有操作员
     */
    @Override
    public  List<MallOperator> getByRoleAllOperator(MallRole mallRole) {
        return mallRoleMapper.getByRoleAllOperator(mallRole);
    }

}
