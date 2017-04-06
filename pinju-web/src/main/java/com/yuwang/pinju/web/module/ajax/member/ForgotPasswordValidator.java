package com.yuwang.pinju.web.module.ajax.member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.security.ForgotPasswordDO;
import com.yuwang.pinju.web.struts2.PinjuAction;

public class ForgotPasswordValidator implements PinjuAction {

	private final static Log log = LogFactory.getLog(ForgotPasswordValidator.class);
	
	private String loginName;
	
	private String mobile;
	
	private String email;
	
	private String sel;
	
	private String errorCode = SUCCESS;
	
	private MemberSecurityAO memberSecurityAO;
	
	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}

	@Override
	public String execute() throws Exception {
		if (EmptyUtil.isBlank(loginName)) {
			log.warn("nickname validator, but parameter nickname is null or empty, nickname = [" + loginName + "]");
			errorCode = ForgotPasswordDO.CODE_NOT_MEMBER;
			return SUCCESS;
		}
		Result result = memberSecurityAO.checkViewLoginMobileAndEmail(new ForgotPasswordDO(loginName, mobile, email, sel));
		if (result.isSuccess()) {
			return SUCCESS;
		}
		//会员不存在
		if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			errorCode = ForgotPasswordDO.CODE_NOT_MEMBER;
		}
		//会员email 不存在
		if (MemberResultConstant.RESULT_MEMBER_EMAIL_NOT_EXIST.equals(result.getResultCode())) {
			errorCode = ForgotPasswordDO.CODE_NOT_EMAIL;
		}
		//会员手机号码不存在
		if (MemberResultConstant.RESULT_MOBILE_NOT_EXISTS.equals(result.getResultCode())) {
			errorCode = ForgotPasswordDO.CODE_NOT_MOBILE;
		}
		//该邮箱不属于该会员
		if (MemberResultConstant.RESULT_MEMBER_EMAIL_NOT_EQUAL.equals(result.getResultCode())) {
			errorCode = ForgotPasswordDO.CODE_MEMBER_EMAIL;
		}
		//该手机不属于该会员
		if (MemberResultConstant.RESULT_MEMBER_MOBILE_NOT_EQUAL.equals(result.getResultCode())) {
			errorCode = ForgotPasswordDO.CODE_MEMBER_MOBILE;
		}
		return SUCCESS;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
