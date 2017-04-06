package com.yuwang.pinju.core.margin.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.margin.MarginPinJuDO;

public interface MarginPinJuDAO {
	
	/**
	 * 通过编号查询该账户的信息
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	MarginPinJuDO getMarginPinJuDOById(Long id) throws DaoException;	
	
	/**
	 * 添加品聚保证金信息
	 * @param marginPinJuDO
	 * @throws DaoException
	 */
	void addMarginPinJuDO(MarginPinJuDO marginPinJuDO) throws DaoException;
	
	/**
	 * 更新该账户保证金的信息
	 * @param marginPinJuDO
	 * @return
	 * @throws DaoException
	 */
	int updateMarginPinJuDO(MarginPinJuDO marginPinJuDO)throws DaoException;
	
	/**
	 * 由于该表只有一条记录，所以此方法可以进行无条件全表扫描
	 * @return
	 * @throws DaoException
	 */
	List<MarginPinJuDO> getMarginPinJuDO() throws DaoException;
	
}
