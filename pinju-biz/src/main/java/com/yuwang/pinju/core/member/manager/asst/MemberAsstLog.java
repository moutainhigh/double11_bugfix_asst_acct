package com.yuwang.pinju.core.member.manager.asst;

/**
 * <p>子账号会员操作日志记录</p>
 *
 * @author gaobaowen
 * @since 2011-12-23 14:49:26
 */
public interface MemberAsstLog {

	/**
	 * <p>记录子账号操作日志</p>
	 *
	 * @param message 操作日志内容
	 *
	 * @author gaobaowen
	 * @since 2011-12-23 14:49:46
	 */
	void log(String message);

	/**
	 * <p>记录子账号操作日志</p>
	 *
	 * @param target  操作权限目标
	 * @param action  操作权限行为
	 * @param message 操作日志内容
	 *
	 * @author gaobaowen
	 * @since 2011-12-23 14:49:56
	 */
	void log(String target, String action, String message);
}
