package com.yuwang.pinju.core.search.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.search.dao.KeywordDAO;
import com.yuwang.pinju.domain.search.KeywordDO;

public class KeywordDAOImpl extends BaseDAO implements KeywordDAO {
	public static final String NAMESPACE = "SearchKeyword.";

	@Override
	public long queryExistsKeyword(KeywordDO keyword) throws DaoException {
		return (Long) super.executeQueryForObject(NAMESPACE + "selectKeyword",
				keyword);
	}

	@Override
	public void addKeyword(KeywordDO keyword) throws DaoException {
		super.executeInsert(NAMESPACE + "insertKeyword", keyword);
	}

	@Override
	public void updateKeyword(KeywordDO keyword) throws DaoException {
		super.executeUpdate(NAMESPACE + "updateKeyword", keyword);
	}

}
