package com.yuwang.pinju.web.freemarker.share;

import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * <p>用于页面上会员权限处理</p>
 *
 * @author gaobaowen
 * @since 2011-12-20 16:40:50
 */
public class MemberAuth {

	private final static MemberAuth instance = new MemberAuth();

	private MemberAuth() {
	}

	public static MemberAuth getInstance() {
		return instance;
	}

	/**
	 * <p></p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-20 下午04:41:13
	 */
	public boolean isMasterAccount() {
		return getCookieLoginInfo().isMasterAccount();
	}

	public boolean isAssistantAccount() {
		return getCookieLoginInfo().isAssistantAccount();
	}

	public boolean isCommonAccount() {
		return getCookieLoginInfo().isCommonAccount();
	}

	public boolean isSellerAccount() {
		return getCookieLoginInfo().isSellerAccount();
	}

	public boolean hasAsstPerm(String target, String action) {
		return getCookieLoginInfo().hasAsstPerm(target, action);
	}

	private static CookieLoginInfo getCookieLoginInfo() {
		return CookieLoginInfo.getCookieLoginInfo();
	}
}
