/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: RestService.java project: jcloud-b2c-user-service
 * @creator: wangxin17
 * @date: 2017/2/10
 */
package com.jcloud.b2c.mall.service.rpc.service;

import com.jcloud.b2c.mall.service.rpc.model.BaseRpcReq;
import com.jcloud.b2c.mall.service.rpc.model.BaseRpcRes;

import java.util.Map;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-10 18:13
 * @lastdate:
 */
public interface RestService {

    <R> BaseRpcRes<R> post(Class<R> returnType, String path, BaseRpcReq req);

    <R> BaseRpcRes<R> get(Class<R> returnType, String path);

    <R> BaseRpcRes<R> get(Class<R> returnType, String path, BaseRpcReq req);

    <R> BaseRpcRes<R> get(Class<R> returnType, String path, Map<String, Object> req);

}
