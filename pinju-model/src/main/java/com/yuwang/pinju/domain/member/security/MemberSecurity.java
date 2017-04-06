package com.yuwang.pinju.domain.member.security;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.common.RandomUUID;

/**
 * <p>会员安全工具类</p>
 *
 * @author gaobaowen
 * @since 2011-12-8 11:16:29
 */
public class MemberSecurity {

	/**
	 * 消息编号长度
	 */
	private final static int MESSAGE_ID_LENGTH = 36;

	/**
	 * TOKEN 编号长度
	 */
	private final static int TOKEN_LENGTH = 36;

	private final static Pattern TOKEN_PATTERN = Pattern.compile("[1-9a-zA-Z]{" + TOKEN_LENGTH + "}");

	private final static Pattern MESSAGE_ID_PATTERN = Pattern.compile("[1-9a-zA-Z]{" + MESSAGE_ID_LENGTH + "}");

	private MemberSecurity(){}

	/**
	 * <p>随机生成一个消息编号</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-8 11:15:00
	 */
	public static String generateMessageId() {
		return RandomUUID.get(MESSAGE_ID_LENGTH);
	}

	/**
	 * <p>随机生成一个消息 TOKEN</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-8 11:15:16
	 */
	public static String generateMessageToken() {
		return RandomUUID.get(TOKEN_LENGTH);
	}

	/**
	 * <p>判断是否是消息 TOKEN的数据</p>
	 *
	 * @param token
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-8 11:15:31
	 */
	public static boolean isMessageToken(String messageToken) {
		if (StringUtils.isBlank(messageToken)) {
			return false;
		}
		if (messageToken.length() != TOKEN_LENGTH) {
			return false;
		}
		return TOKEN_PATTERN.matcher(messageToken).matches();
	}

	/**
	 * <p>判断是否是消息编号的数据</p>
	 *
	 * @param token
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-8 11:15:31
	 */
	public static boolean isMessageId(String messageId) {
		if (StringUtils.isBlank(messageId)) {
			return false;
		}
		if (messageId.length() != MESSAGE_ID_LENGTH) {
			return false;
		}
		return MESSAGE_ID_PATTERN.matcher(messageId).matches();
	}

	public static String hash(String salt, String... args) {
		long base = 0;
		for (int i = 0; i < args.length; i++) {
			char[] chs = args[i].toCharArray();
			for (int j = 0; j < chs.length; j++) {
				base = base * 31 + chs[j] * salt.hashCode();
			}
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			long s = base;
			char[] chs = args[i].toCharArray();
			for (int j = 0; j < chs.length; j++) {
				s = s * 31 + chs[j];
			}
			builder.append(Base61.toBase61(s & 0xffffffffL, 6));
		}
		return builder.toString();
	}

	public static String encodeMemberId(long n) {
		return Base61.encodeMemberId(n);
	}

	public static long base61ToLong(String str) {
		return Base61.base61ToLong(str);
	}

	public static String toBase61(Long n, int len) {
		return Base61.toBase61(n, len);
	}

	public static long decode(String encodeMemberId) {
		return Base61.decode(encodeMemberId);
	}

	/**
	 * <p>对 Memeber ID 进行 Cookie 编码解码的工具</p>
	 *
	 * @author gaobaowen
	 * 2011-6-18 上午10:18:13
	 */
	private static class Base61 {

		/**
		 * 乱序的 Base61 字符
		 */
	    private final static char[] BASE61 = "gcLAmWGjedwzpNDF45funCJlYyqRZX9rVhEsP1SIMxTvoOiU73tBa2HKk6Q8b".toCharArray();

	    /**
	     * Base61 字符值映射
	     */
	    private final static int[] BASE61_MAPPING = { 3,51,21,14,34,15,6,54,39,22,55,2,40,13,45,36,58,27,38,42,47,32,5,29,24,28,52,60,1,9,8,18,0,33,46,7,56,23,4,20,44,12,26,31,35,50,19,43,10,41,25,11,37,53,49,16,17,57,48,59,30 };

	    /**
	     * 编码元素长度
	     */
	    private final static int LEN = BASE61.length;

	    /**
	     * 校验码盐
	     */
	    private final static int CHECK_SALT = 97;

