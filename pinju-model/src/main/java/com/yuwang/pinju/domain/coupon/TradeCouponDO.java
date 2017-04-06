package com.yuwang.pinju.domain.coupon;

import java.util.Date;

import com.yuwang.pinju.common.MoneyUtil;

public class TradeCouponDO implements java.io.Serializable {

	private static final long serialVersionUID = -1507790799704871850L;

	/**
	 * 优惠券使用状态--已使用
	 */
	public static final Integer COUPON_USED = 20;
	
	/**
	 * 优惠券使用状态--未使用
	 */
	public static final Integer COUPON_NOT_USED = 10;
	
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
    private Integer useStatus;

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
     * 优惠券金额   显示用
     */
    private String couponMoneyByYuan;

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

    /**
     * 使用条件(订单满多少钱可以用，0为不限制)
     */
    private Long useCondition;
    
    /**
     * 使用条件(订单满多少钱可以用，0为不限制)  显示
     */
    private String useConditionByYuan;
    
	public Long getId(){
        return id;
    }

    public Long getCouponBatchId(){
        return couponBatchId;
    }

    public Long getOrderId(){
        return orderId;
    }

    public Integer getUseStatus(){
        return useStatus;
    }

    public Integer getCouponStatus(){
        return couponStatus;
    }

    public Long getBuyerId(){
        return buyerId;
    }

    public String getBuyerNick(){
        return buyerNick;
    }

    public Date getInvalidDate(){
        return invalidDate;
    }

    public Date getCouponCreateDate(){
        return couponCreateDate;
    }

    public Date getCouponModifyDate(){
        return couponModifyDate;
    }

    public String getCouponAttributes(){
        return couponAttributes;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModify(){
        return gmtModify;
    }

    public Long getCouponMoney(){
        return couponMoney;
    }

    public Long getSellerId(){
        return sellerId;
    }

    public String getSellerNick(){
        return sellerNick;
    }

    public Long getShopId(){
        return shopId;
    }

    public String getShopName(){
        return shopName;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setCouponBatchId(Long couponBatchId){
        this.couponBatchId = couponBatchId;
    }

    public void setOrderId(Long orderId){
        this.orderId = orderId;
    }

    public void setUseStatus(Integer useStatus){
        this.useStatus = useStatus;
    }

    public void setCouponStatus(Integer couponStatus){
        this.couponStatus = couponStatus;
    }

    public void setBuyerId(Long buyerId){
        this.buyerId = buyerId;
    }

    public void setBuyerNick(String buyerNick){
        this.buyerNick = buyerNick;
    }

    public void setInvalidDate(Date invalidDate){
        this.invalidDate = invalidDate;
    }

    public void setCouponCreateDate(Date couponCreateDate){
        this.couponCreateDate = couponCreateDate;
    }

    public void setCouponModifyDate(Date couponModifyDate){
        this.couponModifyDate = couponModifyDate;
    }

    public void setCouponAttributes(String couponAttributes){
        this.couponAttributes = couponAttributes;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModify(Date gmtModify){
        this.gmtModify = gmtModify;
    }

    public void setCouponMoney(Long couponMoney){
        this.couponMoney = couponMoney;
    }

    public void setSellerId(Long sellerId){
        this.sellerId = sellerId;
    }

    public void setSellerNick(String sellerNick){
        this.sellerNick = sellerNick;
    }

    public void setShopId(Long shopId){
        this.shopId = shopId;
    }

    public void setShopName(String shopName){
        this.shopName = shopName;
    }

	public String getCouponMoneyByYuan() {
		return MoneyUtil.getCentToDollar(couponMoney);
	}

    public Long getUseCondition() {
		return useCondition;
	}

	public void setUseCondition(Long useCondition) {
		this.useCondition = useCondition;
	}

	public String getUseConditionByYuan() {
		return MoneyUtil.getCentToDollar(useCondition);
	}
	
	
}