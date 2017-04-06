package com.yuwang.pinju.domain.order.query;

public class SellerVO implements Comparable<SellerVO>{
	private Long sellerId;
	
	private String sellerNick;
	
	
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerNick() {
		return sellerNick;
	}
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sellerId == null) ? 0 : sellerId.hashCode());
		result = prime * result
				+ ((sellerNick == null) ? 0 : sellerNick.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SellerVO other = (SellerVO) obj;
		if (sellerId == null) {
			if (other.sellerId != null)
				return false;
		} else if (!sellerId.equals(other.sellerId))
			return false;
		if (sellerNick == null) {
			if (other.sellerNick != null)
				return false;
		} else if (!sellerNick.equals(other.sellerNick))
			return false;
		return true;
	}

	public int compareTo(SellerVO sellerVO){
		if(this.equals(sellerVO))
			return 0;
		
		return this.sellerId.compareTo(sellerVO.getSellerId());
	}
}
