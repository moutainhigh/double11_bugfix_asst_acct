package com.yuwang.pinju.web.module.my.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.active.manager.ActivityDiscountManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.coupon.manager.TradeCouponBatchManager;
import com.yuwang.pinju.core.item.manager.CategoryManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.margin.manager.MarginManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.domain.Activity;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.active.ActivityDiscountQuery;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.LoginChecker;
import com.yuwang.pinju.web.struts2.LoginChecker.LoginCheckerResult;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopOpenFlowDO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.margin.MarginSellerDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;

public class MyMainAction implements Action {
	private ActivityDiscountManager activityDiscountManager;

	//private List<ActivityDiscountDO> activityList;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private ShopOpenFlowDO shopOpenFlowDO;
	private ShopOpenManager shopOpenManager;
	private ShopShowInfoManager shopShowInfoManager;
	private MarginManager marginManager;
	private ItemManager itemManager;
	private CategoryManager categoryManager;
	private OrderQueryManager orderQueryManager;
	private MemberManager memberManager;
	private SellerQualityDO sellerQualityDO;
	private ShopInfoDO shopInfoDO;
	private ShopBusinessInfoDO shopBusinessInfoDO;
	private long currentMargin;
	private Integer level;
	
	private int[] items = new int[4];

	private int sellerType;
	
	private String pj0;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String account() throws Exception {
		return SUCCESS;
	}

	public String buy() throws Exception {
		return SUCCESS;
	}
	
	public String iframePJNews() throws Exception {
		return SUCCESS;
	}
	
	public String iframePJTip() throws Exception {
		return SUCCESS;
	}
	
	public void updateMemberMyPageTypeByMemberId(){
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		Long memberId = loginChecker.getMemberId();
		try {
			memberManager.updateMemberMyPageTypeByMemberId(memberId, 2);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String sell() {
		try {
			LoginCheckerResult loginChecker = LoginChecker.check(log, true);
			if (!loginChecker.isSuccess()) {
				return loginChecker.getResult();
			}

//			Long memberId = loginChecker.getMemberId();
			Long memberId = CookieLoginInfo.getCookieLoginInfo().getMasterMemberId();
			if (log.isDebugEnabled()) {
				log.debug("Member Id: " + memberId);
			}

			// 检查开店流程，如果没有成功直接返回检查结果
			String result = checkShopOpenResult(memberId);
			if (!"success".equals(result)) {
				log.warn("开店流程未结束，跳转到开店流程。。。" + result);
				return result;
			}
			shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(memberId, null);
			// if(shopInfoDO.getShopLogo().equals("http://static.pinju.com/img/shop_default_logo.png")){
			// shopInfoDO.setShopLogo("");
			// }
			if (shopInfoDO != null) {
				if (shopInfoDO.getSellerType() != null
						&& shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_C))) {
					// List bList =
					// (List)shopOpenManager.queryShopBusinessInfo(memberId);
					// if(bList!=null && bList.size()>0){
					// shopBusinessInfoDO = (ShopBusinessInfoDO)bList.get(0);
					// if(shopBusinessInfoDO!=null&&shopBusinessInfoDO.getSellerNature()>=0){
					shopInfoDO.setSellerType(ShopConstant.SELLER_NATURE__C);
					// }
					// }
				} else if (shopInfoDO.getSellerType() != null
						&& shopInfoDO.getSellerType().equals(String.valueOf(ShopConstant.SELLER_TYPE_IShop))) {
					shopInfoDO.setSellerType(ShopConstant.SELLER_ISHOP);
				} else {
					shopInfoDO.setSellerType(ShopConstant.SELLER_NATURE_LIST_B.get(Integer.parseInt(shopInfoDO
							.getSellerType()) - 1));
				}
				CategoryDO categoryDO = categoryManager.getItemCategory(Long.parseLong(shopInfoDO.getShopCategory()));
				if (categoryDO != null) {
					shopInfoDO.setShopCategory(categoryDO.getName());
				}
			}
			ItemQuery itemQuery = new ItemQuery();
			itemQuery.setSellerId(memberId);
			// 商品状态
			List<Long> status = new ArrayList<Long>();
			status.add(ItemConstant.STATUS_TYPE_2);
			status.add(ItemConstant.STATUS_TYPE_4);
			status.add(ItemConstant.STATUS_TYPE_6);
			status.add(ItemConstant.STATUS_TYPE_7);
			status.add(ItemConstant.STATUS_TYPE_8);
			status.add(ItemConstant.STATUS_TYPE_9);
			itemQuery.setStatus(status);
			// 获取某状态下订单数量
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sellerId", memberId);
			map.put("orderState", 2);
			items[0] = orderQueryManager.getOrderListInTimeCount(map);
			map.clear();
			map.put("sellerId", memberId);
			List<Integer> refundState = new ArrayList<Integer>();
			refundState.add(RefundDO.STATUS_WAIT_SELLER_AGREE);
			refundState.add(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS);
			refundState.add(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS);
			refundState.add(RefundDO.STATUS_SELLER_REFUSE_BUYER);
			refundState.add(RefundDO.STATUS_CS_PROCESS);
			map.put("refundState", refundState);
			items[1] = orderQueryManager.getOrderListInTimeCount(map);
			map.clear();
			map.put("sellerId", memberId);
			map.put("orderState", 1);
			items[2] = orderQueryManager.getOrderListInTimeCount(map);
			// 获取待上架商品数量
			items[3] = itemManager.getItemListCount(itemQuery);

			// 获取卖家级别 0/S；1/A；2/B；3/C；4/D
			sellerQualityDO = memberManager.getSellerQualityByMemberId(memberId);
			if (sellerQualityDO != null && sellerQualityDO.getLevel() >= 0) {
				level = sellerQualityDO.getLevel();
			}
			// 获取卖家积分
			MarginSellerDO marginSellerDO = marginManager.getMarginSellerDOBySellerId(memberId);
			if (marginSellerDO != null && marginSellerDO.getCurrentMargin() > 0) {
				currentMargin = marginSellerDO.getCurrentMargin();
			} else {
				currentMargin = 0;
			}

			List<Activity> tempActivityList = new ArrayList<Activity>();

			// 查询卖家限时折扣活动信息
			List<ActivityDiscountDO> activityList = queryActivityDiscountList(memberId);
			convertDiscountActivity(activityList, tempActivityList);
			// 查询卖家优惠券活动信息
			List<TradeCouponBatchDO> tradeCouponBatchList = tradeCouponBatchManager
					.queryTradeCouponBatchByTop(memberId);
			convertCouponActivity(tradeCouponBatchList, tempActivityList);

			processAllActivityList(tempActivityList);

			log.debug(allActivityList);

			pj0 = MemberIdPuzzle.encode(memberId);
		}catch (Exception e) {
			log.error("卖家首页展现异常",e);
		}
		return SUCCESS;
	}
	
