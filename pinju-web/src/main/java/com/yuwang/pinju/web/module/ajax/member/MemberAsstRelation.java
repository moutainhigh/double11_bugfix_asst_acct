package com.yuwang.pinju.web.module.ajax.member;

import com.opensymphony.xwork2.Action;
import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.system.ServletUtil;

public class MemberAsstRelation implements Action {

	private MemberAsstAO memberAsstAO;

	private String status;
	
	private String asstMemberId;
	
    private boolean result = false;
	
	@Override
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin() && login.isMasterAccount()) {
			ServletUtil.loginCurrentResultUrl();
			return SUCCESS;
		}
		long masterMemberId = login.getMemberId();
		if (!NumberUtil.isInteger(status)) {
			return SUCCESS;
		}
		long dAsstMemberId = MemberIdPuzzle.decode(asstMemberId);
		if (dAsstMemberId == -1) {
			return SUCCESS;
		}

		Result delResult = memberAsstAO.setMemberIsvalid(masterMemberId, dAsstMemberId, Integer.parseInt(status));
		if (!delResult.isSuccess()) {
			return SUCCESS;
		}
		result = true;
		return SUCCESS;
	}

	public void setMemberAsstAO(MemberAsstAO memberAsstAO) {
		this.memberAsstAO = memberAsstAO;
	}

	public void setAsstMemberId(String asstMemberId) {
		this.asstMemberId = asstMemberId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
