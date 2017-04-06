package com.yuwang.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class SearchUtil {

	/**
	 * 由于solr不支持特殊字符必须对字符串进行过滤
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 * @return String
	 */
	public static String stringFilter(String str) {
		// 清除掉所有特殊字符
		String regEx = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(str);
		String regStr = "";
		if (!matcher.find()) {
			regStr = matcher.replaceAll("");
		}
		return regStr;
	}

}
