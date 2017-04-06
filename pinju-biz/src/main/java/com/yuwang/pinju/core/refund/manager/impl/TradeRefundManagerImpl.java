package com.yuwang.pinju.core.refund.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.refund.dao.TradeRefundLeavewordDAO;
import com.yuwang.pinju.core.refund.manager.TradeRefundManager;
import com.yuwang.pinju.domain.refund.TradeRefundLeavewordDO;

public class TradeRefundManagerImpl  extends BaseManager implements TradeRefundManager{

	private TradeRefundLeavewordDAO tradeRefundLeavewordDAO;
	
	/**
	 * 查询留言列表信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言列表LIST
	 */
	public List<TradeRefundLeavewordDO> selectRefundLeavewordList(TradeRefundLeavewordDO tradeRefundLeavewordDO) throws ManagerException{
		try {
			return tradeRefundLeavewordDAO.selectRefundLeavewordList(tradeRefundLeavewordDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	/**
	 * 查询留言列表信息
	 * @param refundId 退款id
	 * @return Result 留言列表LIST
	 */
	public List<TradeRefundLeavewordDO> selectRefundLeavewordList(Long refundId)throws ManagerException{
		try {
			return tradeRefundLeavewordDAO.selectRefundLeavewordList(refundId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	/**
	 * 插入留言信息
	 * @param tradeRefundLeavewordDO 留言DO
	 * @return 留言ID
	 */
	public Long insertRefundLeaveword(TradeRefundLeavewordDO tradeRefundLeavewordDO) throws ManagerException{
		try {
			return tradeRefundLeavewordDAO.insertRefundLeaveword(tradeRefundLeavewordDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	
	public void setTradeRefundLeavewordDAO(TradeRefundLeavewordDAO tradeRefundLeavewordDAO) {
		this.tradeRefundLeavewordDAO = tradeRefundLeavewordDAO;
	}
}
