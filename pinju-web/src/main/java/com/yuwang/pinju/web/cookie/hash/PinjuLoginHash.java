package com.yuwang.pinju.web.cookie.hash;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.hash.CookieHash;
import com.yuwang.cookie.rw.CookieValue;
import com.yuwang.cookie.rw.CookieValue.SubCookieMap;
import com.yuwang.cookie.util.CookieBinaryUtil;
import com.yuwang.pinju.core.constant.system.PinjuConstant;

/**
 * <p>登录数据 HASH 计算</p>
 *
 * @author gaobaowen
 * @since 2011-9-17 16:43:00
 */
public class PinjuLoginHash implements CookieHash {

	private final static Log log = LogFactory.getLog(PinjuLoginHash.class);

	@Override
	public String hash(Collection<CookieValue> cookieValues) {
		StringBuilder builder = new StringBuilder(PinjuLoginHash.class.getName());
		for(CookieValue value : cookieValues) {
			Object original = value.getOriginalValue();
			if (original instanceof SubCookieMap) {
				for (Object subvalue : ((SubCookieMap)original).values()) {
					append(builder, subvalue);
				}
			} else {
				append(builder, original);
			}
		}
		byte[] bys = new byte[0];
		try {
			bys = DigestUtils.sha(builder.toString().getBytes(PinjuConstant.DEFAULT_CHARSET));
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		return CookieBinaryUtil.encode(bys);



//		for(CookieValue value : cookieValues) {
//			Object original = value.getOriginalValue();
//			if (original instanceof SubCookieMap) {
//				for (Object subvalue : ((SubCookieMap)original).values()) {
//					n = (int)hash(subvalue, n, 31);
//					System.out.println("----------> " + subvalue);
//				}
//			} else {
//				n = (int)hash(original, n, 31);
//				System.out.println("------> " + original);
//			}
//		}
//		n &= Integer.MAX_VALUE;
//		StringBuilder builder = new StringBuilder();
//		for(CookieValue value : cookieValues) {
//			long x = 1;
//			Object original = value.getOriginalValue();
//			if (original instanceof SubCookieMap) {
//				for (Object subvalue : ((SubCookieMap)original).values()) {
//					x = hash(subvalue, x, n);
//					x |= 0x100000000L;
//					x &= 0xcffffffffL;
//					builder.append(CodeUtil.toBase62(x));
//				}
//			} else {
//				x = hash(original, x, n);
//				x |= 0x100000000L;
//				x &= 0xcffffffffL;
//				builder.append(CodeUtil.toBase62(x));
//			}
//		}
//		return builder.toString();
	}

	@Override
	public boolean validate(Collection<CookieValue> cookieValues, String hash) {
		String hashValue = hash(cookieValues);
		boolean result = hashValue.equals(hash);
		if (log.isDebugEnabled()) {
			log.debug("validate hash, cookie hash: [" + hash + "], calculate hash: [" + hashValue + "], check result: [" + result + "]");
		}
		return result;
	}

	private StringBuilder append(StringBuilder builder, Object obj) {
		builder.append("-_-|||");
		builder.append(String.valueOf(obj));
		return builder;
	}
}
