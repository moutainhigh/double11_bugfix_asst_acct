package com.yuwang.pinju.web.interceptor;

import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.yuwang.cookie.util.CodeUtil;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.cookie.PinjuCookieManager;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>
 * 基于 Cookie 的防重复提交 token
 * </p>
 *
 * @author gaobaowen
 * @since 2011-7-21 10:46:24
 */
public class CookieRefreshTokenInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	public final static String REFRESH_TOKEN_VALUE_STACK_NAME = "_token_";

	private final static Log log = LogFactory.getLog(CookieRefreshTokenInterceptor.class);

	/**
	 * 是否生成防刷新 TOKEN
	 */
	private boolean generate = false;

	/**
	 * 是否需要校验防刷新 TOKEN
	 */
	private boolean validate = false;

	/**
	 * 发生刷新重复提交时的跳转
	 */
	private String repeatSubmit = PinjuAction.REPEAT_SUBMIT;

	/**
	 * 发生刷新重复提交时的提示文字 KEY（配置于 message.properties），默认为：operate.submit.duplicate
	 */
	private String messageKey = MessageName.OPERATE_SUBMIT_DUPLICATE;

	public boolean isGenerate() {
		return generate;
	}

	public void setGenerate(boolean generate) {
		this.generate = generate;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String getRepeatSubmit() {
		return repeatSubmit;
	}

	public void setRepeatSubmit(String repeatSubmit) {
		this.repeatSubmit = repeatSubmit;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("execute CookieRefreshTokenInterceptor check, url: " + ServletUtil.getRequestUrl()
					+ ", generate: " + generate + ", validate: " + validate);
		}
		if (generate) {
			invocation.addPreResultListener(new GenerateRefreshTokenListener());
		}
		if (validate) {			
			String refreshToken = (String) ServletActionContext.getRequest().getParameter(REFRESH_TOKEN_VALUE_STACK_NAME);
			if(EmptyUtil.isBlank(refreshToken)) {
				Map<String, Object> params = ActionContext.getContext().getParameters();
				if(params != null) {
					Object obj = params.get(REFRESH_TOKEN_VALUE_STACK_NAME);
					if(obj instanceof String) {
						refreshToken = (String)obj;
					} else if (obj instanceof String[]) {
						String[] strs = (String[])obj;
						if(strs.length > 0) {
							refreshToken = strs[0];
						}
					}
					log.info("form token can not find in parameter");
				}				
			}
			log.info("form token _token_ value: " + refreshToken);
			if (!RefreshToken.validate(refreshToken)) {
				ActionInvokeResult.setInvokeMessageKey(messageKey);
				log.warn("cause repeat submit, direct to repeat-submit page");
				return repeatSubmit;
			}
		}
		return invocation.invoke();
	}

	private static class GenerateRefreshTokenListener implements PreResultListener {

		public void beforeResult(ActionInvocation invocation, String resultCode) {
			RefreshToken refreshToken = RefreshToken.newRefreshToken();
			PinjuCookieManager.writeRefreshToken(refreshToken);
			ActionContext.getContext().getValueStack().set(REFRESH_TOKEN_VALUE_STACK_NAME, refreshToken.getTokenValue());
			log.debug("generate new refresh token: " + refreshToken);
		}
	}

	public static class RefreshToken {

		private final static char[] BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
				.toCharArray();

		private String cookieValue;
		private String tokenValue;
		private static Random ran = new Random();

		private RefreshToken() {
			random(this);
		}

		public static RefreshToken newRefreshToken() {
			return new RefreshToken();
		}

		public String getCookieValue() {
			return cookieValue;
		}

		public String getTokenValue() {
			return tokenValue;
		}

		@Override
		public String toString() {
			return "RefreshTokenGenerator [cookieValue=" + cookieValue + ", tokenValue=" + tokenValue + "]";
		}

		private static boolean validate(String tv) {
			if (EmptyUtil.isBlank(tv)) {
				log.warn("form token value is empty, token check failed");
				return false;
			}
			String cv = PinjuCookieManager.getRefreshToken();
			if (EmptyUtil.isBlank(cv)) {
				log.warn("can not find refresh token value in cookie, token check failed, form token: " + tv);
				return false;
			}
			String gtv = generateTokenValue(cv);
			if (log.isDebugEnabled()) {
				log.debug("validate refresh token, cookie value: " + cv + ", refresh value: " + tv
						+ ", decode cookie value: " + gtv);
			}
			return gtv.equals(tv);
		}

		private static void random(RefreshToken rt) {
			char[] chs = new char[12];
			for (int i = 0; i < chs.length; i++) {
				chs[i] = BASE[ran.nextInt(BASE.length)];
			}
			rt.cookieValue = new String(chs);
			rt.tokenValue = generateTokenValue(chs);
		}

		private static String generateTokenValue(String value) {
			return generateTokenValue(value.toCharArray());
		}

		private static String generateTokenValue(char[] chs) {
			long x = 1;
			for (int i = 0; i < chs.length; i++) {
				x = x * 31 + chs[i];
			}
			return CodeUtil.toBase62(x & Long.MAX_VALUE);
		}
	}
}
