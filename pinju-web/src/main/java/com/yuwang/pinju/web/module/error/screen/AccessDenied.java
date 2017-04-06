package com.yuwang.pinju.web.module.error.screen;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.core.member.manager.asst.MemberAsstPermManager;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionDO;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>子账号访问受限</p>
 *
 * @author gaobaowen
 * @since 2011-12-20 15:02:03
 */
public class AccessDenied implements PinjuAction {

	private String t;
	private String history;
	private String returnUrl;
	private MemberAsstPermissionDO __permission__;
	private MemberAsstPermManager memberAsstPermManager;

	@Override
	public String execute() throws Exception {
		history = ServletUtil.checkReturnUrl(history, "http://www.pinju.com/my/sell.htm");
		returnUrl = ServletUtil.checkReturnUrl(returnUrl, "http://www.pinju.com/my/sell.htm");
		if (StringUtils.isBlank(t)) {
			return SUCCESS;
		}
		String[] targetAction = t.split("\\.");
		if (targetAction == null || targetAction.length != 2) {
			return SUCCESS;
		}
		__permission__ = memberAsstPermManager.getAsstPermission(targetAction[0], targetAction[1]);
		return SUCCESS;
	}

	public void setT(String t) {
		this.t = t;
	}

	public void setMemberAsstPermManager(MemberAsstPermManager memberAsstPermManager) {
		this.memberAsstPermManager = memberAsstPermManager;
	}

	public MemberAsstPermissionDO get__permission__() {
		return __permission__;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
}
