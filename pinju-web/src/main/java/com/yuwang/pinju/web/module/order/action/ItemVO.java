package com.yuwang.pinju.web.module.order.action;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;

public class ItemVO extends ItemDO implements Comparable<ItemVO>{
	private static final long serialVersionUID = 1L;
	private String skuid;
	private String skuDesc;
	
	//收藏时候的价格
	private String favPrice;
	
	//收藏时候的数量
	private String favCount;
	

	private ItemPropertyVO itemPropertyVO;
	private String channelId;
	private String ad;
	private String itemCount;
	private String singleTotal;
	private Long   sortDate;
	private boolean needPriceTip = false;
	private Integer lowerPrices;	// 1=降价  2=涨价
	private String leftPrices;		//降价 多少 或 涨价了 多少
	private Integer minStocks=1;	//显示库存紧张
	private static final Integer MIN_STOCKS_FLAG= 5;

	public ItemVO(ItemDO item){
		super();
		this.setId(item.getId());
		this.setTitle(item.getTitle());
		this.setSellerId(item.getSellerId());
		this.setSellerNick(item.getSellerNick());
		this.setPrice(item.getPrice());
		this.setCurStock(item.getCurStock());
		this.setStatus(item.getStatus());
		this.setPicUrl(item.getPicUrl());
		this.setItemCount("" + item.getCurStock());
	}
	
	public String getFavCount() {
		return favCount;
	}

	public void setFavCount(String favCount) {
		this.favCount = favCount;
	}

	
	public boolean needPriceTip() {
		return needPriceTip;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}
	
	public String getSingleTotal() {
		return singleTotal;
	}

	public void setSingleTotal(String singleTotal) {
		this.singleTotal = singleTotal;
	}
	
	public String getSkuid() {
		return skuid;
	}
	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}
	public String getSkuDesc() {
		return skuDesc;
	}
	public void setSkuDesc(String skuDesc) {
		this.skuDesc = skuDesc;
	}
	public String getFavPrice() {
		return favPrice;
	}
	public void setFavPrice(String favPrice) {
		this.favPrice = favPrice;
		if(favPrice != null && favPrice.equals(getPriceByYuan()))
			needPriceTip = true;
	}
	
	public ItemPropertyVO getItemPropertyVO() {
		return itemPropertyVO;
	}
	public void setItemPropertyVO(ItemPropertyVO itemPropertyVO) {
		this.itemPropertyVO = itemPropertyVO;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Long getSortDate() {
		return sortDate;
	}

	public void setSortDate(Long sortDate) {
		this.sortDate = sortDate;
	}

	public Integer getLowerPrices() {
		return lowerPrices;
	}

	public void setLowerPrices(Integer lowerPrices) {
		this.lowerPrices = lowerPrices;
	}
	
	public Integer getCurStockInt(){
		return this.getCurStock().intValue();
	}
	
	public Integer getFavCountInt(){
		if(favCount == null || "".equals(favCount))
			return 0;
		
		return Integer.parseInt(favCount);
	}

	public String getLeftPrices() {
		return leftPrices;
	}

	public void setLeftPrices(Long leftPrice) {
		this.leftPrices = MoneyUtil.getCentToDollar(leftPrice);
	}
	
	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	
	public Integer getMinStocks() {
		return minStocks;
	}

	public void setMinStocks(Long minStocks) {
		this.minStocks = minStocks==0 ?  0 : minStocks>MIN_STOCKS_FLAG ? 1 : 0;;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemVO other = (ItemVO) obj;
		if (sortDate == null) {
			if (other.sortDate != null)
				return false;
		} else if (sortDate.compareTo(other.sortDate) != 0)
			return false;
		
		return true;
	}

	@Override
	public int compareTo(ItemVO itemVO) {
		if(this.equals(itemVO))
			return 0;
		
		//升序
		//return this.sortDate.compareTo(itemVO.getSortDate());
		
		//降序
		return itemVO.getSortDate().compareTo(this.sortDate);
	}
}