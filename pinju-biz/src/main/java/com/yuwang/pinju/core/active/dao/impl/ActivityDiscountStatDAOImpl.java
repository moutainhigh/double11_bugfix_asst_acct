/**
 * 
 */
package com.yuwang.pinju.core.active.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.core.active.dao.ActivityDiscountStatDAO;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.domain.active.ActivityDiscountStatDO;

/**  
 * @Project: pinju-biz
 * @Title: ActivityDiscountStatDAOImpl.java
 * @Package com.yuwang.pinju.core.active.dao.impl
 * @Description: 限时折扣统计实现类
 * @author liuboen liuboen@zba.com
 * @date 2011-7-28 下午02:19:07
 * @version V1.0  
 */

public class ActivityDiscountStatDAOImpl extends BaseDAO implements ActivityDiscountStatDAO {
	private ReadBaseDAO readBaseDAOForOracle ;

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.active.dao.ActivityDiscountStat#insertActivityDiscountStat(com.yuwang.pinju.core.active.dao.ActivityDiscountStat)
	 */
	@Override
	public long insertActivityDiscountStat(
			ActivityDiscountStatDO activityDiscountStatDO) throws DaoException {
		return (Long)executeInsert("activityDiscountStat.insertActivityDiscountStat", activityDiscountStatDO);
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.active.dao.ActivityDiscountStat#selectActivityDiscountStatById(long, java.lang.String)
	 */
	@Override
	public ActivityDiscountStatDO selectActivityDiscountStatById(long id) throws DaoException {
		return (ActivityDiscountStatDO) readBaseDAOForOracle.executeQueryForObject("activityDiscountStat.selectActivityDiscountStatById", id);
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.active.dao.ActivityDiscountStat#selectActivityDiscountStatByMemberId(long, java.lang.String)
	 */
	@Override
	public ActivityDiscountStatDO selectActivityDiscountStatByMemberId(
			long memberId, long statDate) throws DaoException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("statDate",statDate);
		return (ActivityDiscountStatDO) readBaseDAOForOracle.executeQueryForObject("activityDiscountStat.selectActivityDiscountStatByMemberIdAndStatdate", map);
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.active.dao.ActivityDiscountStat#updateActivityDiscountStat(com.yuwang.pinju.core.active.dao.ActivityDiscountStat)
	 */
	@Override
	public int updateActivityDiscountStat(
			long usedTime,long usedNum,long memberId,long statDate,long versionNow) throws DaoException {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("usedTime",usedTime);
		map.put("usedNum",usedNum);
		map.put("statDate",statDate);
		map.put("oldVersion",versionNow);
		map.put("version",versionNow+1);
		map.put("gmtModified", new Date());
		return executeUpdate("activityDiscountStat.updateActivityDiscountStat", map);
	}

	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}

}
