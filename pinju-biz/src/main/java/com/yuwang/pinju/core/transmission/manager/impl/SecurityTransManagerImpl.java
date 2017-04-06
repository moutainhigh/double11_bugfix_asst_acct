package com.yuwang.pinju.core.transmission.manager.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.RandomUUID;
import com.yuwang.pinju.common.ReflectionUtils;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.cache.MemcachedManager;
import com.yuwang.pinju.core.constant.system.PinjuConstantUtil;
import com.yuwang.pinju.core.transmission.manager.SecurityTransManager;
import com.yuwang.pinju.domain.annotation.SecurityTransmission;
import com.yuwang.pinju.domain.transmission.TransHandshakeResponse;

public class SecurityTransManagerImpl implements SecurityTransManager {

	private final static Log log = LogFactory.getLog(SecurityTransManagerImpl.class);

	private MemcachedManager transKeyMemcachedManager;

	private static Map<Class<?>, PropertyDescriptor[]> cache = new ConcurrentHashMap<Class<?>, PropertyDescriptor[]>();

	public void setTransKeyMemcachedManager(MemcachedManager transKeyMemcachedManager) {
		this.transKeyMemcachedManager = transKeyMemcachedManager;
	}

	@Override
	public TransHandshakeResponse handshake(String base64CipherText) {
		String plain = PinjuConstantUtil.decryptRsaB64(base64CipherText);
		if (plain == null) {
			log.error("[handshake] decrypt cipher error, cipher text: " + base64CipherText);
			return null;
		}
		// AES 256 key (32bytes) and IV(16bytes), total 48bytes, also 96 hex characters
		if (plain.length() != 96 || !StringUtil.isByteHex(plain)) {
			log.error("[handshake] decrypt cipher is not AES 256 key and iv [" + plain + "], cipher text: " + base64CipherText);
			return null;
		}
		TransHandshakeResponse response = new TransHandshakeResponse(plain);
		String transId = response.getTransId();

		boolean result = false;
		int retry = 0;

		while (!result && ++retry < 3) {
			result = transKeyMemcachedManager.setCacheObject(transId, plain);
			if (!result) {
				log.warn("[handshake] set cache failed, transId: [" + transId + "], key: [" + plain + "], retry count: [" + retry + "]");
			}
		}

		if (!result) {
			log.error("[handshake] retry set cache failed, transId: [" + transId + "], key: [" + plain + "] handshake execute failed");
			return null;
		}

		if (log.isInfoEnabled()) {
			log.info("[handshake] response: " + response);
		}
		return response;
	}

	public String[] decryptTransData(String transId, String... ciphersHex) {
		if (ciphersHex == null || ciphersHex.length == 0) {
			log.warn("[decryptTransData] cipherHex is null, can not decrypt, transId: [" + transId + "]");
			return new String[0];
		}
		if (!RandomUUID.isRandomUUID(transId, 30)) {
			log.warn("[decryptTransData] transId (" + transId + ") is invalid, cipher hex: [" + Arrays.toString(ciphersHex) + "]");
			return new String[ciphersHex.length];
		}
		String cachedKey = transKeyMemcachedManager.getAndClearCachedObject(transId, String.class);
		if (StringUtils.isBlank(cachedKey)) {
			log.error("[decryptTransData] transId (" + transId + ") can not find key, cipher hex: [" + ciphersHex + "]");
			return new String[ciphersHex.length];
		}
		AesCipher aes = AesCipher.parse(cachedKey);
		if (aes == null) {
			log.error("[decryptTransData] construct AesCipher object cause error, transId: [" + transId + "], cipher hex: [" + Arrays.toString(ciphersHex) + "], key hex: [" + cachedKey + "]");
			return new String[ciphersHex.length];
		}
		return aes.decryptHexes(ciphersHex);
	}

	@Override
	public boolean decryptProperties(String transId, Object obj) {
		if (obj == null) {
			log.warn("[decryptProperties] decrypt object is null, transId: [" + transId + "]");
			return false;
		}
		if (!RandomUUID.isRandomUUID(transId, 30)) {
			log.warn("[decryptProperties] transId (" + transId + ") is invalid, object: " + obj);
			return false;
		}
		try {
			PropertyDescriptor[] properties = getSecurityTransmissionPD(obj);
			String[] ciphers = new String[properties.length];
			for (int i = 0; i < properties.length; i++) {
				ciphers[i] = (String)properties[i].getReadMethod().invoke(obj);
			}
			String[] plains = decryptTransData(transId, ciphers);
			if (plains == null || plains.length != properties.length || StringUtil.hasBlank(plains)) {
				log.warn("[decryptProperties] plains has invalid, tid: [" + transId + "], plains: [" + Arrays.toString(plains) + "], obj: " + obj);
				return false;
			}
			for (int i = 0; i < properties.length; i++) {
				properties[i].getWriteMethod().invoke(obj, plains[i]);
			}
			return true;
		} catch (Exception e) {
			log.error("[decryptProperties] cause exception, tid: [" + transId + "], obj: " + obj, e);
			return false;
		}
	}

	private static PropertyDescriptor[] getSecurityTransmissionPD(Object obj) throws IntrospectionException {
		if (obj == null) {
			return new PropertyDescriptor[0];
		}
		if (log.isDebugEnabled()) {
			log.debug("[getSecurityTransmissionPD] get PD object: " + obj.getClass().getName());
		}

		PropertyDescriptor[] pds = cache.get(obj.getClass());
		if (pds == null) {
			pds = ReflectionUtils.getPropertyDescriptors(obj, SecurityTransmission.class);
			cache.put(obj.getClass(), pds);
			log.warn("[getSecurityTransmissionPD] in cache not exists, reflect it, class: [" + obj.getClass().getName() +
						"], property descriptiors: " + Arrays.toString(pds) + "], cache key: [" + cache.keySet() + "]");
		} else if (log.isDebugEnabled()) {
			log.debug("[getSecurityTransmissionPD] find it from cache, class: [" + obj.getClass().getName() +
					"], pds size: [" + pds.length + "]");
		}
		return pds;
	}
}
