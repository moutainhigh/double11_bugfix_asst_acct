package com.yuwang.pinju.domain.order.query;

import com.yuwang.pinju.domain.Paginator;

public class QueryVO  extends Paginator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8147031191915526486L;
	
	//开始时间
	private String startDate;
	
	//结束时间
	private String endDate;

	//卖家昵称
	private String sellerNick;
	
	//买家昵称
	private String buyerNick;
	
	//卖家编号
	private Long sellerId;
	
	//买家编号
	private Long buyerId;
	
	//订单状态
	private Long orderState;
	
	//退款状态
	private Integer[] refundState;
	
	//订单编号
	private Long orderId;
	
	//评价状态
	private Long isRate;
	
	//商品名称
	private String itemTitle;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public Long getOrderState() {
		return orderState;
	}

	public void setOrderState(Long orderState) {
		this.orderState = orderState;
	}

	

	public Integer[] getRefundState() {
		return refundState;
	}

	public void setRefundState(Integer[] refundState) {
		this.refundState = refundState;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getIsRate() {
		return isRate;
	}

	public void setIsRate(Long isRate) {
		this.isRate = isRate;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	
}
