package com.yuwang.pinju.core.httpclient;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import com.mysql.jdbc.StringUtils;
import com.yuwang.pinju.common.ArrayUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.search.SearchConstent;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.logistics.manager.LogisticsCityIpManager;
import com.yuwang.pinju.core.util.HtmlRegexpUtil;
import com.yuwang.pinju.domain.search.SearchBaseDO;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.SearchShopDO;
import com.yuwang.pinju.domain.search.index.ItemIndexDO;
import com.yuwang.pinju.domain.search.index.ShopIndexD0;
import com.yuwang.pinju.domain.search.result.ItemResult;
import com.yuwang.pinju.domain.search.result.SearchResult;
import com.yuwang.pinju.domain.search.result.ShopResult;

@SuppressWarnings("unchecked")
public class SolrService {

	static Logger logger = Logger.getLogger(SolrService.class.getName());

	private CommonsHttpSolrServer itemSearchServer = null;
	private CommonsHttpSolrServer shopSearchServer = null;
	private CommonsHttpSolrServer itemSearchBoxServer = null;

	private LogisticsCityIpManager logisticsCityIpManager;

	/**
	 * <p>
	 * 商品搜索/店铺内商品搜索
	 * @param itemDo 查询参数对象
	 * @param obj 可变参数,其他组成员调用搜索接口不应该(特殊需求用 eg:CEO需求)传值给该参数. 
	 * <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;since at 20111-12-14
	 * @return SearchResult
	 * @throws Exception
	 */
	public SearchResult searchItem(SearchBaseDO itemDo,Object ...obj) throws Exception {
		@SuppressWarnings("rawtypes")
		List docs = null;
		SearchResult result = null;
		
		try {

			initSolrServer();
			// 记录用户行为
			saveUserBehavior(itemDo);

			SolrQuery query = this.buildQuery(itemDo);
			// 搜索推广搜索
			if(obj!=null &&obj.length>0 && SearchConstent.SEARCH_TYPE_EXTENG.equals(obj[0])){
				query.addFilterQuery("salesNum:[1 TO *]");
			}
			// 增量更新的时候,会有些status不为0/1的的记录
			query.addFilterQuery("(status:0 OR status:1)");
			QueryResponse rsp = itemSearchServer.query(query);

			String areaId = null;

			if (StringUtil.isNotBlank(itemDo.getIp())) {
				try {
					//this mehtod only declare maybe throw ManagerException ,but it may throw other Exception :add 2011-12-14
					areaId = logisticsCityIpManager.getRegionCode(itemDo.getIp());
				} catch (ManagerException e) {
					logger.error("搜索通过IP:" + itemDo.getIp() + "获取城市错误!", e);
				}
				//:add 2011-12-14
				catch(Exception e){
					logger.error("搜索通过IP:" + itemDo.getIp() + "获取城市错误!", e);
				}
			}
			docs = rsp.getBeans(ItemResult.class);
			// 判断有命中数,由于命中结果数不足(当前页码偏大,教上一查询条件下了更多条件会出现查无结果(有命中数))导致页面显示查无结果情况
			// 再查一把,从第一项开始显示
			// add at 2011-11-23
			if ((rsp != null && rsp.getResults() != null && rsp.getResults().getNumFound() > 0)
					&& (docs != null && docs.size() == 0)) {
				query.setStart(0);
				rsp = itemSearchServer.query(query);
				docs = rsp.getBeans(ItemResult.class);
				itemDo.setStart("1");
			}

			List<ItemResult> _docs = (List<ItemResult>) docs;
			// Highlighting
			Map<String, Map<String, List<String>>> map = rsp.getHighlighting();
			Map<String, List<String>> _map = null;
			List<String> _list = null;
			String f[] = null;

			Long startTime = System.currentTimeMillis();

			String strong = "";
			for (ItemResult item : _docs) {
				item.setTitle(HtmlRegexpUtil.replaceTag(item.getTitle()));
				if ((_map = map.get(String.valueOf(item.getId()))) != null) {
					if ((_list = _map.get("title")) != null && _list.size() > 0) {
						strong = HtmlRegexpUtil.replaceTag(_list.get(0));
						strong = strong.replaceAll("&lt;strong&gt;", "<strong>");
						strong = strong.replaceAll("&lt;/strong&gt;", "</strong>");
						item.setHlTitle(strong);
					}
				}
				// 运费
				if (item.getMailCosts() > 0l || item.getDeliveryCosts() > 0l || item.getEmsCosts() > 0l) {
					if (item.getDeliveryCosts() > 0l
							&& (item.getFreight() == 0 || item.getDeliveryCosts() < item.getFreight())) {
						item.setFreight(item.getDeliveryCosts());
					}
					if (item.getEmsCosts() > 0l && (item.getFreight() == 0 || item.getEmsCosts() < item.getFreight())) {
						item.setFreight(item.getEmsCosts());
					}
					if (item.getMailCosts() > 0l && (item.getFreight() == 0 || item.getMailCosts() < item.getFreight())) {
						item.setFreight(item.getMailCosts());
					}
				} else {
					if (areaId != null) {
						if (!StringUtils.isNullOrEmpty(item.getAreaCarriage())) {
							// 地区运费
							if (item.getAreaCarriage().indexOf("D:" + areaId) != -1) {
								f = item.getAreaCarriage().split(";");
								String t = "0";
								for (int i = 0; i < f.length; i++) {
									if (f[i].indexOf("D:" + areaId) != -1) {
										try {
											t = f[i].split(",")[2].split(":")[1];
										} catch (Exception e) {
											t = "0";
										}
										if (Long.parseLong(t) > 0
												&& (item.getFreight() == 0 || Long.parseLong(t) < item.getFreight())) {
											item.setFreight(Long.parseLong(t));
										}
									}
								}
							}
						}
					}
					if (item.getFreight() <= 0 && !StringUtils.isNullOrEmpty(item.getCarriage())) {
						// 默认运费
						// A:2,B:800,C:200;A:1,B:800,C:200;
						f = item.getCarriage().split(";");
						String t = "0";
						for (int i = 0; i < f.length; i++) {
							try {
								t = f[i].split(",")[1].split(":")[1];
							} catch (Exception e) {
								t = "0";
							}
							if (Long.parseLong(t) > 0
									&& (item.getFreight() == 0 || Long.parseLong(t) < item.getFreight())) {
								item.setFreight(Long.parseLong(t));
							}
						}
					}
				}
				f = null;
			}

			logger.debug("searchItem 耗时:" + (System.currentTimeMillis() - startTime));

			result = this.buildResult(rsp, itemDo, docs);
		} catch (Exception e) {
			logger.error("搜索错误SolrService#searchItem", e);
			throw e;
		}
		return result;
	}

