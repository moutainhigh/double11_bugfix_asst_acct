package com.yuwang.pinju.core.order.ao.impl;

import java.util.List;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.core.order.ao.ShoppingCartDetailAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.BaseValueDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;


/**
 * 购物车详细相关业务操作
 * @author shihongbo
 * @date   2011-6-8
 * @version 1.0
 */

public class ShoppingCartDetailAOImpl extends BaseAO implements ShoppingCartDetailAO{

	private ItemManager itemManager;
	private ShopShowInfoManager shopShowInfoManager;
	private SkuManager skuManager;
	private CategoryCacheManager categoryCacheManager;

	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	/**
	 * 取得店铺详细信息
	 * 
	 * @param shopInfoDO
	 * @return
	 */
	@Override
	public List<ShopInfoDO> getShopList(List<Long> ids){
		try {
			return shopShowInfoManager.queryShopInfoByUserIdList(ids);
		} catch (ManagerException e) {
			log.error("Event=[ShoppingCartDetailAOImpl#getShopList] 通过ID列表获取商品详情失败", e);
		}
		return null;

	}
	
	/**
	 * 取得商品详细信息
	 * 
	 * @param shopInfoDO
	 * @return
	 */
	public List<ItemDO> getItemList(List<Long> ids){
		try {
			return itemManager.getItemListByIds(ids);
		} catch (ManagerException e) {
			log.error("Event=[ShoppingCartDetailAOImpl#getItemList] 通过ID列表获取商品详情失败", e);
		}
		return null;
		
	}
	
	/**
	 * 取得商品属性
	 * 
	 * @param ItemPropertyVO
	 * @return
	 */
	public ItemPropertyVO getItemPropertyBySku(String pv){
		ItemPropertyVO itemPropertyVO = new ItemPropertyVO();
		
		if (pv == null || pv.equals("")) 
			return itemPropertyVO;
		
		try{
			String indexPV = StringUtil.substringBefore(pv,";");
			String indexP = StringUtil.substringBefore(indexPV, ":");
			String indexV = StringUtil.substringAfter(indexPV, ":");
			long indexPLong = Long.parseLong(indexP);
			long indexVLong = Long.parseLong(indexV);
			
			//获取基础属性值
			BaseValueDO baseValueDO = categoryCacheManager.getBaseValueById(indexVLong);
			//System.out.println("basevalue = " + baseValueDO.getValue());
			
			CategoryPropertyDO  cateProDO = categoryCacheManager.getItemCategoryPropertyById(indexPLong);
			//System.out.println("cateProDO = " + cateProDO.getName() + cateProDO.getCategoryId());
			
			itemPropertyVO.setName(cateProDO.getName());
			itemPropertyVO.setValue(baseValueDO.getValue());

			return itemPropertyVO;
		}catch (ManagerException e) {
			log.error("Event=[ShoppingCartDetailAOImpl#getItemList] 通过ID列表获取商品详情失败", e);
		}
		return itemPropertyVO;
	}
	
	/**
	 * 通过商品sku编号 获得SKU
	 * 
	 * @param skuid
	 * @return
	 */
	public SkuDO getItemSkuById(Long skuid){
		try {
			SkuDO sku = skuManager.getItemSkuById(skuid);
			
			return sku;
		} catch (ManagerException e) {
			log.error("Event=[ShoppingCartDetailAOImpl#getItemSkuByItemId] 通过商品编号 获得SKU", e);
		}
		return null;

	}
	
	/**
	 * 取得商品库存数量
	 * 
	 * @param itemId
	 * @param skuid
	 * @return
	 */
	public Long getItemStockCount(Long itemId, String skuid){
		try {
			if(skuid == null || "".equals(skuid))
				skuid = "0";
			
			//有sku,取sku库存
			if(! "0".equals(skuid)){
				SkuDO sku = skuManager.getItemSkuById(Long.parseLong(skuid));
				return sku.getCurrentStock();
			}
			
			//取商品库存
			ItemDO itemDO = itemManager.getItemDOById(itemId);
			return itemDO.getCurStock();
			
		} catch (ManagerException e) {
			log.error("Event=[ShoppingCartDetailAOImpl#getItemStockCount] 取得商品库存数量", e);
			return 0L;
		}
	}
	
}
