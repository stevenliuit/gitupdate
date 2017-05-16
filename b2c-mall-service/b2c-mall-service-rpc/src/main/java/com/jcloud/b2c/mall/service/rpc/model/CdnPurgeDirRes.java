/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: CdnPurgeDirRes.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/3/30
 */
package com.jcloud.b2c.mall.service.rpc.model;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-03-30 15:09
 * @lastdate:
 */
public class CdnPurgeDirRes {
    Integer Code;
    String Message;
    String Result;

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @Override
    public String toString() {
        return "CdnPurgeDirRes{" +
                "Code=" + Code +
                ", Message='" + Message + '\'' +
                ", Result='" + Result + '\'' +
                '}';
    }
}