	public LogisticsCityIpManager getLogisticsCityIpManager() {
		return logisticsCityIpManager;
	}

	public void setLogisticsCityIpManager(LogisticsCityIpManager logisticsCityIpManager) {
		this.logisticsCityIpManager = logisticsCityIpManager;
	}

	/**
	 * <p>
	 * 店铺搜索
	 * 
	 * @param shopDo
	 * @return SearchResult
	 * @throws Exception
	 */
	public SearchResult searchShop(SearchShopDO shopDo) throws Exception {
		@SuppressWarnings("rawtypes")
		List docs = null;
		SearchResult result = null;
		try {

			initSolrServer();

			if (shopDo.getConditions() != null && shopDo.getConditions().length > 0) {
				if (ArrayUtil.equals(shopDo.getConditions(), new String[] { "name", "nickName", "title" })) {
					shopDo.setType("1");
				} else if (ArrayUtil.equals(shopDo.getConditions(), new String[] { "name", "nickName" })) {
					shopDo.setType("2");
				} else if (ArrayUtil.equals(shopDo.getConditions(), new String[] { "title" })) {
					shopDo.setType("3");
				}else{
					shopDo.setConditions(new String[] { "name", "nickName", "title" });
					shopDo.setType("1");
				}
			} else{
				shopDo.setConditions(new String[] { "name", "nickName", "title" });
				shopDo.setType("1");
			}

			SolrQuery query = this.buildQuery(shopDo);

			query.addFilterQuery("itemCount:[10 TO *]");
			query.addFilterQuery("approveStatus:1");
			QueryResponse rsp = shopSearchServer.query(query);

			docs = rsp.getBeans(ShopResult.class);
			List<ShopResult> _docs = (List<ShopResult>) docs;
			// 判断有命中数,由于命中结果数不足(当前页码偏大,教上一查询条件下了更多条件会出现查无结果(有命中数))导致页面显示查无结果情况
			// 再查一把,从第一项开始显示
			// add at 2011-11-23
			if ((rsp != null && rsp.getResults() != null && rsp.getResults().getNumFound() > 0)
					&& (docs != null && docs.size() == 0)) {
				query.setStart(0);
				rsp = shopSearchServer.query(query);
				docs = rsp.getBeans(ShopResult.class);
				shopDo.setStart("1");
			}
			// Highlighting
			Map<String, Map<String, List<String>>> map = rsp.getHighlighting();
			Map<String, List<String>> _map = null;
			List<String> _list = null;
			for (ShopResult item : _docs) {
				item.setTitle(HtmlRegexpUtil.replaceTag(item.getTitle()));
				item.setDescription(HtmlRegexpUtil.replaceTag(item.getDescription()));
				item.setNickName(HtmlRegexpUtil.replaceTag(item.getNickName()));
				if ((_map = map.get(String.valueOf(item.getId()))) != null) {
					if (shopDo.getHlFl() != null && shopDo.getHlFl().length > 0) {
						Method method = null;
						// fl need Highlighting field
						String strong = "";
						for (String fl : shopDo.getHlFl()) {
							if (fl != null) {
								try {
									fl = HtmlRegexpUtil.replaceTag(fl);
									method = item.getClass().getMethod(
											"set" + fl.substring(0, 1).toUpperCase() + fl.substring(1), String.class);
									if ((_list = _map.get(fl)) != null) {
										strong = HtmlRegexpUtil.replaceTag(_list.get(0));
										strong = strong.replaceAll("&lt;strong&gt;", "<strong>");
										strong = strong.replaceAll("&lt;/strong&gt;", "</strong>");
										method.invoke(item, strong);
									}
								} catch (NoSuchMethodException e) {
									logger.warn("class " + item.getClass().getName() + " not found method " + "set"
											+ fl.substring(0, 1).toUpperCase() + fl.substring(1));
								}
							}
						}
					} else {
						if ((_list = _map.get("title")) != null && _list.size() > 0) {
							item.setTitle(_list.get(0));
						}
					}
				}
			}
			result = this.buildResult(rsp, shopDo, docs);
		} catch (SolrServerException e) {
			logger.error("搜索错误SolrService#searchShop", e);
			throw e;
		}
		return result;
	}

