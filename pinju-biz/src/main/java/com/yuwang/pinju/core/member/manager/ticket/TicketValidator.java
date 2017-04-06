package com.yuwang.pinju.core.member.manager.ticket;

import com.yuwang.pinju.domain.member.ticket.MemberTicketDO;
import com.yuwang.pinju.domain.member.ticket.TicketValidatorResultDO;

/**
 * <p>用户登录 ticket 验证</p>
 *
 * @author gaobaowen
 * 2011-6-1 上午09:20:35
 */
public interface TicketValidator {

	/**
	 * <p>验证第三方回传的 ticket 数据是否正确，并在验证通过后获取一些会员在第三方的信息</p>
	 *
	 * @param ticket   含有第三方回传 ticket 串等信息
	 * @return
	 *
	 * @author gaobaowen
	 */
	TicketValidatorResultDO validate(MemberTicketDO ticket);
}
