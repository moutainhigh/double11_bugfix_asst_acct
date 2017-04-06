package com.yuwang.pinju.core.order.ao;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.core.common.Result;


/**
 *
 * @author 杜成
 * @date   2011-6-8
 * @version 1.0
 */
public class TestOrderCustomerAO {
	
	@SpringBean("orderCreationAO")
	private OrderCreationAO orderCreationAO;
	
	private Result result;
	/**
	 * 生成商品结算页面测试
	 * 测试状态1  : 参数正确
	 * 数据：
	 * 	Long [] itemId = new Long[]{new Long(21),new Long(22)};
		Long [] num = new Long[]{new Long(2),new Long(1)};
	 * 测试结果
	 * 2011-6-8 通过
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCreationSettling(){
		/**
		 * 测试状态1  : 参数正确
		 * ResultCode 返回CREATION_SETTLING_3
		 */
/*		Long [] itemId = new Long[]{21L,22L};
		Long [] num = new Long[]{2L,6L};
		Long [] price = new Long[]{3345L,2345L};
		Assert.assertEquals(orderCustomerAO.creationSettling(itemId, num, price,100000055100000L).getResultCode(),OrderConstant.OPERATESUCCED);
	
		*//**
		 * 测试状态1  : 参数不正确  价格或库存变更
		 * ResultCode 返回 CREATION_SETTLING_2
		 * errorItemIdList 长度为1
		 *//*
		itemId = new Long[]{21L,22L};
		num = new Long[]{1L,6L};
		price = new Long[]{3345L,235L};
		result =orderCustomerAO.creationSettling(itemId, num, price,100000055100000L);
		Assert.assertEquals(result.getResultCode(),OrderConstant.CHECKERROR);
		Assert.assertEquals(((List<ItemDO>)result.getModel("errorItemIdList")).size(),1);*/
	
	}
	
	/**
	 * 生成订单
	 * 
	 */
	@Test
	public void testCreationOrder(){
		/**
		 * 测试状态1： 参数正确
		 * ResultCode 返回CREATION_ORDER_3
		 */
/*		Long [] itemId = new Long[]{21L,22L};
		Long [] num = new Long[]{1L,6L};
		Long [] price = new Long[]{3345L,2345L};
		Long buyerId = 100000045000000L;
		Map <Long,String> map = new HashMap<Long,String>();
		map.put(100000070000000L, "111不好用");
		map.put(100000055000000L, "飞机很好用");
		result = orderCustomerAO.creationOrder(itemId, num, price, buyerId,"131.224.12.231",map);
		Assert.assertEquals(result.getResultCode(),OrderConstant.OPERATESUCCED);*/
	}
}
