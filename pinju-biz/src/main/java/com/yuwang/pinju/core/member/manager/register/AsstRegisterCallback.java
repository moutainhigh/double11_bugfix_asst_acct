package com.yuwang.pinju.core.member.manager.register;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.manager.MemberAsstManager;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstMemberRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;

public class AsstRegisterCallback implements RegisterCallback {

	private MemberAsstManager memberAsstManager;
	private MemberAsstRelationDO memberAsstRelation;
	private MemberAsstMemberRoleDO[] memberAsstMemberRoles;

	public AsstRegisterCallback(MemberAsstManager memberAsstManager, MemberAsstRelationDO memberAsstRelation, MemberAsstMemberRoleDO[] memberAsstMemberRoles) {
		this.memberAsstManager = memberAsstManager;
		this.memberAsstRelation = memberAsstRelation;
		this.memberAsstMemberRoles = memberAsstMemberRoles;
	}

	@Override
	public void setMemberProperty(MemberDO member) {
		memberAsstRelation.setAsstMemberId(member.getMemberId());
	}

	@Override
	public void doCallback() throws DaoException {
		memberAsstRelation = memberAsstManager.insertMemberAsstRelation(memberAsstRelation);
		if (memberAsstRelation == null) {
			throw new DaoException("insert memberAsstRelation error, memberAsstRelation = null");
		}
		if (memberAsstMemberRoles != null) {
			for(int i = 0; i < memberAsstMemberRoles.length; i++) {
				memberAsstMemberRoles[i].setAsstMemberId(memberAsstRelation.getAsstMemberId());
				memberAsstMemberRoles[i].setMasterMemberId(memberAsstRelation.getMasterMemberId());
				memberAsstManager.insertMemberAsstMemberRole(memberAsstMemberRoles[i]);
			}
		}
	}
}
