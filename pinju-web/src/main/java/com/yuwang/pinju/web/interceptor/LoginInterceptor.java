package com.yuwang.pinju.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>登录拦截器</p>
 *
 * @author gaobaowen
 * 2011-6-3 下午01:13:09
 */
public class LoginInterceptor extends AbstractInterceptor {

	private final static Log log = LogFactory.getLog(LoginInterceptor.class);

	private static final long serialVersionUID = 1L;

	private final static long IP_MASK = 0xffff0000L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if(log.isDebugEnabled()) {
			log.debug("execute LoginInterceptor check, url: " + ServletUtil.getRequestUrl());
		}
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("current member has not login, need re-login");
			return toLogin(login);
		}

		// 检查登录信息中的 IP 地址前两段是否与当前 IP 地址的前两段相同
		long currentIp = ServletUtil.getRemoteNumberIp();
		if ((login.getIp() & IP_MASK) != (currentIp & IP_MASK)) {
			log.warn("login get ip: [" + ServletUtil.toIPv4(login.getIp()) + "(" + login.getIp() + ")], current client ip: [" +  ServletUtil.toIPv4(currentIp) + "(" + currentIp + ")], two segment of head are not same");
			return toLogin(login);
		}

		if(log.isDebugEnabled()) {
			log.debug("member login, " + login.getMemberId() + ", login time: " + login.getLoginTime() + ", member nickname: " + login.getNickname());
		}

		if (invocation.getAction() instanceof MemberCheckerAware) {
			ActionContext.getContext().put(MemberCheckerAware.MEMBER_CHECKER_NAME, login.hashSessionMId());
		}
		log.info("execute LoginInterceptor check finished, member already login");
		return invocation.invoke();
	}

	private String toLogin(CookieLoginInfo login) {
		login.logout();
		ServletUtil.loginCurrentResultUrl();
		return "pinju-login";
	}

	/**
	 * <p>需要检查是否是同一会员的标记接口</p>
	 *
	 * @author gaobaowen
	 * @since 2011-9-21 09:44:15
	 */
	public interface MemberCheckerAware {

		/**
		 * 会员检查参数名
		 */
		String MEMBER_CHECKER_NAME = "pj0";
	}
}