	private void processAllActivityList(List<Activity> tempActivityList) {
		if(EmptyUtil.isBlank(tempActivityList)){
			return;
		}
		Collections.sort(tempActivityList);
		if(tempActivityList.size() <= 5){
			allActivityList = new ArrayList<Activity>(tempActivityList);
		}else{
			allActivityList = new ArrayList<Activity>();
			for(int i = 0; i < 5; i++){
				allActivityList.add(tempActivityList.get(i));
			}
		}
	}

	private void convertCouponActivity(
			List<TradeCouponBatchDO> tradeCouponBatchList, List<Activity> tempActivityList) {
		if(EmptyUtil.isBlank(tradeCouponBatchList)){
			return;
		}
		for(TradeCouponBatchDO coupon : tradeCouponBatchList){
			Activity activity = new Activity();
			activity.setActivityId(coupon.getId());
			activity.setStartTime(coupon.getCouponCreateDate());
			activity.setEndTime(coupon.getCouponInvalidDate());
			activity.setTitle(coupon.getCouponMoneyByYuan());
			activity.setType(Activity.ACTIVITY_TYPE_COUPON);
			tempActivityList.add(activity);
		}
	}
	
	private void convertDiscountActivity(List<ActivityDiscountDO> activityList, List<Activity> tempActivityList) {
		if(EmptyUtil.isBlank(activityList)){
			return;
		}
		for(ActivityDiscountDO discount : activityList){
			Activity activity = new Activity();
			activity.setActivityId(discount.getId());
			activity.setStartTime(discount.getStartTime());
			activity.setEndTime(discount.getEndTime());
			activity.setTitle(discount.getActName());
			activity.setType(Activity.ACTIVITY_TYPE_DISCOUNT);
			tempActivityList.add(activity);
		}
	}


	/**
	 * 所有活动列表
	 */
	private List<Activity> allActivityList;
	
	/**
	 *	优惠券活动列表 
	 */
//	private List<TradeCouponBatchDO> tradeCouponBatchList;
	
	/**
	 * 优惠券管理类
	 */
	private TradeCouponBatchManager tradeCouponBatchManager;

	public List<Activity> getAllActivityList() {
		return allActivityList;
	}

	/*public List<TradeCouponBatchDO> getTradeCouponBatchList() {
		return tradeCouponBatchList;
	}*/

	public void setTradeCouponBatchManager(
			TradeCouponBatchManager tradeCouponBatchManager) {
		this.tradeCouponBatchManager = tradeCouponBatchManager;
	}

