package com.yuwang.pinju.web.module.refund.action;

import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.web.message.RefundMessage;
import com.yuwang.pinju.web.message.RefundMessageName;
import com.yuwang.pinju.web.module.BaseAction;

@SuppressWarnings("serial")
public class RefundBaseAction extends BaseAction implements RefundMessageName{
	/**
	 * 错误信息
	 */
	protected String errorMessage;
	
	/**
	 * 要跳转的地址
	 */
	protected String returnUrl;
	
	
	protected static Map<Integer, String> buyerRefundUrlMap = new HashMap<Integer, String>();
	static{
		buyerRefundUrlMap.put(RefundDO.STATUS_WAIT_SELLER_AGREE, "/refund/checkApply.htm");
		buyerRefundUrlMap.put(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS, "/refund/buyerViewWaitGoodsReturn.htm");
		buyerRefundUrlMap.put(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS, "/refund/buyerWaitSellerDeliveryGoods.htm");
		buyerRefundUrlMap.put(RefundDO.STATUS_CLOSED, "/refund/buyerViewRefundClosed.htm");
		buyerRefundUrlMap.put(RefundDO.STATUS_SUCCESS, "/refund/buyerViewRefundSuccess.htm");
		buyerRefundUrlMap.put(RefundDO.STATUS_SELLER_REFUSE_BUYER, "/refund/buyerViewSellerRefuseRefund.htm");
		buyerRefundUrlMap.put(RefundDO.STATUS_CS_PROCESS, "/refund/buyerViewCustProcessRefund.htm");
		buyerRefundUrlMap.put(RefundDO.STATUS_REFUND_FAIL, "/refund/buyerViewRefundFail.htm");
		buyerRefundUrlMap.put(RefundDO.STATUS_REFUND_PROTOCAL_AGREE, "/refund/buyerViewRefundProtocalAgree.htm");
	}
	
	protected static Map<Integer, String> sellerRefundUrlMap = new HashMap<Integer, String>();
	
	static{
		sellerRefundUrlMap.put(RefundDO.STATUS_WAIT_SELLER_AGREE, "/refund/sellerCheck.htm");
		sellerRefundUrlMap.put(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS, "/refund/sellerViewWaitGoodsReturn.htm");
		sellerRefundUrlMap.put(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS, "/refund/sellerViewConfirmGoods.htm");
		sellerRefundUrlMap.put(RefundDO.STATUS_CLOSED, "/refund/sellerViewRefundClosed.htm");
		sellerRefundUrlMap.put(RefundDO.STATUS_SUCCESS, "/refund/sellerViewRefundSuccess.htm");
		sellerRefundUrlMap.put(RefundDO.STATUS_SELLER_REFUSE_BUYER, "/refund/viewSellerReject.htm");
		sellerRefundUrlMap.put(RefundDO.STATUS_CS_PROCESS, "/refund/sellerViewRefundCustProcess.htm");
		sellerRefundUrlMap.put(RefundDO.STATUS_REFUND_FAIL, "/refund/sellerViewRefundFail.htm");
		sellerRefundUrlMap.put(RefundDO.STATUS_REFUND_PROTOCAL_AGREE, "/refund/sellerViewRefundProtocalAgree.htm");
	}
	
	
	/**
	 * <p>Description: 当前状态发生变化的时候 添加相应的提示信息	  </p>
	 * @param refundDO
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-6
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void buyerTipMessage(RefundDO refundDO,String messageName){
		if(refundDO != null){
		String status=RefundDO.getRefundStateMap().get(refundDO.getRefundState());
		String pageUrl=buyerRefundUrlMap.get(refundDO.getRefundState());
		this.errorMessage =RefundMessage.getMessage(messageName, status);
		this.returnUrl =pageUrl.concat("?id=").concat(refundDO.getId().toString());
		}
	}
	
	public void sellerTipMessage(RefundDO refundDO,String messageName){
		if(refundDO != null){
		String status=RefundDO.getRefundStateMap().get(refundDO.getRefundState());
		String pageUrl=sellerRefundUrlMap.get(refundDO.getRefundState());
		this.errorMessage =RefundMessage.getMessage(messageName, status);
		this.returnUrl =pageUrl.concat("?id=").concat(refundDO.getId().toString());
		}
	}
	
	/**
	 * <p>Description: 退款error Message的提示</p>
	 * @param refundDO      当前退款 数据
	 * @param errorMsg		要提示的error Message
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 *//*
	public void addTimMessage(RefundDO refundDO,String errorMsg){
		if(refundDO != null){
			String pageUrl=buyerRefundUrlMap.get(refundDO.getRefundState());
			this.errorMessage =errorMsg;
			this.returnUrl =pageUrl.concat("?id=").concat(refundDO.getId().toString());
		}
	}*/
	
	/**
	 * Method description TODO
	 * @param refundDO
	 * @author:[MaYuanChao]
	 * @version $Revision$
	 * @create:2011-11-21
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void buyerReturnNewStatePage(RefundDO refundDO){
		if(refundDO != null){
			String pageUrl=buyerRefundUrlMap.get(refundDO.getRefundState());
			this.returnUrl =pageUrl.concat("?id=").concat(refundDO.getId().toString());
		}
	}
	/**
	 * <p>Description: String转换成Long</p>
	 * @param val
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public  Long createLong(String val){
		Long _val = 0L;
		try {
			_val = Long.valueOf(val);
		} catch (NumberFormatException e) {
			log.error("PlatformRefundBaseAO.createLong() error", e);
			_val = 0L;
		}
		return _val;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
		
	public String getReturnUrl() {
		return returnUrl;
	}
}
