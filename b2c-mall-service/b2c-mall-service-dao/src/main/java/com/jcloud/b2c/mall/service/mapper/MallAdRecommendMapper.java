package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallAdRecommend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MallAdRecommendMapper {
    int deleteByPrimaryKey(MallAdRecommend record);

    int insert(MallAdRecommend record);

    int insertSelective(MallAdRecommend record);

    MallAdRecommend selectByPrimaryKey(MallAdRecommend record);

    int updateByPrimaryKeySelective(MallAdRecommend record);

    int updateByPrimaryKey(MallAdRecommend record);

    List<MallAdRecommend> queryWithMallAd(MallAdRecommend record);

    MallAdRecommend selectById(MallAdRecommend record);
}