package com.yuwang.pinju.core.user.ao.impl;

import com.yuwang.pinju.core.user.ao.UserAO;
import com.yuwang.pinju.core.user.manager.UserManager;

/**
 * 用户管理业务封装场景
 * 
 * @author mike
 *
 */
public class UserAOImpl implements UserAO {
	/**
	 * 用户管理
	 */
	private UserManager userManager;
	
	@Override
	public String queryUser(Long userId) {
		//测试数据
		return userManager.queryUserById(userId);
	}

	/**
	 * 注入用户管理
	 * 
	 * @param userManager
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
