package com.yuwang.pinju.core.search.manager.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.search.dao.KeywordDAO;
import com.yuwang.pinju.core.search.manager.KeywordManager;
import com.yuwang.pinju.domain.search.KeywordDO;

public class KeywordManagerImpl extends BaseManager implements KeywordManager {

	private KeywordDAO keywordDAO;

	public void setKeywordDAO(KeywordDAO keywordDAO) {
		this.keywordDAO = keywordDAO;
	}

	public void updateKeyword(KeywordDO keyword) throws ManagerException {
		long result;
		try {
			result = keywordDAO.queryExistsKeyword(keyword);
			if (result == 0) {
				keywordDAO.addKeyword(keyword);
			} else {
				keywordDAO.updateKeyword(keyword);
			}
		} catch (DaoException e) {
			log.error("[keywordDAOImpl] error", e);
			throw new ManagerException(
					"[KeywordManagerImpl] updateKeyword error", e);
		}
	}

}