	private SearchResult buildResult(QueryResponse rsp, SearchBaseDO searchDo, List<Object> docs) {
		SearchResult result = new SearchResult();
		result.setResultList(docs);
		// field 统计信息 catetory hits
		if (rsp.getFacetFields() != null && rsp.getFacetFields().size() != 0) {
			result.setFacetField(rsp.getFacetFields().get(0).getValues());
		}
		result.setFacetQuery(rsp.getFacetQuery());
		// 当前页码
		// solr页码从0开始，分页组件页码从1开始
		// 每页显示项数
		if (StringUtil.isNumeric(searchDo.getCount())) {
			result.setItemsPerPage(Integer.parseInt(searchDo.getCount()));
		} else {
			result.setItemsPerPage(SearchBaseDO.DEFAULT_COUNT);
		}

		// 总共项数
		result.setItems((int) rsp.getResults().getNumFound());
		if (StringUtil.isNumeric(searchDo.getStart())) {
			result.setPage(Integer.parseInt(searchDo.getStart()));
		} else {
			result.setPage(1);
		}

		logger.debug("searchDo.getStart()=" + searchDo.getStart() + " searchDo.getCount()=" + searchDo.getCount()
				+ " result.getPage()=" + result.getPage() + "  result.getPages()=" + result.getPages() + "  ");
		logger.debug(rsp.getResponse());
		// 推荐查询关键字 Array
		// logger.info("文档个数：" + docs.size());
		// logger.info("查询时间：" + rsp.getQTime());
		return result;
	}

