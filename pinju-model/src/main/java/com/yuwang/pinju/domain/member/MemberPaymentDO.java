/**
 *
 */
package com.yuwang.pinju.domain.member;

import java.util.Date;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.domain.BaseDO;

/**
 * @author yejingfeng
 * @since 2011-6-8
 * 会员支付账户信息
 */
public class MemberPaymentDO extends BaseDO {

	private static final long serialVersionUID = 6849613725564308607L;

	public final static String INSTITUTION_SHENGPAY = "SHENGPAY";
	public final static String INSTITUTION_TENPAY = "TENPAY";

	/**
	 * 账户类型 -- 商家（0）
	 */
	public final static Integer ACCOUNT_TYPE_BUSSINESS = 0;

	/**
	 * 账户类型 -- 个人（1）
	 */
	public final static Integer ACCOUNT_TYPE_PERSON = 1;

	/**
	 * 激活状态 -- 未激活（0）
	 */
	public final static Integer ACTIVE_STATUS_UNACTIVE = 0;

	/**
	 * 激活状态 -- 已激活（1）
	 */
	public final static Integer ACTIVE_STATUS_ACTIVE = 1;

	/**
	 * 绑定状态 -- 未绑定（0）
	 */
	public final static Integer BOUND_STATUS_UNBOUND = 0;

	/**
	 * 绑定状态 -- 已绑定（1）
	 */
	public final static Integer BOUND_STATUS_BOUND = 1;
	
	/**
	 * 签订代扣协议 -- 未签订（0）
	 */
	public final static Integer SIGN_AGREEMENT_NO = 0;
	
	/**
	 * 签订代扣协议 -- 已签订（1）
	 */
	public final static Integer SIGN_AGREEMENT_YES = 1;
	
	public MemberPaymentDO(){}
	
	public MemberPaymentDO(long memberId, String institution) {
		this.memberId = memberId;
		this.institution = institution;
	}

	private Long id;

	/**
	 * pinju会员编号
	 */
	private Long memberId;

	/**
	 * 支付机构。盛付通（{@link #INSTITUTION_SHENGPAY}）、财付通（{@link #INSTITUTION_SHENGPAY}）
	 */
	private String institution;

	/**
	 * 支付账户名称
	 */
	private String accountNO;

	/**
	 * 支付账户类型
	 */
	private Integer accountType;

	/**
	 * 账户激活状态
	 */
	private Integer activeStatus;

	/**
	 * 账户绑定状态
	 */
	private Integer bondStatus;

	/**
	 * 支付账户邮箱或手机号
	 */
	private String outUser;

	/**
	 * 会员是否已签订《代扣协议》（0：未签；1：已签）
	 */
	private Integer signAgreement = SIGN_AGREEMENT_NO;
    
	/**
	 * 支付账号人的姓名
	 */
	private String personName;

	/**
	 * 《代扣协议》签订成功的时间
	 */
	private Date signAgreementTime;
	private Date gmtCreate;
	private Date gmtModified;
	private Long version;

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

	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getAccountNO() {
		return accountNO;
	}
	public void setAccountNO(String accountNO) {
		this.accountNO = StringUtil.trim(accountNO);
	}

	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Integer getBondStatus() {
		return bondStatus;
	}
	public void setBondStatus(Integer bondStatus) {
		this.bondStatus = bondStatus;
	}

	public String getOutUser() {
		return outUser;
	}
	public void setOutUser(String outUser) {
		this.outUser = outUser;
	}

	public Integer getSignAgreement() {
		return signAgreement;
	}
	public void setSignAgreement(Integer signAgreement) {
		this.signAgreement = signAgreement;
	}
	public Date getSignAgreementTime() {
		return signAgreementTime;
	}
	public void setSignAgreementTime(Date signAgreementTime) {
		this.signAgreementTime = signAgreementTime;
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

	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((accountNO == null) ? 0 : accountNO.hashCode());
		result = prime * result
				+ ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result
				+ ((activeStatus == null) ? 0 : activeStatus.hashCode());
		result = prime * result
				+ ((bondStatus == null) ? 0 : bondStatus.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((institution == null) ? 0 : institution.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((outUser == null) ? 0 : outUser.hashCode());
		result = prime * result
				+ ((personName == null) ? 0 : personName.hashCode());
		result = prime * result
				+ ((signAgreement == null) ? 0 : signAgreement.hashCode());
		result = prime
				* result
				+ ((signAgreementTime == null) ? 0 : signAgreementTime
						.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		MemberPaymentDO other = (MemberPaymentDO) obj;
		if (accountNO == null) {
			if (other.accountNO != null)
				return false;
		} else if (!accountNO.equals(other.accountNO))
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (activeStatus == null) {
			if (other.activeStatus != null)
				return false;
		} else if (!activeStatus.equals(other.activeStatus))
			return false;
		if (bondStatus == null) {
			if (other.bondStatus != null)
				return false;
		} else if (!bondStatus.equals(other.bondStatus))
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
		if (institution == null) {
			if (other.institution != null)
				return false;
		} else if (!institution.equals(other.institution))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (outUser == null) {
			if (other.outUser != null)
				return false;
		} else if (!outUser.equals(other.outUser))
			return false;
		if (personName == null) {
			if (other.personName != null)
				return false;
		} else if (!personName.equals(other.personName))
			return false;
		if (signAgreement == null) {
			if (other.signAgreement != null)
				return false;
		} else if (!signAgreement.equals(other.signAgreement))
			return false;
		if (signAgreementTime == null) {
			if (other.signAgreementTime != null)
				return false;
		} else if (!signAgreementTime.equals(other.signAgreementTime))
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
		return "MemberPaymentDO [id=" + id + ", memberId=" + memberId + ", institution=" + institution + ", accountNO="
				+ accountNO + ", accountType=" + accountType + ", activeStatus=" + activeStatus + ", bondStatus="
				+ bondStatus + ", outUser=" + outUser + ", signAgreement=" + signAgreement + ", signAgreementTime="
				+ signAgreementTime + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", version="
				+ version + "]";
	}
}
