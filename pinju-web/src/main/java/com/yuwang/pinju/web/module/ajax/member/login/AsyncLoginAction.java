package com.yuwang.pinju.web.module.ajax.member.login;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.api.OpenApiTokenAction;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.member.ao.PinjuMemberAO;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.transmission.manager.SecurityTransManager;
import com.yuwang.pinju.core.util.CharsetUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.auth.AuthParams;
import com.yuwang.pinju.domain.member.login.MemberAjaxLoginResultVO;
import com.yuwang.pinju.domain.member.login.MemberAjaxLoginVO;
import com.yuwang.pinju.web.listener.EventProcess;
import com.yuwang.pinju.web.listener.login.AfterLoginProcess;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>异步登录处理</p>
 *
 * @author gaobaowen
 * @since 2011-11-24 14:33:24
 */
public class AsyncLoginAction implements PinjuAction, ModelDriven<MemberAjaxLoginVO> {

	private final static Log log = LogFactory.getLog(AsyncLoginAction.class);

	private PinjuMemberAO pinjuMemberAO;
	private MemberAO memberAO;
	private MemberSecurityManager memberSecurityManager;
	private SecurityTransManager securityTransManager;

	private MemberAjaxLoginVO login;
	private MemberAjaxLoginResultVO result;

	public AsyncLoginAction() {
		login = new MemberAjaxLoginVO();
	}

	@Override
	public String execute() throws Exception {

		String method = ServletActionContext.getRequest().getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			if (StringUtils.isBlank(login.getReturnUrl())) {
				login.setReturnUrl(PinjuConstant.PINJU_SERVER);
			}
			return LOGIN;
		}

		boolean showCaptcha = login.checkCaptcha();
		String returnUrl = login.getReturnUrl();

		// 禁用 HTTP 缓存
		ServletUtil.forbidBrowserCache();

		if ( !securityTransManager.decryptProperties(login.getTid(), login) ) {
			log.warn("decrypt login data error, client ip: [" + ServletUtil.getRemoteIp() + "], " + login);
			return wrapErrorKey(MessageName.MEMBER_LOGINNAME_NOT_FOUND, showCaptcha);
		}

		ActionInvokeResult air = new ActionInvokeResult(login);
		if (!air.validate()) {
			log.warn("check login parameter failed, " + login);
			for(Map.Entry<String, String> entry : air.getValidator().entrySet()) {
				return wrapError(entry.getValue(), showCaptcha);
			}
		}

		Result result = pinjuMemberAO.pinjuLogin(login.toMemberLogin());

		String code = result.getResultCode();

		if (log.isDebugEnabled()) {
			log.debug("do pinju login, login: " + login + ", invoke result: " + code);
		}

		Number errorCount = (Number)result.getModel(MemberKeyConstant.KEY_LOGIN_OVER_ERROR_COUNT);
		if (errorCount != null) {
			showCaptcha = true;
			log.warn("login user error count over limit, need show capatcha, error count: " + errorCount + ", login info: " + login);
		}

		if (!processError(result, showCaptcha)) {
			return ERROR;
		}

		// 登录成功
		MemberDO member = result.getModel(MemberDO.class);

		EventProcess event = new AfterLoginProcess(memberAO, member, login.getReferer(), login.getReturnUrl());
		boolean login = event.process(log);
		if (!login) {
			log.warn("register pinju member login process result is failed, " + login);
			return wrapErrorKey(MessageName.OPERATE_INVALID, showCaptcha);
		}
		returnUrl = ServletUtil.processReturnUrl(returnUrl);

		if (log.isDebugEnabled()) {
			log.debug("login return url: " + returnUrl);
		}

		AuthParams auth = result.getModel(AuthParams.class);
		if (auth != null) {
			String param1 = memberSecurityManager.encryptMessageBase64(auth.getToken());
			String param3 = String.valueOf(System.currentTimeMillis());
			String param2 = memberSecurityManager.macMessageBase64(auth.getToken() + param3);
			returnUrl = MessageFormat.format(OpenApiTokenAction.TOKEN_URL_TEMPLATE, CharsetUtil.encodeURL(param1), CharsetUtil.encodeURL(param2), param3);
			log.info("[OPEN-API] current member is open api login, " +
					"login member id: [" + member.getMemberId() + "], " +
					"app key: [" + auth.getAppKey() + "], " +
					"token: [" + auth.getToken() + "], " +
				    "is success? [" + auth.getSuccess() + "]");
		}
		return wrapSuccess(returnUrl);
	}

	private boolean processError(Result result, boolean showCaptcha) {
		if (result.isSuccess()) {
			return true;
		}
		if (MemberResultConstant.RESULT_CAPTCHA_ERROR.equals(result.getResultCode())) {
			log.warn("login user captcha input error, " + login);
			wrapErrorKey(MessageName.OPERATE_CAPTCHA_ERROR, showCaptcha);
			return false;
		}
		if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			log.warn("login user account is not exist, " + login);
			wrapErrorKey(MessageName.MEMBER_LOGINNAME_NOT_FOUND, showCaptcha);
			return false;
		}
		if (MemberResultConstant.RESULT_PINJU_LOGIN_ACCOUNT_BLOCK.equals(result.getResultCode())) {
			log.warn("login user account was blocked, " + login);
			wrapErrorKey(MessageName.MEMBER_LOGIN_ACCOUNT_BLOCK, showCaptcha);
			return false;
		}
		if (MemberResultConstant.RESULT_PINJU_LOGIN_ACCOUNT_MASTER_BLOCK.equals(result.getResultCode())) {
			log.warn("login user account was master account blocked, " + login);
			wrapErrorKey(MessageName.MEMBER_LOGIN_ACCOUNT_MASTER_BLOCK, showCaptcha);
			return false;
		}
		if (MemberResultConstant.RESULT_PINJU_LOGIN_ERROR.equals(result.getResultCode())) {
			log.warn("login user account password error, " + login);
			wrapErrorKey(MessageName.MEMBER_PASSWORD_ERROR, showCaptcha);
			return false;
		}
		if (MemberResultConstant.RESULT_PARAMETERS_ERROR.equals(result.getResultCode())) {
			log.warn("login user parameter error, " + login);
			wrapErrorKey(MessageName.MEMBER_LOGIN_PARAMETER_ERROR, showCaptcha);
			return false;
		}
		wrapErrorKey(MessageName.MEMBER_LOGIN_FAILED, showCaptcha);
		return false;
	}

	private String wrapSuccess(String returnUrl) {
		result = MemberAjaxLoginResultVO.createSuccess(returnUrl);
		return SUCCESS;
	}

	private String wrapError(String message, boolean showCaptcha) {
		result = MemberAjaxLoginResultVO.createError(message, showCaptcha);
		return ERROR;
	}

	private String wrapErrorKey(String messageKey, boolean showCaptcha) {
		return wrapError(Message.getMessage(messageKey), showCaptcha);
	}

	public void setPinjuMemberAO(PinjuMemberAO pinjuMemberAO) {
		this.pinjuMemberAO = pinjuMemberAO;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public void setMemberSecurityManager(MemberSecurityManager memberSecurityManager) {
		this.memberSecurityManager = memberSecurityManager;
	}

	public void setSecurityTransManager(SecurityTransManager securityTransManager) {
		this.securityTransManager = securityTransManager;
	}

	@Override
	public MemberAjaxLoginVO getModel() {
		return login;
	}

	public MemberAjaxLoginResultVO getResult() {
		return result;
	}
}
