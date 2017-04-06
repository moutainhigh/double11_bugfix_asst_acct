package com.yuwang.pinju.domain.member.security;

public class SmsSenderResultVO {

	private final static int SUCCESS = 0;
	private final static int FAILED = 1;

	private int status;
	private String message;
	private String mid;

	public SmsSenderResultVO() {
	}

	public SmsSenderResultVO(int status, String message, String mid) {
		this.status = status;
		this.message = message;
		this.mid = mid;
	}

	public static SmsSenderResultVO createSuccess(String mid) {
		return new SmsSenderResultVO(SUCCESS, "OK", mid);
	}

	public static SmsSenderResultVO createError(String message) {
		return new SmsSenderResultVO(FAILED, message, null);
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
}
