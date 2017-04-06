/**
 * 
 */
package com.yuwang.pinju.domain.item;

import java.util.List;

/**
 * @author liyouguo
 * 
 */
public class ItemQueryEx extends ItemQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4268446461903669872L;

	private String orderBy;
	
	private List<Long> itemIdList;
	
	private String ip;
	
	public List<Long> getItemIdList() {
		return itemIdList;
	}

	public void setItemIdList(List<Long> itemIdList) {
		this.itemIdList = itemIdList;
	}

	public ItemQueryEx() {
		super();
		setEndRow(20);
		setStartRow(1);
		setMaxPrice(0L);
		setMinPrice(0L);
		setMaxSales(0L);
		setMinSales(0L);
		setItemsPerPage(20);
	}
	
	public ItemQueryEx(String empty) {
		super();
	}
	/**
	 * 查询供应商分销商品列表(格子铺专用)
	 * 
	 * @param itemIdList
	 * 			商品ids
	 * @author caiwei
	 */
	public ItemQueryEx(List<Long> itemIdList,Integer itemsPerPage) {
		super();
		this.itemIdList = itemIdList;
		setItems(itemIdList!=null?itemIdList.size():0);
		setPage(1);
		setItemsPerPage(getItems());
	}

	/**
	 * 查询供应商分销商品列表(分销页面显示专用)
	 * 
	 * @param itemIdList
	 * 			商品ids
	 * @author caiwei
	 */
	public ItemQueryEx(List<Long> itemIdList) {
		super();
		this.itemIdList = itemIdList;
		setItems(10);
		setPage(1);
		setItemsPerPage(10);
	}
	
	public ItemQueryEx(List<Integer> statusList,long userId) {
		super();
		setStatus(statusList);
		setSellerId(userId);
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
