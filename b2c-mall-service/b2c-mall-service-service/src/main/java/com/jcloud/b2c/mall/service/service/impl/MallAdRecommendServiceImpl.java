package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.mall.service.domain.MallAd;
import com.jcloud.b2c.mall.service.domain.MallAdRecommend;
import com.jcloud.b2c.mall.service.mapper.MallAdMapper;
import com.jcloud.b2c.mall.service.mapper.MallAdRecommendMapper;
import com.jcloud.b2c.mall.service.service.MallAdRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/11 17:05
 * @lasdate
 */
@Service("mallAdRecommendService")
public class MallAdRecommendServiceImpl implements MallAdRecommendService {

    @Autowired
    private MallAdRecommendMapper mallAdRecommendMapper;

    @Autowired
    private MallAdMapper mallAdMapper;

    @Override
    public MallAdRecommend get(Long id, Long tenantId) {
        MallAdRecommend mallAdRecommend = new MallAdRecommend();
        mallAdRecommend.setId(id);
        mallAdRecommend.setTenantId(tenantId);
        return mallAdRecommendMapper.selectById(mallAdRecommend);
    }

    @Override
    public boolean add(MallAdRecommend mallAdRecommend) {
        Date now = new Date();
        mallAdRecommend.setCreated(now);
        mallAdRecommend.setModified(now);
        MallAd mallAd = mallAdRecommend.getMallAd();
        String url = mallAd.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallAd.setCreated(now);
        mallAd.setModified(now);
        mallAdMapper.insert(mallAd);
        mallAdRecommend.setAdId(mallAd.getId());
        return mallAdRecommendMapper.insertSelective(mallAdRecommend)==1;
    }

    @Override
    public boolean update(MallAdRecommend mallAdRecommend) {
        Date now = new Date();
        mallAdRecommend.setModified(now);
        MallAd mallAd = mallAdRecommend.getMallAd();
        String url = mallAd.getLink();
        if (CsrfUtils.isCsrf(url)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallAd.setModified(now);
        mallAdMapper.updateByPrimaryKeySelective(mallAd);
        return mallAdRecommendMapper.updateByPrimaryKeySelective(mallAdRecommend)==1;
    }

    @Override
    public boolean delete(MallAdRecommend mallAdRecommend) {
        return mallAdRecommendMapper.deleteByPrimaryKey(mallAdRecommend)==1;
    }

    @Override
    public List<MallAdRecommend> queryWithMallAd(MallAdRecommend mallAdRecommend) {
        return mallAdRecommendMapper.queryWithMallAd(mallAdRecommend);
    }
}
