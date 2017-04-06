package com.yuwang.pinju.core.refund.manager;

import com.yuwang.pinju.core.common.ManagerException;

/**
 * 退款检查管理
 * @author shihongbo
 * @since   2011-6-24
 */
public interface RefundCheckManager {
	/**
	 * 是否需要执行平台退款
	 * 
	 * @param refundId 退款id
	 * @return true 全额退款，并且所有退款为协议达成状态
	 */
	public Boolean needPlatRefund(Long refundId)throws ManagerException;
	
	/**
	 * 查询订单中处于退款中状态的子订单数量,给定refundId除外
	 * <p>除了订单状态为“协议达成”，“退款成功”，“退款关闭”，其他状态均为退款中</p>
	 * 
	 * @param orderId 订单id
	 * @param excludeRefundId 退款id
	 * @return  退款中状态的子订单数量
	 */
	public Integer getRefundingCount(Long orderId, Long excludeRefundId)throws ManagerException;
	
	/**
	 * 查询订单中处于退款中状态的子订单数量
	 * <p>除了订单状态为“协议达成”，“退款成功”，“退款关闭”，其他状态均为退款中</p>
	 * 
	 * @param orderId 订单id
	 * @return  退款中状态的子订单数量
	 */
	public Integer getRefundingCount(Long orderId)throws ManagerException;
}
