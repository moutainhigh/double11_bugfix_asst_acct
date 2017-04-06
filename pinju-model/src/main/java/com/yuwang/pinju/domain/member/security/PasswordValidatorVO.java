package com.yuwang.pinju.domain.member.security;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.common.StringUtil;

/**
 * <p>会员密码验证数据对象</p>
 *
 * @author gaobaowen
 * @since 2011-11-29 10:09:02
 */
public class PasswordValidatorVO {

	/**
	 * 会员登录账号（允许会员名称、验证后的 E-mail 地址、验证后的手机号码）
	 */
	private String loginName;

	/**
	 * 会员密码（用户输入的密码（应在 6 到 16 个字符之间），若该参数不在 6～16 字符之间的话直接认为密码是不正确）
	 */
	private String password;

	/**
	 * 安全数据传输的 TID
	 */
	private String tid;
	
	public PasswordValidatorVO() {
	}

	public PasswordValidatorVO(String loginName, String password, String tid) {
		this.loginName = loginName;
		this.password = password;
		this.tid = tid;
	}

	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = StringUtils.trim(loginName);
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = StringUtils.trim(password);
	}

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = StringUtils.trim(tid);
	}

	public boolean checkParameter() {
		if (StringUtils.isBlank(loginName)) {
			return false;
		}
		if (StringUtils.isBlank(password)) {
			return false;
		}
		if (StringUtils.isBlank(tid)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((tid == null) ? 0 : tid.hashCode());
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
		PasswordValidatorVO other = (PasswordValidatorVO) obj;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (tid == null) {
			if (other.tid != null)
				return false;
		} else if (!tid.equals(other.tid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ValidatorPasswordVO [loginName=" + loginName + ", password=" + StringUtil.asterisk(password) + ", tid=" + tid + "]";
	}
}
