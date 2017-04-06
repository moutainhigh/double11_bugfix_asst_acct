package com.yuwang.pinju.web.manager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.core.member.manager.MemberAsstManager;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstPermManager;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.interceptor.MemberAuthInterceptor;
import com.yuwang.pinju.web.system.ServletUtil;

public class Struts2MemberAsstLog implements MemberAsstLog {

	private final static Log log = LogFactory.getLog(Struts2MemberAsstLog.class);

	private MemberAsstPermManager memberAsstPermManager;
	private MemberAsstManager memberAsstManager;

	public void setMemberAsstPermManager(MemberAsstPermManager memberAsstPermManager) {
		this.memberAsstPermManager = memberAsstPermManager;
	}

	public void setMemberAsstManager(MemberAsstManager memberAsstManager) {
		this.memberAsstManager = memberAsstManager;
	}

	@Override
	public void log(String message) {
		log( null, null, message );
	}

	@Override
	public void log(String target, String action, String message) {
		MemberAsstOperLogDO oper = null;
		try {
			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
			if (!login.isLogin()) {
				log.warn("[log] current user no login, can not record log, client ip: [" + ServletUtil.getRemoteIp() + "]");
				return;
			}
			if (StringUtils.isBlank(message)) {
				log.warn("[log] message is empty, can not record log, client ip: [" + ServletUtil.getRemoteIp() + "], member id: [" + login.getMemberId() + "]");
				return;
			}
			if (StringUtils.isBlank(target)) {
				target = getTarget();
			}
			if (StringUtils.isBlank(action)) {
				action = getAction();
			}
			if (StringUtils.isBlank(target) || StringUtils.isBlank(action)) {
				log.warn("[log] target or action has empty, target: [" + target + "], action: [" + action + "], client ip: [" + ServletUtil.getRemoteIp() + "], member id: [" + login.getMemberId() + "]");
				return;
			}
			MemberAsstPermissionDO permission = memberAsstPermManager.getAsstPermission(target, action);
			oper = createOperLog(target, action, permission, message, login);
			memberAsstManager.logMeberAsstOperation(oper);
		} catch (Exception e) {
			log.error("[log] record cause exception, " + oper, e);
		}
	}

	private MemberAsstOperLogDO createOperLog(String target, String action, MemberAsstPermissionDO permission, String message, CookieLoginInfo login) {
		MemberAsstOperLogDO oper = new MemberAsstOperLogDO();
		oper.setMasterMemberId( login.getMasterMemberId() );
		oper.setMasterLoginName( login.getMasterMemberName() );
		oper.setAsstMemberId( login.getMemberId() );
		oper.setAsstLoginName( login.getNickname() );
		oper.setTarget( target );
		oper.setAction( action );
		oper.setTargetDesc( permission == null ? "[UNKNOWN]" : permission.getTargetDesc() );
		oper.setActionDesc( permission == null ? "[UNKNOWN]" : permission.getActionDesc() );
		oper.setOperationLog( message );
		oper.setOperationIp( ServletUtil.getRemoteIp() );
		return oper;
	}

	private String getTarget() {
		return (String)ActionContext.getContext().get(MemberAuthInterceptor.CONTEXT_NAME_TARGET);
	}

	private String getAction() {
		return (String)ActionContext.getContext().get(MemberAuthInterceptor.CONTEXT_NAME_ACTION);
	}
}
