package com.yuwang.pinju.core.citystation.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.citystation.CityOrderDO;
import com.yuwang.pinju.domain.citystation.CityOrderQuery;

public interface CityStationOrderManager {

	/**
	 * 根据条件查询分站订单详细
	 * @param cityOrderQuery
	 * @return
	 * @throws ManagerException
	 */
	List<CityOrderDO> selectAllCityOrders(CityOrderQuery cityOrderQuery) throws ManagerException;
}
