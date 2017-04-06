package com.yuwang.pinju.core.citystation.ao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.citystation.ao.CityStationAO;
import com.yuwang.pinju.core.citystation.manager.CityStationManager;
import com.yuwang.pinju.core.citystation.manager.CityStationOrderManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.manager.LogisticsCityIpManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.citystation.CityOrderDO;
import com.yuwang.pinju.domain.citystation.CityOrderQuery;
import com.yuwang.pinju.domain.citystation.CityStationDO;
import com.yuwang.pinju.domain.logistics.LogisticsCityIpDO;

public class CityStationAOImpl extends BaseAO implements CityStationAO {
	private static Log log = LogFactory.getLog(CityStationAOImpl.class);

	private CityStationManager cityStationManager;

	private LogisticsCityIpManager logisticsCityIpManager;

	private CityStationOrderManager cityStationOrderManager;

	@Override
	public List<CityStationDO> getAllCityStations() {
		List<CityStationDO> cityStationList = null;
		try {
			cityStationList = cityStationManager.selectAllCityStations();
		} catch (ManagerException e) {
			log.error("Query All City Stations Exception : ", e);
		}
		return (cityStationList != null) ? cityStationList
				: new ArrayList<CityStationDO>();
	}

	@Override
	/**
	 * @date 2011-11-01
	 * @author qiuhongming
	 * @remark 
	 * 		原先接口只能查询到省份和城市的组合（eg：浙江省温州市），
	 *      不满足要求，现改为根据接口返回和城市所有分站进行匹配
	 * @error 
	 * 		TODO 后期维护
	 * 		不同省，相同市，则存在问题
	 */
	public CityStationDO getCityStationInfo(String remoteIp) {
		try {
			LogisticsCityIpDO logisticsCityIpDO = logisticsCityIpManager
					.loadLogisticsCityIpByIp(remoteIp);
			if (logisticsCityIpDO != null
					&& logisticsCityIpDO.getProvince() != null) {
				if (log.isDebugEnabled()) {
					log.debug("Customer IP Address mapped Province : "
							+ logisticsCityIpDO.getProvince());
				}
				List<CityStationDO> cityStationList = getAllCityStations();
				for(CityStationDO cityStation:cityStationList){
					if(logisticsCityIpDO.getProvince().indexOf(cityStation.getCityName()) != -1){
						if (log.isDebugEnabled()) {
							log.debug("Customer IP Address mapped City : "
									+ cityStation.getCityName());
						}
						return cityStation;
					}
				}
			}
		} catch (ManagerException e) {
			log.error("Find City Station By Remote IP Exception : ", e);
		}
		return null;
	}

	public void setCityStationManager(CityStationManager cityStationManager) {
		this.cityStationManager = cityStationManager;
	}

	public void setLogisticsCityIpManager(
			LogisticsCityIpManager logisticsCityIpManager) {
		this.logisticsCityIpManager = logisticsCityIpManager;
	}

	public void setCityStationOrderManager(
			CityStationOrderManager cityStationOrderManager) {
		this.cityStationOrderManager = cityStationOrderManager;
	}

	@Override
	public List<CityOrderDO> getAllCityOrders(CityOrderQuery cityOrderQuery) {
		List<CityOrderDO> cityOrderList = null;
		try {
			cityOrderList = cityStationOrderManager
					.selectAllCityOrders(cityOrderQuery);
			if(cityOrderList == null){
				log.debug("No deal data found!");
			}
		} catch (ManagerException e) {
			log.error("Query All City Stations Exception : ", e);
		}
		return (cityOrderList != null) ? cityOrderList
				: new ArrayList<CityOrderDO>();
	}

	@Override
	public CityStationDO getCityStationInfo(Long memberId) {
		try {
			return cityStationManager.selectCityStationByMemberId(memberId);
		} catch (ManagerException e) {
			log.error("Find City Station By Member ID Exception : ", e);
			return null;
		}
	}
}
