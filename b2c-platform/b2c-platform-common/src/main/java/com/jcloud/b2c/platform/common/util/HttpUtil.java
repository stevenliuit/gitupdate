package com.jcloud.b2c.platform.common.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by panshizhao on 2017/4/10.
 */
public class HttpUtil {

    private static Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * TD 开始标记
     */
    public final static String STARTTDHTML = "<td>";
    /**
     * TD 结束标记
     */
    public final static String ENDTDHTML = "</td>";
    /**
     * B 开始标记
     */
    public final static String STARTBHTML = "<b>";
    /**
     * B 结束标记
     */
    public final static String ENDBHTML = "</b>";
    /**
     * 结束标记
     */
    public final static String ENDINDEXHTML = "\">";
    /**
     * A 结束标记
     */
    public final static String ENDHREFHTML = "</a>";
    /**
     * A 开始标记
     */
    public final static String STARTHREFHTML = "<a";
    /**
     * HTTP
     */
    public final static String HTTPHTML = "http://";

    public final static String LOGINCONTEXT = "loginContext";

    public final static String JDMONITOR_URL = "JDmonitorURL";

    public static String sendHttp(String url) {
        CloseableHttpResponse response = null;
        String resultStr = "";
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            String json = "";
            StringEntity input = new StringEntity(json);
            input.setContentType("text/plain");
            httpPost.setEntity(input);
            response = httpclient.execute(httpPost);
            resultStr = EntityUtils.toString(response.getEntity());
            return resultStr;
        } catch (Exception e) {
            e.printStackTrace();
            return resultStr;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
