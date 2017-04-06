package com.yuwang.pinju.core.refund.ao;

public interface SellerRefundLogAO {
	/**
	 * 卖家同意退款，记录操作日志
	 * 
	 * @param refundId 退款id
	 * @return true 记录日志成功
	 */
	public void sellerAgreeRefundApply(Long refundId);
	
	/**
	 * 卖家拒绝退款，记录操作日志
	 * 
	 * @param refundId 退款id
	 * @return true 记录日志成功
	 */
	public void sellerRejectRefundApply(Long refundId);
	
	/**
	 * 卖家确认收货，记录操作日志
	 * 
	 * @param refundId 退款id
	 * @return true 记录日志成功
	 */
	public void confirmReceiveGoods(Long refundId);
	
	/**
	 * 卖家申请客服介入，记录操作日志
	 * 
	 * @param refundId 退款id
	 * @return true 记录日志成功
	 */
	public void sellerApplyCustProcess(Long refundId);
	
	
}
