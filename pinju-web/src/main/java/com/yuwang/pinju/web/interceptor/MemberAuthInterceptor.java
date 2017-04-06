package com.yuwang.pinju.web.interceptor;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.annotatioin.AssistantPermissions;
import com.yuwang.pinju.web.annotatioin.MasterAccount;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>会员权限拦截器</p>
 *
 * @author gaobaowen
 * @since 2011-12-20 13:20:47
 */
public class MemberAuthInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(MemberAuthInterceptor.class);

	public final static String CONTEXT_NAME_TARGET = "__pj_asst_target__";
	public final static String CONTEXT_NAME_ACTION = "__pj_asst_action__";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("execute action: [" + invocation.getAction().getClass().getName() + "], method: [" + invocation.getProxy().getMethod() + "]");
		}
		MethodRestrict mr = new MethodRestrict(invocation);
		if (!mr.checkPermission()) {
			setContext(mr);
			return PinjuAction.ACCESS_DENIED;
		}
		return invocation.invoke();
	}

	public static String accessDenied() {
		ActionContext.getContext().getValueStack().set("history", ServletUtil.getHttpReferer());
		ActionContext.getContext().getValueStack().set("returnUrl", ServletUtil.getRequestUrl());
		return PinjuAction.ACCESS_DENIED;
	}

	private void setContext(MethodRestrict mr) {
		ActionContext.getContext().getValueStack().set("history", ServletUtil.getHttpReferer());
		ActionContext.getContext().getValueStack().set("returnUrl", ServletUtil.getRequestUrl());
		ActionContext.getContext().getValueStack().set("targetAction", mr.getTarget() + "." + mr.getAction());
	}

	/**
	 * <p>子账号权限方法标注数据</p>
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 14:45:28
	 */
	private static class MethodRestrict {

		private List<AssistantPermission> assistantPermissions;
		private MasterAccount masterAccount;
		private Method method;
		private ActionInvocation invocation;

		private MethodRestrict(ActionInvocation invocation) throws Exception {
			this.invocation = invocation;
			this.method = getInvokeMethod();
			this.masterAccount = method.getAnnotation(MasterAccount.class);
			loadAssistantPermissions();
		}

		private Method getInvokeMethod() throws Exception {
			String methodName = invocation.getProxy().getMethod();
			return invocation.getAction().getClass().getMethod(methodName);
		}

		private void loadAssistantPermissions() {

			if (log.isDebugEnabled()) {
				log.debug("[loadAssistantPermissions] start, action: " + invocation.getAction().getClass().getName() + ", method: " + method.getName());
			}

			assistantPermissions = new LinkedList<AssistantPermission>();
			AssistantPermission ap = method.getAnnotation(AssistantPermission.class);
			if (ap != null) {
				assistantPermissions.add(ap);
			}
			AssistantPermissions aps = method.getAnnotation(AssistantPermissions.class);
			if (aps == null) {
				return;
			}
			AssistantPermission[] as = aps.value();
			for (int i = 0; i < as.length; i++) {
				assistantPermissions.add(as[i]);
			}

			if (log.isDebugEnabled()) {
				StringBuilder builder = new StringBuilder();
				int i = 0;
				for (AssistantPermission asstPerm : assistantPermissions) {
					if (i++ > 0) {
						builder.append("; ");
					}
					builder.append(asstPerm.target()).append(":").append(asstPerm.action());
				}
				log.debug("[loadAssistantPermissions] finished, action: " + invocation.getAction().getClass().getName() + ", method: " + method.getName() + ", annotated permission(s): " + builder);
			}
		}

		public String getTarget() {
			if (EmptyUtil.isBlank(assistantPermissions)) {
				return null;
			}
			return assistantPermissions.get(0).target();
		}

		public String getAction() {
			if (EmptyUtil.isBlank(assistantPermissions)) {
				return null;
			}
			return assistantPermissions.get(0).action();
		}

		public boolean checkPermission() {
			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
			if (masterAccount != null) {
				boolean check = login.isMasterAccount();
				if (check) {
					injectTargetActionContext("master", "master");
				} else if (!check) {
					log.warn("[checkPermission] Action: " + invocation.getAction().getClass().getName() + ", method: " +
						method.getName() + " annotate @MasterAccount, current login member is not master account, " + login);
				}
				return check;
			}
			if (EmptyUtil.isBlank(assistantPermissions)) {
				log.warn("[checkPermission] Action: " + invocation.getAction().getClass().getName() + ", method: " +
						method.getName() + " hasnot annotate @AssistantPermission, ignore assistant permission");
				return true;
			}

			if (login.isMasterAccount()) {
				AssistantPermission asstPerm = assistantPermissions.get(0);
				injectTargetActionContext(asstPerm.target(), asstPerm.action());
				return true;
			}
			for (AssistantPermission asstPerm : assistantPermissions) {
				if (login.hasAsstPerm(asstPerm.target(), asstPerm.action())) {
					injectTargetActionContext(asstPerm.target(), asstPerm.action());
					return true;
				}
			}
			return false;
		}

		private void injectTargetActionContext(String target, String action) {
			ActionContext.getContext().put(CONTEXT_NAME_TARGET, target);
			ActionContext.getContext().put(CONTEXT_NAME_ACTION, action);
		}
	}
}
