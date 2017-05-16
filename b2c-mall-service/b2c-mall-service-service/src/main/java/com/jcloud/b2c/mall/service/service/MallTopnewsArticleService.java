package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallBanner;
import com.jcloud.b2c.mall.service.domain.MallTopnewsArticle;

import java.util.List;

/**
 * @description:头条service
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/5 14:00
 * @lasdate
 */
public interface MallTopnewsArticleService {

    MallTopnewsArticle get(Long id, Long tenantId);

    boolean add(MallTopnewsArticle mallTopnewsArticle);

    boolean update(MallTopnewsArticle mallTopnewsArticle);

    boolean delete(MallTopnewsArticle mallTopnewsArticle);

    List<MallTopnewsArticle> query(MallTopnewsArticle mallTopnewsArticle);
}
