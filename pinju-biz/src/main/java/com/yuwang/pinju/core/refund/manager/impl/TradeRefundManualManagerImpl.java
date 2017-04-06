package com.yuwang.pinju.core.refund.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.refund.dao.TradeRefundManualQueryDAO;
import com.yuwang.pinju.core.refund.dao.TradeRefundManualUpDateDAO;
import com.yuwang.pinju.core.refund.manager.TradeRefundManualManager;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;


public class TradeRefundManualManagerImpl implements TradeRefundManualManager {

	private TradeRefundManualUpDateDAO refundManualUpDateDAO;
	private TradeRefundManualQueryDAO refundManualQueryDAO;
	
	@Override
	public List<TradeRefundManualDO> getRefundManualList(
			TradeRefundManualDO refundManualDO) throws ManagerException {
		try {
			return refundManualQueryDAO.getRefundManualList(refundManualDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[TradeRefundManualManagerImpl#getRefundManualList] 获取退款工单记录报错：", e);
		}
	}

	/**
	 * <p>保存退款工单</p>
	 *
	 * @param refundManualDO
	 * @return 保存的信息id
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public Long saveRefundManual(TradeRefundManualDO refundManualDO) throws ManagerException{
		try {
			return refundManualUpDateDAO.saveRefundManual(refundManualDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[TradeRefundManualManagerImpl#getRefundManualList] 保存退款工单报错：", e);
		}
	}
	
	/**
	 * <p>查询手工退款工单</p>
	 *
	 * @param orderId
	 * @return 手工退款工单
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public TradeRefundManualDO loadRefundManualByOrderId(Long orderId) throws ManagerException{
		try {
			return refundManualQueryDAO.loadRefundManualByOrderId(orderId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[TradeRefundManualManagerImpl#getRefundManualList] 获取退款工单记录报错：", e);
		}
	}

	public void setRefundManualUpDateDAO(
			TradeRefundManualUpDateDAO refundManualUpDateDAO) {
		this.refundManualUpDateDAO = refundManualUpDateDAO;
	}

	public void setRefundManualQueryDAO(
			TradeRefundManualQueryDAO refundManualQueryDAO) {
		this.refundManualQueryDAO = refundManualQueryDAO;
	}

	@Override
	public List<TradeRefundManualDO> selectAllTradeRefundManualNotRefund(Long refundState)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return refundManualQueryDAO.selectAllTradeRefundManualNotRefund(refundState);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("Event=[TradeRefundManualManagerImpl#selectAllTradeRefundManualNotRefund] 获取退款工单记录报错：", e);
		}
	}



}
