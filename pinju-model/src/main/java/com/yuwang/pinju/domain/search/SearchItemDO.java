package com.yuwang.pinju.domain.search;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.solr.client.solrj.util.ClientUtils;

/**
 * 商品搜索
 * 
 * @author liming
 * @since 2011-07-19
 * 
 */
public class SearchItemDO extends SearchBaseDO {

	private static final long serialVersionUID = 6779076956800346647L;

	public static List<String> SORT_NAME_LIST;
	static {
		SORT_NAME_LIST = new ArrayList<String>();
		SORT_NAME_LIST.add("startTime-desc");
		SORT_NAME_LIST.add("startTime-asc");
		SORT_NAME_LIST.add("salesNum-desc");
		SORT_NAME_LIST.add("price-desc");
		SORT_NAME_LIST.add("price-asc");
	}

	/**
	 * 搜索类型
	 */
	private int searchType = 1;

	/**
	 * 商品类目ID
	 */
	private String cp;

	/**
	 * 价格上限 单位:分
	 */
	private String endPrice;

	/**
	 * 扩展的查询参数
	 */
	private Map<String, String> extParam;

	/**
	 * 排除
	 */
	private String filterExclude;

	/**
	 * 搜索来源; 字符串, sug | rs | se
	 */
	private String fr;

	/**
	 * 商品属性，用以过滤查询结果 ,字符串，pv=“pn1:pv1 AND pn1 AND pv2;pn2 OR pv3;pn2 OR pv4”
	 */
	private String pv;

	/**
	 * @deprecated SDO<br>
	 *             店铺内自定义分类
	 */
	private String scp;

	/**
	 * 卖家ID，用以店铺内搜索
	 */
	private String sellerId;

	/**
	 * 价格下限
	 */
	private String startPrice;
	/**
	 * SDO<br>
	 * 显示风格: 字符串，grid | list 默认是grid
	 */
	private String style;

	/**
	 * 表示用户选择搜索提示内容
	 */
	private String suc;

	/**
	 * 用户选择第几条搜索提示
	 */
	private String sun;

	/**
	 * @deprecated SDO<br>
	 *             暂定商品二手值过滤
	 */
	private String tab;

	/**
	 * 类目名
	 */
	private String catetoryName;
	/**
	 * 省份
	 */
	private String provinces;
	/**
	 * 店铺分类
	 */
	private String storeCategories;

	/**
	 * 特性
	 */
	private String features;

	public String getCatetoryName() {
		return catetoryName;
	}

	public String getCp() {
		return cp;
	}

	public String getEndPrice() {
		return endPrice;
	}

	public Map<String, String> getExtParam() {
		return extParam;
	}

	public String getFeatures() {
		return features;
	}

	public String getFilterExclude() {
		return filterExclude;
	}

