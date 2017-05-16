package com.jcloud.b2c.mall.service.web;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jcloud.b2c.common.common.vo.BaseResponseVo;
import com.jcloud.b2c.mall.service.client.vo.EsItemVo;
import com.jcloud.b2c.mall.service.domain.MallPrincipalItem;
import com.jcloud.b2c.mall.service.service.EsItemService;
import com.jcloud.b2c.mall.service.service.impl.EsItemServiceImpl;
import com.jd.ump.profiler.CallerInfo;
import com.jd.ump.profiler.proxy.Profiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * liuhao
 * Created by issuser on 2017/3/2.
 *
 */
@RestController
@RequestMapping("/esItem")
public class EsItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsItemController.class);

    @Autowired
    private EsItemService esItemService;

    @RequestMapping(value = "/putIntoEs" ,method = RequestMethod.GET)
    public BaseResponseVo<Void> putIntoEs(@RequestParam Long itemId, @RequestParam Long tenantId) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo();
        CallerInfo callerInfo = null;
        try {
            callerInfo = Profiler.registerInfo("com.jcloud.b2c.mall.service.web.EsItemController.putIntoEs", false, true);
            esItemService.putIntoEs(itemId, tenantId);
            responseVo.setIsSuccess(true);
        }catch (Exception e) {
            Profiler.functionError(callerInfo);
            LOGGER.error("loginBack error:", e);
            throw  new RuntimeException(e);
        } finally {
            Profiler.registerInfoEnd(callerInfo);
        }
        return responseVo;
    }

    @RequestMapping(value = "/batchPutIntoEs" ,method = RequestMethod.GET)
    public BaseResponseVo<Void> batchPutIntoEs(@RequestParam List<Long> itemIds, @RequestParam Long tenantId) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo();
        for (Long itemId : itemIds) {
            try {
                esItemService.putIntoEs(itemId, tenantId);
            } catch (Exception e) {
                LOGGER.error("batchPutIntoEs error, itemId=" + itemId + ", tenantId=" + tenantId, e);
            }
        }
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/putIntoEsTask" ,method = RequestMethod.POST)
    public BaseResponseVo<Void> putIntoEsTask(@RequestBody List<Long> itemIds, @RequestParam Long tenantId) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo();
        CallerInfo callerInfo = null;
        try {
            callerInfo = Profiler.registerInfo("com.jcloud.b2c.mall.service.web.EsItemController.putIntoEsTask", false, true);
            esItemService.putIntoEsTask(tenantId, itemIds);
            responseVo.setIsSuccess(true);
        }catch (Exception e) {
            Profiler.functionError(callerInfo);
            LOGGER.error("loginBack error:", e);
            throw  new RuntimeException(e);
        } finally {
            Profiler.registerInfoEnd(callerInfo);
        }
        return responseVo;
    }

    @RequestMapping(value = "/putJsonMapping" ,method = RequestMethod.GET)
    public BaseResponseVo<Void> putJsonMapping(@RequestParam Long tenantId) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo();
        esItemService.putJsonMapping(tenantId);
        responseVo.setIsSuccess(true);
        return responseVo;
    }

    @RequestMapping(value = "/deleteIndex" ,method = RequestMethod.GET)
    public BaseResponseVo<Void> deleteIndex(@RequestParam Long tenantId) {
        BaseResponseVo<Void> responseVo = new BaseResponseVo();
        esItemService.deleteIndex(tenantId);
        responseVo.setIsSuccess(true);
        return responseVo;
    }

}
