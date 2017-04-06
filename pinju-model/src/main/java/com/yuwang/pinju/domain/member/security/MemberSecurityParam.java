package com.yuwang.pinju.domain.member.security;

public interface MemberSecurityParam {

	/**
	 * 安全类型 -- 邮箱（1）
	 */
	String SECURITY_TYPE_EMAIL = "1";

	/**
	 * 安全类型 -- 手机号码（2）
	 */
	String SECURITY_TYPE_MOBILE = "2";

	/**
	 * <p>获得取安全参数：Message Token</p>
	 */
	String getToken();

	/**
	 * <p>获得取安全参数：Message ID</p>
	 */
	String getMessageId();

	/**
	 * <p>获得取安全参数：安全类型</p>
	 */
	String getSecurityType();

	public static class DefaultSecurityParam implements MemberSecurityParam {

		private String messageId;
		private String token;
		private String securityType;

		public DefaultSecurityParam(String messageId, String token, String securityType) {
			super();
			this.messageId = messageId;
			this.token = token;
			this.securityType = securityType;
		}

		public String getMessageId() {
			return messageId;
		}

		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getSecurityType() {
			return securityType;
		}

		public void setSecurityType(String securityType) {
			this.securityType = securityType;
		}
		
		public boolean isMobile() {
			return SECURITY_TYPE_MOBILE.equals(securityType);
		}
		
		public boolean isEmail() {
			return SECURITY_TYPE_EMAIL.equals(securityType);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
			result = prime * result + ((securityType == null) ? 0 : securityType.hashCode());
			result = prime * result + ((token == null) ? 0 : token.hashCode());
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
			DefaultSecurityParam other = (DefaultSecurityParam) obj;
			if (messageId == null) {
				if (other.messageId != null)
					return false;
			} else if (!messageId.equals(other.messageId))
				return false;
			if (securityType == null) {
				if (other.securityType != null)
					return false;
			} else if (!securityType.equals(other.securityType))
				return false;
			if (token == null) {
				if (other.token != null)
					return false;
			} else if (!token.equals(other.token))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "DefaultSecurityParam [messageId=" + messageId + ", token=" + token + ", securityType="
					+ securityType + "]";
		}
	}
}
