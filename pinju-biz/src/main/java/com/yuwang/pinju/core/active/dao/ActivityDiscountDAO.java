package com.yuwang.pinju.core.active.dao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.active.ActivityDiscountQuery;

public interface ActivityDiscountDAO {
	/**
	 * 根据查询条件查询折扣活动列表
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	List<ActivityDiscountDO> selectActivityDiscountPageList(ActivityDiscountQuery query) throws DaoException;
	
	/**
	 * 根据查询条件查询折扣活动总数
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	int selectActivityDiscountPageCount(ActivityDiscountQuery query) throws DaoException;

	/**
	 * 根据ID查询限时打折活动信息
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	ActivityDiscountDO selectActivityDiscountById(Long id) throws DaoException;

	/**
	 * 更新限时折扣活动信息
	 * @param activityDiscount
	 * @return
	 * @throws DaoException
	 */
	int updateActivityDiscount(ActivityDiscountDO activityDiscount) throws DaoException;

	/**
	 * 更新折扣活动状态
	 * @param map
	 * @return
	 */
	int updateActivityDiscountStatus(Map<String, Object> map) throws DaoException;
	
	/**
	 * 新增限时活动
	 * @param activityDiscountDO
	 * @return
	 * @throws DaoException
	 */
	Long insertActivityDiscount(ActivityDiscountDO activityDiscountDO) throws DaoException;

	/**
	 * 删除限时折扣活动（更改状态）
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	int deleteActivityDiscountById(Long id) throws DaoException;
}
