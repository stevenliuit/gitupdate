package com.jcloud.b2c.mall.service.service.impl;


import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.mall.service.domain.MallFunction;
import com.jcloud.b2c.mall.service.domain.MallOperator;
import com.jcloud.b2c.mall.service.mapper.MallOperatorMapper;
import com.jcloud.b2c.mall.service.service.MallOperatorService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Method:
 * @Author:zhangjian
 * @Date: 2017/5/15
 */
@Component
public class MallOperatorServiceImpl implements MallOperatorService {

	@Resource
    private MallOperatorMapper mallOperatorMapper;


    @Override
    public MallOperator getByOperatorKey(MallOperator mallOperator) {
        return mallOperatorMapper.getByOperatorKey(mallOperator);
    }

    /**
     * 修改用户
     */
    @Override
    public boolean updateByOperatorKey(MallOperator mallOperator) {;
        return mallOperatorMapper.updateByOperatorKey(mallOperator) == 1 ? true : false;
    }

    /**
     * 修改操作员角色
     */
    @Override
    public boolean updateOperatorRole(Long operatorId,Long tenantId,String roleIds) {;
        MallOperator mallOperator = new MallOperator();
        mallOperator.setTenantId(tenantId);
        mallOperator.setId(operatorId);
        int flag = mallOperatorMapper.deleteByOperatorOrleKey(mallOperator);
        if(flag > 0){
            String[] roleIdss = roleIds.split(",");
            List<Long> roleId = new ArrayList<Long>();
            for (int i = 0; i <roleIdss.length ; i++) {
                roleId.add(Long.valueOf(roleIdss[i]));
            }
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("operatorId",operatorId);
            map.put("roleIds",roleId);
            return mallOperatorMapper.addOrUpdateOperatorRole(map) == roleIdss.length ? true : false;
        }
        return false;
    }

    /**
     * 查询所有的 操作员
     */
    @Override
    public List<MallOperator> querySelective(MallOperator mallOperator){
        return mallOperatorMapper.querySelective(mallOperator);
    }

    /**
     * 查询用户所拥有的权限
     */
    @Override
    public List<MallFunction> queryFunction(MallOperator mallOperator){
        return mallOperatorMapper.queryFunction(mallOperator);
    }

    /**
     * 添加用户
     */
    @Override
    public  boolean insertOperator(MallOperator mallOperator) {
        return mallOperatorMapper.insertOperator(mallOperator)==1 ? true : false;
    }

    /**
     * 给用户添加角色
     */
    @Override
    public  boolean addOperatorRole(Long operatorId,String roleIds) {
        String[] roleIdss = roleIds.split(",");
        List<Long> roleId= new ArrayList<Long>();
        for (int i = 0; i <roleIdss.length ; i++) {
            roleId.add(Long.valueOf(roleIdss[i]));
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("operatorId",operatorId);
        map.put("roleIds",roleId);
        return mallOperatorMapper.addOrUpdateOperatorRole(map) == roleIdss.length ? true : false;
    }

    /**
     * 禁用用户
     */
    @Override
    public boolean deleteByOperatorKey(MallOperator mallOperator) {
        Date time =new Date();
        mallOperator.setModified(time);
        mallOperator.setStatus(YesNoEnum.NO.getValue());
        return mallOperatorMapper.deleteByOperatorKey(mallOperator) == 1 ? true : false;
    }



}
