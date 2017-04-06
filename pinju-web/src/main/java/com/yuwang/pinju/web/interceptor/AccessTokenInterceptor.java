package com.yuwang.pinju.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;

/**
 * <p>用于往客户端写入初次访问的 Token 值以及会话的发起时间</p>
 *
 * @author gaobaowen
 * 2011-5-30 下午05:45:50
 */
public class AccessTokenInterceptor extends AbstractInterceptor {

	private final static Log log = LogFactory.getLog(AccessTokenInterceptor.class);

	private static final long serialVersionUID = 1L;

	public AccessTokenInterceptor() {
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		log.debug("execute AccessTokenInterceptor check");
		processTokenCookie();
		String result = invocation.invoke();
		return result;
	}

	private void processTokenCookie() {
		if(hasSessionIdCookie()) {
			log.debug("current client token already exists find in cookie");
			return;
		}
		log.debug("token check, cannot find TOKEN or TOKEN values is incorrect, then write it, session id: " + Thread.currentThread().getId());
		PinjuCookieManager.writeSessionId();
		log.debug("write token cookie has finished");
	}

	private boolean hasSessionIdCookie() {
		return PinjuCookieManager.hasSessionIdCookie();
	}
}
