package com.yuwang.pinju.domain.item;

import java.util.List;

/**
 * 类目显示
 * 
 * @author liming
 * 
 */
public class CategoryQuery {

	/**
	 * 类目对象
	 */
	private CategoryDO categoryDO;

	private int propertySize;

	private int sellPropertySize;

	/**
	 * 普通类目属性集合
	 */
	private List categoryPropertyList;

	/**
	 * 关键类目属性集合
	 */
	private List keyCategoryPropertyList;

	/**
	 * 销售类目属性集合
	 */
	private List sellCategoryPropertyList;

	/**
	 * SKU集合(更新用)
	 */
	private List skuList;
	
	/**
	 * 类目名称
	 */
	private String categoryTitle;

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public CategoryDO getCategoryDO() {
		return categoryDO;
	}

	public void setCategoryDO(CategoryDO categoryDO) {
		this.categoryDO = categoryDO;
	}

	public List getCategoryPropertyList() {
		return categoryPropertyList;
	}

	public void setCategoryPropertyList(List categoryPropertyList) {
		this.categoryPropertyList = categoryPropertyList;
	}

	public List getKeyCategoryPropertyList() {
		return keyCategoryPropertyList;
	}

	public void setKeyCategoryPropertyList(List keyCategoryPropertyList) {
		this.keyCategoryPropertyList = keyCategoryPropertyList;
	}

	public List getSellCategoryPropertyList() {
		return sellCategoryPropertyList;
	}

	public void setSellCategoryPropertyList(List sellCategoryPropertyList) {
		this.sellCategoryPropertyList = sellCategoryPropertyList;
	}

	public List getSkuList() {
		return skuList;
	}

	public void setSkuList(List skuList) {
		this.skuList = skuList;
	}

	public int getPropertySize() {
		return propertySize;
	}

	public void setPropertySize(int propertySize) {
		this.propertySize = propertySize;
	}

	public int getSellPropertySize() {
		return sellPropertySize;
	}

	public void setSellPropertySize(int sellPropertySize) {
		this.sellPropertySize = sellPropertySize;
	}

}
