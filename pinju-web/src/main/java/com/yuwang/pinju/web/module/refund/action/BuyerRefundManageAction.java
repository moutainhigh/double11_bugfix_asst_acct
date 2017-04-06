package com.yuwang.pinju.web.module.refund.action;

import java.util.Date;

import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.refund.ao.BuyerRefundLogAO;
import com.yuwang.pinju.core.refund.ao.BuyerRefundTimeoutAO;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.RefundManageAO;
import com.yuwang.pinju.core.refund.ao.SellerRefundTimeoutAO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;

/**
 * <p>Description:管理买家所有操作</p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-8-26
 */
public class BuyerRefundManageAction extends RefundBaseAction  implements BuyerRefund{
	private static final long serialVersionUID = -2424240695169613620L;
	private Long refundId;
	private RefundApplyAO refundApplyAO;
	
	private RefundDO refundDO;
	private String logisticsId;
    private String logisticsNumber;
    private String buyerMemo;
    
    private LogisticsCorpAO logisticsCorpAO;

    private RefundManageAO refundManageAO;
    private BuyerRefundTimeoutAO buyerRefundTimeoutAO;
    private SellerRefundTimeoutAO sellerRefundTimeoutAO;
    private BuyerRefundLogAO buyerRefundLogAO;


	private String buyerReplyTimeoutUrl;
	private String sellerReplyTimeoutUrl;


