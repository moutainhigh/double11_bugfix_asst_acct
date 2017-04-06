/**
 * Created on 2006-09-21
 */
package com.yuwang.pinju.core.util;

import java.util.Calendar;

import org.springframework.util.Assert;

/**
 * @author James
 *
 */
public final class MfDateTime extends MfDate {
    /**
     * 最大小时数.
     */
    public final static int maxHour = 23;
    /**
     * 最大分钟数.
     */
    public final static int maxMinute = 59;
    /**
     * 最大秒数.
     */
    public final static int maxSeconds = 59;

    /**
     * Constructor.
     * @param year Year
     * @param month Month
     * @param day Day
     * @param hour Hour
     * @param minute Minute
     * @param second Second
     */
    public MfDateTime(final int year, final int month, final int day,
            final int hour, final int minute, final int second) {
        super(year, month, day);
        Assert.notNull(hour);
        Assert.notNull(minute);
        Assert.notNull(second);
        Assert.isTrue(hour <= this.maxHour && hour >= 0, "0 <= hour <=23");
        Assert.isTrue(minute <= this.maxMinute && minute >= 0,
            "0<=minute <= 59");
        Assert.isTrue(second <= this.maxSeconds && second >= 0,
                "0<=seconds<=59");
        base.set(Calendar.HOUR_OF_DAY, hour);
        base.set(Calendar.MINUTE , minute);
        base.set(Calendar.SECOND, second);
    }

    /**
     * 得到小时.
     * @return int
     */
    public int getHour() {
        return base.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 得到分钟.
     * @return int
     */
    public int getMinute() {
        return base.get(Calendar.MINUTE);
    }

    /**
     * 得到秒数.
     * @return int
     */
    public int getSecond() {
        return base.get(Calendar.SECOND);
    }

    /**
     * 返回MfTime.
     * @return MfTime
     */
    public MfTime getMfTime() {
        return new MfTime(getHour(), getMinute());
    }

    /**
     * Compare with another MfDateTime.
     * @param date MfDateTime
     * @return int
     */
    public int compareTo(final MfDateTime date) {
        return this.getTime().compareTo(date.getTime());
    }
}
