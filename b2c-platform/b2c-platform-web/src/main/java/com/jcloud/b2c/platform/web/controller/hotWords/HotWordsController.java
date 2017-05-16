package com.jcloud.b2c.platform.web.controller.hotWords;


import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.vo.MallHotWordsVo;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/2/25.
 */

@Controller
@RequestMapping(value="/mall/hotWords")
public class HotWordsController {
    private static final Logger log = LoggerFactory.getLogger(HotWordsController.class);

    @Autowired
    private com.jcloud.b2c.platform.service.feign.HotWordsClient hotWordsClient;

    @RequestMapping(value = "/toHotWordsManage", method = {RequestMethod.GET, RequestMethod.POST})
    public String toHotWordsManage(ModelMap modelMap) {
        Long tenantId = ControllerContext.getTenantId();
        BaseResponseVo<List<MallHotWordsVo>> first = new BaseResponseVo<List<MallHotWordsVo>>();
        MallHotWordsVo hotWordsVo=new MallHotWordsVo();
        hotWordsVo.setTenantId(tenantId);
        hotWordsVo.setState(1);
        first=hotWordsClient.query(hotWordsVo);
        modelMap.addAttribute("hotWordsList", first.getData());
        return "hotWords/hotWordsManage";
    }
    @RequestMapping(value = "/delhotWordsById", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String delhotWordsById(HttpServletRequest httpRequest, ModelMap result, Long id,Integer sortNum) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        Map<String, Object> map = new HashMap<String, Object>();
        if (id == null) {
            map.put("success", false);
            map.put("msg", "参数有误，稍后再试");
            jsonObject.put("result", map);
            log.info(jsonObject.toString());
            return jsonObject.toString();
        }
        try {
            MallHotWordsVo mallHotWordsVo =new MallHotWordsVo();
            mallHotWordsVo.setTenantId(tenantId);
            mallHotWordsVo.setId(id);
            mallHotWordsVo.setSortNum(sortNum);
            BaseResponseVo baseResponseVo= hotWordsClient.delete(mallHotWordsVo);
            if (baseResponseVo.getIsSuccess()==true){
            map.put("success",true);
            map.put("msg", "删除热词成功啦");
            jsonObject.put("result",map);
            }
        } catch (Exception e) {
            log.error("删除单个热词controller调用service时出现异常。", e);
            map.put("success", false);
            map.put("msg", "删除热词失败");
            jsonObject.put("result", map);
        }

        log.info(jsonObject.toString());
        return jsonObject.toString();
    }

    @RequestMapping(value = "/addNewHotWords", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String addNewHotWords(HttpServletRequest httpRequest, String hotWordsName,int wordsType,int clientType) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            MallHotWordsVo mallHotWordsVo=new MallHotWordsVo();
            mallHotWordsVo.setName(hotWordsName);
            mallHotWordsVo.setTenantId(tenantId);
            mallHotWordsVo.setWordsType(wordsType);
            mallHotWordsVo.setCreated(new Date());
            mallHotWordsVo.setModified(new Date());
            mallHotWordsVo.setClientType(clientType);
            log.info(mallHotWordsVo.getName());
            Integer sort=hotWordsClient.selectMaxSort(mallHotWordsVo);
            if (sort==null){
                sort=0;
            }
            Integer sortNum=sort+1;
            mallHotWordsVo.setSortNum(sortNum);
            hotWordsClient.add(mallHotWordsVo);

            map.put("success", true);

        } catch (Exception e) {
            log.error("添加热词controller层调用异常", e);
            map.put("success", false);
            map.put("msg", "添加热词失败");
        }
        jsonObject.put("result", map);
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }
    @RequestMapping(value = "/updateHotWords", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String updateHotWords(HttpServletRequest httpRequest,Long id, String name,int wordsType,int clientType) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        MallHotWordsVo mallHotWordsVo = new MallHotWordsVo();
        mallHotWordsVo.setName(name);
        mallHotWordsVo.setId(id);
        mallHotWordsVo.setTenantId(tenantId);
        mallHotWordsVo.setWordsType(wordsType);
        mallHotWordsVo.setClientType(clientType);
        mallHotWordsVo.setModified(new Date());
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            hotWordsClient.update(mallHotWordsVo);
            map.put("success", true);
        } catch (Exception e) {
            log.error("修改热词controller层调用异常", e);
            map.put("success", false);
            map.put("msg", "修改热词失败");
        }
        jsonObject.put("result", map);
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 排序置顶
     *
     */
    @RequestMapping(value = "/toStick", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toStick(HttpServletRequest httpRequest,Long id,Integer sortNum) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        MallHotWordsVo mallHotWordsVo = new MallHotWordsVo();
        mallHotWordsVo.setId(id);
        mallHotWordsVo.setSortNum(sortNum);
        mallHotWordsVo.setTenantId(tenantId);
        mallHotWordsVo.setModified(new Date());
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            hotWordsClient.updateToStick(mallHotWordsVo);
            map.put("success", true);
        } catch (Exception e) {
            log.error("修改排序controller层调用异常", e);
            map.put("success", false);
            map.put("msg", "排序置顶失败");
        }
        jsonObject.put("result", map);
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 排序置尾
     */
    @RequestMapping(value = "/toFinally", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toFinally(HttpServletRequest httpRequest,Long id,Integer sortNum) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        MallHotWordsVo mallHotWordsVo = new MallHotWordsVo();
        mallHotWordsVo.setId(id);
        mallHotWordsVo.setSortNum(sortNum);
        mallHotWordsVo.setTenantId(tenantId);
        mallHotWordsVo.setModified(new Date());
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            hotWordsClient.updateToFinally(mallHotWordsVo);
            map.put("success", true);
        } catch (Exception e) {
            log.error("修改排序controller层调用异常", e);
            map.put("success", false);
            map.put("msg", "排序置顶失败");
        }
        jsonObject.put("result", map);
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 排序上移
     */
    @RequestMapping(value = "/moveUp", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String moveUp(HttpServletRequest httpRequest,Long id,Integer sortNum) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        MallHotWordsVo mallHotWordsVo = new MallHotWordsVo();
        mallHotWordsVo.setId(id);
        mallHotWordsVo.setSortNum(sortNum);
        mallHotWordsVo.setTenantId(tenantId);
        mallHotWordsVo.setModified(new Date());
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            hotWordsClient.updateMoveUp(mallHotWordsVo);
            map.put("success", true);
        } catch (Exception e) {
            log.error("修改排序controller层调用异常", e);
            map.put("success", false);
            map.put("msg", "排序上移失败");
        }
        jsonObject.put("result", map);
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * 排序下移
     */
    @RequestMapping(value = "/moveDown", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String moveDown(HttpServletRequest httpRequest,Long id,Integer sortNum) {
        JSONObject jsonObject = new JSONObject();
        Long tenantId = ControllerContext.getTenantId();
        log.info("tenantId：[" + tenantId + "]");
        MallHotWordsVo mallHotWordsVo = new MallHotWordsVo();
        mallHotWordsVo.setId(id);
        mallHotWordsVo.setSortNum(sortNum);
        mallHotWordsVo.setTenantId(tenantId);
        mallHotWordsVo.setModified(new Date());
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            hotWordsClient.moveDown(mallHotWordsVo);
            map.put("success", true);
        } catch (Exception e) {
            log.error("修改排序下移controller层调用异常", e);
            map.put("success", false);
            map.put("msg", "排序下移失败");
        }
        jsonObject.put("result", map);
        log.info(jsonObject.toString());
        return jsonObject.toString();
    }





}
