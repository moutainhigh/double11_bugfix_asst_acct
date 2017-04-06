package com.yuwang.pinju.core.refund.ao;

public interface BuyerRefundLogAO {
	/**
	 * 买家申请客服介入，记录操作日志
	 * 
	 * @param refundId 退款id
	 * @return true 记录日志成功
	 */
	public void buyerApplyCustProcess(Long refundId);
}
