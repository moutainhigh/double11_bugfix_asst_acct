package com.yuwang.pinju.core.refund.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.refund.TradeRefundLeavewordDO;

public interface TradeRefundManager {

	/**
	 * 查询留言列表信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言列表LIST
	 */
	public List<TradeRefundLeavewordDO> selectRefundLeavewordList(TradeRefundLeavewordDO tradeRefundLeavewordDO) throws ManagerException;
	
	/**
	 * 查询留言列表信息
	 * @param refundId 退款id
	 * @return Result 留言列表LIST
	 */
	public List<TradeRefundLeavewordDO> selectRefundLeavewordList(Long refundId)throws ManagerException;
	
	/**
	 * 插入留言信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言ID
	 */
	public Long insertRefundLeaveword(TradeRefundLeavewordDO tradeRefundLeavewordDO) throws ManagerException ;
}
