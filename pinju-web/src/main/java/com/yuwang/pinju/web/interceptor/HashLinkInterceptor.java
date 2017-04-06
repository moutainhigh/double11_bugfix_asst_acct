package com.yuwang.pinju.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.HashLink;
import com.yuwang.pinju.web.struts2.HashLinkAware;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>URL HASH 传参设置及校验拦截器</p>
 *
 * @author gaobaowen
 * @since 2011-7-1 下午02:42:12
 */
public class HashLinkInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	private final static Log log = LogFactory.getLog(HashLinkInterceptor.class);

	/**
	 * 是否需要进行 HASH 校验
	 */
	private boolean before = true;

	/**
	 * 是否需要进行 HASH 计算
	 */
	private boolean after = true;

	/**
	 * 是否只允许 POST 方式请求数据（默认为 true），设置为 false 时使用 GET 方式也可以进行请求
	 */
	private boolean onlyAllowPost = true;

	public boolean isBefore() {
		return before;
	}
	public void setBefore(boolean before) {
		this.before = before;
	}

	public boolean isAfter() {
		return after;
	}
	public void setAfter(boolean after) {
		this.after = after;
	}

	public boolean isOnlyAllowPost() {
		return onlyAllowPost;
	}
	public void setOnlyAllowPost(boolean onlyAllowPost) {
		this.onlyAllowPost = onlyAllowPost;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		Object action = invocation.getAction();

		if(log.isDebugEnabled()) {
			log.debug("execute hashLink interceptor: " + hashCode() + ", action: " + action);
		}

		if(!(action instanceof HashLinkAware)) {
			log.warn("action isnot HashLinkAware type, action: " + action);
			return invocation.invoke();
		}

		HashLink link = ((HashLinkAware)action).getHashLink();

		if(link == null) {
			log.warn("action HashLink object is null, action: " + action);
			return invocation.invoke();
		}

		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if(!login.isLogin()) {
			log.warn("current member no logon, continue invoking action");
			return invocation.invoke();
		}

		if(log.isDebugEnabled()) {
			log.debug("hashLink intercepter, before: " + before + ", after: " + after + ", onlyAllowPost: " + onlyAllowPost);
		}

		String method = ServletActionContext.getRequest().getMethod().toUpperCase();

		log.debug("current url using HTTP/" + method + " to access");

		if(onlyAllowPost && !"POST".equals(method)) {
			log.warn("ACCESS DENIED, url: " + ServletUtil.getRequestUrl() + " is not allowed to access with HTTP/" + method + " method");
			return PinjuAction.NOT_ALLOWED_METHOD;
		}

		if(before) {
			String message = link.onValidate(login.getMemberId());
			if(!HashLink.VALIDATE_SUCCESS.equals(message)) {
				log.warn("before invoke hashLink check, validate message: " + message);
				return link.onError(message);
			}
			link.onConvertHash();
			log.debug("before invoke hashLink validate success, convert hashLink value ok");
		}

		if(after) {
			invocation.addPreResultListener(new HashLinkPreResultListener(link, login));
		}

		return invocation.invoke();
	}

	private static class HashLinkPreResultListener implements PreResultListener {

		private HashLink link;
		private CookieLoginInfo login;

		private HashLinkPreResultListener(HashLink link, CookieLoginInfo login) {
			this.link = link;
			this.login = login;
		}

		public void beforeResult(ActionInvocation invocation, String resultCode) {
			log.debug("after invoke hashLink, execute onHash...");
			link.onHash(login.getMemberId());
		}
	}
}
