/**
 * 
 */
package com.yuwang.pinju.core.active.dao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.active.ActivityDiscountMapDO;

/**
 * @Project: pinju-biz
 * @Package com.yuwang.pinju.core.active.dao
 * @Description: 商品限时折扣活动中间表操作
 * @date 2011-12-07
 * @version V1.0
 */

public interface ActivityDiscountMapDAO {

	/**
	 * 通过活动ID获取商品列表
	 * 
	 * @param actId
	 * @return
	 * @throws DaoException
	 */
	public List<ActivityDiscountMapDO> selectActivityDiscountMapByActId(
			long actId) throws DaoException;

	/**
	 * 通过活动ID和商品ID获取活动信息
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public ActivityDiscountMapDO selectActivityDiscountMapByActIdAndItemId(
			Map<String, Object> map) throws DaoException;

	/**
	 * 插入活动 -- 商品信息
	 * 
	 * @param activityDiscountMapDO
	 * @return
	 * @throws DaoException
	 */
	public Object insertActivityDiscountMap(
			ActivityDiscountMapDO activityDiscountMapDO) throws DaoException;

	/**
	 * 删除活动 -- 商品信息
	 * 
	 * @param activityDiscountMap
	 * @return
	 * @throws DaoException
	 */
	public int deleteActivityDiscountMapByActId(long actId) throws DaoException;

}
