package com.yuwang.pinju.web.module.user.screen;

import com.yuwang.pinju.core.user.ao.UserAO;

/**
 * 测试hello world
 * 
 * @author mike
 * 
 */
public class HelloWorld {
	private String message;

	private UserAO userAO;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * ִ 测试
	 * 
	 * @return 成功信息
	 */
	public String execute() {
		Long userId = 1L;
		String name = userAO.queryUser(userId);
		this.message = name + "已经成功显示111222";
		return "success";
	}

	/**
	 * 注入用户业务管理
	 * 
	 * @param userAO
	 */
	public void setUserAO(UserAO userAO) {
		this.userAO = userAO;
	}

}
