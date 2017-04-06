package com.yuwang.pinju.biz.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.yuwang.pinju.biz.BaseTestCase;
import com.yuwang.pinju.core.httpclient.HttpService;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.SearchShopDO;
import com.yuwang.pinju.domain.search.result.ShopResult;

public class TestHttpServiceImpl extends BaseTestCase {
	@SpringBean("httpService")
	HttpService httpService;

	@Test
	public void testdoGet() throws IOException {
		// HttpServiceImpl service = new HttpServiceImpl();
		// System.out.println(service.doGet("http://10.245.135.201:8080/item/?q=%E7%9B%B8%E6%9C%BA&count=1&start=1"));
		// httpService.doGet("http://10.245.135.201/s/?q=%E5%87%AF%E8%BF%AA%E6%8B%89%E5%85%8B");
		Map<String, String> query = new HashMap<String, String>();

		query.put("q", "相机");
		query.put("count", "2");
		query.put("start", "1");
		// query.put("searchType","item");
		// query.put("q", "11");
		// query.put("q", "11");
		// System.out.println(httpService.doGet(query));

		SearchItemDO _do = new SearchItemDO();
//		_do.setType(1L);
//		_do.setQ("相机");
//		_do.setCount(1);
//		_do.setStart(1);
	}

	public void testSearchShop() throws IOException {
		SearchShopDO shop = new SearchShopDO();
//		shop.setType(shop.SEARCH_TYPE_SHOP);
		shop.setQ("");
//		shop.setCount(1);
//		shop.setStart(1);
		String shopJson = httpService.doGet(shop);
		System.out.print(shopJson);
	}

	public void testJsonToList() throws IOException {
		SearchShopDO shopSearch = new SearchShopDO();
		List shopList = new ArrayList();
//		shopSearch.setType(shopSearch.SEARCH_TYPE_SHOP);
		shopSearch.setQ("");
		String returnJson = httpService.doGet(shopSearch);
		if (returnJson != null && returnJson != "") {
			// String strJson = returnJson.replace("?(", "").replace(")",
			// "").replace(";", "");
			if (returnJson.startsWith("{")) {
				JSONObject jsonObjcet = JSONObject.fromObject(returnJson);
				JSONArray jsonArray = JSONArray.fromObject(jsonObjcet.get("results"));
				JSONObject jsonObj = jsonArray.getJSONObject(0);
				JSONArray dataArray = JSONArray.fromObject(jsonObj.get("docs"));
				for (int i = 0; i < dataArray.size(); i++) {
					ShopResult shop = new ShopResult();
					JSONObject jsonShop = dataArray.getJSONObject(i);
					shop.setId(new Long(jsonShop.get("ID").toString()));
					//shop.setAddress(jsonShop.get("ADDRESS").toString());
					//shop.setApproveStatus(new Integer(jsonShop.get("APPROVE_STATUS").toString()));
					//shop.setBrandCertificate(jsonShop.get("BRAND_CERTIFICATE").toString());
					//shop.setBrandEnglishName(jsonShop.get("BRAND_ENGLISH_NAME").toString());
					//shop.setBrandName(jsonShop.get("BRAND_LOGO").toString());
					//shop.setBrandLogo(jsonShop.get("BRAND_LOGO").toString());
					//shop.setNickName(jsonShop.get("NAME").toString());
					//shop.setBrandStory(jsonShop.get("BRAND_STORY").toString());
					Object jsonStr = jsonShop.get("FIRST_CATEGORY");
					List<String> list = (List<String>) jsonStr;
					//shop.setFirstCategory(list);
					shopList.add(shop);
				}
				/**
				for (int i = 0; i < shopList.size(); i++) {
					ShopResult shop1 = (ShopResult) shopList.get(i);
					for (int j = 0; j < shop1.getFirstCategory().size(); j++) {
						System.out.println(shop1.getFirstCategory().get(j));
					}
				}
				**/
			}
		}
	}
}