	public String getFr() {
		return fr;
	}
	
	
	/**
	 * @deprecated SDO<br>
	 */
	public String getParametersValue() throws UnsupportedEncodingException {
		StringBuffer _sb = new StringBuffer();
		if (isNotEmpty(this.getCity())) {
			_sb.append("&city="
					+ URLEncoder.encode(this.getCity(), (null != this.getEncode() ? this.getEncode() : "utf-8")));
		}
		if (isNotEmpty(this.getCount())) {
			_sb.append("&count=" + this.getCount());
		}
		if (isNotEmpty(this.getCp())) {
			_sb.append("&cp=" + this.getCp());
		}
		if (isNotEmpty(this.getFr())) {
			_sb.append("&fr=" + this.getFr());
		}
		if (isNotEmpty(this.getPv())) {
			_sb.append("&pv="
					+ URLEncoder.encode(this.getPv(), (null != this.getEncode() ? this.getEncode() : "utf-8")));
		}
		if (isNotEmpty(this.getScp())) {
			_sb.append("&scp=" + this.getScp());
		}
		if (isNotEmpty(this.getSellerId())) {
			_sb.append("&seller=" + this.getSellerId());
		}
		if (isNotEmpty(this.getSort())) {
			_sb.append("&sort="
					+ URLEncoder.encode(this.getSort(), (null != this.getEncode() ? this.getEncode() : "utf-8")));
		}
		if (isNotEmpty(this.getStart())) {
			_sb.append("&start=" + this.getStart());
		}
		if (isNotEmpty(this.getStyle())) {
			_sb.append("&style=" + this.getStyle());
		}
		if (isNotEmpty(this.getSuc())) {
			_sb.append("&suc="
					+ URLEncoder.encode(this.getSuc(), (null != this.getEncode() ? this.getEncode() : "utf-8")));
		}
		if (isNotEmpty(this.getSun())) {
			_sb.append("&sun=" + this.getSun());
		}
		if (isNotEmpty(this.getTab())) {
			_sb.append("&tab=" + this.getTab());
		}

		if (isNotEmpty(this.getQ())) {
			_sb.append("&q=" + URLEncoder.encode(this.getQ(), (null != this.getEncode() ? this.getEncode() : "utf-8")));
		} else {
			_sb.append("&q=");
		}

		if (this.getExtParam() != null) {
			Set<String> _keySet = this.getExtParam().keySet();
			for (String _key : _keySet) {
				_sb.append("&"
						+ _key
						+ "="
						+ URLEncoder.encode(this.getExtParam().get(_key), (null != this.getEncode() ? this.getEncode()
								: "utf-8")));
			}
		}
		// 删除第一个&
		if (_sb.length() > 1) {
			_sb.delete(0, 1);
		}
		return _sb.toString();
	}

	public String getProvinces() {
		return provinces;
	}

	/**
	 * @return
	 */
	public String getPv() {
		return pv;
	}

	public String getScp() {
		return scp;
	}

	public int getSearchType() {
		return searchType;
	}

	public String getSellerId() {
		return sellerId;
	}

	public String getStartPrice() {
		return startPrice;
	}

	public String getStoreCategories() {
		return storeCategories;
	}

	/**
	 * @deprecated SDO<br>
	 * @return
	 */
	public String getStyle() {
		return style;
	}

	// ############ get set method #########

	public String getSuc() {
		return suc;
	}

	public String getSun() {
		return sun;
	}

	public String getTab() {
		return tab;
	}

