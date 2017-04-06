/**
 * 
 */
package com.yuwang.pinju.Constant;

/**  
 * @Project: pinju-model
 * @Title: ItemFeaturesEnum.java
 * @Package com.yuwang.pinju.Constant
 * @Description: 商品featrues
 * @author liuboen liuboen@zba.com
 * @date 2011-7-27 下午02:47:25
 * @version V1.0  
 */

public enum ItemFeaturesEnum {
	
	SHOP_DISTRIBUTION(1,"shopDestri","店铺分销"),
	ITEM_LIMIT_TIME_DISCOUNT(2,"limitDiscount","限时折扣"),
	ITEM_LIMIT_XIANGOU(3,"itemxiangou","限购特供");
	/**
	 * 类型
	 */
	private int featureType;
	/**
	 * 名称(数据库featrues存储名)
	 */
	private String featureName;
	/**
	 * 描叙
	 */
	private String description;
	/**
	 * 初始化
	 * @param featureType
	 * @param featureName
	 * @param description
	 */
	ItemFeaturesEnum(int featureType,String featureName,String description){
		this.featureType=featureType;
		this.featureName=featureName;
		this.description=description;
	}
	
	ItemFeaturesEnum getValueByType(int featureType){
		for (ItemFeaturesEnum enums : values()) {
			if (enums.getFeatureType()==featureType) {
				return enums;
			}
		}
		return null;
	}
	ItemFeaturesEnum getValueByName(String featureName){
		for (ItemFeaturesEnum enums : values()) {
			//不区分大小写返回
			if (enums.getFeatureName().equalsIgnoreCase(featureName)) {
				return enums;
			}
		}
		return null;
	}

	/**
	 * @return the featureType
	 */
	public int getFeatureType() {
		return featureType;
	}

	/**
	 * @return the featureName
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
}
