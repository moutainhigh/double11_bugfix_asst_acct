package com.yuwang.pinju.web.struts2;

import org.apache.commons.logging.Log;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.system.ServletUtil;

public class LoginChecker {

	public static LoginCheckerResult check(Log log, boolean allowGet) {

		// 获取登录数据
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		// 未登录时跳至登录页面
		if (!login.isLogin()) {
			log.warn("bound, current member not logged, member id: " + login);
			ServletUtil.loginCurrentResultUrl();
			return LoginCheckerResult.createError(PinjuAction.LOGIN);
		}

		if(!allowGet) {
			String method = ServletActionContext.getRequest().getMethod();
			// 不允许除 POST 方法的访问！
			if(!"POST".equalsIgnoreCase(method)) {
				log.warn("user request this update using HTTP/GET, that can not been accessed, cookie info: " + login);
				return LoginCheckerResult.createError(PinjuAction.NOT_ALLOWED_METHOD);
			}
		}
		return LoginCheckerResult.createSuccess(login);
	}

	public static class LoginCheckerResult {

		private final static String SUCCESS = "SUCCESS";

		private CookieLoginInfo login;
		private String result;

		private LoginCheckerResult(CookieLoginInfo login, String result) {
			this.login = login;
			this.result = result;
		}

		private static LoginCheckerResult createSuccess(CookieLoginInfo login) {
			return new LoginCheckerResult(login, SUCCESS);
		}

		private static LoginCheckerResult createError(String result) {
			return new LoginCheckerResult(null, result);
		}

		public boolean isSuccess() {
			return SUCCESS.equals(result);
		}
		public CookieLoginInfo getLogin() {
			return login;
		}
		public long getMemberId() {
			if(!login.isLogin()) {
				return -1;
			}
			return login.getMemberId();
		}
		public String getResult() {
			return result;
		}
	}
}
