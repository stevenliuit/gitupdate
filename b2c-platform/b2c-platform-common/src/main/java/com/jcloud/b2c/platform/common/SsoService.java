package com.jcloud.b2c.platform.common;

import com.alibaba.fastjson.JSON;

import com.jcloud.b2c.platform.common.sso.UserInfo;
import com.jcloud.b2c.platform.common.sso.VerifyTicketRequest;
//import com.jd.ssa.exception.SsoException;
import net.sf.json.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-03-07 11:45
 * @lastdate:
 */
public class SsoService {
    long serialVersionUID = 83923054537918795L;
    /**
     * 成功,返回值大于1的全是失败
     */
    public static final int SUCCESS = 1;

    /**
     * 参数错误
     */
    public static final int PARAMS_ERROR = 2;

    /**
     * 无效的ticket
     */
    public static final int TICKET_ERROR = 3;

    /**
     * 无效的service ticket
     */
    public static final int ST_ERROR = 4;
    /**
     * 账号错误，账号不存在
     */
    public static final int VERIFY_USER_NOT_EXIST = 10;

    /**
     * 密码错误
     */
    public static final int VERIFY_PASSWORD_ERROR = 11;
    /**
     * 账号已锁定
     */
    public static final int VERIFY_LOCKED = 12;

    /**
     * 无权限访问
     */
    public static final int VERIFY_NO_AUTH = 13;

    private String verifyTicketUrl = "http://test.ssa.jd.com/sso/ticket/verifyTicket";
    /**
     * 根据单点登录ticket判断用户是否已登录,有效时返回用户信息
     *
     * @param ticket 单点登录key,cookie:sso.jd.com的值
     * @param url 用户访问的URL
     * @param clientIp 访问者的IP地址
     * @return 用户基本信息
     */
    public UserInfo verifyTicket(String ticket, String url, String clientIp) throws Exception{
        CloseableHttpResponse response = null;
        UserInfo userInfo = new UserInfo();
        try {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(verifyTicketUrl);
        VerifyTicketRequest verifyTicketRequest = new VerifyTicketRequest();
        if (ticket != null){
            verifyTicketRequest.setTicket(ticket);
        }
        if (url != null){
            verifyTicketRequest.setUrl(url);
        }
        if (clientIp != null){
            verifyTicketRequest.setClientIp(clientIp);
        }
            StringEntity input = new StringEntity(JSON.toJSONString(verifyTicketRequest));
            input.setContentType("text/plain");
            httpPost.setEntity(input);
            response = httpclient.execute(httpPost);
            String resultStr = EntityUtils.toString(response.getEntity());
            JSONObject json = JSONObject.fromObject(resultStr);
            boolean flag= json.getBoolean("REQ_FLAG");
            if (flag == true){
                userInfo = (UserInfo)json.get("REQ_DATA");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return userInfo;
    }

}
