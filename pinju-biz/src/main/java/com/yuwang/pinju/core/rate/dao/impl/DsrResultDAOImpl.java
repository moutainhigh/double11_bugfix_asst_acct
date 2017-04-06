package com.yuwang.pinju.core.rate.dao.impl;

import java.util.Date;
import java.util.List;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.rate.dao.DsrResultDAO;
import com.yuwang.pinju.domain.rate.DsrResultDO;

public class DsrResultDAOImpl extends BaseDAO implements DsrResultDAO {

	/**
	 * RATE_DSR_RESULT 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE = "DsrResult.";

	@Override
	public DsrResultDO getDsrResult(long id) throws DaoException {
		return (DsrResultDO) super.executeQueryForObject(NAMESPACE + "getDsrResultById", id);
	}

	@Override
	public DsrResultDO saveDsrResult(DsrResultDO dsrResult) throws DaoException {
		Date current = DateUtil.current();
		dsrResult.setGmtCreate(current);
		dsrResult.setGmtModified(current);
		Number id = (Number) super.executeInsert(NAMESPACE + "saveDsrResult", dsrResult);
		dsrResult.setId(id.longValue());
		return dsrResult;
	}

	@Override
	public void saveDsrResults(List<DsrResultDO> dsrResults) throws DaoException {
		super.executeBatchInsert(NAMESPACE + "saveDsrResult", dsrResults, 100);
	}
}
