package com.yuwang.pinju.core.active.manager.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.active.dao.ActivityDiscountMapDAO;
import com.yuwang.pinju.core.active.manager.ActivityDiscountMapManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.active.ActivityDiscountMapDO;

public class ActivityDiscountMapManagerImpl extends BaseManager implements
		ActivityDiscountMapManager {
	private ActivityDiscountMapDAO activityDiscountMapDAO; 

	@Override
	public int deleteActivityDiscountMapByActId(long actId)
			throws ManagerException {
		try {
			return activityDiscountMapDAO.deleteActivityDiscountMapByActId(actId);
		} catch (DaoException e) {
			throw new ManagerException("ActivityDiscountMapDAO.deleteActivityDiscountMapByActId Exception:", e);
		}
	}

	@Override
	public void insertActivityDiscountMap(
			ActivityDiscountMapDO activityDiscountMapDO)
			throws ManagerException {
		try {
			activityDiscountMapDAO.insertActivityDiscountMap(activityDiscountMapDO);
		} catch (DaoException e) {
			throw new ManagerException("ActivityDiscountMapDAO.insertActivityDiscountMap Exception:", e);
		}
	}

	@Override
	public List<ActivityDiscountMapDO> selectActivityDiscountMapByActId(
			long actId) throws ManagerException {
		try {
			return activityDiscountMapDAO.selectActivityDiscountMapByActId(actId);
		} catch (DaoException e) {
			throw new ManagerException("ActivityDiscountMapDAO.selectActivityDiscountMapByActId Exception:", e);
		}
	}

	@Override
	public ActivityDiscountMapDO selectActivityDiscountMapByActIdAndItemId(
			Map<String, Object> map) throws ManagerException {
		try {
			return activityDiscountMapDAO.selectActivityDiscountMapByActIdAndItemId(map);
		} catch (DaoException e) {
			throw new ManagerException("ActivityDiscountMapDAO.selectActivityDiscountMapByActIdAndItemId Exception:", e);
		}
	}

	public void setActivityDiscountMapDAO(
			ActivityDiscountMapDAO activityDiscountMapDAO) {
		this.activityDiscountMapDAO = activityDiscountMapDAO;
	}

}
