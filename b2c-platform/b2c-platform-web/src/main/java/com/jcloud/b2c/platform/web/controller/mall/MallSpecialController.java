package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.*;
import com.jcloud.b2c.mall.service.client.vo.MallAdFloorVo;
import com.jcloud.b2c.mall.service.client.vo.MallAdVo;
import com.jcloud.b2c.mall.service.client.vo.MallFloorVo;
import com.jcloud.b2c.platform.service.feign.MallAdFloorClient;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description:app首页专题
 * @author: YueZheng
 * @requireNo:
 * @createdate: 2017/3/14 14:32
 * @lasdate
 */
@Controller
@RequestMapping("/mall/mallSpecial")
public class MallSpecialController {

    @Autowired
    private MallAdFloorClient mallAdFloorClient;

    /**
     * 专题列表
     * @return
     */
    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public List<MallAdFloorVo> list(){
        MallAdFloorVo mallAdFloor = new MallAdFloorVo();
        Long tenantId = ControllerContext.getTenantId();
        mallAdFloor.setTenantId(tenantId);
        mallAdFloor.setClientType(ClientTypeEnum.ANDROID.getValue());
        mallAdFloor.getMallAd().setAdType(AdTypeEnum.SPECIAL.getValue());
        mallAdFloor.getMallFloor().setFloorType(FloorTypeEnum.SPECIAL.getValue());
        BaseResponseVo<List<MallAdFloorVo>> result = mallAdFloorClient.queryBySpecial(mallAdFloor);
        return result.getData();
    }

    /**
     * 添加专题
     * @param mallAdFloor
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean add (MallAdFloorVo mallAdFloor){
        Long tenantId = ControllerContext.getTenantId();
        mallAdFloor.setTenantId(tenantId);

        MallAdVo mallAd = mallAdFloor.getMallAd();
        mallAd.setLink(mallAd.getLink()==null?null:mallAd.getLink().trim());
        mallAd.setTenantId(tenantId);
        mallAd.setClientType(mallAdFloor.getClientType());
        mallAd.setState(StateEnum.ON_SHELF.getValue());

        MallFloorVo mallFloor = mallAdFloor.getMallFloor();
        mallFloor.setTenantId(tenantId);
        mallFloor.setClientType(mallAdFloor.getClientType());
        mallFloor.setSortNum(mallAdFloor.getSortNum());
        mallFloor.setState(StateEnum.ON_SHELF.getValue());

        BaseResponseVo<Void> result = mallAdFloorClient.addBySpecial(mallAdFloor);
        return result.isSuccess();
    }

    /**
     * 更新专题
     * @param mallAdFloor
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean update (MallAdFloorVo mallAdFloor){
        Long tenantId = ControllerContext.getTenantId();
        mallAdFloor.setTenantId(tenantId);

        MallAdVo mallAd = mallAdFloor.getMallAd();
        mallAd.setLink(mallAd.getLink()==null?null:mallAd.getLink().trim());
        mallAd.setId(mallAdFloor.getAdId());
        mallAd.setTenantId(tenantId);
        mallAd.setClientType(mallAdFloor.getClientType());
        mallAd.setState(StateEnum.ON_SHELF.getValue());

        MallFloorVo mallFloor = mallAdFloor.getMallFloor();
        mallFloor.setId(mallAdFloor.getFloorId());
        mallFloor.setTenantId(tenantId);
        mallFloor.setClientType(mallAdFloor.getClientType());
        mallFloor.setSortNum(mallAdFloor.getSortNum());
        mallFloor.setState(StateEnum.ON_SHELF.getValue());

        BaseResponseVo<Void> result = mallAdFloorClient.updateBySpecial(mallAdFloor);
        return result.isSuccess();
    }

    /**
     * 删除专题
     * @param mallAdFloor
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean delete(MallAdFloorVo mallAdFloor){
        Long tenantId = ControllerContext.getTenantId();
        mallAdFloor.setTenantId(tenantId);
        BaseResponseVo<Void> result = mallAdFloorClient.deleteBySpecial(mallAdFloor);
        return result.isSuccess();
    }

    /**
     * 获得专题
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public MallAdFloorVo get(@RequestParam Long id, @RequestParam Integer adType,@RequestParam Integer floorType,@RequestParam Integer clientType,@RequestParam Long channelId){
        Long tenantId = ControllerContext.getTenantId();
        MallAdFloorVo mallAdFloorVo = new MallAdFloorVo();
        MallAdVo mallAdVo = new MallAdVo();
        MallFloorVo mallFloorVo = new MallFloorVo();
        mallAdVo.setAdType(adType);
        mallFloorVo.setFloorType(floorType);
        mallFloorVo.setChannelId(channelId);

        mallAdFloorVo.setId(id);
        mallAdFloorVo.setTenantId(tenantId);
        mallAdFloorVo.setClientType(clientType);
        mallAdFloorVo.setMallAd(mallAdVo);
        mallAdFloorVo.setMallFloor(mallFloorVo);
        BaseResponseVo<List<MallAdFloorVo>> result = mallAdFloorClient.queryBySpecial(mallAdFloorVo);
        List<MallAdFloorVo> list = result.getData();
        MallAdFloorVo mallAdFloor = null;
        if (list.size()>0){
            mallAdFloor = list.get(0);
        }
        return mallAdFloor;
    }

}
