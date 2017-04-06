/**
 * 
 */
package com.yuwang.pinju.core.active.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.active.ActivityDiscountStatDO;


/**  
 * @Project: pinju-biz
 * @Title: ActivityDiscountStat.java
 * @Package com.yuwang.pinju.core.active.dao
 * @Description: 商品限时打折活动统计DAO
 * @author liuboen liuboen@zba.com
 * @date 2011-7-28 下午01:22:10
 * @version V1.0  
 */

public interface ActivityDiscountStatDAO {

	/**
	 * 通过ID获取
	 * @param id		
	 * @param statDate 统计月份
	 * @return
	 * @throws DaoException
	 */
	public ActivityDiscountStatDO selectActivityDiscountStatById(long id)throws DaoException;
	/**
	 * 通过用户ID获取
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public ActivityDiscountStatDO selectActivityDiscountStatByMemberId(long memberId,long statDate)throws DaoException;
	
	/**
	 * 插入一条活动信息表
	 * @param activityDiscountStat
	 * @return
	 * @throws DaoException
	 */
	public long	insertActivityDiscountStat(ActivityDiscountStatDO activityDiscountStatDO)throws DaoException;

	/**
	 * 更新活动统计信息
	 * @param activityDiscountStat
	 * @return
	 * @throws DaoException
	 */
	public int updateActivityDiscountStat(long usedTime,long usedNum,long memberId,long statDate,long versionNow)throws DaoException;
	
	
}
