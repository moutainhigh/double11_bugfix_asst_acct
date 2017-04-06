package com.yuwang.pinju.core.refund.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;

public interface RefundLogisticsDAO {
	/**
	 * <p>保存退货物流信息</p>
	 *
	 * @param tradeRefundLogisticsDO
	 * @return
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	Long persist(TradeRefundLogisticsDO tradeRefundLogisticsDO) throws DaoException;
	
	/**
	 * <p>加载退货物流信息</p>
	 *
	 * @param refundId
	 * @return 退货物流信息
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public TradeRefundLogisticsDO loadRefundLogistics(Long refundId) throws DaoException;
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
	Long update(TradeRefundLogisticsDO tradeRefundLogisticsDO) throws DaoException;
}
