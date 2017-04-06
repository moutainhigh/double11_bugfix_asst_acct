package com.yuwang.pinju.biz.search;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;
import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.search.manager.impl.SearchManagerImpl;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.SearchShopItem;
import com.yuwang.pinju.domain.search.result.SearchResult;


public class SearchManagerImplTest_z extends BaseTestCase {
	@SpringBean("searchManager")
	SearchManagerImpl  searchManager;
	
	public void testSearchItem()throws ManagerException{
		SearchItemDO search = new SearchItemDO();
		//search.setQuery("SKU 相机！~！！！测试测试");
		//search.setQuery("Q币");
		SearchResult  result = searchManager.searchItem(search);
		System.out.println(result);
	}
	
	
	@Test 
	public void testSearchItemByShop() throws ManagerException{
		SearchShopItem query = new  SearchShopItem();
		query.setSellerId(100000055000000L);
		//query.setQuery("SKU 相机！~！！！测试测试");
		//query.setQuery("Q币");
		SearchResult  result = searchManager.searchItemByShop(query);

		System.out.println(result);
		
	}
}
