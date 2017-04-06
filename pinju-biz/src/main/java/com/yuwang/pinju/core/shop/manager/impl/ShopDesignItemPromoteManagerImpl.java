/**
 * 
 */
package com.yuwang.pinju.core.shop.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.yuwang.pinju.Constant.ShopDecorationConstant;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.search.manager.SearchManager;
import com.yuwang.pinju.core.shop.manager.ShopBaseDesignerManager;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.result.SearchResult;
import com.yuwang.pinju.domain.shop.SearchResultExt;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

/**
 * @author liyouguo
 * 
 */
public class ShopDesignItemPromoteManagerImpl extends
		ShopBaseDesignerManagerImpl implements ShopBaseDesignerManager {

	/**
	 * 商品管理器
	 */
	private ItemManager itemManager;

	/**
	 * 搜索引擎接口
	 */
	private SearchManager searchManager;
	
	/**
	 * 店铺分类管理器
	 */
	private ShopCategoryManager shopCategoryManager;

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public ShopCategoryManager getShopCategoryManager() {
		return shopCategoryManager;
	}

	public void setShopCategoryManager(ShopCategoryManager shopCategoryManager) {
		this.shopCategoryManager = shopCategoryManager;
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}
	/**
	 * 展现商品推广（自动），获取接口数据
	 * 
	 * @param shopUserModuleParamDO
	 * @param properties
	 * 
	 */
	protected void getOtherModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		// TODO Auto-generated method stub
		try {
			/*
			String key = properties.getProperty("SELECTCATEGORY");
			List<Long> itemIds = new ArrayList<Long>();
			if (key != null && key.trim().length() > 0) {
				String[] cates = key.split(ShopConstants.SHOP_CATEGORY_SPLIT);
				Map<String, ShopCategoryListDO> map = shopCategoryManager
						.queryShopCategoryList(shopUserModuleParamDO
								.getShopId().intValue());
				ShopCategoryListDO cateList = map.get(cates[0]);
				itemIds = getAllItemIds(cateList, key);// 根据用户选择的店铺分类，获取分类下所有的商品列表
			}
			*/
			if(properties.get("__ISRELEASE")!=null&&properties.get("__ISRELEASE").equals("TRUE")){
				SearchItemDO searchShopItem = new SearchItemDO();
				if(properties.getProperty("MINPRICE")!=null&& !"".equals(properties.getProperty("MINPRICE"))){
					searchShopItem.setStartPrice(properties.getProperty("MINPRICE"));
				}
				if(properties.getProperty("MAXPRICE")!=null&& !"".equals(properties.getProperty("MAXPRICE"))){
					searchShopItem.setEndPrice(properties.getProperty("MAXPRICE"));
				}
				if(properties.get("SELECTCATEGORY")!=null&&!properties.get("SELECTCATEGORY").equals("")
						&&NumberUtil.isLong(properties.get("SELECTCATEGORY").toString())){
					searchShopItem.setStoreCategories(properties.get("SELECTCATEGORY").toString());
				}
				int count = 16;
				try {
					String countStr = properties.getProperty("ITEMNUM");
					if ("-1".equals(countStr))
						countStr = properties.getProperty("OTHERNUM");
					if(StringUtil.isNotEmpty(countStr)){
						count = Integer.parseInt(countStr);
					}
				} catch (Exception e) {
				}
				
				String sortType="GMT_CREATE DESC";
				String tempSort=properties.getProperty("SORTTYPE");
				if(StringUtil.isNotEmpty(tempSort)){
					sortType=tempSort;
				}
				searchShopItem.setCount(String.valueOf(count));
				searchShopItem.setSort(ShopDecorationConstant.SORT_NAME_LIST_ItemPromote.get(sortType));
				searchShopItem.setSellerId(shopUserModuleParamDO.getUserId().toString());
				searchShopItem.setIp(shopUserModuleParamDO.getIp());
				SearchResult result = searchManager.searchItemByShop(searchShopItem);
				SearchResultExt query = new SearchResultExt(result);
				properties.put("query", query);
				properties.put("picServer", PinjuConstant.VIEW_IMAGE_SERVER);
			}else{
//				SearchShopItem searchShopItem = new SearchShopItem();
//				if(properties.getProperty("MINPRICE")!=null&& !"".equals(properties.getProperty("MINPRICE"))&&NumberUtil.isDouble(properties.getProperty("MINPRICE").toLowerCase())){
//					searchShopItem.setMinPrice(Double.valueOf(properties.getProperty("MINPRICE")));
//				}
//				if(properties.getProperty("MAXPRICE")!=null&& !"".equals(properties.getProperty("MAXPRICE"))&&NumberUtil.isDouble(properties.getProperty("MINPRICE").toLowerCase())){
//					searchShopItem.setMaxPrice(Double.valueOf(properties.getProperty("MAXPRICE")));
//				}
//				if(properties.get("SELECTCATEGORY")!=null&&!properties.get("SELECTCATEGORY").equals("")
//						&&NumberUtil.isLong(properties.get("SELECTCATEGORY").toString())){
//					searchShopItem.setShopCategory(properties.get("SELECTCATEGORY").toString());
//				}else{
//					searchShopItem.setShopCategory("0");
//				}
//				int count = 16;
//				try {
//					String countStr = properties.getProperty("ITEMNUM");
//					if ("-1".equals(countStr))
//						countStr = properties.getProperty("OTHERNUM");
//					if(StringUtil.isNotEmpty(countStr)){
//						count = Integer.parseInt(countStr);
//					}
//				} catch (Exception e) {
//				}
//				
//				String sortType="GMT_CREATE DESC";
//				String tempSort=properties.getProperty("SORTTYPE");
//				if(StringUtil.isNotEmpty(tempSort)){
//					sortType=tempSort;
//				}
//				searchShopItem.setCount(String.valueOf(20));
//				searchShopItem.setStart("1");
//				List status = new ArrayList();
//				status.add("0");
//				status.add("1");
//				searchShopItem.setStatus(status);
//				searchShopItem.setCount(String.valueOf(count));
//				searchShopItem.setSort(ShopDecorationConstant.SORT_NAME_LIST_ItemPromote.get(sortType));
//				searchShopItem.setSellerId(shopUserModuleParamDO.getUserId());
//				SearchResult result = searchManager.searchItemByShopFromDB(searchShopItem);
//				SearchResultExt query = new SearchResultExt(result);
//				properties.put("query", query);
//				properties.put("picServer", PinjuConstant.VIEW_IMAGE_SERVER);
			
				ItemQueryEx itemQuery = new ItemQueryEx();
				//itemQuery.setItemIdList(itemIds);
				itemQuery.setSellerId(shopUserModuleParamDO.getUserId());
				try {
					double minPrice = Double.parseDouble(properties
							.getProperty("MINPRICE"));// 元
					itemQuery.setMinPrice((long) (minPrice * 100));// 转为分
				} catch (Exception e) {
	
				}
				try {
					double maxPrice = Double.parseDouble(properties
							.getProperty("MAXPRICE"));// 元
					itemQuery.setMaxPrice((long) (maxPrice * 100));// 转为分
				} catch (Exception e) {
	
				}
				if(properties.get("SELECTCATEGORY")!=null&&!properties.get("SELECTCATEGORY").equals("")
						&&NumberUtil.isLong(properties.get("SELECTCATEGORY").toString())){
					itemQuery.setCategory(Long.valueOf(properties.get("SELECTCATEGORY").toString()));
				}
				itemQuery.setOrderBy(properties.getProperty("SORTTYPE"));
				int count = 16;
				try {
					String countStr = properties.getProperty("ITEMNUM");
					if ("-1".equals(countStr))
						countStr = properties.getProperty("OTHERNUM");
					count = Integer.parseInt(countStr);
				} catch (Exception e) {
				}
				itemQuery.setItemsPerPage(16);// 设置显示商品数量
				List<ItemDO> list = itemManager.queryItemListEx(itemQuery);
				if(list.size()>count)
					list = list.subList(0, count);
				properties.put("ITEMLIST", list);
				properties.put("picServer", PinjuConstant.VIEW_IMAGE_SERVER);
			}
		} catch (Exception e) {
			log.error("商品推广异常",e);
		}
	}

	/**
	 *根据用户选择的店铺分类，找出此分类下所有的商品列表
	 * 
	 * @param cateList
	 * @param key
	 * @return List<Long>
	 * 
	 */
	@SuppressWarnings("unused")
	private List<Long> getAllItemIds(ShopCategoryListDO cateList, String key) {
		if (key.indexOf(ShopConstants.SHOP_CATEGORY_SPLIT) == -1) {
			List<Long> itemIds = new ArrayList<Long>();
			for (Object secondCate : cateList.getSubCategoryList())
				itemIds.addAll(cateList.getSecondeGoodsList().get(
						cateList.getCategoryName()
								+ ShopConstants.SHOP_CATEGORY_SPLIT
								+ secondCate.toString()));
			return itemIds;
		} else
			return cateList.getSecondeGoodsList().get(key);
	}

	/**
	 * 获取之前用户已编辑的相关数据，需要另外获取用户的店铺分类列表
	 * 
	 * @param shopUserModuleParamDO
	 * @param properties
	 * 
	 */
	protected void getOtherModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		// TODO Auto-generated method stub
		try {
			properties.put("shopCategoryList", shopCategoryManager
					.queryShopCategoryList(shopUserModuleParamDO.getShopId()
							.intValue()));// 放置店铺分类列表
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn(e);
		}
		return;
	}
}
