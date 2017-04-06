package com.yuwang.pinju.core.search.ao.impl;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.search.ao.KeywordAO;
import com.yuwang.pinju.core.search.manager.KeywordManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.search.KeywordDO;

public class KeywordAOImpl extends BaseAO implements KeywordAO {
	private KeywordManager keywordManager;

	public void setKeywordManager(KeywordManager keywordManager) {
		this.keywordManager = keywordManager;
	}

	@Override
	public void updateKeyword(KeywordDO keyword) {
		try {
			keywordManager.updateKeyword(keyword);
		} catch (ManagerException e) {
			log.error(e);
		}

	}

}
