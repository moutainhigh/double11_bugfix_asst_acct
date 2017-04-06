package com.yuwang.pinju.biz.search;
import java.util.ArrayList;
import java.util.List;
import org.unitils.spring.annotation.SpringBean;
import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.httpclient.UpdateSolrService;
import com.yuwang.pinju.domain.search.index.ItemIndexDO;
import com.yuwang.pinju.domain.search.index.ShopIndexD0;

public class TestUpDataIndex extends BaseTestCase {
	@SpringBean("updateSolrService")
	UpdateSolrService updateSolrService;
	
	public void testUpdateItemIndex() throws Exception{
		List<ItemIndexDO> lists = new ArrayList<ItemIndexDO>();
		ItemIndexDO item= new ItemIndexDO();
		ItemIndexDO item2= new ItemIndexDO();
		item.setId(19775893L);
		item.setTitle("周兆华");
		item.setCatetoryId(11l);
		item.setCatetoryName("周兆华");
		item.setCity("南    dfsdfsf  昌");
		item.setCode("20");
		item.setCurStock(11l);
		item.setDeliveryCosts(20l);
		item.setEmsCosts(20l);
		item.setEndTime(new java.util.Date());
		item.setEvaluateNum(11l);
		item.setFeatures("周兆华");
		item.setGmtCreate(new java.util.Date());
		item.setMailCosts(11l);
		item.setPicUrl("ddddd");
		item.setPrice(11l);
		item.setPropertyValuePair("11111");
		item.setProvinces("江西");
		item.setSalesNum(11l);
		item.setSellerId(111111l);
		item.setSellerNick("周兆华");
		item.setSpuId(111l);
		item.setStartTime(new java.util.Date());
		item.setStatus(1l);
		item.setStoreCategories("333");
		item.setItemDegree(333l);
		
		item2.setId(22222l);
		item2.setTitle("周兆华周兆华");
		item2.setCatetoryId(11l);
		item2.setCatetoryName("周兆华");
		item2.setCity(null);
		item2.setCode("20");
		item2.setCurStock(11l);
		item2.setDeliveryCosts(20l);
		item2.setEmsCosts(20l);
		item2.setEndTime(new java.util.Date());
		item2.setEvaluateNum(11l);
		item2.setFeatures("周兆华");
		item2.setGmtCreate(new java.util.Date());
		item2.setMailCosts(11l);
		item2.setPicUrl("ddddd");
		item2.setPrice(11l);
		item2.setPropertyValuePair("11111");
		item2.setProvinces("江西");
		//item2.setSalesNum(11l);
		item2.setSellerId(111111l);
		item2.setSellerNick("周兆华");
		item2.setSpuId(111l);
		item2.setStartTime(new java.util.Date());
		item2.setStatus(1l);
		item2.setStoreCategories("333");
		item2.setItemDegree(333l);
		
		lists.add(item);
		lists.add(item2);
		//updateSolrService.solrItemInput(lists);
		updateSolrService.itemInput(item);
	}
	
	public void testUpdateShopIndex()throws Exception{
		ShopIndexD0 shop = new ShopIndexD0();
		ShopIndexD0 shop2 = new ShopIndexD0();
		ShopIndexD0 shop3 = new ShopIndexD0();
		List<ShopIndexD0> lists = new ArrayList<ShopIndexD0>();
		shop.setCity("南昌");
		shop.setDescription("fsdfsdf");
		shop.setExchangeMargin("dfsdfsdf");
		shop.setId(1111l);
		shop.setName("南昌");
		shop.setNickName("周兆华");
		shop.setOpenDate(new java.util.Date());
		shop.setProductCount(33);
		shop.setProvinces("江西假啊女系");
		shop.setSellerType("sdfsdf");
		shop.setShopCategory("sdfsdf");
		shop.setShopId(3333l);
		shop.setTitle("南昌");
		
		shop2.setCity("南昌南昌");
		shop2.setDescription("fsdfsdf");
		shop2.setExchangeMargin("dfsdfsdf");
		shop2.setId(1121l);
		shop2.setName("南昌");
		shop2.setNickName("周兆华");
		shop2.setOpenDate(new java.util.Date());
		shop2.setProductCount(33);
		shop2.setProvinces("江西");
		shop2.setSellerType("sdfsdf");
		shop2.setShopCategory("sdfsdf");
		shop2.setShopId(3333l);
		shop2.setTitle("南昌");
		
		shop3.setCity("昌南");
		shop3.setDescription("fsdfsdf");
		shop3.setExchangeMargin("dfsdfsdf");
		shop3.setId(33333l);
		shop3.setName("昌南");
		shop3.setNickName("周兆华");
		shop3.setOpenDate(new java.util.Date());
		shop3.setProductCount(33);
		shop3.setProvinces("西江");
		shop3.setSellerType("sdfsdf");
		shop3.setShopCategory("sdfsdf");
		shop3.setShopId(3333l);
		shop3.setTitle("南昌");
		
		
		lists.add(shop);
		lists.add(shop2);
		lists.add(shop3);
		//updateSolrService.shopInput(lists);
		updateSolrService.shopInput(shop2);
	}
	
	public  void testDeleteItemIndex() throws Exception{
		updateSolrService.deleteItemById(19775893L);
	}
	
	public  void testDeleteShopIndex() throws Exception{
		updateSolrService.deleteShopById(1111l);
	}
}

