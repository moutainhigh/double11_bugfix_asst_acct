package com.yuwang.pinju.domain.rights;

import java.util.List;

import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;

/**  
 * @Project: pinju-model
 * @Description: 消保维权Query
 * @author 石兴 shixing@zba.com
 * @date 2011-7-2 上午11:59:59
 * @update 2011-7-2 上午11:59:59
 * @version V1.0  
 */
public class RightsMsgQuery extends Paginator {

	private static final long serialVersionUID = 2158555417539802095L;

	private Long voucherId; 
	
	private Long buyerId;
	
	private RightsDO rightsDO;
	
	private OrderItemDO orderItemDO;
	
	private OrderDO orderDO;
	
	private RightsLogisticsDO rightsLogisticsDO;
	
	private List<RightsMessageDO> rightsMessageDOs ;
	
	/**
	 * 子订单优惠金额
	 */
	private String subOrderDiscPrice;
	
	public String getSubOrderDiscPrice() {
		return subOrderDiscPrice;
	}

	public void setSubOrderDiscPrice(String subOrderDiscPrice) {
		this.subOrderDiscPrice = subOrderDiscPrice;
	}

	public void setRightsMessageDOs(List<RightsMessageDO> rightsMessageDOs) {
		this.rightsMessageDOs = rightsMessageDOs;
	}

	public List<RightsMessageDO> getRightsMessageDOs() {
		return rightsMessageDOs;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setRightsDO(RightsDO rightsDO) {
		this.rightsDO = rightsDO;
	}

	public RightsDO getRightsDO() {
		return rightsDO;
	}

	public void setOrderItemDO(OrderItemDO orderItemDO) {
		this.orderItemDO = orderItemDO;
	}

	public OrderItemDO getOrderItemDO() {
		return orderItemDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setRightsLogisticsDO(RightsLogisticsDO rightsLogisticsDO){
		this.rightsLogisticsDO = rightsLogisticsDO;
	}

	public RightsLogisticsDO getRightsLogisticsDO(){
		return rightsLogisticsDO;
	}
	
	
}
