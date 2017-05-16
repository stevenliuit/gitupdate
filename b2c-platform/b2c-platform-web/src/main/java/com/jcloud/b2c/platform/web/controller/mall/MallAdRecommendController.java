package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.dictionary.ImageSizeEnum;
import com.jcloud.b2c.common.common.util.ImageUtils;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.item.client.vo.item.ItemDetailVo;
import com.jcloud.b2c.mall.service.client.enums.AdTypeEnum;
import com.jcloud.b2c.mall.service.client.vo.MallAdRecommendVo;
import com.jcloud.b2c.mall.service.client.vo.MallAdVo;
import com.jcloud.b2c.platform.service.feign.ItemClient;
import com.jcloud.b2c.platform.service.feign.MallAdRecommendClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:推荐位controller
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/13 11:26
 * @lasdate
 */
@Controller
@RequestMapping("/mall/MallAdRecommend")
public class MallAdRecommendController {

    @Autowired
    private MallAdRecommendClient mallAdRecommendClient;

    @Autowired
    private ItemClient itemClient;

    /**
     * 增加推荐位
     * @param mallAdRecommendVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public boolean add(MallAdRecommendVo mallAdRecommendVo){
        Long tenantId = ControllerContext.getTenantId();
        Long shopId = ControllerContext.getShopId();

        mallAdRecommendVo.setTenantId(tenantId);
        MallAdVo mallAd = mallAdRecommendVo.getMallAd();
        mallAd.setTenantId(tenantId);
        mallAd.setClientType(mallAdRecommendVo.getClientType());

        BaseResponseVo<ItemDetailVo> itemDetail = itemClient.getItemDetail(tenantId, shopId, mallAd.getItemId());
        ItemDetailVo item = itemDetail.getData();

        if (1==mallAdRecommendVo.getRecommendType()){
            mallAd.setImgSrc(ImageUtils.getImageUrl4Jfs(item.getImgUrl(), ImageSizeEnum.IMAGE_SIZE_N2.getCode()));
            mallAd.setAdWords(mallAd.getAdWords()+";"+ item.getPrice());
        } else if (2==mallAdRecommendVo.getRecommendType()){
            mallAd.setAdWords(item.getSkuName()+";"+ item.getPrice());
        }

        BaseResponseVo<Void> result = mallAdRecommendClient.add(mallAdRecommendVo);
        return result.isSuccess();
    }

    /**
     * 更新并拉取sku
     * @param mallAdRecommendVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.GET, RequestMethod.POST})
    public boolean update(MallAdRecommendVo mallAdRecommendVo){
        Long tenantId = ControllerContext.getTenantId();
        Long shopId = ControllerContext.getShopId();

        mallAdRecommendVo.setTenantId(tenantId);
        MallAdVo mallAd = mallAdRecommendVo.getMallAd();
        mallAd.setTenantId(tenantId);
        mallAd.setClientType(mallAdRecommendVo.getClientType());
        mallAd.setId(mallAdRecommendVo.getAdId());

        BaseResponseVo<ItemDetailVo> itemDetail = itemClient.getItemDetail(tenantId, shopId, mallAd.getItemId());
        ItemDetailVo item = itemDetail.getData();

        if (1==mallAdRecommendVo.getRecommendType()){
            mallAd.setImgSrc(ImageUtils.getImageUrl4Jfs(item.getImgUrl(), ImageSizeEnum.IMAGE_SIZE_N2.getCode()));
            mallAd.setAdWords(mallAd.getAdWords()+";"+ item.getPrice());
        } else if (2==mallAdRecommendVo.getRecommendType()){
            mallAd.setAdWords(item.getSkuName()+";"+ item.getPrice());
        }

        BaseResponseVo<Void> result = mallAdRecommendClient.update(mallAdRecommendVo);
        return result.isSuccess();
    }

    /**
     * 获取推荐实体
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    public MallAdRecommendVo get(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo<MallAdRecommendVo> result = mallAdRecommendClient.get(id, tenantId);
        return  result.getData();
    }
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public boolean delete(@RequestParam Long id){
        MallAdRecommendVo mallAdRecommendVo=new MallAdRecommendVo();
        mallAdRecommendVo.setId(id);
        Long tenantId = ControllerContext.getTenantId();
        mallAdRecommendVo.setTenantId(tenantId);
        BaseResponseVo<Void> result = mallAdRecommendClient.delete(mallAdRecommendVo);
        return result.isSuccess();
    }
}
