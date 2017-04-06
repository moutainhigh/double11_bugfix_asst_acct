package com.yuwang.pinju.web.module.member.screen.asst;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.domain.member.asst.InputMemberRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstMemberRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;
import com.yuwang.pinju.web.annotatioin.MasterAccount;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

public class MemberAsstAction extends BaseAction implements PinjuAction, ModelDriven<InputMemberRelationDO>{

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(MemberAsstAction.class);

	private InputMemberRelationDO inputMemberRelation;
	private MemberAsstRelationQuery query;
	private List<MemberAsstRelationDO> memberAsstRelationList;
	private List<MemberAsstMemberRoleDO> memberAsstMemberRoles;
	private List<MemberAsstRoleDO> memberAsstRoleList;

	private MemberAsstAO memberAsstAO;

	public void setMemberAsstAO(MemberAsstAO memberAsstAO) {
		this.memberAsstAO = memberAsstAO;
	}

	public MemberAsstAction() {
		query = new MemberAsstRelationQuery(); 
		inputMemberRelation = new InputMemberRelationDO();
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
		Result result = memberAsstAO.findMemberAsstRelation(query);
		if (!result.isSuccess()) {
			return ERROR;
		}
		memberAsstRelationList = result.getModel(ArrayList.class);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@MasterAccount
	public String editMemberAsst() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("member has not login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		long masterMemberId = login.getMemberId();
		long dAsstMemberId = MemberIdPuzzle.decode(inputMemberRelation.getInputAsstMemberId());
		if(!inputMemberRelation.validateAsstMemberId(dAsstMemberId)) {
			log.warn("inputMemberRelation param is error, inputMemberRelation=" + inputMemberRelation);
			return processRegisterError(masterMemberId);
		}
		inputMemberRelation.setMasterMemberId(masterMemberId);
		Result result = memberAsstAO.eidtAsstRelationRole(inputMemberRelation);
		if (result.isSuccess()) {
			memberAsstMemberRoles = result.getModel(ArrayList.class);
			return SUCCESS;
		}
		if (MemberResultConstant.RESULT_ASST_ACCOUNT_ROLE_NO_EXIST.equals(result.getResultCode())) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_ASST_ACCOUNT_ROLE_NO_EXIST);
		}
		return processRegisterError(masterMemberId);
	}

	@SuppressWarnings("unchecked")
	private String processRegisterError(long memberId) throws Exception {
		Result result = memberAsstAO.getMemberMasterRole(memberId);
		memberAsstRoleList = result.getModel(ArrayList.class);
		for (MemberAsstRoleDO memberAsstRole : memberAsstRoleList) {
			Long[] roleIds = inputMemberRelation.getRoleId();
			if (roleIds == null || roleIds.length == 0) {
				break;
			}
			for (int i = 0; i < roleIds.length; i++) {
				if (roleIds[i] != null && roleIds[i].equals(memberAsstRole.getId())) {
					memberAsstRole.setChecked(true);
				}
			}
		}
		return INPUT;
	}

	public InputMemberRelationDO getInputMemberRelation() {
		return inputMemberRelation;
	}

	public void setInputMemberRelation(InputMemberRelationDO inputMemberRelation) {
		this.inputMemberRelation = inputMemberRelation;
	}

	public List<MemberAsstRelationDO> getMemberAsstRelationList() {
		return memberAsstRelationList;
	}

	public void setMemberAsstRelationList(
			List<MemberAsstRelationDO> memberAsstRelationList) {
		this.memberAsstRelationList = memberAsstRelationList;
	}

	public MemberAsstRelationQuery getQuery() {
		return query;
	}

	public void setQuery(MemberAsstRelationQuery query) {
		this.query = query;
	}

	public List<MemberAsstMemberRoleDO> getMemberAsstMemberRoles() {
		return memberAsstMemberRoles;
	}

	public void setMemberAsstMemberRoles(
			List<MemberAsstMemberRoleDO> memberAsstMemberRoles) {
		this.memberAsstMemberRoles = memberAsstMemberRoles;
	}
	
	public List<MemberAsstRoleDO> getMemberAsstRoleList() {
		return memberAsstRoleList;
	}

	public void setMemberAsstRoleList(List<MemberAsstRoleDO> memberAsstRoleList) {
		this.memberAsstRoleList = memberAsstRoleList;
	}

	@Override
	public InputMemberRelationDO getModel() {
		return inputMemberRelation;
	}
}
