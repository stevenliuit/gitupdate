package com.jcloud.b2c.mall.service.service;

import com.jcloud.b2c.mall.service.domain.MallAdChannel;

import java.util.List;

/**
 * @description:频道广告service
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/20 14:44
 * @lasdate
 */
public interface MallAdChannelService {

    List<MallAdChannel> query(Long channelId, Long tenantId);

    boolean add(MallAdChannel mallAdChannel);

    MallAdChannel get(Long id, Long tenantId);

    boolean update(MallAdChannel mallAdChannel, Integer beforeSort);

    boolean delete(MallAdChannel mallAdChannel);
}
