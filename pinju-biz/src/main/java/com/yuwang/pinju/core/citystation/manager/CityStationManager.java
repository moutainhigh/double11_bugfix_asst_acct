package com.yuwang.pinju.core.citystation.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.citystation.CityStationDO;

public interface CityStationManager {

	/**
	 * 查询所有城市分站信息
	 * @return
	 * @throws ManagerException
	 */
	List<CityStationDO> selectAllCityStations() throws ManagerException;

	/**
	 * 根据城市名称查询分站信息
	 * @param cityName
	 * @return
	 * @throws ManagerException
	 */
	CityStationDO selectCityStationByCityName(String cityName) throws ManagerException;
	
	/**
	 * 根据会员ID查询分站信息
	 * @param cityName
	 * @return
	 * @throws ManagerException
	 */
	CityStationDO selectCityStationByMemberId(Long memberId) throws ManagerException;

}
