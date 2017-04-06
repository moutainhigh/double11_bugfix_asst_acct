package com.yuwang.pinju.core.order.ao.impl;

import com.yuwang.pinju.core.order.ao.LogisticsServiceAO;
import com.yuwang.pinju.core.order.manager.LogisticsServiceManager;
import com.yuwang.pinju.domain.order.LogisticsServiceDO;

public class LogisticsServiceAOImpl implements LogisticsServiceAO {

	private LogisticsServiceManager logisticsServiceManager;
	


	/**
	 * 查询物流信息  （通过WebService）
	 * @param billcode 订单号
	 * @param express  快递公司代码
	 * @return 物流信息实体
	 */
	public LogisticsServiceDO getTrackBybillcode(String billcode, String express){
		return logisticsServiceManager.getTrackBybillcode(billcode, express);
	}
	
	
	
	public void setLogisticsServiceManager(LogisticsServiceManager logisticsServiceManager) {
		this.logisticsServiceManager = logisticsServiceManager;
	}



	/**
	 * <p>Description: 查询物流信息  </p>
	 * @param billcode		订单号
	 * @param express		快递宝 	 物流公司 code
	 * @param express100	快递100    物流公司 code
	 * @return 				物流信息实体
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-2
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public LogisticsServiceDO getTrackBybillcode(String billcode,
			String express, String express100) {
		return logisticsServiceManager.getTrackBybillcode(billcode, express, express100);
	}
}
