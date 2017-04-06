package com.yuwang.pinju.core.active.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.active.ActivityDiscountQuery;

public interface ActivityDiscountManager {
	/**
	 * 根据条件查询折扣活动列表
	 * @param query
	 * @return
	 * @throws ManagerException
	 */
	List<ActivityDiscountDO> queryActivityDiscountPageList(ActivityDiscountQuery query) throws ManagerException;
	
	/**
	 * 根据条件查询折扣活动总数
	 * @param query
	 * @return
	 * @throws ManagerException
	 */
	int queryActivityDiscountPageCount(ActivityDiscountQuery query) throws ManagerException;

	/**
	 * 根据折扣活动ID查询活动信息
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	ActivityDiscountDO queryActivityDiscountById(Long id) throws ManagerException;
	
	/**
	 * 更新折扣活动状态
	 * @param Map ("id", "status")
	 * @return
	 * @throws ManagerException
	 */
	int updateActivityDiscountStatus(Map<String, Object> map) throws ManagerException;
	
	/**
	 * 根据活动ID和商品ID查询活动信息
	 * @param activityId
	 * @param itemId
	 * @return
	 * @throws ManagerException
	 */
	Map<String, Object> getActivityDiscountInfo(Long activityId, Long itemId) throws ManagerException;

	/**
	 * 更新限时折扣活动信息
	 * 
	 * @param activityDiscount
	 * @return
	 * @throws ManagerException
	 */
	int updateActivityDiscount(ActivityDiscountDO activityDiscount) throws ManagerException;
	
	/**
	 * 新增限时活动
	 * @param activityDiscountDO
	 * @return
	 * @throws ManagerException
	 */
	Long addActivityDiscount(ActivityDiscountDO activityDiscountDO) throws ManagerException;

	/**
	 * 根据限时折扣活动ID删除活动
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	boolean deleteActivityDiscountById(Long id) throws ManagerException;
}
