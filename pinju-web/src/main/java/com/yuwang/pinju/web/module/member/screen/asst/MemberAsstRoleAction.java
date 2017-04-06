package com.yuwang.pinju.web.module.member.screen.asst;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.domain.member.asst.InputMemberAsstRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleQuery;
import com.yuwang.pinju.web.annotatioin.MasterAccount;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.member.screen.MemberRegisterAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * 子账号角色管理
 */
public class MemberAsstRoleAction implements PinjuAction, ModelDriven<InputMemberAsstRoleDO> {

	private final static Log log = LogFactory.getLog(MemberRegisterAction.class);

	private InputMemberAsstRoleDO inputMemberAsstRole;
	private MemberAsstAO memberAsstAO;
	private MemberAsstRoleQuery query;
	private List<MemberAsstRoleDO> memberAsstRoleList;
	private String jsonString;

	public void setMemberAsstAO(MemberAsstAO memberAsstAO) {
		this.memberAsstAO = memberAsstAO;
	}

	public MemberAsstRoleAction() {
		inputMemberAsstRole = new InputMemberAsstRoleDO();
		query = new MemberAsstRoleQuery();
	}

	@SuppressWarnings("unchecked")
	@Override
	@MasterAccount
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("member has not login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		long masterMemberId = login.getMemberId();
		query.setMasterMemberId(masterMemberId);
		query.setItemsPerPage(10);
		Result result = memberAsstAO.findMemberAsstRole(query);
		if (!result.isSuccess()) {
			return LOGIN;
		}
		memberAsstRoleList = result.getModel(ArrayList.class);
		return SUCCESS;
	}
	
	@MasterAccount
	public String addAsstRole() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("member has not login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		String method = ServletActionContext.getRequest().getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			return processError();
		}

		ActionInvokeResult air = new ActionInvokeResult(inputMemberAsstRole);
		if (!air.validate()) {
			log.info("register validate has problem");
			return processError();
		}

		inputMemberAsstRole.setMasterMemberId(login.getMemberId());
		inputMemberAsstRole.setLoginName(login.getNickname());
		Result result = memberAsstAO.addAsstRole(inputMemberAsstRole);
		if (result.isSuccess()) {
			return SUCCESS;
		}
		if (MemberResultConstant.RESULT_ASST_ROLE_EXIST.equals(result.getResultCode())) {
			air.addMessageKey("roleName", MessageName.MEMBER_MASTER_ASST_ROLE_EXIST);
		}
		return processError();
	}
	
	@MasterAccount
	public String asstRole() throws Exception {
		jsonString = memberAsstAO.getAsstRolePermission();
		if (jsonString == null) {
			return MAIN_PAGE;
		}
		return SUCCESS;
	}
	
	@MasterAccount
	public String eidtRoleView() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("member has not login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		Long roleId = inputMemberAsstRole.getId();
		Result result = memberAsstAO.getMemberAsstRoleById(login.getMemberId(), roleId);
		if (!result.isSuccess()) {
			return INPUT;
		}
		MemberAsstRoleDO memberAsstRole = result.getModel(MemberAsstRoleDO.class);
		if (memberAsstRole == null) {
			return INPUT;
		}
		inputMemberAsstRole.setRoleName(memberAsstRole.getRoleName());
		inputMemberAsstRole.setRoleDesc(memberAsstRole.getRoleDesc());
		inputMemberAsstRole.setPermissions(memberAsstRole.getPermissions());
		inputMemberAsstRole.setPermissionIds(String.valueOf(result.getModel("PIDS")));
		jsonString = result.getModel(String.class);
		return SUCCESS;
	}
	
	@MasterAccount
	public String eidtRole() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("member has not login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		inputMemberAsstRole.setMasterMemberId(login.getMemberId());
		Result result = memberAsstAO.eidtAsstRole(inputMemberAsstRole);
		if (!result.isSuccess()) {
			return processError();
		}
		jsonString = memberAsstAO.getAsstRolePermission();
		return SUCCESS;
	}
	
	private String processError() {
		jsonString = memberAsstAO.getAsstRolePermission();
		return INPUT;
	}

	@Override
	public InputMemberAsstRoleDO getModel() {
		return inputMemberAsstRole;
	}

	public MemberAsstRoleQuery getQuery() {
		return query;
	}

	public void setQuery(MemberAsstRoleQuery query) {
		this.query = query;
	}

	public List<MemberAsstRoleDO> getMemberAsstRoleList() {
		return memberAsstRoleList;
	}

	public void setMemberAsstRoleList(List<MemberAsstRoleDO> memberAsstRoleList) {
		this.memberAsstRoleList = memberAsstRoleList;
	}
	
	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
}
