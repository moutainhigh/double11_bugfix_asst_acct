package com.yuwang.pinju.core.citystation.dao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.citystation.CityStationDO;

public interface CityStationDAO {
	/**
	 * 查询所有城市分站信息
	 * @return
	 * @throws DaoException
	 */
	public List<CityStationDO> selectAllCityStations() throws DaoException;
	
	/**
	 * 根据城市名称查询城市分站信息
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public CityStationDO selectCityStationByCityName(Map<String, Object> map) throws DaoException;
	
	/**
	 * 根据会员ID查询城市分站信息
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public CityStationDO selectCityStationByMemberId(Long memberId) throws DaoException;
	
}
