package com.yuwang.pinju.core.refund.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.refund.TradeRefundLeavewordDO;

public interface TradeRefundLeavewordDAO {

	/**
	 * 查询留言列表信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言列表LIST
	 */
	public List<TradeRefundLeavewordDO> selectRefundLeavewordList(TradeRefundLeavewordDO tradeRefundLeavewordDO) throws DaoException;
	
	/**
	 * 查询留言列表信息
	 * @param refundId 退款id
	 * @return Result 留言列表LIST
	 */
	public List<TradeRefundLeavewordDO> selectRefundLeavewordList(Long refundId)throws DaoException;
	
	/**
	 * 插入留言信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言ID
	 */
	public Long insertRefundLeaveword(TradeRefundLeavewordDO tradeRefundLeavewordDO) throws DaoException ;
}
