package com.yuwang.pinju.core.citystation.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.citystation.dao.CityStationOrderDAO;
import com.yuwang.pinju.core.citystation.manager.CityStationOrderManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.citystation.CityOrderDO;
import com.yuwang.pinju.domain.citystation.CityOrderQuery;

public class CityStationOrderManagerImpl extends BaseManager implements
		CityStationOrderManager {

	private CityStationOrderDAO cityStationOrderDAO;

	public void setCityStationOrderDAO(CityStationOrderDAO cityStationOrderDAO) {
		this.cityStationOrderDAO = cityStationOrderDAO;
	}

	@Override
	public List<CityOrderDO> selectAllCityOrders(CityOrderQuery cityOrderQuery)
			throws ManagerException {
		try {
			return cityStationOrderDAO.selectAllCityOrders(cityOrderQuery);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

}
