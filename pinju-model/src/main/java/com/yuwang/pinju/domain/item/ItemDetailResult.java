/**
 * 
 */
package com.yuwang.pinju.domain.item;

import java.util.List;
import java.util.Map;

import org.springframework.web.util.HtmlUtils;

import com.yuwang.pinju.common.ImageUtil;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;

/**  
 * @Project: pinju-model
 * @Title: ItemDetailUtil.java
 * @Package com.yuwang.pinju.domain.item
 * @Description: 商品详情页面类
 * @author liuboen liuboen@zba.com
 * @date 2011-6-8 下午01:30:36
 * @version V1.0  
 */

public class ItemDetailResult {
	/**
	 * 商品基本信息
	 */
	private ItemDO itemDO;
	/**
	 * 类目属性和属性值,键值对
	 */
	private List <Map<String, Object>> cateProList;
	private List <Map<String, Object>> spuProList;
	
	private List <Map<String,Object>>jsSkuInit;
	private List <Map<String,Object>> skuListPV;

	private String upperPrice;
	private String lowerPrice;

	private String shopLeftHtml;
	private String shopUpHtml;
	private String shopFitment;

	private Map<String,String> logisticsMap;
	private String logisticsStr;

	private ActivityDiscountDO activityDiscountDO;
	private Double activityDiscount;
	private Long actLimitCount;
	private Long actHour;
	private Long actMinute;
	
	private Boolean isLimitBuyItem;
	
	private Boolean isSeller;
	private Boolean isShelves;
	/**
	 * 加密的ID(以及时间戳)
	 */
	private String encryptItemId;
	/**
	 * 商品标签的其他信息
	 */
	private ItemTagMetaInfo itemTagMetaInfo;
	/**
	 * @return the cateProList
	 */
	public List<Map<String, Object>> getCateProList() {
		return cateProList;
	}
	
	/**
	 * @return the actLimitCount
	 */
	public Long getActLimitCount() {
		return actLimitCount;
	}

	/**
	 * @param actLimitCount the actLimitCount to set
	 */
	public void setActLimitCount(Long actLimitCount) {
		this.actLimitCount = actLimitCount;
	}

	/**
	 * @return the isLimitBuyItem
	 */
	public Boolean getIsLimitBuyItem() {
		return isLimitBuyItem;
	}

	/**
	 * @param isLimitBuyItem the isLimitBuyItem to set
	 */
	public void setIsLimitBuyItem(Boolean isLimitBuyItem) {
		this.isLimitBuyItem = isLimitBuyItem;
	}

	/**
	 * @param actHour the actHour to set
	 */
	public void setActHour(Long actHour) {
		this.actHour = actHour;
	}

	/**
	 * @param actMinute the actMinute to set
	 */
	public void setActMinute(Long actMinute) {
		this.actMinute = actMinute;
	}

	/**
	 * @param cateProList the cateProList to set
	 */
	public void setCateProList(List<Map<String, Object>> cateProList) {
		this.cateProList = cateProList;
	}
	/**
	 * @return the itemDO
	 */
	public ItemDO getItemDO() {
		return itemDO;
	}
	/**
	 * @param itemDO the itemDO to set
	 */
	public void setItemDO(ItemDO itemDO) {
		this.itemDO = itemDO;
	}
	

	/**
	 * @return the actHour
	 */
	public Long getActHour() {
		return actHour;
	}

	/**
	 * @return the actMinute
	 */
	public Long getActMinute() {
		return actMinute;
	}

