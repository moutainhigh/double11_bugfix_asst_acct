package com.yuwang.pinju.domain.margin;


import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 
 * @Project:pinju-model
 * <p>Discription:[卖家保证金流水操作记录DO]</p>
 * @author: lixingquan lixingquan@zba.com
 * @date:2011-8-11
 * @update:2011-8-11
 */
public class MarginSellerOrderDO extends BaseDO {


	private static final long serialVersionUID = -3912598100451595813L;
	
	/**
	 * 编号
	 */
	private Long id;
	
	/**
	 * 会员保证金账户ID
	 */
	private Long memberId;  
	
	/**
	 * 会员保证金账户昵称
	 */
	private String memberNick;  
	
	/**
	 * 涉及会员Id
	 */
	private Long invMemberId;  
	
	/**
	 * 涉及会员昵称
	 */
	private String invMemberNick;  
	
	/**
	 * 涉及会员支付账户
	 */
	private String invMemberPayment;  
	
	/**
	 * 金额
	 */
	private Long amount;  
	
	/**
	 * 支付订单编号
	 */
	private Long payOrderId;  
	
	/**
	 * 操作类型(充值(+)、扣款(-,暂时从品聚账户扣))
	 */
	private Integer operateType;  

	/**
	 * 订单号
	 */
	private Long orderId;
	
	/**
	 * 外部订单号
	 */
	private String outOrderId;
	
	/**
	 * 维权编号
	 */
	private Long rightsId;  
	
	/**
	 * 退款编号
	 */
	private Long refundId;  
	
	private Date gmtCreate;
	
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public Long getInvMemberId() {
		return invMemberId;
	}

	public void setInvMemberId(Long invMemberId) {
		this.invMemberId = invMemberId;
	}

	public String getInvMemberNick() {
		return invMemberNick;
	}

	public void setInvMemberNick(String invMemberNick) {
		this.invMemberNick = invMemberNick;
	}

	public String getInvMemberPayment() {
		return invMemberPayment;
	}

	public void setInvMemberPayment(String invMemberPayment) {
		this.invMemberPayment = invMemberPayment;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Long getRightsId() {
		return rightsId;
	}

	public void setRightsId(Long rightsId) {
		this.rightsId = rightsId;
	}

	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public void setPayOrderId(Long payOrderId) {
		this.payOrderId = payOrderId;
	}

	public Long getPayOrderId() {
		return payOrderId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

}
