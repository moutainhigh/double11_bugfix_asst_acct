package com.yuwang.pinju.domain.member.ticket;

/**
 * <p>用户注册后返回的信息</p>
 *
 * @author gaobaowen
 * 2011-6-1 上午09:07:17
 */
public class RegisterInfoDO {

	/**
	 * 用户注册名
	 */
	private String registerName;

	/**
	 * 盛大通行证PT账号
	 */
	private String ptid;

	/**
	 * 盛大通行证数字账号
	 */
	private String sdid;

	public RegisterInfoDO() {
	}

	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
	}
	public String getSdid() {
		return sdid;
	}
	public void setSdid(String sdid) {
		this.sdid = sdid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ptid == null) ? 0 : ptid.hashCode());
		result = prime * result + ((registerName == null) ? 0 : registerName.hashCode());
		result = prime * result + ((sdid == null) ? 0 : sdid.hashCode());
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
		RegisterInfoDO other = (RegisterInfoDO) obj;
		if (ptid == null) {
			if (other.ptid != null)
				return false;
		} else if (!ptid.equals(other.ptid))
			return false;
		if (registerName == null) {
			if (other.registerName != null)
				return false;
		} else if (!registerName.equals(other.registerName))
			return false;
		if (sdid == null) {
			if (other.sdid != null)
				return false;
		} else if (!sdid.equals(other.sdid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegisterInfoVO [registerName=" + registerName + ", ptid=" + ptid + ", sdid=" + sdid + "]";
	}
}
