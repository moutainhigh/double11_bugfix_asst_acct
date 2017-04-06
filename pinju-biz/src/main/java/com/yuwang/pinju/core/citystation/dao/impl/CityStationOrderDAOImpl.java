package com.yuwang.pinju.core.citystation.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.citystation.dao.CityStationOrderDAO;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.citystation.CityOrderDO;
import com.yuwang.pinju.domain.citystation.CityOrderQuery;

public class CityStationOrderDAOImpl extends BaseDAO implements
		CityStationOrderDAO {

	private static final String CITY_STATION_ORACLE_NAMESPACE = "CityStation_ORACLE.";

	@SuppressWarnings("unchecked")
	@Override
	public List<CityOrderDO> selectAllCityOrders(CityOrderQuery cityOrderQuery)
			throws DaoException {
		return executeQueryForList(CITY_STATION_ORACLE_NAMESPACE
					+ "selectAllCityOrder", cityOrderQuery);
	}

}
