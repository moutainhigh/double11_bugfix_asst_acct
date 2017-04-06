package com.yuwang.pinju.domain.search;

import com.yuwang.api.common.BaseDO;

public class KeywordDO extends BaseDO {
	private long id;// 编号
	private String keyword;// 关键词
	private long count;// 搜索次数

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
