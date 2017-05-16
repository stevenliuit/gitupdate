/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: OpenService.java project: jcloud-b2c-user-service
 * @creator: wangxin17
 * @date: 2017/2/10
 */
package com.jcloud.b2c.mall.service.rpc.service;

import com.jcloud.b2c.mall.service.rpc.model.CdnPurgeDirRes;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-10 18:07
 * @lastdate:
 */
public interface OpenService {

    String getMIndexPage();

    String cdnPurgeDir(String url);
}
