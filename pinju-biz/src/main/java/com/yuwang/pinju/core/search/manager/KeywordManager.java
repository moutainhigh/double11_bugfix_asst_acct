package com.yuwang.pinju.core.search.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.search.KeywordDO;


public interface KeywordManager {
	public void updateKeyword(KeywordDO keyword) throws ManagerException;

}
