package com.yuwang.pinju.core.search.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.search.SearchBaseDO;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.SearchShopDO;
import com.yuwang.pinju.domain.search.SearchShopItem;
import com.yuwang.pinju.domain.search.result.SearchResult;

/**
 * 搜索引擎Manager
 * 
 * @project pinju-biz
 * @description
 * @author liuweiguo liuweiguo@zba.com
 * @date 2011-7-7上午10:31:33
 * @update 2011-7-7上午10:31:33
 * @version V1.0
 * 
 */
public interface SearchManager {

	/**
	 * 搜索商品
	 * 
	 * @param searchItemDO
	 * @return
	 * @throws ManagerException
	 */
	public SearchResult searchItem(SearchItemDO searchItemDO) throws ManagerException;

	/**
	 * 搜索店铺
	 * 
	 * @param searchShopDO
	 * @return
	 * @throws ManagerException
	 */
	public SearchResult searchShop(SearchShopDO searchShopDO) throws ManagerException;

	/**
	 * 搜索店铺内商品
	 * 
	 * @param searchShopItem
	 * @return
	 * @throws ManagerException
	 */
	public SearchResult searchItemByShop(SearchBaseDO searchShopItem) throws ManagerException;

	/**
	 * 实时查找店铺内商品
	 * 
	 * @param searchShopItem
	 * @return
	 * @throws ManagerException
	 */
	public SearchResult searchItemByShopFromDB(SearchShopItem searchShopItem) throws ManagerException;
}
