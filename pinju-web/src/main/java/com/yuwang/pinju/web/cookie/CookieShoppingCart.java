package com.yuwang.pinju.web.cookie;

/**
 * <p>
 * 存放于 Cookie 中的购物车信息
 * </p>
 *
 * @author shihongbo
 */

public class CookieShoppingCart {

	//商品id
	private String itemId;
	
	//商品数量
	private String itemCount;
	
	//商品价格
	private String itemPrice;
	
	//skuid
	private String skuid;
	
	//skudesc
	private String skuDesc;
	
	//分销商id
	private transient String channelId;

	//广告id
	private transient String ad;

	private String time;
	

	public CookieShoppingCart(){}
	
	public CookieShoppingCart(String itemId, String itemCount, String itemPrice, String skuid, String skuDesc, String channelId, String ad, String time) {
		super();
		this.itemId = itemId;
		this.itemCount = itemCount;
		this.itemPrice = itemPrice;
		this.skuid = skuid;
		this.skuDesc = skuDesc;
		this.channelId = channelId;
		this.ad = ad;
		this.time = time;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getSkuDesc() {
		return skuDesc;
	}

	public void setSkuDesc(String skuDesc) {
		this.skuDesc = skuDesc;
	}
	
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

}
