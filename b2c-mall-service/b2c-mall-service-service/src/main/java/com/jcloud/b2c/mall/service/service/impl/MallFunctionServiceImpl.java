package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.mapper.MallFunctionMapper;
import com.jcloud.b2c.mall.service.service.MallFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * @Method:
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
@Component
public class MallFunctionServiceImpl implements MallFunctionService {

    @Resource
    private MallFunctionMapper mallFunctionMapper;

    @Override
    public MallFunction getByFunctionKey(MallFunction mallFunction) {

        return null;
    }

    @Override
    public List<MallFunction> querySelective(MallFunction mallFunctionn){
        return null;
    }

    @Override
    public  boolean insertFunction(MallFunction mallFunctionn) {

        return true;
    }

    @Override
    public boolean updateByFunctionKey(MallFunction mallFunctionn) {

        return true;
    }

    @Override
    public boolean deleteByFunctionKey(MallFunction mallFunctionn) {

        return true;
    }
}
