package com.yuwang.pinju.core.constant.system;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.util.CharsetUtil;


/**
 * <p>{@link PinjuConstant} 相关的工具方法</p>
 * @author gaobaowen
 * @since 2011-11-23 上午11:31:01
 */
public class PinjuConstantUtil {

	private final static Log log = LogFactory.getLog(PinjuConstantUtil.class);

	private PinjuConstantUtil(){}

	/**
	 * <p>判断是否为手机号码</p>
	 *
	 * @param mobile
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-11-23 11:31:21
	 */
	public static boolean isMobile(String mobile) {
		if (mobile == null || mobile.length() != 11) {
			return false;
		}
		return PinjuConstant.MOBILE_NUMBER_PATTERN.matcher(mobile).matches();
	}

	public static String decryptRsaB64(String base64CipherText) {
		if (StringUtils.isBlank(base64CipherText)) {
			log.warn("[RSA decrypt] cipherText is empty, cipherText: [" + base64CipherText + "]");
			return null;
		}
		try {
			byte[] bys = Base64.decodeBase64(base64CipherText);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, PinjuConstant.AUTH_RSA_PRIVATE_KEY);
			byte[] plain = cipher.doFinal( bys );
			return CharsetUtil.toString( plain );
		} catch (Exception e) {
			log.error("[RSA decrypt] cause exception, cipherText: " + base64CipherText + ", message: " + e.getMessage());
			return null;
		}
	}

	/**
	 * <p>将 Base64 的 RSA private key 值转换成为 Key 对象</p>
	 *
	 * @param rsaBase64PrivateKey
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-11-25 14:54:57
	 */
	static Key privateKey(String rsaBase64PrivateKey) {
		try {
			log.warn("[RSA] private key: " + rsaBase64PrivateKey);
			KeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(rsaBase64PrivateKey));
			KeyFactory factory = KeyFactory.getInstance("RSA");
			return factory.generatePrivate(spec);
		} catch (Exception e) {
			throw new IllegalArgumentException("RSA private key is invalid! Please check autoconfig.xml " +
					"name [auth.rsa.private.key] configuration value, the value must be a Base64 of " +
					"RSA private key, the key size is 2048 bits recommended, in order to improve computation " +
					"speed can using 1024 bits also. if the configuration value was modified, need update " +
					"public key module, the module in http://static.pinju.com/js/member/key.js javascript file " +
					"that variable name is MODULES. If need generate a RSA key pair, please execute pinju-biz " +
					"project com.yuwang.pinju.core.constant.system.RSAKeyPairGenerator class main method", e);
		}
	}
}
