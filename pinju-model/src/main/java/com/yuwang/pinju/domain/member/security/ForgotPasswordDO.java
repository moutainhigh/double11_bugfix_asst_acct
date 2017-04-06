package com.yuwang.pinju.domain.member.security;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.validation.annotation.LoginName;

/**
 * <p>忘记密码</p>
 *
 * @author gaobaowen
 * @since 2011-9-6 下午04:48:13
 */
public class ForgotPasswordDO {
	
	/**
	 * 邮箱找回密码
	 */
    public static final String SEL_EMIAL = "1";
	
    /**
     * 手机找回密码
     */
	public static final String SEL_MOBILE = "2";
	
	public static final String CODE_SUCCESS = "success";
	
	public static final String CODE_NOT_MEMBER = "no-loginName";
	
	public static final String CODE_NOT_EMAIL = "no-email";
	
	public static final String CODE_NOT_MOBILE = "no-mobile";
	
	public static final String CODE_MEMBER_EMAIL = "no-equal-eamil";
	
	public static final String CODE_MEMBER_MOBILE = "no-equal-mobile";

	/**
	 * 会员编号
	 */
	private String loginName;
	/**
	 * 会员手机号码
	 */
	private String mobile;
	/**
	 * 找回密码链接的邮箱
	 */
	private String email;
	
	/**
	 * 找回密码类型
	 */
	private String sel;

	/**
	 * 验证码
	 */
	private String captcha;

	/**
	 * 验证码 session id
	 */
	private String sid;

	/**
	 * IP 地址
	 */
	private String ip;
	
	private String param7;

	public ForgotPasswordDO() {}
	
	public ForgotPasswordDO(String loginName, String mobile, String email, String sel) {
		this.loginName = loginName;
		this.mobile = mobile;
		this.email = email;
		this.sel = sel;
	}
	
	public boolean isEmail() {
		return SEL_EMIAL.equals(sel) ? true : false;
	}
	
	public boolean isMobile() {
		return SEL_MOBILE.equals(sel) ? true : false;
	}

	public String getHiddenEmail() {
		return StringUtil.hideEmail(email);
	}
	
	public String getHiddenMobile() {
		return StringUtil.hideMobile(mobile);
	}

	@NotEmpty(message = "{forgotPassword.email.notempty}")
	@Email(message = "{forgotPassword.email.email}")
	@Length(max = 100, message = "{forgotPassword.email.size}")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "{forgotPassword.captcha.notempty}")
	@Pattern(regexp = "[0-9A-Za-z]{4}", message = "{forgotPassword.captcha.pattern}")
	public String getCaptcha() {
		return captcha;
	}

	@LoginName
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Pattern(regexp = "1[3458][0-9]{9}", message = "{member.mobile.failure}")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}
	
	public String getParam7() {
		return param7;
	}

	public void setParam7(String param7) {
		this.param7 = param7;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((captcha == null) ? 0 : captcha.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((sel == null) ? 0 : sel.hashCode());
		result = prime * result + ((sid == null) ? 0 : sid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForgotPasswordDO other = (ForgotPasswordDO) obj;
		if (captcha == null) {
			if (other.captcha != null)
				return false;
		} else if (!captcha.equals(other.captcha))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (sel == null) {
			if (other.sel != null)
				return false;
		} else if (!sel.equals(other.sel))
			return false;
		if (sid == null) {
			if (other.sid != null)
				return false;
		} else if (!sid.equals(other.sid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ForgotPasswordDO [loginName=" + loginName + ", mobile="
				+ mobile + ", email=" + email + ", sel=" + sel + ", captcha="
				+ captcha + ", sid=" + sid + ", ip=" + ip + "]";
	}
}