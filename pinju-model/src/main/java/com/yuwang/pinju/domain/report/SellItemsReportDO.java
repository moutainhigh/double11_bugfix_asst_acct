package com.yuwang.pinju.domain.report;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.domain.item.ItemDO;


public class SellItemsReportDO  extends ItemDO implements java.io.Serializable{
	
	private static final long serialVersionUID = -372559432187931426L;
	
	 private Long itemId;
	 private Long itemSum;
	 private Long priceAmount;
	 
	 private String amountByYuan;
	 
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	public Long getItemSum() {
		return itemSum;
	}
	public void setItemSum(Long itemSum) {
		this.itemSum = itemSum;
	}
	public Long getPriceAmount() {
		return priceAmount;
	}
	public void setPriceAmount(Long priceAmount) {
		this.priceAmount = priceAmount;
		this.amountByYuan=MoneyUtil.getCentToDollar(this.priceAmount);
	}
	public String getAmountByYuan() {
		return amountByYuan;
	}
}
