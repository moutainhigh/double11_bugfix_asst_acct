package com.yuwang.pinju.core.order.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.order.dao.OrderLogisticsDao;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;

public class OrderLogisticsDaoImpl extends BaseDAO implements OrderLogisticsDao {
	
	private final static String NAMESPACE = "TRADE_ORDER_LOGISTICS.";
	
	/**
	 * 查询订单物流信息
	 * 
	 * @return OrderLogisticsDO
	 */
	public OrderLogisticsDO queryOrderLogisticsById(Long orderLogisticsId)
			throws DaoException {
		return (OrderLogisticsDO) super.executeQueryForObject(
				NAMESPACE+"selectOrderLogisticsDOById", orderLogisticsId);
	}

	@Override
	public Long insertOrderLogistics(OrderLogisticsDO orderLogisticsDO)
			throws DaoException {
		return (Long) super.executeInsert(NAMESPACE+"insertOrderLogistics",
				orderLogisticsDO);

	}

	@Override
	public int upOrderLogistics(OrderLogisticsDO orderLogisticsDO)
			throws DaoException {
		return  super.executeUpdate(NAMESPACE+"updateOrderLogistics",orderLogisticsDO);
	}
}
