package com.yuwang.pinju.domain.search;

import java.util.List;

/**
 * 店铺内商品搜索
 * 
 * @author liming
 * @since 2011-09-19
 */
public class SearchShopItem extends SearchBaseDO {

	private static final long serialVersionUID = -6594801919605440001L;
	
	/**
	 * 卖家编号
	 */
	private Long sellerId;

	/**
	 * 店铺分类
	 */
	private String shopCategory;

	/**
	 * 状态列表
	 */
	private List status;

	/**
	 * 最小价格
	 */
	private Double MinPrice;
	
	/**
	 * 最大价格
	 */
	private Double MaxPrice;
	
	public SearchShopItem() {
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}

	public List getStatus() {
		return status;
	}

	public void setStatus(List status) {
		this.status = status;
	}

	public Double getMinPrice() {
		return MinPrice;
	}

	public void setMinPrice(Double minPrice) {
		MinPrice = minPrice;
	}

	public Double getMaxPrice() {
		return MaxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		MaxPrice = maxPrice;
	}

}
