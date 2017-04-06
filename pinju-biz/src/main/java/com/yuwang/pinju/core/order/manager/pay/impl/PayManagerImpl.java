package com.yuwang.pinju.core.order.manager.pay.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.order.manager.pay.PayManager;
import com.yuwang.pinju.domain.order.OrderDO;

/**
 *
 * @author 杜成
 * @date   2011-6-7
 * @version 1.0
 */
public class PayManagerImpl extends BaseManager implements PayManager {

	@Override
	public Long creationPay(OrderDO orderDO, Long fullPrice,
			Long realPaymentAmount, Long dealAmount, String payAccount,
			String acceptAccount, String payType) {
		// TODO Auto-generated method stub
		return null;
	}



}
