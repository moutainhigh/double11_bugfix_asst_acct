package com.yuwang.pinju.domain.member;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.RelationEntity;
import com.yuwang.pinju.domain.BaseDO;

/**
 * <p>
 * 会员登录日志
 * </p>
 * 
 * @author gaobaowen 2011-6-10 16:22:28
 */
public class MemberLoginHisDO extends BaseDO implements RelationEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 会员编号
	 */
	private Long memberId;

	/**
	 * 会员会话编号
	 */
	private String sessionId;

	/**
	 * 会员登录的 IP
	 */
	private String loginIp;

	/**
	 * 会员来源
	 */
	private Integer memberOrigin;

	/**
	 * 会话产生的时间
	 */
	private Date sessionTime;

	/**
	 * 会员登录时间
	 */
	private Date loginTime;

	/**
	 * 会员登录时间
	 */
	private Date logoutTime;

	/**
	 * 登录状态
	 */
	private Integer status;

	/**
	 * 登录源及RETURN_URL（REFERER^RETURN_URL）
	 */
	private String loginUrlInfo;

	public MemberLoginHisDO() {
	}

	@Override
	public boolean isNew() {
		return (id == null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Integer getMemberOrigin() {
		return memberOrigin;
	}

	public void setMemberOrigin(Integer memberOrigin) {
		this.memberOrigin = memberOrigin;
	}

	public Date getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(Date sessionTime) {
		this.sessionTime = sessionTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLoginUrlInfo() {
		return loginUrlInfo;
	}

	public void setLoginUrlInfo(String loginUrlInfo) {
		this.loginUrlInfo = loginUrlInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((loginIp == null) ? 0 : loginIp.hashCode());
		result = prime * result + ((loginTime == null) ? 0 : loginTime.hashCode());
		result = prime * result + ((loginUrlInfo == null) ? 0 : loginUrlInfo.hashCode());
		result = prime * result + ((logoutTime == null) ? 0 : logoutTime.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((memberOrigin == null) ? 0 : memberOrigin.hashCode());
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((sessionTime == null) ? 0 : sessionTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberLoginHisDO other = (MemberLoginHisDO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (loginIp == null) {
			if (other.loginIp != null)
				return false;
		} else if (!loginIp.equals(other.loginIp))
			return false;
		if (loginTime == null) {
			if (other.loginTime != null)
				return false;
		} else if (!loginTime.equals(other.loginTime))
			return false;
		if (loginUrlInfo == null) {
			if (other.loginUrlInfo != null)
				return false;
		} else if (!loginUrlInfo.equals(other.loginUrlInfo))
			return false;
		if (logoutTime == null) {
			if (other.logoutTime != null)
				return false;
		} else if (!logoutTime.equals(other.logoutTime))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (memberOrigin == null) {
			if (other.memberOrigin != null)
				return false;
		} else if (!memberOrigin.equals(other.memberOrigin))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (sessionTime == null) {
			if (other.sessionTime != null)
				return false;
		} else if (!sessionTime.equals(other.sessionTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberLoginHisDO [id=" + id + ", memberId=" + memberId + ", sessionId=" + sessionId + ", loginIp="
				+ loginIp + ", memberOrigin=" + memberOrigin + ", sessionTime=" + DateUtil.formatDatetime(sessionTime)
				+ ", loginTime=" + DateUtil.formatDatetime(loginTime) + ", logoutTime="
				+ DateUtil.formatDatetime(logoutTime) + ", status=" + status + ", loginUrlInfo=" + loginUrlInfo + "]";
	}
}
