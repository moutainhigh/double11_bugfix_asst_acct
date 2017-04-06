package com.yuwang.pinju.web.module.my.screen;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.domain.member.security.MemberSecurityCenterDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.MemberCheckAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * 显示安全中心信息
 *
 */
public class SecurityCenterAction extends MemberCheckAction implements PinjuAction {

	private MemberSecurityAO memberSecurityAO;

	private MemberSecurityCenterDO memberSecurityCenter;
	
	private Long userMemberId;
	
	private Long version;

	public SecurityCenterAction() {
		memberSecurityCenter = new MemberSecurityCenterDO();
	}

	@Override
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		Long memberId = login.getMemberId();
		Result result = memberSecurityAO.getSecurityCenter(memberId);
		if (!result.isSuccess()) {
			return LOGIN;
		}
        userMemberId = memberId;
        version = login.getInfoVersion();
		memberSecurityCenter = result.getModel(MemberSecurityCenterDO.class);
		return SUCCESS;
	}

	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}

	public MemberSecurityCenterDO getMemberSecurityCenter() {
		return memberSecurityCenter;
	}

	public void setMemberSecurityCenter(MemberSecurityCenterDO memberSecurityCenter) {
		this.memberSecurityCenter = memberSecurityCenter;
	}
	
	public Long getUserMemberId() {
		return userMemberId;
	}

	public void setUserMemberId(Long userMemberId) {
		this.userMemberId = userMemberId;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
