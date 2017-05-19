package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallFunction;

import java.util.List;

/**
 * @Method:权限管理
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
public interface MallFunctionService {

    MallFunction getByFunctionKey(MallFunction mallFunction);

    List<MallFunction> querySelective(MallFunction mallFunctionn);

    boolean insertFunction(MallFunction mallFunctionn);

    boolean updateByFunctionKey(MallFunction mallFunctionn);
}
