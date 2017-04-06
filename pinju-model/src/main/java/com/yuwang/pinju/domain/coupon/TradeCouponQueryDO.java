package com.yuwang.pinju.domain.coupon;

import java.util.Date;

import com.yuwang.pinju.domain.Paginator;

public class TradeCouponQueryDO extends Paginator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 优惠券id
	 */
	private Long id;

	/**
	 * 批次表id
	 */
	private Long couponBatchId;

	/**
	 * 主订单id
	 */
	private Long orderId;

	/**
	 * 优惠券使用状态(10未使用，20已使用)
	 */
	private Long useStatus;

	/**
	 * 优惠券状态(10正常，20已失效或过期，40关闭)
	 */
	private Integer couponStatus;

	/**
	 * 买家id
	 */
	private Long buyerId;

	/**
	 * 买家nick
	 */
	private String buyerNick;

	/**
	 * 到期时间
	 */
	private Date invalidDate;

	/**
	 * 优惠券领取时间
	 */
	private Date couponCreateDate;

	/**
	 * 优惠券修改时间
	 */
	private Date couponModifyDate;

	/**
	 * 预留字段 key\:value
	 */
	private String couponAttributes;

	/**
	 * 数据生成时间
	 */
	private Date gmtCreate;

	/**
	 * 数据修改时间
	 */
	private Date gmtModify;

	/**
	 * 优惠券金额
	 */
	private Long couponMoney;

	/**
	 * 卖家id
	 */
	private Long sellerId;

	/**
	 * 卖家nick
	 */
	private String sellerNick;

	/**
	 * 店铺id
	 */
	private Long shopId;

	/**
	 * 店铺name
	 */
	private String shopName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCouponBatchId() {
		return couponBatchId;
	}

	public void setCouponBatchId(Long couponBatchId) {
		this.couponBatchId = couponBatchId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Long useStatus) {
		this.useStatus = useStatus;
	}

	public Integer getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(Integer couponStatus) {
		this.couponStatus = couponStatus;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public Date getCouponCreateDate() {
		return couponCreateDate;
	}

	public void setCouponCreateDate(Date couponCreateDate) {
		this.couponCreateDate = couponCreateDate;
	}

	public Date getCouponModifyDate() {
		return couponModifyDate;
	}

	public void setCouponModifyDate(Date couponModifyDate) {
		this.couponModifyDate = couponModifyDate;
	}

	public String getCouponAttributes() {
		return couponAttributes;
	}

	public void setCouponAttributes(String couponAttributes) {
		this.couponAttributes = couponAttributes;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Long getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(Long couponMoney) {
		this.couponMoney = couponMoney;
	}

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

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}
