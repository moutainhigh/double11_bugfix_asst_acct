package com.yuwang.pinju.core.util;

public abstract class EmailUtil {

	/**
	 * 产生带*号的邮箱
	 * @param email 邮件地址
	 * @return
	 */
	public static String getSectionEmail(String email) {
		if (StringUtil.isEmpty(email)) {
			return email;
		}
		char[] charArray = email.toCharArray();
		StringBuffer buffer  = new StringBuffer();
		int index = email.indexOf("@");
		for (int i = 0; i < charArray.length; i++) {
			if (i == 0 && index == 1) {
				buffer.append("*");
			} else {
				if (i >= 1 && i < index) {
					buffer.append("*");
				} else {
					buffer.append(charArray[i]);
				}
			}
		}
		return buffer.toString();
	}
}
