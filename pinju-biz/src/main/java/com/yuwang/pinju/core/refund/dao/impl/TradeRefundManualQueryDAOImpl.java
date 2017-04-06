package com.yuwang.pinju.core.refund.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.refund.dao.TradeRefundManualQueryDAO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;

public class TradeRefundManualQueryDAOImpl  implements TradeRefundManualQueryDAO {

	private final String REFUND_MANUAL_NAMESPACE="trade_refund_manual.";
	private final String SELECT_ALL_NOT_REFUND_MANUAL = "selectAllTradeRefundManualNotRefund";
	
	private ReadBaseDAO readBaseDAOForOracle ;
	
	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}
	/**
	 * 获取所有的手工单
	 */
	@SuppressWarnings("unchecked")
	public List<TradeRefundManualDO> getRefundManualList(
			TradeRefundManualDO tradeRefundManualDO) throws DaoException {
		return (List<TradeRefundManualDO>)readBaseDAOForOracle.executeQueryForList(REFUND_MANUAL_NAMESPACE + "selectTradeRefundManual", tradeRefundManualDO);
	}

	/**
	 * 根据订单ID 获取手工退款单
	 */
	public TradeRefundManualDO loadRefundManualByOrderId(Long orderId) throws DaoException{
		return (TradeRefundManualDO)readBaseDAOForOracle.executeQueryForObject(REFUND_MANUAL_NAMESPACE + "selectRefundManualByOrderId", orderId);
	}

	/**
	 * 查出没有退款成功的手工单
	 */
	@SuppressWarnings("unchecked")
	public List<TradeRefundManualDO> selectAllTradeRefundManualNotRefund(Long refundState)throws DaoException {
		// TODO Auto-generated method stub
		return  (List<TradeRefundManualDO>)readBaseDAOForOracle.executeQueryForList(REFUND_MANUAL_NAMESPACE.concat(SELECT_ALL_NOT_REFUND_MANUAL),refundState);
	}
	
}
