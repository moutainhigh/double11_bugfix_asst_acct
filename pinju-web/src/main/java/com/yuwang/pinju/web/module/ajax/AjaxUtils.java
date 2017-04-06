package com.yuwang.pinju.web.module.ajax;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.json.JSONObject;

import com.yuwang.pinju.core.util.CharsetUtil;

public class AjaxUtils {

	private AjaxUtils()	{}

	public static InputStream getInputStream(Object obj) {
		byte[] bys = CharsetUtil.toBytes(new JSONObject(obj).toString());
		return new ByteArrayInputStream(bys);
	}
}
