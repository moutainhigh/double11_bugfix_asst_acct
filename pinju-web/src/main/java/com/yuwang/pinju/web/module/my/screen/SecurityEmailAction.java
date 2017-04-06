package com.yuwang.pinju.web.module.my.screen;

import org.apache.struts2.ServletActionContext;

import com.yuwang.message.constants.MessageConstants;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.domain.member.MemberEmailDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO;
import com.yuwang.pinju.domain.member.security.SecurityEmailMessageDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.MemberCheckAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>邮箱验证类</p>
 * @author zwm
 *
 */
public class SecurityEmailAction extends MemberCheckAction implements PinjuAction, EmailAction {

	private MemberSecurityAO memberSecurityAO;

	private static final String SEND_EMAIL_PAGE = "sendEmailPage";

	private String email;

	private String loginName;

	private MemberSecurityEmailLinkDO link;

	/**
	 * <p>跳转到邮箱检验界面</p>
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		Result result = memberSecurityAO.findMemberSecurityEmailByMemberId(login.getMemberId());
		if (!result.isSuccess()) {
			if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
				return LOGIN;
			}
			if (MemberResultConstant.RESULT_MEMBER_INNER_ERROR.equals(result.getResultCode())) {
				return ERROR;
			}
			if (MemberResultConstant.RESULT_MEMBER_EMAIL_CHECKED.equals(result.getResultCode())) {
				MemberSecurityEmailDO memberSecurityEmailDO = (MemberSecurityEmailDO)result.getModel(MemberSecurityEmailDO.class);
				setEmail(StringUtil.hideEmail(memberSecurityEmailDO.getEmailAddr()));
				return SEND_EMAIL_PAGE;
			}
		}
		return SUCCESS;
	}


	public String goUnboundEmail() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		Result result = memberSecurityAO.findMemberSecurityEmailByMemberId(login.getMemberId());
		if (result.isSuccess()) {
			ActionInvokeResult.setInvokeMessage(Message.getMessage(MessageName.MEMBER_EMAIL_NO_BOUND));
			return ERROR;
		}
		if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			return LOGIN;
		}
		if (MemberResultConstant.RESULT_MEMBER_INNER_ERROR.equals(result.getResultCode())) {
			ActionInvokeResult.setInvokeMessage(Message.getMessage(MessageName.MEMBER_EMAIL_UNBOUND_ERROR));
			return ERROR;
		}
		MemberSecurityEmailDO memberSecurityEmailDO = (MemberSecurityEmailDO)result.getModel(MemberSecurityEmailDO.class);
		setEmail(StringUtil.hideEmail(memberSecurityEmailDO.getEmailAddr()));

		return SUCCESS;

	}


	/**
	 * <p>发送邮箱验证链接</p>
	 * @return
	 * @throws Exception
	 */
	public String sendEmail() throws Exception {
		String method = ServletActionContext.getRequest().getMethod();
		if (REQUEST_METHOD_GET.equals(method)) {
			return INPUT;
		}

		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!super.isSameMember(login)) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_MEMBER_NOT_MATCH, login.getNickname());
			return INPUT;
		}
		
		MemberEmailDO med = new MemberEmailDO();
		med.setEmail(email);
		ActionInvokeResult air = new ActionInvokeResult(med);
		if (!air.validate()) {
			return INPUT;
		}

		long memberId = login.isLogin() ? login.getMemberId() : 0;
		Result result = memberSecurityAO.checkEmail(email, memberId);
		if (!result.isSuccess()) {
			if (MemberResultConstant.RESULT_MEMBER_EMAIL_HAS_EXIST.equals(result.getResultCode())) {
				air.addMessage("email", Message.getMessage(MessageName.MEMBER_EMAIL_HAS_BEEN_USED));
				return INPUT;
			} else {
				air.addMessage("email", Message.getMessage(MessageName.MEMBER_SEND_EMAIL_ERROR));
				return INPUT;
			}
		}

		SecurityEmailMessageDO message = new SecurityEmailMessageDO();
		message.setChannel(MessageConstants.CHANNEL_MAIL_MAILVALIDATE);
		message.setEmail(email);
		message.setTitleTemplateName(TITLE_VALIDATE_EMAIL);
		message.setContentTemplateName(CONTENT_VALIDATE_EMAIL);
		message.addContentParam("loginName", login.getNickname());
		message.addContentParam("email", email);
		message.setMemberId(memberId);
		message.setLinkType(MemberSecurityEmailLinkDO.LinkType.EMAIL_VALIDATION);
		message.setEmailLike(PinjuConstant.PINJU_SERVER + LINK_VALIDATE_EMAIL);
		message.setIp(ServletUtil.getRemoteIp());

		result = memberSecurityAO.sendEmail(message);
		if (!result.isSuccess()) {
			air.addMessage("email", Message.getMessage(MessageName.MEMBER_SEND_EMAIL_ERROR));
			return INPUT;
		}
		
		ServletUtil.forbidBrowserCache();
		setEmail(StringUtil.hideEmail(email));
		return SUCCESS;
	}

	/**
	 * 发送邮箱解绑邮件
	 * @return
	 * @throws Exception
	 */
	public String sendUnboundEmail() throws Exception {
		
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		
		String method = ServletActionContext.getRequest().getMethod();
		if (REQUEST_METHOD_GET.equals(method)) {
			return INPUT;
		}
		
		if (!super.isSameMember(login)) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_MEMBER_NOT_MATCH, login.getNickname());
			return ERROR;
		}
		
		long memberId = login.getMemberId();

		SecurityEmailMessageDO message = new SecurityEmailMessageDO();
		message.setChannel(MessageConstants.CHANNEL_MAIL_MAILVALIDATE);
		message.setTitleTemplateName(UNBOUND_EMAIL_TITLE);
		message.setContentTemplateName(UNBOUND_EMAIL_CONTENT);
		message.addContentParam("loginName", login.getNickname());
		message.setMemberId(memberId);
		message.setLinkType(MemberSecurityEmailLinkDO.LinkType.EMAIL_UNBOUND);
		message.setEmailLike(PinjuConstant.PINJU_SERVER + LINK_VALIDATE_EMAIL);
		message.setIp(ServletUtil.getRemoteIp());

		Result result = memberSecurityAO.sendUnboundEmail(message);

		if (!result.isSuccess()) {
			ActionInvokeResult.setInvokeMessage(Message.getMessage(MessageName.MEMBER_SEND_UNBOUND_EMAIL_ERROR));
			return ERROR;
		}

		ServletUtil.forbidBrowserCache();
		return SUCCESS;
	}

	public String emailLink() throws Exception {
		if (link == null) {
			return ERROR;
		}
		
		link.setLinkIp(ServletUtil.getRemoteIp());
		Result result = memberSecurityAO.addEmailLink(link);
		if (result.isSuccess()) {
			MemberSecurityEmailDO memberSecurityEmailDO = (MemberSecurityEmailDO)result.getModel(MemberSecurityEmailDO.class);
			setEmail(memberSecurityEmailDO.getEmailAddr());
			setLoginName(memberSecurityEmailDO.getLoginName());
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String unbandEmail() throws Exception {
		if (link == null) {
			return ERROR;
		}
		
		Result result = memberSecurityAO.unboundEmail(link);
		if (!result.isSuccess())
			return ERROR;
		
		MemberSecurityEmailDO memberSecurityEmailDO = (MemberSecurityEmailDO)result.getModel(MemberSecurityEmailDO.class);
		if (memberSecurityEmailDO == null) {
			return ERROR;
		}
		setLoginName(memberSecurityEmailDO.getLoginName());
		return SUCCESS;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public MemberSecurityEmailLinkDO getLink() {
		return link;
	}

	public void setLink(MemberSecurityEmailLinkDO link) {
		this.link = link;
	}

	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}
}
