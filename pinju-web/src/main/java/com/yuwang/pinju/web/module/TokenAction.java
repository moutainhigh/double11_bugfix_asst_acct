package com.yuwang.pinju.web.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class TokenAction extends ActionSupport {

	private final Log log = LogFactory.getLog(getClass());

	public final static java.lang.String TOKEN_NAME = "_token";

	/**
	 * 验证表单是否重复提交
	 * 
	 * @param request
	 * @return
	 */
	public boolean checkToken() {
		log.debug("start checkToken");
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean isEqual = false;
		HttpSession session = request.getSession();
		String formToken = request.getParameter(TOKEN_NAME);
		String sessionToken = (String) session.getAttribute(TOKEN_NAME);
		log.debug("formToken:" + formToken);
		log.debug("sessionToken:" + sessionToken);
		if (formToken != null && sessionToken != null) {
			if (formToken.equalsIgnoreCase(sessionToken)) {
				isEqual = true;
				session.removeAttribute(TOKEN_NAME);
			}
		}
		log.debug("checkToken result:" + isEqual);
		return isEqual;
	}

	public String getToken() {
		return getToken(ServletActionContext.getRequest());
	}

	/**
	 * 获得令牌
	 * 
	 * @param request
	 * @return
	 */
	public String getToken(HttpServletRequest request) {
		String token = "" + System.currentTimeMillis();
		HttpSession session = request.getSession();
		if (session != null) {
			session.setAttribute(TOKEN_NAME, token);
		}
		return token;
	}

	public String getTokenName() {
		return TOKEN_NAME;
	}
}
