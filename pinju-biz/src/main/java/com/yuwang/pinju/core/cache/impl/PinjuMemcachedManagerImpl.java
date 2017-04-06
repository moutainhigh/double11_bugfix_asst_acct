package com.yuwang.pinju.core.cache.impl;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import com.yuwang.pinju.core.cache.PinjuCacheManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberLoginDO;

public class PinjuMemcachedManagerImpl implements PinjuCacheManager {

	private final static Log log = LogFactory.getLog(PinjuMemcachedManagerImpl.class);

	private MemcachedClient captchaMemcachedClient;
	private MemcachedClient loginMemcachedClient;

	@Value("${memcached.indexkey.pinju.captcha}")
	private String captchaIndexKey;

	@Value("${memcached.indexkey.pinju.login}")
	private String loginIndexKey;

	public void setCaptchaMemcachedClient(MemcachedClient captchaMemcachedClient) {
		this.captchaMemcachedClient = captchaMemcachedClient;
	}

	public void setLoginMemcachedClient(MemcachedClient loginMemcachedClient) {
		this.loginMemcachedClient = loginMemcachedClient;
	}

	@Override
	public boolean setCaptcha(String sessionId, String captcha, int expires) {
		return setCacheObject(captchaMemcachedClient, generateCaptchaKey(sessionId), captcha, expires);
	}

	@Override
	public boolean clearCaptcha(String sessionId) {
		return clearCacheObject(captchaMemcachedClient, generateCaptchaKey(sessionId));
	}

	@Override
	public String getCaptcha(String sessionId) {
		return getCacheObject(captchaMemcachedClient, generateCaptchaKey(sessionId), String.class);
	}

	@Override
	public boolean setLoginErrorCount(MemberLoginDO login, Integer count, int expires) {
		return setCacheObject(loginMemcachedClient, generateLoginErrorCountKey(login), count, expires);
	}

	@Override
	public boolean clearLoginErrorCount(MemberLoginDO login) {
		return clearCacheObject(loginMemcachedClient, generateLoginErrorCountKey(login));
	}

	@Override
	public int getLoginErrorCount(MemberLoginDO login) {
		Integer count = getCacheObject(loginMemcachedClient, generateLoginErrorCountKey(login), Integer.class);
		if (count == null) {
			return 0;
		}
		return count;
	}

	private String generateCaptchaKey(String sessionId) {
		if (EmptyUtil.isBlank(sessionId)) {
			return null;
		}
		return captchaIndexKey + "." + sessionId;
	}

	private String generateLoginErrorCountKey(MemberLoginDO login) {
		return loginIndexKey + "." + login.getLoginName();
	}

	private boolean setCacheObject(MemcachedClient client, String key, Object value, int expires) {
		if(log.isDebugEnabled()) {
			log.debug("setCacheObject, key: " + key + ", value: " + value + ", expires: " + expires);
		}
		if (key == null) {
			log.error("setCacheObject, key is null, cannot set the object cache");
			return false;
		}
		if (value == null) {
			log.error("setCacheObject, value is null, cannot set the object cache");
			return false;
		}
		try {
			boolean result = client.set(key, expires, value);
			if(log.isDebugEnabled()) {
				log.debug("setCacheObject, key: " + key + ", set result: " + result);
			}
			return result;
		} catch (Throwable e) {
			log.error("setCacheObject memcached error, key: " + key + ", value: " + value + ", expires: " + expires + key, e);
			return false;
		}
	}

	private boolean clearCacheObject(MemcachedClient client, String key) {
		if(EmptyUtil.isBlank(key)) {
			return false;
		}
		try {
			return client.delete(key);
		} catch (Exception e) {
			log.error("clearCacheObject memcached error, key: " + key, e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T getCacheObject(MemcachedClient client, String key, Class<T> clazz) {
		if(log.isDebugEnabled()) {
			log.debug("getCacheObject, key: " + key);
		}
		try {
			T obj = (T)client.get(key);
			if(log.isDebugEnabled()) {
				log.debug("getCacheObject, key: " + key + ", find? " + (obj != null));
			}
			return obj;
		} catch (Throwable e) {
			log.error("getCacheObject memcached error, key: " + key, e);
			return null;
		}
	}
}
