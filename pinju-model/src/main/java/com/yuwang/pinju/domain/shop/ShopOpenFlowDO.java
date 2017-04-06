package com.yuwang.pinju.domain.shop;

import java.io.Serializable;
import java.util.Date;

public class ShopOpenFlowDO implements Serializable {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 5020792650629612360L;

	/**
	 * 自增长id
	 */
	private Integer id;
	
	/**
	 * 店铺id
	 */
	private Integer shopId;

	public Integer getId() {
		return id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getSellerType() {
		return sellerType;
	}

	public void setSellerType(Integer sellerType) {
		this.sellerType = sellerType;
	}

	public Integer getIsAgreement() {
		return isAgreement;
	}

	public void setIsAgreement(Integer isAgreement) {
		this.isAgreement = isAgreement;
	}

	public Integer getIsFillInfo() {
		return isFillInfo;
	}

	public void setIsFillInfo(Integer isFillInfo) {
		this.isFillInfo = isFillInfo;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getIsKa() {
		return isKa;
	}

	public void setIsKa(Integer isKa) {
		this.isKa = isKa;
	}

	public Integer getIsBlack() {
		return isBlack;
	}

	public void setIsBlack(Integer isBlack) {
		this.isBlack = isBlack;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getAuditCount() {
		return auditCount;
	}

	public void setAuditCount(Integer auditCount) {
		this.auditCount = auditCount;
	}

	public String getAuditProgress() {
		return auditProgress;
	}

	public void setAuditProgress(String auditProgress) {
		this.auditProgress = auditProgress;
	}

	public Integer getIsOnlineAuditEnd() {
		return isOnlineAuditEnd;
	}

	public void setIsOnlineAuditEnd(Integer isOnlineAuditEnd) {
		this.isOnlineAuditEnd = isOnlineAuditEnd;
	}

	public Integer getIsPostalAuditEnd() {
		return isPostalAuditEnd;
	}

	public void setIsPostalAuditEnd(Integer isPostalAuditEnd) {
		this.isPostalAuditEnd = isPostalAuditEnd;
	}

	public Integer getIsSampleAuditEnd() {
		return isSampleAuditEnd;
	}

	public void setIsSampleAuditEnd(Integer isSampleAuditEnd) {
		this.isSampleAuditEnd = isSampleAuditEnd;
	}

	public String getNoPassReason() {
		return noPassReason;
	}

	public void setNoPassReason(String noPassReason) {
		this.noPassReason = noPassReason;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	/**
	 * 用户id
	 */
	private long userId;

	/**
	 * 开店类型 B/C
	 */
	private Integer sellerType;

	/**
	 * 是否同意了服务协议
	 */
	private Integer isAgreement;

	/**
	 * 是否填写了店铺信息
	 */
	private Integer isFillInfo;

	/**
	 * 审核状态
	 */
	private Integer auditStatus;

	/**
	 * 是否是ka
	 */
	private Integer isKa;

	/**
	 * 是否被拉黑
	 */
	private Integer isBlack;

	/**
	 * 审核时间
	 */
	private Date auditDate;

	/**
	 * 申请次数
	 */
	private Integer auditCount;

	/**
	 * 申请进度
	 */
	private String auditProgress;

	/**
	 * 在线信息是否审核完毕
	 */
	private Integer isOnlineAuditEnd;

	/**
	 * 是否邮寄信息审核完毕
	 */
	private Integer isPostalAuditEnd;

	/**
	 * 是否样品审核完毕
	 */
	private Integer isSampleAuditEnd;

	private String noPassReason;

	/**
	 * 配置参数properties形式存储
	 */
	private String configuration;

	/**
	 * 开店流程创建时间
	 */
	private Date gmtCreate;

	/**
	 * 开店流程修改时间
	 */
	private Date gmtModified;

	private String reviewer;

}
