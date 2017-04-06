package com.yuwang.pinju.core.trade.ao.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.Constant.RightsConstant;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.tenpay.BaseSplitRequestHandler;
import com.yuwang.pinju.core.common.tenpay.ScriptClientResponseHandler;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.refund.manager.TradeRefundManualManager;
import com.yuwang.pinju.core.rights.manager.RightsWorkOrderManager;
import com.yuwang.pinju.core.trade.ao.TenSplitRollBackTimingAO;
import com.yuwang.pinju.core.trade.manager.RefundLogManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;

public class TenSplitRollBackTimingAOImpl extends TenPayAbstractBaseAO implements TenSplitRollBackTimingAO {

	private RightsWorkOrderManager rightsWorkOrderManager;
	private VouchQueryManager vouchQueryManager;
	private TradeRefundManualManager tradeRefundManualManager;
	private RefundLogManager refundLogManager;
	
	/**
	 *  CA证书名
	 */
	public final static String CACERT_NAME = PinjuConstant.TENPAY_CACERT_NAME;
	/**
	 * 个人(商户)证书
	 */
	public final static String CERTIFICATE_NAME = PinjuConstant.TENPAY_PAY_PARTNER.concat(".pfx");
	
	//版本号
	private final String VERSION = PinjuConstant.TENPAY_SEARCHORDER_VERSION;
	//业务代码，查询96
	private final String CMDNO = PinjuConstant.TENPAY_SEARCHORDER_CMDNO;
	//回调地址
	private final String RETURNURL = PinjuConstant.TENPAY_SPLITREFUND_URL;
	//查询订单请求地址
	private final String REQUESTURL = PinjuConstant.TENPAY_SEARCHORDER_SEARCHURL;
	private final String TENPAY_PAY_PARTNER =PinjuConstant.TENPAY_PAY_PARTNER;
	
