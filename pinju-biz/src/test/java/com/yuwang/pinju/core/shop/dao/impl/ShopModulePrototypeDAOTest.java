package com.yuwang.pinju.core.shop.dao.impl;

import java.util.List;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopModulePrototypeDAO;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;

/**
 * 获取待装修页面的接口测试
 * 
 * @author mike
 * 
 * @since 2011-6-1
 */
public class ShopModulePrototypeDAOTest extends BaseTestCase {
	@SpringBean("shopModulePrototypeDAO")
	private ShopModulePrototypeDAO shopModulePrototypeDAO;
	
	public void testGetDesignerModuleByPage() {
		try {
			ShopModulePrototypeDO moduleDO = new ShopModulePrototypeDO();
			List<ShopModulePrototypeDO> list = shopModulePrototypeDAO.getDesignerModuleByPage(moduleDO);
			for(ShopModulePrototypeDO entity:list) {
				System.out.println("MODULEID=" + entity.getModuleId() + ",NAME=" + entity.getName());
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
