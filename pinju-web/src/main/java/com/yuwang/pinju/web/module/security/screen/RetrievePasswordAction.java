package com.yuwang.pinju.web.module.security.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberPinjuLoginDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityParam;
import com.yuwang.pinju.domain.member.security.RetrievePasswordDO;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

public class RetrievePasswordAction implements PinjuAction, ModelDriven<RetrievePasswordDO> {

	private final static Log log = LogFactory.getLog(RetrievePasswordAction.class);

	private RetrievePasswordDO retrieve;
	private MemberSecurityAO memberSecurityAO;
	private MemberSecurityParam link;

	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}

	public RetrievePasswordAction() {
		this.retrieve = new RetrievePasswordDO();
	}

	@Override
	public String execute() throws Exception {
		if (link == null || EmptyUtil.isBlank(link.getToken()) || EmptyUtil.isBlank(link.getMessageId())) {
			log.error("can not find MemberSecurityParam object from action context or the object has not token " +
					"or message id, " + link + ", client ip: [" + ServletUtil.getRemoteIp() + "], " +
					"action context debug information:\n" + ServletUtil.getActionContextContent());
			return ERROR;
		}
		processSecurityParameter();
		retrieve.setParam3(link.getToken());
		retrieve.setParam4(link.getMessageId());
		retrieve.setParam6(link.getSecurityType());
		retrieve.setParam5(memberSecurityAO.macRetrievePassword(retrieve));
		return SUCCESS;
	}

	public String doRetrievePassword() {

		String method = ServletActionContext.getRequest().getMethod();
		// 不允许除 POST 方法的访问！
		if(!"POST".equalsIgnoreCase(method)) {
			log.warn("user request this update using HTTP/GET");
			return NOT_ALLOWED_METHOD;
		}

		ActionInvokeResult air = new ActionInvokeResult(retrieve);
		if (!air.validate()) {
			if (log.isDebugEnabled()) {
				log.debug("change password object parameter error, " + retrieve);
			}
			return INPUT;
		}

		processSecurityParameter();

		Result result = memberSecurityAO.retrievePassword(retrieve);
		if (log.isInfoEnabled()) {
			log.info("execute action, retrieve data object: " + retrieve + ", ao invoke result: " + result.getResultCode());
		}

		if (result.isSuccess()) {
			if (log.isDebugEnabled()) {
				log.debug("execute action, retrieve password success, message id: " + retrieve.getParam4() + " update count: ");
			}
			ServletUtil.forbidBrowserCache();

			MemberPinjuLoginDO pj = result.getModel(MemberPinjuLoginDO.class);
			if (pj != null && !EmptyUtil.isBlank(pj.getLoginName())) {
				ActionContext.getContext().put("loginName", pj.getLoginName());
			}
			return SUCCESS;
		}
		return processErrorResult(result.getResultCode());
	}

	private String processErrorResult(String code) {
		log.warn("retrieve password result is unsuccess, code: [" + code + "], link param: " + link);
		if (MemberResultConstant.RESULT_PARAMETERS_ERROR.equals(code)) {
			ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_FAILED);
			return INPUT;
		}
		if (MemberResultConstant.RESULT_MEMBER_SECURITY_LINK_NOT_FOUND.equals(code)) {
			ServletUtil.forbidBrowserCache();
			return ERROR;
		}
		if (MemberResultConstant.RESULT_MEMBER_SECURITY_MOBILE_NOT_FOUND.equals(code)) {
			ServletUtil.forbidBrowserCache();
			return ERROR;
		}
		if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(code)) {
			return LOGIN;
		}
		if (MemberResultConstant.RESULT_MEMBER_INVALID.equals(code)) {
			return LOGIN;
		}
		// other, MemberResultConstant.RESULT_MEMBER_INNER_ERROR
		ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_FAILED);
		return INPUT;
	}

	private void processSecurityParameter() {
		retrieve.setUserAgent(ServletUtil.getUserAgentHash());
		retrieve.setClientIp(ServletUtil.getRemoteIp());
	}

	public void setLink(MemberSecurityParam link) {
		this.link = link;
	}

	@Override
	public RetrievePasswordDO getModel() {
		return retrieve;
	}
}
