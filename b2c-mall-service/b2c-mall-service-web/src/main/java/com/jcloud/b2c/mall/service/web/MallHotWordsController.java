package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.domain.MallHotWords;
import com.jcloud.b2c.mall.service.service.MallHotWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/17 17:49
 * @lasdate
 */
@RestController
@RequestMapping("/mallHotWords")
public class MallHotWordsController {

    @Autowired
    private MallHotWordsService mallHotWordsService;

    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
    public BaseResponseVo<MallHotWords> getById(@RequestParam Long id, @RequestParam Long tenantId){
        BaseResponseVo<MallHotWords> responseVo = new BaseResponseVo<MallHotWords>();
        MallHotWords MallHotWords = mallHotWordsService.get(id, tenantId);
        responseVo.setIsSuccess(true);
        responseVo.setData(MallHotWords);
        return responseVo;
    }

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public BaseResponseVo<List<MallHotWords>> query(@RequestBody MallHotWords MallHotWords){
        BaseResponseVo<List<MallHotWords>> responseVo = new BaseResponseVo<List<MallHotWords>>();
        List<MallHotWords> list = mallHotWordsService.query(MallHotWords);
        responseVo.setIsSuccess(true);
        responseVo.setData(list);
        return responseVo;
    }

    @RequestMapping(value = "/update" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallHotWords MallHotWords){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallHotWordsService.update(MallHotWords);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallHotWords MallHotWords){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallHotWordsService.add(MallHotWords);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallHotWords MallHotWords){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallHotWordsService.delete(MallHotWords);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/updateSort" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> updateSort(@RequestParam Long id,
                                           @RequestParam Long changeId, @RequestParam Long tenantId){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallHotWordsService.updateSort(id, changeId, tenantId);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/selectMaxSort" ,method = RequestMethod.POST)
    public Integer selectMaxSort(@RequestBody MallHotWords MallHotWords){
        Integer maxSort = mallHotWordsService.selectMaxSort(MallHotWords);
        return maxSort;
    }

    @RequestMapping(value = "/updateToStick" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> updateToStick(@RequestBody MallHotWords MallHotWords){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallHotWordsService.updateToStick(MallHotWords);
        responseVo.setIsSuccess(success);
        return responseVo;
    }


    @RequestMapping(value = "/updateToFinally" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> updateToFinally(@RequestBody MallHotWords MallHotWords){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallHotWordsService.updateToFinally(MallHotWords);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/updateMoveUp" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> updateMoveUp(@RequestBody MallHotWords MallHotWords){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallHotWordsService.updateMoveUp(MallHotWords);
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/moveDown" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> moveDown(@RequestBody MallHotWords MallHotWords){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        boolean success = mallHotWordsService.updateMoveDown(MallHotWords);
        responseVo.setIsSuccess(success);
        return responseVo;
    }


}
