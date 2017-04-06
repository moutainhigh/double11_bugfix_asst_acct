package com.yuwang.pinju.core.logistics.ao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.ao.LogisticsSellerDeliveryAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsSellerDeliveryManager;
import com.yuwang.pinju.domain.logistics.LogisticsSellerDefaultDO;

public class LogisticsSellerDeliveryAOImpl implements LogisticsSellerDeliveryAO {

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private LogisticsSellerDeliveryManager logisticsSellerDeliveryManager;
	
	public LogisticsSellerDeliveryManager getLogisticsSellerDeliveryManager() {
		return logisticsSellerDeliveryManager;
	}

	public void setLogisticsSellerDeliveryManager(
			LogisticsSellerDeliveryManager logisticsSellerDeliveryManager) {
		this.logisticsSellerDeliveryManager = logisticsSellerDeliveryManager;
	}

	@Override
	public List<LogisticsSellerDefaultDO> queryByMemberId(Long sellerId) {
		List<LogisticsSellerDefaultDO> list = null;
		try {
			list = logisticsSellerDeliveryManager.queryByMemberId(sellerId);
		} catch (ManagerException e) {
			log.error("trade_logistics_seller_default表根据卖家id查询失败", e);
		}
		return list;
	}

	@Override
	public int deleteById(Long id) {
		int i=0;
		try {
			i = logisticsSellerDeliveryManager.deleteById(id);
		} catch (ManagerException e) {
			log.error("trade_logistics_seller_default表根据id删除失败", e);
		}
		return i;
	}

	@Override
	public void add(LogisticsSellerDefaultDO logistics) {
		try {
			logisticsSellerDeliveryManager.add(logistics);
		} catch (ManagerException e) {
			log.error("trade_logistics_seller_default表新增失败", e);
		}
	}

}
