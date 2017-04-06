package com.yuwang.pinju.core.cache.impl;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.cache.CacheFetcher;
import com.yuwang.pinju.core.cache.MemcachedManager;

public class MemcachedManagerImpl implements MemcachedManager {

	protected Log log = LogFactory.getLog(getClass());

	/**
	 * 缓存客户端操作对象
	 */
	private MemcachedClient client;

	/**
	 * 缓存 indexKey
	 */
	private String indexKey;

	/**
	 * 过期时间
	 */
	private int expires;

	/**
	 * 缓存为空时的数据抓取器
	 */
	private CacheFetcher fetcher;

	public MemcachedManagerImpl(MemcachedClient client, String indexKey, int expires) {
		this.client = client;
		this.indexKey = indexKey;
		this.expires = expires;
	}

	public MemcachedManagerImpl(MemcachedClient client, String indexKey, int expires, CacheFetcher fetcher) {
		this(client, indexKey, expires);
		this.fetcher = fetcher;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCacheObject(Object key, Class<T> clazz) {
		if(log.isDebugEnabled()) {
			log.debug("getCacheObject, key: " + key);
		}
		String genKey = generateKey(key);
		if (genKey == null) {
			return null;
		}
		try {
			T obj = (T)client.get(genKey);
			if(log.isDebugEnabled()) {
				log.debug("getCacheObject, gen key: " + genKey + ", find? " + (obj != null));
			}
			return obj;
		} catch (Throwable e) {
			log.error("getCacheObject memcached error, gen key: " + genKey, e);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCacheObject(Object key, Class<T> clazz, Object condition) {
		if (fetcher == null) {
			throw new IllegalStateException("CacheFetcher is null, cannot execute the method. Inject CacheFetcher object using constructor method");
		}
		T obj = getCacheObject(key, clazz);
		if (obj == null && fetcher != null) {
			log.warn("[getCacheObject] fetcher is not null, can not find data from memcached, fetch data from Fetcher interface, key: " + key + ", class: " + clazz);
			obj = (T)fetcher.fetch(condition);
			log.warn("[getCacheObject] fetch data from Fetcher, result: " + obj);
			if (obj != null) {
				setCacheObject(key, obj);
			}
		}
		return obj;
	}

	@Override
	public boolean clearCacheObject(Object key) {
		String genKey = generateKey(key);
		if(genKey == null) {
			return false;
		}
		try {
			boolean result = client.delete(genKey);
			if (log.isDebugEnabled()) {
				log.debug("clearCacheObject, result: [" + result + "], key: [" + genKey + "]");
			}
			return result;
		} catch (Throwable e) {
			log.error("clearCacheObject memcached error, gen key: " + genKey, e);
			return false;
		}
	}

	@Override
	public boolean setCacheObject(Object key, Object value) {
		if (log.isDebugEnabled()) {
			log.debug("setCacheObject, key: " + key + ", value: " + value + ", expires: " + expires);
		}
		String genKey = generateKey(key);
		if (genKey == null) {
			return false;
		}
		try {
			boolean result = client.set(genKey, expires, value);
			if (log.isDebugEnabled()) {
				log.debug("setCacheObject, gen key: " + genKey + ", set result: " + result);
			}
			return result;
		} catch (Throwable e) {
			log.error("setCacheObject memcached error, gen key: " + genKey + ", value: " + value + ", expires: " + expires, e);
			return false;
		}
	}

	private String generateKey(Object key) {
		if (key == null) {
			log.error("setCacheObject, key is null, cannot set the object cache");
			return null;
		}
		if ((key instanceof String) && ((String) key).trim().length() == 0) {
			log.error("setCacheObject, key is empty, cannot set the object cache");
			return null;
		}
		return indexKey + '.' + String.valueOf(key);
	}

	@Override
	public <T> T getAndClearCachedObject(Object key, Class<T> clazz) {
		T result = getCacheObject(key, clazz);
		if (result == null) {
			log.warn("");
			return null;
		}
		boolean clear = clearCacheObject(key);
		if (!clear) {
			log.error("[getAndClearCachedObject] clear cache result is false, key: [" + key + "]");
		}
		return result;
	}
}
