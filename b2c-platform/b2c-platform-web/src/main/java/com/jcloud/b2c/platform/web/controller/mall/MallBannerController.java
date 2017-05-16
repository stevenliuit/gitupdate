package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.ChannelFieldEnum;
import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.vo.MallBannerVo;
import com.jcloud.b2c.platform.service.feign.MallBannerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @description:
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/2/27 20:20
 * @lasdate
 */
@Controller
@RequestMapping("/mall/mallBanner")
public class MallBannerController {

    @Autowired
    private MallBannerClient mallBannerClient;

    @ResponseBody
    @RequestMapping(value = "/getBanner", method = {RequestMethod.POST})
    public MallBannerVo getBanner(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo<MallBannerVo> result = mallBannerClient.get(id,tenantId);
        return result.getData();
    }

    @ResponseBody
    @RequestMapping(value = "/updateBanner", method = {RequestMethod.POST})
    public boolean updateBanner(MallBannerVo mallBannerVo){
        Long tenantId = ControllerContext.getTenantId();
        mallBannerVo.setLink(mallBannerVo.getLink()==null?null:mallBannerVo.getLink().trim());
        mallBannerVo.setTenantId(tenantId);
        Long channelId = mallBannerVo.getChannelId();
        mallBannerVo.setChannelId(channelId==null?ChannelFieldEnum.DEFAULT.getValue():channelId);
        BaseResponseVo<Void> update = mallBannerClient.update(mallBannerVo);
        return update.isSuccess();
    }

    /**
     * 新建banner
     * @param mallBannerVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBanner" ,method = {RequestMethod.POST})
    public boolean addBanner(MallBannerVo mallBannerVo){
        Long tenantId = ControllerContext.getTenantId();
        mallBannerVo.setLink(mallBannerVo.getLink()==null?null:mallBannerVo.getLink().trim());
        mallBannerVo.setTenantId(tenantId);
        Long channelId = mallBannerVo.getChannelId();
        mallBannerVo.setChannelId(channelId==null?ChannelFieldEnum.DEFAULT.getValue():channelId);
        Integer clientType = mallBannerVo.getClientType();
        mallBannerVo.setClientType(clientType==null?ClientTypeEnum.PC.getValue():clientType);
        BaseResponseVo<Void> result = mallBannerClient.add(mallBannerVo);
        return result.isSuccess();
    }

    /**
     * 删除banner
     */
    @ResponseBody
    @RequestMapping(value = "/deleteBanner" ,method = {RequestMethod.POST})
    public boolean deleteBanner(@RequestParam Long id){
        Long tenantId = ControllerContext.getTenantId();
        MallBannerVo mallBannerVo = new MallBannerVo();
        mallBannerVo.setTenantId(tenantId);
        mallBannerVo.setId(id);
        BaseResponseVo<Void> result = mallBannerClient.delete(mallBannerVo);
        return result.isSuccess();
    }

    /**
     * 验证闪图开始结束时间规则
     */
    @ResponseBody
    @RequestMapping(value = "/checkByBeginTime" ,method = {RequestMethod.POST})
    public boolean checkByBeginTime(MallBannerVo mallBanner, Integer type){
        Long tenantId = ControllerContext.getTenantId();
        mallBanner.setTenantId(tenantId);
        Date beginTime = mallBanner.getBeginTime();
        Date endTime = mallBanner.getEndTime();
        boolean flag = false;
        if (type==1){
            Date now = new Date();
            //验证晚于最后时间
            //BaseResponseVo<Void> result = mallBannerClient.checkByBeginTime(mallBanner);
            //result.isSuccess()&&
            if (beginTime.getTime()>now.getTime()){
                flag = true;
            }
        } else if (type==2){
            if (beginTime!=null&&beginTime.getTime()<endTime.getTime()){
                flag = true;
            }
        }
        return flag;
    }
}
