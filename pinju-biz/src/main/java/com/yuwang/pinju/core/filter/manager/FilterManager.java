package com.yuwang.pinju.core.filter.manager;

import com.yuwang.pinju.core.filter.FilterResult;

/**
 * <p>字词过滤器</p>
 *
 * @author gaobaowen
 * @since 2011-8-8 14:56:21
 */
public interface FilterManager {

	/**
	 * <p>过滤字词</p>
	 *
	 * @param statement  需要过滤的语句，不忽略标点符号
	 * @return  过滤结果
	 *
	 * @author gaobaowen
	 * @since 2011-8-8 14:56:35
	 */
	FilterResult doFilter(String statement);

	/**
	 * <p>过滤字词</p>
	 *
	 * @param statement 需要过滤的语句
	 * @param ignorePunctuation 是否需要忽略标点符号
	 * @return 过滤结果
	 *
	 * @author gaobaowen
	 * @since 2011-8-8 14:56:58
	 */
	FilterResult doFilter(String statement, boolean ignorePunctuation);
}