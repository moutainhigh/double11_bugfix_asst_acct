package com.yuwang.pinju.core.trade.ao.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.tenpay.ScriptClientResponseHandler;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.RefundConstant;
import com.yuwang.pinju.core.refund.manager.TradeRefundManualManager;
import com.yuwang.pinju.core.trade.ao.TenPlatRefundTimingAO;
import com.yuwang.pinju.core.trade.ao.TenPlatformRefundAO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;

public class TenPlatRefundTimingAOImpl extends PlatformRefundBaseAO implements TenPlatRefundTimingAO {
	
	private TradeRefundManualManager tradeRefundManualManager;
	private TenPlatformRefundAO tenPlatformRefundAO;
	/**
	 * 1. 获取所有的手工, 2. 组装请求参数  3.发起请求 
	 * 4.获取返回结果   5. 处理 订单 和子订单 和退款状态
	 *   6. 处理退款日志状态  7. 处理手工单 退款状态
	 */
	public void platformRefund() {
		try {
			// 获取所有没有退款的手工单
			log.info("Event=[TenPlatRefundTimingAOImpl.platformRefund() start, start time:  "+new Date().toString()+"]");
			List<TradeRefundManualDO> listManualDO = tradeRefundManualManager.selectAllTradeRefundManualNotRefund((Long.valueOf(TradeRefundManualDO.STATUS_PAYBACK_NO)));
			if (listManualDO != null && listManualDO.size() > 0) {
				for (TradeRefundManualDO refundManualDO : listManualDO) {
					try {
						PlatformRefundParamDO sendParamDO = this.createReSendParams(refundManualDO);
						Result result = new ResultSupport();
						result = tenPlatformRefundAO.createPlatformRefundParam(sendParamDO);
						if (result.isSuccess()) {
							String postUrl = (String) result.getModel("refundParamUrl");
							this.platformRefundSendRequestForTiming(postUrl);
						}
					} catch (Exception e) {
						log.error("Event=[TenPlatRefundTimingAOImpl.platformRefund() process refund manual exception]",e);
						continue;
					}
				}
			}
		} catch (ManagerException e) {
			log.error("Event=[TenPlatRefundTimingAOImpl.platformRefund() process refund manual exception]",e);
		}
		log.info("Event=[TenPlatRefundTimingAOImpl.platformRefund() end, end time: "+new Date().toString()+"]");
	}

	/**
	 * 创建 发起退款的参数
	 * 
	 * @param refundManualDO
	 * @return
	 */
	private PlatformRefundParamDO createReSendParams(TradeRefundManualDO refundManualDO) {
		PlatformRefundParamDO sendParamDO = new PlatformRefundParamDO();
		sendParamDO.setTransactionId(refundManualDO.getOrderTenpayId()); // 财付通交易单号
		sendParamDO.setTotalFee(refundManualDO.getRealPayMentamount()); // 商品金额,以分为单位
		sendParamDO.setRefundFee(refundManualDO.getPlatformRefundAmount());
		sendParamDO.setOrderPayId(refundManualDO.getOrderPayId());
		sendParamDO.setRefundId(refundManualDO.getOrderRefundId());
		return sendParamDO;
	}

	/**
	 * 发起退款请求 并处理业务逻辑
	 * 
	 * @param postUrl
	 */
	@SuppressWarnings("unchecked")
	private void platformRefundSendRequestForTiming(String postUrl) {
		// 通信对象
		TenpayHttpClient httpClient = new TenpayHttpClient();
		ScriptClientResponseHandler resHandler = new ScriptClientResponseHandler();
		try {
			// httpClient.setCharset(PinjuConstant.TENPAY_DIRECTPAY_INPUT_CHARSET);
			httpClient.setReqContent(postUrl);
			httpClient.setCaInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(RefundConstant.CACERT_NAME)));
			// 设置个人(商户)证书
			httpClient.setCertInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(RefundConstant.CERTIFICATE_NAME)),PinjuConstant.TENPAY_PAY_PARTNER);
			// httpClient.setTimeOut(60); //设置超时时间 60秒
			// 应答对象
			if (httpClient.call()) {
				// 设置结果参数
				String resContent = httpClient.getResContent();
				resHandler.setContent(resContent);
				// 获取返回参数
				PlatformRefundParamDO receiveParamDO = tenPlatformRefundAO.getReceiveParamDO(resHandler);

				// 获取返回参数
				String pay_result = resHandler.getParameter("pay_result");
				String sign = resHandler.getParameter("sign");
				SortedMap<String, String> allParameters = resHandler.getAllParameters();
				// 判断验签
				if (isTenState(pay_result) && verifySignByGbk(allParameters, sign)) {
					Result result =tenPlatformRefundAO.platformRefundNotifyForTiming(receiveParamDO);
					if (!result.isSuccess()) {
						log.error("TenPlatRefundTimingAOImpl.platformRefundSendRequest() error ".concat(receiveParamDO.getRefundId()));
					}
				} else {
					log.error("pay_result:"+ resHandler.getParameter("pay_result")+ " pay_info:"+ resHandler.getParameter("pay_info")+ "##验证签名失败或业务错误");
				}

			} else {
				// 有可能因为网络原因，请求已经处理，但未收到应答。
				log.error(httpClient.getResponseCode() + ":"+ httpClient.getErrInfo().concat("后台调用通信失败"));
			}
		} catch (Exception e) {
			log.error("Event=[TenPlatRefundTimingAOImpl.platformRefund() Exception] ", e);
		}
	}
	
	/**
	 * 判断返回的结果
	 * @param payResult
	 * @return
	 */
	private boolean isTenState(String payResult) {
		// TODO Auto-generated method stub
		return StringUtils.equalsIgnoreCase(payResult,PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
	}

	public void setTradeRefundManualManager(
			TradeRefundManualManager tradeRefundManualManager) {
		this.tradeRefundManualManager = tradeRefundManualManager;
	}

	public void setTenPlatformRefundAO(TenPlatformRefundAO tenPlatformRefundAO) {
		this.tenPlatformRefundAO = tenPlatformRefundAO;
	}
}
