package com.yuwang.pinju.core.refund.ao;

public interface SellerRefundManageAO {
	/**
	 * 卖家确认收货
	 * 
	 * @param refundId 退款id
	 */
	public void sellerConfirmReceiveGoods(Long refundId);
	
	/**
	 * 卖家拒绝退款申请
	 * 
	 * @param refundId 退款id
	 */
	public void sellerRejectRefundApply(Long refundId);
	
	/**
	 * 卖家申请客服介入
	 */
	public void sellerApplyCustProcess(Long refundId);
	
	/**
	 * 卖家同意退款申请
	 */
	public void sellerAgreeRefundApply(Long refundId);
}
