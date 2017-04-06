package com.yuwang.pinju.core.trade.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.trade.dao.DirectDAO;
import com.yuwang.pinju.domain.trade.DirectOrderDO;
import com.yuwang.pinju.domain.trade.DirectPayDO;
import com.yuwang.pinju.domain.trade.DirectPayRevLogDO;
import com.yuwang.pinju.domain.trade.DirectPaySendLogDO;

/**  
 * @Project: pinju-biz
 * @Discription: [保证金报文接受记录DAO实现类]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-1 下午08:03:51
 * @update 2011-8-1 下午08:03:51
 * @version V1.0  
 */
public class DirectDAOImpl extends BaseDAO implements DirectDAO{

	@Override
	public DirectPayRevLogDO getDirectPayRevLogDOById(Long id) throws DaoException{
		return (DirectPayRevLogDO)executeQueryForObject("TradeDirectPayRevLog.getDirectPayRevLogDOById",id);
	}

	@Override
	public void insertdirectPayRevLogRecord(DirectPayRevLogDO directPayRevLogDO) throws DaoException{
		directPayRevLogDO.setId((Long)executeInsert("TradeDirectPayRevLog.insertdirectPayRevLogRecord",directPayRevLogDO));
	}
	
	@Override
	public void insertDirectOrderRecord(DirectOrderDO directOrderDO) throws DaoException{
		directOrderDO.setId((Long)executeInsert("TradeDirectPayBizOrder.insertDirectOrderRecord",directOrderDO));
	}

	@Override
	public DirectOrderDO getDirectOrderDOById(Long id) throws DaoException{
		return (DirectOrderDO)executeQueryForObject("TradeDirectPayBizOrder.getDirectOrderDOById",id);
	}

	@Override
	public Long getOrderId() throws DaoException {
		return (Long)executeQueryForObject("TradeDirectPayBizOrder.getOrderId",null);
	}
	
	@Override
	public void insertDirectPayRecord(DirectPayDO directPayDO) throws DaoException{
		executeInsert("TradeDirectPayPayOrder.insertDirectPayRecord",directPayDO);
	}

	@Override
	public Long getPayOrderId() throws DaoException {
		return (Long)executeQueryForObject("TradeDirectPayPayOrder.getPayOrderId",null);
	}
	
	@Override
	public DirectPaySendLogDO getDirectPaySendLogDOById(Long id) throws DaoException{
		return (DirectPaySendLogDO)executeQueryForObject("TradeDirectPaySendLog.getDirectPaySendLogDOById",id);
	}

	@Override
	public void insertDirectPaySendLogRecord(DirectPaySendLogDO marginSendLogDO) throws DaoException{
		marginSendLogDO.setId((Long)executeInsert("TradeDirectPaySendLog.insertDirectPaySendLogRecord",marginSendLogDO));
	}
	
	@Override
	public int updateDirectOrderDOStatus(DirectOrderDO directOrderDO)
			throws DaoException {
		return executeUpdate("TradeDirectPayBizOrder.updateDirectOrderDOStatus",directOrderDO);
		
	}

	@Override
	public int updateDirectPayDOStatus(DirectPayDO directPayDO)
			throws DaoException {
		return executeUpdate("TradeDirectPayPayOrder.updateDirectPayDOStatus",directPayDO);
	}

	@Override
	public DirectPayDO getDirectPayDOById(Long payOrderId) throws DaoException {
		return (DirectPayDO)executeQueryForObject("TradeDirectPayPayOrder.getPayOrderDO",payOrderId);
	}

	@Override
	public DirectPayDO getDirectPayDO(DirectPayDO directPayDO)
			throws DaoException {
		return (DirectPayDO)executeQueryForObject("TradeDirectPayPayOrder.getPayOrderDOByOrderId",directPayDO);
	}

}
