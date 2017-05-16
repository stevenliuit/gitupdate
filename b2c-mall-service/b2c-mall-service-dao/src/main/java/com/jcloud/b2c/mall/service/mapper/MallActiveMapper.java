package com.jcloud.b2c.mall.service.mapper;


import com.jcloud.b2c.mall.service.domain.MallActive;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * liuhao
 * Created by issuser on 2017/3/3.
 * 活动页Mapper
 */
@Mapper
public interface MallActiveMapper {

    MallActive getByPrimaryKey(MallActive mallActive);

    List<MallActive> querySelective(MallActive mallActive);

    int insert(MallActive mallActive);

    int updateByPrimaryKeySelective(MallActive mallActive);

    int deleteByPrimaryKey(MallActive mallActive);
}
