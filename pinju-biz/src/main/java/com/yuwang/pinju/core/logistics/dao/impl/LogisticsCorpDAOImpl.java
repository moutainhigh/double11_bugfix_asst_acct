package com.yuwang.pinju.core.logistics.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.logistics.dao.LogisticsCorpDAO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;

public class LogisticsCorpDAOImpl extends BaseDAO implements LogisticsCorpDAO {

	private final String LOGISTICS_NAME_SPACE = "TRADE_LOGISTICS_CORP.";
	
	@Override
	public List<LogisticsCorpDO> getLogisticsCorpByStatus(LogisticsCorpDO corpDo) throws DaoException {
		return super.executeQueryForList(LOGISTICS_NAME_SPACE + "getLogisticsCorp",corpDo);
	}

	@Override
	public void addLogisticsCorp(LogisticsCorpDO corpDO) throws DaoException {
		super.executeInsert(LOGISTICS_NAME_SPACE + "insert", corpDO);
	}

	@Override
	public void updateLogisticsCorp(LogisticsCorpDO corpDO) throws DaoException {
		// TODO Auto-generated method stub

	}

	@Override
	public int delLogisticsCorp(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

}
