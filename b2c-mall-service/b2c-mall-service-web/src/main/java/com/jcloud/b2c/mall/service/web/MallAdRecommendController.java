package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallAdRecommend;
import com.jcloud.b2c.mall.service.mapper.MallAdRecommendMapper;
import com.jcloud.b2c.mall.service.service.MallAdRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/11 18:20
 * @lasdate
 */
@RestController
@RequestMapping("/MallAdRecommend")
public class MallAdRecommendController {

    @Autowired
    private MallAdRecommendService mallAdRecommendService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallAdRecommend> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallAdRecommend> responseVo = new BaseResponseVo<MallAdRecommend>();
        MallAdRecommend mallAdRecommend = mallAdRecommendService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(mallAdRecommend);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallAdRecommend>> query(@RequestBody MallAdRecommend mallAdRecommend){
        BaseResponseVo<List<MallAdRecommend>> responseVo = new BaseResponseVo<List<MallAdRecommend>>();
        List<MallAdRecommend> list = mallAdRecommendService.queryWithMallAd(mallAdRecommend);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallAdRecommend mallAdRecommend){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdRecommendService.update(mallAdRecommend);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallAdRecommend mallAdRecommend){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdRecommendService.add(mallAdRecommend);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallAdRecommend mallAdRecommend){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallAdRecommendService.delete(mallAdRecommend);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

}
