package com.yuwang.pinju.web.module.member.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.system.SysConfig;

/**
 * <p>登录会员平台退出。退出时清除与登录相关的 Cookie 数据。</p>
 * <ul>
 * 	<li>若会员是盛大 CAS 用户时，在清理完 Cookie 后再跳转至盛大 CAS 统一退出 URL，
 * 将平台的登录页面作为盛大统一退出 URL 的返回地址</li>
 *  <li>若会员为非盛大 CAS 用户，在清理完 Cookie 后直接跳转至平台的登录页面</li>
 * </ul>
 *
 * @author gaobaowen
 * 2011-6-4 下午03:56:06
 */
public class MemberLogoutAction implements Action {

	private final static Log log = LogFactory.getLog(MemberLogoutAction.class);

	private final static String SDO_SUCCESS = "sdo-success";

	private MemberAO memberAO;
	private MemberAsstAO memberAsstAO;
	private String returnUrl;

	@Override
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if(!login.isLogin()) {
			log.warn("current user cannot login, jump to login page, " + login);
			return SUCCESS;
		}
		boolean result = login.logout();
		if(log.isDebugEnabled()) {
			log.debug("current user has logout from platform, clear cookie result: " + result + ", " + login);
		}

		if(result) {
			boolean logLogoutResult = memberAO.logout(login.getSessionId(), login.getMemberId(), new Date());
			log.info("current user logout log, log result: " + logLogoutResult);
		}

		if (login.isAssistantAccount()) {
			boolean mr = memberAsstAO.clearAsstPermCache(login.getMemberId());
			log.info("clear assistant member permission cache, member id: [" + login.getMemberId() + "], clear result: [" + mr + "]");
		}

		if(login.isSdoMember()) {
			log.debug("current user IS sdo user, need jump to SDO logout page");
			return SDO_SUCCESS;
		}
		log.debug("current user IS NOT sdo user, need jump to platform login page");
		return SUCCESS;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public void setMemberAsstAO(MemberAsstAO memberAsstAO) {
		this.memberAsstAO = memberAsstAO;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getReturnUrl() {
		return ServletUtil.processReturnUrl(returnUrl, SysConfig.PINJU_LOGIN_URL);
	}
}
