package com.yuwang.pinju.web.module.refund.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * <p>用于验证当前退款信息是否是用户自己的退款信息</p>
 * @author shihongbo
 * @update By MaYuanChao @ 2011-10-12
 */
public class RefundUserInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 5710432978978338886L;
	
	private final static Log log = LogFactory.getLog(RefundUserInterceptor.class);

	private final static String INVALID_SELLER_REFUND = "invalidSellerRefund";
	private final static String INVALID_BUYER_REFUND ="invalidBuyerRefund";
	
	public RefundUserInterceptor() {}

	private RefundApplyAO refundApplyAO;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		log.debug("execute RefundUserInterceptor check");
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		Long masterMemberId = login.getMasterMemberId();
		
		Object  action = invocation.getAction();

		if(!(action instanceof SellerRefund) && 
				!(action instanceof BuyerRefund))
			return invocation.invoke();
		
		if(action instanceof SellerRefund){
			RefundDO refundDO = null;
			
			SellerRefund sellerRefund = (SellerRefund)action;
			Long refundId = sellerRefund.getRefundId();
			Long orderItemId = sellerRefund.getOrderItemId();
			
			if(refundId != null)
				refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			else if(orderItemId != null)
				refundDO = refundApplyAO.loadRefundByOrderItem(orderItemId);
				
				
			if(refundDO != null){
				if(refundDO.getSellerId().compareTo(masterMemberId) != 0){
					return INVALID_SELLER_REFUND;
				}
			}
		}else if(action instanceof BuyerRefund){
			RefundDO refundDO = null;
			
			BuyerRefund buyerRefund = (BuyerRefund)action;
			Long refundId = buyerRefund.getRefundId();
			Long orderItemId = buyerRefund.getOrderItemId();
			
			if(refundId != null)
				refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			else if(orderItemId != null)
				refundDO = refundApplyAO.loadRefundByOrderItem(orderItemId);
			
			if(refundDO != null){
				if(refundDO.getBuyerId().compareTo(masterMemberId) != 0){
					return INVALID_BUYER_REFUND;
				}
			}
		}
		
		return invocation.invoke();
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}
}
