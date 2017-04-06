package com.yuwang.pinju.web.module.shop.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.shop.ao.ShopCategoryAO;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * 店铺商品分类列表
 * 
 * @author mike
 *
 * @since 2011-6-2
 */
public class ShopCategoryAction extends BaseWithUserAction {
	/**
	 * 店铺商品分类AO
	 */
	private ShopCategoryAO  shopCategoryAO;
	
	private ItemManager itemManager;
	
	/**
	 * 店铺商品分类管理
	 */
	private ShopCategoryManager  shopCategoryManager;
	private ShopShowInfoManager shopShowInfoManager;
	/**
	 * 店铺商品分类列表
	 */
	private Map<String,ShopCategoryListDO> shopCategoryList;
	
	private Integer sequenceId;
	
	private Integer shopId;
	
	private Map<String, List<String>> allFirstAndSecondCateMap;
	
	private Map<String, List<String>> itemKeyMap;
	private Map<String, ItemDO> itemDOMap;
	
	private String result;
	/**
	 * 要显示的分类
	 */
	private String cateKey;
	
	/**
	 * 要进行分类的商品id
	 */
	private String itemIdString;
	
	/**
	 * 要归类到的分类
	 */
	private String cateString;
	
	/**
	 * 店铺商品分类展示
	 */
	@AssistantPermission(target = "shop", action = "cat")
	public String execute()throws Exception{
		/**
		 * 根据卖家会员编号查看店铺
		 */
		ShopInfoDO shopInfoDO = null;
		if (shopInfoDO == null && getUserId() != 0L) {
			shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(getUserId(), null);
			if(shopInfoDO != null){
				if(shopInfoDO.getApproveStatus()==null){
					return "NOT_OPEN";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_HEGUI 
						|| shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_CLOSE){
					return "IS_CLOSE";
				}else if(shopInfoDO.getApproveStatus().intValue() == ShopConstant.APPROVE_STATUS_NO){
					return "NOT_OPEN";
				}
			}else{
				return "NOT_EXIST";
			}
			
		}
		shopId = shopInfoDO.getShopId();
		ShopCategoryDO  shopCategoryDO = null;
		shopCategoryDO=shopCategoryManager.queryShopCategory(shopId,getUserId());
		if(shopCategoryDO==null){
			this.shopCategoryList=null;
			this.sequenceId=0;
			return "success";
		}
		this.sequenceId=shopCategoryDO.getId();
		this.shopCategoryList=shopCategoryAO.queryShopCategoryList(shopId);
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public String queryCategoryGoods() throws Exception{
		shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "NOT_OPEN";
		}
		ShopCategoryDO  shopCategoryDO = null;
		shopCategoryDO=shopCategoryManager.queryShopCategory(shopId,getUserId());
		if(shopCategoryDO==null){
			this.shopCategoryList=null;
			this.sequenceId=0;
			return "success";
		}
		this.sequenceId=shopCategoryDO.getId();
		this.shopCategoryList=shopCategoryAO.queryShopCategoryList(shopId);
		
		//获取对应的itemDO
		Iterator   it   =   shopCategoryList.entrySet().iterator();
		List showList = new ArrayList();
		 while   (it.hasNext()) {  
			 Map<String, List<ItemDO>> keyItemsMap = new HashMap<String, List<ItemDO>>();
			 Map.Entry   entry   =   (Map.Entry)   it.next();    
//			 Object   key   =   entry.getKey();    
			 ShopCategoryListDO   value   =   (ShopCategoryListDO)entry.getValue();
			 
			 if(cateKey!=null && cateKey.length()>0){
				 keyItemsMap = getAllItemIds(value, cateKey,getUserId());
			 }else{
				 keyItemsMap = getAllItemIds(value, "",getUserId());
			 }
			 showList.add(keyItemsMap);
		 }  
		 
		 itemKeyMap = new HashMap<String,List<String>>();
		 itemDOMap = new HashMap<String,ItemDO>();
		 for(int i=0;i<showList.size();i++){
			 Map<String, List<ItemDO>> map = (Map<String, List<ItemDO>>) showList.get(i);
			 Iterator   iterator   =   map.entrySet().iterator();
			 while   (iterator.hasNext()) {  
				 Map.Entry   entry   =   (Map.Entry)   iterator.next();    
//				 Object   key   =   entry.getKey();    
				 List<ItemDO>   valueList   =   (List<ItemDO>)entry.getValue();
				 for(int j=0;j<valueList.size();j++){
					 ItemDO itemDO = valueList.get(j);
					 itemDOMap.put(String.valueOf(itemDO.getId()), itemDO);
					 if(itemKeyMap.get(String.valueOf(itemDO.getId())) != null){
						 itemKeyMap.get(String.valueOf(itemDO.getId())).add(entry.getKey().toString());
					 }else{
						 List<String> list = new ArrayList<String>();
						 list.add(entry.getKey().toString());
						 itemKeyMap.put(String.valueOf(itemDO.getId()), list);
					 }
				 }
			 }  
		 }
		
		 if(shopCategoryDO.getFirstCategory()!=null && shopCategoryDO.getFirstCategory().length()>0){
			 String firstCate[] = shopCategoryDO.getFirstCategory().split(ShopConstants.SHOP_VALUE_SPLIT);
			 if(shopCategoryDO.getSecondCategory()!=null && shopCategoryDO.getSecondCategory().length()>0){
				 allFirstAndSecondCateMap = new HashMap<String, List<String>>();
				 for(int i=0;i<firstCate.length;i++){
					 String secondcatesNames[] = shopCategoryDO.getCategoryConfig(firstCate[i]).split(ShopConstants.SHOP_VALUE_SPLIT);
					 List<String> list = new ArrayList<String>();
					 for(int j=0;j<secondcatesNames.length;j++){
						 list.add(secondcatesNames[j]);
					 }
					 allFirstAndSecondCateMap.put(firstCate[i], list);
 				 }
			 
			 }
			 
		 }
		 
		return "success";
	}
	
