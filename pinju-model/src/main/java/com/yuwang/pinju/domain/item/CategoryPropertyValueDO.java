package com.yuwang.pinju.domain.item;

import java.util.Date;

/**
 * 
 * 类目属性值
 * 
 * @author liming
 * 
 */
public class CategoryPropertyValueDO implements java.io.Serializable {

	private static final long serialVersionUID = 65479864653620404L;
	
	/**
	 * 类目属性值ID
	 */
	private Long id;
	
	/**
	 * 类目属性ID
	 */
	private Long valueId;
	
	/**
	 * 值ID
	 */
	private Long categoryPropertyId;
	
	/**
	 * 排序值
	 */
	private Long sortOrder;
	
	/**
	 * 排序类型
	 */
	private Long sortType;
	
	/**
	 * 值的别名
	 */
	private String valueAlias;
	
	/**
	 * 特性
	 */
	private String features;
	
	private String value;
	
	private Date gmtCreate;
	
	private Date gmtModified;
	
	private Long valueType;
	
	public Long getValueType() {
		return valueType;
	}

	public void setValueType(Long valueType) {
		this.valueType = valueType;
	}

	public CategoryPropertyValueDO() {
	}

	public CategoryPropertyValueDO(Long id) {
		this.id = id;
	}

	public CategoryPropertyValueDO(Long id, Long valueId,
			Long categoryPropertyId, Long sortOrder, Long sortType,
			String valueAlias, String features, Long valueType) {
		this.id = id;
		this.valueId = valueId;
		this.categoryPropertyId = categoryPropertyId;
		this.sortOrder = sortOrder;
		this.sortType = sortType;
		this.valueAlias = valueAlias;
		this.features = features;
		this.valueType = valueType;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getValueId() {
		return this.valueId;
	}

	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}

	public Long getCategoryPropertyId() {
		return this.categoryPropertyId;
	}

	public void setCategoryPropertyId(Long categoryPropertyId) {
		this.categoryPropertyId = categoryPropertyId;
	}

	public Long getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getSortType() {
		return this.sortType;
	}

	public void setSortType(Long sortType) {
		this.sortType = sortType;
	}

	public String getValueAlias() {
		return this.valueAlias;
	}

	public void setValueAlias(String valueAlias) {
		this.valueAlias = valueAlias;
	}

	public String getFeatures() {
		return this.features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

}