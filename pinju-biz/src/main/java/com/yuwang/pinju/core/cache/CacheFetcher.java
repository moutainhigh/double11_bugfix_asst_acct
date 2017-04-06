package com.yuwang.pinju.core.cache;

/**
 * <p>缓存数据拮取器。实现类用于缓存中无数据时重新产生需要缓存的数据。</p>
 *
 * @param <T> 缓存数据抓取的数据类型
 * @param <C> 数据抓取条件的数据类型
 * @author gaobaowen
 * @since 2011-12-22 09:55:00
 */
public interface CacheFetcher {
	
	/**
	 * 
	 * <p>抓取待缓存的数据</p>
	 *
	 * @param condition 数据抓取条件
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 09:55:46
	 */
	Object fetch(Object condition);
}
