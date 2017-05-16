/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallBannerServiceImpl.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.mall.service.client.enums.*;
import com.jcloud.b2c.mall.service.client.vo.MallBannerVo;
import com.jcloud.b2c.mall.service.domain.MallBanner;
import com.jcloud.b2c.mall.service.domain.MallPrincipalItem;
import com.jcloud.b2c.mall.service.mapper.MallBannerMapper;
import com.jcloud.b2c.mall.service.mapper.MallPrincipalItemMapper;
import com.jcloud.b2c.mall.service.service.MallBannerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 轮播图+广告位SERVICEIMPL 
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 17:56
 * @lastdate:
 */

@Service("mallBannerService")
public class MallBannerServiceImpl implements MallBannerService {
    @Autowired
    private MallBannerMapper mallBannerMapper;

    private MallPrincipalItemMapper mallPrincipalItemMapper;


    @Override
    public MallBanner get(Long id, Long tenantId){
        MallBanner mallBanner = new MallBanner();
        mallBanner.setId(id);
        mallBanner.setTenantId(tenantId);
        return mallBannerMapper.getByPrimaryKey(mallBanner);
    }

    @Override
    public boolean add(MallBanner mallBanner){
        String url = mallBanner.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallBanner.setCreated(now);
        mallBanner.setModified(now);
        //mallBannerMapper.updateSortToMoveDown(mallBanner);
        return mallBannerMapper.insert(mallBanner) == 1?true:false;
    }

    @Override
    public boolean update(MallBanner mallBanner){
        String url = mallBanner.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallBanner.setModified(now);
        return mallBannerMapper.updateByPrimaryKeySelective(mallBanner) == 1?true:false;
    }

    @Override
    public boolean delete(MallBanner mallBanner){
        Date now = new Date();
        mallBanner.setModified(now);
        mallBanner.setState(StateEnum.DELETED.getValue());
        return mallBannerMapper.deleteByPrimaryKey(mallBanner) == 1?true:false;
    }

    @Override
    public List<MallBanner> query(MallBanner mallBanner){
        return mallBannerMapper.querySelective(mallBanner);
    }

    @Override
    public MallBannerVo queryWithSkuId(MallBanner mallBanner) {
        MallBanner mallBannerSel = mallBannerMapper.getByPrimaryKey(mallBanner);
        //获取skuId
        MallPrincipalItem mallPrincipalItem = new MallPrincipalItem();
        mallPrincipalItem.setTenantId(mallBanner.getTenantId());
        mallPrincipalItem.setClientType(mallBanner.getClientType());
        mallPrincipalItem.setPrincipalId(mallBanner.getId());
        mallPrincipalItem.setPrincipalType(AdPrincipalTypeEnum.SUBJECT.getValue());
        mallPrincipalItem.setState(StateEnum.ON_SHELF.getValue());
        List<MallPrincipalItem> mallPrincipalItems = mallPrincipalItemMapper.querySelective(mallPrincipalItem);
        ArrayList<Long> skuIds = new ArrayList<>();
        for (MallPrincipalItem mallP:mallPrincipalItems) {
            skuIds.add(mallP.getItemId());
        }
        MallBannerVo mallBannerVo = new MallBannerVo();
        BeanUtils.copyProperties(mallBannerVo,mallBannerSel);
        mallBannerVo.setSkuIds(skuIds);
        return mallBannerVo;
    }

    @Override
    public MallBanner queryByFlash(MallBanner mallBanner) {
        Date beginTime = mallBanner.getBeginTime();
        if (null==beginTime){
            Date now = new Date();
            mallBanner.setBeginTime(now);
        }
        return mallBannerMapper.queryFlashByNow(mallBanner);
    }

    @Override
    public boolean checkByBeginTime(MallBanner mallBanner) {
        return mallBannerMapper.checkByBeginTime(mallBanner)>0;
    }
}

