package com.yuwang.pinju.domain.member;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;

public class MemberPinjuLoginDO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 会员编号（MEMBER_MEMBER.MEMBER_ID）
	 */
	private Long memberId;

	/**
	 * 登录会员名（即：MEMBER_MEMBER.NICKNAME）
	 */
	private String loginName;

	/**
	 * 会员密码
	 */
	private String password;

	/**
	 * 注册 IP
	 */
	private String registerIp;

	/**
	 * 注册来源URL
	 */
	private String referer;

	/**
	 * 推广代码
	 */
	private String adCode;

	private Date gmtCreate;

	private Date gmtModified;

	public MemberDO createMember(Integer original) {
		MemberDO member = new MemberDO();
		member.setMemberId(memberId);
		member.setAccountName(loginName);
		member.setMemberOrigin(original);
		member.setNickname(loginName);
		member.setAgreeAgreement(MemberDO.AGREE_AGREEMENT_YES);
		member.setAgreeAgreementTime(gmtCreate);
		member.setCreateTime(gmtCreate);
		return member;
	}

	public MemberDO createMember(Integer original, Integer accountType) {
		MemberDO member = createMember(original);
		if (accountType != null) {
			member.setAccountType(accountType);
		}
		return member;
	}

	public boolean isValid() {
		return true;
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

	public String getPassword() {
		return password;
	}

	public String getReferer() {
		return referer;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adCode == null) ? 0 : adCode.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((referer == null) ? 0 : referer.hashCode());
		result = prime * result + ((registerIp == null) ? 0 : registerIp.hashCode());
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
		MemberPinjuLoginDO other = (MemberPinjuLoginDO) obj;
		if (adCode == null) {
			if (other.adCode != null)
				return false;
		} else if (!adCode.equals(other.adCode))
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
		if (registerIp == null) {
			if (other.registerIp != null)
				return false;
		} else if (!registerIp.equals(other.registerIp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberPinjuLoginDO [id=" + id + ", memberId=" + memberId + ", loginName=" + loginName + ", password="
				+ password + ", registerIp=" + registerIp + ", referer=" + referer + ", adCode=" + adCode + ", gmtCreate=" + DateUtil.formatDatetime(gmtCreate)
				+ ", gmtModified=" + DateUtil.formatDatetime(gmtModified) + "]";
	}
}
