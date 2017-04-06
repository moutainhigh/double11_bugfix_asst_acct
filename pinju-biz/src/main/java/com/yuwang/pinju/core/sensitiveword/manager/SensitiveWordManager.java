package com.yuwang.pinju.core.sensitiveword.manager;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.word.SensitiveWordDO;


public interface SensitiveWordManager {
	
	/**
	 * 查询所有的敏感词
	 * @return
	 * @throws DaoException
	 */
    	List<SensitiveWordDO> findAllSensitive() throws ManagerException;
	
	/**
	 * 初始化敏感词库
	 * @return
	 */
	public void initSensitiveWord() throws ManagerException;
	
}
