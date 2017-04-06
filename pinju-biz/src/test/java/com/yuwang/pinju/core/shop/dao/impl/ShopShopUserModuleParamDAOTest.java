package com.yuwang.pinju.core.shop.dao.impl;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public class ShopShopUserModuleParamDAOTest extends BaseTestCase {
	 	@SpringBean("shopUserModuleParamDao")
	    private ShopUserModuleParamDaoImpl shopUserModuleParamDao;
	 	
	 	public void testinsertShopUserModuleParam() throws Exception{
	 		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
	 		shopUserModuleParamDO.setShopId(1);
	 		shopUserModuleParamDO.setUserId(1L);
	 		shopUserModuleParamDO.setUserPageId(1L);
	 		shopUserModuleParamDO.setModuleId(1L);
	 		shopUserModuleParamDO.setSave_config("");
	 		shopUserModuleParamDO.setRelease_config("");
	 		shopUserModuleParamDao.insertShopUserModuleParam(shopUserModuleParamDO);
	 	}
	 	
	 	public void testqueryShopUserModuleParam() throws Exception{
	 		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
	 		shopUserModuleParamDO.setShopId(1);
	 		shopUserModuleParamDO.setUserId(1L);
	 		shopUserModuleParamDO.setUserPageId(1L);
	 		shopUserModuleParamDO.setModuleId(1L);
	 		shopUserModuleParamDao.queryShopUserModuleParam(shopUserModuleParamDO);
	 	}
	 	
	 	public void testupdateShopUserModuleParam() throws Exception{
	 		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
	 		shopUserModuleParamDO.setShopId(1);
	 		shopUserModuleParamDO.setUserId(1L);
	 		shopUserModuleParamDO.setUserPageId(1L);
	 		shopUserModuleParamDO.setModuleId(1L);
	 		shopUserModuleParamDO.setSave_config("");
	 		shopUserModuleParamDO.setRelease_config("");
	 		shopUserModuleParamDao.updateShopUserModuleParam(shopUserModuleParamDO);
	 	}
	 	
	 	public void testdeleteShopUserModuleParam() throws Exception{
	 		ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
	 		shopUserModuleParamDO.setShopId(1);
	 		shopUserModuleParamDO.setUserId(1L);
	 		shopUserModuleParamDO.setUserPageId(1L);
	 		shopUserModuleParamDO.setModuleId(1L);
	 		shopUserModuleParamDao.deleteShopUserModuleParam(shopUserModuleParamDO);
	 	}

}
