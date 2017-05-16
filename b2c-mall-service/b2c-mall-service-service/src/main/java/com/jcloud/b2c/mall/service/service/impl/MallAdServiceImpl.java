/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdServiceImpl.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.mall.service.client.enums.AdPrincipalTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.*;
import com.jcloud.b2c.mall.service.mapper.MallAdCategoryMapper;
import com.jcloud.b2c.mall.service.mapper.MallAdChannelMapper;
import com.jcloud.b2c.mall.service.mapper.MallAdFloorMapper;
import com.jcloud.b2c.mall.service.mapper.MallAdMapper;
import com.jcloud.b2c.mall.service.service.MallAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 首页广告SERVICEIMPL
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 17:48
 * @lastdate:
 */

@Service("mallAdService")
public class MallAdServiceImpl implements MallAdService {
    @Autowired
    private MallAdMapper mallAdMapper;

    @Autowired
    private MallAdChannelMapper mallAdChannelMapper;

    @Autowired
    private MallAdFloorMapper mallAdFloorMapper;

    @Autowired
    private MallAdCategoryMapper mallAdCategoryMapper;

    @Override
    public MallAd get(Long id, Long tenantId){
        MallAd mallAd = new MallAd();
        mallAd.setId(id);
        mallAd.setTenantId(tenantId);
        return mallAdMapper.getByPrimaryKey(mallAd);
    }

    @Override
    public boolean add(MallAd mallAd, String principalType, Long principalId, Integer principalSort){
        String url = mallAd.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        AdPrincipalTypeEnum adPrincipalTypeEnum = AdPrincipalTypeEnum.getAdPrincipalByCode(principalType);
        if(adPrincipalTypeEnum == null){
            return false;
        }

        Date now = new Date();
        mallAd.setCreated(now);
        mallAd.setModified(now);
        boolean result = mallAdMapper.insert(mallAd) == 1?true:false;

        if(result){
            switch(adPrincipalTypeEnum){
                case CATEGORY:
                    MallAdCategory mallAdCategory = new MallAdCategory();
                    mallAdCategory.setCategoryId(principalId);
                    this.bindAddPrincipal(mallAdCategory, principalSort, mallAd);
                    mallAdCategoryMapper.insert(mallAdCategory);
                    break;
                case CHANNEL:
                    MallAdChannel mallAdChannel = new MallAdChannel();
                    mallAdChannel.setChannelId(principalId);
                    this.bindAddPrincipal(mallAdChannel, principalSort, mallAd);
                    mallAdChannelMapper.insert(mallAdChannel);
                    break;
                case FLOOR:
                    MallAdFloor mallAdFloor = new MallAdFloor();
                    mallAdFloor.setFloorId(principalId);
                    this.bindAddPrincipal(mallAdFloor, principalSort, mallAd);
                    mallAdFloorMapper.insert(mallAdFloor);
                    break;
            }
        }
        return result;
    }

    private void bindAddPrincipal(MallAdPrincipal mallAdPrincipal, Integer principalSort, MallAd mallAd){
        mallAdPrincipal.setSortNum(principalSort);
        mallAdPrincipal.setTenantId(mallAd.getTenantId());
        mallAdPrincipal.setAdId(mallAd.getId());
        mallAdPrincipal.setClientType(mallAd.getClientType());
        mallAdPrincipal.setCreated(new Date());
        mallAdPrincipal.setModified(new Date());
    }

    @Override
    public boolean update(MallAd mallAd, String principalType, Long principalId, Integer principalSort){
        String url = mallAd.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        AdPrincipalTypeEnum adPrincipalTypeEnum = AdPrincipalTypeEnum.getAdPrincipalByCode(principalType);
        if(adPrincipalTypeEnum == null){
            return false;
        }
        Date now = new Date();
        mallAd.setModified(now);
        boolean result = mallAdMapper.updateByPrimaryKeySelective(mallAd) == 1?true:false;
        if(result){
            switch(adPrincipalTypeEnum){
                case CATEGORY:
                    MallAdCategory mallAdCategory = new MallAdCategory();
                    mallAdCategory.setCategoryId(principalId);
                    this.bindUpdatePrincipal(mallAdCategory, principalSort, mallAd);
                    mallAdCategoryMapper.updateUniqueSelective(mallAdCategory);
                    break;
                case CHANNEL:
                    MallAdChannel mallAdChannel = new MallAdChannel();
                    mallAdChannel.setChannelId(principalId);
                    this.bindUpdatePrincipal(mallAdChannel, principalSort, mallAd);
                    mallAdChannelMapper.updateUniqueSelective(mallAdChannel);
                    break;
                case FLOOR:
                    MallAdFloor mallAdFloor = new MallAdFloor();
                    mallAdFloor.setFloorId(principalId);
                    this.bindUpdatePrincipal(mallAdFloor, principalSort, mallAd);
                    mallAdFloorMapper.updateUniqueSelective(mallAdFloor);
                    break;
            }
        }
        return result;
    }

