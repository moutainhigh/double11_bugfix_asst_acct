package com.yuwang.pinju.core.active.ao;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.active.ActivityDiscountQuery;
import com.yuwang.pinju.domain.active.ActivityDiscountStatDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQuery;

import com.yuwang.pinju.domain.shop.ShopCategoryListDO;

public interface ActivityDiscountAO {
	/**
	 * 根据条件查询折扣活动列表
	 * @param request
	 * @param query
	 * @return
	 * @throws Exception
	 */
	List<ActivityDiscountDO> queryActivityDiscountPageList(ActivityDiscountQuery query);
	
	/**
	 * 根据条件查询折扣活动总数
	 * @param request
	 * @param query
	 * @return
	 * @throws Exception
	 */
	int queryActivityDiscountPageCount(ActivityDiscountQuery query);

	/**
	 * 根据折扣活动ID查询折扣活动
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ActivityDiscountDO queryActivityDiscountById(Long id);

	
	/**
	 * 查找店铺类目
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	Map<String, ShopCategoryListDO> getShopCategoryList(Long memberId);
	
	/**
	 * 获取商品列表
	 * @param itemQuery
	 * @return
	 * @throws Exception
	 */
	List<ItemDO> getItemList(ItemQuery itemQuery);
	
	/**
	 * 查询商品总数
	 * @param itemQuery
	 * @return
	 * @throws Exception
	 */
	int getItemPageCount(ItemQuery itemQuery);
	
	/**
	 * 根据卖家ID查询卖家折扣活动数量和时长信息
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	ActivityDiscountStatDO queryActivityDiscountStateByMemberId(Long memberId);

	/**
	 * 更新限时折扣活动
	 * @param activityDiscount
	 * @return
	 */
	Result updateActivityDiscount(ActivityDiscountDO activityDiscount);
	

	/**
	 * 根据已选择商品ID，查询商品列表，用于限时折扣活动更新
	 * @param itemIds
	 * @return
	 * @throws Exception
	 */
	List<ItemDO> querySelectedItemList(List<Long> itemIds);
	
	/**
	 * 根据限制条件查询卖家商品总数
	 * @param itemQuery
	 * @return
	 * @throws Exception
	 */
	int queryAllItemListCount(ItemQuery itemQuery);
	
	/**
	 * 根据限制条件查询卖家商品列表
	 * @param itemQuery
	 * @return
	 * @throws Exception
	 */
	List<ItemDO> queryAllItemList(ItemQuery itemQuery);
	
	/**
	 * 添加限时打折活动
	 * @param activityDiscount
	 * @return
	 * @throws Exception
	 */
	Result addDiscountActivity(ActivityDiscountDO activityDiscount);

	/**
	 * 根据活动ID删除限时折扣活动
	 * @param id
	 * @return
	 */
	boolean deleteActivityDiscountById(Long id);
	
	/**
	 * 插入活动商品信息关系
	 * @param activityDiscount
	 * @return
	 */
	public Result handlerActivityDiscountMap(ActivityDiscountDO activityDiscount);
	
	/**
	 * 删除限时活动对应的商品信息表
	 * @param activityId
	 */
	public void deleteActivityDiscountMapByActivityId(Long activityId);
}
