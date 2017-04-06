package com.yuwang.pinju.core.index.manager.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.index.manager.IndexCountManager;
import com.yuwang.pinju.core.margin.dao.MarginSellerDAO;

public class IndexCountManagerImpl extends BaseManager implements IndexCountManager {

	private MarginSellerDAO marginSellerDAO;

	@Override
	public Long queryIndexVisitCount() throws ManagerException {
		Long count = 0L;
		try {
			count = marginSellerDAO.queryIndexVisitCount();
		} catch (DaoException e) {
			log.error("查询访问次数异常", e);
			new ManagerException("查询访问次数异常", e);
		}
		return count;
	}

	public void setMarginSellerDAO(MarginSellerDAO marginSellerDAO) {
		this.marginSellerDAO = marginSellerDAO;
	}

}
