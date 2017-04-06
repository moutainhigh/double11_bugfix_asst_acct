package com.yuwang.pinju.domain.order.query;

import java.util.Date;

import com.yuwang.pinju.domain.Paginator;



/**
 * Created on 2011-9-27
 * @see
 * <p>Discription: openapi订单查询</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OpenOrderQuery  extends Paginator{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3398412464965197619L;
	/**
	 * 订单开始时间
	 */
	private Date start_date;
	/**
	 * 交易结束时间[订单状态为交易成功或交易关闭时的状态修改时间]
	 */
	private Date end_date;
	/**
	 * 订单状态
	 */
	private OrderStateEnum[] orderStateEnum;
	
	/**
	 * 订单状态(反射用)
	 */
	private int[] status;
	
	/**
	 * 卖家会员ID
	 */
	private Long sellerId;

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date startDate) {
		start_date = startDate;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date endDate) {
		end_date = endDate;
	}



	public OrderStateEnum[] getOrderStateEnum() {
		return orderStateEnum;
	}

	public void setOrderStateEnum(OrderStateEnum[] orderStateEnum) {
		this.orderStateEnum = orderStateEnum;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public int[] getStatus() {
		return status;
	}

	public void setStatus(int[] status) {
		this.status = status;
	}
	
	
}

