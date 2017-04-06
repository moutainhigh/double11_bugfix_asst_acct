package com.yuwang.pinju.core.member.manager.register;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.manager.MemberSinaAccountManager;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.login.MemberSinaAccountDO;

public class SinaRegisterCallback implements RegisterCallback {
	
	private MemberSinaAccountManager sinaAccountManager;
	private MemberSinaAccountDO memberSinaAccount;

	public SinaRegisterCallback(MemberSinaAccountManager sinaAccountManager, MemberSinaAccountDO memberSinaAccount) {
		this.sinaAccountManager = sinaAccountManager;
		this.memberSinaAccount = memberSinaAccount;
	}

	@Override
	public void doCallback() throws DaoException {
		sinaAccountManager.addSinaMember(memberSinaAccount);
	}

	@Override
	public void setMemberProperty(MemberDO member) {
		memberSinaAccount.setMemberId(member.getMemberId());
		member.setAccountName(memberSinaAccount.getSinaUid());
	}
}
