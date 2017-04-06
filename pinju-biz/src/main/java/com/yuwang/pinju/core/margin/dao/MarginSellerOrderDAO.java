package com.yuwang.pinju.core.margin.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.margin.MarginSellerOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginSellerOrderQuery;


public interface MarginSellerOrderDAO{

	/**
	 * 插入保证金交易记录
	 * @param marginSellerOrderDO
	 * @throws DaoException
	 */
	void insertMarginSellerOrderRecord(MarginSellerOrderDO marginSellerOrderDO) throws DaoException;
	
	/**
	 * 更新卖家保证金交易记录
	 * @param marginSellerOrderDO
	 * @return
	 * @throws DaoException
	 */
	int updateMarginSellerOrderRecord(MarginSellerOrderDO marginSellerOrderDO) throws DaoException;
	
	/**
	 * 通过ID查询保证金交易记录
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	MarginSellerOrderDO getMarginSellerOrderDOById(Long id) throws DaoException;
		
	/**
	 * 获取保证金交易记录数
	 * @param marginQuery
	 * @return
	 * @throws DaoException
	 */
	Integer getMarginSellerOrderDOsCount(MarginSellerOrderQuery marginSellerOrderQuery) throws DaoException;
	
	/**
	 * 获取保证金交易记录列表
	 * @param marginQuery
	 * @return
	 * @throws DaoException
	 */
	List<MarginSellerOrderDO> getMarginSellerOrderDOs(MarginSellerOrderQuery marginSellerOrderQuery) throws DaoException;
	
}
