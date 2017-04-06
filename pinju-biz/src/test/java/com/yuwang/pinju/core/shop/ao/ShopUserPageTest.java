
package com.yuwang.pinju.core.shop.ao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.manager.ShopCustomPageManager;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

public class ShopUserPageTest extends Assert {
	/**
	 * 根据userid获取店铺基本信息
	 * @throws DaoException
	 */
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	@Test public void testSaveShopInfo() throws DaoException{
		FileSystemXmlApplicationContext factory = new FileSystemXmlApplicationContext("/src/test/resources/applicationContext.xml");
		
//		ShopCustomPageAO shopCustomPageAO = (ShopCustomPageAO)factory.getBean("shopCustomPageAO");
		
		ShopCustomPageManager shopCustomPageManager = (ShopCustomPageManager)factory.getBean("shopCustomPageManager");
		
		try {
			ShopUserPageDO shopUserPageDO = new ShopUserPageDO();
//			shopUserPageDO.setPageId(new Long(1));
//			shopUserPageDO.setLeftHtml1("<html></html>");
//			shopUserPageDO.setUserId(new Long(1000));
//			shopUserPageDO.setShopId(1100);
//			shopUserPageDO.setGmtCreate(new Date());
//			Object obj = shopCustomPageAO.saveCustomModuleByPage(shopUserPageDO);
//			log.info("insert ----------------------- "+ obj);
			
//			shopUserPageDO = new ShopUserPageDO();
//			shopUserPageDO.setPageId(new Long(1));
//			
//			int i=0;
//			String str="com.yuwang.pinju.domain.shop.ShopUserPageDO"; 
//			Class c;
//			try {
//				c = Class.forName(str);
////				Object obj=c.newInstance(); 
//				Method m = c.getMethod("setRightHtml1",new Class[]{Class.forName("java.lang.String")});
//				m.invoke(shopUserPageDO,new Object[]{"<html>test2</html>"}); 
//				
//				Method m2 = c.getMethod("getRightHtml1");
//				Object obj = m2.invoke(shopUserPageDO); 
//				log.info("getRightHtml1 -------------- " + obj.toString());
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
////			shopUserPageDO.setLeftHtml1("<html><b></b></html>");
//			shopUserPageDO.setUserId(new Long(1000));
//			shopUserPageDO.setShopId(1100);
//			shopUserPageDO.setGmtModified(new Date());
//			Object obj2 = shopCustomPageAO.updateCustomModuleByPage(shopUserPageDO);
//			log.info("update ----------------------- "+ obj2);
			
			shopUserPageDO = new ShopUserPageDO();
			shopUserPageDO.setPageId(1);
			shopUserPageDO.setUserId(100000055000000l);
			shopUserPageDO.setShopId(10122);
//			shopUserPageDO.setRightHtml1("asfasdfasdf");
			ShopUserPageDO shopUserPageDO2 =shopCustomPageManager.queryCustomPageModule(shopUserPageDO);
			if(shopUserPageDO2 != null){
				log.info(shopUserPageDO2.getId());
			}else{
				log.info(shopUserPageDO2);
			}
			
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


