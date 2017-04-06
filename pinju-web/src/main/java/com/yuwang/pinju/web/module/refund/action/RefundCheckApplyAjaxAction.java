package com.yuwang.pinju.web.module.refund.action;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;

public class RefundCheckApplyAjaxAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(RefundCheckApplyAjaxAction.class);
	private TradeRefundLogisticsDO tradeRefundLogisticsDO;
	private Long refundId;
	private RefundApplyAO refundApplyAO;
	
    private String logisticsId;
	private String logisticsName;
    private String logisticsNumber;
    private String buyerMemo;
    private String message;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * Created on 2011-8-18
	 * @see
	 * <p>Description: 
	 * 	更新退款物流信息	 
	 * </p>
	 * @param 
	 * @return SUCCESS
	 * @throws
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String updateGoodsWuliuInfo(){
		if(log.isInfoEnabled()){
			log.info("Entry: RefundCheckApplyAction.updateGoodsWuliuInfo()");
		}
		try{
			tradeRefundLogisticsDO = refundApplyAO.getRefundLogistics(refundId);
			if(tradeRefundLogisticsDO == null) {
				log.info("tradeRefundLogisticsDO is null");
				return ERROR;
			}
			tradeRefundLogisticsDO.setBuyerMemo(buyerMemo);
			tradeRefundLogisticsDO.setLogisticsId(logisticsId);
			/*LogisticsCorpDO logisticsCorpDO=new LogisticsCorpDO();
			logisticsCorpDO.setCorpCode(logisticsId);
			logisticsCorpDO=logisticsCorpAO.getLogisticsCorpByStatus(logisticsCorpDO).get(0);
			if(logisticsCorpDO !=null) tradeRefundLogisticsDO.setLogisticsName(logisticsCorpDO.getCorpName());*/
			Date now = new Date();
			tradeRefundLogisticsDO.setLogisticsName(logisticsName);
			//tradeRefundLogisticsDO.setConfirmDate(now);
			//tradeRefundLogisticsDO.setSendDate(tradeRefundLogisticsDO.getGmtCreate());
			tradeRefundLogisticsDO.setGmtModified(now);
			tradeRefundLogisticsDO.setLogisticsNumber(logisticsNumber);
			refundApplyAO.updateRefundLogistics(tradeRefundLogisticsDO);
			message="1";
		}catch (Exception e) {
			// TODO: handle exception
			log.error("RefundCheckApplyAction.updateGoodsWuliuInfo() occurs error :"+e);
			message="0";
			return ERROR;
		}
		return SUCCESS;
	}

	public void setTradeRefundLogisticsDO(
			TradeRefundLogisticsDO tradeRefundLogisticsDO) {
		this.tradeRefundLogisticsDO = tradeRefundLogisticsDO;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber;
	}

	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}

	public TradeRefundLogisticsDO getTradeRefundLogisticsDO() {
		return tradeRefundLogisticsDO;
	}

	public Long getRefundId() {
		return refundId;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
