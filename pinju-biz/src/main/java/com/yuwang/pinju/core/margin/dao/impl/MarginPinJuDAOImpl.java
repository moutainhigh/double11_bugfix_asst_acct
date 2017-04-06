package com.yuwang.pinju.core.margin.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.margin.dao.MarginPinJuDAO;
import com.yuwang.pinju.domain.margin.MarginPinJuDO;

public class MarginPinJuDAOImpl extends BaseDAO implements MarginPinJuDAO {

	@Override
	public void addMarginPinJuDO(MarginPinJuDO marginPinJuDO)
			throws DaoException {
		marginPinJuDO.setId((Long)executeInsert("TradeMarginPinJu.insertTradeMarginPinJuRecord",marginPinJuDO));

	}

	@Override
	public MarginPinJuDO getMarginPinJuDOById(Long id) throws DaoException {
		return (MarginPinJuDO)executeQueryForObject("TradeMarginPinJu.getTradeMarginPinJuDOById",id);
	}

	@Override
	public int updateMarginPinJuDO(MarginPinJuDO marginPinJuDO)
			throws DaoException {
		return executeUpdate("TradeMarginPinJu.updateTradeMarginPinJuRecord",marginPinJuDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarginPinJuDO> getMarginPinJuDO() throws DaoException {
		return executeQueryForList("TradeMarginPinJu.getTradeMarginPinJuDOs",null);
	}

}
