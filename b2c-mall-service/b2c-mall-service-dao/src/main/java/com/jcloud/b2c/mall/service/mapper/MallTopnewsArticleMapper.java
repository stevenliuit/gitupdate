package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallTopnewsArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MallTopnewsArticleMapper {
    int deleteByPrimaryKey(MallTopnewsArticle record);

    int insert(MallTopnewsArticle record);

    int insertSelective(MallTopnewsArticle record);

    MallTopnewsArticle selectByPrimaryKey(MallTopnewsArticle record);

    int updateByPrimaryKeySelective(MallTopnewsArticle record);

    int updateByPrimaryKey(MallTopnewsArticle record);

    List<MallTopnewsArticle> query(MallTopnewsArticle mallTopnewsArticle);
}