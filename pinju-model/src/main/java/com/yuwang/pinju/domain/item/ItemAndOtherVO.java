/**
 * 
 */
package com.yuwang.pinju.domain.item;

/**  
 * @Project com.yuwang.pinju.domain.item.pinju-model
 * @Description: TODO
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
 * @date 2011-12-1 下午2:56:23
 * @version V1.0  
 */

public class ItemAndOtherVO extends ItemDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2301251889041761160L;
	/**
	 * 折后价
	 */
	private String discountPrice;
	/**
	 * 购买记录数
	 */
	private Long buyCount;
	
	/**
	 * 运费(元)
	 */
	private String freightStr;

	private String cityName;


	public String getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Long getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Long buyCount) {
		this.buyCount = buyCount;
	}

	

	public String getFreightStr() {
		return freightStr;
	}

	public void setFreightStr(String freightStr) {
		this.freightStr = freightStr;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
