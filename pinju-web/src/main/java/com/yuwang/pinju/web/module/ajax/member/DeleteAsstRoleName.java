package com.yuwang.pinju.web.module.ajax.member;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.system.ServletUtil;

public class DeleteAsstRoleName implements Action {

	private MemberAsstAO memberAsstAO;

	private String roleId;
	
    private boolean result = false;
	
	@Override
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return SUCCESS;
		}
		long masterMemberId = login.getMemberId();
		if (!NumberUtil.isLong(roleId)) {
			return SUCCESS;
		}
		String remoteIp = ServletUtil.getRemoteIp();
		Result delResult = memberAsstAO.deleteMemberAsstRole(masterMemberId, Long.parseLong(roleId), remoteIp);
		if (!delResult.isSuccess()) {
			return SUCCESS;
		}
		result = true;
		return SUCCESS;
	}

	public void setMemberAsstAO(MemberAsstAO memberAsstAO) {
		this.memberAsstAO = memberAsstAO;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
