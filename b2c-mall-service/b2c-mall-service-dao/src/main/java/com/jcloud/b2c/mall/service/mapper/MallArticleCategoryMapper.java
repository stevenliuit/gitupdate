package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallArticleCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MallArticleCategoryMapper {
    int deleteByPrimaryKey(MallArticleCategory record);

    int insert(MallArticleCategory record);

    int insertSelective(MallArticleCategory record);

    MallArticleCategory selectByPrimaryKey(MallArticleCategory record);

    int updateByPrimaryKeySelective(MallArticleCategory record);

    int updateByPrimaryKey(MallArticleCategory record);

    List<MallArticleCategory> querySelective(MallArticleCategory record);
}