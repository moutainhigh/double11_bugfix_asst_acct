package com.yuwang.pinju.core.logistics.manager;

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
public interface LogisticsCityIpManager {
	/**
	 * 根据转换后的IP获取对应的省份
	 * 
	 * @param cityIp 当前地区IP地址
	 * 
	 * @return   省份IP段
	 * 
	 * @throws DaoException
	 */
	public LogisticsCityIpDO loadLogisticsCityIpByIp(String cityIp) throws ManagerException;
	
	/**
	 * 
	 * 根据运费模板id和城市ip返回默认运费
	 * @param logisticsTemplateId 运费模板id
	 * @param cityIp 城市ip
	 * @author caoxiao
	 */
	public Map<String, String> getDefaultRegionCarriage(Long logisticsTemplateId, String cityIp) throws ManagerException;
	
	/**
	 * 
	 * 根据运费模板id和城市名返回运费
	 * @param logisticsTemplateId 运费模板id
	 * @param cityName 城市名
	 * @author caoxiao
	 */
	public Map<String, String> getRegionCarriage(Long logisticsTemplateId, String cityName) throws ManagerException;
	
	/**
	 * 由于修改运费模板是全删全增，所以运费模板修改后，清空该运费模板下的区域运费和默认运费的缓存
	 * @param logisticsTemplateId
	 */
	public boolean deleteMemcachedByKey(Long logisticsTemplateId);
	
	/**
	 * 根据传入的ip获取对应运费模板的   区域id
	 * @param cityIp
	 * @return
	 * @throws ManagerException
	 */
	public String getRegionCode(String cityIp) throws ManagerException;
}
