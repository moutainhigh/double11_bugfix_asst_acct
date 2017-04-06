package com.yuwang.pinju.domain.item;

import java.util.Date;

/**
 * 
 * 类目属性
 * 
 * @author user
 * 
 */
public class CategoryPropertyDO implements java.io.Serializable {

	private static final long serialVersionUID = 7775591210653187896L;

	/**
	 * 有效
	 */
	public static final long IS_VALID = 1L;

	/**
	 * 是
	 */
	public static final long IS_YES = 1;

	/**
	 * 否
	 */
	public static final long IS_NO = 2;
	
	/**
	 * 非自定义类型
	 */
	public static final long SELL_CUSTOM_FALSE=0;
	/**
	 * 可自定义类型
	 */
	public static final long SELL_CUSTOM_TRUE=1;
	/**
	 * 可自定义类型,没有图片类型
	 */
	public static final long SELL_CUSTOM_NO_IMG_TRUE=2;

	/**
	 * 类目属性ID
	 */
	private Long id;

	/**
	 * 类目属性父ID
	 */
	private Long parentId;

	/**
	 * 类目ID
	 */
	private Long categoryId;

	/**
	 * 属性ID
	 */
	private Long propertyId;

	/**
	 * 是否注销属性 1-是 2否
	 */
	private Long isSelleProperty;

	/**
	 * 是否关键属性
	 */
	private Long isKeyProperty;

	/**
	 * 是否枚举属性
	 */
	private Long isEnumerate;

	/**
	 * 是否是品牌属性
	 */
	private Long isBrandProperty;

	/**
	 * 是否必填属性
	 */
	private Long required;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	/**
	 * 排序值
	 */
	private Long sortOrder;

	/**
	 * 排序类型
	 */
	private Long sortType;

	/**
	 * 特性
	 */
	private String features;

	/**
	 * 输入类型
	 */
	private Long inputType;

	/**
	 * 属性值长度限制
	 */
	private Long lenLimit;

	/**
	 * 状态
	 */
	private Long status;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 是否是多选 1-单选 下拉框 2-单选 单选框 3 多选 多选框
	 */
	private Long isMultipleChoice;

	/**
	 * 是否允许输入
	 */
	private Long isAcceptsInput;

	/**
	 * 是否活动
	 */
	private Long isActivities;

	/**
	 * 最大输入值
	 */
	private Long maxValue;

	/**
	 * 最小输入值
	 */
	private Long minValue;

	/**
	 * 是否是SPU
	 */
	private Long isSpuKey;
	
	/**
	 * 是否可以自定义 0 不允许自定义 1 允许自定义
	 */
	private Long isSellCustom;

	public CategoryPropertyDO() {
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public String getFeatures() {
		return this.features;
	}

	public Date getGmtCreate() {
		return this.gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public Long getId() {
		return this.id;
	}

	public Long getInputType() {
		return this.inputType;
	}

	public Long getIsAcceptsInput() {
		return isAcceptsInput;
	}

	public Long getIsActivities() {
		return isActivities;
	}

	public Long getIsBrandProperty() {
		return isBrandProperty;
	}

	public Long getIsEnumerate() {
		return this.isEnumerate;
	}

	public Long getIsKeyProperty() {
		return this.isKeyProperty;
	}

	public Long getIsMultipleChoice() {
		return isMultipleChoice;
	}

	public Long getIsSelleProperty() {
		return this.isSelleProperty;
	}

	public Long getIsSpuKey() {
		return isSpuKey;
	}
	
	public Long getLenLimit() {
		return this.lenLimit;
	}

	public Long getMaxValue() {
		return maxValue;
	}

	public Long getMinValue() {
		return minValue;
	}

	public String getName() {
		return name;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public Long getPropertyId() {
		return this.propertyId;
	}

	public Long getRequired() {
		return this.required;
	}

	public Long getSortOrder() {
		return this.sortOrder;
	}

	public Long getSortType() {
		return this.sortType;
	}

	public Long getStatus() {
		return status;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInputType(Long inputType) {
		this.inputType = inputType;
	}

	public void setIsAcceptsInput(Long isAcceptsInput) {
		this.isAcceptsInput = isAcceptsInput;
	}

	public void setIsActivities(Long isActivities) {
		this.isActivities = isActivities;
	}

	public void setIsBrandProperty(Long isBrandProperty) {
		this.isBrandProperty = isBrandProperty;
	}

	public void setIsEnumerate(Long isEnumerate) {
		this.isEnumerate = isEnumerate;
	}

	public void setIsKeyProperty(Long isKeyProperty) {
		this.isKeyProperty = isKeyProperty;
	}

	public void setIsMultipleChoice(Long isMultipleChoice) {
		this.isMultipleChoice = isMultipleChoice;
	}

	public void setIsSelleProperty(Long isSelleProperty) {
		this.isSelleProperty = isSelleProperty;
	}

	public void setIsSpuKey(Long isSpuKey) {
		this.isSpuKey = isSpuKey;
	}

	public void setLenLimit(Long lenLimit) {
		this.lenLimit = lenLimit;
	}

	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}

	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public void setRequired(Long required) {
		this.required = required;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setSortType(Long sortType) {
		this.sortType = sortType;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getIsSellCustom() {
		return isSellCustom;
	}

	public void setIsSellCustom(Long isSellCustom) {
		this.isSellCustom = isSellCustom;
	}

}