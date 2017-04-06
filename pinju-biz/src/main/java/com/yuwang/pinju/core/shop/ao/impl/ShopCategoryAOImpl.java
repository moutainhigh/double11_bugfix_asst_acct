package com.yuwang.pinju.core.shop.ao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.shop.ao.ShopCategoryAO;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;

/**
 * 店铺商品分类列表
 * 
 * @author mike, xueqi
 * 
 * @since 2011-6-7
 */
public class ShopCategoryAOImpl extends BaseAO implements ShopCategoryAO {
	/**
	 * 店铺商品分类管理
	 */
	private ShopCategoryManager shopCategoryManager;

	private ItemManager itemManager;

	@Override
	public Map<String, ShopCategoryListDO> queryShopCategoryList(Integer shopId) {
		Map<String, ShopCategoryListDO> map = null;
		try {
			map = shopCategoryManager.queryShopCategoryList(shopId);
		} catch (ManagerException e) {
			log.error(e);
		}
		return map;
	}
	
	
	
	
	/**
	 * 根据二级分类获取该二级分类下的所有商品
	 * @param key
	 * @param shopId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ItemDO> queryCategoryGoods(String key, Integer shopId) throws Exception {
		List<ItemDO> itemDOList= null;
		List<Long> itemIds = new ArrayList<Long>();
		try {
			if (key != null && key.trim().length() > 0) {
				String[] cates = key.split(ShopConstants.SHOP_CATEGORY_SPLIT);
				Map<String, ShopCategoryListDO> map = shopCategoryManager
						.queryShopCategoryList(shopId);
				ShopCategoryListDO cateList = map.get(cates[0]);
				itemIds = getAllItemIds(cateList, key);// 根据用户选择的店铺分类，获取分类下所有的商品列表
			}
			ItemQueryEx itemQuery = new ItemQueryEx();
			itemQuery.setItemIdList(itemIds);
			itemDOList = itemManager.queryItemListEx(itemQuery);
		}catch(ManagerException e){
			log.error(e);
			log.info(e);
		}
		return itemDOList;
	}

	/**
	 *根据用户选择的店铺分类，找出此分类下所有的商品列表
	 * 
	 * @param cateList
	 * @param key
	 * @return List<Long>
	 * 
	 */
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

	public void setShopCategoryManager(ShopCategoryManager shopCategoryManager) {
		this.shopCategoryManager = shopCategoryManager;
	}

	public ShopCategoryManager getShopCategoryManager() {
		return shopCategoryManager;
	}


	

}
