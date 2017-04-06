package com.yuwang.pinju.domain.search;

import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * 
 * @author liming
 * @since 2011-7-19
 * 
 */
public class SearchBaseDO implements java.io.Serializable {

	/**
	 * 高亮前缀
	 */
	public static String HIGH_LIGHT_SIMPLE_PRE = "<strong>";

	/**
	 * 高亮后缀
	 */
	public static String HIGH_LIGHT_SIMPLE_POST = "</strong>";


	private static final long serialVersionUID = -4131535610257473913L;

	/**
	 * 按地点过滤
	 */
	private String city;
	
	
	/**
	 * 访问者ip
	 */
	private String ip;

	/**
	 * 每页显示结果数 默认是40
	 */
	private String count = "20";

	public static Integer DEFAULT_COUNT = 20;

	/**
	 * @deprecated SDO<br>
	 *             utf8|gbk 表示url中的汉字编码，默认utf8
	 */
	private String encode;
	
	/**
	 *统计字段
	 */
	private String[] facetField;

	/**
	 * 是否启动统计
	 */
	private Boolean facet = true;
	/**
	 * 统计结果限制
	 */
	private String facetLimit;
	/**
	 * 统计查询
	 */
	private String[] facetQuery;
	/**
	 * 统计排序
	 */
	private String facetSort;
	/**
	 * solr / lucence 过滤条件
	 */
	private Set<String> fq;
	/**
	 * 是否高亮
	 */
	private Boolean hl = true;

	/**
	 * 需要高亮字段
	 */
	private String[] hlFl;

	/**
	 * 搜索词
	 */
	private String q;

	/**
	 * 排序
	 */
	private String sort;

	/**
	 * 页码 默认=1
	 */
	private String start = "1";

	public String getCity() {
		return city;
	}

	public String getCount() {
		return count;
	}


	/**
	 * @deprecated SDO<br>
	 * @return
	 */
	public String getEncode() {
		return encode;
	}

	public Boolean getFacet() {
		return facet;
	}

	public String[] getFacetField() {
		return facetField;
	}

	public String getFacetLimit() {
		return facetLimit;
	}

	public String[] getFacetQuery() {
		return facetQuery;
	}

	public String getFacetSort() {
		return facetSort;
	}

	public Set<String> getFq() {
		return fq;
	}

	public Boolean getHl() {
		return hl;
	}

	public String[] getHlFl() {
		return hlFl;
	}

	public String getIp() {
		return ip;
	}

	/**
	 * @deprecated SDO<br>
	 * @return parameter key/value;begin with ? (not include ?)
	 */
	public String getParametersValue() throws UnsupportedEncodingException {
		return null;
	}

	public String getQ() {
		return q;
	}

	public String getSort() {
		return sort;
	}

	public String getStart() {
		return start;
	}

	public Set<String> getWrapFq() {
		return this.getFq();
	}

	public String getWrapQ() {
		return this.getQ();
	}

	public void setCity(String city) {
		this.city = city;
	}

	// get set

	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * @deprecated SDO<br>
	 * @param encode
	 */
	public void setEncode(String encode) {
		this.encode = encode;
	}

	public void setFacet(Boolean facet) {
		this.facet = facet;
	}

	public void setFacetField(String[] facetField) {
		this.facetField = facetField;
	}

	public void setFacetLimit(String facetLimit) {
		this.facetLimit = facetLimit;
	}

	public void setFacetQuery(String[] facetQuery) {
		this.facetQuery = facetQuery;
	}

	public void setFacetSort(String facetSort) {
		this.facetSort = facetSort;
	}

	public void setFq(Set<String> fq) {
		this.fq = fq;
	}

	public void setHl(Boolean hl) {
		this.hl = hl;
	}

	public void setHlFl(String[] hlFl) {
		this.hlFl = hlFl;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * 
	 * @param sort
	 *            as fieldName desc/asc
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setStart(String start) {
		this.start = start;
	}

	protected Boolean isNotEmpty(String obj) {
		if (null == obj || "".equals(obj))
			return false;
		else
			return true;
	}

	protected Boolean isNotNull(String obj) {
		if (null == obj) {
			return false;
		} else
			return true;
	}

}
