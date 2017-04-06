package com.yuwang.pinju.domain.ajax;

/**
 * 封装主页Ajax请求返回提示信息
 * 
 * @author qiuhongming
 *
 */
public class TipsInfoDO {
	/**
	 * 是否已经登录
	 */
	private boolean login;

	public TipsInfoDO() {
		super();
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

}
