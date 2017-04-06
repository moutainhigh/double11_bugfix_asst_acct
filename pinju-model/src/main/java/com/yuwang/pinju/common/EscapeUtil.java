package com.yuwang.pinju.common;

import org.apache.commons.lang.StringEscapeUtils;

public class EscapeUtil {

	private EscapeUtil() {
	}

	public static String escapeHtml(String html) {
		if (html == null || html.length() == 0) {
			return html;
		}
		char[] chs = html.toCharArray();
		StringBuilder builder = new StringBuilder(chs.length * 2);
		for (char c : chs) {
			escape(c, builder);
		}
		return builder.toString();
	}
	
	public static String unescapeHtml(String escapedHtml) {
		if (escapedHtml == null || escapedHtml.length() == 0) {
			return escapedHtml;
		}
		return StringEscapeUtils.unescapeHtml(escapedHtml);
	}

	private static void escape(char c, StringBuilder builder) {
		switch (c) {
		case '\"':
			builder.append("&quot;");
			break;
		case '<':
			builder.append("&lt;");
			break;
		case '>':
			builder.append("&gt;");
			break;
		case '&':
			builder.append("&amp;");
			break;
		default:
			builder.append(c);
			break;
		}
	}

	public static void main(String[] args) {
		System.out.println(escapeHtml("<input type=\"text\">"));
		System.out.println(unescapeHtml("&lt;i&nbsp;nput type=&quot;text&quot;&gt;"));
	}
}
