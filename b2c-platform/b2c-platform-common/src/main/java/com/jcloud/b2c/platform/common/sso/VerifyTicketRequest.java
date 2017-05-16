package com.jcloud.b2c.platform.common.sso;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-03-07 14:17
 * @lastdate:
 */
public class VerifyTicketRequest {
    private String ticket;

    private String url;

    private String clientIp;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
