package com.yuwang.pinju.core.refund.ao.impl;

import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.refund.ao.SellerRefundLogAO;

public class SellerRefundLogAOImpl implements SellerRefundLogAO{
	
	private MemberAsstLog memberAsstLog;
	
	/**
	 * 卖家同意退款，记录操作日志
	 * 
	 * @param refundId 退款id
	 * @return true 记录日志成功
	 */
	public void sellerAgreeRefundApply(Long refundId){
		log("同意 " + refundId + " 退款申请");
	}
	
	/**
	 * 卖家拒绝退款，记录操作日志
	 * 
	 * @param refundId 退款id
	 * @return true 记录日志成功
	 */
	public void sellerRejectRefundApply(Long refundId){
		log("拒绝 " + refundId + " 退款申请");
	}
	
	/**
	 * 卖家确认收货，记录操作日志
	 * 
	 * @param refundId 退款id
	 * @return true 记录日志成功
	 */
	public void confirmReceiveGoods(Long refundId){
		log("确认收到 " + refundId + " 退款退还货物");
	}
	
	/**
	 * 卖家申请客服介入，记录操作日志
	 * 
	 * @param refundId 退款id
	 * @return true 记录日志成功
	 */
	public void sellerApplyCustProcess(Long refundId){
		log("申请 " + refundId + " 退款客服介入");
	}
	
	private void log(String message){
		memberAsstLog.log("crm", "refund", message);
	}
	
	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
}
