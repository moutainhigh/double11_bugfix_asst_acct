package com.yuwang.pinju.domain.member.ticket;

import com.yuwang.pinju.domain.member.MemberDO;

/**
 * <p>Tiket 验证结果</p>
 *
 * @author gaobaowen
 * 2011-6-1 上午09:16:41
 */
public class TicketValidatorResultDO {

	public final static String SUCCESS = "yes";

	public final static String RESPONSE_EMPTY = "RESPONSE_EMPTY";

	/**
	 * 验证结果
	 */
	private String result;

	/**
	 * 登录账号
	 */
	private String loginAccount;

	/**
	 * 登录后响应的 ticket
	 */
	private String ticket;

	/**
	 * 用户来源
	 */
	private int memberOrigin;

	public TicketValidatorResultDO() {
	}

	public static TicketValidatorResultDO create(String result, String loginAccount, MemberTicketDO ticket) {
		TicketValidatorResultDO instance = new TicketValidatorResultDO();
		instance.result = result;
		instance.loginAccount = loginAccount;
		instance.ticket = ticket.getTicket();
		instance.memberOrigin = ticket.getOrigin();
		return instance;
	}

	public static TicketValidatorResultDO createError(String result, MemberTicketDO ticket) {
		return create(result, null, ticket);
	}

	public static TicketValidatorResultDO createError(Throwable e, MemberTicketDO ticket) {
		return create(e + "|" + e.getMessage(), null, ticket);
	}

	public static TicketValidatorResultDO createSuccess(String loginAccount, MemberTicketDO ticket) {
		return create(SUCCESS, loginAccount, ticket);
	}

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getMemberOrigin() {
		return memberOrigin;
	}
	public void setMemberOrigin(int memberOrigin) {
		this.memberOrigin = memberOrigin;
	}

	public MemberDO createQueryMemberParameter() {
		return new MemberDO(loginAccount, memberOrigin);
	}

	/**
	 * <p>用户登录验证是否成功</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public boolean isSuccess() {
		return (result != null) && SUCCESS.equals(result);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loginAccount == null) ? 0 : loginAccount.hashCode());
		result = prime * result + memberOrigin;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
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
		TicketValidatorResultDO other = (TicketValidatorResultDO) obj;
		if (loginAccount == null) {
			if (other.loginAccount != null)
				return false;
		} else if (!loginAccount.equals(other.loginAccount))
			return false;
		if (memberOrigin != other.memberOrigin)
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (ticket == null) {
			if (other.ticket != null)
				return false;
		} else if (!ticket.equals(other.ticket))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TicketValidatorResultVO [result=" + result + ", loginAccount=" + loginAccount + ", token=" + ticket
				+ ", memberOrigin=" + memberOrigin + "]";
	}
}
