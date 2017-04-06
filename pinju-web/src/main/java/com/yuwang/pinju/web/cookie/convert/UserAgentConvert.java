package com.yuwang.pinju.web.cookie.convert;

import java.util.concurrent.atomic.AtomicInteger;

import com.yuwang.cookie.util.CodeUtil;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>用户浏览器 User-Agent 转换器</p>
 *
 * @author gaobaowen
 * @since 2011-9-20 15:03:59
 */
public class UserAgentConvert extends BasicConvert<Integer> {

	private final static long BASE = 10000000000L;
	private final AtomicInteger increment = new AtomicInteger((int)System.currentTimeMillis());

	@Override
	public String encode(Integer obj) {
		int base = Integer.MAX_VALUE;
		if (obj != null) {
			base = obj & Integer.MAX_VALUE;
		}
		long hash = BASE * next() + base;
		return CodeUtil.toBase62(hash);
	}

	@Override
	public Integer decode(String value) {
		int hash = (int)(CodeUtil.base62ToLong(value) % BASE);
		if (hash < 0) {
			return null;
		}
		if (!ServletUtil.getUserAgentHash().equals(hash)) {
			log.warn("read login user agent hash: [" + hash + "] is not same with browser agent[" + ServletUtil.getUserAgentHash() + "], need to re-login, cookie value: " + value);
			return null;
		}
		return hash;
	}

	private int next() {
		return (increment.incrementAndGet() & Integer.MAX_VALUE) % 1000;
	}
}
