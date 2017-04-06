package com.yuwang.pinju.web.module.security.screen;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileCodeDO;
import com.yuwang.pinju.domain.member.security.SmsCodeValidatorVO;
import com.yuwang.pinju.domain.message.SmsMessageType;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.MemberCheckAction;
import com.yuwang.pinju.web.module.security.screen.SecurityLinkAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

public class MobileFindPwdAction extends MemberCheckAction implements PinjuAction, ModelDriven<SmsCodeValidatorVO> {

	private MemberSecurityAO memberSecurityAO;
	private SmsCodeValidatorVO scv;
	private String mid;
	private String hiddenMobile;
	private String param7;

	public MobileFindPwdAction() {
		scv = new SmsCodeValidatorVO();
	}

	public String execute() {
		if (EmptyUtil.isBlank(scv.getMobile()) || EmptyUtil.isBlank(param7)) {
			return ERROR;
		}
		ServletUtil.forbidBrowserCache();
		hiddenMobile = StringUtil.hideMobile(scv.getMobile());
		scv.setClientIp(ServletUtil.getRemoteIp());
		scv.setMessageId(mid);
		scv.setType(SmsMessageType.RETRIEVE_PASSWORD);
		Result result = memberSecurityAO.checkCode(scv, param7);
		if (result.isSuccess()) {
			MemberSecurityMobileCodeDO md = result.getModel(MemberSecurityMobileCodeDO.class);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put(SecurityLinkAction.EMAIL_LINK_OBJECT_PARAM_NAME, md);
			ActionContext.getContext().setParameters(parameters);
			return SUCCESS;
		}
		if (MemberResultConstant.RESULT_PARAMETERS_ERROR.equals(result.getResultCode())) {
			return input(MessageName.OPERATE_SUBMIT_PARAMETER_ERROR);
		}
		if (MemberResultConstant.RESULT_MOBILE_NOT_EXISTS.equals(result.getResultCode())) {
			return input(MessageName.MEMBER_MOBILE_NOT_EXIST);
		}
		if (MemberResultConstant.RESULT_MOBILE_INVALID.equals(result.getResultCode())) {
			return input(MessageName.MEMBER_MOBILE_FAILURE, scv.getMobile());
		}
		if (MemberResultConstant.RESULT_UNCONFIM_CODE_FAILURE.equals(result.getResultCode())) {
			return input(MessageName.MEMBER_MOBILE_UNCONFIM_CODE_FAILURE);
		}
		if (MemberResultConstant.RESULT_MEMBER_INVALID.equals(result.getResultCode())) {
			return ERROR;
		}
		return input(MessageName.OPERATE_INVALID);
	}

	private String input(String messageName, Object... args) {
		ActionInvokeResult.setInvokeMessage(Message.getMessage(messageName, args));
		return INPUT;
	}

	public String mobileUpdatePwd() {
		return SUCCESS;
	}

	public SmsCodeValidatorVO getScv() {
		return scv;
	}

	public void setScv(SmsCodeValidatorVO scv) {
		this.scv = scv;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}

	public String getHiddenMobile() {
		return hiddenMobile;
	}

	public void setHiddenMobile(String hiddenMobile) {
		this.hiddenMobile = hiddenMobile;
	}

	@Override
	public SmsCodeValidatorVO getModel() {
		return scv;
	}

	public String getParam7() {
		return param7;
	}

	public void setParam7(String param7) {
		this.param7 = param7;
	}
}
