/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: BaseRpcRes.java project: jcloud-b2c-user-service
 * @creator: wangxin17
 * @date: 2017/2/10
 */
package com.jcloud.b2c.mall.service.rpc.model;

import java.io.Serializable;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-02-10 18:16
 * @lastdate:
 */
public class BaseRpcRes<T> implements Serializable {

    protected T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
