package com.yuwang.pinju.web.module.security.screen;

import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_CAPTCHA_ERROR;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_EMAIL_NOT_EXIST;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_PARAMETERS_ERROR;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.domain.member.security.ForgotPasswordDO;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>找回密码</p>
 *
 * @author gaobaowen
 * @since 2011-9-8 14:21:31
 */
public class ForgotPasswordAction implements PinjuAction, ModelDriven<ForgotPasswordDO> {

	private final static Log log = LogFactory.getLog(ForgotPasswordAction.class);

	private MemberSecurityAO memberSecurityAO;
	private ForgotPasswordDO model;

	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}

	public ForgotPasswordAction() {
		this.model = new ForgotPasswordDO();
	}

	@Override
	public String execute() throws Exception {
		model.setSid(PinjuCookieManager.getHashSessionId(ForgotPasswordAction.class));
		ServletUtil.forbidBrowserCache();
		return SUCCESS;
	}

	public String forgotPasswordEmail() {		
		String method = ServletActionContext.getRequest().getMethod();
		// 不允许除 POST 方法的访问！
		if(!"POST".equalsIgnoreCase(method)) {
			log.warn("user request this update using HTTP/GET");
			return NOT_ALLOWED_METHOD;
		}

		ServletUtil.forbidBrowserCache();
		try {
			if (!model.isEmail() && !model.isMobile()) {
				log.warn("find pswd email and mobile is not select");
				return NOT_ALLOWED_METHOD;
			}
			
			ActionInvokeResult invoke = new ActionInvokeResult(model);
			if (model.isEmail()) {
				if (!invoke.validate("loginName", "email", "captcha")) {
					model.setSid(PinjuCookieManager.getHashSessionId(ForgotPasswordAction.class));
					return INPUT;
				}
			} else {
				if (!invoke.validate("loginName", "mobile", "captcha")) {
					model.setSid(PinjuCookieManager.getHashSessionId(ForgotPasswordAction.class));
					return INPUT;
				}
			}
			model.setIp(ServletUtil.getRemoteIp());
			if (log.isDebugEnabled()) {
				log.debug("forgot password parameter: " + model);
			}
			Result valiateResult = memberSecurityAO.checkLoginNameMobileEmail(model);
			if (!processValidateError(invoke, valiateResult)) {
				model.setSid(PinjuCookieManager.getHashSessionId(ForgotPasswordAction.class));
				return INPUT;
			}
			
			if (model.isEmail()) {
				Result result = memberSecurityAO.sendRetrievePasswordEmail(model);
				if (log.isInfoEnabled()) {
					log.info("forgot password ao invoke result code: " + result.getResultCode() + ", request parameter: " + model);
				}
				if (result.isSuccess()) {
					ServletUtil.forbidBrowserCache();
					return SUCCESS;
				}
				return processError(invoke, result);
			}
			model.setParam7(memberSecurityAO.macLoginName(model.getLoginName()));
			return SUCCESS + "-mobile";
		} catch (Throwable e) {
			log.error("forgot password email cause exception, " + model, e);
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_FAILED);
			return INPUT;
		}
	}

	@Override
	public ForgotPasswordDO getModel() {
		return model;
	}
	
	private boolean processValidateError(ActionInvokeResult invoke, Result result) {
		//会员不存在
		if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			invoke.addMessage("loginName", Message.getMessage(MessageName.MEMBER_NOT_EXIST));
			return false;
		}
		//会员email 不存在
		if (MemberResultConstant.RESULT_MEMBER_EMAIL_NOT_EXIST.equals(result.getResultCode())) {
			invoke.addMessage("email", Message.getMessage(MessageName.MEMBER_EMAIL_NOT_EXIST));
			return false;
		}
		//会员手机号码不存在
		if (MemberResultConstant.RESULT_MOBILE_NOT_EXISTS.equals(result.getResultCode())) {
			invoke.addMessage("mobile", Message.getMessage(MessageName.MEMBER_MOBILE_NOT_EXIST));
			return false;
		}
		//该邮箱不属于该会员
		if (MemberResultConstant.RESULT_MEMBER_EMAIL_NOT_EQUAL.equals(result.getResultCode())) {
			invoke.addMessage("loginName", Message.getMessage(MessageName.MEMEBR_EMAIL_NOT_EQUAL));
			return false;
		}
		//该手机不属于该会员
		if (MemberResultConstant.RESULT_MEMBER_MOBILE_NOT_EQUAL.equals(result.getResultCode())) {
			invoke.addMessage("loginName", Message.getMessage(MessageName.MEMEBR_MOBILE_NOT_EQUAL));
			return false;
		}
		//验证吗不正确
		if (RESULT_CAPTCHA_ERROR.equals(result.getResultCode())) {
			invoke.addMessage("captcha", Message.getMessage(MessageName.OPERATE_CAPTCHA_ERROR));
			return false;
		}
		
		if (MemberResultConstant.RESULT_MEMBER_INNER_ERROR.equals(result.getResultCode())) {
			invoke.addMessage("loginName", Message.getMessage(MessageName.OPERATE_FAILED));
			return false;
		}
		return true;
	}

	private String processError(ActionInvokeResult invoke, Result result) {
		if (RESULT_MEMBER_EMAIL_NOT_EXIST.equals(result.getResultCode())) {
			log.warn("process error, ao invoke result is email not exist, ip: " + ServletUtil.getRemoteIp() + ", " + model);
			invoke.addMessage("email", Message.getMessage(MessageName.RETRIEVE_PASSWORD_EMAIL_NOT_EXISTS));
			return INPUT;
		}

		if (RESULT_PARAMETERS_ERROR.equals(result.getResultCode())) {
			log.warn("process error, ao invoke result is parameter error, ip: " + ServletUtil.getRemoteIp() + ", " + model);
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_SUBMIT_PARAMETER_ERROR);
			return INPUT;
		}

		log.warn("process error, ao invoke result is OTHER error, ip: " + ServletUtil.getRemoteIp() + ", " + model);

		// RESULT_MEMBER_INNER_ERROR：内部错误
		ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_FAILED);
		return INPUT;
	}
}
