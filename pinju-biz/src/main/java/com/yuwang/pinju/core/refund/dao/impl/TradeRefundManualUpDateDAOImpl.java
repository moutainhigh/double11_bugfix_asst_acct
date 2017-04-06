package com.yuwang.pinju.core.refund.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.refund.dao.TradeRefundManualUpDateDAO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;

public class TradeRefundManualUpDateDAOImpl extends BaseDAO implements TradeRefundManualUpDateDAO {

	private final String REFUND_MANUAL_NAMESPACE="trade_refund_manual.";
	
	private final String UPDATE_REFUND_MANUAL ="updateTradeRefundManual";
	
	@Override
	public Long saveRefundManual(TradeRefundManualDO refundManualDO) throws DaoException{
		return (Long)super.executeInsert(REFUND_MANUAL_NAMESPACE + "insertTradeRefundManual", refundManualDO);
	}
	
	@Override
	public Long updateRefundManual(TradeRefundManualDO refundManualDO)
			throws DaoException {
		return (long) super.executeUpdate(REFUND_MANUAL_NAMESPACE+UPDATE_REFUND_MANUAL, refundManualDO);
	}
	
}
