package com.jcloud.b2c.platform.common.sso;

/**
 * @description:
 * @author: wukun
 * @requireNo:
 * @createdate: 2017-03-07 13:41
 * @lastdate:
 */
public class UserInfo {
    /**
     * 用户中文名称
     */
    private String fullname;
    /**
     * 手机号码，M4及以上领导账号不会返回手机号
     */
    private String mobile;

    /**
     * 员工ID，员工唯一编号，再入职保持不变

     */
    private String personId;
    /**
     * PS机构ID
     */
    private String orgId;
    /**
     * PS机构名称
     */
    private String orgName;
    /**
     * 老部门ID，不建议使用。
     */
    private String hrmDeptId;
    /**
     * 邮箱，M4及以上领导账号不会返回邮箱

     */
    private String email;
    /**
     * ERP账号，员工唯一账号，再入职保持不变
     */
    /**
     * 用户ID，为兼容老的系统设置,不建议使用
     */
    private long userId;

    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getHrmDeptId() {
        return hrmDeptId;
    }

    public void setHrmDeptId(String hrmDeptId) {
        this.hrmDeptId = hrmDeptId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
