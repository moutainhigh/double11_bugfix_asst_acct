package com.yuwang.pinju.core.order.manager.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.order.dao.OrderLogDao;
import com.yuwang.pinju.core.order.manager.OrderLogManager;
import com.yuwang.pinju.domain.order.OrderLogDO;

/**
 *
 * @author 杜成
 * @date   2011-6-7
 * @version 1.0
 */
public class OrderLogManagerImpl extends BaseManager implements OrderLogManager {

	private OrderLogDao orderLogDao;
	
	public void setOrderLogDao(OrderLogDao orderLogDao) {
		this.orderLogDao = orderLogDao;
	}

	@Override
	public long createOrderLog() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long createAcceptPayLog() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long createSendPayLog() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long createOrderLog(OrderLogDO orderLogDO) throws ManagerException {
		try {
			return orderLogDao.insertOrderLogDO(orderLogDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderLogManagerImpl#createOrderLog]  新增日志记录错误:",e);
		}
	}



}
