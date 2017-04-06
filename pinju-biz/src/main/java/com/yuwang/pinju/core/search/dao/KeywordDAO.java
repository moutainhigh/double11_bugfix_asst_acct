package com.yuwang.pinju.core.search.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.search.KeywordDO;

public interface KeywordDAO {
	/**
	 * 检查关键词是否存在
	 * 
	 * @param keyword
	 * @return
	 * @throws DaoException
	 */
	public long queryExistsKeyword(KeywordDO keyword) throws DaoException;

	/**
	 * 添加新关键词
	 * 
	 * @param keyword
	 * @throws DaoException
	 */
	public void addKeyword(KeywordDO keyword) throws DaoException;

	/**
	 * 更新关键词的搜索次数
	 * 
	 * @param keyword
	 * @throws DaoException
	 */
	public void updateKeyword(KeywordDO keyword) throws DaoException;

}
