package com.yuwang.pinju.web.cookie.convert;

import com.yuwang.pinju.domain.member.MemberDO;

public class CookieMemberOriginAgreement {

	public final static int ORIGIN_ERROR = 9;
	public final static int AGREE_AGREEMENT_DEFAULT = MemberDO.AGREE_AGREEMENT_NO;

	private int origin;
	private int agreeAgreement;
	private int accountType;

	public CookieMemberOriginAgreement(int origin, int agreeAgreement, int accountType) {
		this.origin = origin;
		this.agreeAgreement = agreeAgreement;
		this.accountType = accountType;
	}

	public CookieMemberOriginAgreement(int agreeNum) {
		this.origin = agreeNum / 100;
		this.agreeAgreement = (agreeNum / 10) % 10;
		this.accountType = agreeNum % 10;
	}

	public CookieMemberOriginAgreement(MemberDO member) {
		this.origin = (member.getMemberOrigin() == null) ? ORIGIN_ERROR : member.getMemberOrigin();
		this.agreeAgreement = (member.getAgreeAgreement() == null) ? AGREE_AGREEMENT_DEFAULT : member.getAgreeAgreement();
		this.accountType = (member.getAccountType() == null) ? MemberDO.ACCOUNT_TYPE_COMMON : member.getAccountType();
	}

	public int getOrigin() {
		return origin;
	}
	public int getAgreeAgreement() {
		return agreeAgreement;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public void setAgreeAgreement(int agreeAgreement) {
		this.agreeAgreement = agreeAgreement;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String toCookieString() {
		return String.valueOf(origin * 100 + agreeAgreement * 10 + accountType);
	}

	public String toString() {
		return toCookieString();
	}
}
