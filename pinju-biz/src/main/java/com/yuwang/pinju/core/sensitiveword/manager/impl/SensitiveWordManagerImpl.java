package com.yuwang.pinju.core.sensitiveword.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.sensitiveword.dao.SensitiveWordDAO;
import com.yuwang.pinju.core.sensitiveword.manager.SensitiveWordManager;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.domain.word.SensitiveWordDO;


public class SensitiveWordManagerImpl extends BaseManager implements SensitiveWordManager {

	private SensitiveWordDAO sensitiveWordDAO;

	/**
	 * 查询所有的敏感词
	 * @return
	 * @throws DaoException
	 */
	public List<SensitiveWordDO> findAllSensitive() throws ManagerException {
		try {
			return sensitiveWordDAO.findAllSensitive();
		} catch (Exception e) {
			log.error(e);
			throw new ManagerException("查询所有的敏感词失败!", e);
		}
	}
	
	/**
	 * 初始化敏感词库
	 * @return
	 */
	public void initSensitiveWord() throws ManagerException {
		log.info("begin ----- excute init sensitive word job:" + DateUtil.formatDatetime(new Date()));
		try {
			List<SensitiveWordDO> sensitiveWordList = sensitiveWordDAO.findAllSensitive();
			if (sensitiveWordList != null && sensitiveWordList.size() > 0) {
			    Map<Integer, Set<String>> sensitiveWordMap = new HashMap<Integer, Set<String>>();
				for (SensitiveWordDO sensitiveWord : sensitiveWordList) {
				    getSensitiveWordSet(sensitiveWordMap, sensitiveWord.getType()).add(sensitiveWord.getWord().toUpperCase());
				}
				for (Entry<Integer, Set<String>> mapEntry: sensitiveWordMap.entrySet()) {
				    WordFilterFacade.init(mapEntry.getValue(),mapEntry.getKey());
				}
			}
		} catch (Exception e) {
			log.error("execute SensitiveWordManagerImpl.initSensitiveWord failed", e);
			throw new ManagerException("execute sensitive word init failed!", e);
		}
		log.info("end ----- excute init sensitive word job:" + DateUtil.formatDatetime(new Date()));
	}
	
	/**
	 * 获取对应类型关键词的列表，如果没有对应类型则创建一个
	 * 
	 * @param sensitiveWordMap
	 * 		存放关键词的map
	 * @param type
	 * 		关键词的类型
	 * @return
	 */
	private Set<String> getSensitiveWordSet(Map<Integer, Set<String>> sensitiveWordMap, Integer type){
	    if (sensitiveWordMap.get(type) == null) {
		sensitiveWordMap.put(type, new HashSet<String>());
	    }
	    return sensitiveWordMap.get(type);
	}
	
	public void setSensitiveWordDAO(SensitiveWordDAO sensitiveWordDAO) {
		this.sensitiveWordDAO = sensitiveWordDAO;
	}
}
