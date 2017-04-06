package com.yuwang.pinju.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * <p>判断空值的工具类</p>
 *
 * @author gaobaowen
 * 2011-5-30 12:48:36
 */
public class EmptyUtil {

	private EmptyUtil(){}

	/**
	 * <p>判断字符串是否为 null 或者是空值</p>
	 */
	public static boolean isBlank(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * <p>判断一组字符串中是否有 null 或者空值</p>
	 *
	 * @param arg0
	 * @param arg1
	 * @param strs2  一组字符串
	 *
	 * @return 若一组字符串中的任何一个是 null 或者空值时返回 true，否则返回 false
	 *
	 * @author gaobaowen
	 * @since 2011-9-8 10:56:28
	 */
	public static boolean isBlank(String arg0, String arg1, String... args) {
		if (isBlank(arg0) || isBlank(arg1)) {
			return true;
		}
		if (args == null) {
			return true;
		}
		for (int i = 0; i < args.length; i++) {
			if (isBlank(args[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>判断数值对象是否为空</p>
	 */
	public static boolean isBlank(Number number) {
		return (number == null);
	}

	/**
	 * <p>判断集合是否为 null 或者是空值</p>
	 */
	public static boolean isBlank(Collection<?> collection) {
		return (collection == null || collection.size() == 0);
	}

	/**
	 * <p>判断 Map 是否为 null 或者是空值</p>
	 */
	public static boolean isBlank(Map<?, ?> map) {
		return (map == null || map.size() == 0);
	}

	/**
	 * <p>判断对象数组是否为 null 或者是空</p>
	 */
	public static boolean isBlank(Object[] objs) {
		return (objs == null || objs.length == 0);
	}

	/**
	 * <p>去除字符串两边的空白字符</p>
	 *
	 * @param str 需要去除空白字符的字符串
	 * @return 参数为 null 值时返回 null，否则去除两端空白字符后返回
	 *
	 * @author gaobaowen
	 * @since 2011-8-18 10:25:15
	 */
	public static String trim(String str) {
		if (str == null) {
			return str;
		}
		return str.trim();
	}
}
