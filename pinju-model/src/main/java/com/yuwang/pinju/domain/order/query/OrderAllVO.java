package com.yuwang.pinju.domain.order.query;

import java.util.List;

import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;

public class OrderAllVO {
	private OrderDO orderDO;
	
	private List<OrderItemDO> orderItemList;
	
	private OrderLogisticsDO logisticsDO;
	
	private LogisticsCorpDO logisticsCorpDO;
	
	private Integer postPay = 1;  //1：卖家可修改价格   0：卖家不可修改价格

	

	public Integer getPostPay() {
		return postPay;
	}

	public void setPostPay(Integer postPay) {
		this.postPay = postPay;
	}

	public List<OrderItemDO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}

	public OrderLogisticsDO getLogisticsDO() {
		return logisticsDO;
	}

	public void setLogisticsDO(OrderLogisticsDO logisticsDO) {
		this.logisticsDO = logisticsDO;
	}

	public LogisticsCorpDO getLogisticsCorpDO() {
		return logisticsCorpDO;
	}

	public void setLogisticsCorpDO(LogisticsCorpDO logisticsCorpDO) {
		this.logisticsCorpDO = logisticsCorpDO;
	}
}
