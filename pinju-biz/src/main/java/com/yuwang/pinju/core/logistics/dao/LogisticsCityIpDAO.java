package com.yuwang.pinju.core.logistics.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.logistics.LogisticsCityIpDO;

/**
 * 省份IP段
 * 
 * @author heyong
 * @since 2011-07-21
 */
public interface LogisticsCityIpDAO {
	/**
	 * 根据转换后的IP获取对应的省份
	 * 
	 * @param cityIp 转换后的IP
	 * 
	 * @return   省份IP段
	 * 
	 * @throws DaoException
	 */
	public LogisticsCityIpDO loadLogisticsCityIpByIp(long cityIp) throws DaoException;
}
