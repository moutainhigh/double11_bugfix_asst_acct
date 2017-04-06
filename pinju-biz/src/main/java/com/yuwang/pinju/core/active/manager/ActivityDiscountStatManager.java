/**
 * 
 */
package com.yuwang.pinju.core.active.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.active.ActivityDiscountStatDO;

/**  
 * @Project: pinju-biz
 * @Title: ActivityDiscountStatManager.java
 * @Package com.yuwang.pinju.core.active.manager
 * @Description: 限时折扣活动统计manager
 * @author liuboen liuboen@zba.com
 * @date 2011-7-29 上午09:21:57
 * @version V1.0  
 */

public interface ActivityDiscountStatManager {

	/**
	 * 通过用户数字ID获取限时活动统计信息(默认当月)
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 */
	public ActivityDiscountStatDO getStatByMemberId(long memberId)throws ManagerException;
	
	/**
	 * 通过活动统计ID获取活动
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public ActivityDiscountStatDO getStatById(long id)throws ManagerException;

	/**
	 * 添加统计列表信息(默认添加到当月)
	 * @param memberId
	 * @param addUsedTime
	 * @param addUsedNum
	 * @return
	 * @throws ManagerException
	 */
	public Result stat(long memberId,String memberNick,long addUsedTime,long addUsedNum)throws ManagerException;
}
