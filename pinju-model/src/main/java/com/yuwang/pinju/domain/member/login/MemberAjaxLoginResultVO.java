package com.yuwang.pinju.domain.member.login;

import java.io.Serializable;

/**
 * <p>Ajax 登录请求响应</p>
 *
 * @author gaobaowen
 * @since 2011-11-24 13:18:07
 */
public class MemberAjaxLoginResultVO implements Serializable {

	private static final long serialVersionUID = -6768208397476162028L;

	/**
	 * 处理结果正确
	 */
	private final static int SUCCESS = 0;

	/**
	 * 处理结果不正确
	 */
	private final static int FAILED = 1;

	/**
	 * 处理结果
	 */
	private int result;

	/**
	 * 处理结果的消息提示
	 */
	private String message;

	/**
	 * 是否需要呈现验证码
	 */
	private boolean showCaptcha;

	/**
	 * 登录后返回地址
	 */
	private String returnUrl;

	public MemberAjaxLoginResultVO() {
	}

	private MemberAjaxLoginResultVO(int result, String message, boolean showCaptcha, String returnUrl) {
		this.result = result;
		this.message = message;
		this.showCaptcha = showCaptcha;
		this.returnUrl = returnUrl;
	}

	public static MemberAjaxLoginResultVO createError(String message, boolean showCaptcha) {
		return new MemberAjaxLoginResultVO(FAILED, message, showCaptcha, null);
	}

	public static MemberAjaxLoginResultVO createSuccess(String returnUrl) {
		return new MemberAjaxLoginResultVO(SUCCESS, "OK", false, returnUrl);
	}

	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isShowCaptcha() {
		return showCaptcha;
	}
	public void setShowCaptcha(boolean showCaptcha) {
		this.showCaptcha = showCaptcha;
	}

	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + this.result;
		result = prime * result + ((returnUrl == null) ? 0 : returnUrl.hashCode());
		result = prime * result + (showCaptcha ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberAjaxLoginResultVO other = (MemberAjaxLoginResultVO) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (result != other.result)
			return false;
		if (returnUrl == null) {
			if (other.returnUrl != null)
				return false;
		} else if (!returnUrl.equals(other.returnUrl))
			return false;
		if (showCaptcha != other.showCaptcha)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberAjaxLoginResultVO [result=" + result + ", message=" + message + ", showCaptcha=" + showCaptcha
				+ ", returnUrl=" + returnUrl + "]";
	}
}
