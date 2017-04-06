package com.yuwang.pinju.biz.search;

import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.httpclient.SolrService;
public class TestSolrService  extends BaseTestCase {
	@SpringBean("solrService")
	SolrService solrService;
	
	public void test1() throws Exception{
		log.info(solrService.getUpdateItem(1111l));
	}
	
	public void testShop() throws Exception{
		log.info(solrService.getUpdateShop(1121L));
	}
	
	public void testItemSearchBox() throws Exception{
		solrService.itemSearchBox("魔尺");
	}
	
	public void testShopSearchBox() throws Exception{
		solrService.shopSearchBox("苹果");
	}
}
