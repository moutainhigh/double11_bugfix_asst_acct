package com.yuwang.pinju.web.module.search.screen;

import com.yuwang.pinju.core.search.ao.KeywordAO;
import com.yuwang.pinju.domain.search.KeywordDO;
import com.yuwang.pinju.web.module.BaseAction;

public class KeywordAction extends BaseAction {
	
	private KeywordAO keywordAO;
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public KeywordAO getKeywordAO() {
		return keywordAO;
	}

	public void setKeywordAO(KeywordAO keywordAO) {
		this.keywordAO = keywordAO;
	}

	public String updateKeyword() {
		if (keyword == null || "".equals(keyword)) {
			return NONE;
		}
		KeywordDO keywordDO = new KeywordDO();
		keywordDO.setKeyword(keyword);
		keywordAO.updateKeyword(keywordDO);
		return NONE;
	}

}
