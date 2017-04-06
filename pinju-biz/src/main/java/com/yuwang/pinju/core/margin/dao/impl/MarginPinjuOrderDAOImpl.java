package com.yuwang.pinju.core.margin.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.margin.dao.MarginPinjuOrderDAO;
import com.yuwang.pinju.domain.margin.MarginPinjuOrderDO;
import com.yuwang.pinju.domain.margin.query.MarginPinJuOrderQuery;

/**  
 * @Project: pinju-biz
 * @Discription: [品聚保证金交易流水DAO实现类]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-9 下午05:18:05
 * @update 2011-8-9 下午05:18:05
 * @version V1.0  
 */
public class MarginPinjuOrderDAOImpl extends BaseDAO implements MarginPinjuOrderDAO{

	@Override
	public MarginPinjuOrderDO getMarginPinjuOrderDOById(Long memberId) throws DaoException{
		return (MarginPinjuOrderDO)executeQueryForObject("TradeMarginPJOrder.getMarginPJOrderDOById",memberId);
	}

	@Override
	public int getMarginPinjuOrderDOsCount(MarginPinJuOrderQuery marginPinJuOrderQuery) throws DaoException{
		return (Integer)executeQueryForObject("TradeMarginPJOrder.getMarginPJOrderDOsCount",marginPinJuOrderQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarginPinjuOrderDO> getPinjuOrderDOs(MarginPinJuOrderQuery marginPinJuOrderQuery) throws DaoException{
		marginPinJuOrderQuery.setItems(getMarginPinjuOrderDOsCount(marginPinJuOrderQuery));
		return (List<MarginPinjuOrderDO>)executeQueryForList("TradeMarginPJOrder.getMarginPJOrderDOs",marginPinJuOrderQuery);
	}

	@Override
	public void insertMarginPinjuOrderRecord(MarginPinjuOrderDO marginPinjuOrderDO) throws DaoException{
		marginPinjuOrderDO.setId((Long)executeInsert("TradeMarginPJOrder.insertMarginPJOrderRecord",marginPinjuOrderDO));
	}

	@Override
	public int updateMarginPinjuOrderRecord(MarginPinjuOrderDO marginPinjuOrderDO) throws DaoException{
		return executeUpdate("TradeMarginPJOrder.updateMarginPJOrderRecord",marginPinjuOrderDO);
	}

}
