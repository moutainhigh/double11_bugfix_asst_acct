package com.yuwang.pinju.web.cookie;

import org.apache.commons.logging.Log;

import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.web.cookie.PinjuCookieManager.LoginRelationCookie;
import com.yuwang.pinju.web.listener.EventProcess;

/**
 * <p>登录的 Cookie 操作</p>
 *
 * @author gaobaowen
 * 2011-6-2 上午09:31:01
 */
public class AfterLoginCookie implements EventProcess {

	private MemberDO member;

	public AfterLoginCookie(MemberDO member) {
		this.member = member;
	}

	@Override
	public boolean process(Log log) {
		if(member == null || !member.isLogin()) {
			log.warn("current member isnot login, " + member);
			return processUnlogin();
		}
		if(log.isDebugEnabled()) {
			log.debug("current member information: " + member);
		}
		return processLogin();
	}

	private boolean processUnlogin() {
//		CookieOperator.clearLoginCookies();
		return false;
	}

	private boolean processLogin() {
		PinjuCookieManager.writeLogin(new LoginRelationCookie(member));
		return true;
	}
}
