package com.yuwang.pinju.core.refund.ao;

import com.yuwang.pinju.core.common.ManagerException;


public interface MaxRefundMoneyAO {
	/**
	 * 当前子订单可以申请退款的最大值
	 * 
	 * @param orderId
	 * @param orderItemId
	 * 
	 * @return 可以申请退款的最大值，单位为分
	 */
	public Long getMaxRefundMoney(Long orderId, Long orderItemId)throws ManagerException;
}
