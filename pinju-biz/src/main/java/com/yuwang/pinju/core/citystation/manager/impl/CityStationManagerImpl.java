package com.yuwang.pinju.core.citystation.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.citystation.dao.CityStationDAO;
import com.yuwang.pinju.core.citystation.manager.CityStationManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.citystation.CityStationDO;

public class CityStationManagerImpl extends BaseManager implements
		CityStationManager {
	
	private CityStationDAO cityStationDAO;

	public void setCityStationDAO(CityStationDAO cityStationDAO) {
		this.cityStationDAO = cityStationDAO;
	}

	@Override
	public List<CityStationDO> selectAllCityStations() throws ManagerException {
		try {
			return cityStationDAO.selectAllCityStations();
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public CityStationDO selectCityStationByCityName(String cityName)
			throws ManagerException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cityName", cityName);
			return cityStationDAO.selectCityStationByCityName(map);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public CityStationDO selectCityStationByMemberId(Long memberId)
			throws ManagerException {
		try {
			return cityStationDAO.selectCityStationByMemberId(memberId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
}
