/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: EsItemServiceTest.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/4/6
 */
package com.jcloud.b2c.mall;

import com.jcloud.b2c.MallServiceApplication;
import com.jcloud.b2c.mall.service.client.vo.EsItemVo;
import com.jcloud.b2c.mall.service.service.EsItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-04-06 14:17
 * @lastdate:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(MallServiceApplication.class)
@DirtiesContext
@WebIntegrationTest
public class EsItemServiceTest {

    @Resource
    private EsItemService esItemService;

    private static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);

    @Test
    public void testAdd(){EsItemVo esItemVo = new EsItemVo();
        esItemVo.setItemId(1294638L);
        esItemVo.setCat1Id(99L);
        esItemVo.setCat1Name("服装-小五");
        esItemVo.setCat2Id(105L);
        esItemVo.setCat2Name("T恤");
        esItemVo.setCat3Id(107L);
        esItemVo.setCat3Name("毛衫");
        esItemVo.setAppCat1Id(21L);
        esItemVo.setAppCat1Name("APP一级类目");
        esItemVo.setAppCat2Id(169L);
        esItemVo.setAppCat2Name("测试1");
        esItemVo.setAppCatString("169:测试1;21:APP一级类目;");
        esItemVo.setCategoryString("107:毛衫;105:T恤;99:服装-小五;");
        esItemVo.setItemName("尼康（Nikon）D7000(18-200)+尼康AF-S 50mm f/1.8G 镜头");
        esItemVo.setBrandName("尼康");
        esItemVo.setBrandId(13392L);
        esItemVo.setState(1);
        esItemService.putIntoEs(1L, esItemVo, 1);
        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                esItemService.putIntoEs(1L, esItemVo, 1);
            }
        });
        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                esItemService.putIntoEs(1L, esItemVo, 1);
            }
        });
        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                esItemService.putIntoEs(1L, esItemVo, 1);
            }
        });
        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                esItemService.putIntoEs(1L, esItemVo, 1);
            }
        });
        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                esItemService.putIntoEs(1L, esItemVo, 1);
            }
        });

    }
}
