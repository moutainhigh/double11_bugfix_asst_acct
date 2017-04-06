package com.yuwang.pinju.core.user.dao.impl;

import java.util.Collections;
import java.util.List;

import com.yuwang.pinju.core.user.dao.UserDAO;
import com.yuwang.pinju.domain.user.UserDO;

/**
 * 用户管理数据实现
 * 
 * @author mike
 *
 */
public class UserDAOImpl implements UserDAO {

	/**
	 * 返回所有用户
	 */
	@Override
	public List<UserDO> queryAllUser() {
		return Collections.emptyList();
	}

	/**
	 * 查询用户名字
	 */
	@Override
	public String queryUserById(Long userId) {
		// TODO Auto-generated method stub
		return "mike";
	}

}
