package com.yuwang.pinju.web.module.my.screen;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.cookie.util.CodeUtil;
import com.yuwang.pinju.web.interceptor.CookieRefreshTokenInterceptor;

/**
 * <p>用于 URL 上的 TOKEN 防重复提交</p>
 *
 * @author gaobaowen
 * @Deprecated 由于采用后端代理方式发布，禁用 session，使用 {@link CookieRefreshTokenInterceptor} 替代
 * 2011-6-10 下午02:33:57
 */
@Deprecated
public class UrlToken {

	public final static String TOKEN_SESSION_NAME = UrlToken.class.getName();

	private final static Log log = LogFactory.getLog(UrlToken.class);

	private String token;

	public String getToken() {
		if(token == null) {
			token = CodeUtil.random(10);
			HttpSession session = ServletActionContext.getRequest().getSession(true);
			session.setAttribute(TOKEN_SESSION_NAME, token);
			if(log.isDebugEnabled()) {
				log.debug("cannot find token, then generate a token: " + token);
			}
		}
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean validateToken() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(session == null) {
			log.warn("cannot find active HttpSession, token validation result is ok");
			return true;
		}
		Object sessionToken = session.getAttribute(TOKEN_SESSION_NAME);
		if(sessionToken == null) {
			log.warn("cannot find " + TOKEN_SESSION_NAME + " in HttpSession, token validation result is ok");
			return true;
		}
		// 清除 TOKEN
		session.removeAttribute(TOKEN_SESSION_NAME);
		boolean result = sessionToken.equals(token);
		token = null;
		if(log.isInfoEnabled()) {
			log.info("current session token: " + sessionToken + ", user submit token is: " + token + ", validation result is " + result);
		}
		return result;
	}
}
