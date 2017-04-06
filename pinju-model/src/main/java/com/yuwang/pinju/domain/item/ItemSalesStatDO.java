package com.yuwang.pinju.domain.item;

import java.io.Serializable;

public class ItemSalesStatDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long itemId;
	private Long buyNum;
	private Long orderItemPrice;
	private Long orderCount;
	
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}
	public Long getOrderItemPrice() {
		return orderItemPrice;
	}
	public void setOrderItemPrice(Long orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}
	public Long getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}
}
