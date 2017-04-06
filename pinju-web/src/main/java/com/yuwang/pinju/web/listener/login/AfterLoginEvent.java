package com.yuwang.pinju.web.listener.login;

import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;

public class AfterLoginEvent {

	private MemberAO memberAO;
	private MemberDO member;
	private String referer;
	private String returnUrl;
	private MemberLoginHisDO loginHis;

	public AfterLoginEvent(MemberAO memberAO, MemberDO member, String referer, String returnUrl) {
		this.memberAO = memberAO;
		this.member = member;
		this.referer = EmptyUtil.trim(referer);
		this.returnUrl = EmptyUtil.trim(returnUrl);
	}

	public MemberDO getMember() {
		return member;
	}

	public MemberAO getMemberAO() {
		return memberAO;
	}

	public String getReferer() {
		return referer;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public MemberLoginHisDO getLoginHis() {
		return loginHis;
	}

	public void setLoginHis(MemberLoginHisDO loginHis) {
		this.loginHis = loginHis;
	}
}
