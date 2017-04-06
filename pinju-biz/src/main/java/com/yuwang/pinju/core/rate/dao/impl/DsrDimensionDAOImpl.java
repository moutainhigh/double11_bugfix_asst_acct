package com.yuwang.pinju.core.rate.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.rate.dao.DsrDimensionDAO;
import com.yuwang.pinju.domain.rate.DsrDimensionDO;

public class DsrDimensionDAOImpl extends BaseDAO implements DsrDimensionDAO {

	/**
	 * RATE_DSR_DIMENSION 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE = "DsrDimension.";

	@SuppressWarnings("unchecked")
	@Override
	public List<DsrDimensionDO> getValidDsrDimensions() throws DaoException {
		return (List<DsrDimensionDO>) executeQueryForList(NAMESPACE + "getValidDsrDimensions", null);
	}
}
