package com.yuwang.pinju.core.refund.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;

public interface TradeRefundManualManager {
	
	/**
	 * Created on 2011-9-15
	 * <p>Discription: 查询退款工单记录</p>
	 * @param refundManualDO
	 * @return
	 * @throws ManagerException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<TradeRefundManualDO> getRefundManualList(TradeRefundManualDO refundManualDO) throws ManagerException;
	
	/**
	 * <p>保存退款工单</p>
	 *
	 * @param refundManualDO
	 * @return 保存的信息id
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public Long saveRefundManual(TradeRefundManualDO refundManualDO) throws ManagerException;
	
	/**
	 * <p>查询手工退款工单</p>
	 *
	 * @param orderId
	 * @return 手工退款工单
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public TradeRefundManualDO loadRefundManualByOrderId(Long orderId) throws ManagerException;
	
	
	/**
	 * 获取所有没有退款的手工单
	 * @return
	 * @throws ManagerException
	 */
	public List<TradeRefundManualDO> selectAllTradeRefundManualNotRefund(Long refundState) throws ManagerException;
}