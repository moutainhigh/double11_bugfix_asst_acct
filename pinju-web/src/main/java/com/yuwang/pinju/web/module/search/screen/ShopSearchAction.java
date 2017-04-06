package com.yuwang.pinju.web.module.search.screen;

import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.httpclient.SolrService;
import com.yuwang.pinju.core.search.manager.SearchManager;
import com.yuwang.pinju.domain.search.SearchShopDO;
import com.yuwang.pinju.domain.search.result.SearchResult;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * 
 * @author liming
 * @since 2011-8-31
 * 
 */
public class ShopSearchAction extends BaseAction implements ModelDriven<SearchShopDO>, Preparable {

	private SearchResult result;
	private SearchShopDO searchQuery = new SearchShopDO();
	private SolrService solrService;

	private SearchManager searchManager;

	/**
	 * 显示店铺搜索页
	 * 
	 * @return
	 */
	public String showShopSearchScreen() {

		if (!StringUtil.isNotBlank(searchQuery.getQ())) {
			return "nullq";
		}

		if (!(searchQuery.getLocation() == null || "".equals(searchQuery.getLocation()))) {
			Set<String> paramSet = new HashSet<String>();
			paramSet.add("city:" + searchQuery.getLocation());
			searchQuery.setFq(paramSet);
		}
		if (!StringUtil.isNotBlank(searchQuery.getType())) {
			searchQuery.setType("1");
		}
		try {
			result = (SearchResult) solrService.searchShop(searchQuery);

			log.debug("resutlt:" + result);
		} catch (Exception e) {
			log.error("店铺搜索错误", e);
		}
		return SUCCESS;
	}

	public SolrService getSolrService() {
		return solrService;
	}

	public void setSolrService(SolrService solrService) {
		this.solrService = solrService;
	}

	public SearchResult getResult() {
		return result;
	}

	public void setResult(SearchResult result) {
		this.result = result;
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	@Override
	public void prepare() throws Exception {

	}

	@Override
	public SearchShopDO getModel() {
		return searchQuery;
	}

}
