package com.yuwang.pinju.core.logistics.ao.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.ao.LogisticsCityIpAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsCityIpManager;
import com.yuwang.pinju.domain.logistics.LogisticsCityIpDO;

/**
 * 省份IP段
 * 
 * @author heyong
 * @since 2011-07-21
 */
public class LogisticsCityIpAOImpl implements LogisticsCityIpAO{
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private LogisticsCityIpManager logisticsCityIpManager;
	public void setLogisticsCityIpManager(
			LogisticsCityIpManager logisticsCityIpManager) {
		this.logisticsCityIpManager = logisticsCityIpManager;
	}

	/**
	 * 根据转换后的IP获取对应的省份
	 * 
	 * @param cityIp 当前地区IP地址
	 * 
	 * @return   省份IP段
	 * 
	 * @throws DaoException
	 */
	public LogisticsCityIpDO loadLogisticsCityIpByIp(String cityIp) {
		try {
			return logisticsCityIpManager.loadLogisticsCityIpByIp(cityIp);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			log.error("Event=[LogisticsCityIpAOImpl#loadLogisticsCityIpByIp]根据转换后的IP得到省份信息", e);
		}
		return new LogisticsCityIpDO();
	}

	@Override
	public Map<String, String> getDefaultRegionCarriage(Long logisticsTemplateId, String cityIp) {
		Map<String, String> map = null;
		try {
			map = logisticsCityIpManager.getDefaultRegionCarriage(logisticsTemplateId, cityIp);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, String> getRegionCarriage(Long logisticsTemplateId,String cityName) {
		Map<String, String> map = null;
		try {
			map = logisticsCityIpManager.getRegionCarriage(logisticsTemplateId, cityName);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
		return map;
	}

}
