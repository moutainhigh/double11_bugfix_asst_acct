package com.yuwang.pinju.core.order.dao;


import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.member.manager.MemberManager;


/**
 *
 * @author 杜成
 * @date   2011-6-8
 * @version 1.0
 */
public class TestOrderDAO {
	
	@SpringBean("orderDAO")
	private OrderQueryDAO orderDAO;
	
	@SpringBean("memberManager")
	private MemberManager memberManager;
	
	@Test
	public void testInsertOrder() throws DaoException, ManagerException{
		/**
		 * 接口暂时没有实现
		 *//*
		MemberDO  buyer = memberManager.findMember(100000045000000L);
		*//**
		 * 接口暂时没有实现
		 *//*
		MemberDO  seller = memberManager.findMember(100000055000000L);
		
		OrderDO order = new OrderDO();
		//order.setBuyerId(buyer.getMemberId());
		order.setBuyerId(100000045000000L);
		order.setBuyerMeMo("222aaa三大");
		//order.setBuyerNick(buyer.getNickname());
		order.setBuyerNick("贼小气b");
		order.setBuyIp("10.245.11.231");
		//order.setEndTime(endTime)
		//order.setFailReason(failReason);
		order.setIsBuyerRate("0");
		order.setIsSellerRate("0");
		//order.setLogisticsOrderId(100000045L);
		order.setLogisticsState(OrderDO.LOGISTICS_STATE_1);
		Date date = new Date();
		order.setOrderCreationTime(date);
		order.setOrderModifyTime(date);
		order.setOrderState(OrderDO.ORDER_STATE_1);
		order.setOrderType(OrderDO.ORDER_TYPE_2);
		//order.setSellerId(seller.getMemberId());
		order.setSellerId(100000055000000L);
		//order.setSellerMeMo(sellerMeMo);
		//order.setBuyerMeMo(buyerMeMo);
		//order.setSellerNick(seller.getNickname());
		order.setSellerNick("真小气");
		order.setStateTime(date);
		orderDao.insertOrder(order);*/
	}
}
