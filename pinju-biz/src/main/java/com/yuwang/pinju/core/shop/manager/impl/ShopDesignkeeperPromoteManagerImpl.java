package com.yuwang.pinju.core.shop.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import com.yuwang.pinju.Constant.ShopDecorationConstant;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.search.manager.SearchManager;
import com.yuwang.pinju.core.shop.manager.ShopBaseDesignerManager;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.result.SearchResult;
import com.yuwang.pinju.domain.shop.SearchResultExt;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

/**
 * 掌柜推荐
 * @author xueqi
 *
 * @since 2011-7-4
 */
public class ShopDesignkeeperPromoteManagerImpl extends
		ShopBaseDesignerManagerImpl implements ShopBaseDesignerManager {

	private ItemManager itemManager;
	
	private ShopCategoryManager shopCategoryManager;

	/**
	 * 搜索引擎接口
	 */
	private SearchManager searchManager;
	
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
	 * 获取模块信息用来显示展示页
	 * @param shopUserModuleParamDO
	 * @param properties
	 */
	@Override
	protected void getOtherModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		try {
			if(properties.get("__ISRELEASE")!=null&&properties.get("__ISRELEASE").equals("TRUE")){
				SearchItemDO searchShopItem = new SearchItemDO();
				String promoteStrings = null;
				if(properties.getProperty("ITEMIDS")!=null && properties.getProperty("ITEMIDS").length()>0){
					promoteStrings = properties.getProperty("ITEMIDS");
				}
				Set<String> set  = new TreeSet<String>();
				if (promoteStrings != null && promoteStrings.length() > 0) {
					StringBuffer sb = new StringBuffer("");
					String []promoteds = promoteStrings.split(",");
					for(int i=0;i<promoteds.length;i++){
						sb.append(" OR id:"+promoteds[i]);
					}
					if(sb.toString().length()>0){
						set.add("("+sb.toString().substring(4)+")");
					}
				}else{
					set.add("id:0");
				}
				if(properties.getProperty("itemShowCount")!=null && StringUtil.isNumeric(properties.getProperty("itemShowCount").toString())){
					searchShopItem.setCount(properties.getProperty("itemShowCount"));
				}else{
					searchShopItem.setCount("3");
				}
				searchShopItem.setFq(set);
				searchShopItem.setSort(ShopDecorationConstant.SORT_NAME_LIST.get(properties.getProperty("SORTTYPE")));
				searchShopItem.setSellerId(shopUserModuleParamDO.getUserId().toString());
				searchShopItem.setIp(shopUserModuleParamDO.getIp());
				SearchResult result = searchManager.searchItemByShop(searchShopItem);
				SearchResultExt query = new SearchResultExt(result);
				properties.put("query", query);
				properties.put("pageId", shopUserModuleParamDO.getUserPageId());
				properties.put("moduleId", shopUserModuleParamDO.getModuleId());
				properties.put("picSizeList", ShopDecorationConstant.picSize);
				properties.put("itemShowCountList", ShopDecorationConstant.itemShowCount);
				properties.put("sorttypeList", ShopDecorationConstant.sorttype);
				properties.put("picServer", PinjuConstant.VIEW_IMAGE_SERVER);
				if(properties.getProperty("picSize")==null || properties.getProperty("picSize").length()==0){
					properties.put("picSize", "120px");
				}
				if(properties.getProperty("itemShowCount")==null || properties.getProperty("itemShowCount").length()==0){
					properties.put("itemShowCount", "3");
				}
				
				if(properties.getProperty("SORTTYPE")==null || properties.getProperty("SORTTYPE").length()==0){
					properties.put("SORTTYPE", "0");
				}
				if(properties.getProperty("itemShowCountCustomer")==null || properties.getProperty("itemShowCountCustomer").length()==0){
					properties.put("itemShowCountCustomer", "");
				}
				return;
			}
			//用来获取item
			ItemQueryEx itemQuery = new ItemQueryEx();
			itemQuery.setSellerId(shopUserModuleParamDO.getUserId());
			//设置排序条件
			itemQuery.setOrderBy(ShopDecorationConstant.KEEPER_PROMOTE_SORT.get(properties.getProperty("SORTTYPE")));
			
			//获取参数中已推荐的item
			String promoteStrings = null;
			if(properties.getProperty("ITEMIDS")!=null && properties.getProperty("ITEMIDS").length()>0){
				promoteStrings = properties.getProperty("ITEMIDS");
			}
			if (promoteStrings != null && promoteStrings.length() > 0) {
				//获取可推荐item
				String []promoteds = promoteStrings.split(",");
				List<Long> promotedList = new ArrayList<Long>();
				for(int i=0;i<promoteds.length;i++){
					promotedList.add(Long.parseLong(promoteds[i]));
				}
				itemQuery.setItemIdList(promotedList);
				List<ItemDO> itemsList = itemManager.queryItemListEx(itemQuery);
				
				int showCount = Integer.parseInt(properties.getProperty("itemShowCount"));
				if(showCount == -1){
					showCount = Integer.parseInt(properties.getProperty("itemShowCountCustomer"));
				}
				if (itemsList != null && itemsList.size() > 0) {
					List<ItemDO> items = new ArrayList<ItemDO>();
					for (int i = 0; i < itemsList.size(); i++) {
						ItemDO item = itemsList.get(i);
						log.info(item.getId());
						if (promoteStrings.indexOf(String.valueOf(item.getId())) != -1) {
							items.add(item);
						}
						if(showCount == items.size()){
							break;
						}
					}
					properties.put("ITEMLIST", items);
					properties.put("ITEMNUM", items.size());
					properties.put("pageId", shopUserModuleParamDO.getUserPageId());
					properties.put("moduleId", shopUserModuleParamDO.getModuleId());
					properties.put("picSizeList", ShopDecorationConstant.picSize);
					properties.put("itemShowCountList", ShopDecorationConstant.itemShowCount);
					properties.put("sorttypeList", ShopDecorationConstant.sorttype);
					properties.put("picServer", PinjuConstant.VIEW_IMAGE_SERVER);
					if(properties.getProperty("picSize")==null || properties.getProperty("picSize").length()==0){
						properties.put("picSize", "120px");
					}
					if(properties.getProperty("itemShowCount")==null || properties.getProperty("itemShowCount").length()==0){
						properties.put("itemShowCount", "3");
					}
					
					if(properties.getProperty("SORTTYPE")==null || properties.getProperty("SORTTYPE").length()==0){
						properties.put("SORTTYPE", "0");
					}
					if(properties.getProperty("itemShowCountCustomer")==null || properties.getProperty("itemShowCountCustomer").length()==0){
						properties.put("itemShowCountCustomer", "");
					}
				}
			}
		} catch(NullPointerException nException){
			log.error("获取模块信息用来显示展示页",nException);
		} catch (Exception e) {
			log.error("获取模块信息用来显示展示页",e);
		}
	}
	
	/**
	 * 获取模块信息用来显示编辑页
	 * @param shopUserModuleParamDO
	 * @param properties
	 */
	@Override
	protected void getOtherModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		try {
			//用来获取item
			ItemQueryEx itemQuery = new ItemQueryEx("");
			itemQuery.setPage(1);
			
			itemQuery.setSellerId(shopUserModuleParamDO.getUserId());
			List<Long> status = new ArrayList<Long>();
			status.add(ItemConstant.STATUS_TYPE_0);
			status.add(ItemConstant.STATUS_TYPE_1);
			itemQuery.setStatus(status);
			int count = itemManager.getItemListCount(itemQuery);
			itemQuery.setItems(count);
			int totalPage = count % ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT > 0 ? count / ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT + 1 : count / ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT;
			
			
			//List<ItemDO> allList = itemManager.getItemList(itemQuery);
			itemQuery.setItemsPerPage(ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT);
			itemQuery.setOrderBy("GMT_CREATE DESC");
			//获取参数中已推荐的item
			String promoteStrings = "";
			if(properties.getProperty("ITEMIDS")!=null && properties.getProperty("ITEMIDS").length()>0){
				promoteStrings = properties.getProperty("ITEMIDS");
			}
			//获取可推荐item
				List<ItemDO> itemsList = itemManager.queryItemListEx(itemQuery);
				//if (allList != null && allList.size() > 0) {
					List<ItemDO> items = new ArrayList<ItemDO>();
					//是否已推荐
					String []isPromoted = new String[itemsList.size()];
					if(promoteStrings!=null && promoteStrings.length() > 0){
						log.info(promoteStrings);
						String pr[] = promoteStrings.split(",");
						List<Long> ids = new ArrayList<Long>();
						for(int i=0;i<pr.length;i++){
							ids.add(Long.parseLong(pr[i]));
						}
						items = itemManager.getItemListByIds(ids);
						
						
//						for (int i = 0; i < allList.size(); i++) {
//							ItemDO item = allList.get(i);
//							if (promoteStrings.indexOf(String.valueOf(item.getId())) != -1) {
//								log.info(item.getId() + " " + promoteStrings.indexOf(String.valueOf(item.getId())));
//								items.add(item);
//							}else{
//							}
//						}
						for (int i = 0; i < itemsList.size(); i++) {
							ItemDO item = itemsList.get(i);
							//log.info(item.getId());
							if (promoteStrings.indexOf(String.valueOf(item.getId())) != -1) {
								isPromoted[i] = "1";
							}else{
								isPromoted[i] = "0";
							}
						}
					}
					//要显示的推荐栏的item列表
//					List<ItemDO> promoteShowItemList = new ArrayList<ItemDO>();
//					for(int i=0;i<ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT;i++){
//						promoteShowItemList.add(itemsList.get(i));
//					}
					properties.put("currentPage", 1);
					properties.put("totalPage", totalPage);
					properties.put("nextPage", 2);
					properties.put("prevPage", 0);
					properties.put("isPromoted", isPromoted);
					properties.put("ITEMLIST", itemsList);
					properties.put("ITEMLISTPROMOTE", items);
					properties.put("ITEMNUM", items.size());
					properties.put("pageId", shopUserModuleParamDO.getUserPageId());
					properties.put("moduleId", shopUserModuleParamDO.getModuleId());
					properties.put("picSizeList", ShopDecorationConstant.picSize);
					properties.put("itemShowCountList", ShopDecorationConstant.itemShowCount);
					properties.put("sorttypeList", ShopDecorationConstant.sorttype);
					properties.put("picServer", PinjuConstant.VIEW_IMAGE_SERVER);
					if(properties.getProperty("picSize")==null || properties.getProperty("picSize").length()==0){
						properties.put("picSize", "120px");
					}
					if(properties.getProperty("itemShowCount")==null || properties.getProperty("itemShowCount").length()==0){
						properties.put("itemShowCount", "3");
					}
					if(properties.getProperty("SORTTYPE")==null || properties.getProperty("SORTTYPE").length()==0){
						properties.put("SORTTYPE", "0");
					}
					if(properties.getProperty("itemShowCountCustomer")==null || properties.getProperty("itemShowCountCustomer").length()==0){
						properties.put("itemShowCountCustomer", "");
					}
				//}
		} catch(NullPointerException nException){
			log.error("获取模块信息用来显示展示页",nException);
		} catch (Exception e) {
			log.error(e);
		}
	}
}
