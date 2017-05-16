package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallAppVersion;
import com.jcloud.b2c.mall.service.service.MallAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * APP版本号
 * Created by hongyifei on 2017/3/22.
 */
@RestController
@RequestMapping("appVersion")
public class MallAppVersionController {
    @Autowired
    private MallAppVersionService mallAppVersionService;

    @RequestMapping(value = "/queryMaxVerByPlatform", method = RequestMethod.GET)
    public BaseResponseVo<MallAppVersion> queryMaxVerByPlatform(Integer platform){
        BaseResponseVo<MallAppVersion> responseVo = new BaseResponseVo<>();
        responseVo.setIsSuccess(false);
        if(platform > 0){
            MallAppVersion mallAppVersion = mallAppVersionService.queryByPlatform(platform);
            responseVo.setIsSuccess(true);
            responseVo.setData(mallAppVersion);
        }
        return responseVo;
    }

}
