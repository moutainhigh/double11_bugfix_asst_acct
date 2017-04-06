package com.yuwang.pinju.web.module.search.screen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.constant.search.SearchConstent;
import com.yuwang.pinju.core.httpclient.SolrService;
import com.yuwang.pinju.core.item.ao.CategoryAO;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.domain.item.SearchCategoryResult;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.result.SearchResult;
import com.yuwang.pinju.web.module.BaseAction;

public class ItemSearchAction extends BaseAction implements ModelDriven<SearchItemDO>, Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3036261608762561675L;
	private SolrService solrService;
	private CategoryAO categoryAO;

	private CategoryCacheManager categoryCacheManager;

	// 左侧类目树
	private SearchCategoryResult cateResult;
	// 搜索查询结果
	private SearchResult query;
	private SearchItemDO searchItemDO = new SearchItemDO();

	public CategoryAO getCategoryAO() {
		return categoryAO;
	}

	public SearchCategoryResult getCateResult() {
		return cateResult;
	}

	public CategoryCacheManager getCategoryCacheManager() {
		return categoryCacheManager;
	}

	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	@Override
	public SearchItemDO getModel() {
		return searchItemDO;
	}

	public SearchResult getQuery() {
		return query;
	}

	public SearchItemDO getSearchItemDO() {
		return searchItemDO;
	}

	public SolrService getSolrService() {
		return solrService;
	}

	public void prepare() throws Exception {

	}

	/**
	 * 显示商品搜索页
	 * 
	 * @return
	 */
	public String search() throws Exception {
		try {

			if (!StringUtil.isNotBlank(searchItemDO.getQ())
					&& (!StringUtil.isNotBlank(searchItemDO.getCp()) || !StringUtil.isNumeric(searchItemDO.getCp()))
					&& !StringUtil.isNotBlank(searchItemDO.getCatetoryName())) {
				return "nullq";
			}

			if (!StringUtil.isEmpty(searchItemDO.getCp()) && StringUtil.isNumeric(searchItemDO.getCp())) {
				searchItemDO.setFacetField(null);
				cateResult = categoryAO.getCategoryLevelByCateId(Long.parseLong(searchItemDO.getCp()));
			} else {
				if (null == searchItemDO.getFacetField()) {
					searchItemDO.setFacetField(new String[] { "catetoryName" });
				}
			}
			if (!StringUtil.isNotBlank(searchItemDO.getIp())) {
				searchItemDO.setIp(ipAddr());
			}
			query = (SearchResult) solrService.searchItem(searchItemDO);

			if (query.getResultList() == null || query.getResultList().size() < 1) {
				if (searchItemDO.getCp() == null) {
					if(cateResult == null)cateResult = categoryAO.getCategoryLevelByCateId(60004021);
				}
				searchItemDO = new SearchItemDO();
				//searchItemDO.setQ("*:*");
				searchItemDO.setCount("8");
				searchItemDO.setSort("sortRandom" + System.currentTimeMillis() + "-desc");
				List ls = solrService.searchItem(searchItemDO,SearchConstent.SEARCH_TYPE_EXTENG).getResultList();
				if(ls!=null && ls.size()>0){
					query.setRecommendationList(ls);
				}
			}

		} catch (Exception e) {
			query = new SearchResult();
			log.error(e);
		}
		return SUCCESS;
	}

	public String ipAddr() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (log.isDebugEnabled()) {
			log.debug("Visitor's IP address is :" + ip);
		}
		return ip;
	}

	public void setCategoryAO(CategoryAO categoryAO) {
		this.categoryAO = categoryAO;
	}

	public void setCateResult(SearchCategoryResult cateResult) {
		this.cateResult = cateResult;
	}

	public void setQuery(SearchResult query) {
		this.query = query;
	}

	public void setSearchItemDO(SearchItemDO searchItemDO) {
		this.searchItemDO = searchItemDO;
	}

	public void setSolrService(SolrService solrService) {
		this.solrService = solrService;
	}

}
