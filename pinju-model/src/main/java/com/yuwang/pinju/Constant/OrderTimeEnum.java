package com.yuwang.pinju.Constant;



/**
 * Created on 2011-8-29
 * @see
 * <p>Discription: 订单时间枚举</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public enum OrderTimeEnum {
	//物流模式,时间/小时,描述
	No_LOGIS_TIME(6,"240","无需物流自动收货时间"),
	OTHER_LOGIS_TIME(5,"240","自定义物流默认自动收货时间"),
	EMS_TIME(3,"240","EMS默认自动收货时间"),
	EXPRESS_TIME(2,"240","快递默认自动收货时间"),
	POST_TIME(1,"720","平邮默认自动收货时间"),
	//默认关闭时间/小时
	PAY_CLOSE_TIME(101,"72","其它支付默认关闭时间"),
	
	//默认关闭时间/小时   (团购)
	PAY_CLOSE_TIME_T(101,"3","支付默认关闭时间"),
	
	//默认关闭时间/分钟   (限时折扣)
	PAY_CLOSE_TIME_Z(101,"30","支付默认关闭时间"),
	
	//Discount /小时
	PAY_DISCOUNT_TIME(101,"0.5","团购,限时折扣付款时限");
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
	OrderTimeEnum (int featureType,String featureName,String description){
		this.featureType=featureType;
		this.featureName=featureName;
		this.description=description;
	}
	
	public static OrderTimeEnum getValueByType(int featureType){
		for (OrderTimeEnum enums : values()) {
			if (enums.getFeatureType()==featureType) {
				return enums;
			}
		}
		return null;
	}
	public OrderTimeEnum getValueByName(String featureName){
		for (OrderTimeEnum enums : values()) {
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

