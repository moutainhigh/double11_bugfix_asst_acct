package com.yuwang.pinju.filter;

/**
 * <p>字符串过滤</p>
 *
 * @author gaobaowen
 * @since 2011-9-26 13:36:33
 */
public interface StringFilter {

	/**
	 * <p>执行过滤</p>
	 *
	 * @param str 需要过滤的字符串
	 * @return 过滤后的字符串
	 *
	 * @author gaobaowen
	 * @since 2011-9-26 13:36:47
	 */
	String doFilter(String str);
}
