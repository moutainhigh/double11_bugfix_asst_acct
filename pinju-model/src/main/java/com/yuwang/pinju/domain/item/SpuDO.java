package com.yuwang.pinju.domain.item;
import java.util.Date;

public class SpuDO implements java.io.Serializable {

	private static final long serialVersionUID = -2232654615038586221L;

	public static Long ITEM_SPU_STATUS_1 = 1l;
	
	public static Long ITEM_SPU_STATUS_2 = 1l;
	
	public static Long ITEM_SPU_STATUS_3 = 1l;

	private Long id;

	private Long categoryId;

	private Long keyPropertyId;

	private String name;

	private String memo;

	private String propertyValuePair;

	private String picUrl;

	private Long status;

	private Date gmtCreate;

	private Date gmtModified;
	
	private Long keyPropertyValue;
	
	private Long marketPrice;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getKeyPropertyId() {
		return this.keyPropertyId;
	}

	public void setKeyPropertyId(Long keyPropertyId) {
		this.keyPropertyId = keyPropertyId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPropertyValuePair() {
		return this.propertyValuePair;
	}

	public void setPropertyValuePair(String propertyValuePair) {
		this.propertyValuePair = propertyValuePair;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return this.gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getKeyPropertyValue() {
		return keyPropertyValue;
	}

	public void setKeyPropertyValue(Long keyPropertyValue) {
		this.keyPropertyValue = keyPropertyValue;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Long marketPrice) {
		this.marketPrice = marketPrice;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SpuDO [categoryId=" + categoryId + ", gmtCreate=" + gmtCreate
				+ ", gmtModified=" + gmtModified + ", id=" + id
				+ ", keyPropertyId=" + keyPropertyId + ", keyPropertyValue="
				+ keyPropertyValue + ", marketPrice=" + marketPrice + ", memo="
				+ memo + ", name=" + name + ", picUrl=" + picUrl
				+ ", propertyValuePair=" + propertyValuePair + ", status="
				+ status + "]";
	}

}