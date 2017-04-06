package com.yuwang.pinju.core.logistics.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.logistics.LogisticsSellerDefaultDO;

public interface LogisticsSellerDeliveryManager {
	
	public List<LogisticsSellerDefaultDO> queryByMemberId(Long sellerId)  throws ManagerException;
	
	public int deleteById(Long id)  throws ManagerException;
	
	public void add(LogisticsSellerDefaultDO logistics)  throws ManagerException;

}
