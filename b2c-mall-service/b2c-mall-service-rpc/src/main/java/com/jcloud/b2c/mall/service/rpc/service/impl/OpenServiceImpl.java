/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: OpenServiceImpl.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/3/25
 */
package com.jcloud.b2c.mall.service.rpc.service.impl;

import com.jcloud.b2c.common.common.util.Md5Utils;
import com.jcloud.b2c.mall.service.rpc.model.CdnPurgeDirReq;
import com.jcloud.b2c.mall.service.rpc.model.CdnPurgeDirRes;
import com.jcloud.b2c.mall.service.rpc.service.OpenService;
import com.jcloud.b2c.mall.service.rpc.service.RestService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-03-25 15:11
 * @lastdate:
 */
@Service("openService")
public class OpenServiceImpl implements OpenService {

    @Resource
    private RestService restService;

    @Value("${m.domain}")
    private String mDomain;

    @Value("${cdn.appkey}")
    private String cdnAppKey;

    @Value("${cdn.appid}")
    private String cdnAppId;

    String cdnPurgeDirPath = "http://columbus.jcloud.com?Action={0}&AppId={1}&Path={2}&ReqId={3}&TTL={4}&Signature={5}&Timestamp={6}";

    @Override
    public String getMIndexPage() {
        String html = restService.get(String.class, mDomain).getResult();
        return html;
    }

    @Override
    public String cdnPurgeDir(String url) {
        String timestamp = String.valueOf(System.currentTimeMillis()/1000L);
        String val = cdnAppId + cdnAppKey + timestamp;
        String signature = Md5Utils.getMd5(val).toLowerCase();

       /* Map<String, Object> pars = new HashedMap();
        pars.put("Action", "PurgeDir");
        pars.put("AppId", cdnAppId);
        pars.put("Path", url);
        pars.put("ReqId", "1");
        pars.put("TTL", "604800");
        pars.put("Signature", signature);
        pars.put("Timestamp", timestamp);*/

        String path = MessageFormat.format(cdnPurgeDirPath, new String[]{"PurgeDir", cdnAppId, url, "1", "604800", signature, timestamp});
        Map res = restService.get(Map.class, path).getResult();
        return res.toString();
    }

}
