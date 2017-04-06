package com.yuwang.pinju.web.module.active.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.core.active.ao.ActivityDiscountAO;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.search.manager.SearchManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.active.ActivityDiscountQuery;
import com.yuwang.pinju.domain.active.ActivityDiscountStatDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.search.SearchShopItem;
import com.yuwang.pinju.domain.search.result.ItemResult;
import com.yuwang.pinju.domain.search.result.SearchResult;
import com.yuwang.pinju.domain.shop.ItemCategorizeDO;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.struts2.LoginChecker;
import com.yuwang.pinju.web.struts2.LoginChecker.LoginCheckerResult;

/**
 * 促销管理-限时打折
 * 
 * @author gongjiayun
 * 
 */
public class ActivityDiscountAction extends BaseAction {

	private static final String ERROR_MESSAGE = "errorMessage";

	private static final int ITEM_PER_PAGE = 20;

	private static Log log = LogFactory.getLog(ActivityDiscountAction.class);

	private static final long SECOND_PER_HOUR = 60 * 60 * 1000;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ActivityDiscountDO activityDiscount;

	private ActivityDiscountAO activityDiscountAO;

	private List<ActivityDiscountDO> activityDiscountList;

	private ActivityDiscountQuery activityDiscountQuery;

	private ActivityDiscountStatDO activityDiscountStat;

	/**
	 * 总条数（商品列表）
	 */
	private Integer allCount;

	/**
	 * 总的页数
	 */
	private Integer allPages;

	/**
	 * 用户选中的当前分类
	 */
	private String categoryId = "";

	// *****************以下是限时打折添加参数
	/*
	 * 折扣
	 */
	private String[] discontPrice;

	/*
	 * 默认折扣
	 */
	private String discountDefault;

	/*
	 * 限购量
	 */
	private String[] disnum;

	/*
	 * 结束时间
	 */
	private String endTime;

	/*
	 * 错误消息
	 */
	private String errorMessage;

	private List<ItemCategorizeDO> itemCategorizeList;

	/*
	 * 商品ID
	 */
	private String[] itemId;

	/*
	 * 会员ID
	 */
	private String mbd;

	/**
	 * 查询商品名称
	 */
	private String itemName;

	private ItemQuery itemQuery;

	private List<ItemDO> itemQueryList;

	/**
	 * 分页的当前页码
	 */
	private Integer pageId = 1;

	/**
	 * 返回JSON结果
	 */
	private String result;

	/**
	 * 搜索引擎接口
	 */
	private SearchManager searchManager;
	
	private SearchShopItem searchShopItem;

	private List<ItemDO> selectedItemList;

	private Map<String, ShopCategoryListDO> shopCategoryList;

	private ShopShowInfoManager shopShowInfoManager;

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 商品ID列表
	 */
	private String[] selectedItemId;

	/**
	 * 折扣列表
	 */
	private String[] discount_rate;

	/**
	 * 折扣限制
	 */
	private String[] discount_limit;

	/**
	 * 默认折扣
	 */
	private String defaultDiscount;

	/**
	 * 商品总数
	 */
	private String itemCount;

	/**
	 * 默认折扣
	 */
	private String batchRate;

	/**
	 * 添加限时打折活动
	 * 
	 * @return
	 */
	public String addActivityDiscount() {

		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}

		long member = loginChecker.getMemberId();
		if (!(MemberIdPuzzle.decode(mbd) == member)) {
			log.debug("操作会员信息与提交会员信息不符");
			errorMessage = "操作会员信息与提交会员信息不符!";
			return ERROR;
		}

		// 活动名称不能为空
		if (activityDiscount.getActName() == null || "".equals(activityDiscount.getActName())) {
			log.debug("活动名称不能为空");
			errorMessage = "活动名称不能为空";
			return ERROR;
		}

		// 活动名称不能大于40个字符
		if (activityDiscount.getActName().length() > 40) {
			log.debug("活动名称不能大于40个字符");
			errorMessage = "活动名称不能大于40个字符";
			return ERROR;
		}

