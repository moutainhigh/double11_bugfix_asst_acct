package com.yuwang.pinju.core.user.ao;

/**
 * 用户管理业务场景封装
 * 
 * @author mike
 *
 */
public interface UserAO {
	/**
	 * 查询用户
	 * 
	 * @return 用户名字
	 */
	public String queryUser(Long userId);
}
