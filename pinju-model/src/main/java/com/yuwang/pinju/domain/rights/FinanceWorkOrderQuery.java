package com.yuwang.pinju.domain.rights;

import com.yuwang.pinju.domain.Paginator;

public class FinanceWorkOrderQuery extends Paginator{

	private static final long serialVersionUID = 1L;
	
	private String buyerNick;
	
	private String sellerNick;
	
	private int bizType;
	
	private long bizId;
	
	private long orderId;

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public long getBizId() {
		return bizId;
	}

	public void setBizId(long bizId) {
		this.bizId = bizId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
}
