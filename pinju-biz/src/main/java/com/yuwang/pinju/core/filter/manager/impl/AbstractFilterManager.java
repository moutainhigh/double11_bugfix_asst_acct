package com.yuwang.pinju.core.filter.manager.impl;

import com.yuwang.pinju.core.filter.FilterResult;
import com.yuwang.pinju.core.filter.manager.FilterManager;

public abstract class AbstractFilterManager implements FilterManager {

	@Override
	public FilterResult doFilter(String statement) {
		return doFilter(statement, false);
	}
}
