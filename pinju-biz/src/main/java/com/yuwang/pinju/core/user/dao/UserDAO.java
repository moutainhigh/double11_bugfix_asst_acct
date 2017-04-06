package com.yuwang.pinju.core.user.dao;


import java.util.List;

import com.yuwang.pinju.domain.user.UserDO;




/**
 * 用户管理数据接口
 * 
 * @author mike
 *
 */
public interface UserDAO {
	/**
	 * 返回所有用户
	 * 
	 * @return 用户列表
	 */
	public List<UserDO> queryAllUser();
	
	
	/**
	 * 查询用户名字
	 * 
	 * @param userId
	 * @return 用户名字
	 */
	public String queryUserById(Long userId);
}
