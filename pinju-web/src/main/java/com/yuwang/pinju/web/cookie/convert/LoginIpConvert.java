package com.yuwang.pinju.web.cookie.convert;

import java.util.concurrent.atomic.AtomicInteger;

import com.yuwang.cookie.util.CodeUtil;

/**
 * <p>登录 IP 地址转换</p>
 *
 * @author gaobaowen
 * @since 2011-9-20 15:00:11
 */
public class LoginIpConvert extends BasicConvert<Long> {

	private final static AtomicInteger IP_INCREMENT = new AtomicInteger((int)(System.currentTimeMillis()));
	private final static long BASE = 10000000000L;

	@Override
	public String encode(Long obj) {
		if (obj == null) {
			obj = 0L;
		}
		long encIp = obj + (IP_INCREMENT.incrementAndGet() & Integer.MAX_VALUE) % 1000 * BASE;
		return CodeUtil.toBase62(encIp);
	}

	@Override
	public Long decode(String value) {
		long clientIp = CodeUtil.base62ToLong(value) % BASE;
		if(clientIp < 0 || clientIp > 0xffffffffL) {
        	log.warn("login clientIp is invalid, clientIp: [" + value + "], number client ip: [" + clientIp + "], token: " + value);
			return null;
        }
		return clientIp;
	}
}
