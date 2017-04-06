package com.yuwang.pinju.web.module.my.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.PinjuMemberAO;
import com.yuwang.pinju.core.transmission.manager.SecurityTransManager;
import com.yuwang.pinju.domain.member.ChangePasswordDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.MemberCheckAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>
 * 修改密码
 * </p>
 *
 * @author gaobaowen
 * @since 2011-8-2 09:18:47
 */
public class MyPassswordAction extends MemberCheckAction implements PinjuAction, ModelDriven<ChangePasswordDO> {

	private final static Log log = LogFactory.getLog(MyPassswordAction.class);

	private PinjuMemberAO pinjuMemberAO;
	private SecurityTransManager securityTransManager;
	private ChangePasswordDO change;
	private boolean success;

	public MyPassswordAction() {
		this.change = new ChangePasswordDO();
	}

	@Override
	public String execute() throws Exception {
		ServletUtil.forbidBrowserCache();
		return SUCCESS;
	}

	public String update() {

		String method = ServletActionContext.getRequest().getMethod();
		// 不允许除 POST 方法的访问！
		if(!"POST".equalsIgnoreCase(method)) {
			log.warn("user request this update using HTTP/GET");
			return NOT_ALLOWED_METHOD;
		}

		ServletUtil.forbidBrowserCache();

		// get login data from Cookie
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();

		// current member is not login, need jump to login page
		if (!login.isLogin()) {
			log.warn("current member not logged, member id: " + login);
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		if (!isSameMember(login)) {
			log.warn("current member is not same with member that before form submited, before info: " + getPj0() + ", current login info: " + login);
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_MEMBER_NOT_MATCH, login.getNickname());
			return INPUT;
		}

		if ( !securityTransManager.decryptProperties(change.getTid(), change)) {
			ActionInvokeResult.setInvokeMessageKey("member.change.password.security.failed");
			log.error("password decrypt error, client ip: [" + ServletUtil.getRemoteIp() + "], " + change);
			return INPUT;
		}

		ActionInvokeResult air = new ActionInvokeResult(change);
		if (!air.validate()) {
			if (log.isDebugEnabled()) {
				log.debug("change password object parameter error, " + change);
			}
			return INPUT;
		}

		change.setMemberId(login.getMemberId());
		change.setNickname(login.getNickname());

		Result result = pinjuMemberAO.updatePinjuLoginPassowrd(change);

		if (log.isDebugEnabled()) {
			log.debug("change password, result code: " + result.getResultCode() + ", parameter: " + change);
		}

		return processResult(result);
	}

	private String processResult(Result result) {

		// AO invoke success
		if (result.isSuccess()) {
			Integer count = result.getModel(MemberKeyConstant.KEY_MEMBER_UPDATE_COUNT, Integer.class);

			// update influence row number incorrect
			if (count == null || count < 1) {
				log.warn("process result, result success, but update count incorrect: " + count + ", " + change);
				ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_CHANGE_PASSWORD_FAILED);
				return INPUT;
			}

			// update influence row number correct, password change success
			if (log.isDebugEnabled()) {
				log.debug("process result, result success, update success, update count: " + count + ", " + change);
			}
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_CHANGE_PASSWORD_SUCCESS);
			success = true;
			return SUCCESS;
		}

		String code = result.getResultCode();

		// AO invoke parameter error
		if (MemberResultConstant.RESULT_PARAMETERS_ERROR.equals(code)) {
			log.warn("process result, result parameter error");
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_CHANGE_PASSWORD_FAILED);
			return INPUT;
		}

		// can not be found member login data by current member id
		if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(code)) {
			log.warn("cannot find member information, member id: " + change.getMemberId());
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		// nickname in cookie is not same with member loginname data in DB
		if (MemberResultConstant.RESULT_PINJU_NICKNAME_LOGINNAME_ERROR.equals(code)) {
			log.warn("process result, current login member nickname [" + change.getNickname()
					+ "] is invalid, need relogin");
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		// old password enteried by member is incorrect
		if (MemberResultConstant.RESULT_PINJU_LOGIN_ERROR.equals(code)) {
			log.warn("process result, input old password is incorred, " + change);
			ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_CHANGE_PASSWORD_AUTH_ERROR);
			return INPUT;
		}

		log.error("process result unknown error result code [" + code + "] return " +
				"MEMBER_CHANGE_PASSWORD_FAILED message to page, user input data: " + change);
		ActionInvokeResult.setInvokeMessageKey(MessageName.MEMBER_CHANGE_PASSWORD_FAILED);
		return INPUT;
	}

	public void setPinjuMemberAO(PinjuMemberAO pinjuMemberAO) {
		this.pinjuMemberAO = pinjuMemberAO;
	}

	public void setSecurityTransManager(SecurityTransManager securityTransManager) {
		this.securityTransManager = securityTransManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public ChangePasswordDO getModel() {
		return change;
	}
}
