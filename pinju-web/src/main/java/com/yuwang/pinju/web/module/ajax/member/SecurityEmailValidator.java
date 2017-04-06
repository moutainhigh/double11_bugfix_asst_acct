package com.yuwang.pinju.web.module.ajax.member;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;

public class SecurityEmailValidator implements PinjuAction {

	private MemberSecurityAO memberSecurityAO;
	
	private String email;
	
	private boolean result = false;
	
	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}

	/**
	 * <p>检查邮箱是否符合邮箱规范,如果符合规范检查邮箱是否已经被注册</p>
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {

		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		long memberId = login.isLogin() ? login.getMemberId() : 0;
		Result checkResult = memberSecurityAO.checkEmail(email, memberId);
		if (checkResult.isSuccess()) {
			result = true;
		}
		return SUCCESS;
	
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

}
