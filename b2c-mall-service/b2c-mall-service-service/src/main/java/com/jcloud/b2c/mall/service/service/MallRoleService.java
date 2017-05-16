package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallRole;

import java.util.List;

/**
 * @Method:角色管理
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
public interface MallRoleService {



    List<MallRole> querySelective(MallRole mallRole);

    boolean insertRole(MallRole mallRole);

    boolean updateRoleFunction(Long roleId,Long tenantId,String functionIds);

    boolean updateRole(MallRole mallRole);



   /* MallRole getByRoleKey(MallRole mallRole);
    boolean updateByRoleKey(MallRole mallRole);
    boolean deleteByRoleKey(MallRole mallRole);
    boolean addRoleFunction(Long roleId,String functionIds);*/

}
