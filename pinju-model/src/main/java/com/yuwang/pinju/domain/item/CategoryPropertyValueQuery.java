package com.yuwang.pinju.domain.item;

public class CategoryPropertyValueQuery {

	public String cpvId;

	public String cpvValue;

	public int isSelect;

	public long sort;

	public long id;

	private String value;
	
	private String imgUrl;
	
	private long valueType;

	private String showValue;
	
	public CategoryPropertyValueQuery(String cpvId, String cpvValue, int isSelect, long sort, long valueType, String showValue) {
		this.cpvId = cpvId;
		this.cpvValue = cpvValue;
		this.isSelect = isSelect;
		this.sort = sort;
		this.valueType = valueType;
		this.showValue = showValue;
	}

	public String getCpvId() {
		return cpvId;
	}

	public String getCpvValue() {
		return cpvValue;
	}

	public void setCpvId(String cpvId) {
		this.cpvId = cpvId;
	}

	public void setCpvValue(String cpvValue) {
		this.cpvValue = cpvValue;
	}

	public int getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(int isSelect) {
		this.isSelect = isSelect;
	}

	public long getSort() {
		return sort;
	}

	public void setSort(long sort) {
		this.sort = sort;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public long getValueType() {
		return valueType;
	}

	public void setValueType(long valueType) {
		this.valueType = valueType;
	}
	
	public String getShowValue() {
		return showValue;
	}

	public void setShowValue(String showValue) {
		this.showValue = showValue;
	}
}