package com.yuwang.pinju.core.shop.dao.impl;

import java.util.List;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.shop.ao.impl.ShopPageLayoutAOImpl;
import com.yuwang.pinju.core.shop.dao.ShopPageLayoutDAO;
import com.yuwang.pinju.core.shop.manager.ShopPageLayoutManager;

public class ShopPageLayoutTest extends BaseTestCase {
	 	@SpringBean("shopPageLayoutDAO")
	    private ShopPageLayoutDAOImpl shopPageLayoutDAO;
	 	@SpringBean("shopPageLayoutAO")
	 	private ShopPageLayoutAOImpl shopPageLayoutAO;
	 	
	 	
//	 	public void testinsertUserPageLayout() throws Exception{
//	 		Object ob = shopPageLayoutDAO.insertUserPageLayout(1, 1, 1, "11");
//	 		System.out.println(ob);
//	 	}
//	 	
//	 	public void testqueryUserPageLayout() throws Exception{
//	 		Object ob = shopPageLayoutDAO.queryUserPageLayout(1, 1, 1);
//	 		System.out.println(ob);
//	 	}
//	 	
//	 	public void testqueryPageLayout() throws Exception{
//	 		Object ob = shopPageLayoutDAO.queryPageLayout(1);
//	 		System.out.println(ob);
//	 	}
//	 	
//	 	public void testsaveUserPageLayout() throws Exception{
//	 		Object ob = shopPageLayoutDAO.saveUserPageLayout(1, 1, 1, "222");
//	 		System.out.println(ob);
//	 	}
	 	
//	 	public void testqueryPageLayout() throws Exception{
//
//	 		List result =(List)shopPageLayoutAO.queryPageLayout(new Long(100000055000000L), new Long(100000055000000L), 1);
//	 		
//	 		System.out.println(result);
//
//	 	}
	 	

	 	public void testsavePageLayout() throws Exception{
//	 		Object ob = shopPageLayoutAO.savePageLayout(new Long(100000055000000L), , 1, "22");
//	 		System.out.println(ob);
	 	}

}
