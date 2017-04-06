package com.yuwang.pinju.web.module;

import com.yuwang.pinju.filter.StringFilter;
import com.yuwang.pinju.filter.impl.IllegalCharacterFilter;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.interceptor.LoginInterceptor.MemberCheckerAware;

/**
 * <p>是否是同一会员检查</p>
 *
 * @author gaobaowen
 * @since 2011-9-19 14:13:53
 */
public class MemberCheckAction implements MemberCheckerAware {

	private static final long serialVersionUID = 1L;
	private final static StringFilter filter = IllegalCharacterFilter.white("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ:".toCharArray()).max(45);

	/**
	 * 编码后的会员编号及散列值
	 */
	private String pj0;

	public void setPj0(String pj0) {
		this.pj0 = filter.doFilter(pj0);
	}

	public String getPj0() {
		return pj0;
	}

	/**
	 * <p>是否是同一会员</p>
	 *
	 * @param login 当前登录会员信息
	 * @return 是否是当前会员做的操作。若会员没有登录，或者缺乏登录信息则返回 false
	 *
	 * @author gaobaowen
	 * @since 2011-9-19 14:16:48
	 */
	public boolean isSameMember(CookieLoginInfo login) {
		if (login == null) {
			return false;
		}
		return login.isSameMember(pj0);
	}
}
