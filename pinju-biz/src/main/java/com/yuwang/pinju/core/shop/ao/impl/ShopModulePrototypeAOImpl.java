/**
 * 
 */
package com.yuwang.pinju.core.shop.ao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.ao.ShopModulePrototypeAO;
import com.yuwang.pinju.core.shop.manager.ShopModulePrototypeManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;

/**
 * @author liyouguo
 * 
 */
public class ShopModulePrototypeAOImpl extends BaseAO implements
		ShopModulePrototypeAO {
	
	private ShopModulePrototypeManager shopModulePrototypeManager;
	
	public ShopModulePrototypeManager getShopModulePrototypeManager() {
		return shopModulePrototypeManager;
	}

	public void setShopModulePrototypeManager(
			ShopModulePrototypeManager shopModulePrototypeManager) {
		this.shopModulePrototypeManager = shopModulePrototypeManager;
	}

	/**
	 * 获取待编辑的页面的指定部分可添加的模块
	 * @param 	pageId 	--页面编号与表SHOP_PAGE_PROTOTYPE关联
	 * 			type 	--页面的哪个部分（上，左，右，下）
	 * @return
	 */
	@Override
	public List<ShopModulePrototypeDO> getDesignerModuleByPage(Integer pageId,
			Integer type) {
		// TODO Auto-generated method stub
		try {
			return shopModulePrototypeManager.getDesignerModuleByUserPage(pageId, type);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return null;
	}
}
