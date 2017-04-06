package com.yuwang.pinju.core.transmission.manager;

import com.yuwang.pinju.domain.annotation.SecurityTransmission;
import com.yuwang.pinju.domain.transmission.TransHandshakeResponse;

/**
 * <p>安全传输数据交互</p>
 *
 * @author gaobaowen
 * @since 2011-11-28 上午10:39:14
 */
public interface SecurityTransManager {

	/**
	 * <p>安全数据传输的握手。客户端通过 RSA 公钥将后续数据加密用的密钥告知服务端。
	 * 服务端通过该方法进行处理客户端传输过来的密钥密文。</p>
	 *
	 * @param base64CipherText 基于 Base64 编码的加密密钥的 RSA 密文
	 * @return 密钥交换服务端握手握手响应
	 *
	 * @author gaobaowen
	 * @since 2011-11-29 14:39:48
	 */
	TransHandshakeResponse handshake(String base64CipherText);

	/**
	 * <p>通过握手过程中产生的密钥会话 ID 以及客户端加密数据的密文进行解密处理。</p>
	 *
	 * @param transId 密钥会话 ID
	 * @param ciphersHex 一系列的密文
	 * @return 解密后的明文
	 *
	 * @author gaobaowen
	 * @since 2011-11-29 14:42:00
	 */
	String[] decryptTransData(String transId, String... ciphersHex);

	/**
	 * <p>使用密钥会话ID所对应的密钥对对象属性中的密文进行解密处理，原密文值将重置为解密后的明文。
	 * 如果该对象中的某些属性是采用加密传输时，需要在其 get 或者 set 方法上标注 @{@link SecurityTransmission}</p>
	 *
	 * @param transId
	 * @param obj
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-11-29 下午03:10:53
	 */
	boolean decryptProperties(String transId, Object obj);
}
