package com.jcloud.b2c.mall.service.mapper;

import com.jcloud.b2c.mall.service.domain.MallAppVersion;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by hongyifei on 2017/3/21.
 */
@Mapper
public interface MallAppVersionMapper {

    MallAppVersion queryByPlatform(Integer platform);
}
