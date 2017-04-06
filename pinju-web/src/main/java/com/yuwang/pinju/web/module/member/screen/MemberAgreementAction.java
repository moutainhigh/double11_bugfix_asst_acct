package com.yuwang.pinju.web.module.member.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberAgreementDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.cookie.PinjuCookieManager.LoginRelationCookie;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.system.SysConfig;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

public class MemberAgreementAction implements PinjuAction, ModelDriven<MemberAgreementDO> {

	private final static Log log = LogFactory.getLog(MemberAgreementAction.class);

	private MemberAgreementDO agreement;
	private String returnUrl;
	private MemberAO memberAO;

	public MemberAgreementAction() {
		this.agreement = new MemberAgreementDO();
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String accept() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			log.warn("login has not login");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		log.info("accept user agreement, member id: " + login.getMemberId());

		ActionInvokeResult air = new ActionInvokeResult(agreement);
		if (!air.validate()) {
			log.warn("MemberAgreement object value validated failed, " + agreement);
			return INPUT;
		}

		agreement.setMemberId(login.getMemberId());

		Result result = memberAO.acceptAgreement(agreement);

		String resultCode = result.getResultCode();

		if (log.isDebugEnabled()) {
			log.debug("process accept agreement result: " + resultCode);
		}

		// SUCCESS
		// RESULT_MEMBER_ACCEPT_AGREEMENT
		if(result.isSuccess() || MemberResultConstant.RESULT_MEMBER_ACCEPT_AGREEMENT.equals(resultCode)) {
			log.info("user has been accepted agreement, " + agreement);
			LoginRelationCookie relation = new LoginRelationCookie(login);
			relation.setNickname(agreement.getNickname());
			relation.setAcceptAgreement(MemberDO.AGREE_AGREEMENT_YES);
			PinjuCookieManager.writeLogin(relation);
			return SUCCESS;
		}

		// RESULT_MEMBER_MEMBER_NOT_EXIST
		if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(resultCode)) {
			log.warn("cannot find member, member id: " + login.getMemberId());
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		// RESULT_MEMBER_INNER_ERROR
		// RESULT_PARAMETERS_ERROR
		log.warn("server inner error, member id: " + login.getMemberId());
		ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_FAILED);
		return ERROR;
	}

	public String getReturnUrl() {
		return EmptyUtil.isBlank(returnUrl) ? SysConfig.PINJU_WWW_MAIN_PAGE : returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public MemberAO getMemberAO() {
		return memberAO;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	@Override
	public MemberAgreementDO getModel() {
		return agreement;
	}
}
