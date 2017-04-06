package com.yuwang.pinju.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.yuwang.pinju.core.constant.system.PinjuConstant;

public class CharsetUtil {

	private CharsetUtil(){}

	public static String encodeURL(String str) {
		if (EmptyUtil.isBlank(str)) {
			return str;
		}
		try {
			return URLEncoder.encode(str, PinjuConstant.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static String decodeURL(String str) {
		if (EmptyUtil.isBlank(str)) {
			return str;
		}
		try {
			return URLDecoder.decode(str, PinjuConstant.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static byte[] toBytes(String str) {
		if (str == null) {
			return null;
		}
		try {
			return str.getBytes(PinjuConstant.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static String toString(byte[] bys) {
		if (bys == null) {
			return null;
		}
		try {
			return new String(bys, PinjuConstant.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
