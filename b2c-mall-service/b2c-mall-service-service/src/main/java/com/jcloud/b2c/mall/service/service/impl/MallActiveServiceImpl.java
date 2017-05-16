package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallActive;
import com.jcloud.b2c.mall.service.mapper.MallActiveMapper;
import com.jcloud.b2c.mall.service.service.MallActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * liuhao
 * Created by issuser on 2017/3/3.
 * 活动页serviceImpl
 */
@Service("mallActiveService")
public class MallActiveServiceImpl implements MallActiveService{

    @Autowired
    private MallActiveMapper mallActiveMapper;

    @Override
    public MallActive get(Long id, Long tenantId) {
        MallActive mallActive =new MallActive();
        mallActive.setId(id);
        mallActive.setTenantId(tenantId);
        return mallActiveMapper.getByPrimaryKey(mallActive);
    }

    @Override
    public List<MallActive> query(MallActive mallActive) {
        return mallActiveMapper.querySelective(mallActive);
    }

    @Override
    public boolean add(MallActive mallActive) {
        Date time=new Date();
        mallActive.setCreated(time);
        mallActive.setModified(time);
        return mallActiveMapper.insert(mallActive) == 1 ? true : false;
    }

    @Override
    public boolean update(MallActive mallActive) {
        Date time=new Date();
        mallActive.setModified(time);
        return mallActiveMapper.updateByPrimaryKeySelective(mallActive) == 1 ? true:false;
    }

    @Override
    public boolean delete(MallActive mallActive) {
        Date time =new Date();
        mallActive.setModified(time);
        mallActive.setState(StateEnum.DELETED.getValue());
        return mallActiveMapper.deleteByPrimaryKey(mallActive) == 1 ? true :false;
    }

}
