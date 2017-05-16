package com.jcloud.b2c.mall.service.web;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.common.common.vo.PageInfo;
import com.jcloud.b2c.mall.service.domain.MallFeedback;
import com.jcloud.b2c.mall.service.service.MallFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 意见反馈Controller
 * Created by hongyifei on 2017/3/10.
 */
@RestController
@RequestMapping("/mallFeedback")
public class MallFeedbackController {
    @Autowired
    private MallFeedbackService mallFeedbackService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResponseVo<Void> add(@RequestBody MallFeedback mallFeedback){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<>();
        boolean isSuccess = mallFeedbackService.add(mallFeedback);
        responseVo.setIsSuccess(isSuccess);
        return responseVo;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BaseResponseVo<Void> update(@RequestBody MallFeedback mallFeedback){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<>();
        boolean isSuccess = mallFeedbackService.update(mallFeedback);
        responseVo.setIsSuccess(isSuccess);
        return responseVo;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResponseVo<Void> delete(@RequestBody MallFeedback mallFeedback){
        BaseResponseVo<Void> responseVo = new BaseResponseVo<>();
        boolean isSuccess = mallFeedbackService.delete(mallFeedback);
        responseVo.setIsSuccess(isSuccess);
        return responseVo;
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public BaseResponseVo<List<MallFeedback>> query(@RequestBody MallFeedback mallFeedback){
        BaseResponseVo<List<MallFeedback>> responseVo = new BaseResponseVo<>();
        mallFeedback.setOffset(mallFeedback.getPageIndex() * mallFeedback.getPageSize());
        List<MallFeedback> feedbackList = mallFeedbackService.query(mallFeedback);
        Integer totalCount = mallFeedbackService.queryCount(mallFeedback);
        Integer pageSize = mallFeedback.getPageSize();
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPage(mallFeedback.getPageIndex());
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotalRecord(totalCount);
        pageInfo.setTotalPage((totalCount - 1)/pageSize + 1);
        responseVo.setPageInfo(pageInfo);
        responseVo.setData(feedbackList);
        responseVo.setIsSuccess(true);
        return responseVo;
    }
}
