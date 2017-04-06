package com.yuwang.pinju.core.refund.manager.impl;

import java.util.Date;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.domain.refund.RefundDO;

/**退款管理接口测试
 * 
 * @author shihongbo
 * @date 2011-6-28
 * @version 1.0
 */
public class RefundManagerTest extends BaseTestCase {
	@SpringBean("refundManager")
	private RefundManager refundManager;
	
	private RefundDO refund;
	
    /**
     * 测试增退款信息
     */
    public void testPersistRefund()throws Exception{
		refund = constructRefund();
		refundManager.persist(refund);
    }
	
    /**
     * 构建一个退款信息
     * 
     * @return 退款信息
     */
	private RefundDO constructRefund(){
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
