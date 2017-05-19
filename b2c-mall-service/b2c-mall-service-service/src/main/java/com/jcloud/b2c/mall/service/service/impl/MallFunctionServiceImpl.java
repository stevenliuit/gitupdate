package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.mapper.MallFunctionMapper;
import com.jcloud.b2c.mall.service.service.MallFunctionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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

        return  mallFunctionMapper.getByFunctionKey(mallFunction);
    }

    @Override
    public List<MallFunction> querySelective(MallFunction mallFunctionn){
        return mallFunctionMapper.querySelective(mallFunctionn);
    }

    @Override
    public  boolean insertFunction(MallFunction mallFunctionn) {
        Date time = new Date();
        mallFunctionn.setCreated(time);
        mallFunctionn.setModified(time);
        return mallFunctionMapper.insertFunction(mallFunctionn) == 1 ? true : false;
    }

    @Override
    public boolean updateByFunctionKey(MallFunction mallFunctionn) {
        Date time = new Date();
        mallFunctionn.setModified(time);
        return mallFunctionMapper.updateByFunctionKey(mallFunctionn) == 1 ? true : false;
    }

}
