package com.yuwang.pinju.core.shop.dao.impl;

import java.util.List;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopPagePrototypeDAO;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;

/**
 * 获取待装修页面的接口测试
 * 
 * @author mike
 * 
 * @since 2011-6-1
 */
public class ShopPagePrototypeDAOTest extends BaseTestCase {
	@SpringBean("shopPagePrototypeDAO")
	private ShopPagePrototypeDAO shopPagePrototypeDAO;
	
	public void testGetAllDesignerPage() {
		try {
			List<ShopPagePrototypeDO> list = shopPagePrototypeDAO.getAllDesignerPage();
			for(ShopPagePrototypeDO entity:list) {
				System.out.println("PAGEID=" + entity.getPageId() + ",NAME=" + entity.getName());
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
