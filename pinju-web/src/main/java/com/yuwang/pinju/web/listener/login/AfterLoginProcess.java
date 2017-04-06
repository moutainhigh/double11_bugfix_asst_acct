package com.yuwang.pinju.web.listener.login;

import org.apache.commons.logging.Log;

import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.web.cookie.AfterLoginCookie;
import com.yuwang.pinju.web.listener.EventProcess;
import com.yuwang.pinju.web.listener.ListenerManager;

/**
 * <p>登录后操作处理</p>
 *
 * @author gaobaowen
 * @since 2011-7-29 13:22:46
 */
public class AfterLoginProcess implements EventProcess {

	private MemberAO memberAO;
	private MemberDO member;
	private String referer;
	private String returnUrl;

	public AfterLoginProcess(MemberAO memberAO, MemberDO member, String referer, String returnUrl) {
		this.memberAO = memberAO;
		this.member = member;
		this.referer = referer;
		this.returnUrl = returnUrl;
	}

	@Override
	public boolean process(Log log) {
		EventProcess loginCookie = new AfterLoginCookie(member);
		boolean authResult = loginCookie.process(log);
		if (authResult) {
			log.debug("login cookie process result true");
			ListenerManager.getInstance().fireAfterLoginListener(new AfterLoginEvent(memberAO, member, referer, returnUrl));
			return true;
		}
		log.warn("login cookie process result false, need not fire after login listener");
		return false;
	}
}