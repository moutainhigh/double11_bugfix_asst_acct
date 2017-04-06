package com.yuwang.pinju.core.distribute.ao.impl;

import java.util.ArrayList;
import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.distribute.ao.DistributeGoodsManagerAO;
import com.yuwang.pinju.core.distribute.manager.DistributeSupplierItemManager;
import com.yuwang.pinju.core.distribute.manager.DistributeSupplierManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemDO;
import com.yuwang.pinju.domain.distribute.DistrbuteSupplierItemParamDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public class DistributeGoodsManagerAOImpl extends BaseAO implements DistributeGoodsManagerAO {

	/**
	 * 分销商品接口
	 */
	private DistributeSupplierItemManager distributeSupplierItemManager;

	/**
	 * 商品接口（搜索引擎）
	 */
	private ItemManager itemManager;

	/**
	 * 分销商接口
	 */
	private DistributeSupplierManager distributeSupplierManager;

	/**
	 * 店铺接口
	 */
	private ShopOpenManager shopOpenManager;

	/**
	 * 取得商铺所有商品分页列表
	 * 
	 * @param userId
	 * @param page
	 * @param allcount
	 */
	@Override
	public List<DistrbuteSupplierItemDO> getAllGoods(Integer supplierId, ItemQueryEx itemQuery) {
		try {
			List<ItemDO> list = itemManager.queryItemListEx(itemQuery);
			List<DistrbuteSupplierItemDO> displayList = new ArrayList<DistrbuteSupplierItemDO>();
			for (ItemDO item : list) {
				DistrbuteSupplierItemParamDO param = new DistrbuteSupplierItemParamDO();
				param.setSupplierId(supplierId);
				param.setItemId(item.getId());
				DistrbuteSupplierItemDO dsi = distributeSupplierItemManager.getSupplierItemBySupplierIdAndItemId(param);
				if (null == dsi) {
					dsi = new DistrbuteSupplierItemDO();
				}
				dsi.setItemId(item.getId());
				dsi.setItemDO(item);
				dsi.setSupplierId(supplierId);
				// TODO 取得商品分销状态 暂无接口
				displayList.add(dsi);
			}
			return displayList;
		} catch (ManagerException e) {
			log.error("取得商铺所有商品分页列表出错", e);
		}
		return null;
	}

	/**
	 * 取得商铺所有可分销商品数
	 * 
	 * @param userID
	 * @return count
	 */
	@Override
	public int getAllGoodsCount(long userId) {
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(0);
		statusList.add(1);
		try {
			return itemManager.getItemListCount(new ItemQueryEx(statusList,userId));
		} catch (ManagerException e) {
			log.error("取得商铺所有可分销商品数出错", e);
		}
		return 0;
	}

	/**
	 * 取得商铺所有已分销商品数
	 */
	@Override
	public int getDistributeGoodsCount(Integer supplierId) {
		try {
			return distributeSupplierItemManager.getDistributeGoodsCount(supplierId);
		} catch (ManagerException e) {
			log.error("取得商铺所有已分销商品数出错", e);
		}
		return 0;
	}

	/**
	 * 取得商铺所有可分销商品
	 * 
	 */
	@Override
	public List<DistrbuteSupplierItemDO> getDistributeGoods(Integer supplierId, ItemQueryEx itemQuery) {
		List<Long> ids;
		Integer curPage = itemQuery.getPage();
		try {
			// 取得分销商品分页ID
			ids = distributeSupplierItemManager.getDistributeGoodIds(supplierId, itemQuery.getPage(), itemQuery.getItems());
			itemQuery.setItemIdList(ids);
			itemQuery.setPage(1);
			// 取得商品的详细信息
			List<ItemDO> list = itemManager.queryItemListEx(itemQuery);
			List<DistrbuteSupplierItemDO> displayList = new ArrayList<DistrbuteSupplierItemDO>();
			for (ItemDO item : list) {
				DistrbuteSupplierItemParamDO param = new DistrbuteSupplierItemParamDO();
				param.setSupplierId(supplierId);
				param.setItemId(item.getId());
				// 取得商品分销信息
				DistrbuteSupplierItemDO dsi = distributeSupplierItemManager.getSupplierItemBySupplierIdAndItemId(param);
				dsi.setItemId(item.getId());
				dsi.setItemDO(item);
				displayList.add(dsi);
			}
			itemQuery.setPage(curPage);
			return displayList;
		} catch (ManagerException e) {
			log.error("取得商铺所有可分销商品出错", e);
		}
		return null;
	}

	@Override
	public void saveDistributeGoods(List<DistrbuteSupplierItemDO> distributeGoods) {
		try {
			distributeSupplierItemManager.saveSupplierItems(distributeGoods);
		} catch (ManagerException e) {
			log.error("取得商铺所有可分销商品数出错", e);
		}
	}

	@Override
	public void updateDistributeGoods(List<DistrbuteSupplierItemDO> unDistributeGoods) {
		// TODO Auto-generated method stub

	}

	public DistributeSupplierItemManager getDistributeSupplierItemManager() {
		return distributeSupplierItemManager;
	}

	public void setDistributeSupplierItemManager(DistributeSupplierItemManager distributeSupplierItemManager) {
		this.distributeSupplierItemManager = distributeSupplierItemManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public DistributeSupplierManager getDistributeSupplierManager() {
		return distributeSupplierManager;
	}

	public void setDistributeSupplierManager(DistributeSupplierManager distributeSupplierManager) {
		this.distributeSupplierManager = distributeSupplierManager;
	}

	public ShopOpenManager getShopOpenManager() {
		return shopOpenManager;
	}

	public void setShopOpenManager(ShopOpenManager shopOpenManager) {
		this.shopOpenManager = shopOpenManager;
	}

}