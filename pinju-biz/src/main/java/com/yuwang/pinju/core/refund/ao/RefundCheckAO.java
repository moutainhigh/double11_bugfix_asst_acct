package com.yuwang.pinju.core.refund.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.refund.RefundDO;

public interface RefundCheckAO {
	
	/**
	 * 是否需要执行平台退款
	 * 
	 * @param refundId 退款id
	 * @return true 全额退款，并且所有退款为协议达成状态
	 */
	public Result needPlatRefund(Long refundId);
	
	
	/**
	 * <p>Description: 根据orderId判断是否是最后一个退款请求</p>
	 * @param orderId
	 * @param refundDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean isLastRefundOrder(Long orderId,RefundDO refundDO);
	
	/**
	 * <p>Description: 获取支付给卖家的金额</p>
	 * @param orderId
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-25
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public long getNeedPaySellerSum(Long orderId);
	
}
