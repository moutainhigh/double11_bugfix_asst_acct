package com.yuwang.pinju.Constant;



/**
 * Created on 2011-8-29
 * @see
 * <p>Discription: 担保交易支付表分账标记</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public enum VouchPayAttributesEnum {
	CHANNEL_SPLIT(1,"channelSplit","channelSplit:分销商账号,分账金额"),
	PLATFORM_SPLIT(1,"platformSplit","platformSplit:平台账号,平台分账金额"),
	SELLER_SPLIT(1,"sellerSplit","sellerSplit:卖家账号,卖家分账金额");
	/**
	 * 类型
	 */
	private int featureType;
	/**
	 * 名称(数据库featrues存储名)
	 */
	private String featureName;
	/**
	 * 存储数据结构描叙
	 */
	private String description;
	/**
	 * 初始化
	 * @param featureType
	 * @param featureName
	 * @param description
	 */
	VouchPayAttributesEnum (int featureType,String featureName,String description){
		this.featureType=featureType;
		this.featureName=featureName;
		this.description=description;
	}
	
	public VouchPayAttributesEnum getValueByType(int featureType){
		for (VouchPayAttributesEnum enums : values()) {
			if (enums.getFeatureType()==featureType) {
				return enums;
			}
		}
		return null;
	}
	public VouchPayAttributesEnum getValueByName(String featureName){
		for (VouchPayAttributesEnum enums : values()) {
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

