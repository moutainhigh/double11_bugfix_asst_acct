package com.yuwang.pinju.core.user.manager;

import java.util.List;

import com.yuwang.pinju.domain.user.UserDO;




/**
 * 用户业务管理接口
 * 
 * @author mike
 *
 */
public interface UserManager {
	/**
	 * 用户列表
	 * 
	 * @return User List
	 */
	public List<UserDO> queryAllUser();
	
	
	/**
	 * 返回用户名字
	 * 
	 * @return 用户名字
	 */
	public String  queryUserById(Long userId);
}
