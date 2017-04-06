package com.yuwang.pinju.domain.member;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

import com.yuwang.pinju.common.RelationEntity;
import com.yuwang.pinju.domain.member.security.MemberSecurityDO;
import com.yuwang.pinju.domain.member.ticket.MemberTicketDO;
import com.yuwang.pinju.domain.member.ticket.TicketValidatorResultDO;

/**
 * <p>会员账户信息</p>
 *
 * @author gaobaowen
 * 2011-6-2 下午12:13:30
 */
public class MemberDO implements RelationEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * MemberId 最小值
	 */
	public final static long MIN_MEMBER_ID = 100000000000000L;

	/**
	 * MemberId 最大值
	 */
	public final static long MAX_MEMBER_ID = 999999999999999L;

	/**
	 * 会员来源 -- 盛大通行证（0）
	 */
	public final static Integer MEMBER_ORIGIN_SDO = 0;

	/**
	 * 会员来源 -- 网易（1）
	 */
	public final static Integer MEMBER_ORIGIN_163 = 1;

	/**
	 * 会员来源 -- 人人网（2）
	 */
	public final static Integer MEMBER_ORIGIN_RENREN = 2;

	/**
	 * 会员来源 -- 新浪网（3）
	 */
	public final static Integer MEMBER_ORIGIN_SINA = 3;

	/**
	 * 会员来源 -- 开心网（4）
	 */
	public final static Integer MEMBER_ORIGIN_KAIXIN001 = 4;

	/**
	 * 会员来源 -- 品聚自有会员（9）
	 */
	public final static Integer MEMBER_ORIGIN_PINJU = 9;

	/**
	 * 会员性别 -- 女性（0）
	 */
	public final static Integer SEX_FEMALE = 0;

	/**
	 * 会员性别 -- 男性（1）
	 */
	public final static Integer SEX_MALE = 1;

	/**
	 * 会员性别 -- 保密（2）
	 */
	public final static Integer SEX_SECURITY = 2;

	/**
	 * 会员状态 -- 正常（0）
	 */
	public final static Integer STATUS_NORMAL = 0;

	/**
	 * 会员状态 -- 冻结（1）
	 */
	public final static Integer STATUS_BLOCK = 1;

	/**
	 * 会员状态 -- 卖家主账号进行的冻结（2）
	 */
	public final static Integer STATUS_MASTER_BLOCK = 2;

	/**
	 * 盛大通行证账号 KEY -- PT（1）
	 */
	public final static String SNDA_ACCOUNT_KEY_PT = "1";

	/**
	 * 盛大通行证账号 KEY -- MOBILE（3）
	 */
	public final static String SNDA_ACCOUNT_KEY_MOBILE = "3";

	/**
	 * 盛大通行证账号 KEY -- EMAIL（4）
	 */
	public final static String SNDA_ACCOUNT_KEY_EMAIL = "4";

	/**
	 * 盛大通行证账号 KEY -- SNDAEMAIL（5）
	 */
	public final static String SNDA_ACCOUNT_KEY_SNDAEMAIL = "5";

	/**
	 * 昵称长度 -- 最小字符数（3）
	 */
	public final static int NICKNAME_LENGTH_MIN = 3;

	/**
	 * 昵称长度 -- 最大字符数（20）
	 */
	public final static int NICKNAME_LENGTH_MAX = 20;

	/**
	 * 注册状态 -- 其他（1）
	 */
	public final static Integer REGISTER_STATUS_OTHER = 0;

	/**
	 * 注册状态 -- 盛大通行证定制注册页面注册（1）
	 */
	public final static Integer REGISTER_STATUS_SNDA_CUSTOM = 1;

	/**
	 * 会员是否已同意《用户协议》 -- 未同意（0）
	 */
	public final static Integer AGREE_AGREEMENT_NO = 0;

	/**
	 * 会员是否已同意《用户协议》 -- 已同意（1）
	 */
	public final static Integer AGREE_AGREEMENT_YES = 1;

	/**
	 * 账号类型 -- 普通（0）
	 */
	public final static Integer ACCOUNT_TYPE_COMMON = 0;

	/**
	 * 账号类型 -- 卖家主账号（1）
	 */
	public final static Integer ACCOUNT_TYPE_SELLER = 1;

	/**
	 * 账号类型 -- 卖家子账号（2）
	 */
	public final static Integer ACCOUNT_TYPE_ASSISTANT = 2;

	private Long id;
	private Long memberId;
	private String accountName;
	private String nickname;
	private Integer memberOrigin = MEMBER_ORIGIN_SDO;
	private String sdoLoginName;
	private Integer myPageType;

	/**
	 * 盛大通行证 PT 账号（用户的个性化账号；手机账号格式为 MP00000000000aaa.sdo；邮箱账号格式为 EM00000000000aaa.sdo）查询key = 1
	 */
	private String sndaPtAccount;

	/**
	 * 盛大通行证手机账号，查询key = 3
	 */
	private String sndaMobileAccount;

	/**
	 * 盛大通行证邮箱账号，查询 key = 4
	 */
	private String sndaEmailAccount;

	/**
	 * 盛大通行证 SNDA 邮箱账号，查询 key = 5
	 */
	private String sndaSndamailAccount;

	private Integer sex = SEX_SECURITY;

	private Integer status = STATUS_NORMAL;

	/**
	 * 注册状态（1：盛大通行证定制注册页面注册；0：其他）
	 */
	private Integer registerStatus = REGISTER_STATUS_OTHER;

	/**
	 * 会员信息版本号（会员信息、基本信息、社区信息更改时增加1）
	 */
	private Long infoVersion = 0L;

	/**
	 * 会员是否已同意《用户协议》（0：未同意；1：已同意），若是通过定制注册页面的注册的会员，默认为 {@link #AGREE_AGREEMENT_YES} 值
	 */
	private Integer agreeAgreement = AGREE_AGREEMENT_NO;

	/**
	 * 会员同意《用户协议》的时间
	 */
	private Date agreeAgreementTime;

	private Date createTime;

	/**
	 * 账号类型（0：普通；1：卖家主账号；2：卖家子账号）
	 */
	private Integer accountType = ACCOUNT_TYPE_COMMON;

	public MemberDO() {
	}

	public MemberDO(String accountName, Integer memberOrigin) {
		this.accountName = accountName;
		this.memberOrigin = memberOrigin;
	}

	public static MemberDO createMember(long memberId, MemberTicketDO ticket, TicketValidatorResultDO result) {
		MemberDO member = new MemberDO();
		member.memberId = memberId;
		member.accountName = result.getLoginAccount();
		member.memberOrigin = ticket.getOrigin();
		member.createTime = new Date();
		return member;
	}

	public MemberSecurityDO createMemberSecurity() {
		MemberSecurityDO security = new MemberSecurityDO();
		security.setMemberId(memberId);
		security.setLoginName(nickname);
		return security;
	}

	public void setSndaAccount(SndaAccountDO sndaAccount) {
		if(sndaAccount == null) {
			return;
		}
		this.sndaPtAccount = sndaAccount.getSndaAccount(SNDA_ACCOUNT_KEY_PT);
		this.sndaMobileAccount = sndaAccount.getSndaAccount(SNDA_ACCOUNT_KEY_MOBILE);
		this.sndaEmailAccount = sndaAccount.getSndaAccount(SNDA_ACCOUNT_KEY_EMAIL);
		this.sndaSndamailAccount = sndaAccount.getSndaAccount(SNDA_ACCOUNT_KEY_SNDAEMAIL);
	}

	/**
	 * <p>账号是否被冻结</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-26 11:48:13
	 */
	public boolean isBlock() {
		return !STATUS_NORMAL.equals(status);
	}

	/**
	 * <p>会员是否是盛大通行证用户</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 11:12:29
	 */
	public boolean isSndaMember() {
		return memberOrigin(MEMBER_ORIGIN_SDO);
	}

	/**
	 * <p>会员是否是品聚网注册用户</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 11:12:43
	 */
	public boolean isPinjuMember() {
		return memberOrigin(MEMBER_ORIGIN_PINJU);
	}

	/**
	 * <p>会员是否是子账号会员</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 11:12:56
	 */
	public boolean isAssistantMember() {
		return isAccountType(ACCOUNT_TYPE_ASSISTANT);
	}

	private boolean memberOrigin(int origin) {
		if (memberOrigin == null) {
			return false;
		}
		return origin == memberOrigin;
	}

	private boolean isAccountType(Integer type) {
		if (type == null) {
			return false;
		}
		return type.equals(accountType);
	}

	public boolean isNew() {
		return (id == null || id < 1);
	}

	public boolean isLogin() {
		return (id != null) && (memberId != null);
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getMemberOrigin() {
		return memberOrigin;
	}

	public void setMemberOrigin(Integer memberOrigin) {
		this.memberOrigin = memberOrigin;
	}

	public String getSdoLoginName() {
		return sdoLoginName;
	}

	public void setSdoLoginName(String sdoLoginName) {
		this.sdoLoginName = sdoLoginName;
	}

	public String getSndaPtAccount() {
		return sndaPtAccount;
	}

	public void setSndaPtAccount(String sndaPtAccount) {
		this.sndaPtAccount = sndaPtAccount;
	}

	public String getSndaMobileAccount() {
		return sndaMobileAccount;
	}

	public void setSndaMobileAccount(String sndaMobileAccount) {
		this.sndaMobileAccount = sndaMobileAccount;
	}

	public String getSndaEmailAccount() {
		return sndaEmailAccount;
	}

	public void setSndaEmailAccount(String sndaEmailAccount) {
		this.sndaEmailAccount = sndaEmailAccount;
	}

	public String getSndaSndamailAccount() {
		return sndaSndamailAccount;
	}

	public void setSndaSndamailAccount(String sndaSndamailAccount) {
		this.sndaSndamailAccount = sndaSndamailAccount;
	}

	@NotNull(message = "{memberDo.sex.notnull}")
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(Integer registerStatus) {
		this.registerStatus = registerStatus;
	}

	public Long getInfoVersion() {
		return infoVersion;
	}

	public void setInfoVersion(Long infoVersion) {
		this.infoVersion = infoVersion;
	}

	public Integer getAgreeAgreement() {
		return agreeAgreement;
	}

	public void setAgreeAgreement(Integer agreeAgreement) {
		this.agreeAgreement = agreeAgreement;
	}

	public Date getAgreeAgreementTime() {
		return agreeAgreementTime;
	}

	public void setAgreeAgreementTime(Date agreeAgreementTime) {
		this.agreeAgreementTime = agreeAgreementTime;
	}

	public Integer getMyPageType() {
		return myPageType;
	}

	public void setMyPageType(Integer myPageType) {
		this.myPageType = myPageType;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + ((accountType == null) ? 0 : accountType.hashCode());
		result = prime * result + ((agreeAgreement == null) ? 0 : agreeAgreement.hashCode());
		result = prime * result + ((agreeAgreementTime == null) ? 0 : agreeAgreementTime.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((infoVersion == null) ? 0 : infoVersion.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((memberOrigin == null) ? 0 : memberOrigin.hashCode());
		result = prime * result + ((myPageType == null) ? 0 : myPageType.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((registerStatus == null) ? 0 : registerStatus.hashCode());
		result = prime * result + ((sdoLoginName == null) ? 0 : sdoLoginName.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((sndaEmailAccount == null) ? 0 : sndaEmailAccount.hashCode());
		result = prime * result + ((sndaMobileAccount == null) ? 0 : sndaMobileAccount.hashCode());
		result = prime * result + ((sndaPtAccount == null) ? 0 : sndaPtAccount.hashCode());
		result = prime * result + ((sndaSndamailAccount == null) ? 0 : sndaSndamailAccount.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		MemberDO other = (MemberDO) obj;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (accountType == null) {
			if (other.accountType != null)
				return false;
		} else if (!accountType.equals(other.accountType))
			return false;
		if (agreeAgreement == null) {
			if (other.agreeAgreement != null)
				return false;
		} else if (!agreeAgreement.equals(other.agreeAgreement))
			return false;
		if (agreeAgreementTime == null) {
			if (other.agreeAgreementTime != null)
				return false;
		} else if (!agreeAgreementTime.equals(other.agreeAgreementTime))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (infoVersion == null) {
			if (other.infoVersion != null)
				return false;
		} else if (!infoVersion.equals(other.infoVersion))
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
		if (myPageType == null) {
			if (other.myPageType != null)
				return false;
		} else if (!myPageType.equals(other.myPageType))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (registerStatus == null) {
			if (other.registerStatus != null)
				return false;
		} else if (!registerStatus.equals(other.registerStatus))
			return false;
		if (sdoLoginName == null) {
			if (other.sdoLoginName != null)
				return false;
		} else if (!sdoLoginName.equals(other.sdoLoginName))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (sndaEmailAccount == null) {
			if (other.sndaEmailAccount != null)
				return false;
		} else if (!sndaEmailAccount.equals(other.sndaEmailAccount))
			return false;
		if (sndaMobileAccount == null) {
			if (other.sndaMobileAccount != null)
				return false;
		} else if (!sndaMobileAccount.equals(other.sndaMobileAccount))
			return false;
		if (sndaPtAccount == null) {
			if (other.sndaPtAccount != null)
				return false;
		} else if (!sndaPtAccount.equals(other.sndaPtAccount))
			return false;
		if (sndaSndamailAccount == null) {
			if (other.sndaSndamailAccount != null)
				return false;
		} else if (!sndaSndamailAccount.equals(other.sndaSndamailAccount))
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
		return "MemberDO [id=" + id + ", memberId=" + memberId + ", accountName=" + accountName + ", nickname="
				+ nickname + ", memberOrigin=" + memberOrigin + ", sdoLoginName=" + sdoLoginName + ", myPageType="
				+ myPageType + ", sndaPtAccount=" + sndaPtAccount + ", sndaMobileAccount=" + sndaMobileAccount
				+ ", sndaEmailAccount=" + sndaEmailAccount + ", sndaSndamailAccount=" + sndaSndamailAccount + ", sex="
				+ sex + ", status=" + status + ", registerStatus=" + registerStatus + ", infoVersion=" + infoVersion
				+ ", agreeAgreement=" + agreeAgreement + ", agreeAgreementTime=" + agreeAgreementTime + ", createTime="
				+ createTime + ", accountType=" + accountType + "]";
	}

	public static void main(String[] args) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        MemberDO info = new MemberDO();
        Set<ConstraintViolation<MemberDO>> set = validator.validate(info);
        for(ConstraintViolation<MemberDO> result : set) {
        	System.out.println(result.getPropertyPath().toString() + " --> " + result.getMessage());
        }
	}
}
