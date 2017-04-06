package com.yuwang.pinju.domain.member.security;

import java.io.Serializable;
import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.RandomUUID;
import com.yuwang.pinju.common.RelationEntity;

/**
 * <p>安全手机验证码</p>
 */
public class MemberSecurityMobileCodeDO implements Serializable, RelationEntity<Long>, MemberSecurityParam {

	private static final long serialVersionUID = 1L;

	/**
	 * 验证码状态 -- 未验证（0）
	 */
	public final static Integer CODE_STATUS_INIT = 0;

	/**
	 * 验证码状态 -- 已验证（1）
	 */
	public final static Integer CODE_STATUS_VALIDATED = 1;

	/**
	 * 验证码类型 -- 手机绑定（1）
	 */
	public final static Integer CODE_TYPE_MOBILE_BOUND = 1;

	/**
	 * 验证码类型 -- 找回密码（2）
	 */
	public final static Integer CODE_TYPE_RETRIEVE_PASSWORD = 2;

	/**
	 * 验证码类型 -- 解绑手机号（3）
	 */
	public final static Integer CODE_TYPE_MOBILE_UNBOUND = 3;

	/**
	 * 手机消息编号长度
	 */
	public final static int MESSAGE_ID_LENGTH = 36;

	private Long id;

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
	 * 手机验证码
	 */
	private String code;

	/**
	 * 验证码类型 （1：绑定手机；2：手机找回密码）
	 */
	private Integer codeType;

	/**
	 * 验证码发送时间
	 */
	private Date sendTime;

	/**
	 * 验证码过期时间
	 */
	private Date expireTime;

	/**
	 * 手机消息编号
	 */
	private String messageId;

	/**
	 * 验证码请求产生者的IP地址
	 */
	private String codeIp;

	/**
	 * 验证码验证时间
	 */
	private Date codeTime;

	/**
	 * 验证码状态（0：有效；1：无效）
	 */
	private Integer codeStatus = CODE_STATUS_INIT;

	/**
	 * 验证码验证的IP地址
	 */
	private String validateIp;

	/**
	 * 数据版本号
	 */
	private Integer version = 0;

	private String token;

	private Integer tokenStatus = 0;

	private Date gmtCreate;

	private Date gmtModified;

	public MemberSecurityMobileCodeDO() {
	}

	private MemberSecurityMobileCodeDO(long memberId, String loginName, String mobile, int codeType, String code, int expireSeconds, String clientIp) {
		this.memberId = memberId;
		this.loginName = loginName;
		this.mobile = mobile;
		this.code = code;
		this.codeType = codeType;
		this.sendTime = DateUtil.current();
		this.expireTime = new Date(System.currentTimeMillis() + expireSeconds * 1000L);
		this.messageId = RandomUUID.get(MESSAGE_ID_LENGTH);
		this.codeIp = clientIp;
	}

	public MemberSecurityMobileCodeDO(MemberSecurityMobileDO sm, int codeType, String code, int expireSeconds, String clientIp) {
		this(sm.getMemberId(), sm.getLoginName(), sm.getMobile(), codeType, code, expireSeconds, clientIp);
	}

	@Override
	public String getSecurityType() {
		return SECURITY_TYPE_MOBILE;
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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getCodeIp() {
		return codeIp;
	}

	public void setCodeIp(String codeIp) {
		this.codeIp = codeIp;
	}

	public Date getCodeTime() {
		return codeTime;
	}

	public void setCodeTime(Date codeTime) {
		this.codeTime = codeTime;
	}

	public Integer getCodeStatus() {
		return codeStatus;
	}

	public void setCodeStatus(Integer codeStatus) {
		this.codeStatus = codeStatus;
	}

	public String getValidateIp() {
		return validateIp;
	}

	public void setValidateIp(String validateIp) {
		this.validateIp = validateIp;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(Integer tokenStatus) {
		this.tokenStatus = tokenStatus;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((codeIp == null) ? 0 : codeIp.hashCode());
		result = prime * result + ((codeStatus == null) ? 0 : codeStatus.hashCode());
		result = prime * result + ((codeTime == null) ? 0 : codeTime.hashCode());
		result = prime * result + ((codeType == null) ? 0 : codeType.hashCode());
		result = prime * result + ((expireTime == null) ? 0 : expireTime.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result + ((sendTime == null) ? 0 : sendTime.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((tokenStatus == null) ? 0 : tokenStatus.hashCode());
		result = prime * result + ((validateIp == null) ? 0 : validateIp.hashCode());
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
		MemberSecurityMobileCodeDO other = (MemberSecurityMobileCodeDO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (codeIp == null) {
			if (other.codeIp != null)
				return false;
		} else if (!codeIp.equals(other.codeIp))
			return false;
		if (codeStatus == null) {
			if (other.codeStatus != null)
				return false;
		} else if (!codeStatus.equals(other.codeStatus))
			return false;
		if (codeTime == null) {
			if (other.codeTime != null)
				return false;
		} else if (!codeTime.equals(other.codeTime))
			return false;
		if (codeType == null) {
			if (other.codeType != null)
				return false;
		} else if (!codeType.equals(other.codeType))
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
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
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
		if (validateIp == null) {
			if (other.validateIp != null)
				return false;
		} else if (!validateIp.equals(other.validateIp))
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
		return "MemberSecurityMobileCodeDO [id=" + id + ", memberId=" + memberId + ", loginName=" + loginName
				+ ", mobile=" + mobile + ", code=" + code + ", codeType=" + codeType + ", sendTime=" + sendTime
				+ ", expireTime=" + expireTime + ", messageId=" + messageId + ", codeIp=" + codeIp + ", codeTime="
				+ codeTime + ", codeStatus=" + codeStatus + ", validateIp=" + validateIp + ", version=" + version
				+ ", token=" + token + ", tokenStatus=" + tokenStatus + ", gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + "]";
	}
}
