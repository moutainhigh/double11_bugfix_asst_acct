package com.yuwang.pinju.core.user.manager.impl;

import java.util.Collections;
import java.util.List;

import com.yuwang.pinju.core.user.dao.UserDAO;
import com.yuwang.pinju.core.user.manager.UserManager;
import com.yuwang.pinju.domain.user.UserDO;

/**
 * 用户管理业务实现
 * 
 * @author mike
 *
 */
public class UserManagerImpl implements UserManager {
	private UserDAO userDAO;

	/**
	 * 用户列表
	 */
	@Override
	public List<UserDO> queryAllUser() {
		return Collections.emptyList();
	}
	
	/**
	 * 返回用户名字
	 * 
	 * @return 用户名字
	 */
	@Override
	public String queryUserById(Long userId) {
		return userDAO.queryUserById(userId);
	}

	
	/**
	 * 注入用户数据接口
	 * 
	 * @param userDAO
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


}
