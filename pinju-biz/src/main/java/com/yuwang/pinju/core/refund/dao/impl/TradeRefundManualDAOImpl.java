package com.yuwang.pinju.core.refund.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.refund.dao.TradeRefundManualDAO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;

public class TradeRefundManualDAOImpl extends BaseDAO implements TradeRefundManualDAO {

	private final String REFUND_MANUAL_NAMESPACE="trade_refund_manual.";
	
	private final String SELECT_ALL_NOT_REFUND_MANUAL = "selectAllTradeRefundManualNotRefund";
	
	private final String UPDATE_REFUND_MANUAL ="updateTradeRefundManual";
	@Override
	public List<TradeRefundManualDO> getRefundManualList(
			TradeRefundManualDO tradeRefundManualDO) throws DaoException {
		return (List<TradeRefundManualDO>)super.executeQueryForList(REFUND_MANUAL_NAMESPACE + "selectTradeRefundManual", tradeRefundManualDO);
	}

	/**
	 * <p>保存退款工单</p>
	 *
	 * @param refundManualDO
	 * @return 保存的信息id
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	public Long saveRefundManual(TradeRefundManualDO refundManualDO) throws DaoException{
		return (Long)super.executeInsert(REFUND_MANUAL_NAMESPACE + "insertTradeRefundManual", refundManualDO);
	}
	
	/**
	 * <p>查询手工退款工单</p>
	 *
	 * @param orderId
	 * @return 手工退款工单
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	public TradeRefundManualDO loadRefundManualByOrderId(Long orderId) throws DaoException{
		return (TradeRefundManualDO)super.executeQueryForObject(REFUND_MANUAL_NAMESPACE + "selectRefundManualByOrderId", orderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeRefundManualDO> selectAllTradeRefundManualNotRefund(Long refundState)throws DaoException {
		// TODO Auto-generated method stub
		return super.executeQueryForList(REFUND_MANUAL_NAMESPACE.concat(SELECT_ALL_NOT_REFUND_MANUAL),refundState);
	}

	@Override
	public Long updateRefundManual(TradeRefundManualDO refundManualDO)
			throws DaoException {
		// TODO Auto-generated method stub
		return (long) super.executeUpdate(REFUND_MANUAL_NAMESPACE+UPDATE_REFUND_MANUAL, refundManualDO);
	}
	
}
