package com.yuwang.pinju.core.active.ao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.core.active.ao.ActivityDiscountAO;
import com.yuwang.pinju.core.active.manager.ActivityDiscountManager;
import com.yuwang.pinju.core.active.manager.ActivityDiscountMapManager;
import com.yuwang.pinju.core.active.manager.ActivityDiscountStatManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.active.ActivityDiscountMapDO;
import com.yuwang.pinju.domain.active.ActivityDiscountQuery;
import com.yuwang.pinju.domain.active.ActivityDiscountStatDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQuery;

import com.yuwang.pinju.domain.shop.ShopCategoryListDO;

public class ActivityDiscountAOImpl extends BaseAO implements ActivityDiscountAO {
	private static Log log = LogFactory.getLog(ActivityDiscountAOImpl.class);

	private ActivityDiscountManager activityDiscountManager;
	private ShopCategoryManager shopCategoryManager;
	private ItemManager itemManager;

	private ActivityDiscountStatManager activityDiscountStatManager;

	private ActivityDiscountMapManager activityDiscountMapManager;

	private CategoryCacheManager categoryCacheManager;

	@Override
	public int queryActivityDiscountPageCount(ActivityDiscountQuery query) {
		int totalPage = 0;
		try {
			totalPage = activityDiscountManager.queryActivityDiscountPageCount(query);
		} catch (ManagerException e) {
			log.error("Query Seller Discount Activities Count Exception : ", e);
		}
		return totalPage;
	}

	@Override
	public List<ActivityDiscountDO> queryActivityDiscountPageList(ActivityDiscountQuery query) {
		List<ActivityDiscountDO> activityDiscountList = null;
		try {

			activityDiscountList = activityDiscountManager.queryActivityDiscountPageList(query);

		} catch (ManagerException e) {
			log.error("Query Seller Discount Activities Exception : ", e);
		}
		return (activityDiscountList != null ? activityDiscountList : new ArrayList<ActivityDiscountDO>());
	}

	@Override
	public ActivityDiscountDO queryActivityDiscountById(Long id) {
		ActivityDiscountDO activityDiscount = null;
		try {
			activityDiscount = activityDiscountManager.queryActivityDiscountById(id);
		} catch (ManagerException e) {
			log.error("Query Discount Activities By ID Exception : ", e);
		}
		return activityDiscount;
	}

	@Override
	public ActivityDiscountStatDO queryActivityDiscountStateByMemberId(Long memberId) {
		ActivityDiscountStatDO activityDiscountStat = null;
		try {
			activityDiscountStat = activityDiscountStatManager.getStatByMemberId(memberId);
		} catch (ManagerException e) {
			log.error("Query Discount State By MemberId Exception : ", e);
		}
		return activityDiscountStat;
	}

	@Override
	public List<ItemDO> queryAllItemList(ItemQuery itemQuery) {
		List<ItemDO> itemList = null;
		try {
			itemList = itemManager.getItemList(itemQuery);
		} catch (ManagerException e) {
			log.error("调用ItemManager查询卖家商品列表异常：", e);
		}
		return (itemList == null ? new ArrayList<ItemDO>() : itemList);
	}

	@Override
	public List<ItemDO> querySelectedItemList(List<Long> itemIds) {
		List<ItemDO> itemList = null;
		try {
			itemList = itemManager.getItemListByIds(itemIds);
		} catch (ManagerException e) {
			log.error("调用ItemManager查询卖家商品列表异常：", e);
		}
		return (itemList == null ? new ArrayList<ItemDO>() : itemList);
	}

	@Override
	public int queryAllItemListCount(ItemQuery itemQuery) {

		int totalPage = 0;
		try {
			totalPage = itemManager.getItemListCount(itemQuery);
		} catch (ManagerException e) {
			log.error("【ItemManager】查询卖家商品总数异常: ", e);
		}
		return totalPage;
	}

	public ActivityDiscountManager getActivityDiscountManager() {
		return activityDiscountManager;
	}

	public void setActivityDiscountManager(ActivityDiscountManager activityDiscountManager) {
		this.activityDiscountManager = activityDiscountManager;
	}

	public ActivityDiscountStatManager getActivityDiscountStatManager() {
		return activityDiscountStatManager;
	}

