package com.yuwang.pinju.core.index.dao;

import com.yuwang.pinju.core.common.DaoException;

public interface IndexCountDAO {

	static final String NAMESPACE = "index_count.";

	/**
	 * 查询首页访问次数
	 * 
	 * @return
	 * @throws DaoException
	 */
	public Long queryIndexVisitCount() throws DaoException;

}