	private SolrQuery buildQuery(SearchBaseDO searchDo) {
		SolrQuery query = new SolrQuery();

		// 查询词
		if (searchDo.getQ() == null || searchDo.getQ().equalsIgnoreCase("")) {
			query.setQuery("*:*");
		} else {
			query.setQuery(searchDo.getWrapQ());
		}

		// 每页多少项
		int itemCount = SearchBaseDO.DEFAULT_COUNT;
		if (StringUtil.isNumeric(searchDo.getCount()) && Integer.parseInt(searchDo.getCount()) > 0) {
			query.setRows(Integer.parseInt(searchDo.getCount()));
			itemCount = Integer.parseInt(searchDo.getCount());
		} else {
			query.setRows(SearchBaseDO.DEFAULT_COUNT);
		}

		// 开始页 solr页码从0开始，分页组件页码从1开始
		int startIndex = 0;
		if (StringUtil.isNumeric(searchDo.getStart()) && Integer.parseInt(searchDo.getStart()) > 0) {
			startIndex = Integer.parseInt(searchDo.getStart());
			startIndex--;
		}
		if (startIndex > -1) {
			query.setStart(startIndex * itemCount);
		} else {
			searchDo.setStart("1");
			query.setStart(0);
		}

		// 排序规则
		/*
		 * if (null != searchDo.getSort() && !"".equals(searchDo.getSort()) &&
		 * SearchItemDO.SORT_NAME_LIST.contains(searchDo.getSort().trim())) { if
		 * ("startTime".equalsIgnoreCase(searchDo.getSort().split("-")[0])) { query.addSortField("score",
		 * SolrQuery.ORDER.desc); } query.addSortField(searchDo.getSort().split("-")[0],
		 * searchDo.getSort().split("-")[1] .equalsIgnoreCase("desc") ? SolrQuery.ORDER.desc : SolrQuery.ORDER.asc); }
		 * else { query.addSortField("score", SolrQuery.ORDER.desc); if (searchDo instanceof SearchItemDO) {
		 * query.addSortField("sortRandom"+System.currentTimeMillis(), SolrQuery.ORDER.desc);
		 * query.addSortField("startTime", SolrQuery.ORDER.asc); } }
		 */
		String[] _sort = null;
		// sort 不为空/null //暂时只有商品有排序需求2011-11-22
		if (null != searchDo.getSort() && !"".equals(searchDo.getSort())
				&& SearchItemDO.SORT_NAME_LIST.contains(searchDo.getSort().trim())) {
			// 始终含两个元素 由第三个判断条件确保
			_sort = searchDo.getSort().split("-");
			// 页面传来默认值
			if ("startTime".equals(_sort[0])) {
				query.addSortField("score", SolrQuery.ORDER.desc);
				// 搜索商品随机数
				if (searchDo instanceof SearchItemDO) {
					query.addSortField("salesNum", SolrQuery.ORDER.desc);
					query.addSortField("startTime", SolrQuery.ORDER.desc);
					query.addSortField("sortRandom" + System.currentTimeMillis(), SolrQuery.ORDER.desc);
				} else {
					// TODO 店铺排序
					// query.addSortField(_sort[0], _sort[1].equalsIgnoreCase("desc") ? SolrQuery.ORDER.desc :
					// SolrQuery.ORDER.asc);
				}

			} else if ("salesNum".equals(_sort[0])) {
				if (searchDo instanceof SearchItemDO) {
					query.addSortField(_sort[0], _sort[1].equalsIgnoreCase("desc") ? SolrQuery.ORDER.desc
							: SolrQuery.ORDER.asc);
					query.addSortField("score", SolrQuery.ORDER.desc);
					query.addSortField("sortRandom" + System.currentTimeMillis(), SolrQuery.ORDER.desc);
				}
			} else {
				query.addSortField(_sort[0], _sort[1].equalsIgnoreCase("desc") ? SolrQuery.ORDER.desc
						: SolrQuery.ORDER.asc);
			}

		} else if (null != searchDo.getSort() && !"".equals(searchDo.getSort())
				&& searchDo.getSort().startsWith("sortRandom")) {
			_sort = searchDo.getSort().split("-");
			query
					.addSortField(_sort[0], _sort[1].equalsIgnoreCase("desc") ? SolrQuery.ORDER.desc
							: SolrQuery.ORDER.asc);
		}
		// 默认排序
		else {
			query.addSortField("score", SolrQuery.ORDER.desc);
			// 搜索商品随机数
			if (searchDo instanceof SearchItemDO) {
				query.addSortField("salesNum", SolrQuery.ORDER.desc);
				query.addSortField("startTime", SolrQuery.ORDER.desc);
				query.addSortField("sortRandom" + System.currentTimeMillis(), SolrQuery.ORDER.desc);
			} else {
				// TODO 店铺排序
			}
		}

		// 过滤条件
		Set<String> set = null;
		if ((set = searchDo.getWrapFq()) != null) {
			for (String fq : set) {
				query.addFilterQuery(fq);
			}
		}

		// query.setIncludeScore(true);
		// Faceted Search
		if (searchDo.getFacetField() != null) {
			query.setFacet(true);
			query.addFacetField(searchDo.getFacetField());
			// 统计排序count/index
			if (searchDo.getFacetSort() != null && !"".equals(searchDo.getFacetSort())) {
				query.setFacetSort(searchDo.getFacetSort());
			}
			if (searchDo.getFacetLimit() != null) {
				if (StringUtil.isNumeric(searchDo.getFacetLimit()) && Integer.parseInt(searchDo.getFacetLimit()) > 10) {
					query.setFacetLimit(Integer.parseInt(searchDo.getFacetLimit()));
				} else {
					query.setFacetLimit(10);
				}
			}
		}

		if (null != searchDo.getFacetQuery() && searchDo.getFacetQuery().length > 0) {
			for (int i = 0; i < searchDo.getFacetQuery().length; i++) {
				query.addFacetQuery(searchDo.getFacetQuery()[i]);
			}
		}

		if (searchDo.getFacetSort() != null && !"".equals(searchDo.getFacetSort())) {
			query.setFacetSort(searchDo.getFacetSort());
		}

		// Highlighting
		if (searchDo.getHl() == true) {
			query.setHighlight(searchDo.getHl());
			query.setHighlightSimplePre(searchDo.HIGH_LIGHT_SIMPLE_PRE);
			query.setHighlightSimplePost(searchDo.HIGH_LIGHT_SIMPLE_POST);
			if (searchDo.getHlFl() != null && searchDo.getHlFl().length > 0) {
				for (int i = 0; i < searchDo.getHlFl().length; i++) {
					query.addHighlightField(searchDo.getHlFl()[i]);
				}
			} else {
				query.addHighlightField("title");
			}
		}

		try {
			logger.debug(URLDecoder.decode(query.toString(), "utf-8"));
		} catch (UnsupportedEncodingException e) {

		}
		return query;
	}

