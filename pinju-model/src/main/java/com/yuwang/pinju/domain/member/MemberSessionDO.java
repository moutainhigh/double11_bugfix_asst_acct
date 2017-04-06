package com.yuwang.pinju.domain.member;

import java.util.Date;

/**
 * <p>用户会话信息</p>
 *
 * @author gaobaowen
 * 2011-6-10 下午05:42:22
 */
public class MemberSessionDO {

	/**
	 * 会话ID
	 */
	private String sessionId;

	/**
	 * 会话创建时间
	 */
	private Date sessionCreateTime;

	/**
	 * 用户 IP 地址
	 */
	private String clientIp;
	
	public MemberSessionDO() {
	}

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Date getSessionCreateTime() {
		return sessionCreateTime;
	}
	public void setSessionCreateTime(Date sessionCreateTime) {
		this.sessionCreateTime = sessionCreateTime;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Override
	public String toString() {
		return "MemberSessionVO [sessionId=" + sessionId + ", sessionCreateTime=" + sessionCreateTime + ", clientIp="
				+ clientIp + "]";
	}
}
