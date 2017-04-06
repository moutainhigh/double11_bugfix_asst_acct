package com.yuwang.pinju.filter;

public class PropFilter {
	public static String doFilter(String str){
		if(str == null) return str;
		str = str.replaceAll("\\\\", "\\\\\\\\");
		str = str.replaceAll(" ", "\\\\ ");
		return str;
	}
}
