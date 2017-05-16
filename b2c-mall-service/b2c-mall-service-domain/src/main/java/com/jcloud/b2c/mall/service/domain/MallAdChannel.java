/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdChannel.java project: jcloud-b2c-mall-service
 * @creator: lidongxing
 * @date: 2017/2/15
 */

package com.jcloud.b2c.mall.service.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 广告和频道关联
 * @author: lidongxing
 * @requireNo:
 * @createdate: 2017-02-15 11:02
 * @lastdate:
 */

public class MallAdChannel extends MallAdPrincipal implements Serializable {
    /**
     * 频道ID
     */
    private Long channelId;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "MallAdChannel{" +
                "channelId=" + channelId +
                "} " + super.toString();
    }
}