	/**
	 * @return the activityDiscount
	 */
	public Double getActivityDiscount() {
		return activityDiscount;
	}
	/**
	 * @param activityDiscount the activityDiscount to set
	 */
	public void setActivityDiscount(Double activityDiscount) {
		this.activityDiscount = activityDiscount;
	}
	/**
	 * @return the priceYuan
	 */
	public String getPriceCentToDollar(long priceCent) {
		return new Money(priceCent).toString();
	}
	/**
	 * @return the priceYuan
	 */
	public String getDiscount(long priceCent,Double discount) {
		return new Money(priceCent).multiply(discount).toString();
	}
	public String getRichHtmlString(String htmlString){
		return HtmlUtils.htmlEscape(htmlString);
	}
	/**
	 * @return the spuProList
	 */
	public List<Map<String, Object>> getSpuProList() {
		return spuProList;
	}
	/**
	 * @param spuProList the spuProList to set
	 */
	public void setSpuProList(List<Map<String, Object>> spuProList) {
		this.spuProList = spuProList;
	}
	/**
	 * @return the jsSkuInit
	 */
	public List<Map<String, Object>> getJsSkuInit() {
		return jsSkuInit;
	}
	/**
	 * @param jsSkuInit the jsSkuInit to set
	 */
	public void setJsSkuInit(List<Map<String, Object>> jsSkuInit) {
		this.jsSkuInit = jsSkuInit;
	}
	/**
	 * @return the skuListPV
	 */
	public List<Map<String, Object>> getSkuListPV() {
		return skuListPV;
	}
	/**
	 * @param skuListPV the skuListPV to set
	 */
	public void setSkuListPV(List<Map<String, Object>> skuListPV) {
		this.skuListPV = skuListPV;
	}
	/**
	 * @return the upperPrice
	 */
	public String getUpperPrice() {
		return upperPrice;
	}
	/**
	 * @param upperPrice the upperPrice to set
	 */
	public void setUpperPrice(String upperPrice) {
		this.upperPrice = upperPrice;
	}
	/**
	 * @return the lowerPrice
	 */
	public String getLowerPrice() {
		return lowerPrice;
	}
	/**
	 * @param lowerPrice the lowerPrice to set
	 */
	public void setLowerPrice(String lowerPrice) {
		this.lowerPrice = lowerPrice;
	}
	/**
	 * @return the shopLeftHtml
	 */
	public String getShopLeftHtml() {
		return shopLeftHtml;
	}
	/**
	 * @param shopLeftHtml the shopLeftHtml to set
	 */
	public void setShopLeftHtml(String shopLeftHtml) {
		this.shopLeftHtml = shopLeftHtml;
	}
	/**
	 * @return the shopUpHtml
	 */
	public String getShopUpHtml() {
		return shopUpHtml;
	}
	/**
	 * @param shopUpHtml the shopUpHtml to set
	 */
	public void setShopUpHtml(String shopUpHtml) {
		this.shopUpHtml = shopUpHtml;
	}
	/**
	 * @return the shopFitment
	 */
	public String getShopFitment() {
		return shopFitment;
	}
	/**
	 * @param shopFitment the shopFitment to set
	 */
	public void setShopFitment(String shopFitment) {
		this.shopFitment = shopFitment;
	}



	/**
	 * @return the logisticsMap
	 */
	public Map<String, String> getLogisticsMap() {
		return logisticsMap;
	}
	/**
	 * @param logisticsMap the logisticsMap to set
	 */
	public void setLogisticsMap(Map<String, String> logisticsMap) {
		this.logisticsMap = logisticsMap;
	}
	/**
	 * @return the activityDiscountDO
	 */
	public ActivityDiscountDO getActivityDiscountDO() {
		return activityDiscountDO;
	}
	/**
	 * @param activityDiscountDO the activityDiscountDO to set
	 */
	public void setActivityDiscountDO(ActivityDiscountDO activityDiscountDO) {
		this.activityDiscountDO = activityDiscountDO;
	}
	
	/**
	 * @return the encryptItemId
	 */
	public String getEncryptItemId() {
		return encryptItemId;
	}
	/**
	 * @param encryptItemId the encryptItemId to set
	 */
	public void setEncryptItemId(String encryptItemId) {
		this.encryptItemId = encryptItemId;
	}
	public String getThumbJpg(String jpgFileName){
		if (jpgFileName==null||jpgFileName.equals("")) {
			return "";
		}
		return ImageUtil.getJpgThumb(jpgFileName);
	}
	/**
	 * @return the logisticsStr
	 */
	public String getLogisticsStr() {
		return logisticsStr;
	}
	/**
	 * @param logisticsStr the logisticsStr to set
	 */
	public void setLogisticsStr(String logisticsStr) {
		this.logisticsStr = logisticsStr;
	}
	/**
	 * @return the isSeller
	 */
	public Boolean getIsSeller() {
		return isSeller;
	}
	/**
	 * @param isSeller the isSeller to set
	 */
	public void setIsSeller(Boolean isSeller) {
		this.isSeller = isSeller;
	}
	/**
	 * @return the isShelves
	 */
	public Boolean getIsShelves() {
		return isShelves;
	}
	/**
	 * @param isShelves the isShelves to set
	 */
	public void setIsShelves(Boolean isShelves) {
		this.isShelves = isShelves;
	}

	public ItemTagMetaInfo getItemTagMetaInfo() {
		return itemTagMetaInfo;
	}

	public void setItemTagMetaInfo(ItemTagMetaInfo itemTagMetaInfo) {
		this.itemTagMetaInfo = itemTagMetaInfo;
	}
	
}
