package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallAppVersion;

/**
 * Created by hongyifei on 2017/3/22.
 */
public interface MallAppVersionService {

    MallAppVersion queryByPlatform(Integer platform);
}
