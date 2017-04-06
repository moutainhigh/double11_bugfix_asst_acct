package com.yuwang.pinju.core.order.ao;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.Result;

public class TestOrderBusinessAO extends BaseTestCase {

	@SpringBean("orderBusinessAO")
	private OrderBusinessAO orderBusinessAO;
	
	private Result result;
	
	
	/**
	 * 查询所有订单Item
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetOrderItemAll(){
/*		HashMap map = new HashMap();
		result = orderBusinessAO.selectOrderItemList(map);*/
		//Assert.assertNotNull(orderList);
	}
	
	/**
	 * 查询所有订单
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetOrderAll(){
/*		List orderList = orderBusinessAO.getOrderList(null);
		Assert.assertNotNull(orderList);*/
	}
	
	/**
	 * 按条件查询订单
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetOrderList(){
/*		HashMap map = new HashMap();
		map.put("orderId", 10000);
		map.put("buyerNick", "测试测试");
		//map.put("beginDate", "");
		//map.put("endDate", "");
		
		List orderList = orderBusinessAO.getOrderList(map);*/
		//Assert.assertEquals(orderList.size(),1);
	}
}
