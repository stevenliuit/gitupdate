package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallFunction;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Method:权限mapper
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
@Mapper
public interface MallFunctionMapper {

    MallFunction getByFunctionKey(MallFunction mallFunctionn);

    List<MallFunction> querySelective(MallFunction mallFunctionn);

    int insertFunction(MallFunction mallFunctionn);

    int updateByFunctionKey(MallFunction mallFunctionn);

    int deleteByFunctionKey(MallFunction mallFunctionn);
}
