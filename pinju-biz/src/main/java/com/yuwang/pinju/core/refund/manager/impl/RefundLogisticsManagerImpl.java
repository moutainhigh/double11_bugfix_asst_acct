package com.yuwang.pinju.core.refund.manager.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.refund.dao.RefundLogisticsDAO;
import com.yuwang.pinju.core.refund.manager.RefundLogisticsManager;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;

public class RefundLogisticsManagerImpl implements RefundLogisticsManager{
	private final static Log log = LogFactory.getLog(RefundLogisticsManagerImpl.class);
	RefundLogisticsDAO refundLogisticsDAO;
	
	/**
	 * <p>加载退货物流信息</p>
	 *
	 * @param refundId
	 * @return 更新或者保存的信息id
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public TradeRefundLogisticsDO loadRefundLogistics(Long refundId) throws ManagerException{
		try {
			return refundLogisticsDAO.loadRefundLogistics(refundId);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundLogisticsManagerImpl#loadRefundLogistics]加载退货物流信息", e);
		}
	}
	
	/**
	 * <p>保存退货物流信息</p>
	 *
	 * @param tradeRefundLogisticsDO
	 * @return 更新或者保存的信息id
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public Long persist(TradeRefundLogisticsDO tradeRefundLogisticsDO) throws ManagerException{
		try {
			return refundLogisticsDAO.persist(tradeRefundLogisticsDO);
		}catch (DaoException e) {
			log.error(e);
			throw new ManagerException("Event=[RefundLogisticsManagerImpl#persist]保存退货物流信息", e);
		}
	}

	public RefundLogisticsDAO getRefundLogisticsDAO() {
		return refundLogisticsDAO;
	}

	public void setRefundLogisticsDAO(RefundLogisticsDAO refundLogisticsDAO) {
		this.refundLogisticsDAO = refundLogisticsDAO;
	}

	@Override
	
	public Long update(TradeRefundLogisticsDO tradeRefundLogisticsDO)
			throws ManagerException {

		try{
			return refundLogisticsDAO.update(tradeRefundLogisticsDO);
		}catch (DaoException e) {

			log.error("update refundLogistics error : "+e);
			throw new ManagerException("Event=[RefundLogisticsManagerImpl#update]更新退货物流信息", e);
		}
	}

	/**
	 * 卖家确认收货
	 * 
	 * @param refundId 退款id
	 */
	public Long sellerConfirmReceiveGoods(Long refundId) throws ManagerException{
		try{
			TradeRefundLogisticsDO tradeRefundLogisticsDO = new TradeRefundLogisticsDO();
			Date now = new Date();
			tradeRefundLogisticsDO.setGmtModified(now);
			tradeRefundLogisticsDO.setConfirmDate(now);
			tradeRefundLogisticsDO.setRefundId(refundId);
			tradeRefundLogisticsDO.setLogisticsState(TradeRefundLogisticsDO.SELLER_CONFIRMRECEIVEGOODS);
			
			return refundLogisticsDAO.update(tradeRefundLogisticsDO);
		}catch (DaoException e) {
			log.error("update refundLogistics error : "+e);
			throw new ManagerException("Event=[RefundLogisticsManagerImpl#sellerConfirmReceiveGoods]卖家确认收货", e);
		}
	}
}
