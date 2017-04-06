package com.yuwang.pinju.core.active.dao.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.active.dao.ActivityDiscountDAO;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.active.ActivityDiscountQuery;

public class ActivityDiscountDAOImpl extends BaseDAO implements ActivityDiscountDAO {
	private ReadBaseDAO readBaseDAOForOracle ;

	@Override
	public int selectActivityDiscountPageCount(ActivityDiscountQuery query)
			throws DaoException {
		return (Integer)readBaseDAOForOracle.executeQueryForObject("activity_discount.queryActivityDiscountPageCount", query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityDiscountDO> selectActivityDiscountPageList(
			ActivityDiscountQuery query) throws DaoException {
		return readBaseDAOForOracle.executeQueryForList("activity_discount.queryActivityDiscountPageList", query);
	}

	@Override
	public ActivityDiscountDO selectActivityDiscountById(Long id)
			throws DaoException {
		return (ActivityDiscountDO) readBaseDAOForOracle.executeQueryForObject("activity_discount.selectActivityDiscountById", id);
	}

	@Override
	public int updateActivityDiscount(ActivityDiscountDO activityDiscount)
			throws DaoException {
		return executeUpdate("activity_discount.updateActivityDiscount", activityDiscount);
	}

	@Override
	public int updateActivityDiscountStatus(Map<String, Object> map)
			throws DaoException {
		return executeUpdate("activity_discount.endActivityDiscountStatus", map);
	}

	@Override
	public Long insertActivityDiscount(ActivityDiscountDO activityDiscountDO) throws DaoException {
		return (Long) executeInsert("activity_discount.insertActivityDiscount", activityDiscountDO);
	}

	@Override
	public int deleteActivityDiscountById(Long id) throws DaoException {
		return executeUpdate("activity_discount.updateActivityDiscountStatus", id);
	}

	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}
	
}
