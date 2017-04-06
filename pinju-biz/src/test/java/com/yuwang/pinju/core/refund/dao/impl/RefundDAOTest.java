package com.yuwang.pinju.core.refund.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;
import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.refund.dao.RefundDAO;
import com.yuwang.pinju.domain.refund.RefundDO;

/**
 * 退款信息接口测试
 * 
 * @author shihongbo
 *
 * @since 2011-6-24
 */
public class RefundDAOTest extends BaseTestCase
{
	
    @SpringBean("refundDAO")
    private RefundDAO refundDAO;
    
    RefundDO refundDO;
    
    /**
     * 测试新增申请退款
     */
    public void testPersistRefund()throws Exception{
    	refundDO = constructRefund();
    	
    	refundDAO.persist(refundDO);
    }
    
    /**
     * 测试新增申请退款
     */
    public void testLoadRefund()throws Exception{

    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("orderId", 888);
    	map.put("sellerId", 1);
    	
    	List<RefundDO> list = refundDAO.loadRefund(map);
    	
    	//System.out.println("==============================");
    	
    	for(RefundDO refundDO:list){
    		//System.out.println("" + refundDO.getId() + refundDO.getOrderId() + refundDO.getBuyerNick());
    	}

    }
    
    /**
     * 测试新增申请退款
     */
    public void testUpdatefund()throws Exception{

    	RefundDO refund = refundDAO.loadRefund(17L);
    	refund.setApplyMemo("update memo");

    	refundDAO.updateRefundApplyInfo(refund);
    }
    
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
