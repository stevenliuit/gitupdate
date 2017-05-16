package com.jcloud.b2c.platform.web.controller.superAdmin;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.platform.service.feign.CacheFeignClient;
import com.jcloud.b2c.platform.service.feign.MallIndexPageClient;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by shiyusen on 2017/3/29.
 */
@Controller
@RequestMapping("/super/cache")
public class CacheManagerController {

    private static Logger logger= Logger.getLogger(CacheManagerController.class);

    @Resource
    private CacheFeignClient cacheFeignClient;

    @Autowired
    private MallIndexPageClient mallIndexPageClient;

    @RequestMapping(value = "/index",method = {RequestMethod.GET})
    public String index(Model model){
        Long tenantID = ControllerContext.getTenantId();
        List<String> keys= mallIndexPageClient.getCacheKey(tenantID);
        model.addAttribute("keys",keys);
        return "/super/cache";
    }


    @ResponseBody
    @RequestMapping(value = "/save",method = {RequestMethod.GET,RequestMethod.POST})
    public BaseResponseVo<Void> saveCahce(@RequestParam Long tenantId, @RequestParam String key,@RequestParam String value, @RequestParam int expireSecond){
        try {
            boolean sv = cacheFeignClient.saveBytes2Cache(tenantId, key, value.getBytes("UTF-8"), expireSecond);
            if(sv){
                return new BaseResponseVo();
            }
        } catch (Exception e) {
            logger.error("缓存失败，key: "+key+"   value:"+value,e);
        }
        return new BaseResponseVo(false,"缓存失败");
    }
    @ResponseBody
    @RequestMapping(value = "/delete",method = {RequestMethod.GET})
    public BaseResponseVo<Void> deleteCahce(@RequestParam Long tenantId, @RequestParam String key){
        try {
            boolean sv = cacheFeignClient.del(tenantId, key);
            if(sv){
                return new BaseResponseVo();
            }
        } catch (Exception e) {
            logger.error("删除缓存失败，key: "+key,e);
        }
        return new BaseResponseVo(false,"删除缓存失败");
    }

    @ResponseBody
    @RequestMapping(value = "/select",method = {RequestMethod.GET})
    public BaseResponseVo<String> selectCahce(@RequestParam Long tenantId, @RequestParam String key){
        try {
            String html = cacheFeignClient.get(tenantId, key);
            if(!StringUtils.isEmpty(html)){
                BaseResponseVo baseResponseVo = new BaseResponseVo(true);
                baseResponseVo.setData(html);
                return baseResponseVo;
            }
        } catch (Exception e) {
            logger.error("查询缓存异常，key: "+key,e);
        }
        return new BaseResponseVo<String>(false,"缓存不存在或已过期");
    }
    @ResponseBody
    @RequestMapping(value = "/clean",method = {RequestMethod.GET})
    public BaseResponseVo<String> clean(@RequestParam Long tenantId, @RequestParam String key){
         return new BaseResponseVo<String>(false,"缓存不存在或已过期");

    }
}
