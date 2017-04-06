package com.yuwang.pinju.domain.active;

import java.io.Serializable;

import org.apache.commons.lang.xwork.builder.ToStringBuilder;
import org.apache.commons.lang.xwork.builder.ToStringStyle;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 活动报名描述信息
 * @author qiuhongming
 *
 */
public class ActiveDescDO extends BaseDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8647138011117955217L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 活动ID
	 */
	private Long activityId;
	/**
	 * 报名类型 1 - 商品；2 - 店铺
	 */
	private String registType;
	/**
	 * 商品URL
	 */
	private String auctionUrl;
	/**
	 * 商品标题
	 */
	private String auctionTitle;
	/**
	 * 促销图片
	 */
	private String salePic;
	/**
	 * 原价
	 */
	private String oriPrice;
	/**
	 * 促销价
	 */
	private String salePrice;
	/**
	 * 数量
	 */
	private String auctionNum;
	/**
	 * 是否包邮
	 */
	private String isIsbn;
	/**
	 * 其他信息
	 */
	private String otherInfo;
	/**
	 * 店铺名称
	 */
	private String shopTitle;
	/**
	 * 店铺图片
	 */
	private String shopPic;
	/**
	 * 店铺地址
	 */
	private String shopUrl;
	/**
	 * 店铺其他信息
	 */
	private String shopInfo;

	public ActiveDescDO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getRegistType() {
		return registType;
	}

	public void setRegistType(String registType) {
		this.registType = registType;
	}

	public String getAuctionUrl() {
		return auctionUrl;
	}

	public void setAuctionUrl(String auctionUrl) {
		this.auctionUrl = auctionUrl;
	}

	public String getAuctionTitle() {
		return auctionTitle;
	}

	public void setAuctionTitle(String auctionTitle) {
		this.auctionTitle = auctionTitle;
	}

	public String getSalePic() {
		return salePic;
	}

	public void setSalePic(String salePic) {
		this.salePic = salePic;
	}

	public String getOriPrice() {
		return oriPrice;
	}

	public void setOriPrice(String oriPrice) {
		this.oriPrice = oriPrice;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getAuctionNum() {
		return auctionNum;
	}

	public void setAuctionNum(String auctionNum) {
		this.auctionNum = auctionNum;
	}

	public String getIsIsbn() {
		return isIsbn;
	}

	public void setIsIsbn(String isIsbn) {
		this.isIsbn = isIsbn;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	public String getShopPic() {
		return shopPic;
	}

	public void setShopPic(String shopPic) {
		this.shopPic = shopPic;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(String shopInfo) {
		this.shopInfo = shopInfo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
