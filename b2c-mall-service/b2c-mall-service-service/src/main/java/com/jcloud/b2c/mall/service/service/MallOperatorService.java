package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.domain.MallOperator;
import com.jcloud.b2c.mall.service.domain.MallRole;

import java.util.List;
import java.util.Map;

/**
 * @Method:用户管理
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
public interface MallOperatorService {

    MallOperator getByOperatorKey(MallOperator mallOperator);

    boolean updateByOperatorKey(MallOperator mallOperator);

    boolean updateOperatorRole(Long operatorId,Long tenantId,String roleIds);

    List<MallOperator> querySelective(MallOperator mallOperator);

    List<MallFunction> queryFunction(Map<String,Object> map);
    
    List<MallRole> queryOperatorRole(MallOperator operator);

    boolean insertOperator(MallOperator mallOperator);

    boolean addOperatorRole(Long operatorId,String roleIds);



}
