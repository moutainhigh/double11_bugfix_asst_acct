package com.yuwang.pinju.core.logistics.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.logistics.dao.LogisticsSellerDeliveryDAO;
import com.yuwang.pinju.domain.logistics.LogisticsSellerDefaultDO;

public class LogisticsSellerDeliveryDAOImpl extends BaseDAO implements LogisticsSellerDeliveryDAO {

	private final static String NAMESPACE = "trade_logistics_seller_default.";
	
	@Override
	public List<LogisticsSellerDefaultDO> queryByMemberId(Long sellerId) throws DaoException {
		List<LogisticsSellerDefaultDO> list = super.executeQueryForList(NAMESPACE + "queryByMemberId",sellerId);
		return list;
	}

	@Override
	public int deleteById(Long id) throws DaoException {
		return super.executeUpdate(NAMESPACE + "deleteDefaultById", id);
	}

	@Override
	public void add(LogisticsSellerDefaultDO logistics) throws DaoException {
		super.executeInsert(NAMESPACE + "insertDefault", logistics);
	}

}