	public void splitRollbackRefund() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bizType", RightsConstant.BIZ_TYPE_RREFUND);
		map.put("status", RightsConstant.STATUS_HANDLE_NOT);
		try {
			List<FinanceWorkOrderDO> financeList = rightsWorkOrderManager.queryRightsWorkOrderDOByFail(map);
			for (FinanceWorkOrderDO financeWorkOrderDO : financeList) {
				String date = DateUtil.formatDate("yyyyMMdd", financeWorkOrderDO.getGmtCreate());
				VouchPayDO orderPay = new VouchPayDO();
				orderPay.setOrderId(financeWorkOrderDO.getOrderId());
				VouchPayDO reOrderPay = vouchQueryManager.selectOrderPay(orderPay);
				
				
				TenpayHttpClient httpClient = new TenpayHttpClient();
				httpClient.setCaInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(CACERT_NAME)));
				httpClient.setCertInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(CERTIFICATE_NAME)), PinjuConstant.TENPAY_PAY_PARTNER);
				
				ScriptClientResponseHandler resHandler = new ScriptClientResponseHandler();
				
				Result result = createQueryOrderParam(reOrderPay.getOrderPayId(),date);
				String postUrl = (String) result.getModel("parametersUrl");
				httpClient.setReqContent(postUrl);
				
				if (httpClient.call()) {
					resHandler.setContent(httpClient.getResContent());
					String splitRollbackStatus = resHandler.getParameter("bus_split_refund_args");
					//调试
//					System.err.println("pay_result:"+resHandler.getParameter("pay_result"));
//					System.err.println("pay_info:"+resHandler.getParameter("pay_info"));
					
					String sign = resHandler.getParameter("sign");
					SortedMap<String, String> allParameters = resHandler.getAllParameters();
					
					if (isTenState(resHandler.getParameter("pay_result")) && verifySignByGbk(allParameters, sign)) {
						if(!StringUtil.isEmpty(splitRollbackStatus)) {
							TradeRefundManualDO refundManualDO = new TradeRefundManualDO();
							refundManualDO.setBuyerId(financeWorkOrderDO.getBuyerId());
							refundManualDO.setBuyerNick(financeWorkOrderDO.getBuyerNick());
							refundManualDO.setGmtCreate(new Date());
							refundManualDO.setGmtModified(new Date());
							refundManualDO.setOrderId(financeWorkOrderDO.getOrderId());
							refundManualDO.setPlatformRefundAmount(financeWorkOrderDO.getDeMargin());
							refundManualDO.setRefundState(TradeRefundManualDO.STATUS_PAYBACK_NO);
							refundManualDO.setSellerId(financeWorkOrderDO.getSellerId());
							refundManualDO.setSellerNick(financeWorkOrderDO.getSellerNick());
							refundManualDO.setSellerShopId(financeWorkOrderDO.getShopId());
							refundManualDO.setOrderPayId(reOrderPay.getOrderPayId());
							refundManualDO.setOrderTenpayId(reOrderPay.getOrderPayId());
							
							RefundLogDO refundLogDO = refundLogManager.queryRefundLog(reOrderPay.getOutPayId(), reOrderPay.getOrderPayId(), Integer.parseInt(PinjuConstant.TENPAY_SPLITREFUND_CMDNO));
							
							refundManualDO.setOrderRefundId(refundLogDO.getRefundId());
							refundManualDO.setRealPayMentamount(reOrderPay.getRealPayMentamount());
							refundManualDO.setMemo("分账回退手工单失败,财付通已成功");
							
							//插入平台退款工单表
							tradeRefundManualManager.saveRefundManual(refundManualDO);
							
							//关闭退款工单
							financeWorkOrderDO.setStatus(RightsConstant.STATUS_HANDLE_CLOSE);
							rightsWorkOrderManager.updateRightsWorkOrderDO(financeWorkOrderDO);
						}
					}
				}
				
			}
		} catch (ManagerException e) {
			log.error("TenSplitRollBackTimingAOImpl#splitRollbackRefund 定时任务处理工单时查询错误：",e);
		} catch (Exception e) {
			log.error("TenSplitRollBackTimingAOImpl#splitRollbackRefund 定时任务处理工单时请求异常：",e);
		}
		
	}

	protected Result createQueryOrderParam(String transactionId, String date) {
		Result result = new ResultSupport();
		try {
			BaseSplitRequestHandler reqHandler = new BaseSplitRequestHandler(null,null);
			reqHandler.init();
			reqHandler = createParameters(transactionId, date, reqHandler);
			String parametersUrl = reqHandler.getRequestURL();
			result.setModel("parametersUrl", parametersUrl);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	protected BaseSplitRequestHandler createParameters(String transactionId, String date, BaseSplitRequestHandler reqHandler) {
		reqHandler.setParameter("cmdno", CMDNO);
		reqHandler.setParameter("date", date);
		reqHandler.setParameter("bargainor_id", TENPAY_PAY_PARTNER);			
		reqHandler.setParameter("transaction_id", transactionId);		
		reqHandler.setParameter("sp_billno", transactionId);				
		reqHandler.setParameter("version", VERSION);
		reqHandler.setParameter("return_url", RETURNURL);
		reqHandler.setKey(getMD5Key());
		reqHandler.setGateUrl(REQUESTURL);
		return reqHandler;
	}
	
	protected boolean isTenState(String payResult) {
		return StringUtils.equalsIgnoreCase(payResult,PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
	}
	
	public void setRightsWorkOrderManager(
			RightsWorkOrderManager rightsWorkOrderManager) {
		this.rightsWorkOrderManager = rightsWorkOrderManager;
	}

	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}

	@Override
	protected String getMD5Key() {
		return PinjuConstant.TENPAY_PAY_MD5KEY;
	}

	public void setTradeRefundManualManager(
			TradeRefundManualManager tradeRefundManualManager) {
		this.tradeRefundManualManager = tradeRefundManualManager;
	}

	public void setRefundLogManager(RefundLogManager refundLogManager) {
		this.refundLogManager = refundLogManager;
	}

}