	/**
     * <p>Description:买家退还货物</p>
     * @return SUCCESS
     * @author:[MaYuanChao]
     * @version 1.0
     * @create:2011-8-26
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
	public String buyerDeliverGoods() {
		// 买家保存物流信息，更改退款状态和 订单状态
		try {
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS) != 0){
				buyerTipMessage(refundDO,REFUND_DEFAULT_MESSAGE);
				return INPUT;
			}
			
			//判断是否超时   卖家同意退款，而买家在5天内不退还商品 超时  -- 关闭该退款(买家)
			Boolean isBuyerReplyTimeout = buyerRefundTimeoutAO.isBuyerNotReturnGoods(refundDO);
			if(isBuyerReplyTimeout){
				Result timeoutResult = buyerRefundTimeoutAO.buyerNotReturnGoods(refundId, true);
				if(timeoutResult.isSuccess()){
					buyerReplyTimeoutUrl = (String)timeoutResult.getModel("buyerReplyTimeoutUrl");
					if(StringUtils.isNotBlank(timeoutResult.getResultCode())){
						this.errorMessage = ResultCodeMsg.getResultMessage(timeoutResult.getResultCode());
					}
					return BUYER_REPLY_TIMEOUT;
				}
			}
			
			TradeRefundLogisticsDO logisticsDO = refundApplyAO.getRefundLogistics(refundId);
			if (logisticsDO == null) logisticsDO = new TradeRefundLogisticsDO();
			logisticsDO.setRefundId(refundId);
			logisticsDO.setBuyerId(refundDO.getBuyerId());
			logisticsDO.setSellerId(refundDO.getSellerId());
			logisticsDO.setBuyerMemo(buyerMemo);
			logisticsDO.setLogisticsId(logisticsId);
			LogisticsCorpDO logisticsCorpDO = new LogisticsCorpDO();
			logisticsCorpDO.setCorpCode(logisticsId);
			logisticsCorpDO = logisticsCorpAO.getLogisticsCorpByStatus(logisticsCorpDO).get(0);
			if (logisticsCorpDO != null) {
				logisticsDO.setLogisticsName(logisticsCorpDO.getCorpName());
				logisticsDO.setLogisticsType(logisticsCorpDO.getLogisticsType());
			}else{
				logisticsDO.setLogisticsType(2L);
			}
			logisticsDO.setSendDate(new Date());
			logisticsDO.setGmtCreate(new Date());
			logisticsDO.setGmtModified(new Date());
			logisticsDO.setLogisticsState(1L);
			logisticsDO.setLogisticsNumber(logisticsNumber);
			refundApplyAO.buyerDeliverGoods(logisticsDO, refundDO);
			//refundApplyAO.writeOrderLog(refundDO, "" + ServletUtil.getRemoteNumberIp());
		} catch (Exception e) {
			log.error("Entry=[BuyerRefundManageAction.buyerDeliverGoods() occurs exception]", e);
			return INVALID_BUYER_REFUND;
		}
		return SUCCESS;
	}
	
	/**
	 * <p>Description: 	 买家申请客服介入</p>
	 * @return Action.SUCCESS
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-26
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String buyerApplyCustProcess(){
		if(log.isInfoEnabled()){
			log.info("execute BuyerRefundManageAction.buyerApplyCustProcess() buyer apply customer process");
		}
		try {
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			if(refundDO.getRefundState().compareTo(RefundDO.STATUS_SELLER_REFUSE_BUYER) != 0){
				buyerTipMessage(refundDO,REFUND_DEFAULT_MESSAGE);
				return INPUT;
			}
			//卖家拒绝退款，买家5天没反应  --- >退款关闭
			
			Boolean isBuyerReplyTimeout = buyerRefundTimeoutAO.isBuyerNoReplyTimeout(refundDO);
			if(isBuyerReplyTimeout){
				Result timeoutResult = buyerRefundTimeoutAO.buyerReplyTimeout(refundId, true);
				if(timeoutResult.isSuccess()){
					buyerReplyTimeoutUrl = (String)timeoutResult.getModel("buyerReplyTimeoutUrl");
					if(StringUtils.isNotBlank(timeoutResult.getResultCode())){
						this.errorMessage = ResultCodeMsg.getResultMessage(timeoutResult.getResultCode());
					}
					return BUYER_REPLY_TIMEOUT;
				}
			}
			
			//记录日志
			buyerRefundLogAO.buyerApplyCustProcess(refundId);
			
			refundApplyAO.custProcess(refundDO);
			//refundApplyAO.writeOrderLog(refundDO, "" + ServletUtil.getRemoteNumberIp());
		} catch (Exception e) {
			log.error("execute BuyerRefundManageAction.buyerApplyCustProcess()", e);
			return INVALID_BUYER_REFUND;
		}
		return SUCCESS;
	}
	
	/**
	 * <p>Description:买家取消退款申请</p>
	 * @return SUCCESS
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-26
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String buyerCancelRefundApply(){
		if(log.isInfoEnabled()){
			log.info("excute RefundCheckApplyAction.cancelRefundApply() start");
		}
		try{
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE) != 0){
				if(refundDO.getRefundState().compareTo(RefundDO.STATUS_SELLER_REFUSE_BUYER) != 0){
					buyerTipMessage(refundDO,REFUND_DEFAULT_MESSAGE);
					return INPUT;
				}
			} 
			
			Boolean isSellerReplyTimeout = sellerRefundTimeoutAO.isSellerReplyTimeout(refundDO);
			
			//买家申请，卖家超时
			if(isSellerReplyTimeout){
				Result timeoutResult = sellerRefundTimeoutAO.sellerReplyTimeout(refundId, false);
				if(timeoutResult.isSuccess()){
					sellerReplyTimeoutUrl = (String)timeoutResult.getModel("sellerReplyTimeoutUrl");
					//if(StringUtils.isNotBlank(timeoutResult.getResultCode())){
					//	this.errorMessage = ResultCodeMsg.getResultMessage(timeoutResult.getResultCode());
				//}
					return "sellerReplyTimeout";	
				}
			}
			
			refundApplyAO.cancelRefundApply(refundDO);
			//结束退款
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			refundManageAO.endRefund(refundDO);
			
			//refundApplyAO.writeOrderLog(refundDO, "" + ServletUtil.getRemoteNumberIp());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("excute RefundCheckApplyAction.cancelRefundApply() occurs error:"+e);
			return INVALID_BUYER_REFUND;
		}
		return SUCCESS;
	}
	
	
	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public RefundDO getRefundDO() {
		return refundDO;
	}

	public void setRefundDO(RefundDO refundDO) {
		this.refundDO = refundDO;
	}

	public String getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}

	public String getLogisticsNumber() {
		return logisticsNumber;
	}

	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber;
	}

	public String getBuyerMemo() {
		return buyerMemo;
	}

	public String getBuyerReplyTimeoutUrl() {
		return buyerReplyTimeoutUrl;
	}

	public String getSellerReplyTimeoutUrl() {
		return sellerReplyTimeoutUrl;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	public void setLogisticsCorpAO(LogisticsCorpAO logisticsCorpAO) {
		this.logisticsCorpAO = logisticsCorpAO;
	}


	public Long getRefundId() {
		if(getLong("id") != 0) return getLong("id");
		return refundId;
	}
	public Long getOrderItemId() {
		if(getLong("oid") != 0) return getLong("oid");
		return null;
	}
   
    public void setRefundManageAO(RefundManageAO refundManageAO) {
		this.refundManageAO = refundManageAO;
	}
    
	public void setBuyerRefundTimeoutAO(BuyerRefundTimeoutAO buyerRefundTimeoutAO) {
		this.buyerRefundTimeoutAO = buyerRefundTimeoutAO;
	}

    public void setSellerRefundTimeoutAO(SellerRefundTimeoutAO sellerRefundTimeoutAO) {
		this.sellerRefundTimeoutAO = sellerRefundTimeoutAO;
	}
    
	public void setBuyerRefundLogAO(BuyerRefundLogAO buyerRefundLogAO) {
		this.buyerRefundLogAO = buyerRefundLogAO;
	}
}