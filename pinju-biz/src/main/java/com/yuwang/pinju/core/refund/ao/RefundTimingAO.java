package com.yuwang.pinju.core.refund.ao;


/**
 * 退款定时
 * @author shihongbo
 * @since   2011-6-24
 * @version 1.0
 */
public interface RefundTimingAO {
	/**
	 * 卖家拒绝退款，买家5天没反应
	 */
	public void buyerNoReply();
	
	/**
	 * 买家发起退款，卖家5天没反应
	 * 
	 */
	public void sellerNoReply();
	
	/**
	 * 卖家同意，买家不发货
	 * 
	 * @param refundId 退款id
	 */
	public void buyerNoReturnGoods();
	
	/**
	 * 卖家不确认收货 -- 平邮
	 * 
	 * @param refundId 退款id
	 */
	public void sellerNotConfirmPost();
	
	/**
	 * 卖家不确认收货 -- 快递
	 * 
	 * @param refundId 退款id
	 */
	public void sellerNotConfirmExpress();
	
}
