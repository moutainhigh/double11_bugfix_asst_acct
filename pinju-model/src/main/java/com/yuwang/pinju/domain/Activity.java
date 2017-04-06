package com.yuwang.pinju.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.xwork.builder.ToStringBuilder;
import org.apache.commons.lang.xwork.builder.ToStringStyle;

public class Activity implements Comparable<Activity>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final Integer ACTIVITY_TYPE_DISCOUNT = 1;
	
	public static final Integer ACTIVITY_TYPE_COUPON = 2;

	private Long activityId;

	private Integer type;

	private String title;

	private Date startTime;

	private Date endTime;

	public Activity() {
		super();
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public int compareTo(Activity activity) {
		return this.startTime.compareTo(activity.startTime);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
