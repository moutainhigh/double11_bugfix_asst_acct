/**
 * 
 */
package com.yuwang.pinju.core.shop.ao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.ao.ShopPagePrototypeAO;
import com.yuwang.pinju.core.shop.manager.ShopPagePrototypeManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;

/**
 * @author liyouguo
 * 
 */
public class ShopPagePrototypeAOImpl extends BaseAO implements
		ShopPagePrototypeAO {

	private ShopPagePrototypeManager shopPagePrototypeManager;

	public ShopPagePrototypeManager getShopPagePrototypeManager() {
		return shopPagePrototypeManager;
	}

	public void setShopPagePrototypeManager(
			ShopPagePrototypeManager shopPagePrototypeManager) {
		this.shopPagePrototypeManager = shopPagePrototypeManager;
	}

	/**
	 * 获取所有待编辑的页面
	 * 
	 * @return
	 */
	@Override
	public List<ShopPagePrototypeDO> getAllDesignerPage() {
		// TODO Auto-generated method stub
		try {
			return shopPagePrototypeManager.getAllDesignerPage();
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return null;
	}

	@Override
	public ShopPagePrototypeDO queryPageProtoByPageid(Integer pageId)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			return shopPagePrototypeManager.queryPageProtoByPageid(pageId);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			log.error(e);
			throw e;
		}
	}
}
