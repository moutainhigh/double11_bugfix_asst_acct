package com.yuwang.pinju.domain.member;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.validation.annotation.LoginName;

/**
 * <p>会员登录对象</p>
 *
 * @author gaobaowen
 * @since 2011-9-7 16:10:37
 */
public class MemberLoginDO {

	/**
	 * 会话编号（用于验证码）
	 */
	private String sid;

	/**
	 * 登录账号
	 */
	private String loginName;

	/**
	 * 登录密码
	 */
	private String password;

	/**
	 * 验证码
	 */
	private String captcha;

	/**
	 * OpenAPI 加密数据
	 */
	private String param1;

	/**
	 * OpenAPI 加密数据签名
	 */
	private String param2;

	public MemberLoginDO() {
	}

	public MemberLoginDO(String loginName) {
		this.loginName = loginName;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@LoginName
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

	@NotEmpty(message = "{memberRegister.password.notempty}")
	@Length(min = 6, max = 16, message = "{memberRegister.password.length}")
	@Pattern(regexp = "[\\x21-\\x7e]+", message = "{memberRegister.password.pattern}")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((captcha == null) ? 0 : captcha.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((param1 == null) ? 0 : param1.hashCode());
		result = prime * result + ((param2 == null) ? 0 : param2.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		MemberLoginDO other = (MemberLoginDO) obj;
		if (captcha == null) {
			if (other.captcha != null)
				return false;
		} else if (!captcha.equals(other.captcha))
			return false;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (param1 == null) {
			if (other.param1 != null)
				return false;
		} else if (!param1.equals(other.param1))
			return false;
		if (param2 == null) {
			if (other.param2 != null)
				return false;
		} else if (!param2.equals(other.param2))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
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
		return "MemberLoginDO [sid=" + sid + ", loginName=" + loginName + ", password=" + StringUtil.asterisk(password) + ", captcha="
				+ captcha + ", param1=" + param1 + ", param2=" + param2 + "]";
	}
}
