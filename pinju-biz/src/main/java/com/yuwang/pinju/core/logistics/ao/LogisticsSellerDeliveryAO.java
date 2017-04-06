package com.yuwang.pinju.core.logistics.ao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.logistics.LogisticsSellerDefaultDO;

/**
 * 物流发货方式
 * @author caoxiao
 * @create on 2011-07-29
 */
public interface LogisticsSellerDeliveryAO {
	
	public List<LogisticsSellerDefaultDO> queryByMemberId(Long sellerId) ;
	
	public int deleteById(Long id) ;
	
	public void add(LogisticsSellerDefaultDO logistics) ;
}
