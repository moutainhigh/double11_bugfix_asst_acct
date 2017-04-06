package com.yuwang.pinju.core.index.ao.impl;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.index.ao.IndexCountAO;
import com.yuwang.pinju.core.index.manager.IndexCountManager;
import com.yuwang.pinju.core.user.ao.BaseAO;

public class IndexCountAOImpl extends BaseAO implements IndexCountAO{
	
	private IndexCountManager indexCountManager;

	public void setIndexCountManager(IndexCountManager indexCountManager) {
		this.indexCountManager = indexCountManager;
	}

	@Override
	public Long queryIndexVisitCount() {
		Long count=0L;
		try {
			count= indexCountManager.queryIndexVisitCount();
		} catch (ManagerException e) {
			log.error("查询访问次数异常",e);
		}
		return count;
	}

}
