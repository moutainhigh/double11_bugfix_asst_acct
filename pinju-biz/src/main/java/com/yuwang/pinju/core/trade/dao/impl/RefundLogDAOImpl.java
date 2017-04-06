package com.yuwang.pinju.core.trade.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.trade.dao.RefundLogDAO;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;

/**
 * <p>Discription: 退款日志DAO</p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-16
 */
public class RefundLogDAOImpl extends BaseDAO implements RefundLogDAO{
	private final String TRADE_REFUND_LOG_NAMESPANCE = "trade_refund_log.";
	
	/**
	 * 插入SQL Mapping ID
	 */
	private final String TRADE_REFUND_LOG_INSERT = "insertTradeRefundLog";
	/**
	 * 更新日志 Mapping ID
	 */
	private final String TRADE_REFUND_LOG_UPDATE = "updateTradeRefundLogStatus";
	
	/**
	 * 查询日志 Mapping ID
	 */
	private final String TRADE_REFUND_LOG_SELECT = "selectTradeRefundLog";
	/**
	 * 根据ID 查询日志  Mapping ID
	 */
	private final String TRADE_REFUND_LOG_SELECT_BY_ID = "selectTradeRefundLogByid";
	@Override
	public Long insertTradeRefundLog(RefundLogDO refundLogDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return (Long) super.executeInsert(TRADE_REFUND_LOG_NAMESPANCE.concat(TRADE_REFUND_LOG_INSERT), refundLogDO);
	}

	@Override
	public Long updateTradeRefundLog(RefundLogDO refundLogDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return (long) super.executeUpdate(TRADE_REFUND_LOG_NAMESPANCE.concat(TRADE_REFUND_LOG_UPDATE), refundLogDO);
	}

	
	@Override
	public RefundLogDO queryRefundLogById(Long id) throws DaoException {
		// TODO Auto-generated method stub
		return (RefundLogDO) super.executeQueryForObject(TRADE_REFUND_LOG_NAMESPANCE.concat(TRADE_REFUND_LOG_SELECT_BY_ID), id);
	}

	@Override
	public RefundLogDO queryRefundLogByRefundAndCmdnoId(String refundId,
			Integer cmdno) throws DaoException {
		RefundLogDO refundLogDO = new RefundLogDO();
		refundLogDO.setRefundId(refundId);
		refundLogDO.setCmdno(cmdno);
		refundLogDO = (RefundLogDO) super.executeQueryForObject(TRADE_REFUND_LOG_NAMESPANCE.concat(TRADE_REFUND_LOG_SELECT), refundLogDO);
		return refundLogDO;
	}

	@Override
	public RefundLogDO queryRefundLog(String transactionId, String payOrderId,
			Integer cmdno) throws DaoException {
		// TODO Auto-generated method stub
		RefundLogDO refundLogDO = new RefundLogDO();
		refundLogDO.setTransactionId(transactionId);
		refundLogDO.setPayorderId(payOrderId);
		refundLogDO.setCmdno(cmdno);
		refundLogDO = (RefundLogDO) super.executeQueryForObject(TRADE_REFUND_LOG_NAMESPANCE.concat(TRADE_REFUND_LOG_SELECT), refundLogDO);
		return refundLogDO;
	}

	@Override
	public RefundLogDO queryRefundLogByOrderId(Long orderId, Integer cmdno)
			throws DaoException {
		// TODO Auto-generated method stub
		RefundLogDO refundLogDO = new RefundLogDO();
		refundLogDO.setOrderId(orderId);
		refundLogDO.setCmdno(cmdno);
		refundLogDO = (RefundLogDO) super.executeQueryForObject(TRADE_REFUND_LOG_NAMESPANCE.concat(TRADE_REFUND_LOG_SELECT), refundLogDO);
		return refundLogDO;
	}
}


