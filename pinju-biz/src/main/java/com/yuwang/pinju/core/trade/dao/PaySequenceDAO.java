package com.yuwang.pinju.core.trade.dao;

import com.yuwang.pinju.core.common.DaoException;

public interface PaySequenceDAO {
	/**
	 * 根据sequenceName 查询sequence下一个
	 * @param seqName
	 * @return
	 */
	public Long getSequenceNextByName(String seqName) throws DaoException ;
}
