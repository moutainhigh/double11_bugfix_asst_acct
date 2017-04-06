package com.yuwang.pinju.web.module.ajax.member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * <p>
 * 验证用户的昵称是否可用
 * </p>
 *
 * @author gaobaowen
 * @since 2011-6-8 12:41:05
 */
public class RoleNameValidator implements Action {

	private final static Log log = LogFactory.getLog(RoleNameValidator.class);

	private MemberAsstAO memberAsstAO;

	private String roleName;
	private boolean result = false;

	@Override
	public String execute() throws Exception {
		if (EmptyUtil.isBlank(roleName)) {
			log.warn("roleName validator, but parameter roleName is null or empty, roleName = [" + roleName + "]");
			return SUCCESS;
		}

		long memberId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			memberId = login.getMemberId();
			log.warn("current user has been logon, need ignore self's nickname, nickname: [" + login.getNickname() + "]");
		} else {
			log.info("current user has not login, need not ignore self's nickname, nickname: [" + login.getNickname() + "]");
		}

		Result roleResult = memberAsstAO.getMemberMasterRoleByRoleName(memberId, roleName);
		if (!roleResult.isSuccess()) {
			return SUCCESS;
		}
		MemberAsstRoleDO memberAsstRole = roleResult.getModel(MemberAsstRoleDO.class);
		if (memberAsstRole != null) {
			return SUCCESS;
		}
		result = true;
		return SUCCESS;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setMemberAsstAO(MemberAsstAO memberAsstAO) {
		this.memberAsstAO = memberAsstAO;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
