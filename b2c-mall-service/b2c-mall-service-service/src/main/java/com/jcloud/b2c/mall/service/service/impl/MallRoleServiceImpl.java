package com.jcloud.b2c.mall.service.service.impl;

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
     * 询所有角色
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
        mallRoleMapper.deleteByRoleFunction(mallRole);
        String[] functionIdss = functionIds.split(",");
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i <functionIdss.length ; i++) {
            list.add(Long.valueOf(functionIdss[i]));
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("roleId",roleId);
        map.put("functionIds",list);
        return mallRoleMapper.addOrUpdateRoleFunction(map) > 0 ? true :false;
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
}
