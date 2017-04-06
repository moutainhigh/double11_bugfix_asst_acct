package com.yuwang.pinju.domain.active;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.xwork.builder.ToStringBuilder;
import org.apache.commons.lang.xwork.builder.ToStringStyle;

import com.yuwang.pinju.domain.Paginator;

public class ActivityDiscountQuery extends Paginator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7724176890717750763L;
	/**
	 * 限时活动开始时间
	 */
	private Date startTime;
	/**
	 * 限时活动结束时间
	 */
	private Date endTime;
	/**
	 * 判断时间点
	 */
	private Date checkTime = new Date();
	/**
	 * 卖家昵称
	 */
	private String memberNick;
	/**
	 * 卖家ID
	 */
	private Long memberId;
	/**
	 * 限时活动状态： 0-未开始、1-进行中、2-已结束、3-已删除
	 */
	private Integer status;

	public ActivityDiscountQuery() {

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

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
