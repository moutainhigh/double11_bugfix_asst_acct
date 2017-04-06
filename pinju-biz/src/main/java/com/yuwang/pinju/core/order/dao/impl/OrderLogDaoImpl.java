package com.yuwang.pinju.core.order.dao.impl;


import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.order.dao.OrderLogDao;
import com.yuwang.pinju.domain.order.OrderLogDO;


public class OrderLogDaoImpl extends BaseDAO implements OrderLogDao {
	private final static String NAMESPACE = "TRADE_ORDER_LOG.";
	
	@Override
	public Long insertOrderLogDO(OrderLogDO logDO) throws DaoException {
		return (Long) super.executeInsert(NAMESPACE + "insertOrderLogDO",
				logDO);
		
	}

}
