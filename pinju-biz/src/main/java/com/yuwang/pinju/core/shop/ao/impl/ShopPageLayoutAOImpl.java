package com.yuwang.pinju.core.shop.ao.impl;

import java.util.List;
import java.util.Vector;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.ao.ShopPageLayoutAO;
import com.yuwang.pinju.core.shop.manager.ShopPageLayoutManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.shop.ShopPageLayoutDO;

public class ShopPageLayoutAOImpl extends BaseAO implements ShopPageLayoutAO {
	private ShopPageLayoutManager shopPageLayoutManager;

	/**
	 * 查询页面布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ShopPageLayoutDO> queryPageLayout(Long userPageId, Long userId,
			Integer shopId, Integer pageId, boolean isRelease) {
		try {
			return shopPageLayoutManager.queryPageLayoutCache(userPageId,
						userId, shopId, pageId,isRelease);
		} catch (ManagerException e) {
			log.error("获取页面布局结构出错", e);
		}
		return new Vector();
	}

	/**
	 * 保存页面布局
	 * 
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return
	 */
	@Override
	public boolean savePageLayout(Long userId, Integer shopId, Integer pageId,
			String layoutXml) {
		try {
			return shopPageLayoutManager.savePageLayout(userId, shopId, pageId,
					layoutXml);
		} catch (ManagerException e) {
			log.info("保存页面布局出错",e);
		}
		return false;
	}

	public ShopPageLayoutManager getShopPageLayoutManager() {
		return shopPageLayoutManager;
	}

	public void setShopPageLayoutManager(
			ShopPageLayoutManager shopPageLayoutManager) {
		this.shopPageLayoutManager = shopPageLayoutManager;
	}
}
