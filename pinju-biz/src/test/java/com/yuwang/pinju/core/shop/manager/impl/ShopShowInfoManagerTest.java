package com.yuwang.pinju.core.shop.manager.impl;


import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.shop.ShopInfoDO;


public class ShopShowInfoManagerTest extends BaseTestCase {
	@SpringBean("shopShowInfoManager")
	private ShopShowInfoManager shopShowInfoManager;
	@Test
	public void testQueryShopInfoAllListTest(){
		
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopInfoByShopId(10035,ShopConstant.APPROVE_STATUS_NO);
			
			if(shopInfoDO!=null){
				log.info(shopInfoDO.getUserId());
			}
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testQueryShopInfoByUser(){
		
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(100000325009000l,ShopConstant.APPROVE_STATUS_NO);
			
			if(shopInfoDO!=null){
				log.info(shopInfoDO.getShopId());
			}
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
