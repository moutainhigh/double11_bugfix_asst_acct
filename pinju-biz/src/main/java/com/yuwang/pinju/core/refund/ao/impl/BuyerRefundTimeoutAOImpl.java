package com.yuwang.pinju.core.refund.ao.impl;
import java.util.Date;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.RefundResultCode;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.refund.ao.BuyerRefundTimeoutAO;
import com.yuwang.pinju.core.refund.ao.RefundBaseAO;
import com.yuwang.pinju.core.refund.ao.RefundManageAO;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.DateUtil;
import com.yuwang.pinju.domain.refund.RefundDO;

public class BuyerRefundTimeoutAOImpl extends BaseAO implements BuyerRefundTimeoutAO{
	
	private RefundManageAO refundManageAO;
	private RefundManager refundManager;
	private OrderUpDateManager orderUpDateManager;

	/**
	 * <p>检查是否卖家拒绝退款，买家5天超时</p>
	 * @param refundDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-4
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public Boolean isBuyerNoReplyTimeout(RefundDO refundDO) {
		Date outDate = DateUtil.skipDateTime(refundDO.getStateModified(),RefundBaseAO.SELLER_REF_REFUND);
		return new Date().compareTo(outDate) > 0;
	}

	 /**
	 * <p>Description: 卖家同意退款，而买家在5天内不退还商品。
	 * 1.关闭该退款 和子订单的退款状态</p>
	 * @param refundDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-4
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public Boolean isBuyerNotReturnGoods(RefundDO refundDO) {
		Date outDate = DateUtil.skipDateTime(refundDO.getStateModified(),RefundBaseAO.BUYER_RETURN_GOODS);
		return new Date().compareTo(outDate) > 0;
	}
	
	/**
	 * 卖家拒绝退款，买家响应超时的处理
	 * 
	 * @param refundId 退款id
	 * @param isBuyer 为true表示买家，否则表示卖家
	 * @return result
	 */
	public Result buyerReplyTimeout(Long refundId, Boolean isBuyer) {
		Result result = new ResultSupport();
		try{
			//关闭退款
			closeRefund(refundId);
			
			//设置页面跳转URL
			String buyerReplyTimeoutUrl = "";
			
			if(isBuyer)
				buyerReplyTimeoutUrl = "/refund/buyerViewRefundClosed.htm?id=" + refundId;
			else 
				buyerReplyTimeoutUrl = "/refund/sellerViewRefundClosed.htm?id=" + refundId;
			
			result.setModel("buyerReplyTimeoutUrl", buyerReplyTimeoutUrl);
			
			result.setResultCode(RefundResultCode.BUYER_NOTREPLY_TIMEOUT);
			return result;

		}catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[BuyerRefundTimeoutAOImpl#buyerReplyTimeout]", e);
		}
		return result;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	private void closeRefund(Long refundId) throws ManagerException{
		//关闭退款
		final RefundDO refundDO = refundManager.loadRefund(refundId);
		Date now = new Date();
		refundDO.setRefundState(RefundDO.STATUS_CLOSED);
		refundDO.setStateModified(now);
		refundDO.setGmtModified(now);
		
		//更新数据
		getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					refundManager.updateRefundApplyInfo(refundDO);
					
					orderUpDateManager.upRefundState(refundDO.getOrderItemId(), RefundDO.STATUS_CLOSED);
				} catch (ManagerException e) {
					status.setRollbackOnly();
					log.error("Event=[BuyerRefundTimeoutAOImpl.buyerReplyTimeout() update refund status error, refund ID:"+refundDO.getId()+"]", e);
					return false;
				}
				return true;
			}});

		//结束退款
		refundManageAO.endRefund(refundDO);
	}
	
	/**
	 * 卖家同意退款，而买家在5天内不退还商品的超时处理
	 * 
	 * @param refundId 退款id
	 * @param isBuyer 为true表示买家，否则表示卖家
	 * @return result
	 */
	public Result buyerNotReturnGoods(Long refundId, Boolean isBuyer){
		Result result = buyerReplyTimeout(refundId, isBuyer);
		result.setResultCode(RefundResultCode.BUYER_NOT_RETURN_GOODS_TIMEOUT);
		return result;
	}
	
	public void setRefundManageAO(RefundManageAO refundManageAO) {
		this.refundManageAO = refundManageAO;
	}

	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}

	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}

}
