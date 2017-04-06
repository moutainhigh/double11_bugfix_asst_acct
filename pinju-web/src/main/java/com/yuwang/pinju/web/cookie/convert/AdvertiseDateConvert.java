package com.yuwang.pinju.web.cookie.convert;

public class AdvertiseDateConvert extends BasicConvert<Long> {

	@Override
	public Long decode(String value) {
		return Long.parseLong(value);
	}

	@Override
	public String encode(Long value) {
		if(value != null){
			return value.toString();
		}else{
			return "";
		}
	}

}
