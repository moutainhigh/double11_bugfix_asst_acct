package com.yuwang.pinju.domain.citystation;

import java.io.Serializable;
import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 城市分站订单详细
 * 
 * @author qiuhongming
 * 
 */
public class CityOrderDO extends BaseDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6786138840535673876L;

	/**
	 * 统计日期
	 */
	private Date orderDate;

	/**
	 * 广告代码
	 */
	private String adCode;

	/**
	 * 订单总量
	 */
	private Long orderBuyCount;

	/**
	 * 订单总金额
	 */
	private Long orderBuyPrice;

	/**
	 * 付款总量
	 */
	private Long orderPayCount;

	/**
	 * 付款总金额
	 */
	private Long orderPayPrice;

	/**
	 * 交易成功总量
	 */
	private Long orderSucCount;
	
	/**
	 * 交易成功总金额
	 */
	private Long orderSucPrice;

	public CityOrderDO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public Long getOrderBuyCount() {
		return orderBuyCount;
	}

	public void setOrderBuyCount(Long orderBuyCount) {
		this.orderBuyCount = orderBuyCount;
	}

	public Long getOrderBuyPrice() {
		return orderBuyPrice;
	}

	public void setOrderBuyPrice(Long orderBuyPrice) {
		this.orderBuyPrice = orderBuyPrice;
	}

	public Long getOrderPayCount() {
		return orderPayCount;
	}

	public void setOrderPayCount(Long orderPayCount) {
		this.orderPayCount = orderPayCount;
	}

	public Long getOrderPayPrice() {
		return orderPayPrice;
	}

	public void setOrderPayPrice(Long orderPayPrice) {
		this.orderPayPrice = orderPayPrice;
	}

	public Long getOrderSucCount() {
		return orderSucCount;
	}

	public void setOrderSucCount(Long orderSucCount) {
		this.orderSucCount = orderSucCount;
	}

	public Long getOrderSucPrice() {
		return orderSucPrice;
	}

	public void setOrderSucPrice(Long orderSucPrice) {
		this.orderSucPrice = orderSucPrice;
	}

}