	public JSONObject itemSearchBox(String q) throws Exception {
		this.initSolrServer();
		JSONObject obj = new JSONObject();
		SolrQuery query = new SolrQuery();
		if ("".equals(q) || null == q) {
			query.setQuery("*:*");
		} else {
			query.setQuery("keyWord:" + ClientUtils.escapeQueryChars(q) + "* OR spell:"
					+ ClientUtils.escapeQueryChars(q) + "*");
		}
		query.setRows(10);
		query.setSortField("count", ORDER.desc);
		QueryResponse qr = this.itemSearchBoxServer.query(query);
		SolrDocument document = null;
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		if (qr != null && qr.getResults() != null) {
			for (Iterator<SolrDocument> it = qr.getResults().iterator(); it.hasNext();) {
				document = it.next();
				Object a[] = { document.getFieldValue("keyWord"), document.getFieldValue("itemCount") };
				if (document.getFieldValue("itemCount") != null && (Long) document.getFieldValue("itemCount") != 0l) {
					list.add(a);
				}
			}
		}
		obj.put("result", list);
		logger.debug(obj);
		return obj;
	}

	public JSONObject shopSearchBox(String q) throws Exception {
		this.initSolrServer();
		JSONObject obj = new JSONObject();
		SolrQuery query = new SolrQuery();
		if ("".equals(q) || null == q) {
			query.setQuery("*:*");
		} else {
			query.setQuery("baseName:" + ClientUtils.escapeQueryChars(q) + "*");
		}
		query.setSortField("itemCount", ORDER.desc);
		query.setRows(10);
		query.setFacet(true);
		query.setFacetLimit(10);
		query.addFacetField(new String[] { "baseName" });
		QueryResponse qr = this.shopSearchServer.query(query);
		List<FacetField.Count> result = null;
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		if (qr != null && qr.getFacetFields() != null && qr.getFacetFields().size() != 0) {
			result = qr.getFacetFields().get(0).getValues();
			if (result != null) {
				for (FacetField.Count ff : result) {
					Object a[] = { ff.getName(), ff.getCount() };
					// 第二个判断条件add at 2011-12-06 15:52
					// baseName值有空格,而query.setFacetLimit(10);当匹配的baseName统计不足又会用其他baseName统计填充?
					// eg:q=h ^baseName=uni ^getCount=1 ||baseName=uni hera旗舰店
					if (ff.getCount() != 0 && (ff.getName() != null && ff.getName().contains(q))) {
						list.add(a);
					}

					if (logger.isDebugEnabled()) {
						if (ff.getName() != null && !ff.getName().contains(q)) {
							logger
									.debug("q=" + q + "     ^baseName=" + ff.getName() + "    ^getCount="
											+ ff.getCount());
						}
					}
				}
			}
		}

		obj.put("result", list);
		logger.debug(obj);
		return obj;
	}

