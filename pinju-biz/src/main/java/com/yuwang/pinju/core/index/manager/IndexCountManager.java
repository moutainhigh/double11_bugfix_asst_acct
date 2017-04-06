package com.yuwang.pinju.core.index.manager;

import com.yuwang.pinju.core.common.ManagerException;

public interface IndexCountManager {
	/**
	 * 查询首页访问次数
	 * @return
	 * @throws ManagerException
	 */
	public Long queryIndexVisitCount()throws ManagerException;

}
