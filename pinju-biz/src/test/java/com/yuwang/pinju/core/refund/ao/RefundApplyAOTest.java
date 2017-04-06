package com.yuwang.pinju.core.refund.ao;

import java.util.Date;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.domain.refund.RefundDO;

/**
 * 申请退款页面的接口测试
 * 
 * @author shihongbo
 * 
 * @since 2011-6-28
 */
public class RefundApplyAOTest extends BaseTestCase {
	@SpringBean("refundApplyAO")
	private RefundApplyAO refundApplyAO;

	public void testSaveApplyInfo() {
		RefundDO refund = constructRefund();
		refundApplyAO.saveOrUpdateRefund(refund);
	}

	private RefundDO constructRefund() {
		RefundDO refund = new RefundDO();
		refund.setNeedSalesReturn(1);

		refund.setSellerId(1L);
		refund.setSellerNick("卖家");
		refund.setBuyerId(2L);
		refund.setBuyerNick("买家");

		refund.setApplyDate(new Date());
		refund.setApplyMemo("我要退货");
		refund.setApplyReason(1);
		refund.setOrderId(888L);
		refund.setOrderItemId(999L);

		refund.setTradeSum(100L);
		refund.setApplySum(200L);

		return refund;

	}
}
