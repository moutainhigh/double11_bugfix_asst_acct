package com.yuwang.pinju.core.active.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.active.dao.ActivityDiscountDAO;
import com.yuwang.pinju.core.active.manager.ActivityDiscountManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.activity.ActivityConstant;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.active.ActivityDiscountQuery;

public class ActivityDiscountManagerImpl extends BaseManager implements
		ActivityDiscountManager {
	private static Log log = LogFactory
			.getLog(ActivityDiscountManagerImpl.class);

	private ActivityDiscountDAO activityDiscountDAO;
	private CategoryCacheManager categoryCacheManager;
	@Override
	public int queryActivityDiscountPageCount(ActivityDiscountQuery query)
			throws ManagerException {
		int pageCount = 0;
		try {
			pageCount = activityDiscountDAO
					.selectActivityDiscountPageCount(query);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActivityDiscountManagerImpl.queryActivityDiscountPageCount() Exception : ",
					e);
		}
		return pageCount;
	}

	@Override
	public List<ActivityDiscountDO> queryActivityDiscountPageList(
			ActivityDiscountQuery query) throws ManagerException {
		List<ActivityDiscountDO> activityDiscountList = null;
		try {
			activityDiscountList = activityDiscountDAO
					.selectActivityDiscountPageList(query);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActivityDiscountManager.queryActivityDiscountPageList Exception : ",
					e);
		}
		return activityDiscountList;
	}

	@Override
	public ActivityDiscountDO queryActivityDiscountById(Long id)
			throws ManagerException {
		ActivityDiscountDO activityDiscount = null;
		try {
			activityDiscount = activityDiscountDAO
					.selectActivityDiscountById(id);
		} catch (DaoException e) {
			throw new ManagerException(
					"ActivityDiscountManager.queryActivityDiscountById Exception : ",
					e);
		}
		return activityDiscount;
	}

	@Override
	public Map<String, Object> getActivityDiscountInfo(Long activityId,
			Long itemId) throws ManagerException {
		ActivityDiscountDO activityDiscount = null;
		Map<String, Object> map = null;
		try {
			// 校验参数合法性
			if (activityId == null || itemId == null) {
				log.warn("查询失败，参数非法！");
				return null;
			}
			activityDiscount = activityDiscountDAO
					.selectActivityDiscountById(activityId);
			// 校验活动是否存在
			if (activityDiscount == null) {
				log.warn("查询限时折扣活动失败，ID : " + activityId);
				return null;
			}
			// 活动商品列表
			String itemList = activityDiscount.getItemList();
			// 活动折扣列表
			String discountList = activityDiscount.getDiscountList();
			// 活动限购列表
			String limitList = activityDiscount.getLimitList();

			boolean flag = false;
			String discount = null;
			String limit = null;

			if (StringUtil.isEmpty(itemList)
					|| StringUtil.isEmpty(discountList)
					|| StringUtil.isEmpty(limitList)) {
				log.warn("活动的商品列表为空，无商品参加活动");
				return null;
			} else {
				String[] items = itemList.split(",");
				String[] discounts = discountList.split(",");
				String[] limits = limitList.split(",");

				if (!(items.length == discounts.length && items.length == limits.length)) {
					log.warn("商品列表、折扣列表和限购列表数量不同，无法正确查询");
					return null;
				}

				for (int i = 0; i < items.length; i++) {
					if (items[i].equals(itemId.toString())) {
						flag = true;
						discount = discounts[i];
						limit = limits[i];
						log.debug("商品仍在活动中，限购数量为：" + limit + "，折扣率为："
								+ discount);
						break;
					}
				}
			}

			if (!flag) {
				log.warn("商品已从活动中移除，返回null");
				return null;
			}

			map = new HashMap<String, Object>();
			map.put(ActivityConstant.START_TIME, activityDiscount
					.getStartTime());
			map.put(ActivityConstant.END_TIME, activityDiscount.getEndTime());
			map.put(ActivityConstant.DICOUNT_RATE, discount);
			map.put(ActivityConstant.LIMIT_COUNT, limit);
			map.put(ActivityConstant.STATUS, activityDiscount.getStatus());
		} catch (DaoException e) {
			throw new ManagerException("查询商品折扣活动信息异常 ：", e);
		}
		return map;
	}

	public ActivityDiscountDAO getActivityDiscountDAO() {
		return activityDiscountDAO;
	}

	public void setActivityDiscountDAO(ActivityDiscountDAO activityDiscountDAO) {
		this.activityDiscountDAO = activityDiscountDAO;
	}

	/**
	 * @param categoryCacheManager the categoryCacheManager to set
	 */
	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	@Override
	public int updateActivityDiscount(ActivityDiscountDO activityDiscount)
			throws ManagerException {
		int result = 0;
		try {
			result = activityDiscountDAO.updateActivityDiscount(activityDiscount);
			//删除缓存  add by  liuboen
			categoryCacheManager.deleteActivityDiscountDOCache(activityDiscount.getId());
		} catch (DaoException e) {
			throw new ManagerException("DAO层-更新限时折扣活动信息异常：", e);
		}
		return result;
	}

	@Override
	public int updateActivityDiscountStatus(Map<String, Object> map)
			throws ManagerException {
		int result = 0;
		try {
			result = activityDiscountDAO.updateActivityDiscountStatus(map);
			//删除缓存  add by  liuboen
			categoryCacheManager.deleteActivityDiscountDOCache(Long.parseLong(map.get("id").toString()));
		} catch (DaoException e) {
			throw new ManagerException("DAO层-更新限时折扣活动状态异常：", e);
		}
		return result;
	}

	@Override
	public Long addActivityDiscount(ActivityDiscountDO activityDiscountDO) throws ManagerException {
		try {
			return activityDiscountDAO.insertActivityDiscount(activityDiscountDO);
		} catch (DaoException e) {
			throw new ManagerException("Error [activityDiscountDAO.insertActivityDiscount] 添加限时活动异常",e);
		}
	}

	@Override
	public boolean deleteActivityDiscountById(Long id) throws ManagerException {
		boolean flag = false;
		try {
			int res = activityDiscountDAO.deleteActivityDiscountById(id);
			if(res == 1){
				flag = true;
			}
		} catch (DaoException e) {
			throw new ManagerException("删除限时折扣活动异常：", e);
		}
		return flag;
	}
	
}
