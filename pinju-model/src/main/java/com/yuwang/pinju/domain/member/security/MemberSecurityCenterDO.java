package com.yuwang.pinju.domain.member.security;

import java.io.Serializable;
import java.util.Date;

public class MemberSecurityCenterDO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long memberId;
	/**
     * 会员登录名
     */
    private String loginName;

    /**
     * 安全验证掩码（1<<0：邮箱；1<<1：手机）
     */
    private Integer securityAuthMask = 0;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 上次登录IP
     */
    private String lastLoginIp;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮件地址
     */
    private String email;
    
    /**
     * 安全等级
     */
    private Integer level;

    public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getSecurityAuthMask() {
		return securityAuthMask;
	}

	public void setSecurityAuthMask(Integer securityAuthMask) {
		this.securityAuthMask = securityAuthMask;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
