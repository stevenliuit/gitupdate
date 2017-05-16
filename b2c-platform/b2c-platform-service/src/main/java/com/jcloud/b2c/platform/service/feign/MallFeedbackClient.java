package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallFeedbackVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 意见反馈CLIENT
 * Created by hongyifei on 2017/3/14.
 */
@FeignClient(name="b2c-mall-service")
public interface MallFeedbackClient {

    @RequestMapping(value = "/mallFeedback/query", method = RequestMethod.POST)
    BaseResponseVo<List<MallFeedbackVo>> query(@RequestBody MallFeedbackVo mallFeedback);
}
