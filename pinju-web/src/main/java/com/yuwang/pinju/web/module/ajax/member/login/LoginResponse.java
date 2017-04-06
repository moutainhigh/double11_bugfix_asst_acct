package com.yuwang.pinju.web.module.ajax.member.login;

public class LoginResponse {

	private int result;
	private boolean showCaptcha;
	private String returnUrl;
	private String message;
	private String account;

	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
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

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
}
