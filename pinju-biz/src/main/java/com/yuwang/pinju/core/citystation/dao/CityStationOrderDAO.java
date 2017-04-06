package com.yuwang.pinju.core.citystation.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.citystation.CityOrderDO;
import com.yuwang.pinju.domain.citystation.CityOrderQuery;

public interface CityStationOrderDAO {

	/**
	 * 根据查询条件查询分站订单详细
	 * @param cityOrderQuery
	 * @return
	 * @throws DaoException
	 */
	public List<CityOrderDO> selectAllCityOrders(CityOrderQuery cityOrderQuery) throws DaoException;
}
