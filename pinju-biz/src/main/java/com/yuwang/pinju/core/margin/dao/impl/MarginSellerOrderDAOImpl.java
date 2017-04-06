package com.yuwang.pinju.core.margin.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.margin.dao.MarginSellerOrderDAO;
import com.yuwang.pinju.domain.margin.MarginSellerOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginSellerOrderQuery;


public class MarginSellerOrderDAOImpl extends BaseDAO implements MarginSellerOrderDAO{

	@Override
	public MarginSellerOrderDO getMarginSellerOrderDOById(Long id)
			throws DaoException {
		return (MarginSellerOrderDO)executeQueryForObject("TradeMarginSellerOrder.getMarginSellerOrderDOById",id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarginSellerOrderDO> getMarginSellerOrderDOs(
			MarginSellerOrderQuery marginSellerOrderQuery) throws DaoException {
		marginSellerOrderQuery.setItems(getMarginSellerOrderDOsCount(marginSellerOrderQuery));
		return (List<MarginSellerOrderDO>)executeQueryForList("TradeMarginSellerOrder.getMarginSellerOrderDOs",marginSellerOrderQuery);
	}

	@Override
	public Integer getMarginSellerOrderDOsCount(MarginSellerOrderQuery marginSellerOrderQuery)
			throws DaoException {
		return (Integer)executeQueryForObject("TradeMarginSellerOrder.getMarginSellerOrderDOsCount",marginSellerOrderQuery);
	}

	@Override
	public void insertMarginSellerOrderRecord(
			MarginSellerOrderDO marginSellerOrderDO) throws DaoException {
		marginSellerOrderDO.setId((Long)executeInsert("TradeMarginSellerOrder.insertMarginSellerOrderRecord",marginSellerOrderDO));
		
	}

	@Override
	public int updateMarginSellerOrderRecord(
			MarginSellerOrderDO marginSellerOrderDO) throws DaoException {
		return executeUpdate("TradeMarginSellerOrder.updateMarginSellerOrderRecord",marginSellerOrderDO);
	}

}
