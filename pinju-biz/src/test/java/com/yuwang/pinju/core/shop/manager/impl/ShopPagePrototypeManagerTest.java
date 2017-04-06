package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.manager.ShopPagePrototypeManager;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;

/**
 * 获取待装修页面的接口测试
 * 
 * @author mike
 * 
 * @since 2011-6-1
 */
public class ShopPagePrototypeManagerTest extends BaseTestCase {
	@SpringBean("shopPagePrototypeManager")
	private ShopPagePrototypeManager shopPagePrototypeManager;
	
	public void testGetAllDesignerPage() {
		try {
			List<ShopPagePrototypeDO> list = shopPagePrototypeManager.getAllDesignerPage();
			for(ShopPagePrototypeDO entity:list) {
				System.out.println("PAGEID=" + entity.getPageId() + ",NAME=" + entity.getName());
			}
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
