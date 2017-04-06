package com.yuwang.pinju.biz.item.manager;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.CategoryMarginManager;

public class ItemMarginManagerTest extends BaseTestCase{
	@SpringBean("categoryMarginManager")
	private CategoryMarginManager categoryMarginManager;
	
	public void testMargin(){
		try {
			int margin = categoryMarginManager.getItemMargin(60005841l,0);
			System.out.println("++++++++++++++++"+margin);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}
}
