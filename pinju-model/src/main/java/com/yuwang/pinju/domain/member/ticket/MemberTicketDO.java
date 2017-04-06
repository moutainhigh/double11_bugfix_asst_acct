package com.yuwang.pinju.domain.member.ticket;

/**
 * <p>用户登录后的 Ticket 数据</p>
 * @author gaobaowen
 * 2011-6-1 上午09:08:26
 */
public class MemberTicketDO {

	private String ticket;
	private int origin;
	private RegisterInfoDO registerInfo;

	public MemberTicketDO() {
	}

	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getOrigin() {
		return origin;
	}
	public void setOrigin(int origin) {
		this.origin = origin;
	}
	public RegisterInfoDO getRegisterInfo() {
		return registerInfo;
	}
	public void setRegisterInfo(RegisterInfoDO registerInfo) {
		this.registerInfo = registerInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + origin;
		result = prime * result + ((registerInfo == null) ? 0 : registerInfo.hashCode());
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
		MemberTicketDO other = (MemberTicketDO) obj;
		if (origin != other.origin)
			return false;
		if (registerInfo == null) {
			if (other.registerInfo != null)
				return false;
		} else if (!registerInfo.equals(other.registerInfo))
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
		return "MemberTicketVO [ticket=" + ticket + ", origin=" + origin + ", registerInfo=" + registerInfo + "]";
	}
}
