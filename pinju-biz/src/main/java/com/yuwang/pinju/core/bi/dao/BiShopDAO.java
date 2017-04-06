package com.yuwang.pinju.core.bi.dao;

import java.util.List;
import java.util.Map;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.bi.ShopSalesRankDO;

/**
 *
 * @author 杜成
 * @date   2011-6-20
 * @version 1.0
 */
public interface BiShopDAO {
	
	/**
	 * @see 查询商品销售排行
	 * @param map
	 * key:
	 * @return 没有的话返回长度为0的List
	 * @throws DaoException
	 */
	List<ShopSalesRankDO> querySalesRank(Map<String, Object> map)throws DaoException;
}
