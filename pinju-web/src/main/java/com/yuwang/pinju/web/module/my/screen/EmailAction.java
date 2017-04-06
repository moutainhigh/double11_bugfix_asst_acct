package com.yuwang.pinju.web.module.my.screen;

public interface EmailAction {

	/**
	 * 验证邮箱标题模板名称
	 */
	String TITLE_VALIDATE_EMAIL = "titleValidateEmail";
	
	/**
	 * 验证邮箱内容模板名称
	 */
	String CONTENT_VALIDATE_EMAIL = "contentValidateEmail";
	
	/**
	 * 邮箱解绑标题模板名称
	 */
	String UNBOUND_EMAIL_TITLE = "unboundEmailTitle";
	
	/**
	 * 邮箱解绑发送验证邮件的内容
	 */
	String UNBOUND_EMAIL_CONTENT = "unboundEmailContent";
	
	/**
	 * 验证邮箱URL后缀链接
	 */
	String LINK_VALIDATE_EMAIL = "/security/link.htm?param=";
	
}
