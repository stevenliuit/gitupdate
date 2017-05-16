package com.jcloud.b2c.platform.web.controller.cdnManage;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.platform.service.feign.CdnDirClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/30 14:47
 * @lasdate
 */
@Controller
@RequestMapping(value = "/mall/CdnDir")
public class CdnDirController {

    @Autowired
    private CdnDirClient cdnDirClient;

    @RequestMapping(value = "/toPage", method = {RequestMethod.GET, RequestMethod.POST})
    public String toPage() {
        return "mall/cdnManage/cdnPurgeDir";
    }

    @ResponseBody
    @RequestMapping(value = "/doPurge", method = {RequestMethod.GET, RequestMethod.POST})
    public  BaseResponseVo<String> doPurge(String urls) {
        BaseResponseVo<String> result = cdnDirClient.cdnPurgeDir(urls);
        return result;
    }
}
