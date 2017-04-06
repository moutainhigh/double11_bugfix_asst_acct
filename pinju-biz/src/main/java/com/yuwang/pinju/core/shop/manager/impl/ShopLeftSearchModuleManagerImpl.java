package com.yuwang.pinju.core.shop.manager.impl;

import java.util.Properties;

import com.yuwang.pinju.core.shop.manager.ShopLeftSearchModuleManager;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

/**
 * @author KSCN
 *
 * @since 2011-7-4
 */
public class ShopLeftSearchModuleManagerImpl extends
		ShopBaseDesignerManagerImpl implements ShopLeftSearchModuleManager {

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
