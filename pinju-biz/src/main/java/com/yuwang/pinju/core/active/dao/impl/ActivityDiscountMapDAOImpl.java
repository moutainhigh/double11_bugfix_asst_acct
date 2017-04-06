package com.yuwang.pinju.core.active.dao.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.active.dao.ActivityDiscountMapDAO;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.active.ActivityDiscountMapDO;

public class ActivityDiscountMapDAOImpl extends BaseDAO implements ActivityDiscountMapDAO {

	@Override
	public int deleteActivityDiscountMapByActId(long actId) throws DaoException {
		return executeUpdate("activityDiscountMap.deleteActivityDiscountMapByActId", actId);
	}

	@Override
	public Object insertActivityDiscountMap(
			ActivityDiscountMapDO activityDiscountMapDO) throws DaoException {
		return executeInsert("activityDiscountMap.insertActivityDiscountMap", activityDiscountMapDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityDiscountMapDO> selectActivityDiscountMapByActId(
			long actId) throws DaoException {
		return executeQueryForList("activityDiscountMap.selectActivityDiscountMapByActId", actId);
	}

	@Override
	public ActivityDiscountMapDO selectActivityDiscountMapByActIdAndItemId(
			Map<String, Object> map) throws DaoException {
		return (ActivityDiscountMapDO) executeQueryForList("activityDiscountMap.selectActivityDiscountMapByActIdAndItemId", map);
	}

}
