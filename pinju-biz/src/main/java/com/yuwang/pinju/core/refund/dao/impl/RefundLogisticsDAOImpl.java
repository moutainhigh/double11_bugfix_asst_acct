package com.yuwang.pinju.core.refund.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.refund.dao.RefundLogisticsDAO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;

public class RefundLogisticsDAOImpl extends BaseDAO implements RefundLogisticsDAO{
	private final String REFUND_NAME_SPACE="trade_refund_logistics.";
	
	/**
	 * <p>保存退货物流信息</p>
	 *
	 * @param tradeRefundLogisticsDO
	 * @return
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	public Long persist(TradeRefundLogisticsDO tradeRefundLogisticsDO) throws DaoException{
		return (Long)super.executeInsert(REFUND_NAME_SPACE + "insertTradeRefundLogistics", tradeRefundLogisticsDO);
	}
	
	/**
	 * <p>加载退货物流信息</p>
	 *
	 * @param refundId
	 * @return 退货物流信息
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public TradeRefundLogisticsDO loadRefundLogistics(Long refundId) throws DaoException{
		return (TradeRefundLogisticsDO)super.executeQueryForObject(REFUND_NAME_SPACE + "selectTradeRefundLogisticsByRefundid", refundId);
	}
	
	/**
	 * 
	 * Created on 2011-8-18
	 * @see
	 * <p>Discription: 
	 * 	 更新退款物流信息
	 * </p>
	 * @return refundId
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long update(TradeRefundLogisticsDO tradeRefundLogisticsDO)
			throws DaoException {
		return (long) super.executeUpdate(REFUND_NAME_SPACE+ "updateTradeRefundLogistics", tradeRefundLogisticsDO);
		// TODO Auto-generated method stub
	}
}
