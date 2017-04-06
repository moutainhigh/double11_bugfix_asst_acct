package com.yuwang.pinju.core.cache;

import com.yuwang.pinju.core.constant.system.PinjuConstant;

/**
 * <p>缓存工具类</p>
 *
 * @author gaobaowen
 * @since 2011-10-20 09:21:56
 */
public class CacheUtil {

	private CacheUtil(){}

	/**
	 * <p>根据调试状态值确定是否需要直接使用缓存中的数据。若为非开发模式时该值永远返回 true</p>
	 *
	 * @param isCacheDebug 是否处于缓存调试模式
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-10-20 09:22:15
	 */
	public static boolean isReadCache(boolean isCacheDebug) {
		return (!PinjuConstant.isDevelopment || !isCacheDebug);
	}
}