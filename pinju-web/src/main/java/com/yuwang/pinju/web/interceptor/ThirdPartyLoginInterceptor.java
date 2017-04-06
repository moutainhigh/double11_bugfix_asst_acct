package com.yuwang.pinju.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.system.ServletUtil;

public class ThirdPartyLoginInterceptor extends AbstractInterceptor {

	private final static Log log = LogFactory.getLog(LoginInterceptor.class);
	
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("current member has not login, need re-login");
			return toLogin(login);
		}
		
		int memberOrign = login.getMemberOrign();
		
		boolean isViewMenu = (memberOrign == MemberDO.MEMBER_ORIGIN_SINA)?false:true;
		
		ServletActionContext.getRequest().setAttribute("isViewMenu", isViewMenu);
		return invocation.invoke();
	}

	private String toLogin(CookieLoginInfo login) {
		login.logout();
		ServletUtil.loginCurrentResultUrl();
		return "pinju-login";
	}
}
