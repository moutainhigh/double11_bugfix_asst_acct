package com.yuwang.pinju.core.index.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.index.dao.IndexCountDAO;

public class IndexCountDAOImpl extends BaseDAO implements IndexCountDAO {
	
	

	@Override
	public Long queryIndexVisitCount() throws DaoException {
		Long count = 0L;
		try {
			count = (Long) executeQueryForObject(NAMESPACE + "selectSeqIndexCount", "");
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return count;
	}

}
