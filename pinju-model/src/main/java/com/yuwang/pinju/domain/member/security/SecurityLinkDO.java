package com.yuwang.pinju.domain.member.security;

/**
 * <p>Email 安全链接</p>
 *
 * @author gaobaowen
 * @since 2011-9-5 09:33:09
 */
public class SecurityLinkDO {

	private String param;
	private String ip;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = (param == null ? param : param.trim());
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((param == null) ? 0 : param.hashCode());
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
		SecurityLinkDO other = (SecurityLinkDO) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (param == null) {
			if (other.param != null)
				return false;
		} else if (!param.equals(other.param))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SecurityLinkDO [param=" + param + ", ip=" + ip + "]";
	}
}
