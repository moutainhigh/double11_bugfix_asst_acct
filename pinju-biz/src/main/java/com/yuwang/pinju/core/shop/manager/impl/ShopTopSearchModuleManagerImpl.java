package com.yuwang.pinju.core.shop.manager.impl;

import java.util.Properties;

import com.yuwang.pinju.core.shop.manager.ShopTopSearchModuleManager;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public class ShopTopSearchModuleManagerImpl extends ShopBaseDesignerManagerImpl
		implements ShopTopSearchModuleManager {

	@Override
	protected void getOtherModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		// TODO Auto-generated method stub
		properties.put("SHOP_ID", shopUserModuleParamDO.getShopId());
	}

	@Override
	protected void getOtherModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		// TODO Auto-generated method stub

	}

}