	    /**
	     * 自增序列
	     */
	    private final static AtomicInteger SEQ = new AtomicInteger((int)(System.nanoTime()));

	    /**
		 * <p>将 long 数据使用 Base62 进行编码</p>
		 *
		 * @param n	大于等于 0 的数值
		 * @return
		 *
		 * @author gaobaowen
		 */
		public static String toBase61(Long n, int len) {
			if(n == null || n < 1) {
				return String.valueOf(BASE61[0]);
			}
			char[] chs = new char[11];
			int k = 0;
			while(n > 0) {
				chs[k++] = BASE61[(int)(n % LEN)];
				n /= LEN;
			}
			while (k < len) {
				chs[k++] = BASE61[0];
			}
			return new String(chs, 0, k);
		}

		/**
		 * <p>将 Base62 编码后的字符串转换成为 long 值，若存在非法的 Base62 字符时返回 -1。若超出 long 值范围，也返回 -1</p>
		 *
		 * @param str
		 * @return
		 *
		 * @author gaobaowen
		 */
		public static long base61ToLong(String str) {
			if(str == null || str.length() == 0) {
				return -1;
			}
			if(str.length() > 11) {
				return -1;
			}
			long num = 0L;
			char[] chs = str.toCharArray();
			for(int i = chs.length - 1; i >= 0; i--) {
				int v = getNumber(chs[i]);
				if(v < 0) {
					return -1;
				}
				num = num * LEN + v;
			}
			return num;
		}

	    /**
	     * <p>将 15 位的 Member ID 编码成为 13 位的 Base61 串，用于 Cookie 的写入</p>
	     *
	     * @param n    固定 15 位长度的会员编号（Member ID）
	     * @return
	     *
	     * @author gaobaowen
	     */
	    public static String encodeMemberId(long n) {
	        n = next(900) * 10000000000000000L + n * 10 + next(10);
	        char[] chs = new char[13];
	        int k = 0;
	        int c = 0;
	        while (n > 0) {
	            c = c * CHECK_SALT + (chs[k++] = BASE61[(int) (n % LEN)]);
	            n /= LEN;
	        }
	        while (k < 11) {
	            chs[k++] = BASE61[0];
	            c = c * CHECK_SALT + BASE61[0];
	        }
	        c >>>= 1;
	        chs[k++] = BASE61[c % LEN];
	        c /= LEN;
	        chs[k++] = BASE61[c % LEN];
	        return new String(chs);
	    }

	    /**
	     * <p>将编码后的会员编号解码为 15 位的 Member ID。</p>
	     *
	     * @param encodeMemberId  已编码的会员编号
	     * @return  15 位数字格式的会员编号，若编码无效则返回 -1
	     *
	     * @author gaobaowen
	     */
	    public static long decode(String encodeMemberId) {
	        if (encodeMemberId == null || encodeMemberId.length() != 13) {
	            return -1;
	        }
	        char[] chs = encodeMemberId.toCharArray();
	        long n = 0L;
	        int c = 0;
	        for (int i = chs.length - 3; i >= 0; i--) {
	            int k = getNumber(chs[i]);
	            if (k < 0) {
	                return -1;
	            }
	            n = n * LEN + k;
	            c = c * CHECK_SALT + chs[chs.length - 3 - i];
	        }
	        c >>>= 1;
	        if (chs[11] != BASE61[c % LEN]) {
	            return -1;
	        }
	        c /= LEN;
	        if (chs[12] != BASE61[c % LEN]) {
	            return -1;
	        }
	        return n % 10000000000000000L / 10;
	    }

	    private static int next(int mod) {
	        return (Integer.reverse(SEQ.incrementAndGet()) & 0x7fffffff) % mod;
	    }

	    private static int getNumber(char c) {
	        if (c >= 'A' && c <= 'Z') {
	            return BASE61_MAPPING[c - 'A'];
	        }
	        if (c >= 'a' && c <= 'z') {
	            return BASE61_MAPPING[c - 'a' + 26];
	        }
	        if (c >= '1' && c <= '9') {
	            return BASE61_MAPPING[c - '1' + 52];
	        }
	        return -1;
	    }
	}
}
