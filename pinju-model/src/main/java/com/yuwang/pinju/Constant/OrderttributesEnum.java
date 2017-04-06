package com.yuwang.pinju.Constant;



/**
 * Created on 2011-8-29
 * @see
 * <p>Discription: 主订单标记</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public enum OrderttributesEnum {
	IS_POST_PAY(1,"isPostPay","是否有跳转收银台"),
	IS_COUPON(2,"isCoupon","是否有使用优惠券");
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
	OrderttributesEnum (int featureType,String featureName,String description){
		this.featureType=featureType;
		this.featureName=featureName;
		this.description=description;
	}
	
	public OrderttributesEnum getValueByType(int featureType){
		for (OrderttributesEnum enums : values()) {
			if (enums.getFeatureType()==featureType) {
				return enums;
			}
		}
		return null;
	}
	public OrderttributesEnum getValueByName(String featureName){
		for (OrderttributesEnum enums : values()) {
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

