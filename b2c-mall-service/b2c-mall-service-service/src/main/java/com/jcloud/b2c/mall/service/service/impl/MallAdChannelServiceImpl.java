package com.jcloud.b2c.mall.service.service.impl;

import com.jcloud.b2c.common.common.util.CsrfUtils;
import com.jcloud.b2c.mall.service.client.enums.StateEnum;
import com.jcloud.b2c.mall.service.domain.MallAd;
import com.jcloud.b2c.mall.service.domain.MallAdChannel;
import com.jcloud.b2c.mall.service.mapper.MallAdChannelMapper;
import com.jcloud.b2c.mall.service.mapper.MallAdMapper;
import com.jcloud.b2c.mall.service.service.MallAdChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description:设置频道广告
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/20 16:22
 * @lasdate
 */
@Service("mallAdChannelService")
public class MallAdChannelServiceImpl implements MallAdChannelService {

    @Autowired
    private MallAdChannelMapper mallAdChannelMapper;

    @Autowired
    private MallAdMapper mallAdMapper;

    @Override
    public List<MallAdChannel> query(Long channelId, Long tenantId) {
        MallAdChannel mallAdChannel = new MallAdChannel();
        mallAdChannel.setChannelId(channelId);
        mallAdChannel.setTenantId(tenantId);
        mallAdChannel.getMallAd().setState(StateEnum.ON_SHELF.getValue());
        return mallAdChannelMapper.queryByChannel(mallAdChannel);
    }

    @Override
    public boolean add(MallAdChannel mallAdChannel) {
        Date now = new Date();
        mallAdChannel.setCreated(now);
        mallAdChannel.setModified(now);
        MallAd mallAd = mallAdChannel.getMallAd();
        String link = mallAd.getLink();
        if (CsrfUtils.isCsrf(link)) {
            throw new RuntimeException("Url is csrf exception");
        }
        mallAd.setCreated(now);
        mallAd.setModified(now);

        mallAdMapper.insert(mallAd);
        long adId = mallAd.getId();
        mallAdChannel.setAdId(adId);
        //mallAdChannelMapper.updateSortToMoveDown(mallAdChannel, null);
        return mallAdChannelMapper.insert(mallAdChannel) == 1;
    }

    @Override
    public MallAdChannel get(Long id, Long tenantId) {
        MallAdChannel mallAdChannel = new MallAdChannel();
        mallAdChannel.setId(id);
        mallAdChannel.setTenantId(tenantId);
        return mallAdChannelMapper.getAdChannelById(mallAdChannel);
    }

    @Override
    public boolean update(MallAdChannel mallAdChannel, Integer beforeSort) {
        Date now = new Date();
        mallAdChannel.setModified(now);
        MallAd mallAd = mallAdChannel.getMallAd();
        mallAd.setModified(now);
        String link = mallAd.getLink();
        if (CsrfUtils.isCsrf(link)) {
            throw new RuntimeException("Url is csrf exception");
        }
        /*if (mallAdChannel.getSortNum()<beforeSort) {
            mallAdChannelMapper.updateSortToMoveDown(mallAdChannel, beforeSort);
        } else if (mallAdChannel.getSortNum()>beforeSort) {
            Integer afterSort = mallAdChannel.getSortNum();
            mallAdChannel.setSortNum(beforeSort);
            mallAdChannelMapper.updateSortToMoveUp(mallAdChannel, afterSort);
            mallAdChannel.setSortNum(afterSort);
        }*/
        boolean result = mallAdChannelMapper.updateByPrimaryKeySelective(mallAdChannel) == 1? true:false;
        if (result)
            result = mallAdMapper.updateByPrimaryKeySelective(mallAdChannel.getMallAd()) == 1? true:false;
        return result;
    }

    @Override
    public boolean delete(MallAdChannel mallAdChannel) {
        MallAd mallAd = mallAdChannel.getMallAd();
        mallAd.setModified(new Date());
        mallAd.setState(StateEnum.DELETED.getValue());
        boolean result = mallAdMapper.deleteByPrimaryKey(mallAd) == 1?true:false;
        if (result) {
            mallAdChannelMapper.updateSortToMoveUp(mallAdChannel, null);
            result = mallAdChannelMapper.deleteByPrimaryKey(mallAdChannel) == 1? true:false;
        }
        return result;
    }

}
