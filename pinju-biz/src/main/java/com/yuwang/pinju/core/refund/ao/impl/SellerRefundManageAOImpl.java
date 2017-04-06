package com.yuwang.pinju.core.refund.ao.impl;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.refund.ao.SellerRefundManageAO;
import com.yuwang.pinju.core.refund.manager.RefundLogisticsManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.refund.RefundDO;


public class SellerRefundManageAOImpl extends BaseAO implements  SellerRefundManageAO{
	private RefundManager refundManager;
	private OrderUpDateManager orderUpDateManager;
	private RefundLogisticsManager refundLogisticsManager;

	/**
	 * 卖家确认收货
	 * 
	 * @param refundId 退款id
	 */
	@SuppressWarnings("unchecked")
	public void sellerConfirmReceiveGoods(final Long refundId){
		
		getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					//更新退款
					refundManager.sellerConfirmReceiveGoods(refundId);
					
					//更新退货物流
					refundLogisticsManager.sellerConfirmReceiveGoods(refundId);
					
					//更新子订单
					RefundDO refundDO = refundManager.loadRefund(refundId);
					Boolean updateOrderSucc = orderUpDateManager.upRefundState(refundDO.getOrderItemId(), RefundDO.STATUS_REFUND_PROTOCAL_AGREE);
					
					return updateOrderSucc;
					
				} catch (ManagerException e) {
					status.setRollbackOnly();
					log.error("Event=[SellerRefundManageAOImpl#sellerConfirmReceiveGoods] 卖家确认收货", e);
					
					return null;
				}
				
			}
		});
	}
	
	/**
	 * 卖家拒绝退款申请
	 * 
	 * @param refundId 退款id
	 */
	@SuppressWarnings("unchecked")
	public void sellerRejectRefundApply(final Long refundId){

		getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					
					refundManager.sellerRejectRefundApply(refundId);
					
					RefundDO refundDO = refundManager.loadRefund(refundId);
					
					return orderUpDateManager.upRefundState(refundDO.getOrderItemId(), RefundDO.STATUS_SELLER_REFUSE_BUYER);
					
				} catch (ManagerException e) {
					status.setRollbackOnly();
					log.error("Event=[SellerRefundManageAOImpl#sellerRejectRefundApply] 卖家拒绝退款申请", e);
					
					return null;
				}
				
			}
		});
	}
	
	/**
	 * 卖家申请客服介入
	 */
	@SuppressWarnings("unchecked")
	public void sellerApplyCustProcess(final Long refundId){
		getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					
					refundManager.sellerApplyCustProcess(refundId);
					
					RefundDO refundDO = refundManager.loadRefund(refundId);
					
					return orderUpDateManager.upRefundState(refundDO.getOrderItemId(), RefundDO.STATUS_CS_PROCESS);
					
				} catch (ManagerException e) {
					status.setRollbackOnly();
					log.error("Event=[SellerRefundManageAOImpl#sellerApplyCustProcess] 卖家申请客服介入", e);
					
					return null;
				}
				
			}
		});
	}
	
	/**
	 * 卖家同意退款申请
	 */
	@SuppressWarnings("unchecked")
	public void sellerAgreeRefundApply(final Long refundId){
		getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					
					refundManager.sellerAgreeRefundApply(refundId);
					
					RefundDO refundDO = refundManager.loadRefund(refundId);
					
					return orderUpDateManager.upRefundState(refundDO.getOrderItemId(), refundDO.getRefundState());
					
				} catch (ManagerException e) {
					status.setRollbackOnly();
					log.error("Event=[SellerRefundManageAOImpl#sellerAgreeRefundApply] 卖家同意退款申请", e);
					
					return null;
				}
				
			}
		});
	}
	
	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}
	
	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}
	
	public void setRefundLogisticsManager(RefundLogisticsManager refundLogisticsManager) {
		this.refundLogisticsManager = refundLogisticsManager;
	}
	
}
