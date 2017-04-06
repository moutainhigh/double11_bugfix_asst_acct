/**
 * 品聚生活，创造奇迹
 */
package com.yuwang.pinju.domain.shop;

import com.yuwang.pinju.domain.item.ItemTagMetaInfo;
import com.yuwang.pinju.domain.search.result.SearchResult;

/**
 * @author liyouguo
 * 
 * @since 2011-8-4
 */
public class SearchResultExt extends SearchResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7964948613090330637L;
	
	/**
	 * 装修头部
	 */
	private String shopUpHtml;
	/**
	 * 装修的左边栏
	 */
	private String shopLeftHtml;
	
	/**
	 * 店铺风格
	 */
	private String shopFitment;
	
	/**
	 * 商品标签的其他信息
	 */
	private ItemTagMetaInfo itemTagMetaInfo;
	
	public ItemTagMetaInfo getItemTagMetaInfo() {
		return itemTagMetaInfo;
	}

	public void setItemTagMetaInfo(ItemTagMetaInfo itemTagMetaInfo) {
		this.itemTagMetaInfo = itemTagMetaInfo;
	}

	public SearchResultExt() {
		super();
	}
	
	public SearchResultExt(SearchResult result) {
		super();
		this.setAction(result.getAction());
		this.setEndRow(result.getEndRow());
		this.setItems(result.getItems());
		this.setItemsPerPage(result.getItemsPerPage());
		this.setPage(result.getPage());
		this.setResultList(result.getResultList());
		this.setStart(result.getStart());
		this.setStartRow(result.getStartRow());
	}
	
	public String getShopUpHtml() {
		return shopUpHtml;
	}

	public void setShopUpHtml(String shopUpHtml) {
		this.shopUpHtml = shopUpHtml;
	}

	public String getShopLeftHtml() {
		return shopLeftHtml;
	}

	public void setShopLeftHtml(String shopLeftHtml) {
		this.shopLeftHtml = shopLeftHtml;
	}

	public String getShopFitment() {
		return shopFitment;
	}

	public void setShopFitment(String shopFitment) {
		this.shopFitment = shopFitment;
	}
}
