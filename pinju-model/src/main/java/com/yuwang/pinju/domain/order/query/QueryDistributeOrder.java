package com.yuwang.pinju.domain.order.query;

import com.yuwang.pinju.domain.Paginator;

/**
 * Created on 2011-7-28
 * @see
 * <p>Discription: 分销分页查询</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class QueryDistributeOrder extends Paginator{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7444326059592082625L;
	//供应商编号
	private Long supplierId;
	//分销商编号
	private Long channelIds;
	//订单状态
	private Integer orderState;
	
	private Integer month;
	
	private Integer type;
	
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getChannelIds() {
		return channelIds;
	}
	public void setChannelIds(Long channelIds) {
		this.channelIds = channelIds;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}

