package com.yuwang.pinju.core.shop.manager.impl;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.shop.manager.ShopUserModuleParamManager;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public class ShopShopUserModuleParamManagerTest extends BaseTestCase {
	@SpringBean("shopUserModuleParamManager")
	private ShopUserModuleParamManager shopUserModuleParamManager;

	public void testinsertShopUserModuleParam() throws Exception {
		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
		shopUserModuleParamDO.setShopId(1);
		shopUserModuleParamDO.setUserId(1L);
		shopUserModuleParamDO.setUserPageId(1L);
		shopUserModuleParamDO.setModuleId(1L);
		shopUserModuleParamDO.setSave_config("");
		shopUserModuleParamDO.setRelease_config("");
		shopUserModuleParamManager
				.insertShopUserModuleParam(shopUserModuleParamDO);
	}

	public void testqueryShopUserModuleParam() throws Exception {
		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
		shopUserModuleParamDO.setShopId(1);
		shopUserModuleParamDO.setUserId(1L);
		shopUserModuleParamDO.setUserPageId(1L);
		shopUserModuleParamDO.setModuleId(1L);
		shopUserModuleParamManager
				.queryShopUserModuleParam(shopUserModuleParamDO);
	}

	public void testupdateShopUserModuleParam() throws Exception {
		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
		shopUserModuleParamDO.setShopId(1);
		shopUserModuleParamDO.setUserId(1L);
		shopUserModuleParamDO.setUserPageId(1L);
		shopUserModuleParamDO.setModuleId(1L);
		shopUserModuleParamDO.setSave_config("");
		shopUserModuleParamDO.setRelease_config("");
		shopUserModuleParamManager
				.updateShopUserModuleParam(shopUserModuleParamDO);
	}

	public void testdeleteShopUserModuleParam() throws Exception {
		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
		shopUserModuleParamDO.setShopId(1);
		shopUserModuleParamDO.setUserId(1L);
		shopUserModuleParamDO.setUserPageId(1L);
		shopUserModuleParamDO.setModuleId(1L);
		shopUserModuleParamManager
				.deleteShopUserModuleParam(shopUserModuleParamDO);
	}
}
