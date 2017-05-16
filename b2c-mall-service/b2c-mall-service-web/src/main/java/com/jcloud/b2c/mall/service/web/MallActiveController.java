package com.jcloud.b2c.mall.service.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallActiveVo;
import com.jcloud.b2c.mall.service.domain.MallActive;
import com.jcloud.b2c.mall.service.service.MallActiveService;
import com.jcloud.b2c.mall.service.service.feign.CacheFeignClient;
import com.jcloud.b2c.mall.service.service.feign.OssClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * liuhao
 * Created by issuser on 2017/3/3.
 * 活动页Controller
 */
@RestController
@RequestMapping("/mallActive")
public class MallActiveController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MallActiveController.class);
    @Autowired
    private MallActiveService mallActiveService;

    @Autowired
    private OssClient ossClient;

    @Autowired
    private CacheFeignClient cacheFeignClient;

    private static int EXPIRE = 0;
    private static String ENCODING = "UTF-8";
    private static String B2C_ACTIVE_KEY = "B2C_Active_Key_";
    private static Cache<String, String> CACHE = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(10, TimeUnit.SECONDS).build();

//    @RequestMapping(value = "/get" ,method = RequestMethod.GET)
//    public BaseResponseVo<MallActiveVo> getById(@RequestParam Long id, @RequestParam Long tenantId){
//        BaseResponseVo<MallActiveVo> responseVo = new BaseResponseVo<MallActiveVo>();
//        MallActive mallActive = mallActiveService.get(id, tenantId);
//        MallActiveVo mallActiveVo=new MallActiveVo();
//        BeanUtils.copyProperties(mallActive, mallActiveVo);
//        Long platformId=tenantId;
//        String savePath =mallActive.getContentUrl();
//        String str= ossClient.getTxtContent(platformId,savePath);//获得一个html页面
//        mallActiveVo.setHtmlStr(str);
//        responseVo.setIsSuccess(true);
//        responseVo.setData(mallActiveVo);
//        return responseVo;
//    }

    @RequestMapping(value = "/getByCode", method = RequestMethod.POST)
    public BaseResponseVo<MallActiveVo> getById(@RequestBody MallActiveVo mallActive) {
        BaseResponseVo<MallActiveVo> responseVo = new BaseResponseVo<MallActiveVo>();
        String cacheActive = "";
        String code = mallActive.getCode();
        Long tenantId = mallActive.getTenantId();
        try {
            cacheActive = CACHE.get(B2C_ACTIVE_KEY + code + "_" + tenantId, new Callable<String>() {
                public String call() {
                    return cacheFeignClient.get(tenantId, B2C_ACTIVE_KEY + code + "_" + tenantId);
                }
            });
        } catch (Exception e) {
            LOGGER.error("获取缓存内容失败", e);
        }
        if (cacheActive != null && cacheActive != "") {
            JSONObject activeObj = JSON.parseObject(cacheActive);
            Integer isHead = Integer.valueOf(activeObj.get("isHead").toString());
            String activePage = String.valueOf(activeObj.get("data"));
            mallActive.setHtmlStr(activePage);
            mallActive.setIsHead(isHead);
            responseVo.setIsSuccess(true);
            responseVo.setData(mallActive);
        } else {
            responseVo.setIsSuccess(false);
        }
        return responseVo;
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public BaseResponseVo<List<MallActiveVo>> query(@RequestBody MallActive mallActive) {
        BaseResponseVo<List<MallActiveVo>> responseVo = new BaseResponseVo<List<MallActiveVo>>();
        List<MallActiveVo> mallActiveVoList = new ArrayList<MallActiveVo>();
        List<MallActive> list = mallActiveService.query(mallActive);
        for (MallActive active : list) {
            MallActiveVo mallActiveVo = new MallActiveVo();
            BeanUtils.copyProperties(active, mallActiveVo);
            mallActiveVoList.add(mallActiveVo);
        }
        responseVo.setIsSuccess(true);
        responseVo.setData(mallActiveVoList);
        return responseVo;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallActive mallActive) {

        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        Long platformId = mallActive.getTenantId();
        String content = mallActive.getHtmlStr();
        try {
            String contentUrl = ossClient.uploadTxtInternal(platformId, content).get("internet");
            mallActive.setContentUrl(contentUrl);
        } catch (Exception e) {
            LOGGER.error("添加失败", e);
        }
        boolean success = mallActiveService.add(mallActive);
        //key值为B2C_Active_Key_Code_租户ID
        String key = B2C_ACTIVE_KEY + mallActive.getCode() + "_" + platformId;
        JSONObject activeJson = new JSONObject();
        try {
            activeJson.put("data", content);
            activeJson.put("isHead", mallActive.getIsHead());
            byte[] ActiveArray = activeJson.toString().getBytes(ENCODING);
            cacheFeignClient.saveBytes2Cache(platformId, key, ActiveArray, EXPIRE);
        } catch (Exception e) {
            LOGGER.error("缓存活动页失败", e);
        }
        responseVo.setIsSuccess(success);
        return responseVo;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallActive mallActive) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        Long platformId = mallActive.getTenantId();
        String content = mallActive.getHtmlStr();
        try {
            String contentUrl = ossClient.uploadTxtInternal(platformId, content).get("internet");
            mallActive.setContentUrl(contentUrl);
        } catch (Exception e) {
            LOGGER.error("添加失败", e);
        }
        boolean success = mallActiveService.update(mallActive);
        String key = B2C_ACTIVE_KEY + mallActive.getCode() + "_" + platformId;
        JSONObject activeJson = new JSONObject();
        try {
            activeJson.put("data", content);
            activeJson.put("isHead", mallActive.getIsHead());
            byte[] ActiveArray = activeJson.toString().getBytes(ENCODING);
            cacheFeignClient.saveBytes2Cache(platformId, key, ActiveArray, EXPIRE);
        } catch (Exception e) {
            LOGGER.error("缓存活动页失败", e);
        }
        responseVo.setIsSuccess(success);
        return responseVo;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallActive mallActive) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo<Void>();
        Long platformId = mallActive.getTenantId();
        boolean success = mallActiveService.delete(mallActive);
        String key = B2C_ACTIVE_KEY + mallActive.getCode() + "_" + platformId;
        try {
            cacheFeignClient.deleteObjKey(mallActive.getTenantId(), key);
        } catch (Exception e) {
            LOGGER.error("删除活动页失败", e);
        }
        responseVo.setIsSuccess(success);
        return responseVo;
    }
}
