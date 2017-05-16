/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallIndexPageService.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/2/16
 */
package com.jcloud.b2c.mall.service.service;

/**
 * @description: 首页静态化等
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-16 11:16
 * @lastdate:
 */
public interface MallIndexPageService {

    Boolean reloadTemplate();
    /**
     * 发布首页
     * @param tenantId
     * @return
     */
    Boolean createIndexPage(Long tenantId,Boolean reloadTempate);
    Boolean createH5IndexPage(Long tenantId);

    /**
     * 预览首页
     * @param tenantId
     * @return
     */
    String viewIndexPage(Long tenantId);
    String viewH5IndexPage(Long tenantId);
    String getMIndexPage(Long tenantId);

    /**
     * 获取公共头部
     * @param tenantId
     * @return
     */
    String getCommonHeader(Long tenantId);

    /**
     * 获取公共底部
     * @param tenantId
     * @return
     */
    String getCommonFooter(Long tenantId);

    /**
     * 获取公共头部 head区域
     * @param tenantId
     * @return
     */
    String getCommonHeaderFragment(Long tenantId);

    /**
     * 创建频道首页
     * @param tenantId 租户id
     * @param channelCode 频道code
     * @return
     */
    Boolean createChannelIndexPage(Long tenantId, String channelCode);

    /**
     * 预览频道首页
     * @param tenantId 租户id
     * @param channelCode 频道code
     * @return
     */
    String viewChannelIndexPage(Long tenantId, String channelCode);

    /**
     * 获取频道首页
     * @param tenantId
     * @param channelCode
     * @return
     */
    String getChannelIndexPage(Long tenantId, String channelCode);


    /**
     * 缓存PayHeader
     * @param tenantId
     * @return
     */
    Boolean cachePayHeader(Long tenantId);

    /**
     * 返回PayHeader
     * @param tenantId
     * @return
     */
    String getPayHeader(Long tenantId);

    /**
     * 获取首页
     * @param tenantId
     * @return
     */
    String getIndexPage(Long tenantId);

    /**
     * 简单头部html
     * @param tenantId
     * @return
     */
    String getSimpleHeader(Long tenantId);

    /**
     * 删除缓存中的频道首页
     * @param tenantId
     * @param channelCode
     * @return
     */
    void deleteChannelIndexPageFromCache(Long tenantId, String channelCode);
}
