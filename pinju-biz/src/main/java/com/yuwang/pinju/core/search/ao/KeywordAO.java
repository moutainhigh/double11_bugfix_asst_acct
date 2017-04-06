package com.yuwang.pinju.core.search.ao;

import com.yuwang.pinju.domain.search.KeywordDO;

public interface KeywordAO {
	/**
	 * 更新关键词搜索次数
	 * 
	 * @param keyword
	 */
	public void updateKeyword(KeywordDO keyword);

}
