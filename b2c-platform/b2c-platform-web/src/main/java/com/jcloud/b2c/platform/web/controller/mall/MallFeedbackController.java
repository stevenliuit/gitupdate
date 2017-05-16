package com.jcloud.b2c.platform.web.controller.mall;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.vo.PageInfo;
import com.jcloud.b2c.common.common.web.security.ControllerContext;
import com.jcloud.b2c.mall.service.client.enums.AdPrincipalTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.ClientTypeEnum;
import com.jcloud.b2c.mall.service.client.enums.YesNoEnum;
import com.jcloud.b2c.mall.service.client.vo.MallFeedbackVo;
import com.jcloud.b2c.platform.service.feign.MallFeedbackClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 意见反馈
 * Created by hongyifei on 2017/3/14.
 */
@Controller
@RequestMapping("/mall/feedback")
public class MallFeedbackController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MallAdController.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(MallFeedbackController.class);

    @Autowired
    private MallFeedbackClient mallFeedbackClient;

    private static Integer PAGE_SIZE = 10;

    @RequestMapping(value = "/main" ,method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request){
        LOGGER.info("进入意见反馈管理页面");
        ModelAndView modelAndView = new ModelAndView("mall/feedback/feedbackManage");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Map<String, Object> query(HttpServletRequest request,
                                     @RequestParam(value = "contact") String contact,
                                     @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize){
        LOGGER.info("查询意见反馈，contact={}", contact);
        Map<String, Object> data = new HashMap<String, Object>();
        //分页查询，关联数据
        MallFeedbackVo mallFeedback = new MallFeedbackVo();
//        mallFeedback.setUserId(ControllerContext.getUserId());
//        mallFeedback.setTenantId(ControllerContext.getTenantId());
        if (contact.trim().equals("")){
            mallFeedback.setContact(null);
        }else{
            mallFeedback.setContact(contact);
        }
        mallFeedback.setState(YesNoEnum.YES.getValue());
        mallFeedback.setPageIndex(pageIndex == null ? 0 : (pageIndex - 1));
        mallFeedback.setPageSize(pageSize == null ? PAGE_SIZE : pageSize);
        BaseResponseVo<List<MallFeedbackVo>> baseResponse = mallFeedbackClient.query(mallFeedback);
        if (!baseResponse.isSuccess()){
            LOGGER.info("意见反馈查询失败，contact={}", contact);
            data.put("isSuccess", false);
            return data;
        }
        List<MallFeedbackVo> mallFeedbackVoList = baseResponse.getData();
        if (mallFeedbackVoList != null && mallFeedbackVoList.size() > 0){
            LOGGER.info("意见反馈查询成功，contact={}", contact);
            PageInfo pageInfo = baseResponse.getPageInfo();
            data.put("totalCount",pageInfo.getTotalRecord());
            data.put("totalPages", pageInfo.getTotalPage());
            data.put("mallFeedbackList", mallFeedbackVoList);
            data.put("isSuccess", true);
        }else {
            LOGGER.info("意见反馈的返回列表为空，contact={}", contact);
            data.put("isSuccess", false);
        }
        return data;
    }

    private boolean baseValidate(String clientType){
        if(clientType == null || clientType.length()==0){
            return false;
        }
        ClientTypeEnum ct = ClientTypeEnum.getClientTypeByName(clientType);
        if(ct == null){
            return false;
        }
        return true;
    }

}
