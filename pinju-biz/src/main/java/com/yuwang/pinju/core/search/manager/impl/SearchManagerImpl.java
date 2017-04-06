package com.yuwang.pinju.core.search.manager.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.httpclient.SolrService;
import com.yuwang.pinju.core.item.dao.ItemDAO;
import com.yuwang.pinju.core.search.manager.SearchManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.search.SearchBaseDO;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.SearchShopDO;
import com.yuwang.pinju.domain.search.SearchShopItem;
import com.yuwang.pinju.domain.search.result.ItemResult;
import com.yuwang.pinju.domain.search.result.SearchResult;

/**
 * 搜索引擎
 * 
 * @project pinju-biz
 * @description
 * @author liuweiguo liuweiguo@zba.com
 * @date 2011-7-7上午10:33:13
 * @update 2011-7-7上午10:33:13
 * @version V1.0
 * 
 */
public class SearchManagerImpl extends BaseManager implements SearchManager {

	private ItemDAO itemDAO;
	private SolrService solrService;

	private SearchResult getItemsByShop(SearchShopItem searchShopItem) throws DaoException {

		SearchResult result = new SearchResult();
		List<Object> items = new ArrayList<Object>();

		if (searchShopItem.getSellerId() == null) {
			return null;
		}

		List<ItemDO> list = itemDAO.selectItemList(searchShopItem.getSellerId());

		List<ItemDO> l = new ArrayList<ItemDO>();
		for (ItemDO i : list) {
			if (!searchShopItem.getStatus().contains(String.valueOf(i.getStatus()))) {
				continue;
			}
			if (!EmptyUtil.isBlank(searchShopItem.getQ())
					&& i.getTitle().toLowerCase().indexOf(searchShopItem.getQ().trim().toLowerCase()) == -1) {
				continue;
			}
			l.add(i);
		}
		list = new ArrayList<ItemDO>(l);

		if (!EmptyUtil.isBlank(searchShopItem.getShopCategory())) {

			if ("0".equalsIgnoreCase(searchShopItem.getShopCategory())) {
				List<ItemDO> ls = new ArrayList<ItemDO>();
				for (ItemDO i : list) {
					if (EmptyUtil.isBlank(i.getStoreCategories())) {
						ls.add(i);
					}
				}
				list = new ArrayList<ItemDO>(ls);
			} else {

				// 过滤店铺分类
				String scs[] = StringUtil.split(searchShopItem.getShopCategory(), ",");
				if (scs != null && scs.length > 0) {
					List<ItemDO> ls = new ArrayList<ItemDO>();
					for (ItemDO i : list) {
						if (!EmptyUtil.isBlank(i.getStoreCategories())) {
							String cs[] = StringUtil.split(i.getStoreCategories(), ",");
							for (String s : scs) {
								for (String ss : cs) {
									if (s.trim().equalsIgnoreCase(ss.trim())) {
										ls.add(i);
									}
								}
							}
						}
					}
					list = new ArrayList<ItemDO>(ls);
				}
			}
		}

		result.setResultList(items);

		if (StringUtil.isNumeric(searchShopItem.getCount())) {
			result.setItemsPerPage(Integer.parseInt(searchShopItem.getCount()));
		} else {
			result.setItemsPerPage(SearchBaseDO.DEFAULT_COUNT);
		}

		result.setItems(list.size());
		if (StringUtil.isNumeric(searchShopItem.getStart())) {
			result.setPage(Integer.parseInt(searchShopItem.getStart()));
		} else {
			result.setPage(1);
		}

		for (int i = result.getStart(); i < result.getEndIndex(); i++) {

			ItemDO item = (ItemDO) list.get(i);
			ItemResult itemResult = new ItemResult();

			itemResult.setId(item.getId());
			itemResult.setCity(item.getCity());
			itemResult.setPrice(item.getPrice());
			itemResult.setTitle(item.getTitle());
			itemResult.setPicUrl(item.getPicUrl());
			itemResult.setSalesNum(item.getSales());
			itemResult.setSellerId(item.getSellerId());
			itemResult.setProvinces(item.getProvinces());
			itemResult.setSellerNick(item.getSellerNick());
			itemResult.setFreight(item.getMailCosts());
			itemResult.setGmtCreate(item.getGmtCreate());
			itemResult.setStoreCategories(item.getStoreCategories());
			itemResult.setFeatures(item.getFeatures());
			itemResult.setCode(item.getCode());
			itemResult.setCurStock(item.getCurStock());
			// itemResult.setEvaluateNum(); // evaluateNum

			items.add(itemResult);
		}

		return result;
	}

	@Override
	public SearchResult searchItem(SearchItemDO searchItemDO) throws ManagerException {
		try {
			return solrService.searchItem(searchItemDO);
		} catch (IOException e) {
			logger.error(e);
			throw new ManagerException("搜索出错!SearchManagerImpl#searchItem", e);
		} catch (Exception e) {
			logger.error(e);
			throw new ManagerException("搜索出错!SearchManagerImpl#searchItem", e);
		}
	}

	@Override
	public SearchResult searchItemByShop(SearchBaseDO searchShopItem) throws ManagerException {
		try {
			return solrService.searchItem(searchShopItem);
		} catch (Exception e) {
			throw new ManagerException("搜索出错!SearchManagerImpl#searchItemByShop", e);
		}
	}

	@Override
	public SearchResult searchItemByShopFromDB(SearchShopItem searchShopItem) throws ManagerException {
		try {
			return this.getItemsByShop(searchShopItem);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public SearchResult searchShop(SearchShopDO searchShopDO) throws ManagerException {
		try {
			return this.solrService.searchShop(searchShopDO);
		} catch (Exception e) {
			logger.error(e);
			throw new ManagerException("搜索出错!SearchManagerImpl#searchShop", e);
		}
	}

	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	public void setSolrService(SolrService solrService) {
		this.solrService = solrService;
	}

}
