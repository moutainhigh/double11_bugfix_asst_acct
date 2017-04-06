package com.yuwang.pinju.core.logistics.ao;

import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.logistics.LogisticsCityIpDO;


/**
 * 省份IP段
 * 
 * @author heyong
 * @since 2011-07-21
 */
public interface LogisticsCityIpAO {
	/**
	 * 根据转换后的IP获取对应的省份
	 * 
	 * @param cityIp 当前地区IP地址
	 * 
	 * @return   省份IP段
	 * 
	 * @throws DaoException
	 */
	public LogisticsCityIpDO loadLogisticsCityIpByIp(String cityIp);
	
	public Map<String, String> getDefaultRegionCarriage(Long logisticsTemplateId, String cityIp);
	
	public Map<String, String> getRegionCarriage(Long logisticsTemplateId, String cityName);
}
