package com.yuwang.pinju.core.sensitiveword.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.sensitiveword.dao.SensitiveWordDAO;
import com.yuwang.pinju.domain.word.SensitiveWordDO;

public class SensitiveWordDAOImpl extends BaseDAO implements SensitiveWordDAO {

	private static final String NAMESPACE = "sensitiveWordSqlMap";
	
	/**
	 * 查询所有的敏感词
	 * @return
	 * @throws DaoException
	 */
	public List<SensitiveWordDO> findAllSensitive() throws DaoException {
		return executeQueryForList(NAMESPACE + ".queryAllSensitiveWord",null);
	}
	
}
