package com.yuwang.pinju.web.module.ajax.member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.json.annotations.JSON;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;

/**
 * <p>检查会员输入的邮箱是否可用</p>
 *
 * @author gaobaowen
 * @since 2011-8-2 09:15:02
 */
public class EmailValidator implements PinjuAction {

	private final static Log log = LogFactory.getLog(EmailValidator.class);

	private MemberAO memberAO;

	private MemberInfoDO memberInfo = new MemberInfoDO();
	private boolean result = false;
	private String email;

	@Override
	public String execute() throws Exception {

		long memberId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			memberId = login.getMemberId();
		}

		Result checkResult = memberAO.checkEmail(email, memberId);

		if (log.isDebugEnabled()) {
			log.debug("email validator, email: " + email + ", check result: " + checkResult.getResultCode());
		}
		if (checkResult.isSuccess()) {
			result = true;
		}
		return SUCCESS;
	}

	public boolean isResult() {
		return result;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JSON(serialize = false)
	public MemberInfoDO getMemberInfo() {
		return memberInfo;
	}
}
