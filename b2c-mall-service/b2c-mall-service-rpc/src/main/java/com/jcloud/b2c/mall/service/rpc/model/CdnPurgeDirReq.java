/**
 * @copyright: Copyright (c) 2015-2020 jd.com All Rights Reserved
 * @file: CdnPurgeDirReq.java project: jcloud-b2c-mall-service
 * @creator: wangxin17
 * @date: 2017/3/30
 */
package com.jcloud.b2c.mall.service.rpc.model;

/**
 * @description:
 * @author: wangxin17
 * @requireNo:
 * @createdate: 2017-03-30 14:36
 * @lastdate:
 */
public class CdnPurgeDirReq extends BaseRpcReq {

    private String Action;
    private String Path;
    private String TTL;
    private String ReqId;
    private String AppId;
    private String AppKey;
    private String Timestamp;
    private String Signature;

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getTTL() {
        return TTL;
    }

    public void setTTL(String TTL) {
        this.TTL = TTL;
    }

    public String getReqId() {
        return ReqId;
    }

    public void setReqId(String reqId) {
        ReqId = reqId;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getAppKey() {
        return AppKey;
    }

    public void setAppKey(String appKey) {
        AppKey = appKey;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public String getSignature() {
        /*String val = this.getAppId() + this.getAppKey() + this.getTimestamp();
        byte[] signature = DigestUtils.getMd5Digest().digest(val.getBytes());
        Signature = new String(signature).toLowerCase();*/
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    @Override
    public String toString() {
        return "CdnPurgeDirReq{" +
                "Action='" + Action + '\'' +
                ", Path='" + Path + '\'' +
                ", TTL='" + TTL + '\'' +
                ", ReqId='" + ReqId + '\'' +
                ", AppId='" + AppId + '\'' +
                ", AppKey='" + AppKey + '\'' +
                ", Timestamp='" + Timestamp + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }
}
