package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.mall.service.domain.MallAppVersion;
import com.jcloud.b2c.mall.service.mapper.MallAppVersionMapper;
import com.jcloud.b2c.mall.service.service.MallAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  版本号
 * Created by hongyifei on 2017/3/22.
 */
@Service("mallAppVersionService")
public class MallAppVersionServiceImpl implements MallAppVersionService {

    @Autowired
    private MallAppVersionMapper mallAppVersionMapper;

    @Override
    public MallAppVersion queryByPlatform(Integer platform) {
        return mallAppVersionMapper.queryByPlatform(platform);
    }
}
