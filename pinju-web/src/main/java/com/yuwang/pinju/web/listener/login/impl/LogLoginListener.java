package com.yuwang.pinju.web.listener.login.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.listener.login.AfterLoginEvent;
import com.yuwang.pinju.web.listener.login.AfterLoginListener;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>会员登录后记录用户登录日志</p>
 *
 * @author gaobaowen
 * @since 2011-7-11 上午09:23:43
 */
public class LogLoginListener implements AfterLoginListener {

	private final static Log log = LogFactory.getLog(LogLoginListener.class);

	/**
	 * 用于登录日志 URL 中 Referer 与 returnUrl 的分隔符
	 */
	private final static char SEPARATOR = '^';

	/**
	 * URL 记录最大字符数
	 */
	private final static int LOGIN_URL_INFO_MAX_LENGTH = 1023;

	/**
	 * URL 记录一半字符数（{@link #LOGIN_URL_INFO_MAX_LENGTH} / 2 - 3）
	 */
	private final static int HALF_LOGIN_URL_INFO_LENGTH = 508;

	@Override
	public boolean afterLogin(AfterLoginEvent event) {
		MemberDO member = event.getMember();
		if(member == null) {
			log.warn("member is null, cannot fire the listener");
			return false;
		}
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if(login == null) {
			log.warn("can not get current member login information, cannot fire the listener");
			return false;
		}
		String ip = ServletUtil.getRemoteIp(ServletActionContext.getRequest());
		MemberLoginHisDO his = login.newMemberLoginHis(ip);
		his.setMemberId(member.getMemberId());
		his.setMemberOrigin(member.getMemberOrigin());
		his.setLoginUrlInfo(getUrlInfo(event.getReferer(), event.getReturnUrl()));
		event.setLoginHis(his);
		boolean result = event.getMemberAO().logLogin(his);

		if(log.isDebugEnabled()) {
			log.debug("log login result: " + result + ", " + his);
		}
		return true;
	}

	public String getUrlInfo(String referer, String returnUrl) {
		referer = referer == null ? "" : referer;
		returnUrl = returnUrl == null ? "" : returnUrl;
		int ref = referer.length();
		int ret = returnUrl.length();
		if (ref + ret == 0) {
			return null;
		}
		if (ref + ret < LOGIN_URL_INFO_MAX_LENGTH) {
			return referer + SEPARATOR + returnUrl;
		}
		if (ref <= HALF_LOGIN_URL_INFO_LENGTH) {
			return referer + SEPARATOR + returnUrl.substring(0, LOGIN_URL_INFO_MAX_LENGTH - ref - 3) + "...";
		}
		if (ret <= HALF_LOGIN_URL_INFO_LENGTH) {
			return referer.substring(0, LOGIN_URL_INFO_MAX_LENGTH - ret - 3) + "..." + SEPARATOR + returnUrl;
		}
		return referer.substring(0, HALF_LOGIN_URL_INFO_LENGTH) + "..." + SEPARATOR + returnUrl.substring(0, HALF_LOGIN_URL_INFO_LENGTH) + "...";
	}
}
