package com.jcloud.b2c.mall;

import com.jcloud.b2c.MallServiceApplication;
import com.jcloud.b2c.mall.service.domain.MallAppVersion;
import com.jcloud.b2c.mall.service.service.MallAppVersionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hongyifei on 2017/3/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(MallServiceApplication.class)
@DirtiesContext
@WebIntegrationTest
public class MallAppVersionTest {
    @Autowired
    private MallAppVersionService mallAppVersionService;

    @Test
    public void testQueryByplatform(){
        MallAppVersion mallAppVersion = mallAppVersionService.queryByPlatform(1);
        System.out.println(mallAppVersion);
    }
}
