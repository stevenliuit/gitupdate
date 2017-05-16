package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Method:角色mapper
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
@Mapper
public interface MallRoleMapper {

    List<MallRole> querySelective(MallRole mallRole);

    int insertRole(MallRole mallRole);

    int deleteByRoleFunction(MallRole mallRole);

    int addOrUpdateRoleFunction(Map<String,Object> map);

    int updateRole(MallRole mallRole);

}
