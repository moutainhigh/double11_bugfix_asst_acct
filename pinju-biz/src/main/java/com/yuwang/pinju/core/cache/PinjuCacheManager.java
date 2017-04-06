package com.yuwang.pinju.core.cache;

import com.yuwang.pinju.domain.member.MemberLoginDO;

/**
 * <p>品聚缓存管理</p>
 *
 * @author gaobaowen
 * @since 2011-7-28 14:09:53
 */
public interface PinjuCacheManager {

	/**
	 * <p>将验证码放入缓存</p>
	 *
	 * @param sessionId  会话ID
	 * @param captcha 验证码值
	 * @param expires 验证码值超时时间
	 * @return 缓存添加是否成功
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 14:14:16
	 */
	boolean setCaptcha(String sessionId, String captcha, int expires);
	
	/**
	 * <p>清除会话编号的验证码</p>
	 *
	 * @param sessionId 会话ID
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 14:24:07
	 */
	boolean clearCaptcha(String sessionId);

	/**
	 * <p>从缓存中获取验证码值</p>
	 *
	 * @param sessionId  会话ID
	 * @return 验证码值
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 14:14:16
	 */
	String getCaptcha(String sessionId);

	/**
	 * <p>将会员错误登录次数放入缓存</p>
	 *
	 * @param login 登录信息
	 * @param count  错误登录次数
	 * @param expires  缓存超时时间
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 14:17:25
	 */
	boolean setLoginErrorCount(MemberLoginDO login, Integer count, int expires);
	
	/**
	 * <p>清除会员的错误登录缓存</p>
	 *
	 * @param login 登录信息
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 14:24:51
	 */
	boolean clearLoginErrorCount(MemberLoginDO login);

	/**
	 * <p>从缓存中获取错误的登录次数</p>
	 *
	 * @param login 登录信息
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 下午02:18:06
	 */
	int getLoginErrorCount(MemberLoginDO login);
}
