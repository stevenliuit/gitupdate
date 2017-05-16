package com.jcloud.b2c.mall;

import com.jcloud.b2c.MallServiceApplication;
import com.jcloud.b2c.common.common.util.JacksonUtil;
import com.jcloud.b2c.mall.service.domain.MallFeedback;
import com.jcloud.b2c.mall.service.service.MallFeedbackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by hongyifei on 2017/3/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(MallServiceApplication.class)
@DirtiesContext
@WebIntegrationTest
public class MallFeedbackTest {

    @Autowired
    private MallFeedbackService mallFeedbackService;

    @Test
    public void testAdd(){
        MallFeedback mallFeedback = new MallFeedback();
        mallFeedback.setContent("反馈内容");
        mallFeedback.setContact("13800000001");
        mallFeedback.setClientType(2);
        mallFeedback.setTenantId(1L);
        mallFeedback.setUserId(1L);
        boolean result = mallFeedbackService.add(mallFeedback);
        System.out.println(result);
    }

    @Test
    public void testUpdate(){
        MallFeedback mallFeedback = new MallFeedback();
        mallFeedback.setId(1L);
        mallFeedback.setContent("test content222222");
        mallFeedback.setContact("139022201110000");
        boolean result = mallFeedbackService.update(mallFeedback);
        System.out.println(result);
    }

    @Test
    public void testQuery(){
        MallFeedback mallFeedback = new MallFeedback();
        mallFeedback.setUserId(1L);
        List<MallFeedback> result = mallFeedbackService.query(mallFeedback);
        System.out.println(JacksonUtil.convert(result));
    }

    @Test
    public void testDelete(){
        MallFeedback mallFeedback = new MallFeedback();
        mallFeedback.setId(1L);
        boolean result = mallFeedbackService.delete(mallFeedback);
        System.out.println(result);
    }

}
