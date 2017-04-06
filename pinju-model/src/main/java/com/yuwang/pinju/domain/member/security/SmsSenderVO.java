package com.yuwang.pinju.domain.member.security;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.domain.message.SmsMessageType;

/**
 * <p>短信发送数据对象</p>
 *
 * @author gaobaowen
 * @since 2011-11-22 14:29:23
 */
public class SmsSenderVO {

	/**
	 * 会员编号
	 */
	private Long memberId;

	/**
	 * 会员登录名
	 */
	private String loginName;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 短信类型
	 */
	private SmsMessageType smsType;

	/**
	 * 发起客户端 IP 地址
	 */
	private String clientIp;

	public SmsSenderVO(Long memberId, String loginName, String mobile, SmsMessageType smsType, String clientIp) {
		this.memberId = memberId;
		this.loginName = loginName;
		this.mobile = mobile;
		this.smsType = smsType;
		this.clientIp = clientIp;
	}

	public SmsSenderVO(String mobile, SmsMessageType smsType, String clientIp) {
		this(null, null, mobile, smsType, clientIp);
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
		this.loginName = StringUtils.trim(loginName);
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = StringUtils.trim(mobile);
	}
	public SmsMessageType getSmsType() {
		return smsType;
	}
	public void setSmsType(SmsMessageType smsType) {
		this.smsType = smsType;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = StringUtils.trim(clientIp);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientIp == null) ? 0 : clientIp.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((smsType == null) ? 0 : smsType.hashCode());
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
		SmsSenderVO other = (SmsSenderVO) obj;
		if (clientIp == null) {
			if (other.clientIp != null)
				return false;
		} else if (!clientIp.equals(other.clientIp))
			return false;
		if (loginName == null) {
			if (other.loginName != null)
				return false;
		} else if (!loginName.equals(other.loginName))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (smsType != other.smsType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SmsSenderVO [memberId=" + memberId + ", loginName=" + loginName + ", mobile=" + mobile + ", smsType="
				+ smsType + ", clientIp=" + clientIp + "]";
	}
}
