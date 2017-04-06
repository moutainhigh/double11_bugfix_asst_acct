package com.yuwang.pinju.domain.report;

import com.yuwang.pinju.domain.Paginator;

public class ReportQueryDO extends Paginator {
	
	private String stateModifyTime;
	
	private Long buyNumCount;
	
	private String orderItemPriceCount;
	
	private Long buyNumSum;
	
	private Long orderItemPriceSum;

	public String getStateModifyTime() {
		return stateModifyTime;
	}

	public void setStateModifyTime(String stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}

	public Long getBuyNumCount() {
		return buyNumCount;
	}

	public void setBuyNumCount(Long buyNumCount) {
		this.buyNumCount = buyNumCount;
	}

	public String getOrderItemPriceCount() {
		return orderItemPriceCount;
	}

	public void setOrderItemPriceCount(String orderItemPriceCount) {
		this.orderItemPriceCount = orderItemPriceCount;
	}

	public Long getBuyNumSum() {
		return buyNumSum;
	}

	public void setBuyNumSum(Long buyNumSum) {
		this.buyNumSum = buyNumSum;
	}

	public Long getOrderItemPriceSum() {
		return orderItemPriceSum;
	}

	public void setOrderItemPriceSum(Long orderItemPriceSum) {
		this.orderItemPriceSum = orderItemPriceSum;
	}
	
}
