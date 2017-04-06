package com.yuwang.pinju.core.shop.manager.impl;


import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.domain.shop.ShopQuery;


public class ShopOpenManagerTest extends BaseTestCase {
	@SpringBean("shopOpenManager")
	private  ShopOpenManager shopOpenManager;
	/*@Test
	public void queryShopInfoAllListTest(){
		List<ShopBusinessInfoDO> list;
		try {
			list = shopOpenManager.queryShopInfoAllList();
			for(ShopBusinessInfoDO shop:list){
				System.out.println("MODULEID=" + shop+ ",NAME=" + shop.getName());
			}
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	@Test
	public void testqueryShopBusinessInfo(){/*
		ShopQuery shopQuery = new ShopQuery();
		//shopQuery.setProvince("上海");
		//shopQuery.setExchangeType(1);
		
		shopQuery.setEndRow(1);
		shopQuery.setStartRow(3);
		
		
		
		
		try {
			shopQuery =  shopOpenManager.queryListShopBusinessInfo(shopQuery);
			System.out.println("shopQuery count================= "+shopQuery.getItems());
			System.out.println("shopQuery resultList================= "+shopQuery.getResultList());
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/}
	
	@Test
	public void setSellerIsSupplier(){/*
		try {
			shopOpenManager.setSellerIsSupplier(10100, 1);
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/}
	
	@Test
	public void testsetShopIsOpenForMargin(){
		try {
			Object object = shopOpenManager.setShopIsOpenForMargin(100000630009000l);
			log.info(object+"------------------------");
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
