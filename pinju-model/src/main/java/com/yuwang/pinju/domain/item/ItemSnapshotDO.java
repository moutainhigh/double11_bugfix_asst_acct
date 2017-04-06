package com.yuwang.pinju.domain.item;

import java.util.Date;

/**
 * 
 * 商品快照DO
 * 
 * @author liming
 * 
 */
public class ItemSnapshotDO implements java.io.Serializable {

	private static final long serialVersionUID = 4266801800897658159L;

	private Long id;
	private Long itemId;
	private Long catetoryId;
	private Long spuId;
	private String title;
	private String storeCategories;
	private String description;
	private String propertyValuePair;
	private Integer itemType;
	private Long sellerId;
	private String picUrl;
	private Integer picColor;
	private Long price;
	private String provinces;
	private String city;
	private Long deliveryCosts;
	private Long mailCosts;
	private Long emsCosts;
	private Long freeTemplateId;
	// private String freeTemplates;
	private Date startTime;
	private Date endTime;
	private Integer itemDegree;
	private Integer recommendedStatus;
	private Long collectionNumber;
	private String features;
	private Long expiredDate;
	private Date lastModified;
	private Date gmtModified;
	private Date gmtCreate;
	private String code;
	private String sellerNick;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getCatetoryId() {
		return this.catetoryId;
	}

	public void setCatetoryId(Long catetoryId) {
		this.catetoryId = catetoryId;
	}

	public Long getSpuId() {
		return this.spuId;
	}

	public void setSpuId(Long spuId) {
		this.spuId = spuId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPropertyValuePair() {
		return this.propertyValuePair;
	}

	public void setPropertyValuePair(String propertyValuePair) {
		this.propertyValuePair = propertyValuePair;
	}

	public Integer getItemType() {
		return this.itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Long getSellerId() {
		return this.sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getPicColor() {
		return this.picColor;
	}

	public void setPicColor(Integer picColor) {
		this.picColor = picColor;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getProvinces() {
		return this.provinces;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getDeliveryCosts() {
		return this.deliveryCosts;
	}

	public void setDeliveryCosts(Long deliveryCosts) {
		this.deliveryCosts = deliveryCosts;
	}

	public Long getMailCosts() {
		return this.mailCosts;
	}

	public void setMailCosts(Long mailCosts) {
		this.mailCosts = mailCosts;
	}

	public Long getEmsCosts() {
		return this.emsCosts;
	}

	public void setEmsCosts(Long emsCosts) {
		this.emsCosts = emsCosts;
	}

	public Long getFreeTemplateId() {
		return freeTemplateId;
	}

	public void setFreeTemplateId(Long freeTemplateId) {
		this.freeTemplateId = freeTemplateId;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getItemDegree() {
		return this.itemDegree;
	}

	public void setItemDegree(Integer itemDegree) {
		this.itemDegree = itemDegree;
	}

	public Integer getRecommendedStatus() {
		return this.recommendedStatus;
	}

	public void setRecommendedStatus(Integer recommendedStatus) {
		this.recommendedStatus = recommendedStatus;
	}

	public Long getCollectionNumber() {
		return this.collectionNumber;
	}

	public void setCollectionNumber(Long collectionNumber) {
		this.collectionNumber = collectionNumber;
	}

	public String getFeatures() {
		return this.features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public Long getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Long expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStoreCategories() {
		return storeCategories;
	}

	public void setStoreCategories(String storeCategories) {
		this.storeCategories = storeCategories;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

}