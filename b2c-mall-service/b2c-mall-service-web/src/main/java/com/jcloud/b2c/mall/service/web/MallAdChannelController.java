package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallAdChannel;
import com.jcloud.b2c.mall.service.service.MallAdChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:频道广告controller
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/20 14:40
 * @lasdate
 */
@RestController
@RequestMapping("/mallAdChannel")
public class MallAdChannelController {

    @Autowired
    private MallAdChannelService mallAdChannelService;

    /**
     * 频道广告列表
     * @param channelId
     * @param tenantId
     * @return
     */
    @RequestMapping(value = "/query" ,method = RequestMethod.GET)
    public BaseResponseVo<List<MallAdChannel>> query(@RequestParam Long channelId, @RequestParam Long tenantId) {
        BaseResponseVo<List<MallAdChannel>> responseVo = new BaseResponseVo<List<MallAdChannel>>();
        List<MallAdChannel> list = mallAdChannelService.query(channelId, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallAdChannel mallAdChannel) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdChannelService.add(mallAdChannel);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallAdChannel> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallAdChannel> responseVo = new BaseResponseVo<MallAdChannel>();
        MallAdChannel mallAdChannel = mallAdChannelService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallAdChannel);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallAdChannel mallAdChannel, @RequestParam Integer beforeSort){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdChannelService.update(mallAdChannel, beforeSort);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallAdChannel mallAdChannel){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdChannelService.delete(mallAdChannel);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
