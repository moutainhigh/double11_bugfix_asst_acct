package com.yuwang.pinju.web.interceptor;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

public class AgreeAgreementInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(AgreeAgreementInterceptor.class);

	private String[] excludes;

	public String[] getExcludes() {
		return excludes;
	}

	public void setExcludes(String commaExcludes) {
		if(EmptyUtil.isBlank(commaExcludes)) {
			excludes = new String[0];
			return;
		}
		excludes = commaExcludes.split(",");
		for(int i = 0; i < excludes.length; i++) {
			excludes[i] = excludes[i].trim();
		}
		log.debug("following actions are ignored to check user agreement (namespace:action), " + Arrays.toString(excludes));
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin() && !login.isAgreeAgreement() && isFilterAction(invocation)) {
			log.debug("current user has been logon, member id: [" + login.getMemberId() + "], agreeAgreement: [" + login.getAgreeAgreement() + "]");
			ServletUtil.loginCurrentResultUrl();
			return PinjuAction.AGREEMENT;
		}
		return invocation.invoke();
	}

	private boolean isFilterAction(ActionInvocation invocation) {
		if(excludes == null || excludes.length == 0) {
			log.info("filter actions is empty, all actions are filtered agreement");
			return true;
		}
		ActionProxy proxy = invocation.getProxy();
		String action = proxy.getNamespace() + ":" + proxy.getActionName();
		for(int i = 0; i < excludes.length; i++) {
			if(excludes[i].equals(action)) {
				if(log.isDebugEnabled()) {
					log.debug("current page need not filter agreement, action: " + action);
				}
				return false;
			}
		}
		if(log.isDebugEnabled()) {
			log.debug("current page need filter agreement, action: " + action);
		}
		return true;
	}
}
