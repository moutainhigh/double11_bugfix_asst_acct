package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.manager.ShopModulePrototypeManager;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;

/**
 * 获取待装修页面的接口测试
 * 
 * @author mike
 * 
 * @since 2011-6-1
 */
public class ShopModulePrototypeManagerTest extends BaseTestCase {
	@SpringBean("shopModulePrototypeManager")
	private ShopModulePrototypeManager shopModulePrototypeManager;

	public void testGetDesignerModuleByUserPage() {
		try {
			List<ShopModulePrototypeDO> list = shopModulePrototypeManager
					.getDesignerModuleByUserPage(1, 1);
			for (ShopModulePrototypeDO entity : list) {
				System.out.println("MODULEID=" + entity.getPageId() + ",NAME="
						+ entity.getName());
			}
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
