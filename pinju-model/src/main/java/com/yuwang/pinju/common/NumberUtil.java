package com.yuwang.pinju.common;

import org.springframework.util.Assert;

/**
 * <p>数字操作工具类</p>
 *
 * @author gaobaowen
 * @since 2011-12-3 10:38:40
 */
public class NumberUtil {

	private NumberUtil() {
	}

	/**
	 * <p>将字符串格式的 long 类型数值解析为 long 类型。</p>
	 *
	 * @param str 需要解析的字符串
	 * @param defaultValue 解析错误时使用的默认值
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 10:39:08
	 */
	public static long parseLong(String str, long defaultValue) {
		if (str == null || str.length() == 0) {
			return defaultValue;
		}
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * <p>将字符串格式的 int 类型数值解析为 int 类型。</p>
	 *
	 * @param str 需要解析的字符串
	 * @param defaultValue 解析错误时使用的默认值
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-12-3 10:39:08
	 */
	public static int parseInt(String str, int defaultValue) {
		if (str == null || str.length() == 0) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * <p>获取 int 数据值，对象为 null 时计为 0</p>
	 *
	 * @param num
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static int getInt(Number num) {
		return (num == null) ? 0 : num.intValue();
	}
	
	/**
	 * <p>获取 long 数据值，对象为 null 时计为 0</p>
	 *
	 * @param num
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static long getLong(Number num) {
		return (num == null) ? 0L : num.longValue();
	}
	
	/**
	 * <p>获取 long 数据值，对象为 null 时计为 0</p>
	 *
	 * @param num
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static double getDouble(Number num) {
		return (num == null) ? 0 : num.doubleValue();
	}
	
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    public static boolean isInteger(String value) {
    	 try {
             Integer.parseInt(value);
         } catch(NumberFormatException e) {
             return false;
         }
         return true;
    }
    
    public static Boolean isBetweenEquals(final Long mid , final Long min , final Long max) {
        Assert.notNull(mid);
        Assert.notNull(min);
        if (max == null || max == new Long(0)) {
            return isGreateThenEquals(mid , min);
        }
        return max >= mid && mid >= min;
    }
    public static Boolean isGreateThenEquals(final Long max , final Long min) {
        return max >= min;
    }
}
