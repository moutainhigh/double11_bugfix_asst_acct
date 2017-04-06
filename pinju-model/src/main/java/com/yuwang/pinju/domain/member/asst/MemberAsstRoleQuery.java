package com.yuwang.pinju.domain.member.asst;

import com.yuwang.pinju.domain.Paginator;

public class MemberAsstRoleQuery extends Paginator {

	private static final long serialVersionUID = 1L;
	
	 /**
     * 主账号会员编号（MEMBER_MEMBER.MEMBER_ID）
     */
    private Long masterMemberId;

	public Long getMasterMemberId() {
		return masterMemberId;
	}

	public void setMasterMemberId(Long masterMemberId) {
		this.masterMemberId = masterMemberId;
	}

	@Override
	public String toString() {
		return "MemberAsstRelationQuery [masterMemberId=" + masterMemberId
				+ "]";
	}
}
