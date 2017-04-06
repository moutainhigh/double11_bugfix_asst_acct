package com.yuwang.pinju.core.logistics.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.dao.LogisticsSellerDeliveryDAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsSellerDeliveryManager;
import com.yuwang.pinju.domain.logistics.LogisticsSellerDefaultDO;

public class LogisticsSellerDeliveryManagerImpl implements LogisticsSellerDeliveryManager {

	private LogisticsSellerDeliveryDAO logisticsSellerDeliveryDAO;
	
	public LogisticsSellerDeliveryDAO getLogisticsSellerDeliveryDAO() {
		return logisticsSellerDeliveryDAO;
	}

	public void setLogisticsSellerDeliveryDAO(
			LogisticsSellerDeliveryDAO logisticsSellerDeliveryDAO) {
		this.logisticsSellerDeliveryDAO = logisticsSellerDeliveryDAO;
	}

	@Override
	public List<LogisticsSellerDefaultDO> queryByMemberId(Long sellerId)
			throws ManagerException {
		try {
			return logisticsSellerDeliveryDAO.queryByMemberId(sellerId);
		} catch (DaoException e) {
			throw new ManagerException("trade_logistics_seller_default表根据卖家id查询失败", e);
		}
	}

	@Override
	public int deleteById(Long id) throws ManagerException {
		try {
			return logisticsSellerDeliveryDAO.deleteById(id);
		} catch (DaoException e) {
			throw new ManagerException("trade_logistics_seller_default表根据id删除失败", e);
		}
	}

	@Override
	public void add(LogisticsSellerDefaultDO logistics) throws ManagerException {
		try {
			logisticsSellerDeliveryDAO.add(logistics);
		} catch (DaoException e) {
			throw new ManagerException("trade_logistics_seller_default表新增失败", e);
		}
	}

}
