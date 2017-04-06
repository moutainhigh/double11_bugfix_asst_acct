package com.yuwang.pinju.core.refund.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.refund.dao.TradeRefundLeavewordDAO;
import com.yuwang.pinju.domain.refund.TradeRefundLeavewordDO;

public class TradeRefundLeavewordDAOImpl extends BaseDAO implements TradeRefundLeavewordDAO {
	private final String REFUND_NAME_SPACE="trade_refund_leaveword.";
	
	/**
	 * 查询留言列表信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言列表LIST
	 */
	@SuppressWarnings("unchecked")
	public List<TradeRefundLeavewordDO> selectRefundLeavewordList(TradeRefundLeavewordDO tradeRefundLeavewordDO) throws DaoException{
		return (List<TradeRefundLeavewordDO>)super.executeQueryForList(REFUND_NAME_SPACE + "selectTradeRefundLeaveword",tradeRefundLeavewordDO);
	}
	
	/**
	 * 查询留言列表信息
	 * @param refundId 退款id
	 * @return Result 留言列表LIST
	 */
	public List<TradeRefundLeavewordDO> selectRefundLeavewordList(Long refundId)throws DaoException{
		return (List<TradeRefundLeavewordDO>)super.executeQueryForList(REFUND_NAME_SPACE + "selectLeavewordByRefundId", refundId);
	}
	
	/**
	 * 插入留言信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言ID
	 */
	public Long insertRefundLeaveword(TradeRefundLeavewordDO tradeRefundLeavewordDO) throws DaoException {
		return (Long)super.executeInsert(REFUND_NAME_SPACE + "insertTradeRefundLeaveword",tradeRefundLeavewordDO);
	}
}
