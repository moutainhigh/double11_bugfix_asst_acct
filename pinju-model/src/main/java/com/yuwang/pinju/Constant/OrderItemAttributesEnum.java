package com.yuwang.pinju.Constant;



/**
 * Created on 2011-8-29
 * @see
 * <p>Discription: 子订单标记</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public enum OrderItemAttributesEnum {
	SHOP_DISTRIBUTION(1,"shopDestri","店铺分销"),
	//存储value : limitDiscount:活动编号,折扣率
	ITEM_LIMIT_TIME_DISCOUNT(2,"limitDiscount","限时折扣"),
	DISCOUNT_CODE(3,"discountCode","特供码"),
	AD(4,"ad","广告");
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
	OrderItemAttributesEnum (int featureType,String featureName,String description){
		this.featureType=featureType;
		this.featureName=featureName;
		this.description=description;
	}
	
	public OrderItemAttributesEnum getValueByType(int featureType){
		for (OrderItemAttributesEnum enums : values()) {
			if (enums.getFeatureType()==featureType) {
				return enums;
			}
		}
		return null;
	}
	public OrderItemAttributesEnum getValueByName(String featureName){
		for (OrderItemAttributesEnum enums : values()) {
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

