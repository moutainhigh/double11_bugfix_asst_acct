package com.yuwang.pinju.core.citystation.ao;

import java.util.List;

import com.yuwang.pinju.domain.citystation.CityOrderDO;
import com.yuwang.pinju.domain.citystation.CityOrderQuery;
import com.yuwang.pinju.domain.citystation.CityStationDO;

public interface CityStationAO {
	/**
	 * 查询所有城市分站信息
	 * @return
	 */
	public List<CityStationDO> getAllCityStations();
	
	/**
	 * 根据客户IP查询所属分站
	 * @param remoteIp
	 * @return
	 */
	public CityStationDO getCityStationInfo(String remoteIp);
	
	/**
	 * 根据客户ID查询所属分站
	 * @param remoteIp
	 * @return
	 */
	public CityStationDO getCityStationInfo(Long memberId);
	
	/**
	 * 根据条件查询分站订单详细
	 * @param cityOrderQuery
	 * @return
	 */
	public List<CityOrderDO> getAllCityOrders(CityOrderQuery cityOrderQuery);
	
}
