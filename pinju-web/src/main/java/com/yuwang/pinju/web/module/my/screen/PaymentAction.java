package com.yuwang.pinju.web.module.my.screen;

public interface PaymentAction {

	/**
	 * 支付绑定错误
	 */
	String PAY_ERROR = "pay_error";
	
	/**
	 * 支付绑定成功
	 */
	String PAY_SUCCESS = "pay_success";
	
	/**
	 * 账号没有绑定
	 */
	String UNBOUND = "unbound";
	
	/**
	 * 账号已绑定
	 */
	String BOUND = "bound";
	
	/**
	 * 财付通账号验证
	 */
	String USER_INFO_CHECK_CGI = "https://www.tenpay.com/cgi-bin/v4.0/user_info_check.cgi";
	
}
