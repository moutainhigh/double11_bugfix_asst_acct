package com.yuwang.pinju.core.shop.ao;

import java.util.List;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;

/**
 * 获取待装修页面的接口测试
 * 
 * @author mike
 * 
 * @since 2011-6-1
 */
public class ShopModulePrototypeAOTest extends BaseTestCase {
	@SpringBean("shopModulePrototypeAO")
	private ShopModulePrototypeAO shopModulePrototypeAO;

	public void testGetDesignerModuleByPage() {
		try {
			List<ShopModulePrototypeDO> list = shopModulePrototypeAO
					.getDesignerModuleByPage(1, 1);
			for (ShopModulePrototypeDO entity : list) {
				System.out.println("MODULEID=" + entity.getModuleId()
						+ ",NAME=" + entity.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
