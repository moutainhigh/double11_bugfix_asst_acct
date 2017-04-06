package com.yuwang.pinju.domain.report;

import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.common.MoneyUtil;

/**
 * @see
 * <p>Discription: 
 * 	 封装类型查询的bean
 * </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-8-27
 */
public class SellCategoryReportDO implements java.io.Serializable{
	private static final long serialVersionUID = -502964900223942136L;
	private Long categoryId;
	 private Long itemSum;
	 private Long priceAmount;
	 private String amountByYuan;
	 private ItemCategoryDO itemCategoryDO;
	 private String displayName;

	 
	 
	public ItemCategoryDO getItemCategoryDO() {
		return itemCategoryDO;
	}

	public void setItemCategoryDO(ItemCategoryDO itemCategoryDO) {
		this.itemCategoryDO = itemCategoryDO;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public void setAmountByYuan(String amountByYuan) {
		this.amountByYuan = amountByYuan;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

/*	
	public ItemCategoryDO getItemCategoryDO() {
		return itemCategoryDO;
	}

	public void setItemCategoryDO(ItemCategoryDO itemCategoryDO) {
		StringBuffer buf=new StringBuffer();
		if(StringUtils.isNotBlank(itemCategoryDO.getName())){
			buf.append(itemCategoryDO.getName());
		}
		if(StringUtils.isNotBlank(itemCategoryDO.getLevel1Name())){
			buf.append(">"+itemCategoryDO.getLevel1Name());
		}
		if(StringUtils.isNotBlank(itemCategoryDO.getLevel2Name())){
			buf.append(">"+itemCategoryDO.getLevel2Name());
		}
		if(StringUtils.isNotBlank(itemCategoryDO.getLevel3Name())){
			buf.append(">"+itemCategoryDO.getLevel3Name());
		}
		if(StringUtils.isNotBlank(itemCategoryDO.getLevel4Name())){
			buf.append(">"+itemCategoryDO.getLevel4Name());
		}
		itemCategoryDO.setDisplayName(buf.toString());
		
		this.itemCategoryDO = itemCategoryDO;
	}*/
	
}


