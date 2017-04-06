package com.yuwang.pinju.core.util;

/**
 * 超时剩余时间
 * 
 * 比如买家申请退款，提示卖家您还有"2天1小时3分3秒"来处理请求，超时默认同意。
 * 
 * @author shihongbo
 * @since 2011-6-28
 * @version 1.0
 */

public class LeftDate {
	private Long day;
	private Long hour;
	private Long minute;
	private Long second;
	
	public LeftDate(){}
	
	public LeftDate(Long day, Long hour, Long minute, Long second) {
		super();
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	public Long getDay() {
		return day;
	}
	public void setDay(Long day) {
		this.day = day;
	}
	public Long getHour() {
		return hour;
	}
	public void setHour(Long hour) {
		this.hour = hour;
	}
	public Long getMinute() {
		return minute;
	}
	public void setMinute(Long minute) {
		this.minute = minute;
	}
	public Long getSecond() {
		return second;
	}
	public void setSecond(Long second) {
		this.second = second;
	}

	@Override
	public String toString() {
		return "LeftDate [day=" + day + ", hour=" + hour + ", minute=" + minute
				+ ", second=" + second + "]";
	}

	
	
}
