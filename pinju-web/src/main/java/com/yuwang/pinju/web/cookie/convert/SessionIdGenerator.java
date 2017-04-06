package com.yuwang.pinju.web.cookie.convert;

import com.yuwang.cookie.util.CodeUtil;
import com.yuwang.cookie.util.Tools;

public class SessionIdGenerator {

    private final static int CHECK_LENGTH = 6;
    
    private SessionIdGenerator() {
    }

    public static String getHashSessionId(String tokenId, Class<?> clazz) {
    	if (Tools.isBlank(tokenId)) {
			return tokenId;
		}
		char[] chs = tokenId.toCharArray();
		long base = 0;
		for (int i = 0; i < chs.length; i++) {
			base = base * 31 + chs[i];
		}
		StringBuilder builder = new StringBuilder(85);
		long k = 0;
		for (int i = 0; i < chs.length; i++) {
			if (i > 0 && i % 7 == 0) {
				builder.append(CodeUtil.toBase62(k & Long.MAX_VALUE));
				k = 0;
				continue;
			}
			k = k * base  + chs[i];
		}
		builder.append(CodeUtil.toBase62(k & Long.MAX_VALUE));
		builder.append(CodeUtil.toBase62(tokenId.hashCode() & Long.MAX_VALUE));
		if (clazz != null) {
			base = 0;
			char[] chv = clazz.getName().toCharArray();
			for (int i = 0; i < chv.length; i++) {
				base = base * 31 + chv[i];
			}
			builder.append(CodeUtil.toBase62(base & Integer.MAX_VALUE));
		}
		base = 0;
		for (int i = 0, x = builder.length(); i < x; i++) {
			base = base * 31 + builder.charAt(i);
		}
		builder.append(hashSessionCheckCode(base));
		return builder.toString();
    }

    public static boolean validateHashSessionId(String sessionId) {
		if (Tools.isBlank(sessionId)) {
			return false;
		}
		if (sessionId.length() < CHECK_LENGTH) {
			return false;
		}
		char[] chs = sessionId.toCharArray();
		long base = 0;
		for (int i = 0, k = chs.length - CHECK_LENGTH; i < k; i++) {
			base = base * 31 + chs[i];
		}
		char[] hash = hashSessionCheckCode(base);
		for(int i = 0, k = chs.length - CHECK_LENGTH; i < CHECK_LENGTH; i++, k++) {
			if (hash[i] != chs[k]) {
				return false;
			}
		}
		return true;
	}

	private static char[] hashSessionCheckCode(long hash) {
		hash &= Long.MAX_VALUE;
		char[] chs = new char[CHECK_LENGTH];
		for (int i = 0; i < chs.length; i++) {
			chs[i] = Token.BASE61[(int)(hash % Token.LEN)];
			hash /= Token.LEN;
		}
		return chs;
	}
}
