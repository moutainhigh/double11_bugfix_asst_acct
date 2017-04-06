package com.yuwang.pinju.web.module.member.screen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.member.ao.PinjuMemberAO;
import com.yuwang.pinju.core.transmission.manager.SecurityTransManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberRegisterDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.listener.EventProcess;
import com.yuwang.pinju.web.listener.login.AfterLoginProcess;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>品聚会员注册</p>
 *
 * @author gaobaowen
 * @since 2011-7-29 13:27:50
 */
public class MemberRegisterAction implements PinjuAction, ModelDriven<MemberRegisterDO> {

	private final static Log log = LogFactory.getLog(MemberRegisterAction.class);

	private final static Pattern AD_CODE_URL_PARAM = Pattern.compile("https?://[a-z0-9-]{1,63}\\.pinju\\.com.*?[&?]p=([^?&]+)");

	private PinjuMemberAO pinjuMemberAO;
	private MemberAO memberAO;
	private MemberRegisterDO register;
	private SecurityTransManager securityTransManager;
	private String returnUrl;

	public MemberRegisterAction() {
		register = MemberRegisterDO.newPinjuRegister();
		register.setReferer(ServletUtil.getHttpReferer());
	}

	@Override
	public String execute() throws Exception {
		if (isLogin()) {
			return MAIN_PAGE;
		}
		register.setSid(PinjuCookieManager.getHashSessionId(MemberRegisterAction.class));
		processAdCode();
		ServletUtil.forbidBrowserCache();
		return SUCCESS;
	}

	public String register() throws Exception {
		if (isLogin()) {
			return MAIN_PAGE;
		}
		String method = ServletActionContext.getRequest().getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			execute();
			return INPUT;
		}

		ServletUtil.forbidBrowserCache();

		if ( !securityTransManager.decryptProperties(register.getTid(), register) ) {
			log.warn("decrypt register data error, client ip: [" + ServletUtil.getRemoteIp() + "], " + register);
			ActionInvokeResult.setInvokeMessageKey("member.register.security.failed");
			return INPUT;
		}

		ActionInvokeResult air = new ActionInvokeResult(register);
		if (!air.validate()) {
			log.info("register validate has problem");
			return INPUT;
		}
		register.setRegisterIp(ServletUtil.getRemoteIp());
		log.debug(register);
		Result result = pinjuMemberAO.registerPinjuMember(register);

		// 会员名中含有争议词
		if (MemberResultConstant.RESULT_MEMBER_NICKNAME_WORDS_INVALID.equals(result.getResultCode())) {
			log.warn("login name [" + register.getLoginName() + "] has invalid words");
			air.addMessageKey("loginName", MessageName.MEMBER_LOGINNAME_INVALID);
			return INPUT;
		}

		// 会员名中含有敏感词
		if (MemberResultConstant.RESULT_INSENSIVE_WORDS.equals(result.getResultCode())) {
			log.warn("login name [" + register.getLoginName() + "] has insensive words");
			air.addMessageKey("loginName", MessageName.MEMBER_LOGINNAME_INSENSIVE);
			return INPUT;
		}

		// 验证码输入错误
		if (MemberResultConstant.RESULT_CAPTCHA_ERROR.equals(result.getResultCode())) {
			log.warn("register pinju member captcha input error");
			air.addMessage("captcha", Message.getMessage(MessageName.OPERATE_CAPTCHA_ERROR));
			return INPUT;
		}

		// 会员名已经被使用
		if (MemberResultConstant.RESULT_MEMBER_NICKNAME_HAS_EXIST.equals(result.getResultCode())) {
			log.warn("register pinju member login name has been used by other member, login name: " + register.getLoginName());
			air.addMessage("loginName", Message.getMessage(MessageName.MEMBER_LOGINNAME_HAS_BEEN_USED));
			return INPUT;
		}

		// 邮箱已经被使用
		if (MemberResultConstant.RESULT_MEMBER_EMAIL_HAS_EXIST.equals(result.getResultCode())) {
			log.warn("register pinju member email has been used by other member, email: " + register.getEmail());
			air.addMessage("email", Message.getMessage(MessageName.MEMBER_EMAIL_HAS_BEEN_USED));
			return INPUT;
		}

		// 其他情况，注册失败
		if (!result.isSuccess()) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_INVALID);
			log.warn("register pinju member result is failed, result: " + result.getResultCode() + ", register: " + register);
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_REGISTER_FAILED);
			return INPUT;
		}

		// 注册成功
		MemberDO member = result.getModel(MemberDO.class);

		EventProcess event = new AfterLoginProcess(memberAO, member, ServletUtil.getHttpReferer(), returnUrl);
		boolean login = event.process(log);
		if (!login) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_INVALID);
			log.warn("register pinju member login process result is failed");
			return INPUT;
		}
		returnUrl = ServletUtil.processReturnUrl(returnUrl);
		return SUCCESS;
	}

	/**
	 * <p>处理 returnUrl 中的广告链接，若 URL 参数中已经拥有广告链接参数，则不再从 returnUrl 中获取</p>
	 *
	 * @author gaobaowen
	 * @since 2011-9-15 15:32:08
	 */
	private void processAdCode() {
		if (register.isAdCode()) {
			return;
		}
		if (EmptyUtil.isBlank(returnUrl)) {
			return;
		}
		Matcher matcher = AD_CODE_URL_PARAM.matcher(returnUrl);
		while (matcher.find()) {
			register.setCode(matcher.group(1));
		}
	}

	private boolean isLogin() {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		boolean result = login.isLogin();
		if (log.isDebugEnabled()) {
			log.debug("current member login status: " + result);
		}
		if (result) {
			log.warn("current has login status, jump to main page, current member: " + login);
		}
		return result;
	}

	public String registerSuccess() {
		return SUCCESS;
	}

	@Override
	public MemberRegisterDO getModel() {
		return register;
	}

	public void setPinjuMemberAO(PinjuMemberAO pinjuMemberAO) {
		this.pinjuMemberAO = pinjuMemberAO;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public void setSecurityTransManager(SecurityTransManager securityTransManager) {
		this.securityTransManager = securityTransManager;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
}
