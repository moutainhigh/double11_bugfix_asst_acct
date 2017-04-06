package com.yuwang.pinju.core.refund.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.refund.RefundDO;

/**
 * 卖家超时处理
 * @author shihongbo
 * @since   2011-11-04
 */
public interface SellerRefundTimeoutAO {
	/**
	 * 检查买家申请退款，卖家响应是否超时
	 * 
	 * @param refundDO 退款
	 * @return true 卖家超时
	 */
	public Boolean isSellerReplyTimeout(RefundDO refundDO);
	
	/**
	 * 买家申请退款，卖家响应超时的处理
	 * 
	 * @param refundId 退款id
	 * @param isSeller 为true表示卖家，否则表示买家
	 * @return result
	 */
	public Result sellerReplyTimeout(Long refundId, Boolean isSeller);
	
	/**
	 * 检查买家退货，卖家确认收货是否超时
	 * 
	 * @param refundId 退款id
	 * @return true 卖家超时
	 */
	public Boolean isSellerConfirmGoodsTimeout(Long refundId);
	
	/**
	 * 买家退货，卖家确认收货超时的处理
	 * 
	 * @param refundId 退款id
	 * @param isSeller 为true表示卖家，否则表示买家
	 * @return result
	 */
	public Result sellerConfirmGoodsTimeout(Long refundId, Boolean isSeller);
}
