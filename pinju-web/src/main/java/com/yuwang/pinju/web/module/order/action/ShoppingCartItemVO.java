package com.yuwang.pinju.web.module.order.action;

public class ShoppingCartItemVO {
	
	//收藏数量
	private Integer itemCount;
	
	//收藏时的价格
	private Long priceAtFav;
	
	//是否降价
	private Boolean isPriceDown = false;
	
	public Boolean getIsPriceDown() {
		return isPriceDown;
	}

	public void setIsPriceDown(Boolean isPriceDown) {
		this.isPriceDown = isPriceDown;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public Long getPriceAtFav() {
		return priceAtFav;
	}

	public void setPriceAtFav(Long priceAtFav) {
		this.priceAtFav = priceAtFav;
	}

	
	
	
}
