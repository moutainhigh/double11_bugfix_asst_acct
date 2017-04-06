package com.yuwang.pinju.domain.api;

/**
 * <p>读取部分 Cookie 会员信息</p>
 *
 * @author gaobaowen
 * @since 2011-8-3 下午03:18:39
 */
public class MemberCookieDO {

	private String H;
	private String O;
	private String N;
	private String L;
	private String V;
	private String D;
	private boolean login;

	private MemberCookieDO(boolean login) {
		this.login = login;
	}

	public static MemberCookieDO createLogin() {
		return new MemberCookieDO(true);
	}

	public static MemberCookieDO createUnlogin() {
		return new MemberCookieDO(false);
	}

	public String getH() {
		return H;
	}
	public void setH(String h) {
		H = h;
	}

	public String getO() {
		return O;
	}
	public void setO(String o) {
		O = o;
	}

	public String getN() {
		return N;
	}
	public void setN(String n) {
		N = n;
	}

	public String getL() {
		return L;
	}
	public void setL(String l) {
		L = l;
	}

	public String getV() {
		return V;
	}
	public void setV(String v) {
		V = v;
	}

	public String getD() {
		return D;
	}
	public void setD(String d) {
		D = d;
	}

	public boolean isLogin() {
		return login;
	}
	public void setLogin(boolean login) {
		this.login = login;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((D == null) ? 0 : D.hashCode());
		result = prime * result + ((H == null) ? 0 : H.hashCode());
		result = prime * result + ((L == null) ? 0 : L.hashCode());
		result = prime * result + ((N == null) ? 0 : N.hashCode());
		result = prime * result + ((O == null) ? 0 : O.hashCode());
		result = prime * result + ((V == null) ? 0 : V.hashCode());
		result = prime * result + (login ? 1231 : 1237);
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
		MemberCookieDO other = (MemberCookieDO) obj;
		if (D == null) {
			if (other.D != null)
				return false;
		} else if (!D.equals(other.D))
			return false;
		if (H == null) {
			if (other.H != null)
				return false;
		} else if (!H.equals(other.H))
			return false;
		if (L == null) {
			if (other.L != null)
				return false;
		} else if (!L.equals(other.L))
			return false;
		if (N == null) {
			if (other.N != null)
				return false;
		} else if (!N.equals(other.N))
			return false;
		if (O == null) {
			if (other.O != null)
				return false;
		} else if (!O.equals(other.O))
			return false;
		if (V == null) {
			if (other.V != null)
				return false;
		} else if (!V.equals(other.V))
			return false;
		if (login != other.login)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberCookieDO [H=" + H + ", O=" + O + ", N=" + N + ", L=" + L + ", V=" + V + ", D=" + D + ", login="
				+ login + "]";
	}
}
