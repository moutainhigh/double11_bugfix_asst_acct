package com.yuwang.pinju.core.logistics.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.logistics.dao.LogisticsCityIpDAO;
import com.yuwang.pinju.domain.logistics.LogisticsCityIpDO;

public class LogisticsCityIpDAOImpl extends BaseDAO implements LogisticsCityIpDAO{
	private final String LOGISTICS_NAME_SPACE = "trade_logistics_city_ip.";
	
	private ReadBaseDAO readBaseDAOForOracle ;

	/**
	 * 根据转换后的IP获取对应的省份
	 * 
	 * @param cityIp 转换后的IP
	 * 
	 * @return   省份IP段
	 * 
	 * @throws DaoException
	 */
	public LogisticsCityIpDO loadLogisticsCityIpByIp(long cityIp)throws DaoException {
		return (LogisticsCityIpDO) readBaseDAOForOracle.executeQueryForObject(LOGISTICS_NAME_SPACE + "selectTradeLogisticsCityIpByIp", cityIp);
	}

	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}

}
