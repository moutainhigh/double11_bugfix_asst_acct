package com.yuwang.pinju.core.citystation.dao.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.citystation.dao.CityStationDAO;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.citystation.CityStationDO;

public class CityStationDAOImpl extends BaseDAO implements CityStationDAO {

	private static final String CITY_STATION_MYSQL_NAMESPACE = "CityStation_MYSQL.";

	@SuppressWarnings("unchecked")
	@Override
	public List<CityStationDO> selectAllCityStations() throws DaoException {
		return executeQueryForList(CITY_STATION_MYSQL_NAMESPACE
				+ "selectAllCityStation", null);
	}

	@Override
	public CityStationDO selectCityStationByCityName(Map<String, Object> map)
			throws DaoException {
		return (CityStationDO) executeQueryForObject(CITY_STATION_MYSQL_NAMESPACE
				+ "selectCityStationByCityName", map);
	}

	@Override
	public CityStationDO selectCityStationByMemberId(Long memberId)
			throws DaoException {
		return (CityStationDO) executeQueryForObject(CITY_STATION_MYSQL_NAMESPACE
				+ "selectCityStationByMemberId", memberId);
	}

}
