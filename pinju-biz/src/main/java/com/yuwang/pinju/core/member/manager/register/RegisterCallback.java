package com.yuwang.pinju.core.member.manager.register;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.MemberDO;

public interface RegisterCallback {

	RegisterCallback EMPTY_CALL_BACK = new RegisterCallback() {
		@Override
		public void doCallback() {
		}
		@Override
		public void setMemberProperty(MemberDO member) {
		}
	};
	
	void setMemberProperty(MemberDO member);

	void doCallback() throws DaoException ;
}
