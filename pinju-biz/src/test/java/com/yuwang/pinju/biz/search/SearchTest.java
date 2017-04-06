package com.yuwang.pinju.biz.search;

import java.util.List;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.search.manager.SearchManager;
import com.yuwang.pinju.domain.search.SearchShopItem;
import com.yuwang.pinju.domain.search.result.ItemResult;
import com.yuwang.pinju.domain.search.result.SearchResult;

public class SearchTest extends BaseTestCase {

	@SpringBean("searchManager")
	private SearchManager searchManager;

	@Test
	public void testSearch() throws Exception {

		SearchShopItem searchShopItem = new SearchShopItem();
		searchShopItem.setSellerId(100000310009000L);
		/*searchShopItem.setStart(3);
		searchShopItem.setCount(20);*/

		SearchResult searchResult = searchManager.searchItemByShop(searchShopItem);

		

		List ls = searchResult.getResultList();
		
		System.out.println("总记录数" +searchResult.getItems());
		System.out.println("总页数" + searchResult.getPages());
		System.out.println("当前页数" + searchResult.getPage());

		for (Object object : ls) {
			ItemResult itemResult = (ItemResult) object;
			System.out.print(itemResult.getId() + ",");
			System.out.print(itemResult.getTitle() + ",");
			System.out.println(itemResult.getGmtCreate());
		}
		System.out.println(ls.size());

	}

}
