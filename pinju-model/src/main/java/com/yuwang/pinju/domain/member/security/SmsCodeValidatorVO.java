package com.yuwang.pinju.domain.member.security;

import com.yuwang.pinju.domain.message.SmsMessageType;

/**
 * <p>手机验证码验证数据对象</p>
 *
 * @author gaobaowen
 * @since 2011-11-23 17:23:02
 */
public class SmsCodeValidatorVO {

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 消息编号
	 */
	private String messageId;

	/**
	 * 短信类型
	 */
	private SmsMessageType type;

	/**
	 * 用户输入的校验码
	 */
	private String code;

	/**
	 * 提交验证请求的客户端 IP 地址
	 */
	private String clientIp;

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public SmsMessageType getType() {
		return type;
	}
	public void setType(SmsMessageType type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientIp == null) ? 0 : clientIp.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		SmsCodeValidatorVO other = (SmsCodeValidatorVO) obj;
		if (clientIp == null) {
			if (other.clientIp != null)
				return false;
		} else if (!clientIp.equals(other.clientIp))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SmsCodeValidatorVO [mobile=" + mobile + ", messageId=" + messageId + ", type=" + type + ", code="
				+ code + ", clientIp=" + clientIp + "]";
	}
}
