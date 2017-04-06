package com.yuwang.pinju.core.refund.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.order.dao.OrderQueryDAO;
import com.yuwang.pinju.core.refund.dao.RefundDAO;
import com.yuwang.pinju.core.refund.manager.RefundCheckManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.trade.dao.VouchCreateDAO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

public class RefundCheckManagerImpl implements RefundCheckManager{
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private VouchCreateDAO vouchCreateDAO;
	private RefundDAO refundDAO;
	private OrderQueryDAO orderQueryDAO;
	private RefundManager refundManager;

	/**
	 * 是否需要执行平台退款
	 * 
	 * @param refundId 退款id
	 * @return true 全额退款，并且所有退款为协议达成状态
	 */
	public Boolean needPlatRefund(Long refundId)throws ManagerException{
		try {
			RefundDO refundDO = refundDAO.loadRefund(refundId);
			
			Long orderId = refundDO.getOrderId();
			VouchPayDO vouchPayDO = vouchCreateDAO.selectOrderPayByOrderId(orderId);
			
			List<RefundDO> refundList = refundDAO.queryRefundByOrderId(orderId);
			
			Long sum = 0L;
			for(RefundDO refund:refundList){
				sum += refund.getApplySum();
			}
			
			//是否全额退款
			Boolean allRefund = vouchPayDO.getRealPayMentamount().compareTo(sum) == 0;
			
			//不是全额退款时，不需要平台退款
			if(!allRefund)
				return false;
			
			List<OrderItemDO> orderItemList = orderQueryDAO.queryOrderItemList(orderId);
			for(OrderItemDO orderItemDO:orderItemList){
				RefundDO r =  refundDAO.loadRefundByOrderItem(orderItemDO.getOrderItemId());
				
				//子订单没有退款
				if(r == null){
					return false;
					
				//退款没有达成协议
				}else if(r.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE) != 0){
					return false;
				}
			}
			
			return true;
		}catch (DaoException e) {

			throw new ManagerException("Event=[RefundCheckManagerImpl#needPlatRefund]是否需要执行平台退款", e);
		}
	}
	
	/**
	 * 查询订单中处于退款中状态的子订单数量
	 * <p>除了订单状态为“协议达成”，“退款成功”，“退款关闭”，其他状态均为退款中</p>
	 * 
	 * @param orderId 订单id
	 * @return  退款中状态的子订单数量
	 */
	public Integer getRefundingCount(Long orderId)throws ManagerException{
		return getRefundingCount(orderId, null);
	}
	
	/**
	 * 查询订单中处于退款中状态的子订单数量,给定refundId除外
	 * <p>除了订单状态为“协议达成”，“退款成功”，“退款关闭”，其他状态均为退款中</p>
	 * 
	 * @param orderId 订单id
	 * @param excludeRefundId 退款id
	 * @return  退款中状态的子订单数量
	 */
	public Integer getRefundingCount(Long orderId, Long excludeRefundId) throws ManagerException {
		try {
			int count = 0;
			List<RefundDO> refundList = refundManager.queryRefundByOrderId(orderId);
			
			for (RefundDO refundDO : refundList) {
				
				//给定refundId除外
				if(excludeRefundId != null && 
						refundDO.getId().compareTo(excludeRefundId) == 0)
					continue;
				
				boolean refundConfirmed = 
					(refundDO.getRefundState().compareTo(RefundDO.STATUS_CLOSED) == 0 || 
					refundDO.getRefundState().compareTo(RefundDO.STATUS_SUCCESS) == 0 || 
					refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE) == 0);
				
				if (!refundConfirmed) {
					count++;
				}
			}
			
			return count;
		} catch (Exception e) {
			throw new ManagerException(
					"Event=[RefundCheckManagerImpl#getRefundingCount] 查询订单中处于退款中状态的子订单数量：",
					e);
		}
	}
	
	public void setVouchCreateDAO(VouchCreateDAO vouchCreateDAO) {
		this.vouchCreateDAO = vouchCreateDAO;
	}

	public void setRefundDAO(RefundDAO refundDAO) {
		this.refundDAO = refundDAO;
	}

	public void setOrderQueryDAO(OrderQueryDAO orderQueryDAO) {
		this.orderQueryDAO = orderQueryDAO;
	}

	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}
	
	

}
