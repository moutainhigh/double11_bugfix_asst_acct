package com.yuwang.pinju.domain.servicefee;

/**
 * 平台服务费返回返回结果
 * 
 * @project pinju-model
 * @author liuweiguo liuweiguo@zba.com
 * @date 2011-9-16下午1:29:10
 * @version V1.0
 * 
 */
public class ServiceFeeResultDO {
	/**
	 * 平台收取服务费
	 */
	private Long serviceFee;
	// 平台服务费率(乘以100)
	private Long serviceFeeRate;
	// 店铺服务费倍率(乘以100)
	private Long shopRate;

	public Long getServiceFee() {
		if(serviceFee==null)
			return 0L;
		return serviceFee;
	}

	public void setServiceFee(Long serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Long getServiceFeeRate() {
		if(serviceFeeRate==null)
			return 0L;
		return serviceFeeRate;
	}

	public void setServiceFeeRate(Long serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	public Long getShopRate() {
		if(shopRate==null)
			return 0L;
		return shopRate;
	}

	public void setShopRate(Long shopRate) {
		this.shopRate = shopRate;
	}

}
