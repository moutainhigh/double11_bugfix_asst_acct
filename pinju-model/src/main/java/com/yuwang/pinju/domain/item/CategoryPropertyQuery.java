package com.yuwang.pinju.domain.item;

import java.util.ArrayList;
import java.util.List;

public class CategoryPropertyQuery {

	private String cpId;

	private String childIds;

	private String preId;

	private String topPreId;

	private String cpName;

	private int isEnumerate;

	private int required;

	private int isMultipleChoice;

	private int isAcceptsInput;

	private int isSpuKey;

	private String propertyValue;

	private int inputType;

	private int lenLimit;
	
	private String maxValue;

	private String minValue;
	/**
	 * 是否注销属性 1-是 2否
	 */
	private int isSelleProperty;

	/**
	 * 是否关键属性
	 */
	private int isKeyProperty;

	/**
	 * 是否允许自定义
	 */
	private int isSellCustom;

	public List<CategoryPropertyValueQuery> cpvList;
	
	public List<CustomProValueDO> customSkuList; 

	public List<CustomProValueDO> getCustomSkuList() {
		return customSkuList;
	}

	public void setCustomSkuList(List<CustomProValueDO> customSkuList) {
		this.customSkuList = customSkuList;
	}

	public void addCategoryPropertyValue(String cpvId, String cpvValue, int isSelect, long sort, long valueType, String showValue) {
		if (cpvList == null) {
			cpvList = new ArrayList<CategoryPropertyValueQuery>();
		}
		cpvList.add(new CategoryPropertyValueQuery(cpvId, cpvValue, isSelect, sort, valueType, showValue));
	}

	public String getChildIds() {
		return childIds;
	}

	public String getCpId() {
		return cpId;
	}

	public String getCpName() {
		return cpName;
	}

	public List<CategoryPropertyValueQuery> getCpvList() {
		return cpvList;
	}

	public int getIsAcceptsInput() {
		return isAcceptsInput;
	}

	public int getIsEnumerate() {
		return isEnumerate;
	}

	public int getIsKeyProperty() {
		return isKeyProperty;
	}

	public int getIsMultipleChoice() {
		return isMultipleChoice;
	}

	public int getIsSelleProperty() {
		return isSelleProperty;
	}

	public int getIsSpuKey() {
		return isSpuKey;
	}

	public String getPreId() {
		return preId;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public int getRequired() {
		return required;
	}

	public String getTopPreId() {
		return topPreId;
	}

	public void setChildIds(String childIds) {
		this.childIds = childIds;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public void setCpvList(List cpvList) {
		this.cpvList = cpvList;
	}

	public void setIsAcceptsInput(int isAcceptsInput) {
		this.isAcceptsInput = isAcceptsInput;
	}

	public void setIsEnumerate(int isEnumerate) {
		this.isEnumerate = isEnumerate;
	}

	public void setIsKeyProperty(int isKeyProperty) {
		this.isKeyProperty = isKeyProperty;
	}

	public void setIsMultipleChoice(int isMultipleChoice) {
		this.isMultipleChoice = isMultipleChoice;
	}

	public void setIsSelleProperty(int isSelleProperty) {
		this.isSelleProperty = isSelleProperty;
	}

	public void setIsSpuKey(int isSpuKey) {
		this.isSpuKey = isSpuKey;
	}

	public void setPreId(String preId) {
		this.preId = preId;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public void setRequired(int required) {
		this.required = required;
	}

	public void setTopPreId(String topPreId) {
		this.topPreId = topPreId;
	}
	
	public int getInputType() {
		return inputType;
	}

	public void setInputType(int inputType) {
		this.inputType = inputType;
	}

	public int getLenLimit() {
		return lenLimit;
	}

	public void setLenLimit(int lenLimit) {
		this.lenLimit = lenLimit;
	}
	
	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	
	public int getIsSellCustom() {
		return isSellCustom;
	}

	public void setIsSellCustom(int isSellCustom) {
		this.isSellCustom = isSellCustom;
	}
}
