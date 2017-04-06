package com.yuwang.pinju.core.refund.ao.impl;

import java.util.Date;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.RefundBaseAO;
import com.yuwang.pinju.core.refund.ao.RefundManageAO;
import com.yuwang.pinju.core.refund.ao.SellerRefundManageAO;
import com.yuwang.pinju.core.refund.ao.SellerRefundTimeoutAO;
import com.yuwang.pinju.core.refund.manager.RefundCheckManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.trade.ao.TenPlatformRefundAO;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.DateUtil;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;

public class SellerRefundTimeoutAOImpl extends BaseAO implements SellerRefundTimeoutAO{

	private SellerRefundManageAO sellerRefundManageAO;
	private RefundApplyAO refundApplyAO;
	private RefundManageAO refundManageAO;
	private RefundCheckManager refundCheckManager;
	private TenPlatformRefundAO tenPlatformRefundAO;
	private OrderUpDateManager orderUpDateManager;
	private RefundManager refundManager;

	/**
	 * 检查买家申请退款，卖家响应是否超时
	 * 
	 * @param refundDO 退款
	 * @return true 卖家超时
	 */
	public Boolean isSellerReplyTimeout(RefundDO refundDO){
		Date leftDay = DateUtil.skipDateTime(refundDO.getApplyDate(), 5);
		
		Date now = new Date();
		
		return leftDay.compareTo(now) < 0;
	}
	
	/**
	 * 买家申请退款，卖家响应超时的处理
	 * 
	 * @param refundId 退款id
	 * @param isSeller 为true表示卖家，否则表示买家
	 * @return result
	 */
	public Result sellerReplyTimeout(Long refundId, Boolean isSeller){
		Result result = new ResultSupport();
		result.setSuccess(true);
		
		try{
			//超时默认同意
			sellerRefundManageAO.sellerAgreeRefundApply(refundId);

			Boolean needPlatRefund = refundCheckManager.needPlatRefund(refundId);

			//需要全额退款
			if(needPlatRefund){
				RefundDO _refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
				log.info("orderId:" + _refundDO.getOrderId() + " exuecute platform refund.");
				tenPlatformRefundAO.platformRefundForOneOrder(_refundDO.getOrderId());
			}

			
			//根据退款状态，设置页面跳转url
			RefundDO refundDO = refundApplyAO.loadRefundApplyInfo(refundId);

			String sellerReplyTimeoutUrl = "";
			if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS) == 0){
				if(isSeller)
					sellerReplyTimeoutUrl = "/refund/sellerViewWaitGoodsReturn.htm?id=" + refundId;
				else 
					sellerReplyTimeoutUrl = "/refund/buyerViewWaitGoodsReturn.htm?id=" + refundId;

			}else if(refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE) == 0){
				refundManageAO.endRefund(refundDO);

				if(isSeller)
					sellerReplyTimeoutUrl = "/refund/sellerViewRefundProtocalAgree.htm?id=" + refundId;
				else 
					sellerReplyTimeoutUrl = "/refund/buyerViewRefundProtocalAgree.htm?id=" + refundId;

			}else if(refundDO.getRefundState().compareTo(RefundDO.STATUS_SUCCESS) == 0){
				refundManageAO.endRefund(refundDO);

				if(isSeller)
					sellerReplyTimeoutUrl = "/refund/sellerViewRefundSuccess.htm?id=" + refundId;
				else 
					sellerReplyTimeoutUrl = "/refund/buyerViewRefundSuccess.htm?id=" + refundId;

			}else if(refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_FAIL) == 0){
				refundManageAO.endRefund(refundDO);

				if(isSeller)
					sellerReplyTimeoutUrl = "/refund/sellerViewRefundFail.htm?id=" + refundId;
				else 
					sellerReplyTimeoutUrl = "/refund/buyerViewRefundFail.htm?id=" + refundId;

			}
			
			result.setModel("sellerReplyTimeoutUrl", sellerReplyTimeoutUrl);
			
		}catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[SellerRefundTimeoutAOImpl#sellerReplyTimeout]", e);
		}
		return result;
	}
	
	
	/**
	 * 检查买家退货，卖家确认收货是否超时
	 * 
	 * @param refundId 退款id
	 * @return true 卖家超时
	 */
	public Boolean isSellerConfirmGoodsTimeout(Long refundId){
		TradeRefundLogisticsDO tradeRefundLogisticsDO = refundApplyAO.getRefundLogistics(refundId);
		
		Date leftDay;
		
		// 1 是平邮 2,3是快递
		Long logisticsType = tradeRefundLogisticsDO.getLogisticsType() == null ? 1L : tradeRefundLogisticsDO.getLogisticsType();
		if (logisticsType.compareTo(1L) == 0) {
			leftDay = DateUtil.skipDateTime(tradeRefundLogisticsDO.getSendDate(),RefundBaseAO.SELLER_ACCETP_SNAIL);
		} else {
			leftDay = DateUtil.skipDateTime(tradeRefundLogisticsDO.getSendDate(),RefundBaseAO.SELLER_ACCETP_EMS);
		}
		
		Date now = new Date();
		
		return leftDay.compareTo(now) < 0;
	}
	
	/**
	 * 买家退货，卖家确认收货超时的处理
	 * 
	 * @param refundId 退款id
	 * @param isSeller 为true表示卖家，否则表示买家
	 * @return result
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" })
	public Result sellerConfirmGoodsTimeout(Long refundId, Boolean isSeller){
		Result result = new ResultSupport();
		result.setSuccess(true);
		
		final RefundDO refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
		Date now = new Date();
		refundDO.setGmtModified(now);
		refundDO.setStateModified(now);
		refundDO.setRefundState(RefundDO.STATUS_CS_PROCESS);
		refundDO.setIsCustProcessed(RefundDO.CUST_PROCESS_YES);
		
		//更新数据
		getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					orderUpDateManager.upRefundState(refundDO.getOrderItemId(), refundDO.getRefundState());

					refundManager.updateRefundApplyInfo(refundDO);
				} catch (ManagerException e) {
					status.setRollbackOnly();
					log.error("Event=[SellerRefundTimeoutAOImpl.sellerConfirmGoodsTimeout() update refund status error, refund ID:"+refundDO.getId()+"]", e);
					return false;
				}
				return true;
			}});
		

		//设置页面跳转url
		String sellerConfirmGoodsTimeoutUrl = "";
		
		if(isSeller)
			sellerConfirmGoodsTimeoutUrl = "/refund/sellerViewRefundCustProcess.htm?id=" + refundId;
		else 
			sellerConfirmGoodsTimeoutUrl = "/refund/buyerViewCustProcessRefund.htm?id=" + refundId;
		
		result.setModel("sellerConfirmGoodsTimeoutUrl", sellerConfirmGoodsTimeoutUrl);
		
		return result;
	}
	
	public void setSellerRefundManageAO(SellerRefundManageAO sellerRefundManageAO) {
		this.sellerRefundManageAO = sellerRefundManageAO;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	public void setRefundManageAO(RefundManageAO refundManageAO) {
		this.refundManageAO = refundManageAO;
	}
	
	public void setRefundCheckManager(RefundCheckManager refundCheckManager) {
		this.refundCheckManager = refundCheckManager;
	}

	public void setTenPlatformRefundAO(TenPlatformRefundAO tenPlatformRefundAO) {
		this.tenPlatformRefundAO = tenPlatformRefundAO;
	}

	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}
	
	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}

}