	/**
	 *根据用户选择的店铺分类，找出此分类下所有的商品列表
	 * 
	 * @param cateList
	 * @param key
	 * @return List<Long>
	 * 
	 */
	private Map<String, List<ItemDO>> getAllItemIds(ShopCategoryListDO cateList, String key,Long userId) {
		Map<String, List<ItemDO>> keyItemsMap = new HashMap<String, List<ItemDO>>();
		try {
			if (key.indexOf(ShopConstants.SHOP_CATEGORY_SPLIT) == -1) {
				List<Long> itemIds = new ArrayList<Long>();
				for (Object secondCate : cateList.getSubCategoryList()){
					itemIds.addAll(cateList.getSecondeGoodsList().get(
							cateList.getCategoryName()
									+ ShopConstants.SHOP_CATEGORY_SPLIT
									+ secondCate.toString()));
					
					ItemQueryEx itemQuery = new ItemQueryEx();
					itemQuery.setItemIdList(itemIds);
					itemQuery.setSellerId(userId);
					
					keyItemsMap.put(cateList.getCategoryName()
							+ ShopConstants.SHOP_CATEGORY_SPLIT
							+ secondCate, itemManager.queryItemListEx(itemQuery));
				}
			} else{
				ItemQueryEx itemQuery = new ItemQueryEx();
				itemQuery.setItemIdList(cateList.getSecondeGoodsList().get(key));
				itemQuery.setSellerId(userId);
				
				keyItemsMap.put(key, itemManager.queryItemListEx(itemQuery));
				
			}
		} catch (ManagerException e) {
			log.info("根据用户选择的店铺分类，找出此分类下所有的商品列表出错",e);
			
		}
		return keyItemsMap;
	}
	
	public Map<String,ShopCategoryListDO> getShopCategoryList() {
		return shopCategoryList;
	}
	public void setShopCategoryList(Map<String,ShopCategoryListDO> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}
	
	public void setShopCategoryManager(ShopCategoryManager shopCategoryManager) {
		this.shopCategoryManager = shopCategoryManager;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Integer getSequenceId() {
		return sequenceId;
	}

	public void setShopCategoryAO(ShopCategoryAO shopCategoryAO) {
		this.shopCategoryAO = shopCategoryAO;
	}
	
	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}


	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public Map<String, List<String>> getAllFirstAndSecondCateMap() {
		return allFirstAndSecondCateMap;
	}

	public void setAllFirstAndSecondCateMap(
			Map<String, List<String>> allFirstAndSecondCateMap) {
		this.allFirstAndSecondCateMap = allFirstAndSecondCateMap;
	}

	public Map<String, List<String>> getItemKeyMap() {
		return itemKeyMap;
	}

	public void setItemKeyMap(Map<String, List<String>> itemKeyMap) {
		this.itemKeyMap = itemKeyMap;
	}

	public Map<String, ItemDO> getItemDOMap() {
		return itemDOMap;
	}

	public void setItemDOMap(Map<String, ItemDO> itemDOMap) {
		this.itemDOMap = itemDOMap;
	}

	public String getCateKey() {
		return cateKey;
	}

	public void setCateKey(String cateKey) {
		this.cateKey = cateKey;
	}

	public String getItemIdString() {
		return itemIdString;
	}

	public void setItemIdString(String itemIdString) {
		this.itemIdString = itemIdString;
	}

	public String getCateString() {
		return cateString;
	}

	public void setCateString(String cateString) {
		this.cateString = cateString;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
