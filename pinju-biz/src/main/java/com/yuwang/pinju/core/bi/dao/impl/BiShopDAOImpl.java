package com.yuwang.pinju.core.bi.dao.impl;

import java.util.List;
import java.util.Map;
import com.yuwang.pinju.core.bi.dao.BiShopDAO;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.domain.bi.ShopSalesRankDO;

/**
 *
 * @author 杜成
 * @date   2011-6-20
 * @version 1.0
 */
public class BiShopDAOImpl extends BaseDAO implements BiShopDAO {
	/**
	 * BI_SHOP_SALESRANK 表 iBatis 命名空间前缀
	 */
	private ReadBaseDAO readBaseDAOForOracle ;
	
	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}
	private final static String NAMESPACE_PREFIX_BI_SALESRANK = "biSalesRank.";

	@SuppressWarnings("unchecked")
	@Override
	public List<ShopSalesRankDO> querySalesRank(Map<String, Object> map) throws DaoException {
		return readBaseDAOForOracle.executeQueryForList(NAMESPACE_PREFIX_BI_SALESRANK.concat("findShopSalesRank"), map);
	}
	

}