	/**
	 * 检查开店流程结果
	 * @param memberId
	 * @return
	 */
	private String checkShopOpenResult(Long memberId) {
		String result = "unKnown";
		try {
			List<ShopOpenFlowDO> resultList = (ArrayList<ShopOpenFlowDO>) shopOpenManager
					.queryShopOpenFlow(memberId);

			if (resultList != null && resultList.size() > 0) {
				shopOpenFlowDO = resultList.get(0);
			}else{
				shopOpenFlowDO = null;
			}
			if (shopOpenFlowDO != null) {
				switch (shopOpenFlowDO.getAuditStatus().intValue()) {
				// 审核未通过
				case -1:
					result = "unPassed";
					break;
				// 未审核
				case 0:
					result = "unChecked";
					break;
				// 审核已通过
				case 1:
					result = "passed";
					break;
				// 开店已成功
				case 2:
					result = "success";
					break;
				default:
					result = "unChecked";
					break;
				}
			} else {
				// 开店状态未知
//				ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(memberId,null);
//				if (shopInfoDO != null) {
//					sellerType = Integer.parseInt(shopInfoDO.getSellerType());
//				} else {
//					sellerType = -1;
//				}
				result = "unApply";
			}
		} catch (ManagerException e) {
			log.error("检查卖家（" + memberId + "）开店流程结果异常：", e);
		}
		return result;
	}

	/**
	 * 查询限时折扣活动
	 * 
	 * @param memberId
	 * @return
	 */
	private List<ActivityDiscountDO> queryActivityDiscountList(Long memberId) {
		List<ActivityDiscountDO> tempList = new ArrayList<ActivityDiscountDO>();
		ActivityDiscountQuery activityDiscountQuery = new ActivityDiscountQuery();
		activityDiscountQuery.setMemberId(memberId);
		activityDiscountQuery.setItemsPerPage(5);
		activityDiscountQuery.setPage(1);
		activityDiscountQuery.setStatus(1);
		activityDiscountQuery.setCheckTime(new Date());
		log.debug("ActivityDiscountQuery : " + activityDiscountQuery);
		try {
			List<ActivityDiscountDO> resultList = activityDiscountManager
					.queryActivityDiscountPageList(activityDiscountQuery);
			if(resultList != null && resultList.size() > 0){
				for(ActivityDiscountDO activity : resultList){
					if (activity.getStatus().intValue() == 0 || activity.getStatus().intValue() == 1) {
						tempList.add(activity);
					}
				}
			}
		} catch (ManagerException e) {
			log.error("查询卖家（" + memberId + "）折扣活动信息异常：", e);
		}
		return tempList;
	}

	public ActivityDiscountManager getActivityDiscountManager() {
		return activityDiscountManager;
	}

	public void setActivityDiscountManager(
			ActivityDiscountManager activityDiscountManager) {
		this.activityDiscountManager = activityDiscountManager;
	}

	/*public List<ActivityDiscountDO> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<ActivityDiscountDO> activityList) {
		this.activityList = activityList;
	}*/

	public ShopOpenFlowDO getShopOpenFlowDO() {
		return shopOpenFlowDO;
	}

	public void setShopOpenFlowDO(ShopOpenFlowDO shopOpenFlowDO) {
		this.shopOpenFlowDO = shopOpenFlowDO;
	}

	public ShopOpenManager getShopOpenManager() {
		return shopOpenManager;
	}

	public void setShopOpenManager(ShopOpenManager shopOpenManager) {
		this.shopOpenManager = shopOpenManager;
	}
	
	public MemberManager getMemberManager() {
		return memberManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}
	
	public MarginManager getMarginManager() {
		return marginManager;
	}

	public void setMarginManager(MarginManager marginManager) {
		this.marginManager = marginManager;
	}
	
	public CategoryManager getCategoryManager() {
		return categoryManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
	
	public OrderQueryManager getOrderQueryManager() {
		return orderQueryManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}
	
	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	public int getSellerType() {
		return sellerType;
	}

	public void setSellerType(int sellerType) {
		this.sellerType = sellerType;
	}
	
	public int[] getItems() {
		return items;
	}

	public void setItems(int[] items) {
		this.items = items;
	}
	
	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}
	
	public ShopInfoDO getShopInfoDO() {
		return shopInfoDO;
	}

	public void setShopInfoDO(ShopInfoDO shopInfoDO) {
		this.shopInfoDO = shopInfoDO;
	}
	
	public ShopBusinessInfoDO getShopBusinessInfoDO() {
		return shopBusinessInfoDO;
	}
	
	public SellerQualityDO getSellerQualityDO() {
		return sellerQualityDO;
	}
	
	public void setShopBusinessInfoDO(ShopBusinessInfoDO shopBusinessInfoDO) {
		this.shopBusinessInfoDO = shopBusinessInfoDO;
	}
	
	public long getCurrentMargin() {
		return currentMargin;
	}

	public void setCurrentMargin(long currentMargin) {
		this.currentMargin = currentMargin;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPj0() {
		return pj0;
	}
	
}
