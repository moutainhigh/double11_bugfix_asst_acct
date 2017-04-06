package com.yuwang.pinju.domain.member.security;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;

/**
 * <p>
 * 会员安全链接信息
 * </p>
 * 
 * @author gaobaowen
 * @since 2011-9-1 10:28:49
 */
public class MemberSecurityEmailLinkDO implements java.io.Serializable, MemberSecurityParam {

	private static final long serialVersionUID = 1L;

	/**
	 * 确认状态 -- 未确认（0）
	 */
	public final static Integer CONFIRM_STATUS_NO = 0;

	/**
	 * 确认状态 -- 已确认（1）
	 */
	public final static Integer CONFIRM_STATUS_YES = 1;

	/**
	 * 链接类型 -- 邮箱验证（1）
	 */
	public final static Integer LINK_TYPE_EMAIL_VALIDATION = 1;

	/**
	 * 链接类型 -- 找回密码（2）
	 */
	public final static Integer LINK_TYPE_RETRIEVE_PASSWORD = 2;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 会员编号（MEMBER_MEMBER.MEMBER_ID）
	 */
	private Long memberId;

	/**
	 * 会员登录名
	 */
	private String loginName;

	/**
	 * 会员安全邮箱地址
	 */
	private String emailAddr;

	/**
	 * 安全链接URL参数
	 */
	private String linkParam;

	/**
	 * 安全链接类型（1：邮箱验证；2：邮箱找回密码）
	 */
	private Integer linkType;

	/**
	 * 邮件发送时间
	 */
	private Date sendTime;

	/**
	 * 链接过期时间
	 */
	private Date expireTime;

	/**
	 * 全局唯一的邮件消息编号
	 */
	private String messageId;

	/**
	 * 邮件链接请求产生者的IP地址
	 */
	private String linkIp;

	/**
	 * 确认状态（0：未确认；1：已确认）
	 */
	private Integer confirmStatus;

	/**
	 * 确认时间
	 */
	private Date confirmTime;

	/**
	 * 邮件链接确认者的IP地址
	 */
	private String confirmIp;

	/**
	 * 乐观锁版本号
	 */
	private Integer version;

	/**
	 * 邮件链接TOKEN
	 */
	private String token;

	/**
	 * 邮件链接TOKEN状态
	 */
	private Integer tokenStatus;

	private Date gmtCreate;

	private Date gmtModified;

	@Override
	public String getSecurityType() {
		return SECURITY_TYPE_EMAIL;
	}

	public Long getId() {
		return id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public String getLinkParam() {
		return linkParam;
	}

	public Integer getLinkType() {
		return linkType;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	@Override
	public String getMessageId() {
		return messageId;
	}

	public String getLinkIp() {
		return linkIp;
	}

	public Integer getConfirmStatus() {
		return confirmStatus;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public String getConfirmIp() {
		return confirmIp;
	}

	public Integer getVersion() {
		return version;
	}

	@Override
	public String getToken() {
		return token;
	}

	public Integer getTokenStatus() {
		return tokenStatus;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public void setLinkParam(String linkParam) {
		this.linkParam = linkParam;
	}

	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setLinkIp(String linkIp) {
		this.linkIp = linkIp;
	}

	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public void setConfirmIp(String confirmIp) {
		this.confirmIp = confirmIp;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTokenStatus(Integer tokenStatus) {
		this.tokenStatus = tokenStatus;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((confirmIp == null) ? 0 : confirmIp.hashCode());
		result = prime * result + ((confirmStatus == null) ? 0 : confirmStatus.hashCode());
		result = prime * result + ((confirmTime == null) ? 0 : confirmTime.hashCode());
		result = prime * result + ((emailAddr == null) ? 0 : emailAddr.hashCode());
		result = prime * result + ((expireTime == null) ? 0 : expireTime.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((linkIp == null) ? 0 : linkIp.hashCode());
		result = prime * result + ((linkParam == null) ? 0 : linkParam.hashCode());
		result = prime * result + ((linkType == null) ? 0 : linkType.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((sendTime == null) ? 0 : sendTime.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((tokenStatus == null) ? 0 : tokenStatus.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		MemberSecurityEmailLinkDO other = (MemberSecurityEmailLinkDO) obj;
		if (confirmIp == null) {
			if (other.confirmIp != null)
				return false;
		} else if (!confirmIp.equals(other.confirmIp))
			return false;
		if (confirmStatus == null) {
			if (other.confirmStatus != null)
				return false;
		} else if (!confirmStatus.equals(other.confirmStatus))
			return false;
		if (confirmTime == null) {
			if (other.confirmTime != null)
				return false;
		} else if (!confirmTime.equals(other.confirmTime))
			return false;
		if (emailAddr == null) {
			if (other.emailAddr != null)
				return false;
		} else if (!emailAddr.equals(other.emailAddr))
			return false;
		if (expireTime == null) {
			if (other.expireTime != null)
				return false;
		} else if (!expireTime.equals(other.expireTime))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModified == null) {
			if (other.gmtModified != null)
				return false;
		} else if (!gmtModified.equals(other.gmtModified))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (linkIp == null) {
			if (other.linkIp != null)
				return false;
		} else if (!linkIp.equals(other.linkIp))
			return false;
		if (linkParam == null) {
			if (other.linkParam != null)
				return false;
		} else if (!linkParam.equals(other.linkParam))
			return false;
		if (linkType == null) {
			if (other.linkType != null)
				return false;
		} else if (!linkType.equals(other.linkType))
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
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (sendTime == null) {
			if (other.sendTime != null)
				return false;
		} else if (!sendTime.equals(other.sendTime))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (tokenStatus == null) {
			if (other.tokenStatus != null)
				return false;
		} else if (!tokenStatus.equals(other.tokenStatus))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberSecurityEmailLinkDO [id=" + id + ", memberId=" + memberId + ", loginName=" + loginName
				+ ", emailAddr=" + emailAddr + ", linkParam=" + linkParam + ", linkType=" + linkType + ", sendTime="
				+ sendTime + ", expireTime=" + expireTime + ", messageId=" + messageId + ", linkIp=" + linkIp
				+ ", confirmStatus=" + confirmStatus + ", confirmTime=" + DateUtil.formatDatetime(confirmTime)
				+ ", confirmIp=" + confirmIp + ", version=" + version + ", token=" + token + ", tokenStatus="
				+ tokenStatus + ", gmtCreate=" + DateUtil.formatDatetime(gmtCreate) + ", gmtModified="
				+ DateUtil.formatDatetime(gmtModified) + "]";
	}

	/**
	 * <p>
	 * 邮件链接类型
	 * </p>
	 * 
	 * @author gaobaowen
	 * @since 2011-9-1 14:57:37
	 */
	public static enum LinkType {

		/**
		 * 邮箱验证
		 */
		EMAIL_VALIDATION(1),

		/**
		 * 邮箱找回密码
		 */
		EMAIL_RETRIEVE_PASSWORD(2),
		
		/**
		 * 邮箱解绑
		 */
		EMAIL_UNBOUND(3)

		;

		private Integer type;

		private LinkType(Integer type) {
			this.type = type;
		}

		public Integer getType() {
			return type;
		}
	}
}
