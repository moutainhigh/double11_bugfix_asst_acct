package com.yuwang.pinju.core.cache;

/**
 * <p>缓存管理操作。需要注意的是：缓存的 key 均会在该 key 之间加上 indexKey 数据。
 * 比如：缓存 key 叫 name，而 indexKey 为：com.yuwang.cache.index.login，
 * 实际放入缓存的 key 为 com.yuwang.cache.index.login.name。</p>
 *
 * @author gaobaowen
 * @since 2011-9-13 14:59:43
 */
public interface MemcachedManager {

	/**
	 * <p>获取缓存中的数据</p>
	 *
	 * @param <T>  数据对象类型
	 * @param key  缓存的 key（不含 indexKey）
	 * @param clazz 对象类型
	 * @return 已缓存的对象，若无法获取或者其他异常情况时返回 null 值
	 *
	 * @author gaobaowen
	 * @since 2011-9-13 14:59:56
	 */
	<T> T getCacheObject(Object key, Class<T> clazz);

	/**
	 * 
	 * <p>获取缓存中的数据，若缓存中无该数据，则从 {@link CacheFetcher} 中获取数据后再存入缓存，
	 * 若取得的数据为 null 时不再存入缓存</p>
	 *
	 * @param <T>  缓存中的数据类型
	 * @param key  缓存的 key
	 * @param clazz 缓存数据类型 Class 对象
	 * @param condition 无法从缓存中获取，从数据抓取器中获取数据时的条件
	 * @return  已缓存的对象
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 10:56:43
	 */
	<T> T getCacheObject(Object key, Class<T> clazz, Object condition);

	/**
	 * <p>清除缓存中的数据</p>
	 *
	 * @param key 缓存的 key（不含 indexKey）
	 * @return  清除成功返回 true，否则返回 false
	 *
	 * @author gaobaowen
	 * @since 2011-9-13 15:02:44
	 */
	boolean clearCacheObject(Object key);

	/**
	 * <p>将数据对象放入缓存</p>
	 *
	 * @param key  缓存的 key（不含 indexKey）
	 * @param value 需要缓存的对象
	 * @return 设置成功返回 true，否则返回 false
	 *
	 * @author gaobaowen
	 * @since 2011-9-13 15:03:39
	 */
	boolean setCacheObject(Object key, Object value);

	/**
	 * <p>获取缓存中的数据，并从缓存中删除该数据</p>
	 *
	 * @param <T>  数据对象类型
	 * @param key  缓存的 key（不含 indexKey）
	 * @param clazz 对象类型
	 * @return 已缓存的对象，若无法获取或者其他异常情况时返回 null 值
	 *
	 * @author gaobaowen
	 * @since 2011-11-28 18:00:00
	 */
	<T> T getAndClearCachedObject(Object key, Class<T> clazz);
}
