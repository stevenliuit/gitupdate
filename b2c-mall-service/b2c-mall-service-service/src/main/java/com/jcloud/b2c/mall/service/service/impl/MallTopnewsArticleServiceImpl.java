package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.mall.service.domain.MallTopnewsArticle;
import com.jcloud.b2c.mall.service.mapper.MallTopnewsArticleMapper;
import com.jcloud.b2c.mall.service.service.MallTopnewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/5 14:01
 * @lasdate
 */
@Service("mallTopnewsArticleService")
public class MallTopnewsArticleServiceImpl implements MallTopnewsArticleService {

    @Autowired
    private MallTopnewsArticleMapper mapper;

    @Override
    public MallTopnewsArticle get(Long id, Long tenantId) {
        MallTopnewsArticle mallTopnewsArticle = new MallTopnewsArticle();
        mallTopnewsArticle.setId(id);
        mallTopnewsArticle.setTenantId(tenantId);
        return mapper.selectByPrimaryKey(mallTopnewsArticle);
    }

    @Override
    public boolean add(MallTopnewsArticle mallTopnewsArticle) {
        String articleUrl = mallTopnewsArticle.getArticleUrl();
        if (CsrfUtils.isCsrf(articleUrl)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallTopnewsArticle.setCreated(now);
        mallTopnewsArticle.setModified(now);
        return mapper.insertSelective(mallTopnewsArticle)==1;
    }

    @Override
    public boolean update(MallTopnewsArticle mallTopnewsArticle) {
        String articleUrl = mallTopnewsArticle.getArticleUrl();
        if (CsrfUtils.isCsrf(articleUrl)) {
            throw new RuntimeException("Url is csrf exception");
        }
        Date now = new Date();
        mallTopnewsArticle.setModified(now);
        return mapper.updateByPrimaryKeySelective(mallTopnewsArticle)==1;
    }

    @Override
    public boolean delete(MallTopnewsArticle mallTopnewsArticle) {
        return mapper.deleteByPrimaryKey(mallTopnewsArticle)==1;
    }

    @Override
    public List<MallTopnewsArticle> query(MallTopnewsArticle mallTopnewsArticle) {
        return mapper.query(mallTopnewsArticle);
    }
}
