package com.yuwang.pinju.domain.trade;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * @Project: pinju-model
 * @Description: 盛付通返回支付参数DO
 * @author 石兴 shixing@zba.com
 * @date 2011-8-12 下午03:30:36
 * @update 2011-8-12 下午03:30:36
 * @version V1.0
 * @update [2011-8-15 下午01:30:36] [杜成]
 * @Description: 参数修改
 */
public class DirectPayReceiveParamDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1871117035653090450L;

	/**
	 * 业务类型(产品编号)
	 */
	private Integer productNo;
	/**
	 * 订单金额
	 */
	private Long amount;
	/**
	 * 支付金额
	 */
	private Long payAmount;
	/**
	 * 支付订单编号payOrderId
	 */
	private Long orderNo;
	/**
	 * 外部订单号
	 */
	private String serialno;
	/**
	 * 第3方支付时间
	 */
	private Date payTime;
	
	/**
	 * 订单结束时间
	 */
	private Date endTime = new Date();
	
	/**
	 * 返回报文
	 */
	private String RevDetail;
	
	public Integer getProductNo() {
		return productNo;
	}

	public void setProductNo(Integer productNo) {
		this.productNo = productNo;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRevDetail() {
		return RevDetail;
	}

	public void setRevDetail(String revDetail) {
		RevDetail = revDetail;
	}

}
