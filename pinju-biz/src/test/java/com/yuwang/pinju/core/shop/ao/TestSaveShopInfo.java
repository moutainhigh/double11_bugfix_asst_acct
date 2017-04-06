
package com.yuwang.pinju.core.shop.ao;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

public class TestSaveShopInfo extends Assert {
	/**
	 * 保存店铺基本信息
	 * @throws DaoException
	 */
	@Test public void testSaveShopInfo() throws DaoException{
		FileSystemXmlApplicationContext factory = new FileSystemXmlApplicationContext("/src/test/resources/applicationContext.xml");
		
//		ShopShowInfoAO shopShowInfoAO = (ShopShowInfoAO)factory.getBean("shopShowInfoAo");
//	
//		
//		ShopInfoDO shopInfoDO = new ShopInfoDO(); 
//		shopInfoDO.setUserId(3000);
//		shopInfoDO.setName("shop test222");
//		shopInfoDO.setShopCategory("1");
//		shopInfoDO.setSellerType(1);
//		shopInfoDO.setTitle("title 111");
//		shopInfoDO.setGoodsSource(1);
//		shopInfoDO.setDescription("服装服装服装服装服装服装！！！！");
//		shopInfoDO.setShopId(111);
//		shopInfoDO.setGmtCreate(new Date());
//		Object backObject;
//		try {
//			backObject = shopShowInfoAO.saveShopBaseInfo(shopInfoDO);
//		} catch (ManagerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

		ShopShowInfoAO shopShowInfoAO = (ShopShowInfoAO)factory.getBean("shopShowInfoAo");
	
		
		ShopInfoDO shopInfoDO = new ShopInfoDO();
		shopInfoDO.setUserId(3000L);
		shopInfoDO.setName("shop test222");
		shopInfoDO.setShopCategory("1");
		shopInfoDO.setSellerType("1");
		shopInfoDO.setTitle("title 111");
		shopInfoDO.setGoodsSource(1);
		shopInfoDO.setDescription("服装服装服装服装服装服装！！！！");
		shopInfoDO.setShopId(111);
		shopInfoDO.setGmtCreate(new Date());
		Object backObject;
		try {
			backObject = shopShowInfoAO.saveShopBaseInfo(shopInfoDO);
			System.out.println(backObject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		/*try {
			shopShowInfoAO.saveShopBaseInfo(shopInfoDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
//		assertEquals(map.size(),true);
//		assertNotNull(map);
//		assertTrue(map.size()>0);
	}
}

