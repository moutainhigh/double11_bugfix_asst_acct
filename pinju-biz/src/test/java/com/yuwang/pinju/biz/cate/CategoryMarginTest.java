package com.yuwang.pinju.biz.cate;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.CategoryMarginManager;

public class CategoryMarginTest extends BaseTestCase{
	
	@SpringBean("categoryMarginManager")
	private CategoryMarginManager categoryMarginManager;
	
	public void marginTest(){
		try {
			int margin = categoryMarginManager.getItemMargin(60000000l,2);
			System.out.println(margin);
		} catch (ManagerException e) {
			e.printStackTrace();
		}
	}

}