		Long currenDate = System.currentTimeMillis();
		try {
			Date sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
			Date edt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
			Long startDate = sdt.getTime();
			Long endDate = edt.getTime();

			// 开始时间不能小于当前时间
			if (startDate < currenDate) {
				log.debug("开始时间不能小于当前时间");
				errorMessage = "开始时间不能小于当前时间";
				return ERROR;
			}

			// 开始时间不能大于结束时间
			if (startDate > endDate) {
				log.debug("开始时间不能大于结束时间");
				errorMessage = "开始时间不能大于结束时间";
				return ERROR;
			}

			// 单个活动时长不能超过240个小时
			Long leftTime = endDate - startDate;
			Long time = leftTime / (1000 * 60 * 60);
			if (time > 240) {
				log.debug("单个活动时长不能超过240个小时");
				errorMessage = "单个活动时长不能超过240个小时";
				return ERROR;
			}

			activityDiscount.setStartTime(sdt);
			activityDiscount.setEndTime(edt);

		} catch (ParseException e) {
			log.error("Error 日期格式化异常", e);
		}

		// 一个活动的商品数不能大于20个
		if (discontPrice != null) {
			int itemNum = discontPrice.length;
			if (itemNum > 20) {
				log.debug("一个活动的商品数不能大于20个");
				errorMessage = "一个活动的商品数不能大于20个";
				return ERROR;
			}

			// 折扣不能为空,不能为0,不能大于9.5折
			for (int i = 0; i < discontPrice.length; i++) {
				String dpStr = discontPrice[i];
				if (dpStr == null || "".equals(dpStr)) {
					log.debug("折扣不能为空");
					errorMessage = "折扣不能为空";
					return ERROR;
				}
				Long discountP = getCents(dpStr);
				if (discountP == 0) {
					log.debug("折扣不能为0");
					errorMessage = "折扣不能为0";
					return ERROR;
				}

				if (discountP > 950) {
					log.debug("折扣不能大于9.5折");
					errorMessage = "折扣不能大于9.5折";
					return ERROR;
				}
			}

			StringBuilder itemIdBuilder = new StringBuilder();
			StringBuilder disCountBuilder = new StringBuilder();
			StringBuilder disNumBuilder = new StringBuilder();
			for (int k = 0; k < discontPrice.length; k++) {

				itemIdBuilder.append(itemId[k]);
				disCountBuilder.append(getCents(discontPrice[k]));
				if (disnum[k] == null || "".equals(disnum[k])) {
					disNumBuilder.append("0");
				} else {
					disNumBuilder.append(disnum[k]);
				}

				if (k != discontPrice.length - 1) {
					itemIdBuilder.append(",");
					disCountBuilder.append(",");
					disNumBuilder.append(",");
				}
			}

			activityDiscount.setDiscountList(disCountBuilder.toString());
			activityDiscount.setItemList(itemIdBuilder.toString());
			activityDiscount.setLimitList(disNumBuilder.toString());
			activityDiscount.setItemCount((long) discontPrice.length);
		} else {
			activityDiscount.setDiscountList("0");
			activityDiscount.setItemList("0");
			activityDiscount.setLimitList("0");
			activityDiscount.setItemCount(0l);
		}

		if (discountDefault == null || "".equals(discountDefault)) {
			activityDiscount.setDiscountDefault(0l);
		} else {
			Long disDefault = getCents(discountDefault);
			activityDiscount.setDiscountDefault(disDefault);
		}

		Long memberId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
		String nickName = CookieLoginInfo.getCookieLoginInfo().getNickname();
		activityDiscount.setMemberId(memberId);
		activityDiscount.setMemberNick(nickName);
		activityDiscount.setStatus(0l);

		try {
			Result result = activityDiscountAO.addDiscountActivity(activityDiscount);
			if (!result.isSuccess()) {
				log.debug(result.getModels().get("errorMessage"));
				errorMessage = "添加限时折扣活动失败";
				return ERROR;
			}
		} catch (Exception e) {
			log.error("Error [ActivityDiscountAction.addActivityDiscount] 添加限时折扣异常", e);
		}

