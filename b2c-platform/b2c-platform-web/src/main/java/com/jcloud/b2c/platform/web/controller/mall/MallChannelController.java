package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.HomeCodeEnum;
import com.jcloud.b2c.mall.service.client.vo.MallChannelVo;
import com.jcloud.b2c.platform.service.feign.CdnDirClient;
import com.jcloud.b2c.platform.service.feign.MallChannelClient;
import com.jcloud.b2c.platform.service.feign.MallIndexPageClient;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @description:频道管理
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/27 13:59
 * @lasdate
 */
@Controller
@RequestMapping("/mall/mallChannel")
public class MallChannelController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MallAdController.class);

    @Autowired
    private MallChannelClient mallChannelClient;
    @Autowired
    private CdnDirClient cdnDirClient;

    @Autowired
    private MallIndexPageClient mallIndexPageClient;

    /**
     * 获取频道
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getChannel", method = {RequestMethod.POST})
    public MallChannelVo getChannel(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo<MallChannelVo> result = mallChannelClient.get(id, tenantId);
        return result.getData();
    }

    /**
     * 更新频道
     * @param mallChannelVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateChannel", method = {RequestMethod.POST})
    public boolean updateChannel(MallChannelVo mallChannelVo){
        Long tenantId = ControllerContext.getTenantId();
        mallChannelVo.setLink(mallChannelVo.getLink()==null?null:mallChannelVo.getLink().trim());
        mallChannelVo.setTenantId(tenantId);
        if (null == mallChannelVo.getHomeCode())
            mallChannelVo.setHomeCode(HomeCodeEnum.NORMAL.getValue());
        BaseResponseVo<Void> update = mallChannelClient.update(mallChannelVo);
        return update.isSuccess();
    }

    /**
     * 添加频道
     * @param mallChannelVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addChannel" ,method = {RequestMethod.POST})
    public boolean addChannel(MallChannelVo mallChannelVo){
        Long tenantId = ControllerContext.getTenantId();
        mallChannelVo.setLink(mallChannelVo.getLink()==null?null:mallChannelVo.getLink().trim());
        mallChannelVo.setTenantId(tenantId);
        if (null == mallChannelVo.getHomeCode())
            mallChannelVo.setHomeCode(HomeCodeEnum.NORMAL.getValue());
        BaseResponseVo<Void> result = mallChannelClient.add(mallChannelVo);
        return result.isSuccess();
    }

    /**
     * 删除频道
     */
    @ResponseBody
    @RequestMapping(value = "/deleteChannel" ,method = {RequestMethod.POST})
    public boolean deleteChannel(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        MallChannelVo mallChannelVo = new MallChannelVo();
        mallChannelVo.setTenantId(tenantId);
        mallChannelVo.setId(id);

        String homeCode = mallChannelClient.get(id, tenantId).getData().getHomeCode();
        BaseResponseVo<Void> result = mallChannelClient.delete(mallChannelVo);

        /**
         * 删除频道首页缓存
         */
        LOGGER.info("delete channel index page, tenantId={}, homeCode={}", tenantId, homeCode);
        mallIndexPageClient.deleteChannelIndexPageFromCache(tenantId, homeCode);
        LOGGER.info("delete channel index page success, tenantId={}, homeCode={}", tenantId, homeCode);
        return result.isSuccess();
    }

    /**
     * 预览频道首页
     * @param code
     * @return
     */
    @RequestMapping(value = "/viewIndexPage" ,method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView viewIndexPage(String code) {
        Long tenantId = ControllerContext.getTenantId();
        String html = mallIndexPageClient.viewChannelIndexPage(tenantId, code).getData();
        ModelAndView mv = new ModelAndView("pageManage/viewMallIndexPage");
        mv.addObject("html", html);
        return mv;
    }

    /**
     * 创建频道首页
     * @param code
     * @return
     */
    @RequestMapping(value = "/createIndexPage" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> createIndexPage(String code) {
        try{
            cdnDirClient.cdnPurgeAllDir();
        }catch (Exception e){
            LOGGER.error("createPcIndexPage cdnPurgeAllDir error",e);
        }
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo responseVo = mallIndexPageClient.createChannelIndexPage(tenantId, code);
        Map<String, Object> result = new HashedMap();
        result.put("success", responseVo.getIsSuccess() ? 1 : -1);
        return result;

    }
}
