package com.yuwang.pinju.domain.member.login;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.domain.annotation.SecurityTransmission;
import com.yuwang.pinju.domain.member.MemberLoginDO;
import com.yuwang.pinju.validation.annotation.LoginName;

/**
 * <p>会员 Ajax 登录数据</p>
 *
 * @author gaobaowen
 * @since 2011-11-24 11:54:25
 */
public class MemberAjaxLoginVO implements Serializable {

	private static final long serialVersionUID = -3481672014362374721L;

	/**
	 * 登录账号
	 */
	private String loginName;

	/**
	 * 账号密码
	 */
	private String password;

	/**
	 * 验证码
	 */
	private String captcha;

	/**
	 * 验证码 Session ID
	 */
	private String sid;

	/**
	 * Security Transmission ID
	 */
	private String tid;

	/**
	 * 登录之前的页面（HTTP Referer）
	 */
	private String referer;

	/**
	 * 登录后返回的页面
	 */
	private String returnUrl;

	/**
	 * 是否需要进行验证码校验
	 */
	private String checkCaptcha;

	/**
	 * 登录 IP 地址
	 */
	private String loginIp;

	/**
	 * OpenAPI 加密数据
	 */
	private String param1;

	/**
	 * OpenAPI 加密数据签名
	 */
	private String param2;

	public MemberAjaxLoginVO() {
	}

	public MemberLoginDO toMemberLogin() {
		MemberLoginDO login = new MemberLoginDO();
		login.setSid(sid);
		login.setLoginName(loginName);
		login.setPassword(password);
		login.setCaptcha(captcha);
		login.setParam1(param1);
		login.setParam2(param2);
		return login;
	}

	public boolean checkCaptcha() {
		return "true".equalsIgnoreCase(checkCaptcha);
	}

	@LoginName
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = StringUtils.trim(loginName);
	}

	@SecurityTransmission
	@NotEmpty(message = "{memberRegister.password.notempty}")
	@Length(min = 6, max = 16, message = "{memberRegister.password.length}")
	@Pattern(regexp = "[\\x21-\\x7e]+", message = "{memberRegister.password.pattern}")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = StringUtils.trim(password);
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = StringUtils.trim(captcha);
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = StringUtils.trim(sid);
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = StringUtils.trim(referer);
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = StringUtils.trim(returnUrl);
	}

	public String getCheckCaptcha() {
		return checkCaptcha;
	}

	public void setCheckCaptcha(String checkCaptcha) {
		this.checkCaptcha = checkCaptcha;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = StringUtils.trim(loginIp);
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = StringUtils.trim(param1);
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = StringUtils.trim(param2);
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((captcha == null) ? 0 : captcha.hashCode());
		result = prime * result + ((checkCaptcha == null) ? 0 : checkCaptcha.hashCode());
		result = prime * result + ((loginIp == null) ? 0 : loginIp.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((param1 == null) ? 0 : param1.hashCode());
		result = prime * result + ((param2 == null) ? 0 : param2.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((referer == null) ? 0 : referer.hashCode());
		result = prime * result + ((returnUrl == null) ? 0 : returnUrl.hashCode());
		result = prime * result + ((sid == null) ? 0 : sid.hashCode());
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
		MemberAjaxLoginVO other = (MemberAjaxLoginVO) obj;
		if (captcha == null) {
			if (other.captcha != null)
				return false;
		} else if (!captcha.equals(other.captcha))
			return false;
		if (checkCaptcha == null) {
			if (other.checkCaptcha != null)
				return false;
		} else if (!checkCaptcha.equals(other.checkCaptcha))
			return false;
		if (loginIp == null) {
			if (other.loginIp != null)
				return false;
		} else if (!loginIp.equals(other.loginIp))
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
		if (referer == null) {
			if (other.referer != null)
				return false;
		} else if (!referer.equals(other.referer))
			return false;
		if (returnUrl == null) {
			if (other.returnUrl != null)
				return false;
		} else if (!returnUrl.equals(other.returnUrl))
			return false;
		if (sid == null) {
			if (other.sid != null)
				return false;
		} else if (!sid.equals(other.sid))
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
		return "MemberAjaxLoginVO [loginName=" + loginName + ", password=" + StringUtil.asterisk(password) + ", captcha=" + captcha
				+ ", sid=" + sid + ", tid=" + tid + ", referer=" + referer + ", returnUrl=" + returnUrl + ", checkCaptcha="
				+ checkCaptcha + ", loginIp=" + loginIp + ", param1=" + param1 + ", param2=" + param2 + "]";
	}
}