    private void bindUpdatePrincipal(MallAdPrincipal mallAdPrincipal, Integer principalSort, MallAd mallAd){
        mallAdPrincipal.setSortNum(principalSort);
        mallAdPrincipal.setTenantId(mallAd.getTenantId());
        mallAdPrincipal.setAdId(mallAd.getId());
        mallAdPrincipal.setModified(new Date());
    }

    @Override
    public boolean delete(MallAd mallAd, String principalType, Long principalId){
        AdPrincipalTypeEnum adPrincipalTypeEnum = AdPrincipalTypeEnum.getAdPrincipalByCode(principalType);
        if(adPrincipalTypeEnum == null){
            return false;
        }
        Date now = new Date();
        mallAd.setModified(now);
        mallAd.setState(StateEnum.DELETED.getValue());
        boolean result =  mallAdMapper.deleteByPrimaryKey(mallAd) == 1?true:false;

        if(result){
            switch(adPrincipalTypeEnum){
                case CATEGORY:
                    MallAdCategory mallAdCategory = new MallAdCategory();
                    mallAdCategory.setCategoryId(principalId);
                    this.bindDelPrincipal(mallAdCategory, mallAd);
                    mallAdCategoryMapper.deleteUnique(mallAdCategory);
                    break;
                case CHANNEL:
                    MallAdChannel mallAdChannel = new MallAdChannel();
                    mallAdChannel.setChannelId(principalId);
                    this.bindDelPrincipal(mallAdChannel, mallAd);
                    mallAdChannelMapper.deleteUnique(mallAdChannel);
                    break;
                case FLOOR:
                    MallAdFloor mallAdFloor = new MallAdFloor();
                    mallAdFloor.setFloorId(principalId);
                    this.bindDelPrincipal(mallAdFloor, mallAd);
                    mallAdFloorMapper.deleteUnique(mallAdFloor);
                    break;
            }
        }
        return result;
    }

    private void bindDelPrincipal(MallAdPrincipal mallAdPrincipal, MallAd mallAd){
        mallAdPrincipal.setTenantId(mallAd.getTenantId());
        mallAdPrincipal.setAdId(mallAd.getId());
    } 

    @Override
    public List<MallAd> query(MallAd mallAd, String principalType, Long principalId){
        List<MallAd> list = null;
        AdPrincipalTypeEnum adPrincipalTypeEnum = AdPrincipalTypeEnum.getAdPrincipalByCode(principalType);
        if(adPrincipalTypeEnum == null){
            return list;
        }

        switch(adPrincipalTypeEnum){
            case CATEGORY:
                list = mallAdMapper.queryByCategory(mallAd, principalId);
                break;
            case CHANNEL:
                list = mallAdMapper.queryByChannel(mallAd, principalId);
                break;
            case FLOOR:
                list = mallAdMapper.queryByFloor(mallAd, principalId);
                break;
        }

        return list;
    }

    @Override
    public boolean add(MallAd mallAd) {
        String url = mallAd.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallAd.setCreated(now);
        mallAd.setModified(now);
        return mallAdMapper.insert(mallAd)==1;
    }

    @Override
    public boolean update(MallAd mallAd) {
        String url = mallAd.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallAd.setModified(now);
        return mallAdMapper.updateByPrimaryKeySelective(mallAd)==1;
    }

    @Override
    public boolean delete(MallAd mallAd) {
        Date now = new Date();
        mallAd.setModified(now);
        return mallAdMapper.deleteByPrimaryKey(mallAd)==1;
    }


    @Override
    public List<MallAd> query(MallAd mallAd) {
        return mallAdMapper.querySelective(mallAd);
    }

}
