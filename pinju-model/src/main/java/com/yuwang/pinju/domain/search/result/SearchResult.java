package com.yuwang.pinju.domain.search.result;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;

import com.yuwang.pinju.domain.Paginator;

public class SearchResult extends Paginator {

	private static final long serialVersionUID = -3594493766502777330L;
	
	/**
	 * 结果集合
	 */
	private List<Object> resultList;
	
	/**
	 *推荐查询词
	 */
	private Object relatedQuery [] ;
	
	/**
	 *field 统计信息  :默认是catetory下hit条数 
	 */
	List<?> facetField;
	
	/**
	 * 统计该条件下命中数 ：如 price:[0 TO 100]=170
	 */
	Map<String,Integer> facetQuery;
	
	/**
	 * 推荐列表
	 */
	List<Object> recommendationList;
	
	
	public List<Object> getRecommendationList() {
		return recommendationList;
	}

	public void setRecommendationList(List<Object> recommendationList) {
		this.recommendationList = recommendationList;
	}

	public Map<String, Integer> getFacetQuery() {
		return facetQuery;
	}

	public void setFacetQuery(Map<String, Integer> facetQuery) {
		this.facetQuery = facetQuery;
	}

	public List<?> getFacetField() {
		return facetField;
	}

	public void setFacetField(List<?> facetField) {
		this.facetField = facetField;
	}

	public Object [] getRelatedQuery() {
		return relatedQuery;
	}

	public void setRelatedQuery(Object [] relatedQuery) {
		this.relatedQuery = relatedQuery;
	}

	public List<Object> getResultList() {
		return resultList;
	}

	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}

}
