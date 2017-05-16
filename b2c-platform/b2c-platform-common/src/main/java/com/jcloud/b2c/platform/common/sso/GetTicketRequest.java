package com.jcloud.b2c.platform.common.sso;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-03-07 16:11
 * @lastdate:
 */
public class GetTicketRequest {
    String sso_service_ticket;

    String url;

    String ip;

    public String getSso_service_ticket() {
        return sso_service_ticket;
    }

    public void setSso_service_ticket(String sso_service_ticket) {
        this.sso_service_ticket = sso_service_ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
