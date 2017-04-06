package com.yuwang.pinju.web.module.member.screen;


import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.member.ao.SinaMemberAO;
import com.yuwang.pinju.core.transmission.manager.SecurityTransManager;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberSinaRegisterDO;
import com.yuwang.pinju.web.listener.EventProcess;
import com.yuwang.pinju.web.listener.login.AfterLoginProcess;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * 第三方会员登录
 * @author zhaowenm
 *
 */
public class MemberSinaLoginAction implements PinjuAction {

	private final static Log log = LogFactory.getLog(MemberSinaLoginAction.class);

	private String code;
	private String state;
	private String returnUrl;
	private String aj0;
	private String aj1;
	private String nickname;
	private String password;
	private String confirm;
	private String tid;

	private SinaMemberAO sinaMemberAO;
	private MemberAO memberAO;
	private SecurityTransManager securityTransManager;

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public void setSinaMemberAO(SinaMemberAO sinaMemberAO) {
		this.sinaMemberAO = sinaMemberAO;
	}

	public void setSecurityTransManager(SecurityTransManager securityTransManager) {
		this.securityTransManager = securityTransManager;
	}

	@Override
	public String execute() throws Exception {
		if (StringUtil.isBlank(code)) {
			log.warn("code is null, error=" + MessageName.MEMBER_SINA_LOGIN_ERROR);
			return MAIN_PAGE;
		}

		Result result = sinaMemberAO.sinaLogin(code);

		if (!result.isSuccess()) {
			if (MemberResultConstant.RESULT_SINA_NO_OAUTH.equals(result.getResultCode())) {
				log.warn("sina no oauth for link, code = " + code + ", error=" + MessageName.MEMBER_SINA_NO_OAUTH);
				return ERROR;
			}

			if (MemberResultConstant.RESULT_SINA_USER_NO_EXIST.equals(result.getResultCode())) {
				log.warn("sina user not exist, code = " + code + ", error = " + MessageName.MEMBER_SINA_USER_NO_EXIST);
				return ERROR;
			}

			if (MemberResultConstant.RESULT_SINA_LOGIN_ERROR.equals(result.getResultCode())) {
				log.warn("sinLogin is error, code = " + code + ", error = " + MessageName.MEMBER_SINA_LOGIN_ERROR);
				return ERROR;
			}

			if (MemberResultConstant.RESULT_SINA_NOT_NICKNAME_ERROR.equals(result.getResultCode())) {
				MemberSinaRegisterDO msrd = result.getModel(MemberSinaRegisterDO.class);
				setAj0(msrd.getAj0());
				setAj1(msrd.getAj1());
				return "sina-login";
			}
		}

		// sina会员登录成功
		MemberDO member = result.getModel(MemberDO.class);

		EventProcess event = new AfterLoginProcess(memberAO, member, ServletUtil.getHttpReferer(), state);
		boolean login = event.process(log);
		if (!login) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_INVALID);
			log.warn("register sina member login process result is failed");
			return INPUT;
		}

		if (StringUtil.isNotBlank(state)) {
			state = URLDecoder.decode(state, "UTF-8");
		}

		returnUrl = ServletUtil.processReturnUrl(state);
		return SUCCESS;
	}

	public String sinaRegister() throws Exception {

		String method = ServletActionContext.getRequest().getMethod();
		if(!"POST".equalsIgnoreCase(method)) {
			return MAIN_PAGE;
		}

		ServletUtil.forbidBrowserCache();

		MemberSinaRegisterDO msrd = new MemberSinaRegisterDO();
		msrd.setLoginName(nickname);
		msrd.setPassword(password);
		msrd.setConfirm(confirm);
		msrd.setAj0(aj0);
		msrd.setAj1(aj1);
		msrd.setRegisterIp(ServletUtil.getRemoteIp());
		
		ActionInvokeResult air = new ActionInvokeResult(msrd);

		if (!securityTransManager.decryptProperties(tid, msrd) ) {
			log.warn("decrypt register data error, client ip: [" + ServletUtil.getRemoteIp() + "], " + msrd);
			ActionInvokeResult.setInvokeMessageKey("member.sina.security.failed");
			return INPUT;
		}

		if (!air.validate()) {
			Map<String, String> validateError = air.getValidator();
			for (Map.Entry<String, String> entry : validateError.entrySet()) {
				ActionInvokeResult.setInvokeMessage(entry.getValue());
			}
			log.info("sna register validate has problem");
			return INPUT;
		}

        Result result = sinaMemberAO.registerSinaMember(msrd);

        //sina会员信息被修改
        if (MemberResultConstant.RESULT_MEMBER_SINA_USER_DIFFERENT.equals(result.getResultCode())) {
        	log.warn("sina user is modify");
        	ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_SINA_USER_DIFFERENT);
			return INPUT;
        }

       // 会员名中含有争议词
		if (MemberResultConstant.RESULT_MEMBER_NICKNAME_WORDS_INVALID.equals(result.getResultCode())) {
			log.warn("login name [" + msrd.getLoginName() + "] has invalid words");
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_LOGINNAME_INVALID);
			return INPUT;
		}

		// 会员名中含有敏感词
		if (MemberResultConstant.RESULT_INSENSIVE_WORDS.equals(result.getResultCode())) {
			log.warn("login name [" + msrd.getLoginName() + "] has insensive words");
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_LOGINNAME_INSENSIVE);
			return INPUT;
		}

		// 会员名已经被使用
		if (MemberResultConstant.RESULT_MEMBER_NICKNAME_HAS_EXIST.equals(result.getResultCode())) {
			log.warn("register sina member login name has been used by other member, login name: " + msrd.getLoginName());
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_LOGINNAME_HAS_BEEN_USED);
			return INPUT;
		}

		//您的新浪微博会员已被使用
		if (MemberResultConstant.RESULT_MEMBER_SIN_UID_HAS_EXIST.equals(result.getResultCode())) {
			log.warn("register sin member sina uid has been used by other member, login name: " + msrd.getLoginName());
			ActionInvokeResult.setInvokeMessageKey(MessageName.RESULT_MEMBER_SIN_UID_HAS_EXIST);
			return INPUT;
		}

		// 其他情况，注册失败
		if (!result.isSuccess()) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_INVALID);
			log.warn("register pinju member result is failed, result: " + result.getResultCode() + ", register: " + msrd);
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_REGISTER_FAILED);
			return INPUT;
		}

		// sina会员名注册成功
		MemberDO member = result.getModel(MemberDO.class);

		EventProcess event = new AfterLoginProcess(memberAO, member, ServletUtil.getHttpReferer(), returnUrl);
		boolean login = event.process(log);
		if (!login) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_INVALID);
			log.warn("register pinju member login process result is failed");
			return INPUT;
		}
		returnUrl = ServletUtil.processReturnUrl(returnUrl);
		//注册成功

		return SUCCESS;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getAj0() {
		return aj0;
	}

	public void setAj0(String aj0) {
		this.aj0 = aj0;
	}

	public String getAj1() {
		return aj1;
	}

	public void setAj1(String aj1) {
		this.aj1 = aj1;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
}