	public void setActivityDiscountStatManager(ActivityDiscountStatManager activityDiscountStatManager) {
		this.activityDiscountStatManager = activityDiscountStatManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	/**
	 * @param categoryCacheManager
	 *            the categoryCacheManager to set
	 */
	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	public void setShopCategoryManager(ShopCategoryManager shopCategoryManager) {
		this.shopCategoryManager = shopCategoryManager;
	}

	@Override
	public Map<String, ShopCategoryListDO> getShopCategoryList(Long memberId) {
		try {
			return shopCategoryManager.queryShopCategoryByUser(memberId);
		} catch (ManagerException e) {
			log.error("Error [ActivityDiscountAOImpl.getShopCategoryList] 查找店铺类目异常,会员ID为:" + memberId, e);
		}
		return new HashMap<String, ShopCategoryListDO>();
	}

	@Override
	public List<ItemDO> getItemList(ItemQuery itemQuery) {
		try {
			return itemManager.getItemList(itemQuery);
		} catch (ManagerException e) {
			log.error("Error [ActivityDiscountAOImpl.getItemList] 查找商品列表异常", e);
		}
		return null;
	}

	@Override
	public int getItemPageCount(ItemQuery itemQuery) {
		try {
			return itemManager.getItemListCount(itemQuery);
		} catch (ManagerException e) {
			log.error("Error [ActivityDiscountAOImpl.getItemPageCount] 查找商品总数异常", e);
		}
		return 0;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Result updateActivityDiscount(final ActivityDiscountDO activityDiscount) {
		final Result result = new ResultSupport();
		result.setSuccess(false);
		return (Result) zizuTransactionTemplate.execute(new TransactionCallback() {
			Boolean success = true;

			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					// 查询原信息
					ActivityDiscountDO oriDiscount = activityDiscountManager.queryActivityDiscountById(activityDiscount
							.getId());

					if (oriDiscount != null) {
						boolean flag = true;
						// 判断活动状态，未开始的活动判断是否更新统计信息
						int activityStatus = 3;
						Date now = new Date();
						if (oriDiscount.getStatus().intValue() == 3) {
							// 活动已结束
							activityStatus = 3;
							success = false;
							result.getModels().put("errorMessage", "活动已删除，无法修改");
							return result;
						} else if (now.compareTo(oriDiscount.getStartTime()) >= 0
								&& now.compareTo(oriDiscount.getEndTime()) <= 0) {
							// 活动进行中
							activityStatus = 1;
						} else if (now.compareTo(oriDiscount.getStartTime()) < 0) {
							// 活动未开始
							activityStatus = 0;
						} else if (now.compareTo(oriDiscount.getEndTime()) > 0) {
							// 活动已结束
							activityStatus = 2;
							success = false;
							result.getModels().put("errorMessage", "活动已结束，无法修改");
							return result;
						}

						switch (activityStatus) {
						case 0:
							flag = handleActivityCycle(oriDiscount, activityDiscount);
							break;
						case 1:
							activityDiscount.setActName(null);
							activityDiscount.setStartTime(null);
							activityDiscount.setEndTime(null);
							break;
						default:
							break;
						}
						if (!flag) {
							success = false;
							result.getModels().put("errorMessage", "修改折扣活动时间失败");
							return result;
						}

						String oriItemList = oriDiscount.getItemList();
						String itemList = activityDiscount.getItemList();
						// 更新商品标签属性
						flag = handleItemFeatures(oriItemList, itemList, activityDiscount.getId().toString());
						if (!flag) {
							success = false;
							result.getModels().put("errorMessage", "更新商品标签失败");
							return result;
						}

						activityDiscount.setGmtModified(new Date());

						int count = activityDiscountManager.updateActivityDiscount(activityDiscount);
						if (log.isDebugEnabled()) {
							log.debug("更新限时折扣活动信息结果：" + (count == 1 ? true : false));
						}
						if (count == 1) {
							result.setSuccess(true);
						} else {
							success = false;
							result.getModels().put("errorMessage", "修改折扣活动信息失败");
							return result;
						}
						result.setSuccess(true);
					} else {
						log.error("原限时折扣活动信息查询失败！");
						success = false;
						result.getModels().put("errorMessage", "原折扣活动信息查询失败");
						return result;
					}

				} catch (ManagerException e) {
					success = false;
					result.getModels().put("errorMessage", "更新限时折扣失败");
					log.error("Error [更新限时折扣异常]", e);
				} finally {
					if (!success) {
						status.setRollbackOnly();
					}
				}
				return result;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public Result handlerActivityDiscountMap(final ActivityDiscountDO activityDiscount) {
		final Result result = new ResultSupport();
		result.setSuccess(false);
		return (Result) zizuTransactionTemplate.execute(new TransactionCallback() {
			Boolean success = true;

			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					// 先删除原有活动所含商品信息

					int count = activityDiscountMapManager.deleteActivityDiscountMapByActId(activityDiscount.getId()); // 删除记录数应该等于商品列表数
					log.debug("删除原有活动商品记录数为：" + count);

					boolean flag = handlerItemList(activityDiscount);
					if (!flag) {
						success = false;
						result.getModels().put("errorMessage", "插入折扣活动商品信息失败");
						return result;
					}
					result.setSuccess(true);
				} catch (Exception e) {
					success = false;
					result.getModels().put("errorMessage", "更新折扣活动商品信息失败");
					log.error("【ERROR 更新限时折扣活动商品信息异常】：", e);
					return result;
				} finally {
					if (!success) {
						status.setRollbackOnly();
					}
				}
				return result;
			}
		});
	}

	private boolean handlerItemList(ActivityDiscountDO activityDiscount) {
		// 活动商品列表
		String itemList = activityDiscount.getItemList();
		// 活动折扣列表
		String discountList = activityDiscount.getDiscountList();
		// 活动限购列表
		String limitList = activityDiscount.getLimitList();

		try {
			if (StringUtil.isEmpty(itemList) || StringUtil.isEmpty(discountList) || StringUtil.isEmpty(limitList)) {
				log.warn("活动的商品列表为空，无商品参加活动");
				return true;
			} else {
				String[] items = itemList.split(",");
				String[] discounts = discountList.split(",");
				String[] limits = limitList.split(",");

				if (!(items.length == discounts.length && items.length == limits.length)) {
					log.error("商品列表、折扣列表和限购列表数量不同，无法修改");
					return false;
				}

				for (int i = 0; i < items.length; i++) {
					ActivityDiscountMapDO map = new ActivityDiscountMapDO();
					map.setActId(activityDiscount.getId());
					map.setActName(activityDiscount.getActName());
					map.setDiscount(Integer.valueOf(discounts[i]));
					map.setDiscountDefault(activityDiscount.getDiscountDefault().intValue());
					map.setEndTime(activityDiscount.getEndTime());
					map.setStartTime(activityDiscount.getStartTime());
					map.setItemId(Long.valueOf(items[i]));
					map.setLimitList(Integer.parseInt(limits[i]));
					map.setMemberId(activityDiscount.getMemberId());
					map.setMemberNick(activityDiscount.getMemberNick());
					map.setGmtCreate(new Date());
					activityDiscountMapManager.insertActivityDiscountMap(map);
				}
				return true;
			}
		} catch (NumberFormatException e) {
			log.error("限时折扣活动商品信息转换异常！");
			return false;
		} catch (ManagerException e) {
			log.error("插入限时折扣活动商品信息异常！");
			return false;
		}
	}

	private boolean handleActivityCycle(ActivityDiscountDO oriDiscount, ActivityDiscountDO activityDiscount)
			throws ManagerException {
		long oriCycle = getDurations(oriDiscount);
		long cycle = getDurations(activityDiscount);

		if (oriCycle == cycle) {
			// 原活动时长和现时长相等，不需要更新统计信息，直接返回
			log.debug("原活动时长和现时长相等，不需要更新统计信息");
			return true;
		} else {
			// 原活动时长和现时长不相等，需要更新统计信息，返回更新结果
			Long diffrence = cycle - oriCycle;
			Result result = activityDiscountStatManager.stat(oriDiscount.getMemberId(), oriDiscount.getMemberNick(),
					diffrence, 0L);
			log.debug("更新卖家折扣活动统计信息结果：" + result.isSuccess());
			return result.isSuccess();
		}
	}

	private long getDurations(ActivityDiscountDO activityDiscount) {
		return (activityDiscount.getEndTime().getTime() - activityDiscount.getStartTime().getTime()) / (1000 * 60 * 60);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result addDiscountActivity(final ActivityDiscountDO activityDiscountDO) {
		final Result result = new ResultSupport();
		result.setSuccess(true);

		return (Result) zizuTransactionTemplate.execute(new TransactionCallback() {
			Boolean success = true;

			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					// 添加限时折扣
					Long i = activityDiscountManager.addActivityDiscount(activityDiscountDO);
					if (i < 0) {
						success = false;
						result.setSuccess(false);
						result.getModels().put("errorMessage", "添加限时折扣失败");
						log.warn("[addDiscountActivity] is error, paramater is [" + activityDiscountDO + "]");
						return result;
					}

//					String iteList[] = activityDiscountDO.getItemList().split(",");
//					String disCountList[] = activityDiscountDO.getDiscountList().split(",");
//					String limitList[] = activityDiscountDO.getLimitList().split(",");
//					int length = iteList.length;
//					if (length != disCountList.length && length != limitList.length) {
//						success = false;
//						result.setSuccess(false);
//						result.getModels().put("errorMessage", "添加限时折扣失败");
//						log.warn("[addDiscountActivity] is error, iteLsit:[" + iteList + "], disCountList:["
//								+ disCountList + "], limitList:[" + limitList + "]");
//						return result;
//					}
//
//					for (int k = 0; k < length; k++) {
//						ActivityDiscountMapDO map = new ActivityDiscountMapDO();
//						map.setActId(activityDiscountDO.getId());
//						map.setActName(activityDiscountDO.getActName());
//						map.setDiscount(Integer.valueOf(disCountList[k]));
//						map.setDiscountDefault(activityDiscountDO.getDiscountDefault().intValue());
//						map.setEndTime(activityDiscountDO.getEndTime());
//						map.setStartTime(activityDiscountDO.getStartTime());
//						map.setItemId(Long.valueOf(iteList[k]));
//						map.setLimitList(Integer.parseInt(limitList[k]));
//						map.setMemberId(activityDiscountDO.getMemberId());
//						map.setMemberNick(activityDiscountDO.getMemberNick());
//						map.setGmtCreate(new Date());
//						activityDiscountMapManager.insertActivityDiscountMap(map);
//					}

					// 修改限时折扣统计表
					Long countTime = activityDiscountDO.getEndTime().getTime()
							- activityDiscountDO.getStartTime().getTime();
					Long time = countTime / (1000 * 60 * 60);
					Result re = activityDiscountStatManager.stat(activityDiscountDO.getMemberId(),
							activityDiscountDO.getMemberNick(), time, 1l);
					if (!re.isSuccess()) {
						success = false;
						result.setSuccess(false);
						result.getModels().put("errorMessage", "添加限时折扣统计表失败");
						return result;
					}

					// 标记限时折扣商品
					String itemList = activityDiscountDO.getItemList();
					if (!itemList.equals("0")) {
						String item[] = itemList.split(",");
						for (int k = 0; k < item.length; k++) {
							Result resul = itemManager.setFeatures(Long.parseLong(item[k]),
									ItemFeaturesEnum.ITEM_LIMIT_TIME_DISCOUNT, i.toString());
							if (!resul.isSuccess()) {
								success = false;
								result.setSuccess(false);
								result.getModels().put("errorMessage", "商品限时折扣标记失败");
								return result;
							}
						}
					}

				} catch (ManagerException e) {
					success = false;
					result.setSuccess(false);
					result.getModels().put("errorMessage", "添加限时折扣失败");
					log.error("Error [添加限时折扣异常]", e);
				} finally {
					if (!success) {
						status.setRollbackOnly();
					}
				}
				return result;
			}
		});
	}

	private boolean handleItemFeatures(String oriItemList, String itemList, String activityId) throws ManagerException {
		boolean flag = true;
		if (StringUtils.isEmpty(oriItemList) || "0".equals(oriItemList)) {
			// 原列表为空
			if (StringUtils.isNotEmpty(itemList)) { // 现列表不为空，新商品列表添加标签
				String[] items = itemList.split(",");
				for (String itemId : items) {
					flag = setFeatures(itemId, activityId);
					if (!flag) {
						log.error("添加标签失败-1！");
						return flag;
					}
				}
			}
		} else {
			// 原列表不为空
			if (StringUtils.isEmpty(itemList)) { // 现列表为空， 删除原商品列表标签
				String[] items = oriItemList.split(",");
				for (String itemId : items) {
					flag = setFeatures(itemId, null);
					if (!flag) {
						log.error("更新标签失败-2！");
						return flag;
					}
				}
			} else { // 现列表不为空，判断商品列表差异
				String[] oriItemIds = oriItemList.split(",");
				String[] itemIds = itemList.split(",");
				flag = dispatcher(oriItemIds, itemIds, activityId);
				if (!flag) {
					log.error("更新标签失败-3！");
					return flag;
				}
			}
		}
		return flag;
	}

	private boolean dispatcher(String[] ori, String[] des, String activityId) throws ManagerException {
		List<String> oriList = Arrays.asList(ori);
		List<String> desList = Arrays.asList(des);
		Set<String> repeatSet = new HashSet<String>(oriList);
		repeatSet.addAll(desList);
		boolean flag = true;
		for (String itemId : oriList) {
			// 原列表有的，现在没有，去掉标签（且不为0）
			if (!"0".equals(itemId) && repeatSet.contains(itemId) && !desList.contains(itemId)) {
				flag = setFeatures(itemId, null);
				if (!flag) {
					log.error("更新标签失败-4！");
					return flag;
				}
			}
		}
		for (String itemId : desList) {
			// 原列表没有，现在有的，打上标签
			if (repeatSet.contains(itemId) && !oriList.contains(itemId)) {
				flag = setFeatures(itemId, activityId);
				if (!flag) {
					log.error("设置标签失败-5！");
					return flag;
				}
			}
			// TODO 原列表中的是否需要刷新标签
		}
		return flag;
	}

	/**
	 * 调用接口设置商品标签
	 * 
	 * @param itemId
	 * @param features
	 * @return
	 * @throws ManagerException
	 */
	private boolean setFeatures(String itemId, String features) throws ManagerException {
		if (isNumericString(itemId)) {
			long id = Long.valueOf(itemId);
			Result result = itemManager.setFeatures(id, ItemFeaturesEnum.ITEM_LIMIT_TIME_DISCOUNT, features);
			if (result.isSuccess()) {
				// 删除商品缓存 add by liuboen
				categoryCacheManager.deleteItemDOCache(id);
			}
			return result.isSuccess();
		}
		// 未更新，直接返回true
		return true;
	}

	/**
	 * 判断字符串是否为空和是否为数字型字符串
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNumericString(String str) {
		return StringUtils.isNotEmpty(str) && StringUtils.isNumeric(str);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteActivityDiscountById(final Long id) {
		/*
		 * boolean flag = false; try { flag =
		 * activityDiscountManager.deleteActivityDiscountById(id); } catch
		 * (ManagerException e) { log.error("删除限时折扣活动异常：", e); throw new
		 * Exception("删除限时折扣活动异常：", e); } return flag;
		 */

		return (Boolean) zizuTransactionTemplate.execute(new TransactionCallback() {
			Boolean success = true;

			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					// 查询原信息
					ActivityDiscountDO oriDiscount = activityDiscountManager.queryActivityDiscountById(id);

					if (oriDiscount != null) {
						Long activityStatus = oriDiscount.getStatus();
						// 对于未开始的活动删除时，不修改时间次数限制，但要更新商品标签
						// 对于已结束的活动删除时，a:状态为1的活动未同步标签，需要更新
						if (activityStatus.intValue() == 0 || activityStatus.intValue() == 1) {
							String itemList = oriDiscount.getItemList();
							// 更新商品标签属性
							if (StringUtils.isNotEmpty(itemList)) { // 列表不为空
								String[] items = itemList.split(",");
								for (String itemId : items) {
									success = setFeatures(itemId, null);
									if (!success) {
										log.error("删除标签失败！");
										return success;
									}
								}
							}
						}
						// 对于已结束的活动删除时，b:状态为2的活动已同步标签，不需更新
						success = activityDiscountManager.deleteActivityDiscountById(id);
						if (log.isDebugEnabled()) {
							log.debug("删除限时折扣活动信息结果：" + success);
						}
					} else {
						log.error("原限时折扣活动信息查询失败！");
						success = false;
					}

				} catch (ManagerException e) {
					success = false;
					log.error("Error [删除限时折扣异常]", e);
				} finally {
					if (!success) {
						status.setRollbackOnly();
					}
				}
				return success;
			}
		});
	}

	public void setActivityDiscountMapManager(ActivityDiscountMapManager activityDiscountMapManager) {
		this.activityDiscountMapManager = activityDiscountMapManager;
	}

	public void deleteActivityDiscountMapByActivityId(Long activityId) {
		try {
			int count = activityDiscountMapManager.deleteActivityDiscountMapByActId(activityId);
			log.debug("删除限时折扣商品信息表记录数：" + count);
		} catch (ManagerException e) {
			log.error("删除限时折扣商品信息异常：", e);
		}
	}

}
