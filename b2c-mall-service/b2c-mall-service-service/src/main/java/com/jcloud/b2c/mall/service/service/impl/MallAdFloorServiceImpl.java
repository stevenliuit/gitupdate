package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallAd;
import com.jcloud.b2c.mall.service.domain.MallAdFloor;
import com.jcloud.b2c.mall.service.domain.MallFloor;
import com.jcloud.b2c.mall.service.mapper.MallAdFloorMapper;
import com.jcloud.b2c.mall.service.mapper.MallAdMapper;
import com.jcloud.b2c.mall.service.mapper.MallFloorMapper;
import com.jcloud.b2c.mall.service.service.MallAdFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/21 10:55
 * @lasdate
 */
@Service("mallAdFloorService")
public class MallAdFloorServiceImpl implements MallAdFloorService {

    @Autowired
    private MallAdFloorMapper mallAdFloorMapper;

    @Autowired
    private MallAdMapper mallAdMapper;

    @Autowired
    private MallFloorMapper mallFloorMapper;

    @Override
    public List<MallAdFloor> query(Long floorId, Integer adType, Long tenantId) {
        return mallAdFloorMapper.queryByFloorId(floorId, adType, tenantId);
    }

    @Override
    public boolean add(MallAdFloor mallAdFloor) {
        Date now = new Date();
        mallAdFloor.setCreated(now);
        mallAdFloor.setModified(now);
        MallAd mallAd = mallAdFloor.getMallAd();
        String url = mallAd.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallAd.setCreated(now);
        mallAd.setModified(now);

        mallAdMapper.insert(mallAd);
        Long mallAdId = mallAd.getId();

        mallAdFloor.setAdId(mallAdId);
        //mallAdFloorMapper.updateSortToMoveDown(mallAdFloor, null);
        return mallAdFloorMapper.insert(mallAdFloor) == 1;
    }

    @Override
    public boolean delete(MallAdFloor mallAdFloor) {
        MallAd mallAd = mallAdFloor.getMallAd();
        mallAd.setModified(new Date());
        mallAd.setState(StateEnum.DELETED.getValue());
        boolean result = mallAdMapper.deleteByPrimaryKey(mallAdFloor.getMallAd()) == 1? true:false;
        if (result) {
            mallAdFloorMapper.updateSortToMoveUp(mallAdFloor, null);
            result = mallAdFloorMapper.deleteByPrimaryKey(mallAdFloor) == 1? true:false;
        }
        return result;
    }

    @Override
    public MallAdFloor get(Long id, Long tenantId) {
        return mallAdFloorMapper.getAdFloorById(id, tenantId);
    }

    @Override
    public boolean update(MallAdFloor mallAdFloor, Integer beforeSort) {
        Date now = new Date();
        mallAdFloor.setModified(now);
        MallAd mallAd = mallAdFloor.getMallAd();
        String url = mallAd.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallAd.setModified(now);
        /*if (mallAdFloor.getSortNum()<beforeSort) {
            mallAdFloorMapper.updateSortToMoveDown(mallAdFloor, beforeSort);
        } else if (mallAdFloor.getSortNum()>beforeSort) {
            Integer afterSort = mallAdFloor.getSortNum();
            mallAdFloor.setSortNum(beforeSort);
            mallAdFloorMapper.updateSortToMoveUp(mallAdFloor, afterSort);
            mallAdFloor.setSortNum(afterSort);
        }*/
        boolean result = mallAdMapper.updateByPrimaryKeySelective(mallAd) == 1? true:false;
        if (result)
            result = mallAdFloorMapper.updateByPrimaryKeySelective(mallAdFloor) == 1? true:false;
        return result;
    }

    /**
     * 查询专题
     */
    @Override
    public List<MallAdFloor> queryBySpecial(MallAdFloor mallAdFloor) {
        return mallAdFloorMapper.queryBySpecial(mallAdFloor);
    }

    @Override
    public boolean addBySpecial(MallAdFloor mallAdFloor) {
        Date now = new Date();

        MallAd mallAd = mallAdFloor.getMallAd();
        String link = mallAd.getLink();
        if (CsrfUtils.isCsrf(link)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallAd.setCreated(now);
        mallAd.setModified(now);
        mallAdMapper.insert(mallAd);

        MallFloor mallFloor = mallAdFloor.getMallFloor();
        String moreLink = mallFloor.getMoreLink();
        if (CsrfUtils.isCsrf(moreLink)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallFloor.setCreated(now);
        mallFloor.setModified(now);
        mallFloorMapper.insert(mallFloor);

        mallAdFloor.setCreated(now);
        mallAdFloor.setModified(now);
        mallAdFloor.setAdId(mallAd.getId());
        mallAdFloor.setFloorId(mallFloor.getId());
        int insert = mallAdFloorMapper.insert(mallAdFloor);
        return insert==1;
    }

    @Override
    public boolean updateBySpecial(MallAdFloor mallAdFloor) {
        Date now = new Date();

        MallAd mallAd = mallAdFloor.getMallAd();
        String link = mallAd.getLink();
        if (CsrfUtils.isCsrf(link)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallAd.setCreated(now);
        mallAd.setModified(now);
        mallAdMapper.updateByPrimaryKeySelective(mallAd);

        MallFloor mallFloor = mallAdFloor.getMallFloor();
        String moreLink = mallFloor.getMoreLink();
        if (CsrfUtils.isCsrf(moreLink)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallFloor.setCreated(now);
        mallFloor.setModified(now);
        mallFloorMapper.updateByPrimaryKeySelective(mallFloor);

        mallAdFloor.setCreated(now);
        mallAdFloor.setModified(now);

        return mallAdFloorMapper.updateByPrimaryKeySelective(mallAdFloor)==1;
    }

    @Override
    public boolean deleteBySpecial(MallAdFloor mallAdFloor) {
        Date now = new Date();
        Long tenantId = mallAdFloor.getTenantId();
        Long adId = mallAdFloor.getAdId();
        Long floorId = mallAdFloor.getFloorId();

        MallAd mallAd = new MallAd();
        mallAd.setModified(now);
        mallAd.setId(adId);
        mallAd.setTenantId(tenantId);
        mallAd.setState(StateEnum.DELETED.getValue());
        mallAdMapper.deleteByPrimaryKey(mallAd);

        MallFloor mallFloor = new MallFloor();
        mallFloor.setModified(now);
        mallFloor.setId(floorId);
        mallFloor.setTenantId(tenantId);
        mallFloor.setState(StateEnum.DELETED.getValue());
        mallFloorMapper.deleteByPrimaryKey(mallFloor);

        return mallAdFloorMapper.deleteByPrimaryKey(mallAdFloor)==1;
    }
}
