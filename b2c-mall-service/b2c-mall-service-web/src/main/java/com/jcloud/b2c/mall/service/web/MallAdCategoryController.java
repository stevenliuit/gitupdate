/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: MallAdCategoryController.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/2/28
 */
package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallAdCategory;
import com.jcloud.b2c.mall.service.domain.MallAdChannel;
import com.jcloud.b2c.mall.service.service.MallAdCategoryService;
import com.jcloud.b2c.mall.service.service.MallAdChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-28 20:49
 * @lastdate:
 */
@RestController
@RequestMapping("/mallAdCategory")
public class MallAdCategoryController {

    @Resource
    private MallAdCategoryService mallAdCategoryService;

    /**
     * 频道广告列表
     * @param channelId
     * @param tenantId
     * @return
     */
    @RequestMapping(value = "/query" ,method = RequestMethod.GET)
    public BaseResponseVo<List<MallAdCategory>> query(@RequestParam Long channelId, @RequestParam Long tenantId) {
        BaseResponseVo<List<MallAdCategory>> responseVo = new BaseResponseVo();
        List<MallAdCategory> list = mallAdCategoryService.query(channelId, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallAdCategory mallAdCategory) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdCategoryService.add(mallAdCategory);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallAdCategory> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallAdCategory> responseVo = new BaseResponseVo();
        MallAdCategory mallAdCategory = mallAdCategoryService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallAdCategory);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallAdCategory mallAdCategory, @RequestParam Integer beforeSort){
        BaseResponseVo<Void> responseVo = new BaseResponseVo();
        boolean success = mallAdCategoryService.update(mallAdCategory, beforeSort);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallAdCategory mallAdCategory){
        BaseResponseVo<Void> responseVo = new BaseResponseVo();
        boolean success = mallAdCategoryService.delete(mallAdCategory);
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
