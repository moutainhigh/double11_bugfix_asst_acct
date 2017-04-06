package com.yuwang.pinju.core.util;

import org.springframework.util.Assert;

/*
 * @auth James
 */
public class NumberUtil {
	
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
