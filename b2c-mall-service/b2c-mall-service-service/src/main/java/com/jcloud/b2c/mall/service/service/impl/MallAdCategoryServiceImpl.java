package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallAd;
import com.jcloud.b2c.mall.service.domain.MallAdCategory;
import com.jcloud.b2c.mall.service.mapper.MallAdCategoryMapper;
import com.jcloud.b2c.mall.service.mapper.MallAdMapper;
import com.jcloud.b2c.mall.service.service.MallAdCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/22 16:18
 * @lasdate
 */
@Service("mallAdCategoryService")
public class MallAdCategoryServiceImpl implements MallAdCategoryService {

    @Autowired
    private MallAdCategoryMapper mallAdCategoryMapper;

    @Autowired
    private MallAdMapper mallAdMapper;

    @Override
    public List<MallAdCategory> query(Long categoryId, Long tenantId) {
        return mallAdCategoryMapper.queryByCategory(categoryId, tenantId, StateEnum.ON_SHELF.getValue());
    }

    @Override
    public boolean add(MallAdCategory mallAdCategory) {
        Date now = new Date();
        mallAdCategory.setCreated(now);
        mallAdCategory.setModified(now);
        MallAd mallAd = mallAdCategory.getMallAd();
        String link = mallAd.getLink();
        if (CsrfUtils.isCsrf(link)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallAd.setCreated(now);
        mallAd.setModified(now);
        mallAdMapper.insert(mallAd);
        Long adId = mallAd.getId();
        mallAdCategory.setAdId(adId);
        //mallAdCategoryMapper.updateSortToMoveDown(mallAdCategory, null);
        boolean result = mallAdCategoryMapper.insert(mallAdCategory) == 1;
        return result;
    }

    @Override
    public boolean delete(MallAdCategory mallAdCategory) {
        Date now = new Date();
        MallAd mallAd = mallAdCategory.getMallAd();
        mallAd.setState(StateEnum.DELETED.getValue());
        mallAd.setModified(now);
        boolean result = mallAdMapper.deleteByPrimaryKey(mallAd) == 1;
        if (result)
            mallAdCategoryMapper.updateSortToMoveUp(mallAdCategory, null);
            result = mallAdCategoryMapper.deleteByPrimaryKey(mallAdCategory) == 1;
        return result;
    }

    @Override
    public MallAdCategory get(Long id, Long tenantId) {
        MallAdCategory mallAdCategory = new MallAdCategory();
        mallAdCategory.setId(id);
        mallAdCategory.setTenantId(tenantId);
        return mallAdCategoryMapper.queryAdCategoryById(mallAdCategory);
    }

    @Override
    public boolean update(MallAdCategory mallAdCategory, Integer beforeSort) {
        Date now = new Date();
        mallAdCategory.setModified(now);
        MallAd mallAd = mallAdCategory.getMallAd();
        mallAd.setModified(now);
        String link = mallAd.getLink();
        if (CsrfUtils.isCsrf(link)) {
            throw new RuntimeException("Url is csrf exception");
        }
        /*if (mallAdCategory.getSortNum()<beforeSort) {
            mallAdCategoryMapper.updateSortToMoveDown(mallAdCategory, beforeSort);
        } else if (mallAdCategory.getSortNum()>beforeSort) {
            Integer afterSort = mallAdCategory.getSortNum();
            mallAdCategory.setSortNum(beforeSort);
            mallAdCategoryMapper.updateSortToMoveUp(mallAdCategory, afterSort);
            mallAdCategory.setSortNum(afterSort);
        }*/
        boolean result = mallAdCategoryMapper.updateByPrimaryKeySelective(mallAdCategory) == 1;
        if (result)
            result = mallAdMapper.updateByPrimaryKeySelective(mallAdCategory.getMallAd()) == 1;
        return result;
    }
}
