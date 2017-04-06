package com.yuwang.pinju.core.margin.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.margin.dao.MarginSellerDAO;
import com.yuwang.pinju.domain.margin.MarginSellerDO;

/**  
 * @Project: pinju-biz
 * @Discription: [卖家保证金DAOImpl]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-1 下午04:57:38
 * @update 2011-8-1 下午04:57:38
 * @version V1.0  
 */
public class MarginSellerDAOImpl extends BaseDAO implements MarginSellerDAO{


	@Override
	public MarginSellerDO getMarginSellerDOBySellerId(Long sellerId) throws DaoException {
		return (MarginSellerDO)executeQueryForObject("TradeMarginSeller.getMarginSellerDOBySellerId",sellerId);
	}

	@Override
	public void insertMarginSellerDORecord(MarginSellerDO marginSellerDO)
			throws DaoException {
		marginSellerDO.setId((Long)executeInsert("TradeMarginSeller.insertMarginSellerRecord",marginSellerDO));
	}

	@Override
	public int updateMarginSellerDORecord(MarginSellerDO marginSellerDO)
			throws DaoException {
		return executeUpdate("TradeMarginSeller.updateMarginSellerRecord",marginSellerDO);
	}

	@Override
	public Long queryIndexVisitCount() throws DaoException {
		return (Long) executeQueryForObject("TradeMarginSeller.selectSeqIndexCount", "");
	}

}
