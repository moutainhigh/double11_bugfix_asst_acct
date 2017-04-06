package com.yuwang.pinju.domain.coupon;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.domain.Paginator;

public class TradeCouponBatchVO extends Paginator implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 主键  优惠券批次id
     */
    private Long id;
    
    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺name
     */
    private String shopName;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 卖家name
     */
    private String sellerNick;

    /**
     * 优惠券金额
     */
    private Long couponMoney;
    
    /**
     * 优惠券金额   显示用
     */
    private String couponMoneyByYuan;

    /**
     * 发行总量
     */
    private Long releaseCount;

    /**
     * 发放类型(10、买家领取)
     */
    private Integer releaseType;

    /**
     * 每人限领数量，0为不限制
     */
    private Integer restrictAmount;

    /**
     * 使用条件(订单满多少钱可以用，0为不限制)
     */
    private Long useCondition;

    /**
     * 是否显示推广文案(10显示，20不显示)
     */
    private Integer spreadShow;

    /**
     * 优惠券批次状态(10正常，20优惠券领取失效，30优惠券失效，40关闭)
     */
    private Integer batchStatus;

    /**
     * 皮肤编号
     */
    private String skinNum;

    /**
     * 优惠券链接
     */
    private String couponLink;

    /**
     * 优惠券代码
     */
    private String couponCode;

    /**
     * 操作人id
     */
    private Long operatorId;

    /**
     * 操作人name
     */
    private String operatorName;

    /**
     * 优惠券创建时间
     */
    private Date couponCreateDate;

    /**
     * 优惠券修改时间
     */
    private Date couponModifyDate;

    /**
     * 到期时间
     */
    private Date couponInvalidDate;

    /**
     * 剩余数量
     */
    private Long surplusAmount;

    /**
     * 乐观锁版本号
     */
    private Long version;

    /**
     * 预留字段 key\:value
     */
    private String couponAttributes;

    /**
     * 生成时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;
    
    
    /**
     * 未使用数量 
     */
    private Long noUsedCount;
    
    /**
     * 已使用数量
     */
    private Long usedCount;
    
    /**
     * 已领取数量
     */
    private Long hasReceivedCount;



    public Long getHasReceivedCount() {
		return hasReceivedCount;
	}

	public void setHasReceivedCount(Long hasReceivedCount) {
		this.hasReceivedCount = hasReceivedCount;
	}

	public Long getShopId(){
        return shopId;
    }

    public String getShopName(){
        return shopName;
    }

    public Long getSellerId(){
        return sellerId;
    }

    public String getSellerNick(){
        return sellerNick;
    }

    public Long getCouponMoney(){
        return couponMoney;
    }

    public Long getReleaseCount(){
        return releaseCount;
    }

    public Integer getReleaseType(){
        return releaseType;
    }

    public Integer getRestrictAmount(){
        return restrictAmount;
    }

    public Long getUseCondition(){
        return useCondition;
    }

    public Integer getSpreadShow(){
        return spreadShow;
    }

    public Integer getBatchStatus(){
        return batchStatus;
    }

    public String getCouponLink(){
        return new String(Base64.decodeBase64(couponLink));
    }

    public String getCouponCode(){
        return new String(Base64.decodeBase64(couponCode));
    }

    public Long getOperatorId(){
        return operatorId;
    }

    public String getOperatorName(){
        return operatorName;
    }

    public Date getCouponCreateDate(){
        return couponCreateDate;
    }

    public Date getCouponModifyDate(){
        return couponModifyDate;
    }

    public Date getCouponInvalidDate(){
        return couponInvalidDate;
    }

    public Long getSurplusAmount(){
        return surplusAmount;
    }

    public Long getVersion(){
        return version;
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

    public void setShopId(Long shopId){
        this.shopId = shopId;
    }

    public void setShopName(String shopName){
        this.shopName = shopName;
    }

    public void setSellerId(Long sellerId){
        this.sellerId = sellerId;
    }

    public void setSellerNick(String sellerNick){
        this.sellerNick = sellerNick;
    }

    public void setCouponMoney(Long couponMoney){
        this.couponMoney = couponMoney;
    }

    public void setReleaseCount(Long releaseCount){
        this.releaseCount = releaseCount;
    }

    public void setReleaseType(Integer releaseType){
        this.releaseType = releaseType;
    }

    public void setRestrictAmount(Integer restrictAmount){
        this.restrictAmount = restrictAmount;
    }

    public void setUseCondition(Long useCondition){
        this.useCondition = useCondition;
    }

    public void setSpreadShow(Integer spreadShow){
        this.spreadShow = spreadShow;
    }

    public void setBatchStatus(Integer batchStatus){
        this.batchStatus = batchStatus;
    }
    
    public void setCouponLink(String couponLink){
        this.couponLink = new String(Base64.encodeBase64(couponLink.getBytes()));
    }

    public void setCouponCode(String couponCode){
        this.couponCode = new String(Base64.encodeBase64(couponCode.getBytes()));
    }

    public void setOperatorId(Long operatorId){
        this.operatorId = operatorId;
    }

    public void setOperatorName(String operatorName){
        this.operatorName = operatorName;
    }

    public void setCouponCreateDate(Date couponCreateDate){
        this.couponCreateDate = couponCreateDate;
    }

    public void setCouponModifyDate(Date couponModifyDate){
        this.couponModifyDate = couponModifyDate;
    }

    public void setCouponInvalidDate(Date couponInvalidDate){
        this.couponInvalidDate = couponInvalidDate;
    }

    public void setSurplusAmount(Long surplusAmount){
        this.surplusAmount = surplusAmount;
    }

    public void setVersion(Long version){
        this.version = version;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNoUsedCount() {
		return noUsedCount;
	}

	public void setNoUsedCount(Long noUsedCount) {
		this.noUsedCount = noUsedCount;
	}

	public Long getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Long usedCount) {
		this.usedCount = usedCount;
	}

	public String getCouponMoneyByYuan() {
		return MoneyUtil.getCentToDollar(couponMoney);
	}

	public String getSkinNum() {
		return skinNum;
	}

	public void setSkinNum(String skinNum) {
		this.skinNum = skinNum;
	}
	
	
	
}

