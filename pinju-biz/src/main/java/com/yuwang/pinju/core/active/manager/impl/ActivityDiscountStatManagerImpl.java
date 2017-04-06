/**
 * 
 */
package com.yuwang.pinju.core.active.manager.impl;

import java.util.Date;

import com.yuwang.pinju.Constant.ActivityDiscountConstant;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.active.dao.ActivityDiscountStatDAO;
import com.yuwang.pinju.core.active.manager.ActivityDiscountStatManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.domain.active.ActivityDiscountStatDO;

/**  
 * @Project: pinju-biz
 * @Title: ActivityDiscountStatManagerImpl.java
 * @Package com.yuwang.pinju.core.active.manager.impl
 * @Description: 限时折扣统计实现类
 * @author liuboen liuboen@zba.com
 * @date 2011-7-29 上午09:32:22
 * @version V1.0  
 */

public class ActivityDiscountStatManagerImpl extends BaseManager implements
		ActivityDiscountStatManager {
	ActivityDiscountStatDAO activityDiscountStatDAO;
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.active.manager.ActivityDiscountStatManager#getStatById(long)
	 */
	@Override
	public ActivityDiscountStatDO getStatById(long id) throws ManagerException {
		
		try {
			return activityDiscountStatDAO.selectActivityDiscountStatById(id);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.active.manager.ActivityDiscountStatManager#getStatByMemberId(long)
	 */
	@Override
	public ActivityDiscountStatDO getStatByMemberId(long memberId)
			throws ManagerException {
		//获取当前月份
		String statDateString=DateUtil.formatMonth(new Date());
		long statDate=Long.parseLong(statDateString);
		try {
			return activityDiscountStatDAO.selectActivityDiscountStatByMemberId(memberId, statDate);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.active.manager.ActivityDiscountStatManager#stat(long, java.lang.String, long, long)
	 */
	@Override
	public Result stat(long memberId, String memberNick, long addUsedTime,
			long addUsedNum) throws ManagerException {
		Result result=new ResultSupport();
		result.setSuccess(false);
		/** -------验证华丽begin分割线------*/
		if (memberId<=0||memberNick==null||memberNick.length()<=0) {
			result.getModels().put("errorMessage","卖家昵称和ID不能为空");
			return result;
		}
		if (addUsedTime>ActivityDiscountConstant.DISCOUNT_STAT_ONLY_USED_TIME_MAX) {
			result.getModels().put("errorMessage","本次活动时间大于单次活动时间限额");
			return result;
		}
		/** -------验证华丽end分割线------*/
		//获取当前月份
		String statDateString=DateUtil.formatMonth(new Date());
		long statDate=Long.parseLong(statDateString);
		try {
			ActivityDiscountStatDO statDO=activityDiscountStatDAO.selectActivityDiscountStatByMemberId(memberId, statDate);
			if (statDO==null) {
				if (addUsedNum!=0L && addUsedNum!=1L) {
					result.getModels().put("errorMessage","活动数量不等于0或1");
					return result;
				}
				//当月首次添加时
				ActivityDiscountStatDO activityDiscountStatDO=new ActivityDiscountStatDO();
				activityDiscountStatDO.setGmtCreate(new Date());
				activityDiscountStatDO.setGmtModified(new Date());
				activityDiscountStatDO.setMemberId(memberId);
				activityDiscountStatDO.setMemberNick(memberNick);
				activityDiscountStatDO.setStatDate(statDate);
				activityDiscountStatDO.setUsedNum(addUsedNum);
				activityDiscountStatDO.setUsedTime(addUsedTime);
				activityDiscountStatDO.setVersion(0l);
				activityDiscountStatDO.setId(activityDiscountStatDAO.insertActivityDiscountStat(activityDiscountStatDO));
				if (activityDiscountStatDO.getId().longValue()>0) {
					result.getModels().put("statDO",activityDiscountStatDO);
					result.setSuccess(true);
				}else {
					result.getModels().put("errorMessage","添加活动统计信息失败");
				}
				return result;
			}else {
				//已经添加过
				long newUsedNum=addUsedNum+statDO.getUsedNum().longValue();
				long newUsedTime=addUsedTime+statDO.getUsedTime().longValue();
				/** -------又一次验证华丽begin分割线------*/
				if (newUsedNum>ActivityDiscountConstant.DISCOUNT_STAT_USED_NUM_MAX) {
					result.getModels().put("errorMessage","活动总次数超过"+ActivityDiscountConstant.DISCOUNT_STAT_USED_NUM_MAX+"次");
					return result;
				}
				if (newUsedTime>ActivityDiscountConstant.DISCOUNT_STAT_USED_TIME_MAX) {
					result.getModels().put("errorMessage","活动总时间超过"+ActivityDiscountConstant.DISCOUNT_STAT_USED_TIME_MAX+"小时");
					return result;
				}
				/** -------又一次验证华丽end分割线------*/
				int flag=activityDiscountStatDAO.updateActivityDiscountStat(newUsedTime, newUsedNum, memberId, statDate, statDO.getVersion());
				if (flag>0) {
					result.setSuccess(true);
				}else {
					result.getModels().put("errorMessage","更新失败,version不一致");
				}
				return result;
			}
		} catch (DaoException e) {
			throw new  ManagerException(e);
		}
	}

	/**
	 * @param activityDiscountStatDAO the activityDiscountStatDAO to set
	 */
	public void setActivityDiscountStatDAO(
			ActivityDiscountStatDAO activityDiscountStatDAO) {
		this.activityDiscountStatDAO = activityDiscountStatDAO;
	}

}
