package com.yuwang.pinju.core.refund.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;

/**
 * 退货物流信息管理
 * @author shihongbo
 * @since   2011-07-06
 */
public interface RefundLogisticsManager {
	/**
	 * <p>加载退货物流信息</p>
	 *
	 * @param refundId
	 * @return 退货物流信息管理
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public TradeRefundLogisticsDO loadRefundLogistics(Long refundId) throws ManagerException;
	
	
	/**
	 * <p>保存退货物流信息</p>
	 *
	 * @param tradeRefundLogisticsDO
	 * @return 更新或者保存的信息id
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public Long persist(TradeRefundLogisticsDO tradeRefundLogisticsDO) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-8-18
	 * @see
	 * <p>Discription: 
	 * 	 更新退款物流信息
	 * </p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long update(TradeRefundLogisticsDO tradeRefundLogisticsDO) throws ManagerException;
	
	/**
	 * 卖家确认收货
	 * 
	 * @param refundId 退款id
	 */
	public Long sellerConfirmReceiveGoods(Long refundId) throws ManagerException;
}
