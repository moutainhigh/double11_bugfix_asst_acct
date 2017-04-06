/**
 * 
 */
package com.yuwang.pinju.core.active.manager;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.active.ActivityDiscountMapDO;

public interface ActivityDiscountMapManager {
	/**
	 * 通过活动ID获取商品列表
	 * 
	 * @param actId
	 * @return
	 * @throws DaoException
	 */
	public List<ActivityDiscountMapDO> selectActivityDiscountMapByActId(
			long actId) throws ManagerException;

	/**
	 * 通过活动ID和商品ID获取活动信息
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public ActivityDiscountMapDO selectActivityDiscountMapByActIdAndItemId(
			Map<String, Object> map) throws ManagerException;

	/**
	 * 插入活动 -- 商品信息
	 * 
	 * @param activityDiscountMapDO
	 * @return
	 * @throws DaoException
	 */
	public void insertActivityDiscountMap(
			ActivityDiscountMapDO activityDiscountMapDO) throws ManagerException;

	/**
	 * 删除活动 -- 商品信息
	 * 
	 * @param activityDiscountMap
	 * @return
	 * @throws DaoException
	 */
	public int deleteActivityDiscountMapByActId(long actId) throws ManagerException;
}