		try {
			log.debug("延迟500ms，等待数据库同步。。。");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			log.error("List Activity Interrupted Exception:", e);
		}
		return SUCCESS;
	}

	public String addDiscount() {

		Long memberId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
		mbd = MemberIdPuzzle.encode(memberId);
		try {
			shopCategoryList = activityDiscountAO.getShopCategoryList(memberId);

			// 查询默认记录所对应的总页数，便于初始化时总页数的显示
			SearchResult searchResult = searchItemByShop(memberId);
			searchResult.setAction("/active/itemList.htm");

			request.setAttribute("query", searchResult);

			activityDiscountStat = activityDiscountAO.queryActivityDiscountStateByMemberId(memberId);

			if (activityDiscountStat == null) {
				request.setAttribute("surplus", 500);
			} else {
				request.setAttribute("surplus", 500 - activityDiscountStat.getUsedTime());
			}
		} catch (Exception e) {
			log.error("Error [ActivityDiscountAction.addDiscount] error", e);
		}

		return INPUT;
	}

	/**
	 * 将商品ID列表转换为List
	 * 
	 * @param itemListString
	 * @return
	 */
	private List<Long> changeStringToLong(String itemListString) {
		List<Long> itemIdList = new ArrayList<Long>();
		String[] itemIdStrings = itemListString.split(",");
		for (String itemIdString : itemIdStrings) {
			if (StringUtils.isNotEmpty(itemIdString) && StringUtils.isNumeric(itemIdString)) {
				Long itemId = Long.valueOf(itemIdString);
				itemIdList.add(itemId);
			}
		}
		return itemIdList;
	}

	private JSONArray convertListToJSONArray(List<ItemCategorizeDO> itemCategorizeDOList) {
		JSONArray array = new JSONArray();
		for (ItemCategorizeDO itemCategorizeDO : itemCategorizeDOList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", itemCategorizeDO.getId());
			jsonObject.put("title", itemCategorizeDO.getTitle());
			jsonObject.put("code", itemCategorizeDO.getCode());
			jsonObject.put("picUrl", itemCategorizeDO.getPicUrl());
			jsonObject.put("price", itemCategorizeDO.getPrice());
			jsonObject.put("itemCates", itemCategorizeDO.getItemCates());
			jsonObject.put("featuresMap", itemCategorizeDO.getFeaturesMap());
			jsonObject.put("curStock", itemCategorizeDO.getCurStock());

			// 附带总页数信息
			jsonObject.put("pages", allCount / ITEM_PER_PAGE + 1);
			array.add(jsonObject);
		}
		return array;
	}

	/**
	 * 生成商品当前归类列表（名称 一级分类-->二级分类）
	 * 
	 * @param cateIds
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> createCateNameList(String[] cateIds) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		for (String cateId : cateIds) {
			if (cateId == null || "".equals(cateId.trim()))
				continue;
			for (String key : shopCategoryList.keySet()) {
				ShopCategoryListDO category = shopCategoryList.get(key);
				if (category.getSubCategoryMap().size() == 0 && cateId.equals(category.getId())) {// 只有一级目录
					map.put(cateId, category.getCategoryName());
					break;
				} else if (category.getSubCategoryMap().containsKey(cateId)) {
					map.put(cateId, category.getCategoryName() + "</span><span class=\"aro\"></span><span>"
							+ category.getSubCategoryMap().get(cateId));
					break;
				}
			}
		}
		return map;
	}

	/**
	 * 删除限时折扣活动
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteActivityDiscount() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		long memberId = loginChecker.getMemberId();
		String pageMemberId = getString("pj0");
		if (!(MemberIdPuzzle.decode(pageMemberId) == memberId)) {
			log.debug("操作会员信息与提交会员信息不符");
			request.setAttribute(ERROR_MESSAGE, "登录信息已失效，请重试");
			return ERROR_MESSAGE;
		}
		Long id = getLong("id");
		log.debug("Delete Discount Activity By ID : " + id);
		if (id == null || id.longValue() == 0) {
			request.setAttribute(ERROR_MESSAGE, "删除折扣活动失败");
		} else {
			boolean flag = activityDiscountAO.deleteActivityDiscountById(id);
			if (!flag) {
				request.setAttribute(ERROR_MESSAGE, "删除折扣活动失败");
				//return SUCCESS;
			}
			//activityDiscountAO.deleteActivityDiscountMapByActivityId(id);
		}

		try {
			log.debug("延迟500ms，等待数据库同步。。。");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			log.error("List Activity Interrupted Exception:", e);
		}
		return SUCCESS;
	}

	/**
	 * 更新限时折扣活动
	 * 
	 * @return
	 * @throws Exception
	 */
	public String doUpdateActivityDiscount() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		Long memberId = loginChecker.getMemberId();
		String pageMemberId = getString("pj0");
		if (!(MemberIdPuzzle.decode(pageMemberId) == memberId.longValue())) {
			log.debug("操作会员信息与提交会员信息不符");
			request.setAttribute(ERROR_MESSAGE, "折扣活动数据失效");
			return ERROR_MESSAGE;
		}
		log.debug("更新折扣活动信息：" + activityDiscount);
		if (!checkField(activityDiscount)) {
			log.debug("限时折扣活动信息校验失败");
			request.setAttribute(ERROR_MESSAGE, "折扣活动编辑失败");
			return ERROR_MESSAGE;
		}
		// if (checkToken()) {
		
		ActivityDiscountDO ori = activityDiscountAO.queryActivityDiscountById(activityDiscount.getId());
		if (ori == null) {
			log.debug("Query Discount Activity By ID Failure !");
			request.setAttribute(ERROR_MESSAGE, "查询折扣活动失败");
			return ERROR_MESSAGE;
		}
		
		if(!memberId.equals(ori.getMemberId())){
			log.debug("This Activity isn't created by this member!");
			request.setAttribute(ERROR_MESSAGE, "查询折扣活动失败");
			return ERROR_MESSAGE;
		}
		
		Result result = activityDiscountAO.updateActivityDiscount(activityDiscount);
		if (!result.isSuccess()) {
			log.debug("折扣活动编辑失败");
			request.setAttribute(ERROR_MESSAGE, result.getModel("errorMessage"));
			return ERROR_MESSAGE;
		}
		// }
		/*activityDiscount = activityDiscountAO.queryActivityDiscountById(activityDiscount.getId());
		result = activityDiscountAO.handlerActivityDiscountMap(activityDiscount);
		if (!result.isSuccess()) {
			log.debug("插入限时折扣活动商品信息失败");
			request.setAttribute(ERROR_MESSAGE, result.getModel("errorMessage"));
			return ERROR_MESSAGE;
		}*/
		try {
			log.debug("延迟500ms，等待数据库同步。。。");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			log.error("List Activity Interrupted Exception:", e);
		}
		
		return SUCCESS;
	}

	private boolean checkField(ActivityDiscountDO active) {
		if (active == null) {
			log.debug("折扣活动对象为空");
			return false;
		}
		// 对于未开始的活动，验证活动名称和活动时间
		if (active.getStatus() != null && active.getStatus().intValue() == 0) {
			if (active.getActName() == null || active.getActName().length() > 40) {
				log.debug("折扣活动名称错误");
				return false;
			}
			if (active.getStartTime() == null) {
				log.debug("折扣活动开始时间错误");
				return false;
			}
			if (active.getEndTime() == null) {
				log.debug("折扣活动结束时间错误");
				return false;
			}
			if (active.getStartTime().compareTo(active.getEndTime()) >= 0) {
				log.debug("折扣活动开始时间晚于折扣活动结束时间");
				return false;
			}
			if (active.getStartTime().compareTo(new Date()) < 0) {
				log.debug("折扣活动开始时间早于当前系统时间");
				return false;
			}
			if (getDuration(active.getStartTime(), active.getEndTime()) <= 0
					|| getDuration(active.getStartTime(), active.getEndTime()) > 240) {
				log.debug("单个活动时长必须在0到240小时之内");
				return false;
			}
		}
		// 商品总数
		if (itemCount == null || "".equals(itemCount.trim())) {
			log.debug("折扣活动商品总数错误:" + itemCount);
			return false;
		} else {
			active.setItemCount(Long.parseLong(itemCount));
		}
		// 默认折扣
		if (batchRate != null && !"".equals(batchRate.trim())) {
			if (batchRate.substring(batchRate.indexOf(".") + 1).length() > 2) {
				log.debug("折扣活动默认折扣错误:" + batchRate);
				return false;
			}
			Long disDefault = getCents(batchRate.trim());
			if (disDefault.intValue() <= 0 || disDefault.intValue() > 950) {
				// 不更新数据
				log.debug("折扣活动默认折扣错误:" + batchRate);
				// return false;
			} else {
				active.setDiscountDefault(disDefault);
			}
		} else {
			active.setDiscountDefault(0l);
		}
		// 商品列表
		if (selectedItemId == null || discount_limit == null || discount_rate == null) {
			active.setDiscountList(" ");
			active.setItemList(" ");
			active.setLimitList(" ");
			active.setItemCount(0l);
		} else {
			try {
				if (Integer.parseInt(itemCount) != selectedItemId.length) {
					log.debug("折扣活动商品总数统计错误：" + itemCount + " != " + selectedItemId.length);
					return false;
				}
				StringBuffer itemList = new StringBuffer();
				StringBuffer limitList = new StringBuffer();
				StringBuffer discountList = new StringBuffer();
				for (String selectedItem : selectedItemId) {
					if (selectedItem != null && !"".equals(selectedItem.trim())) {
						itemList.append(selectedItem).append(",");
					} else {
						log.debug("商品ID为空");
						return false;
					}
				}
				for (String limit : discount_limit) {
					if (limit != null && !"".equals(limit.trim())) {
						limitList.append(limit).append(",");
					} else {
						limitList.append(0).append(",");
					}
				}
				for (String rate : discount_rate) {
					if (rate != null && !"".equals(rate.trim())) {
						if (rate.substring(rate.indexOf(".") + 1).length() > 2) {
							log.debug("折扣活动商品折扣错误:" + rate);
							return false;
						}
						Long rateValue = getCents(rate);
						if (rateValue.intValue() <= 0 || rateValue.intValue() > 950) {
							log.debug("折扣活动商品折扣错误:" + rateValue);
							return false;
						}
						discountList.append(rateValue).append(",");
					} else {
						log.debug("折扣值为空");
						return false;
					}
				}

				active.setDiscountList(checkListStringEndsWith(discountList.toString()));
				active.setItemList(checkListStringEndsWith(itemList.toString()));
				active.setLimitList(checkListStringEndsWith(limitList.toString()));
			} catch (Exception e) {
				log.error("处理表单信息异常：", e);
				return false;
			}
		}
		return true;
	}

	private String checkListStringEndsWith(String listString) {
		if (listString.endsWith(",")) {
			return listString.substring(0, listString.lastIndexOf(","));
		}
		return listString;
	}

	public ActivityDiscountDO getActivityDiscount() {
		return activityDiscount;
	}

	public ActivityDiscountAO getActivityDiscountAO() {
		return activityDiscountAO;
	}

	public List<ActivityDiscountDO> getActivityDiscountList() {
		return activityDiscountList;
	}

	public ActivityDiscountQuery getActivityDiscountQuery() {
		return activityDiscountQuery;
	}

	public ActivityDiscountStatDO getActivityDiscountStat() {
		return activityDiscountStat;
	}

	public Integer getAllCount() {
		return allCount;
	}

	public Integer getAllPages() {
		return allPages;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public Long getCents(String cent) {
		Money money = new Money(cent);
		return money.getCent();

	}

	public String[] getDiscontPrice() {
		return discontPrice;
	}

	public String getDiscountDefault() {
		return discountDefault;
	}

	public String[] getDisnum() {
		return disnum;
	}

	private Long getDuration(Date start, Date end) {
		long duration = end.getTime() - start.getTime();
		return duration / SECOND_PER_HOUR;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List<ItemCategorizeDO> getItemCategorizeList() {
		return itemCategorizeList;
	}

	public String[] getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public ItemQuery getItemQuery() {
		return itemQuery;
	}

	public List<ItemDO> getItemQueryList() {
		return itemQueryList;
	}

	public Integer getPageId() {
		return pageId;
	}

	public String getResult() {
		return result;
	}

	public List<ItemDO> getSelectedItemList() {
		return selectedItemList;
	}

	public Map<String, ShopCategoryListDO> getShopCategoryList() {
		return shopCategoryList;
	}

	public String getStartTime() {
		return startTime;
	}

	public String itemList() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		Long memberId = loginChecker.getMemberId();

		shopCategoryList = activityDiscountAO.getShopCategoryList(memberId);

		Integer page = getInteger("page");
		if (page != null && page != 0) {
			pageId = page;
		}

		SearchResult searchResult = searchItemByShop(memberId);

		String type = getString("type");
		if (type != null && "refresh".equals(type)) {
			result = searchResult.getPages() + "";
			return SUCCESS;
		}

		List<Object> list = searchResult.getResultList();
		itemCategorizeList = new ArrayList<ItemCategorizeDO>();
		for (Object o : list) {
			ItemResult item = (ItemResult) o;
			ItemCategorizeDO itemCate = new ItemCategorizeDO();
			itemCate.setId(item.getId());
			itemCate.setTitle(item.getTitle());
			itemCate.setPicUrl(item.getPicUrl());
			itemCate.setPrice(item.getPrice());
			itemCate.setFeatures(item.getFeatures());
			itemCate.setCode(item.getCode());
			itemCate.setCurStock(item.getCurStock());
			itemCate.setItemCates(item.getId() + "_"
					+ (item.getStoreCategories() == null ? "" : item.getStoreCategories()));
			try {
				itemCate.setCategoryNameList(createCateNameList(item.getStoreCategories().split(",")));
			} catch (Exception ignore) {
				itemCate.setCategoryNameList(new HashMap<String, String>());
			}
			itemCategorizeList.add(itemCate);
		}

		JSONArray array = convertListToJSONArray(itemCategorizeList);
		result = array.toString();

		return SUCCESS;
	}

	/**
	 * 查询限时折扣活动列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String promotionManager() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}

		Long memberId = loginChecker.getMemberId();

		// 验证卖家是否开店
		ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(memberId, null);
		if(shopInfoDO==null||shopInfoDO.getShopId()==null){
			return "NOT_OPEN";
		}else{
			if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()==ShopConstant.APPROVE_STATUS_HEGUI){
				return "IS_CLOSE";	
			}else if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()!=ShopConstant.APPROVE_STATUS_YES){
				return "NOT_EXIST";
			}
		}

		request.setAttribute("pj0", MemberIdPuzzle.encode(memberId));

		if (activityDiscountQuery == null) {
			activityDiscountQuery = new ActivityDiscountQuery();
		}
		activityDiscountQuery.setMemberId(memberId);
		activityDiscountQuery.setMemberNick(loginChecker.getLogin().getNickname());
		log.debug("ActivityDiscountQuery : " + activityDiscountQuery);

		int currentPage = getInteger("currentPage");
		if (currentPage == 0) {
			currentPage = 1;
		}
		activityDiscountQuery.setItemsPerPage(ITEM_PER_PAGE);
		activityDiscountQuery.setPage(currentPage);

		Date now = new Date();
		activityDiscountQuery.setCheckTime(now);
		int count = activityDiscountAO.queryActivityDiscountPageCount(activityDiscountQuery);

		activityDiscountList = activityDiscountAO.queryActivityDiscountPageList(activityDiscountQuery);
		activityDiscountQuery.setAction("/active/promotionManager.htm");
		request.setAttribute("query", activityDiscountQuery);

		List<Long> durations = new ArrayList<Long>();
		if (activityDiscountList != null && activityDiscountList.size() > 0) {
			for (ActivityDiscountDO discount : activityDiscountList) {
				if (discount.getStatus().intValue() == 3) {
					discount.setStatus(3L);
					count--;
				} else if (discount.getEndTime().compareTo(now) < 0) {
					discount.setStatus(2L);
				} else if (discount.getStartTime().compareTo(now) > 0) {
					discount.setStatus(0L);
				} else if (discount.getEndTime().compareTo(now) >= 0 && discount.getStartTime().compareTo(now) <= 0) {
					discount.setStatus(1L);
				} else {
					discount.setStatus(3L);
					count--;
				}
				if (discount.getStartTime() != null && discount.getEndTime() != null) {
					durations.add(getDuration(discount.getStartTime(), discount.getEndTime()));
				} else {
					durations.add(0L);
				}
			}
		}
		activityDiscountQuery.setItems(count);
		request.setAttribute("durations", durations);

		activityDiscountStat = activityDiscountAO.queryActivityDiscountStateByMemberId(memberId);
		if (activityDiscountStat == null) {
			activityDiscountStat = new ActivityDiscountStatDO();
			activityDiscountStat.setMemberId(memberId);
			activityDiscountStat.setUsedNum(0L);
			activityDiscountStat.setUsedTime(0L);
		}

		return SUCCESS;
	}

	private SearchResult searchItemByShop(Long memberId) throws ManagerException {
		if (searchShopItem == null) {
			searchShopItem = new SearchShopItem();
		}
		searchShopItem.setCount(String.valueOf(ITEM_PER_PAGE));
		searchShopItem.setQ(itemName);
		searchShopItem.setStart(String.valueOf(pageId));
		searchShopItem.setSellerId(memberId);
		List<String> status = new ArrayList<String>();
		status.add("0");
		status.add("1");
		searchShopItem.setStatus(status);
		searchShopItem.setShopCategory(categoryId);
		SearchResult searchResult = searchManager.searchItemByShopFromDB(searchShopItem);
		allCount = searchResult.getItems();
		allPages = searchResult.getPages() == 0 ? 1 : searchResult.getPages();
		return searchResult;
	}

	public void setActivityDiscount(ActivityDiscountDO activityDiscount) {
		this.activityDiscount = activityDiscount;
	}

	public void setActivityDiscountAO(ActivityDiscountAO activityDiscountAO) {
		this.activityDiscountAO = activityDiscountAO;
	}

	public void setActivityDiscountList(List<ActivityDiscountDO> activityDiscountList) {
		this.activityDiscountList = activityDiscountList;
	}

	public void setActivityDiscountQuery(ActivityDiscountQuery activityDiscountQuery) {
		this.activityDiscountQuery = activityDiscountQuery;
	}

	public void setActivityDiscountStat(ActivityDiscountStatDO activityDiscountStat) {
		this.activityDiscountStat = activityDiscountStat;
	}

	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}

	public void setAllPages(Integer allPages) {
		this.allPages = allPages;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setDiscontPrice(String[] discontPrice) {
		this.discontPrice = discontPrice;
	}

	public void setDiscountDefault(String discountDefault) {
		this.discountDefault = discountDefault;
	}

	public void setDisnum(String[] disnum) {
		this.disnum = disnum;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setItemCategorizeList(List<ItemCategorizeDO> itemCategorizeList) {
		this.itemCategorizeList = itemCategorizeList;
	}

	public void setItemId(String[] itemId) {
		this.itemId = itemId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemQuery(ItemQuery itemQuery) {
		this.itemQuery = itemQuery;
	}

	public void setItemQueryList(List<ItemDO> itemQueryList) {
		this.itemQueryList = itemQueryList;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setSelectedItemList(List<ItemDO> selectedItemList) {
		this.selectedItemList = selectedItemList;
	}

	public void setShopCategoryList(Map<String, ShopCategoryListDO> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String updateActivityDiscount() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}
		if (activityDiscountQuery == null) {
			activityDiscountQuery = new ActivityDiscountQuery();
		}
		Long memberId = loginChecker.getMemberId();

		String pageMemberId = getString("pj0");
		if (!(MemberIdPuzzle.decode(pageMemberId) == memberId)) {
			log.debug("操作会员信息与提交会员信息不符");
			request.setAttribute(ERROR_MESSAGE, "登录信息已失效，请重试");
			return ERROR_MESSAGE;
		}

		request.setAttribute("pj0", MemberIdPuzzle.encode(memberId));
		// 查询限时折扣活动信息
		Long id = getLong("id");
		if (id.longValue() == 0) {
			log.debug("Discount Activity ID Missing !");
			request.setAttribute(ERROR_MESSAGE, "请选择要修改的折扣活动");
			return ERROR_MESSAGE;
		}
		if (log.isDebugEnabled()) {
			log.debug("Update Discount Activity By ID : " + id);
		}
		activityDiscount = activityDiscountAO.queryActivityDiscountById(id);

		if (activityDiscount == null) {
			log.debug("Query Discount Activity By ID Failure !");
			request.setAttribute(ERROR_MESSAGE, "查询折扣活动失败");
			return ERROR_MESSAGE;
		}
		
		if(!memberId.equals(activityDiscount.getMemberId())){
			log.debug("This Activity isn't created by this member!");
			request.setAttribute(ERROR_MESSAGE, "查询折扣活动失败");
			return ERROR_MESSAGE;
		}
		
		Date now = new Date();
		if (activityDiscount.getEndTime().compareTo(now) < 0) {
			activityDiscount.setStatus(2L);
		} else if (activityDiscount.getStartTime().compareTo(now) > 0) {
			activityDiscount.setStatus(0L);
		} else if (activityDiscount.getEndTime().compareTo(now) >= 0
				&& activityDiscount.getStartTime().compareTo(now) <= 0) {
			activityDiscount.setStatus(1L);
		} else {
			activityDiscount.setStatus(3L);
		}

		if (activityDiscount.getStatus().intValue() > 1) {
			log.debug("Discount Activity is over or delete !");
			request.setAttribute(ERROR_MESSAGE, "折扣活动已结束或已删除");
			return ERROR_MESSAGE;
		}
		// 将现在的小时差放入页面，以供修改判断（前提活动未开始）
		if (activityDiscount.getStatus() == 0) {
			Long duration = getDuration(activityDiscount.getStartTime(), activityDiscount.getEndTime());
			request.setAttribute("duration", duration);
		}

		// 将现在的小时差放入页面，以供修改判断（前提活动未开始）
		if (activityDiscount.getStatus() == 0) {
			Long duration = getDuration(activityDiscount.getStartTime(), activityDiscount.getEndTime());
			request.setAttribute("duration", duration);
		}

		// 查询折扣数据统计信息
		activityDiscountStat = activityDiscountAO.queryActivityDiscountStateByMemberId(memberId);
		if (activityDiscountStat == null) {
			request.setAttribute("surplus", 500);
		} else {
			request.setAttribute("surplus", 500 - activityDiscountStat.getUsedTime());
		}

		shopCategoryList = activityDiscountAO.getShopCategoryList(memberId);

		// 查询默认记录所对应的总页数，便于初始化时总页数的显示
		SearchResult searchResult = searchItemByShop(memberId);
		searchResult.setAction("/active/itemList.htm");

		request.setAttribute("query", searchResult);

		// TODO 查询卖家已选商品列表信息
		if (StringUtils.isNotEmpty(activityDiscount.getItemList())) {
			List<Long> itemIds = changeStringToLong(activityDiscount.getItemList());
			selectedItemList = activityDiscountAO.querySelectedItemList(itemIds);
			activityDiscount.setItemCount(new Long(selectedItemList.size()));
		}

		return SUCCESS;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public String[] getSelectedItemId() {
		return selectedItemId;
	}

	public void setSelectedItemId(String[] selectedItemId) {
		this.selectedItemId = selectedItemId;
	}

	public String[] getDiscount_rate() {
		return discount_rate;
	}

	public void setDiscount_rate(String[] discountRate) {
		discount_rate = discountRate;
	}

	public String[] getDiscount_limit() {
		return discount_limit;
	}

	public void setDiscount_limit(String[] discountLimit) {
		discount_limit = discountLimit;
	}

	public String getDefaultDiscount() {
		return defaultDiscount;
	}

	public void setDefaultDiscount(String defaultDiscount) {
		this.defaultDiscount = defaultDiscount;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getBatchRate() {
		return batchRate;
	}

	public void setBatchRate(String batchRate) {
		this.batchRate = batchRate;
	}

	public String getMbd() {
		return mbd;
	}

	public void setMbd(String mbd) {
		this.mbd = mbd;
	}
	
	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	public SearchShopItem getSearchShopItem() {
		return searchShopItem;
	}

	public void setSearchShopItem(SearchShopItem searchShopItem) {
		this.searchShopItem = searchShopItem;
	}

}
