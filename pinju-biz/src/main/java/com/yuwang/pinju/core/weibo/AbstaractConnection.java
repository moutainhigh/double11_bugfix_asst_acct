package com.yuwang.pinju.core.weibo;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AbstaractConnection {

	protected URL generateURL(String url, RequestParameter[] requestParameter) throws MalformedURLException {
		StringBuilder builder = new StringBuilder();
		builder.append(url);
		if (requestParameter != null && requestParameter.length > 0) {
			for (int i = 0; i < requestParameter.length; i++) {
				if (i == 0) {
					builder.append("?");
				} else {
					builder.append("&");
				}
				builder.append(requestParameter[i].getName()).append("=").append(encode(requestParameter[i].getValue()));
			}
		}
		return new URL(builder.toString());
	}
	
	protected String getPostContent(RequestParameter[] requestParameter) {
		StringBuilder builder = new StringBuilder();
		if (requestParameter != null && requestParameter.length > 0) {
			for (int i = 0; i < requestParameter.length; i++) {
				if (i != 0) {
					builder.append("&");
				} 
				builder.append(requestParameter[i].getName()).append("=").append(encode(requestParameter[i].getValue()));
			}
		}
		return builder.toString();
	}
	
	private String encode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException ignore) {
			return null;
		}
	}
}
