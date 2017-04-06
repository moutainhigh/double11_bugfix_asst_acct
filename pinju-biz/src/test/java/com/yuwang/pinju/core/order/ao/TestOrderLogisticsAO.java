package com.yuwang.pinju.core.order.ao;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.Result;


public class TestOrderLogisticsAO extends BaseTestCase{

	@SpringBean("orderBusinessAO")
	private OrderBusinessAO orderBusinessAO;
	
	private Result result;
	
	/**
	 * 查询订单物流信息
	 * @return OrderLogisticsDO
	 */
	public void testQueryOrderLogisticsById(){
/*		result = orderBusinessAO.queryOrderLogisticsById(1L);*/
	}
}
