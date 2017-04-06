package com.yuwang.pinju.domain.shop;

import java.util.List;

import com.yuwang.pinju.domain.Paginator;

public class ShopQuery extends Paginator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -586790358942742343L;
	
	/**
	 * 店铺id list
	 */
	List<Integer> shopIdList;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 消保类型
	 */
	private String exchangeType;
	/**
	 * 返回结果
	 */
	private List<ShopBusinessInfoDO> resultList;
	
	public ShopQuery() {
		super();
	}

	public ShopQuery(Integer currentPage, String province, String exchangeType) {
		super();
		this.province = province;
		this.exchangeType = exchangeType;
		setPage(currentPage);
	}

	public ShopQuery(List<Integer> shopIdList) {
		super();
		this.shopIdList = shopIdList;
		setPage(1);
		setItemsPerPage(20);
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}


	public List<ShopBusinessInfoDO> getResultList() {
		return resultList;
	}

	public void setResultList(List<ShopBusinessInfoDO> resultList) {
		this.resultList = resultList;
	}

	public List<Integer> getShopIdList() {
		return shopIdList;
	}

	public void setShopIdList(List<Integer> shopIdList) {
		this.shopIdList = shopIdList;
	}

	public String getExchangeType() {
	    return exchangeType;
	}

	public void setExchangeType(String exchangeType) {
	    this.exchangeType = exchangeType;
	}

}
