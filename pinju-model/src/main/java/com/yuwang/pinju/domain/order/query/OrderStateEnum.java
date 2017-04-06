package com.yuwang.pinju.domain.order.query;
/**
 * Created on 2011-8-29
 * @see
 * <p>Discription: 订单状态枚举</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public enum OrderStateEnum {
		ORDER_STATE_UNPAID(1,1,"等待买家付款"),
		ORDER_STATE_UNFILLED(2,2,"等待卖家发货"),
		ORDER_STATE_UNRECOGNIZED(3,3,"卖家已发货"),
		ORDER_STATE_SUCCESS(5,5,"交易成功"),
		ORDER_STATE_CLOSE(6,6,"交易关闭");

		
		/**
		 * 类型
		 */
		private Integer featureType;
		/**
		 * 值
		 */
		private Integer featureName;
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
		OrderStateEnum (int featureType,int featureName,String description){
			this.featureType=featureType;
			this.featureName=featureName;
			this.description=description;
		}
		
		public static OrderStateEnum getValueByType(int featureType){
			for (OrderStateEnum enums : values()) {
				if (enums.getFeatureType()==featureType) {
					return enums;
				}
			}
			return null;
		}
		public  static OrderStateEnum getValueByName(String featureName){
			for (OrderStateEnum enums : values()) {
				//不区分大小写返回
				if (enums.getFeatureName().equals(featureName)) {
					return enums;
				}
			}
			return null;
		}

		/**
		 * @return the featureType
		 */
		public Integer getFeatureType() {
			return featureType;
		}

		/**
		 * @return the featureName
		 */
		public Integer getFeatureName() {
			return featureName;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		
	
}

