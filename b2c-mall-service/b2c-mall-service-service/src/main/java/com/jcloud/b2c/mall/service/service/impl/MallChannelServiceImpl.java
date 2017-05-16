/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallChannelServiceImpl.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallChannel;
import com.jcloud.b2c.mall.service.mapper.MallChannelMapper;
import com.jcloud.b2c.mall.service.service.MallChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description: 频道SERVICEIMPL
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 17:58
 * @lastdate:
 */

@Service("mallChannelService")
public class MallChannelServiceImpl implements MallChannelService {
    @Autowired
    private MallChannelMapper mallChannelMapper;

    @Override
    public MallChannel get(Long id, Long tenantId){
        MallChannel mallChannel = new MallChannel();
        mallChannel.setId(id);
        mallChannel.setTenantId(tenantId);
        return mallChannelMapper.getByPrimaryKey(mallChannel);
    }

    @Override
    public boolean add(MallChannel mallChannel){
        String url = mallChannel.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallChannel.setCreated(now);
        mallChannel.setModified(now);
        return mallChannelMapper.insert(mallChannel) == 1?true:false;
    }

    @Override
    public boolean update(MallChannel mallChannel){
        String url = mallChannel.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallChannel.setModified(now);
        return mallChannelMapper.updateByPrimaryKeySelective(mallChannel) == 1?true:false;
    }

    @Override
    public boolean delete(MallChannel mallChannel){
        Date now = new Date();
        mallChannel.setModified(now);
        mallChannel.setState(StateEnum.DELETED.getValue());
        return mallChannelMapper.deleteByPrimaryKey(mallChannel) == 1?true:false;
    }

    @Override
    public List<MallChannel> query(MallChannel mallChannel){
        return mallChannelMapper.querySelective(mallChannel);
    }
}

