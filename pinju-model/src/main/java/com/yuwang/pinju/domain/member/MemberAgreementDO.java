package com.yuwang.pinju.domain.member;

import com.yuwang.pinju.validation.annotation.MemberName;

public class MemberAgreementDO {

	private Long memberId;
	private String nickname;

	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@MemberName
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "MemberAgreementDO [memberId=" + memberId + ", nickname=" + nickname + "]";
	}
}
