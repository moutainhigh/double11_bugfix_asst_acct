package com.yuwang.pinju.web.cookie.convert;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.util.Tools;

public class OriginAgreementConvert extends BasicConvert<CookieMemberOriginAgreement> {

	private final static Log log = LogFactory.getLog(OriginAgreementConvert.class);

	private final static Pattern pattern = Pattern.compile("[0-9][01][0-2]");

	@Override
	public String encode(CookieMemberOriginAgreement obj) {
		return obj.toCookieString();
	}

	@Override
	public CookieMemberOriginAgreement decode(String value) {
		if(Tools.isBlank(value)) {
			log.warn("decode origin and agreement cookie value, value is null or empty, value: [" + value + "]");
			return null;
		}
		if(!pattern.matcher(value).matches()) {
			log.warn("decode origin and agreement status cookie value, value pattern is not [0-9][01][0-2], value: [" + value + "]");
			return null;
		}
		return new CookieMemberOriginAgreement( Integer.parseInt(value) );
	}
}
