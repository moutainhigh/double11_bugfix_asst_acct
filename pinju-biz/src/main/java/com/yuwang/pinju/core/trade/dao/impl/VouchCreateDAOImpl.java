package com.yuwang.pinju.core.trade.dao.impl;

import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.trade.dao.VouchCreateDAO;
import com.yuwang.pinju.domain.order.pay.PayBackLogDO;
import com.yuwang.pinju.domain.order.pay.PaySendLogDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * @Discription: 担保交易日志操作实现类
 * @Project: pinju-biz
 * @Package: com.yuwang.pinju.core.trade.dao.impl
 * @Title: VouchCreateDAOImpl.java
 * @author: [贺泳]
 * @date 2011-9-15 上午11:12:19
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class VouchCreateDAOImpl extends BaseDAO implements VouchCreateDAO{
	private final String TRADE_PAY_SEND_LOG_NAMESPACE = "TRADE_PAY_SEND_LOG.";
	private final String TRADE_PAY_BACK_LOG_NAMESPACE="TRADE_PAY_BACK_LOG.";
	private final String TRADE_ORDER_PAY_NAMESPACE="TRADE_ORDER_PAY.";

	@Override
	public Long insertTradePaySendLog(PaySendLogDO paySendLogDO)throws DaoException {
		return (Long)super.executeInsert(TRADE_PAY_SEND_LOG_NAMESPACE + "insertTradePaySendLog", paySendLogDO);
	}

	@Override
	public Long updateTradePaySendLog(PaySendLogDO paySendLogDO)throws DaoException {
		return (long)super.executeUpdate(TRADE_PAY_SEND_LOG_NAMESPACE + "updateTradePaySendLog", paySendLogDO);
	}

	@Override
	public Long insertTradePayBackLog(PayBackLogDO payBackLogDO)throws DaoException {
		return (Long)super.executeInsert(TRADE_PAY_BACK_LOG_NAMESPACE + "insertTradePayBackLog", payBackLogDO);
	}

	@Override
	public Long updateTradePayBackLog(PayBackLogDO payBackLogDO)throws DaoException {
		return (long)super.executeUpdate(TRADE_PAY_BACK_LOG_NAMESPACE + "updateTradePayBackLog", payBackLogDO);
	}

	@Override
	public Long insertTrderOrderPay(VouchPayDO orderPayDO) throws DaoException {
		return (Long) super.executeInsert(TRADE_ORDER_PAY_NAMESPACE + "insertTradeOrderPay", orderPayDO);
	}

	@Override
	public VouchPayDO selectOrderPayById(Long id) throws DaoException {
		return (VouchPayDO) super.executeQueryForObject(TRADE_ORDER_PAY_NAMESPACE + "selectTradeOrderPayByid", id);
	}

	@Override
	public VouchPayDO selectOrderPayByOrderId(Long OrderId) throws DaoException {
		return (VouchPayDO) super.executeQueryForObject(TRADE_ORDER_PAY_NAMESPACE + "selectTradeOrderPayByOrderId", OrderId);
	}
	
	@Override
	public VouchPayDO selectOrderPay(VouchPayDO orderPayDO) throws DaoException {
		return (VouchPayDO) super.executeQueryForObject(TRADE_ORDER_PAY_NAMESPACE + "selectTradeOrderPay", orderPayDO);
	}

	@Override
	public Long updateOrderPay(VouchPayDO orderPayDO) throws DaoException {
		return (long)super.executeUpdate(TRADE_ORDER_PAY_NAMESPACE + "updateTradeOrderPayByOrderPayId", orderPayDO);
	}

	@Override
	public VouchPayDO selectOrderPay(Map map) throws DaoException {
		return (VouchPayDO) super.executeQueryForObject(TRADE_ORDER_PAY_NAMESPACE + "selectTradeOrderPayByDO", map);
	}

}
