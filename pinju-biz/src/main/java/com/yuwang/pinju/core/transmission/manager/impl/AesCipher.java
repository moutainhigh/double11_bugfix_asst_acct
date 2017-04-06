package com.yuwang.pinju.core.transmission.manager.impl;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.util.CharsetUtil;

/**
 * <p>AES 解码</p>
 *
 * @author gaobaowen
 * @since 2011-11-30 13:09:43
 */
public class AesCipher {

	private final static Log log = LogFactory.getLog(AesCipher.class);

	private final static String CRYPTO_ALGORITHM = "AES/CBC/ISO10126Padding";

	/**
	 * 加密密钥
	 */
	private Key key;

	/**
	 * CBC 初始化向量（IV）
	 */
	private AlgorithmParameterSpec iv;

	/**
	 * 缓存中密钥和 IV 的原始字符串
	 */
	private String cachedKey;

	private AesCipher(Key key, AlgorithmParameterSpec iv, String cachedKey) {
		this.key = key;
		this.iv = iv;
		this.cachedKey = cachedKey;
	}

	/**
	 * <p>解析带有 IV 值的密钥构建对象。</p>
	 *
	 * @param cachedKey
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-11-30 下午01:10:04
	 */
	public static AesCipher parse(String cachedKey) {
		try {
			byte[] bys = Hex.decodeHex(cachedKey.toCharArray());
			Key key = new SecretKeySpec(bys, 0, 32, "AES");
			AlgorithmParameterSpec iv = new IvParameterSpec(bys, 32, 16);
			return new AesCipher(key, iv, cachedKey);
		} catch (Exception e) {
			log.error("[parseKey] decode cached key exception, cached key: [" + cachedKey + "]");
			return null;
		}
	}

	public String[] decryptHexes(String... hexes) {
		if (hexes == null || hexes.length == 0) {
			return new String[0];
		}
		String[] plains = new String[hexes.length];
		for (int i = 0; i < hexes.length; i++) {
			plains[i] = decryptHex(hexes[i]);
			if (plains[i] == null) {
				log.warn("[decryptHexes], index: [" + i + "], hex: [" + hexes[i] + "] is invalid");
				break;
			}
		}
		return plains;
	}

	public String decryptHex(String hex) {
		if (StringUtil.isBlank(hex) || !StringUtil.isByteHex(hex)) {
			log.warn("[decryptHex] parameter is not HEX, hex: [" + hex + "]");
			return null;
		}
		if (hex.length() % 32 != 0) {
			log.warn("[decryptHex] parameter is AES 256 cipher text, hex: [" + hex + "]");
			return null;
		}
		try {
			return decrypt(Hex.decodeHex(hex.toCharArray()));
		} catch (Exception e) {
			log.warn("[decryptHex] decode hex cause exception, hex: [" + hex + "]", e);
			return null;
		}
	}

	private String decrypt(byte[] bys) {
		try {
			Cipher cipher = Cipher.getInstance(CRYPTO_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
			return CharsetUtil.toString(cipher.doFinal(bys));
		} catch (Exception e) {
			log.error("[decrypt] cause exception, cached key: [" + cachedKey + "], " +
					"cipher text(hex): [" + (bys == null ? "null" : new String(Hex.encodeHex(bys))) + "]", e);
			return null;
		}
	}
}
