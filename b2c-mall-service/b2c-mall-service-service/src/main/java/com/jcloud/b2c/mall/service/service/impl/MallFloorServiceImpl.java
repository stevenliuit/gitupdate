/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallFloorServiceImpl.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/14
 */

package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallFloor;
import com.jcloud.b2c.mall.service.mapper.MallFloorMapper;
import com.jcloud.b2c.mall.service.service.MallFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 楼层SERVICE_IMPL
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-14 17:04
 * @lastdate:
 */

@Service("mallFloorService")
public class MallFloorServiceImpl implements MallFloorService {
    @Autowired
    private MallFloorMapper mallFloorMapper;

    @Override
    public MallFloor get(Long id, Long tenantId){
        MallFloor mallFloor = new MallFloor();
        mallFloor.setId(id);
        mallFloor.setTenantId(tenantId);
        return mallFloorMapper.getByPrimaryKey(mallFloor);
    }

    @Override
    public boolean add(MallFloor mallFloor){
        Date now = new Date();
        mallFloor.setCreated(now);
        mallFloor.setModified(now);
        return mallFloorMapper.insert(mallFloor) == 1?true:false;
    }

    @Override
    public boolean update(MallFloor mallFloor){
        Date now = new Date();
        mallFloor.setModified(now);
        return mallFloorMapper.updateByPrimaryKeySelective(mallFloor) == 1?true:false;
    }

    @Override
    public boolean delete(MallFloor mallFloor){
        Date now = new Date();
        mallFloor.setModified(now);
        mallFloor.setState(StateEnum.DELETED.getValue());
        return mallFloorMapper.deleteByPrimaryKey(mallFloor) == 1?true:false;
    }

    @Override
    public List<MallFloor> query(MallFloor mallFloor){
        mallFloor.setState(StateEnum.ON_SHELF.getValue());
        return mallFloorMapper.querySelective(mallFloor);
    }
}
