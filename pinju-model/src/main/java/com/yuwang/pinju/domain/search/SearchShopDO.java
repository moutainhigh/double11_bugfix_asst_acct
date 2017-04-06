package com.yuwang.pinju.domain.search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.util.ClientUtils;

/**
 * 店铺搜索
 * 
 * @author liming
 * @since 2011-07-19
 * 
 */
public class SearchShopDO extends SearchBaseDO {

	private String keyword;

	private String location;
	private String provinces;

	private String type;
	private String[] conditions;

	/**
	 * 搜索类型
	 */
	private int searchType = 2;

	private static final long serialVersionUID = 3001703229641674836L;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 查询参数扩展
	 */
	private Map<String, String> extParam;

	public SearchShopDO() {
		this.setHlFl(new String[] { "name", "title", "nickName" });
	}

	public String[] getConditions() {
		return conditions;
	}

	public Map<String, String> getExtParam() {
		return extParam;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getLocation() {
		return location;
	}

	public String getParametersValue() throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		if (isNotEmpty(this.getCity())) {
			sb.append("city="
					+ URLEncoder.encode(this.getCity(), null != this.getEncode() ? this.getEncode() : "utf-8"));
		}
		if (isNotNull(this.getCount())) {
			sb.append("&count=" + this.getCount());
		}
		if (isNotNull(this.getStart())) {
			sb.append("&start=" + this.getStart());
		}
		if (isNotEmpty(this.getSort())) {
			sb.append("&sort=" + this.getSort());
		}
		if (this.getExtParam() != null) {
			Set<String> keySet = this.getExtParam().keySet();
			for (String key : keySet) {
				sb.append(key + this.getExtParam().get(key));
			}
		}
		if (this.isNotEmpty(this.getQ())) {
			sb.append("&q=" + URLEncoder.encode(this.getQ(), null != this.getEncode() ? this.getEncode() : "utf-8"));
		} else {
			sb.append("&q=");
		}

		// 删除第一个&
		if (sb.length() > 1) {
			sb.delete(0, 1);
		}
		return sb.toString();

	}

	public String getProvinces() {
		return provinces;
	}

	public Set<String> getWrapFq() {
		Set<String> set = this.getFq();
		if (set == null) {
			set = new HashSet<String>();
		}
		if (isNotEmpty(this.getCity())) {
			if ("所有地区".equals(this.getCity())) {
			} else if ("江浙沪".equals(this.getCity())) {
				set.add("provinces:江苏 OR provinces:浙江 OR provinces:上海 OR city:上海");
			} else if ("珠三角".equals(this.getCity())) {
				set.add("provinces:广东 OR provinces:福建");
			} else if ("港澳台".equals(this.getCity())) {
				set.add("provinces:香港 OR provinces:澳门 OR provinces:台湾");
			} else if ("海外".equals(this.getCity())) {
				set.add("provinces:海外");
			} else {
				set.add("city:" + ClientUtils.escapeQueryChars(this.getCity()) + "OR provinces:"
						+ ClientUtils.escapeQueryChars(this.getCity()));
			}
		} else if (isNotEmpty(provinces)) {
			set.add("provinces:" + ClientUtils.escapeQueryChars(provinces));
		}
		return set;
	}

	public String getWrapQ() {
		StringBuffer cq = new StringBuffer();
		if (this.getQ() == null || "".equals(this.getQ())) {
			cq = new StringBuffer("");
		} else {
			keyword = ClientUtils.escapeQueryChars(this.getQ());
			if (conditions != null && conditions.length >= 0) {
				for (int i = 0; i < conditions.length; i++) {
					if (i > 0) {
						cq.append(" OR ");
					}
					cq.append(conditions[i] + ":" + ClientUtils.escapeQueryChars(this.getQ()));
				}
			} else {
				return keyword;
			}
		}
		return cq.toString();
	}

	public void setConditions(String[] conditions) {
		this.conditions = conditions;
	}

	public void setExtParam(Map<String, String> extParam) {
		this.extParam = extParam;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
}
