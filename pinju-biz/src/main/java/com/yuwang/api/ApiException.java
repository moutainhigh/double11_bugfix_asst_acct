/**
 * 品聚开放平台API，版本号：1.0
 * 提供给第三方开发者使用
 */
package com.yuwang.api;

/**
 * @author liyouguo
 * 
 * @since 2011-9-2
 */
public class ApiException extends Exception {

	private static final long serialVersionUID = -238091758285157331L;

	/**
	 * 错误编码
	 */
	private String errCode;

	/**
	 * 错误信息
	 */
	private String errMsg;

	public ApiException() {
		super();
	}

	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(Throwable cause) {
		super(cause);
	}

	public ApiException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}
}
