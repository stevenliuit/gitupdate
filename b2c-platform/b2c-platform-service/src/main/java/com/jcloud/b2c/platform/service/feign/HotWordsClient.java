package com.jcloud.b2c.platform.service.feign;

import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.MallCategoryVo;
import com.jcloud.b2c.mall.service.client.vo.MallHotWordsVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Administrator on 2017/2/25.
 */
@FeignClient(name = "b2c-mall-service")
public interface HotWordsClient {
    @RequestMapping(value = "/mallHotWords/query", method = POST)
    BaseResponseVo<List<MallHotWordsVo>> query(@RequestBody MallHotWordsVo mallHotWords);

    @RequestMapping(value = "/mallHotWords/delete", method = POST)
    BaseResponseVo<Void> delete(@RequestBody MallHotWordsVo mallHotWords);

    @RequestMapping(value = "/mallHotWords/add", method = POST)
    BaseResponseVo<Void> add(@RequestBody MallHotWordsVo mallHotWords);

    @RequestMapping(value = "/mallHotWords/update", method = POST)
    BaseResponseVo<Void> update(@RequestBody MallHotWordsVo mallHotWords);

    @RequestMapping(value = "/mallHotWords/selectMaxSort", method = POST)
    Integer selectMaxSort(@RequestBody MallHotWordsVo mallHotWords);

    @RequestMapping(value = "/mallHotWords/updateToStick", method = POST)
    BaseResponseVo<Void> updateToStick(@RequestBody MallHotWordsVo mallHotWords);

    @RequestMapping(value = "/mallHotWords/updateToFinally", method = POST)
    BaseResponseVo<Void> updateToFinally(@RequestBody MallHotWordsVo mallHotWords);

    @RequestMapping(value = "/mallHotWords/updateMoveUp", method = POST)
    BaseResponseVo<Void> updateMoveUp(@RequestBody MallHotWordsVo mallHotWords);

    @RequestMapping(value = "/mallHotWords/moveDown", method = POST)
    BaseResponseVo<Void> moveDown(@RequestBody MallHotWordsVo mallHotWords);

}
