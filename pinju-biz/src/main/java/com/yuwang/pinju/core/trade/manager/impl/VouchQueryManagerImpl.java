package com.yuwang.pinju.core.trade.manager.impl;

import java.util.Map;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.trade.dao.VouchCreateDAO;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * Created on 2011-9-15
 * @see
 * <p>Discription:担保交易查询实现类</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class VouchQueryManagerImpl extends BaseManager implements VouchQueryManager{
	private VouchCreateDAO vouchCreateDAO; 

	@Override
	public VouchPayDO selectOrderPayByOrderId(Long OrderId)throws ManagerException {
		log.debug("execute selectOrderPayByOrderId");
		try {
			return vouchCreateDAO.selectOrderPayByOrderId(OrderId);
		} catch (DaoException e) {
			log.error("Event=[VouchQueryManagerImpl#selectOrderPayByOrderId]", e);
			throw new ManagerException("Event=[VouchQueryManagerImpl#selectOrderPayByOrderId]", e);
		}
	}
	
	@Override
	public VouchPayDO selectOrderPay(VouchPayDO orderPayDO)throws ManagerException {
		log.debug("execute selectOrderPayByOrderId");
		try {
			return vouchCreateDAO.selectOrderPay(orderPayDO);
		} catch (DaoException e) {
			log.error("Event=[VouchQueryManagerImpl#selectOrderPayByOrderId]", e);
			throw new ManagerException("Event=[VouchQueryManagerImpl#selectOrderPayByOrderId]", e);
		}
	}
	
	
	public void setVouchCreateDAO(VouchCreateDAO vouchCreateDAO) {
		this.vouchCreateDAO = vouchCreateDAO;
	}

	@Override
	public VouchPayDO selectOrderPayByOrderPayId(String orderPayId) throws ManagerException {
		// TODO Auto-generated method stub
		log.debug("execute selectOrderPayByOrderPayId");
		try {
			VouchPayDO orderPayDO = new VouchPayDO();
			orderPayDO.setOrderPayId(orderPayId);
			return vouchCreateDAO.selectOrderPay(orderPayDO);
		} catch (DaoException e) {
			log.error("Event=[VouchQueryManagerImpl#selectOrderPayByOrderPayId]", e);
			throw new ManagerException("Event=[VouchQueryManagerImpl#selectOrderPayByOrderPayId]", e);
		}
	}

	@Override
	public VouchPayDO selectOrderPay(Map map) throws ManagerException {
		log.debug("execute selectOrderPay");
		try {
			return vouchCreateDAO.selectOrderPay(map);
		} catch (DaoException e) {
			log.error("Event=[VouchQueryManagerImpl#selectOrderPay]", e);
			throw new ManagerException("Event=[VouchQueryManagerImpl#selectOrderPay]", e);
		}
	}
	
}
