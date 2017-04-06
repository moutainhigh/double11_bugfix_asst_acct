package com.yuwang.pinju.core.shop.manager.impl;

import java.util.Map;
import java.util.Properties;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.core.shop.manager.ShopLeftCategoryModuleManager;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public class ShopLeftCategoryModuleManagerImpl extends
		ShopBaseDesignerManagerImpl implements ShopLeftCategoryModuleManager {
	private ShopCategoryManager shopCategoryManager;
	@Override
	protected void getOtherModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		try{
			Map<String, ShopCategoryListDO> map = shopCategoryManager.queryShopCategoryList(shopUserModuleParamDO.getShopId().intValue());
			properties.put("SHOP_ID", shopUserModuleParamDO.getShopId());
			properties.put("shopCategoryList", map.values());
		}catch(ManagerException e){
			log.error(e);
		}
	}

	@Override
	protected void getOtherModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		try{
			Map<String, ShopCategoryListDO> map = shopCategoryManager.queryShopCategoryList(shopUserModuleParamDO.getShopId().intValue());
			properties.put("shopCategoryList", map.values());
		}catch(ManagerException e){
			log.error(e);
		}
	}
	public void setShopCategoryManager(
			ShopCategoryManager shopCategoryManager) {
		this.shopCategoryManager = shopCategoryManager;
	}
}
