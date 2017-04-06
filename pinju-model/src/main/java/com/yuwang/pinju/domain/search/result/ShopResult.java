package com.yuwang.pinju.domain.search.result;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 店铺搜索
 * 
 * @author liming
 * @since 2011-09-17
 */
public class ShopResult implements java.io.Serializable {

	private static final long serialVersionUID = 1196762773061425417L;

	/**
	 * 编号
	 */
	@Field("id")
	private Long id;

	/**
	 * 店铺名称
	 */
	@Field("name")
	private String name;
	
	/**
	 * 店铺基础名称
	 */
	@Field("baseName")
	private String baseName;

	/**
	 * 店标
	 */
	@Field("shopLogo")
	private String shopLogo;
	/**
	 * 店铺简介
	 */
	@Field("title")
	private String title;
	
	/**
	 * 店铺介绍
	 */
	@Field("description")
	private String description;
	/**
	 * 店铺类型
	 */
	@Field("sellerType")
	private String sellerType;
	/**
	 * 商品分类
	 */
	@Field("shopCategory")
	private String shopCategory;
	/**
	 * 城市
	 */
	@Field("city")
	private String city;
	/**
	 * 省份
	 */
	@Field("province")
	private String province;
	/**
	 * 消保类型
	 */
	@Field("exchangeMargin")
	private String exchangeMargin;
	/**
	 * 商品数量
	 */
	@Field("productCount")
	private Integer productCount;
	/**
	 * 买家昵称
	 */
	@Field("nickName")
	private String nickName;
	
	/**
	 * 卖家编号
	 */
	@Field("userId")
	private String userId;
	
	/**
	 * 商品数量
	 */
	@Field("itemCount")
	private Long itemCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}

	public String getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getExchangeMargin() {
		return exchangeMargin;
	}

	public void setExchangeMargin(String exchangeMargin) {
		this.exchangeMargin = exchangeMargin;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getItemCount() {
		return itemCount;
	}

	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}