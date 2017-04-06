package com.yuwang.pinju.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * <p>日期相关工具</p>
 *
 * @author gaobaowen
 * 2011-6-14 下午03:26:55
 */
public class DateUtil {

	private final static long MILLISECONDS_EVERY_DAY = 24L * 3600 * 1000;

	private DateUtil() {}

	/**
	 * <p>当前日期时间</p>
	 *
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static Date current() {
		return new Date();
	}

	public static String getCurrentYYYYMMDD() {
		return format("yyyyMMdd", current());
	}

	/**
	 * <p>判断指定日期是否在有效期之内。</p>
	 *
	 * <p>若指定时间是：2011-06-01 09:12:00，有期效为 10 天。当前时间在 2011-06-11 09:12:00 之后则表示已经不在有效期内。</p>
	 *
	 * @param date   指定时间
	 * @param period  有效期（天）
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static boolean isIndate(Date date, int period) {
		return date.getTime() + MILLISECONDS_EVERY_DAY * period <= System.currentTimeMillis();
	}

	/**
	 * <p>增加日期数</p>
	 */
	public static Date incrementDay(Date date, int increment) {
		if(date == null) {
			return null;
		}
		if(increment < 1) {
			return date;
		}
		return changeDay(date, increment);
	}

	/**
	 * <p>减少日期数</p>
	 */
	public static Date decrementDay(Date date, int decrement) {
		if(date == null) {
			return null;
		}
		if(decrement < 1) {
			return date;
		}
		return changeDay(date, -decrement);
	}

	private static Date changeDay(Date date, int change) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, change);
		return c.getTime();
	}

	/**
	 * <p>以 datetime 格式化日期</p>
	 *
	 * @param date
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static String formatDatetime(Date date) {
		if(date == null) {
			return null;
		}
		return format("yyyy-MM-dd HH:mm:ss", date);
	}

	/**
	 * <p>以 date 格式化日期</p>
	 *
	 * @param date
	 * @return
	 *
	 * @author gaobaowen
	 */
	public static String formatDate(Date date) {
		if(date == null) {
			return null;
		}
		return format("yyyy-MM-dd", date);
	}
	/**
	 * <p>以 date 格式化月份</p>
	 * <p>可强制转换为数字类型,存入数据库</p>
	 * @param date
	 * @return
	 *
	 * @author liuboen
	 */
	public static String formatMonth(Date date) {
		if(date == null) {
			return null;
		}
		return format("yyyyMM", date);
	}

	public static String formatDate(String pattern, Date date) {
		if(date == null) {
			return null;
		}
		return format(pattern, date);
	}

	private static String format(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 通过字符串及patten获取date对象
	 * @param pattern
	 * @param strDateTime
	 * @return
	 */
    public static java.util.Date parse(String pattern, String strDateTime) {
        java.util.Date date = null;
        if (strDateTime == null || pattern == null) return null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            date = formatter.parse(strDateTime);
        }
        catch (ParseException e) {
            return null;
        }
        return date;
    }

    /**
     *
     * 通过毫秒返回时间字符串
     * @param longTime
     * @return
     */
    public static String formatLongToString(long longTime){
    	if (longTime<=0) {
			return null;
		}
    	Date date=new Date(longTime);
    	return format("yyyy-MM-dd HH:mm:ss", date);
    }


	public static void main(String fdf[]){
		System.out.println(DateUtil.formatMonth(new Date()));;
	}
}
