package com.yuwang.pinju.core.config.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.config.dao.ConfigManagerDAO;
import com.yuwang.pinju.domain.config.ConfigManagerDO;

public class ConfigManagerDAOImpl extends BaseDAO implements ConfigManagerDAO{

	public static final String CONFIG_MANAGER_SELECT_LIST="config_manager.selectConfigManagerList";
	public static final String CONFIG_MANAGER_SELECT_INCR_LIST="config_manager.selectIncrConfigManagerList";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigManagerDO> selectConfigManagerListByProjectType(
			Integer projectType) throws DaoException {
		return executeQueryForList(CONFIG_MANAGER_SELECT_LIST, projectType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConfigManagerDO> selectIncrConfigManagerList(
			Integer projectType, String startTime,String endTime) throws DaoException {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("projectType",projectType);
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		return executeQueryForList(CONFIG_MANAGER_SELECT_INCR_LIST, map);
	}

}
