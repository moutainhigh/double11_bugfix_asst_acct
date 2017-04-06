package com.yuwang.pinju.core.sensitiveword.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.word.SensitiveWordDO;

public interface SensitiveWordDAO {

	
	/**
	 * 查询所有的敏感词
	 * @return
	 * @throws DaoException
	 */
    	List<SensitiveWordDO> findAllSensitive() throws DaoException;
}
