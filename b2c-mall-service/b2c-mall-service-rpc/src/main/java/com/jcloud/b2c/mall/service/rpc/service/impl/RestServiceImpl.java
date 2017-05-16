/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: RestServiceImpl.java project: jcloud-b2c-user-service
 * @creator: wangxin17
 * @date: 2017/2/10
 */
package com.jcloud.b2c.mall.service.rpc.service.impl;

import com.jcloud.b2c.common.common.util.JacksonUtil;
import com.jcloud.b2c.mall.service.rpc.model.BaseRpcReq;
import com.jcloud.b2c.mall.service.rpc.model.BaseRpcRes;
import com.jcloud.b2c.mall.service.rpc.service.RestService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-10 18:23
 * @lastdate:
 */
@Service("restService")
public class RestServiceImpl implements RestService {

//    private final static Log LOG = LogFactory.getLog(RestServiceImpl.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceImpl.class);

    @Resource
    private RestTemplate restTemplate;

    @Override
    public <R> BaseRpcRes<R> post(Class<R> returnType, String path, BaseRpcReq req) {
        LOGGER.info("post returnType={}, path={}, req={}", new Object[]{returnType, path, req});
        restTemplate.setErrorHandler(new MyResponseErrorHandler());
        ResponseEntity<R> responseEntity = restTemplate.postForEntity(path, req, returnType);
        BaseRpcRes<R> res = new BaseRpcRes();
        LOGGER.info("response body={}", responseEntity.getBody());
        res.setResult(responseEntity.getBody());
        return res;
    }

    @Override
    public <R> BaseRpcRes<R> get(Class<R> returnType, String path) {
        LOGGER.info("post returnType={}, path={}", new Object[]{returnType, path});
        restTemplate.setErrorHandler(new MyResponseErrorHandler());
        ResponseEntity<R> responseEntity = restTemplate.getForEntity(path, returnType);
        BaseRpcRes<R> res = new BaseRpcRes();
        LOGGER.info("response body={}", responseEntity.getBody());
        res.setResult(responseEntity.getBody());
        return res;
    }

    public <R> BaseRpcRes<R> get(Class<R> returnType, String path, BaseRpcReq req) {
        LOGGER.info("get returnType={}, path={}, req={}", new Object[]{returnType, path, req});
        String json = JacksonUtil.convert(req);
        Map<String, Object> pars = JacksonUtil.parse(json, Map.class);
        ResponseEntity<R> responseEntity = restTemplate.getForEntity(path, returnType, pars);
        BaseRpcRes<R> res = new BaseRpcRes();
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            res.setResult(responseEntity.getBody());
        }
        return res;
    }

    @Override
    public <R> BaseRpcRes<R> get(Class<R> returnType, String path, Map<String, Object> req) {
        LOGGER.info("get returnType={}, path={}, req={}", new Object[]{returnType, path, req});
        ResponseEntity<R> responseEntity = restTemplate.getForEntity(path, returnType, req);
        BaseRpcRes<R> res = new BaseRpcRes();
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            res.setResult(responseEntity.getBody());
        }
        return res;
    }

    class MyResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return ! clientHttpResponse.getStatusCode().equals(HttpStatus.OK);
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            LOGGER.error("http status code = [{}], http status = [{}], error response body : {}", clientHttpResponse.getStatusCode().value(), clientHttpResponse.getStatusText() , IOUtils.toString(clientHttpResponse.getBody(), "UTF-8"));
        }
    }
}
