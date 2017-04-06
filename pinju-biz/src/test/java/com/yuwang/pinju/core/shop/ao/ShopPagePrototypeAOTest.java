package com.yuwang.pinju.core.shop.ao;

import java.util.List;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;

/**
 * 获取待装修页面的接口测试
 * 
 * @author mike
 * 
 * @since 2011-6-1
 */
public class ShopPagePrototypeAOTest extends BaseTestCase {
	@SpringBean("shopPagePrototypeAO")
	private ShopPagePrototypeAO shopPagePrototypeAO;
	
	public void testGetAllDesignerPage() {
		try {
			List<ShopPagePrototypeDO> list = shopPagePrototypeAO.getAllDesignerPage();
			for(ShopPagePrototypeDO entity:list) {
				System.out.println("PAGEID=" + entity.getPageId() + ",NAME=" + entity.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
