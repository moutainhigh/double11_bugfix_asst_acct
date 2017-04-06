package com.yuwang.pinju.domain.rights;

import java.util.Date;
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
public class RightsQuery extends Paginator {

	private static final long serialVersionUID = 2158555417539802095L;

	private Long buyerId;
	
	private Long sellerId;
	
	private List<RightsDO> rightsDOs ;
	
	private OrderItemDO orderItemDO;
	
	private OrderDO orderDO;
	
	private RightsDO rightsDO;
	
	private Long orderId;
	
	private Long rightsId;
	
	private Date beginDate;
	
	private Date endDate;
	
	//维权状态
	private Integer status;
	
	
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getRightsId() {
		return rightsId;
	}

	public void setRightsId(Long rightsId) {
		this.rightsId = rightsId;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setRightsDOs(List<RightsDO> rightsDOs) {
		this.rightsDOs = rightsDOs;
	}

	public List<RightsDO> getRightsDOs() {
		return rightsDOs;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setOrderItemDO(OrderItemDO orderItemDO) {
		this.orderItemDO = orderItemDO;
	}

	public OrderItemDO getOrderItemDO() {
		return orderItemDO;
	}

	public void setRightsDO(RightsDO rightsDO) {
		this.rightsDO = rightsDO;
	}

	public RightsDO getRightsDO() {
		return rightsDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getSellerId() {
		return sellerId;
	}

}
