package com.jcloud.b2c.platform.domain.vo;


public class RunResults {
    private boolean success;
    private String reason;

    public RunResults() {
        success = true;
        reason = "";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setFail(String reason){
        success = false;
        this.reason = reason;
    }
}
