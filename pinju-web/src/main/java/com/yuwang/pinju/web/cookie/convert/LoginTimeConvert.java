package com.yuwang.pinju.web.cookie.convert;

import com.yuwang.cookie.util.CodeUtil;

/**
 * <p>登录时间转换器</p>
 *
 * @author gaobaowen
 * @since 2011-7-14 11:30:11
 */
public class LoginTimeConvert extends BasicConvert<Long> {

	@Override
	public String encode(Long obj) {
		return CodeUtil.toBase62(obj);
	}

	@Override
	public Long decode(String value) {
		Long time = CodeUtil.base62ToLong(value);
		if (time < 0) {
			log.warn("decode login time error, value: " + value + ", decode value: " + time);
			return null;
		}
		return time;
	}
}
