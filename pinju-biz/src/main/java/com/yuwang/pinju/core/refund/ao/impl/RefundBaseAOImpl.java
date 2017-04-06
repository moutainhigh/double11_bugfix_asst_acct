package com.yuwang.pinju.core.refund.ao.impl;

import java.util.Date;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.RefundBaseAO;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.DateUtil;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/** <p>Discription: 	  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-10-14
 */
public class RefundBaseAOImpl extends BaseAO implements RefundBaseAO {
	 private LogisticsCorpAO logisticsCorpAO;
	 private RefundApplyAO refundApplyAO;
	 private VouchQueryManager vouchQueryManager;
	@Override
	public Date getOvertimes(RefundDO refundDO) {
		// TODO Auto-generated method stub
		Date outDate = null; 
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS) == 0) {
			 TradeRefundLogisticsDO tradeRefundLogisticsDO = refundApplyAO.getRefundLogistics(refundDO.getId());
			LogisticsCorpDO logisticsCorpDO = new LogisticsCorpDO();
			logisticsCorpDO.setCorpCode(tradeRefundLogisticsDO.getLogisticsId());
			logisticsCorpDO = logisticsCorpAO.getLogisticsCorpByStatus(logisticsCorpDO).get(0);
			// 1 是平邮 2,3是快递
			if (logisticsCorpDO.getLogisticsType().compareTo(1L) == 0) {
				outDate = DateUtil.skipDateTime(tradeRefundLogisticsDO.getSendDate(),SELLER_ACCETP_SNAIL);
			} else {
				outDate = DateUtil.skipDateTime(tradeRefundLogisticsDO.getSendDate(),SELLER_ACCETP_EMS);
			}
		}
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS)==0){
			outDate =DateUtil.skipDateTime(refundDO.getStateModified(), BUYER_RETURN_GOODS);
		}
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_SELLER_REFUSE_BUYER) == 0) {
			outDate = DateUtil.skipDateTime(refundDO.getReplyDate(),SELLER_REF_REFUND);
		}
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE)==0){
			outDate = DateUtil.skipDateTime(refundDO.getApplyDate(), WAIT_SELL_ARGEE);
		}
		return outDate == null ? refundDO.getStateModified() : outDate;
	}
	
	public void setLogisticsCorpAO(LogisticsCorpAO logisticsCorpAO) {
		this.logisticsCorpAO = logisticsCorpAO;
	}
	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	@Override
	public String getRealPayMentamount(Long orderId) {
		// TODO Auto-generated method stub
		  Long realPayPrice = 0L;
		try {
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderId(orderId);
			if(vouchPayDO != null) realPayPrice = vouchPayDO.getRealPayMentamount();
		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			log.error("Event=RefundBaseAOImpl.getRealPayMentamount()##获取实付价格失败", e);
		}
		
		if(EmptyUtil.isBlank(realPayPrice)){
			log.error("Event=RefundBaseAOImpl.getRealPayMentamount()## 实付金额为空， OrderId ="+ orderId );
			return MoneyUtil.getCentToDollar(0L);
		}
		return MoneyUtil.getCentToDollar(realPayPrice);
	}

	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}
}


