package com.yuwang.pinju.domain.shop;

import java.util.*;

import com.yuwang.pinju.domain.item.ItemDO;

/**
 * 商品归类实体
 * @author liyouguo
 *
 * @since 2011-7-15
 */
public class ItemCategorizeDO extends ItemDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3516565783086817531L;
	
	/**
	 * 商品归类复选框的value值
	 */
	private String itemCates;

	/**
	 * 商品当前归类情况
	 */
	private Map<String, String> categoryNameList;
	
	public String getItemCates() {
		return itemCates;
	}

	public void setItemCates(String itemCates) {
		this.itemCates = itemCates;
	}

	public Map<String, String> getCategoryNameList() {
		return categoryNameList;
	}

	public void setCategoryNameList(Map<String, String> categoryNameList) {
		this.categoryNameList = categoryNameList;
	} 
}
