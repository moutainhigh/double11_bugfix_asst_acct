package com.yuwang.pinju.core.logistics.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.logistics.LogisticsSellerDefaultDO;

/**
 * 物流发货方式
 * @author caoxiao
 * @create on 2011-07-29
 */
public interface LogisticsSellerDeliveryDAO {
	
	public List<LogisticsSellerDefaultDO> queryByMemberId(Long sellerId) throws DaoException;
	
	public int deleteById(Long id) throws DaoException;
	
	public void add(LogisticsSellerDefaultDO logistics) throws DaoException;
}