	public ItemIndexDO getUpdateItem(Long id) throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + id);
		this.initSolrServer();
		QueryResponse rsp = itemSearchServer.query(query);
		List<ItemIndexDO> list = rsp.getBeans(ItemIndexDO.class);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public ShopIndexD0 getUpdateShop(Long id) throws Exception {

		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + id);
		this.initSolrServer();
		QueryResponse rsp = this.shopSearchServer.query(query);
		List<ShopIndexD0> list = rsp.getBeans(ShopIndexD0.class);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 通过查询参数记录user行为
	 * 
	 * @param searchDo
	 */
	private void saveUserBehavior(SearchBaseDO searchDo) {
		// TODO
	}
	
	private void initSolrServer() throws Exception {

		try {

			if (itemSearchServer == null) {
				logger.info("item solr init ...");
				logger.info("solr serverUrl:" + PinjuConstant.SEARCH_SERVERURL);
				logger.info("solr connectTimeout:" + PinjuConstant.SEARCH_CONNECTION_TIMEOUT);
				logger.info("solr soTimeout:" + PinjuConstant.SEARCH_SOTIMEOUT);
				logger.info("solr maxConnectionsPerHost:" + PinjuConstant.SEARCH_MAX_CONNECTIONS_PER_HOST);
				logger.info("solr maxTotalConnections:" + PinjuConstant.SEARCH_MAX_TOTAL_CONNECTIONS);
				itemSearchServer = new CommonsHttpSolrServer(PinjuConstant.SEARCH_SERVERURL
						+ SearchConstent.ITEM_SEARCH_PATH);
				itemSearchServer.setSoTimeout(PinjuConstant.SEARCH_SOTIMEOUT);
				itemSearchServer.setConnectionTimeout(PinjuConstant.SEARCH_CONNECTION_TIMEOUT);
				itemSearchServer.setFollowRedirects(false);
				itemSearchServer.setDefaultMaxConnectionsPerHost(PinjuConstant.SEARCH_MAX_CONNECTIONS_PER_HOST);
				itemSearchServer.setMaxRetries(0);
				itemSearchServer.setAllowCompression(true);
				itemSearchServer.setMaxTotalConnections(PinjuConstant.SEARCH_MAX_TOTAL_CONNECTIONS);
				logger.info("end solr init ...");
			}

			if (shopSearchServer == null) {
				logger.info("item solr init ...");
				logger.info("solr serverUrl:" + PinjuConstant.SEARCH_SERVERURL);
				logger.info("solr connectTimeout:" + PinjuConstant.SEARCH_CONNECTION_TIMEOUT);
				logger.info("solr soTimeout:" + PinjuConstant.SEARCH_SOTIMEOUT);
				logger.info("solr maxConnectionsPerHost:" + PinjuConstant.SEARCH_MAX_CONNECTIONS_PER_HOST);
				logger.info("solr maxTotalConnections:" + PinjuConstant.SEARCH_MAX_TOTAL_CONNECTIONS);
				shopSearchServer = new CommonsHttpSolrServer(PinjuConstant.SEARCH_SERVERURL
						+ SearchConstent.SHOP_SEARCH_PATH);
				shopSearchServer.setSoTimeout(PinjuConstant.SEARCH_SOTIMEOUT);
				shopSearchServer.setConnectionTimeout(PinjuConstant.SEARCH_CONNECTION_TIMEOUT);
				shopSearchServer.setFollowRedirects(false);
				shopSearchServer.setDefaultMaxConnectionsPerHost(PinjuConstant.SEARCH_MAX_CONNECTIONS_PER_HOST);
				shopSearchServer.setMaxRetries(0);
				shopSearchServer.setAllowCompression(true);
				shopSearchServer.setMaxTotalConnections(PinjuConstant.SEARCH_MAX_TOTAL_CONNECTIONS);
				logger.info("end solr init ...");
			}

			if (itemSearchBoxServer == null) {
				logger.info("item searchBox solr init ...");
				logger.info("solr serverUrl:" + PinjuConstant.SEARCH_SERVERURL);
				logger.info("solr connectTimeout:" + PinjuConstant.SEARCH_CONNECTION_TIMEOUT);
				logger.info("solr soTimeout:" + PinjuConstant.SEARCH_SOTIMEOUT);
				logger.info("solr maxConnectionsPerHost:" + PinjuConstant.SEARCH_MAX_CONNECTIONS_PER_HOST);
				logger.info("solr maxTotalConnections:" + PinjuConstant.SEARCH_MAX_TOTAL_CONNECTIONS);
				itemSearchBoxServer = new CommonsHttpSolrServer(PinjuConstant.SEARCH_SERVERURL
						+ SearchConstent.SEARCH_BOX_PATH);
				itemSearchBoxServer.setSoTimeout(PinjuConstant.SEARCH_SOTIMEOUT);
				itemSearchBoxServer.setConnectionTimeout(PinjuConstant.SEARCH_CONNECTION_TIMEOUT);
				itemSearchBoxServer.setFollowRedirects(false);
				itemSearchBoxServer.setDefaultMaxConnectionsPerHost(PinjuConstant.SEARCH_MAX_CONNECTIONS_PER_HOST);
				itemSearchBoxServer.setMaxRetries(0);
				itemSearchBoxServer.setAllowCompression(true);
				itemSearchBoxServer.setMaxTotalConnections(PinjuConstant.SEARCH_MAX_TOTAL_CONNECTIONS);
				logger.info("end item searchBox solr init ...");
			}
		} catch (Exception e) {
			logger.error("配置solr错误！", e);
			throw e;
		}
	}

}
