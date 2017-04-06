package com.yuwang.pinju.web.module.member.screen.asst;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.core.transmission.manager.SecurityTransManager;
import com.yuwang.pinju.domain.member.asst.MemberAsstRegisterDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;
import com.yuwang.pinju.web.annotatioin.MasterAccount;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.member.screen.MemberRegisterAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * 第三方会员登录
 * @author zhaowenm
 *
 */
public class MemberAsstManagerAction implements PinjuAction, ModelDriven<MemberAsstRegisterDO> {

	private final static Log log = LogFactory.getLog(MemberRegisterAction.class);

	private MemberAsstRegisterDO asstRegister;
	private SecurityTransManager securityTransManager;
	private MemberAsstAO memberAsstAO;
	private List<MemberAsstRoleDO> memberAsstRoleList;

	private String inputAsstMemberId; 

	public void setMemberAsstAO(MemberAsstAO memberAsstAO) {
		this.memberAsstAO = memberAsstAO;
	}

	public void setSecurityTransManager(SecurityTransManager securityTransManager) {
		this.securityTransManager = securityTransManager;
	}

	public MemberAsstManagerAction() {
		this.asstRegister = new MemberAsstRegisterDO();
	}

	@SuppressWarnings("unchecked")
	@Override
	@MasterAccount
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("member has not login");
			ServletUtil.loginCurrentResultUrl();
			return MAIN_PAGE;
		}
		Result result = memberAsstAO.getMemberMasterRole(login.getMemberId());
		if (!result.isSuccess()) {
			 return PAGE_500;
		}
		memberAsstRoleList = result.getModel(ArrayList.class);
		return SUCCESS;
	}

	@MasterAccount
	public String asstRegister() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("member has not login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		String method = ServletActionContext.getRequest().getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			return MAIN_PAGE;
		}

		ServletUtil.forbidBrowserCache();

		if (!securityTransManager.decryptProperties(asstRegister.getTid(), asstRegister) ) {
			log.warn("decrypt register data error, client ip: [" + ServletUtil.getRemoteIp() + "], " + asstRegister);
			ActionInvokeResult.setInvokeMessageKey("member.register.security.failed");
			return processRegisterError(login.getMemberId());
		}

		if (!asstRegister.validateInputRole()) {
			return processRegisterError(login.getMemberId());
		}

		ActionInvokeResult air = new ActionInvokeResult(asstRegister);
		if (!air.validate()) {
			log.info("register validate has problem");
			return processRegisterError(login.getMemberId());
		}

		asstRegister.setMasterMemberId(login.getMemberId());
		asstRegister.setRegisterIp(ServletUtil.getRemoteIp());
		asstRegister.setMasterLoginName(login.getNickname());
		Result result = memberAsstAO.registerAsstAccount(asstRegister);

		if (MemberResultConstant.RESULT_ASST_ACCOUNT_ROLE_NO_EXIST.equals(result.getResultCode())) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_ASST_ACCOUNT_ROLE_NO_EXIST);
			return processRegisterError(login.getMemberId());
		}

		// 会员名中含有争议词
		if (MemberResultConstant.RESULT_MEMBER_NICKNAME_WORDS_INVALID.equals(result.getResultCode())) {
			log.warn("login name [" + asstRegister.getLoginName() + "] has invalid words");
			air.addMessageKey("loginName", MessageName.MEMBER_LOGINNAME_INVALID);
			return processRegisterError(login.getMemberId());
		}

		// 会员名中含有敏感词
		if (MemberResultConstant.RESULT_INSENSIVE_WORDS.equals(result.getResultCode())) {
			log.warn("login name [" + asstRegister.getLoginName() + "] has insensive words");
			air.addMessageKey("loginName", MessageName.MEMBER_LOGINNAME_INSENSIVE);
			return processRegisterError(login.getMemberId());
		}

		// 会员名已经被使用
		if (MemberResultConstant.RESULT_MEMBER_NICKNAME_HAS_EXIST.equals(result.getResultCode())) {
			log.warn("register pinju member login name has been used by other member, login name: " + asstRegister.getLoginName());
			air.addMessage("loginName", Message.getMessage(MessageName.MEMBER_LOGINNAME_HAS_BEEN_USED));
			return processRegisterError(login.getMemberId());
		}

		// 其他情况，注册失败
		if (!result.isSuccess()) {
			log.warn("register pinju member result is failed, result: " + result.getResultCode() + ", register: " + asstRegister);
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_INVALID);
			return processRegisterError(login.getMemberId());
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@MasterAccount
	public String findMemberAsst() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("member has not login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		String method = ServletActionContext.getRequest().getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			return INPUT;
		}

		long masterMemberId = login.getMemberId();
		long dAsstMemberId = MemberIdPuzzle.decode(inputAsstMemberId);
		if (dAsstMemberId == -1) {
			return PAGE_500;
		}
		Result result = memberAsstAO.getMemberAsstRelationRole(masterMemberId, dAsstMemberId);
		memberAsstRoleList = result.getModel(ArrayList.class);
		MemberAsstRelationDO memberAsstRelation = result.getModel(MemberAsstRelationDO.class);
		if (memberAsstRelation == null) {
			return MAIN_PAGE;
		}
		asstRegister.setLoginName(memberAsstRelation.getAsstLoginName());
		asstRegister.setAsstAcctDesc(memberAsstRelation.getAsstAcctDesc());
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private String processRegisterError(long memberId) throws Exception {
		Result result = memberAsstAO.getMemberMasterRole(memberId);
		memberAsstRoleList = result.getModel(ArrayList.class);
		for (MemberAsstRoleDO memberAsstRole : memberAsstRoleList) {
			Long[] roleIds = asstRegister.getRoleId();
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

	@Override
	public MemberAsstRegisterDO getModel() {
		return asstRegister;
	}

	public List<MemberAsstRoleDO> getMemberAsstRoleList() {
		return memberAsstRoleList;
	}

	public void setMemberAsstRoleList(List<MemberAsstRoleDO> memberAsstRoleList) {
		this.memberAsstRoleList = memberAsstRoleList;
	}

	public String getInputAsstMemberId() {
		return inputAsstMemberId;
	}

	public void setInputAsstMemberId(String inputAsstMemberId) {
		this.inputAsstMemberId = inputAsstMemberId;
	}
}
