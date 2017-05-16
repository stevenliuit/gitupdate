package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.*;
import com.jcloud.b2c.mall.service.client.vo.*;
import com.jcloud.b2c.platform.service.feign.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @description:pc首页管理
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/24 13:45
 * @lasdate
 */
@Controller
@RequestMapping("/mall/mallPcIndex")
public class MallPcIndexController {

    private static final Logger log = LoggerFactory.getLogger(MallPcIndexController.class);

    @Autowired
    private MallChannelClient mallChannelClient;

    @Autowired
    private MallBannerClient mallBannerClient;

    @Autowired
    private MallFloorClient mallFloorClient;

    @Autowired
    private MallIndexPageClient mallIndexPageClient;

    @Autowired
    private MallTopnewsArticleClient mallTopnewsArticleClient;

    @Autowired
    private MallAdClient mallAdClient;
    @Autowired
    private CdnDirClient cdnDirClient;

    @Autowired
    private MallAdRecommendClient mallAdRecommendClient;

    @Autowired
    private MallAdFloorClient mallAdFloorClient;

    /**
     * pc首页
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toManage" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String toManage(ModelMap modelMap){
        Long tenantId = ControllerContext.getTenantId();
        //频道列表
        MallChannelVo mallChannelVo = new MallChannelVo();
        mallChannelVo.setClientType(ClientTypeEnum.PC.getValue());
        mallChannelVo.setTenantId(tenantId);
        mallChannelVo.setState(StateEnum.ON_SHELF.getValue());
        mallChannelVo.setClientType(ClientTypeEnum.PC.getValue());
        BaseResponseVo<List<MallChannelVo>> channelResult = mallChannelClient.query(mallChannelVo);
        //轮播图
        MallBannerVo mallBannerVo = new MallBannerVo();
        mallBannerVo.setClientType(ClientTypeEnum.PC.getValue());
        mallBannerVo.setTenantId(tenantId);
        mallBannerVo.setChannelId(ChannelFieldEnum.DEFAULT.getValue());
        mallBannerVo.setBannerType(BannerTypeEnum.BANNER.getValue());
        mallBannerVo.setState(StateEnum.ON_SHELF.getValue());
        mallBannerVo.setClientType(ClientTypeEnum.PC.getValue());
        BaseResponseVo<List<MallBannerVo>> bannerResult = mallBannerClient.query(mallBannerVo);
        //推荐位广告
        mallBannerVo.setBannerType(BannerTypeEnum.ADSENSE.getValue());
        BaseResponseVo<List<MallBannerVo>> adBannerResult = mallBannerClient.query(mallBannerVo);
        //吸顶广告
        mallBannerVo.setBannerType(BannerTypeEnum.CEILING_ADSENSE.getValue());
        mallBannerVo.setClientType(ClientTypeEnum.PC.getValue());
        BaseResponseVo<List<MallBannerVo>> topBannerResult = mallBannerClient.query(mallBannerVo);
        //楼层
        MallFloorVo mallFloorVo = new MallFloorVo();
        mallFloorVo.setClientType(ClientTypeEnum.PC.getValue());
        mallFloorVo.setTenantId(tenantId);
        mallFloorVo.setFloorType(FloorTypeEnum.NORMAL.getValue());
        mallFloorVo.setChannelId(ChannelFieldEnum.DEFAULT.getValue());
        mallFloorVo.setClientType(ClientTypeEnum.PC.getValue());
        mallFloorVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallFloorVo>> floorResult = mallFloorClient.query(mallFloorVo);

        modelMap.addAttribute("channels", channelResult.getData());
        modelMap.addAttribute("banners",bannerResult.getData());
        modelMap.addAttribute("adBanners",adBannerResult.getData());
        modelMap.addAttribute("topBanner", CollectionUtils.isNotEmpty(topBannerResult.getData()) ? topBannerResult.getData().get(0) : null);
        modelMap.addAttribute("floors",floorResult.getData());
        return "pageManage/pcIndexManage";
    }

    /**
     * 预览首页
     * @return
     */
    @RequestMapping(value = "/viewPcIndexPage" ,method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView viewPcIndexPage() {
        Long tenantId = ControllerContext.getTenantId();
        String html = mallIndexPageClient.viewIndexPage(tenantId).getData();
        ModelAndView mv = new ModelAndView("pageManage/viewMallIndexPage");
        mv.addObject("html", html);
        return mv;
    }

    @RequestMapping(value = "/viewMIndexPage" ,method = {RequestMethod.GET, RequestMethod.POST})
    public void viewMIndexPage(HttpServletResponse response) throws IOException {
        Long tenantId = ControllerContext.getTenantId();
        String html = mallIndexPageClient.viewMIndexPage(tenantId).getData();
        response.setContentType("text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        OutputStream os = response.getOutputStream();
        os.write(html.getBytes("UTF-8"));
        os.flush();os.close();
    }

    /**
     * 生成首页
     * @return
     */
    @RequestMapping(value = "/createPcIndexPage" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> createPcIndexPage() {
        try{
            cdnDirClient.cdnPurgeAllDir();
        }catch (Exception e){
            log.error("createPcIndexPage cdnPurgeAllDir error",e);
        }
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo responseVo = mallIndexPageClient.createIndexPage(tenantId);
        Map<String, Object> result = new HashedMap();
        result.put("success", responseVo.getIsSuccess() ? 1 : -1);
        return result;

    }
    @RequestMapping(value = "/createMIndexPage" ,method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> createMIndexPage() {
        try{
            cdnDirClient.cdnPurgeAllDir();
        }catch (Exception e){
            log.error("createMIndexPage cdnPurgeAllDir error",e);
        }
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo responseVo = mallIndexPageClient.createMIndexPage(tenantId);
        Map<String, Object> result = new HashedMap();
        result.put("success", responseVo.getIsSuccess() ? 1 : -1);
        return result;

    }

    /**
     * 频道首页
     * @param channelId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toChannelManage" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String toChannelManage (@RequestParam Long channelId, ModelMap modelMap){
        Long tenantId = ControllerContext.getTenantId();
        MallBannerVo mallBannerVo = new MallBannerVo();
        mallBannerVo.setClientType(ClientTypeEnum.PC.getValue());
        mallBannerVo.setChannelId(channelId);
        mallBannerVo.setTenantId(tenantId);
        mallBannerVo.setState(StateEnum.ON_SHELF.getValue());
        //轮播图列表
        mallBannerVo.setBannerType(BannerTypeEnum.BANNER.getValue());
        BaseResponseVo<List<MallBannerVo>> banners = mallBannerClient.query(mallBannerVo);
        //推荐位列表
        mallBannerVo.setBannerType(BannerTypeEnum.ADSENSE.getValue());
        BaseResponseVo<List<MallBannerVo>> adBanners = mallBannerClient.query(mallBannerVo);
        //楼层列表
        MallFloorVo mallFloorVo = new MallFloorVo();
        mallFloorVo.setClientType(ClientTypeEnum.PC.getValue());
        mallFloorVo.setTenantId(tenantId);
        mallFloorVo.setChannelId(channelId);
        mallFloorVo.setFloorType(FloorTypeEnum.NORMAL.getValue());
        mallFloorVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallFloorVo>> floors = mallFloorClient.query(mallFloorVo);

        MallChannelVo mallChannelVo = mallChannelClient.get(channelId, tenantId).getData();
        modelMap.addAttribute("banners",banners.getData());
        modelMap.addAttribute("adBanners",adBanners.getData());
        modelMap.addAttribute("floors",floors.getData());
        modelMap.addAttribute("channelId",channelId);
        modelMap.addAttribute("channelCode", mallChannelVo.getHomeCode());
        return "/pageManage/channelIndexManage";
    }

    /**
     * app首页管理
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toAppManage" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String toAppManage (ModelMap modelMap){
        Long tenantId = ControllerContext.getTenantId();
        //app频道
        MallChannelVo mallChannelVo = new MallChannelVo();
        mallChannelVo.setTenantId(tenantId);
        mallChannelVo.setClientType(ClientTypeEnum.ANDROID.getValue());
        mallChannelVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallChannelVo>> channels = mallChannelClient.query(mallChannelVo);
        //头条管理
        MallTopnewsArticleVo mallTopnewsArticleVo = new MallTopnewsArticleVo();
        mallTopnewsArticleVo.setTenantId(tenantId);
        mallTopnewsArticleVo.setClientType(ClientTypeEnum.ANDROID.getValue());
        mallTopnewsArticleVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallTopnewsArticleVo>> topnews = mallTopnewsArticleClient.query(mallTopnewsArticleVo);
        //banner表
        MallBannerVo mallBannerVo = new MallBannerVo();
        mallBannerVo.setTenantId(tenantId);
        mallBannerVo.setClientType(ClientTypeEnum.ANDROID.getValue());
        mallBannerVo.setChannelId(ChannelFieldEnum.DEFAULT.getValue());
        mallBannerVo.setState(StateEnum.ON_SHELF.getValue());
        //轮播图
        mallBannerVo.setBannerType(BannerTypeEnum.BANNER.getValue());
        BaseResponseVo<List<MallBannerVo>> banners = mallBannerClient.query(mallBannerVo);
        //快捷入口
        mallBannerVo.setBannerType(BannerTypeEnum.SHORTCUT.getValue());
        BaseResponseVo<List<MallBannerVo>> shortcuts = mallBannerClient.query(mallBannerVo);
        //推荐位广告
        mallBannerVo.setBannerType(BannerTypeEnum.ADSENSE.getValue());
        BaseResponseVo<List<MallBannerVo>> adBanners = mallBannerClient.query(mallBannerVo);
        //专题管理xxxxxxx
        /*mallBannerVo.setBannerType(BannerTypeEnum.SPECIAL.getValue());
        BaseResponseVo<List<MallBannerVo>> specials = mallBannerClient.query(mallBannerVo);*/
        //app广告位
        mallBannerVo.setBannerType(BannerTypeEnum.APPAD.getValue());
        BaseResponseVo<List<MallBannerVo>> appads = mallBannerClient.query(mallBannerVo);
        //专题管理
        MallAdFloorVo mallAdFloor = new MallAdFloorVo();
        mallAdFloor.setMallAd(new MallAdVo());
        mallAdFloor.setMallFloor(new MallFloorVo());
        mallAdFloor.setTenantId(tenantId);
        mallAdFloor.setClientType(ClientTypeEnum.ANDROID.getValue());
        mallAdFloor.getMallAd().setAdType(AdTypeEnum.SPECIAL.getValue());
        mallAdFloor.getMallFloor().setFloorType(FloorTypeEnum.SPECIAL.getValue());
        mallAdFloor.getMallFloor().setChannelId(ChannelFieldEnum.DEFAULT.getValue());
        BaseResponseVo<List<MallAdFloorVo>> specials = mallAdFloorClient.queryBySpecial(mallAdFloor);

        modelMap.addAttribute("channels",channels.getData());
        modelMap.addAttribute("topnews",topnews.getData());
        modelMap.addAttribute("banners",banners.getData());
        modelMap.addAttribute("shortcuts",shortcuts.getData());
        modelMap.addAttribute("adBanners",adBanners.getData());
        modelMap.addAttribute("specials",specials.getData());
        modelMap.addAttribute("appads",appads.getData());
        return "/pageManage/appIndexManage";
    }

    @RequestMapping(value = "/toAppChannelManage" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String toAppChannelManage (@RequestParam Long channelId, ModelMap modelMap){
        Long tenantId = ControllerContext.getTenantId();
        MallBannerVo mallBannerVo = new MallBannerVo();
        mallBannerVo.setTenantId(tenantId);
        mallBannerVo.setClientType(ClientTypeEnum.ANDROID.getValue());
        mallBannerVo.setChannelId(channelId);
        mallBannerVo.setState(StateEnum.ON_SHELF.getValue());
        //轮播图
        mallBannerVo.setBannerType(BannerTypeEnum.BANNER.getValue());
        BaseResponseVo<List<MallBannerVo>> banners = mallBannerClient.query(mallBannerVo);
        //热门分类管理
        mallBannerVo.setBannerType(BannerTypeEnum.HOTCLASS.getValue());
        BaseResponseVo<List<MallBannerVo>> hotclasses = mallBannerClient.query(mallBannerVo);
        //专题管理
        MallAdFloorVo mallAdFloor = new MallAdFloorVo();
        mallAdFloor.setMallAd(new MallAdVo());
        mallAdFloor.setMallFloor(new MallFloorVo());
        mallAdFloor.setTenantId(tenantId);
        mallAdFloor.setClientType(ClientTypeEnum.ANDROID.getValue());
        mallAdFloor.getMallAd().setAdType(AdTypeEnum.SPECIAL.getValue());
        mallAdFloor.getMallFloor().setFloorType(FloorTypeEnum.SPECIAL.getValue());
        mallAdFloor.getMallFloor().setChannelId(channelId);
        BaseResponseVo<List<MallAdFloorVo>> specials = mallAdFloorClient.queryBySpecial(mallAdFloor);
        //品牌管理
        mallBannerVo.setBannerType(BannerTypeEnum.BRAND.getValue());
        BaseResponseVo<List<MallBannerVo>> brands = mallBannerClient.query(mallBannerVo);

        modelMap.addAttribute("banners",banners.getData());
        modelMap.addAttribute("hotclasses",hotclasses.getData());
        modelMap.addAttribute("specials",specials.getData());
        modelMap.addAttribute("brands",brands.getData());
        modelMap.addAttribute("channelId",channelId);
        return "/pageManage/appChannelIndexManage";
    }

    /**
     * m首页管理
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toMManage" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String toMManage (ModelMap modelMap){
        Long tenantId = ControllerContext.getTenantId();
        //头条管理
        MallTopnewsArticleVo mallTopnewsArticleVo = new MallTopnewsArticleVo();
        mallTopnewsArticleVo.setTenantId(tenantId);
        mallTopnewsArticleVo.setClientType(ClientTypeEnum.H5.getValue());
        mallTopnewsArticleVo.setState(StateEnum.ON_SHELF.getValue());
        BaseResponseVo<List<MallTopnewsArticleVo>> topnews = mallTopnewsArticleClient.query(mallTopnewsArticleVo);
        //banner表
        MallBannerVo mallBannerVo = new MallBannerVo();
        mallBannerVo.setTenantId(tenantId);
        mallBannerVo.setClientType(ClientTypeEnum.H5.getValue());
        mallBannerVo.setChannelId(ChannelFieldEnum.DEFAULT.getValue());
        mallBannerVo.setState(StateEnum.ON_SHELF.getValue());
        //轮播图
        mallBannerVo.setBannerType(BannerTypeEnum.BANNER.getValue());
        BaseResponseVo<List<MallBannerVo>> banners = mallBannerClient.query(mallBannerVo);
        //快捷入口
        mallBannerVo.setBannerType(BannerTypeEnum.SHORTCUT.getValue());
        BaseResponseVo<List<MallBannerVo>> shortcuts = mallBannerClient.query(mallBannerVo);

        //楼层
        MallAdFloorVo mallAdFloor = new MallAdFloorVo();
        mallAdFloor.setMallAd(new MallAdVo());
        mallAdFloor.setMallFloor(new MallFloorVo());
        mallAdFloor.setTenantId(tenantId);
        mallAdFloor.setClientType(ClientTypeEnum.H5.getValue());
        mallAdFloor.getMallAd().setAdType(AdTypeEnum.NORMAL.getValue());
        mallAdFloor.getMallFloor().setFloorType(FloorTypeEnum.NORMAL.getValue());
        mallAdFloor.getMallFloor().setChannelId(ChannelFieldEnum.DEFAULT.getValue());
        BaseResponseVo<List<MallAdFloorVo>> specials = mallAdFloorClient.queryBySpecial(mallAdFloor);


        //推荐位管理
        MallAdRecommendVo mallAdRecommendVo = new MallAdRecommendVo();
        mallAdRecommendVo.setTenantId(tenantId);
        mallAdRecommendVo.setRecommendType(RecommendTypeEnum.RECOMMEND.getValue());
        mallAdRecommendVo.setClientType(ClientTypeEnum.H5.getValue());
        BaseResponseVo<List<MallAdRecommendVo>> recommends = mallAdRecommendClient.query(mallAdRecommendVo);

        //通栏推荐位
        mallAdRecommendVo.setRecommendType(RecommendTypeEnum.FULLRECOMMEND.getValue());
        BaseResponseVo<List<MallAdRecommendVo>> fullRecommends = mallAdRecommendClient.query(mallAdRecommendVo);

        modelMap.addAttribute("topnews",topnews.getData());
        modelMap.addAttribute("banners",banners.getData());
        modelMap.addAttribute("shortcuts",shortcuts.getData());
        modelMap.addAttribute("specials",specials.getData());
        modelMap.addAttribute("recommends",recommends.getData());
        modelMap.addAttribute("fullRecommends",fullRecommends.getData());
        return "/pageManage/MIndexManage";
    }

    /**
     * app闪图管理
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/toAppFlashManage" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String toAppFlashManage (ModelMap modelMap){
        Long tenantId = ControllerContext.getTenantId();
        MallBannerVo mallBanner = new MallBannerVo();
        mallBanner.setTenantId(tenantId);
        mallBanner.setClientType(ClientTypeEnum.ANDROID.getValue());
        mallBanner.setBannerType(BannerTypeEnum.FLASH.getValue());
        mallBanner.setChannelId(ChannelFieldEnum.DEFAULT.getValue());
        mallBanner.setState(StateEnum.ON_SHELF.getValue());

        BaseResponseVo<List<MallBannerVo>> result = mallBannerClient.query(mallBanner);
        modelMap.addAttribute("flashs",result.getData());
        return "/pageManage/appFlashManage";
    }
}