	/*
	 * public String toString() { try { return this.getParametersValue(); } catch (UnsupportedEncodingException e) {
	 * return super.toString(); } }
	 */
	public Set<String> getWrapFq() {
		Set<String> set = this.getFq();

		if (set == null) {
			set = new HashSet<String>();
		}

		if (isNotEmpty(this.getCity())) {

			if ("所有地区".equals(this.getCity())) {

			} else if ("江浙沪".equals(this.getCity())) {
				set.add("provinces:江苏 OR provinces:浙江 OR city:上海");
			} else if ("珠三角".equals(this.getCity())) {
				set.add("provinces:广东 OR provinces:福建");
			} else if ("港澳台".equals(this.getCity())) {
				set.add("provinces:香港 OR provinces:澳门 OR provinces:台湾");
			} else if ("海外".equals(this.getCity())) {
				set.add("provinces:海外");
			} else {
				set.add("city:" + ClientUtils.escapeQueryChars(this.getCity()) + " OR provinces:"
						+ ClientUtils.escapeQueryChars(this.getCity()));
			}
		}
		// 基本单位是分
		if (isNotEmpty(this.getStartPrice()) || isNotEmpty(this.getEndPrice())) {
			String _str = null;
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			if (this.getStartPrice() != null && !pattern.matcher(this.getStartPrice()).matches()) {
				this.setStartPrice(null);
			}
			if (this.getEndPrice() != null && !pattern.matcher(this.getEndPrice()).matches()) {
				this.setEndPrice(null);
			}
			if (isNotEmpty(this.getStartPrice() )) {
				_str = "[" + (int) (Double.parseDouble(this.getStartPrice()) * 100) + " TO ";
			} else {
				_str = "[* TO ";
			}
			if (isNotEmpty(this.getEndPrice())) {
				_str += (int) (Double.parseDouble(getEndPrice()) * 100) + "]";
			} else {
				_str += "*]";
			}
			
			if (!"[* TO *]".equals(_str)) {
				set.add("price:" + _str);
			}
		}
		if (isNotEmpty(this.features)) {
			set.add("features:" + ClientUtils.escapeQueryChars(this.features));
		}
		// 类目ID
		if (isNotEmpty(cp)) {
			set.add("categoryLevel:" + cp);
		}
		// 类目名称
		if (isNotEmpty(catetoryName)) {
			set.add("catetoryName:" + ClientUtils.escapeQueryChars(catetoryName));
		}
		// 店铺内商品搜索
		if (this.isNotEmpty(sellerId)) {
			set.add("sellerId:" + sellerId);
		}
		if (this.isNotEmpty(storeCategories)) {
			set.add("storeCategories:" + storeCategories);
		}
		// 店铺内商品搜索 end
		if (this.isNotEmpty(this.getPv())) {
			set.add(ClientUtils.escapeQueryChars(this.getPv()));
		}
		// 扩展参数
		if (this.getExtParam() != null) {
			Set<String> _keySet = this.getExtParam().keySet();
			for (String _key : _keySet) {
				set.add(_key + ":" + ClientUtils.escapeQueryChars(this.getExtParam().get(_key)));
			}
		}
		return set;
	}

	public String getWrapQ() {
		if (!"".equals(filterExclude) && filterExclude != null) {
			return ClientUtils.escapeQueryChars(this.getQ()) + " -"
					+ ClientUtils.escapeQueryChars(this.getFilterExclude());
		} else {
			return ClientUtils.escapeQueryChars(this.getQ());
		}
	}

	public void setCatetoryName(String catetoryName) {
		this.catetoryName = catetoryName;
	}

	/**
	 * @param cp
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	public void setEndPrice(String endPrice) {
		this.endPrice = endPrice;
	}

	public void setExtParam(Map<String, String> extParam) {
		this.extParam = extParam;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public void setFilterExclude(String filterExclude) {
		this.filterExclude = filterExclude;
	}

	public void setFr(String fr) {
		this.fr = fr;
	}

	public void setProvinces(String provinces) {
		this.provinces = provinces;
	}

	/**
	 *@param pv
	 *            as pn1:pv1 AND pn1 AND pv2;pn2 OR pv3;pn2 OR pv4
	 */
	public void setPv(String pv) {
		this.pv = pv;
	}

	public void setScp(String scp) {
		this.scp = scp;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}

	public void setStoreCategories(String storeCategories) {
		this.storeCategories = storeCategories;
	}

	/**
	 * @deprecated SDO<br>
	 * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	public void setSuc(String suc) {
		this.suc = suc;
	}

	public void setSun(String sun) {
		this.sun = sun;
	}

	/**
	 * @deprecated SDO<br>
	 * @param tab
	 */
	public void setTab(String tab) {
		this.tab = tab;
	}

	public  Boolean isNotEmpty(String obj) {
		if (null == obj || (null!=obj && "".equals(obj.trim()))) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String[] args) {

/*		Integer a = 11;
		Integer b = 3;
		Integer c = 123;

		if (c == null || a <= c) {
			if (b == null || a <= b) {
				System.out.println(a);
			} else {
				System.out.println(b);
			}
		} else {
			System.out.println(c);
		}
		SearchItemDO sdo = new SearchItemDO();
		System.out.println(sdo.isNotEmpty(""));
		System.out.println(sdo.isNotEmpty(null));
		System.out.println(sdo.isNotEmpty("13"));*/
	}
}
