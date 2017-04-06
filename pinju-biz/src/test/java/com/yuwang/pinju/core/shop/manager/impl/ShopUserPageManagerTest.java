/**
 * 
 */
package com.yuwang.pinju.core.shop.manager.impl;

import java.util.*;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.manager.ShopUserPageManager;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

/**
 * @author liyouguo
 *
 */
public class ShopUserPageManagerTest extends BaseTestCase {
	@SpringBean("shopUserPageManager")
	private ShopUserPageManager shopUserPageManager;

	public void testSaveUserCustomerPage() {
		List<ShopUserPageDO> userPageList = new ArrayList<ShopUserPageDO>();
		ShopUserPageDO entity = new ShopUserPageDO();
		entity.setTitle("自定义页面1");
		entity.setPageId(3);
		entity.setUserId(100000055000000L);
		entity.setShopId(10122);
		entity.setOrderId(1);
		entity.setSaveType(1);
		userPageList.add(entity);
		entity = new ShopUserPageDO();
		entity.setTitle("自定义页面2");
		entity.setPageId(3);
		entity.setUserId(100000055000000L);
		entity.setShopId(11022);
		entity.setOrderId(3);
		entity.setSaveType(1);
		userPageList.add(entity);
		entity = new ShopUserPageDO();
		entity.setTitle("自定义页面3");
		entity.setPageId(3);
		entity.setUserId(100000055000000L);
		entity.setShopId(11022);
		entity.setOrderId(2);
		entity.setSaveType(1);
		userPageList.add(entity);
		try {
			shopUserPageManager.saveUserCustomerPage(userPageList);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testGetTopPageHtml(){
		try {
			shopUserPageManager.getTopPageHtml(100000055000000L);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testGetLeftPageHtml(){
		try {
			shopUserPageManager.getLeftPageHtml(100000055000000L);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
