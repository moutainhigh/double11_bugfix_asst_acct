package com.yuwang.pinju.core.member.manager;

import com.yuwang.pinju.domain.member.login.LoginPageImgVO;

/**
 * <p>登录页面管理</p>
 *
 * @author gaobaowen
 * @since 2011-11-30 16:58:04
 */
public interface LoginPageManager {

	/**
	 * <p>获取登录页面的图片数据。该方法的实现需要先从缓存中获取数据，若缓存中无数据时，将从主库中进行查询。
	 * 若主库查询失败，将从备库中查询。若备库中查询失败，则使用默认的图片数据 {@link #DEFAULT_LOGIN_PAGE_IMG}。</p>
	 *
	 * @param disabledCache  是否需要禁用从缓存中获取数据
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-11-30 16:58:15
	 */
	LoginPageImgVO getLoginPageImg(boolean disabledCache);
}
