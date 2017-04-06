package com.yuwang.pinju.web.module.search.screen;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.constant.search.SearchConstent;
import com.yuwang.pinju.core.httpclient.SolrService;
import com.yuwang.pinju.core.item.ao.CategoryAO;
import com.yuwang.pinju.domain.item.SearchCategoryResult;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.result.SearchResult;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * 热点搜索
 * 
 * @author liming
 * 
 */
public class HotAction extends BaseAction implements ModelDriven<SearchItemDO>, Preparable {

	private static final long serialVersionUID = 1L;

	private SearchItemDO searchItemDO = new SearchItemDO();

	private SearchResult query;

	private SolrService solrService;

	private CategoryAO categoryAO;

	private SearchCategoryResult cateResult;

	/**
	 * 热点搜索
	 * 
	 * @return
	 * @throws Exception
	 */
	public String hot() throws Exception {
		try {
			searchItemDO.setSearchType(SearchConstent.SEARCH_TYPE_HOT);
			if (!StringUtil.isNotBlank(searchItemDO.getQ())
					&& (!StringUtil.isNotBlank(searchItemDO.getCp()) || !StringUtil.isNumeric(searchItemDO.getCp()))
					&& !StringUtil.isNotBlank(searchItemDO.getCatetoryName())) {
				return "nullq";
			}

			if (!StringUtil.isNotBlank(searchItemDO.getIp())) {
				searchItemDO.setIp(ipAddr());
			}
			// 热点搜索 排序规则
			searchItemDO.setSort("salesNum-desc");
			searchItemDO.setFacetField(new String[] { "catetoryName" });
			//searchItemDO.setFacetField(new String[] { "catetoryId" });
			query = (SearchResult) solrService.searchItem(searchItemDO);
			
			if (query.getResultList() == null || query.getResultList().size() < 1) {
				if (searchItemDO.getCp() == null) {
					cateResult = categoryAO.getCategoryLevelByCateId(60004021);
				}
				searchItemDO = new SearchItemDO();
				//searchItemDO.setQ("*:*");
				searchItemDO.setCount("8");
				searchItemDO.setSort("sortRandom" + System.currentTimeMillis() + "-desc");
				List ls = solrService.searchItem(searchItemDO).getResultList();
				if(ls!=null && ls.size()>0){
					query.setRecommendationList(ls);
				}
			}
			
			//if (query.getResultList() == null || query.getResultList().size() < 1) {
				//if (searchItemDO.getCp() == null) {
			//cateResult = categoryAO.getCategoryLevelByCateId(60004021);
				//}
		//	}
			
			/*if (query.getFacetField() != null && query.getFacetField().size() > 0) {
				String hotCp = query.getFacetField().get(0).toString();
				searchItemDO.setCp(hotCp.split(" ")[0]);
			}

			if (searchItemDO.getCp() == null) {
				cateResult = categoryAO.getCategoryLevelByCateId(60004021);
			} else {
				cateResult = categoryAO.getCategoryLevelByCateId(Long.parseLong(searchItemDO.getCp()));
			}*/

		} catch (Exception e) {
			query = new SearchResult();
			log.error(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 促销搜索
	 * 
	 * @return
	 * @throws Exception
	 */
	public String promotion() throws Exception {
		try {
			
			searchItemDO.setSearchType(SearchConstent.SEARCH_TYPE_PROMOTION);
			if (!StringUtil.isNotBlank(searchItemDO.getQ())
					&& (!StringUtil.isNotBlank(searchItemDO.getCp()) || !StringUtil.isNumeric(searchItemDO.getCp()))
					&& !StringUtil.isNotBlank(searchItemDO.getCatetoryName())) {
				return "nullq";
			}

			if (!StringUtil.isNotBlank(searchItemDO.getIp())) {
				searchItemDO.setIp(ipAddr());
			}
			// 热点搜索 排序规则
			searchItemDO.setSort("salesNum-desc");
			searchItemDO.setFacetField(new String[] { "catetoryName" });
			//searchItemDO.setFacetField(new String[] { "catetoryId" });
			query = (SearchResult) solrService.searchItem(searchItemDO);
			
			//if (query.getResultList() == null || query.getResultList().size() < 1) {
				//if (searchItemDO.getCp() == null) {
			//cateResult = categoryAO.getCategoryLevelByCateId(60004021);
				//}
		//	}
			
			/*if (query.getFacetField() != null && query.getFacetField().size() > 0) {
				String hotCp = query.getFacetField().get(0).toString();
				searchItemDO.setCp(hotCp.split(" ")[0]);
			}

			if (searchItemDO.getCp() == null) {
				cateResult = categoryAO.getCategoryLevelByCateId(60004021);
			} else {
				cateResult = categoryAO.getCategoryLevelByCateId(Long.parseLong(searchItemDO.getCp()));
			}*/

		} catch (Exception e) {
			query = new SearchResult();
			log.error(e);
		}
		return SUCCESS;
	}

	private String ipAddr() {
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

	public SearchResult getQuery() {
		return query;
	}

	public void setQuery(SearchResult query) {
		this.query = query;
	}

	public SolrService getSolrService() {
		return solrService;
	}

	public void setSolrService(SolrService solrService) {
		this.solrService = solrService;
	}

	public CategoryAO getCategoryAO() {
		return categoryAO;
	}

	public void setCategoryAO(CategoryAO categoryAO) {
		this.categoryAO = categoryAO;
	}

	public SearchCategoryResult getCateResult() {
		return cateResult;
	}

	public void setCateResult(SearchCategoryResult cateResult) {
		this.cateResult = cateResult;
	}

	@Override
	public SearchItemDO getModel() {
		return searchItemDO;
	}

	@Override
	public void prepare() throws Exception {
	}

}
