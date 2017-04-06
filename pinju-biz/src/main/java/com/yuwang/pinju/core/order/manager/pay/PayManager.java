package com.yuwang.pinju.core.order.manager.pay;

import com.yuwang.pinju.domain.order.OrderDO;


/**
 * <p>支付客户管理</p>
 * @author 杜成
 * @date   2011-6-4
 * @version 1.0
 */
public interface PayManager {
	/**
	 * <p>支付记录生成</p>
	 * @param orderDO 主订单
	 * @param fullPrice	订单总价
	 * @param realPaymentAmount 实际支付金额无优惠的话与price相同
	 * @param dealAmount 手续费 无的话默认为0
	 * @param payAccount 买家第3方支付账户
	 * @param acceptAccount 卖家第3方支付账户
	 * @param payType 支付机构
	 * @return 成功返回内部支付生成编号
	 */
	public Long creationPay(OrderDO orderDO,Long fullPrice,Long realPaymentAmount,Long dealAmount,String payAccount,String acceptAccount,String payType);
}
