package com.yuwang.pinju.domain.member;

import java.util.List;

/**
 * <p>会员相关所有的信息</p>
 *
 * @author gaobaowen
 * 2011-6-9 下午12:29:22
 */
public class MemberAllDO {

	/**
	 * 会员编号
	 */
	private Long memberId;

	/**
	 * 基本会员信息
	 */
	private MemberDO member;

	/**
	 * 会员个人资料
	 */
	private MemberInfoDO memberInfo;

	/**
	 * 会员的收货地址
	 */
	private List<MemberDeliveryDO> memberDeliveries;

	/**
	 * 会员支付账户信息
	 */
	private MemberPaymentDO memberPayment;

	public MemberAllDO() {
	}

	public MemberAllDO(Long memberId) {
		super();
		this.memberId = memberId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public MemberDO getMember() {
		return member;
	}

	public void setMember(MemberDO member) {
		this.member = member;
	}

	public MemberInfoDO getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfoDO memberInfo) {
		this.memberInfo = memberInfo;
	}

	public List<MemberDeliveryDO> getMemberDeliveries() {
		return memberDeliveries;
	}

	public void setMemberDeliveries(List<MemberDeliveryDO> memberDeliveries) {
		this.memberDeliveries = memberDeliveries;
	}

	public MemberPaymentDO getMemberPayment() {
		return memberPayment;
	}

	public void setMemberPayment(MemberPaymentDO memberPayment) {
		this.memberPayment = memberPayment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((memberDeliveries == null) ? 0 : memberDeliveries.hashCode());
		result = prime * result + ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result + ((memberInfo == null) ? 0 : memberInfo.hashCode());
		result = prime * result + ((memberPayment == null) ? 0 : memberPayment.hashCode());
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
		MemberAllDO other = (MemberAllDO) obj;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (memberDeliveries == null) {
			if (other.memberDeliveries != null)
				return false;
		} else if (!memberDeliveries.equals(other.memberDeliveries))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (memberInfo == null) {
			if (other.memberInfo != null)
				return false;
		} else if (!memberInfo.equals(other.memberInfo))
			return false;
		if (memberPayment == null) {
			if (other.memberPayment != null)
				return false;
		} else if (!memberPayment.equals(other.memberPayment))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberAllDO [memberId=" + memberId + ", member=" + member + ", memberInfo=" + memberInfo
				+ ", memberDeliveries=" + memberDeliveries + ", memberPayment=" + memberPayment + "]";
	}
}
