package com.yuwang.pinju.domain.margin;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**  
 * @Project: pinju-model
 * @Discription: [品聚保证金交易流水DO]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-9 下午04:53:02
 * @update 2011-8-9 下午04:53:02
 * @version V1.0  
 */
public class MarginPinjuOrderDO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3106898343461627395L;
	
	private Long id;
	
	private Long invMemberId;//设计会员ID
	
	private String invMemberNick;//涉及会员昵称
	
	private String invMemberPayment;//涉及会员支付帐户
	
	private int operateType;//操作类型：0-充值，1-扣款
	
	private Long amount;//金额
	
	/**
	 * 支付订单编号
	 */
	private Long payOrderId;  

	/**
	 * 订单号
	 */
	private Long orderId;
	
	/**
	 * 外部订单号
	 */
	private String outOrderId;
	
	private Long rightsId;  //维权编号
	
	private Long refundId;  //退款编号
	
	private Date gmtCreate;
	
	private Date gmtModified;

	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return id;
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

	public void setOperateType(int operateType){
		this.operateType = operateType;
	}

	public int getOperateType(){
		return operateType;
	}

	public void setAmount(Long amount){
		this.amount = amount;
	}

	public Long getAmount(){
		return amount;
	}

	public void setRightsId(Long rightsId){
		this.rightsId = rightsId;
	}

	public Long getRightsId(){
		return rightsId;
	}

	public void setRefundId(Long refundId){
		this.refundId = refundId;
	}

	public Long getRefundId(){
		return refundId;
	}

	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtCreate(){
		return gmtCreate;
	}

	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public Date getGmtModified(){
		return gmtModified;
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
