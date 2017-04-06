package com.yuwang.pinju.domain.rights;

import java.util.List;

import com.yuwang.pinju.domain.order.OrderItemDO;

public class RightsSellerQuery extends RightsMsgQuery{
	
	private static final long serialVersionUID = -4820259522570723363L;

	private Long voucherId; 
	
	private Long buyerId;
	
	private RightsDO rightsDO;
	/**
	 * 子订单DO
	 */
	private OrderItemDO orderItemDO;
	
	/**
	 * 退货记录DO
	 */
	private RightsLogisticsDO rightsLogisticsDO;
	/**
	 * 留言信息列表
	 */
	private List<RightsMessageDO> rightsMessageDOs ;
	
	private String orderPostPrice;//运费
	
	private String realPayMoney;//实付款
	private String itemTotalPrice;//商品总价
	private String orderTotalPrice;//订单总计
	
	
	public String getRealPayMoney() {
		return realPayMoney;
	}

	public void setRealPayMoney(String realPayMoney) {
		this.realPayMoney = realPayMoney;
	}

	public String getItemTotalPrice() {
		return itemTotalPrice;
	}

	public void setItemTotalPrice(String itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public RightsDO getRightsDO() {
		return rightsDO;
	}

	public void setRightsDO(RightsDO rightsDO) {
		this.rightsDO = rightsDO;
	}

	public OrderItemDO getOrderItemDO() {
		return orderItemDO;
	}

	public void setOrderItemDO(OrderItemDO orderItemDO) {
		this.orderItemDO = orderItemDO;
	}

	public RightsLogisticsDO getRightsLogisticsDO() {
		return rightsLogisticsDO;
	}

	public void setRightsLogisticsDO(RightsLogisticsDO rightsLogisticsDO) {
		this.rightsLogisticsDO = rightsLogisticsDO;
	}

	public List<RightsMessageDO> getRightsMessageDOs() {
		return rightsMessageDOs;
	}

	public void setRightsMessageDOs(List<RightsMessageDO> rightsMessageDOs) {
		this.rightsMessageDOs = rightsMessageDOs;
	}

	public String getOrderPostPrice() {
		return orderPostPrice;
	}

	public void setOrderPostPrice(String orderPostPrice) {
		this.orderPostPrice = orderPostPrice;
	}
	
	
	
}
