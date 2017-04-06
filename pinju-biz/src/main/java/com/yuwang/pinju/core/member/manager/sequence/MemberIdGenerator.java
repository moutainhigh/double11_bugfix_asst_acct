package com.yuwang.pinju.core.member.manager.sequence;

import com.yuwang.pinju.core.member.manager.ticket.MemberOrigin;

public interface MemberIdGenerator {

	/**
	 * 获取下一个会员编号
	 */
	public long nextMemberId(MemberOrigin memberOrign);
}
