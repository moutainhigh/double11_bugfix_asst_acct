package com.yuwang.pinju.domain.bi;

import com.yuwang.pinju.common.MoneyUtil;

/**
 * @see 店铺销售商品排行实体
 * @author 杜成
 * @date 2011-6-20
 * @version 1.0
 * @Update: 2011-11-10 By MaYuanChao
 */
public class ShopSalesRankDO implements java.io.Serializable {
	
	private static final long serialVersionUID = -2536540992362157386L;

	/**
	 * 主键
	 */
	private Long shopSalesRankId;
	/**
	 * 卖家会员编号
	 */
	private Long memberId;
	/**
	 * 店铺编号
	 */
	private Integer shopId;
	/**
	 * 店铺商品分类编号
	 */
	private Integer categoryId;
	/**
	 * 店铺子类商品分类编号
	 */
	private Integer cateparentId;
	/**
	 * 商品编号
	 */
	private Long itemId;
	/**
	 * 商品名称
	 */
	private String itemName;
	/**
	 * 商品价格
	 */
	private Long itemPrice;
	/**
	 * 商品图片地址
	 */
	private String itemPicUrl;
	/**
	 * 商品销售总数
	 */
	private Integer itemSaleCount;
		
	/**
	 * 排行榜类型
	 */
	private Integer rankType;
	
	/**
	 * 商品价格：单位：元
	 */
	private String priceByYuan;

	
	public String getPriceByYuan() {
		return priceByYuan;
	}

	public void setPriceByYuan(String priceByYuan) {
		this.priceByYuan = priceByYuan;
	}

	public Long getShopSalesRankId() {
		return shopSalesRankId;
	}

	public void setShopSalesRankId(Long shopSalesRankId) {
		this.shopSalesRankId = shopSalesRankId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCateparentId() {
		return cateparentId;
	}

	public void setCateparentId(Integer cateparentId) {
		this.cateparentId = cateparentId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
		this.priceByYuan = MoneyUtil.getCentToDollar(itemPrice);
	}

	public String getItemPicUrl() {
		return itemPicUrl;
	}

	public void setItemPicUrl(String itemPicUrl) {
		this.itemPicUrl = itemPicUrl;
	}

	public Integer getItemSaleCount() {
		return itemSaleCount;
	}

	public void setItemSaleCount(Integer itemSaleCount) {
		this.itemSaleCount = itemSaleCount;
	}

	public Integer getRankType() {
		return rankType;
	}

	public void setRankType(Integer rankType) {
		this.rankType = rankType;
	}
}
