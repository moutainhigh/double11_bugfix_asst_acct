package com.yuwang.pinju.core.trade.ao.impl;

import java.io.File;
import java.util.SortedMap;

import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.RefundResultCode;
import com.yuwang.pinju.core.common.tenpay.BaseSplitRequestHandler;
import com.yuwang.pinju.core.common.tenpay.ScriptClientResponseHandler;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.RefundConstant;
import com.yuwang.pinju.core.constant.trade.TenpayResultCodeEnum;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.refund.manager.TradeRefundManualManager;
import com.yuwang.pinju.core.trade.ao.TenPlatformRefundAO;
import com.yuwang.pinju.core.trade.manager.PlatformRefundManager;
import com.yuwang.pinju.core.trade.manager.RefundLogManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.pay.PaySendLogDO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;

/**
 * <p>Description: 	财付通平台退款 支付 </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-8
 */
public class TenPlatformRefundAOImpl extends PlatformRefundBaseAO implements TenPlatformRefundAO {
	
	private OrderQueryManager orderQueryManager;
	private PlatformRefundManager platformRefundManager;
	private RefundLogManager refundLogManager;
	private VouchQueryManager vouchQueryManager;
	private TradeRefundManualManager tradeRefundManualManager;
	
	/**
	 * 1.封装退款支付参数   2.数字签名  3.生成请求地址  4.插入 支付发送日志
	 *   5.插入退款中间日志  6.提供请求地址和参数  7. 发送请求成功
	 */
	@Override
	public Result createPlatformRefundParam(PlatformRefundParamDO paramDO) {
		// TODO Auto-generated method stub
		Result result = new ResultSupport();
		BaseSplitRequestHandler reqHandler = new BaseSplitRequestHandler(null, null);
		try {
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderPayId(paramDO.getOrderPayId());
			
			 //判断改订单是否被支付：  已支付 101
		    if(vouchPayDO == null || vouchPayDO.getPayState() == VouchPayConstant.VOUCH_PAY_STATE_UNPAID){
		    	result.setResultCode(RefundResultCode.PLATFORM_REFUND_PAY_ORDER_NOTEXITS);
		    	result.setSuccess(false);
		    	return result;
		    }
		    
			//paramDO.setTotalFee(vouchPayDO.getRealPayMentamount());
			//paramDO.setOrderId(vouchPayDO.getOrderId());
			// 封装请求的参数
			createRefundParamDO(paramDO, vouchPayDO);
			
			//判断退款是否已经处理
			RefundLogDO refundLogDO = refundLogManager.queryRefundLog(paramDO.getTransactionId(),paramDO.getOrderPayId(), Integer.valueOf(REFUND_CMDNO));
			if(refundLogDO != null && refundLogDO.getRefundState().compareTo(RefundLogDO.REFUND_LOG_IS_SUCCESS) == 0){
				result.setSuccess(false);
				result.setResultCode(RefundResultCode.PLATFORM_REFUND_ALREADY);
				result.setModel("paramDO",paramDO);
				return result;
			}
			if(refundLogDO != null){
				paramDO.setRefundId(refundLogDO.getRefundId());
			}
			reqHandler.init();
			this.createParameters(paramDO,reqHandler);
			
			//生成财富通请求地址
			String	parametersUrl = reqHandler.getRequestURL();
			
			// 插入退款日志
			if(refundLogDO == null){
				refundLogDO = this.createRefundLogDO(paramDO,  reqHandler.getParameter("refund_id"), reqHandler.getRequestURL());  	// TODO  封装退款日志
				 //设置日志状态为  退款失败
				refundLogDO.setRefundState(RefundLogDO.REFUND_LOG_IS_FAIL);
				refundLogManager.insertTradeRefundLog(refundLogDO);
			}
			
			// 插入平台退款发送日志 
			PaySendLogDO paySendLogDO = this.createPaySendLogDO(vouchPayDO);
			paySendLogDO.setSendInfo(parametersUrl);
			this.getVouchCreateManager().insertTradePaySendLog(paySendLogDO);
			
			result.setModel("refundParamUrl", parametersUrl);
			result.setModel("paramDO",paramDO);
			result.setSuccess(true);
		} catch (ManagerException  e) {
			// TODO: handle exception
			result.setSuccess(false);
			result.setResultCode(RefundResultCode.PLATFORM_REFUND_EXCEPTION);
			log.error("Event=[TenPlatformRefundAOImpl.createPlatformRefundParam() create params occurs exception]", e);
			
		}catch(Exception e){
			result.setResultCode(RefundResultCode.PLATFORM_REFUND_EXCEPTION);
			result.setSuccess(false);
			log.error("Event=[TenPlatformRefundAOImpl.createPlatformRefundParam() create params occurs exception]", e);
		}
		return result;
	}
	
	
	
	/**
	 * 平台退款回调 : 财付通返回成功时的处理 -- 流程 :  1. 插入接受流水日志     2. 判断订单是否存在  3 . 判断是否被处理
	 * 4 . 更新子订单状态, 退款状态  , 退款中间日志表状态   5.成功
	 */
	@Override
	public Result platformRefundNotifySuccess(PlatformRefundParamDO refundParamDO) {
		Result result = new ResultSupport();
		try {
			// 插入回调接受日志
			this.createPayBackLog(refundParamDO);
			
			// 根据支付交易号 获取 父订单
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderPayId(refundParamDO.getOrderPayId());
			
			//判断支付订单是否存在
			result = this.validateRefund(refundParamDO, vouchPayDO);
			if(!result.isSuccess()){
				log.error("Event=[TenPlatformRefundAOImpl.validateRefund() Invalid Refund]");
				return result;
			}
			boolean isAll = false;
			if(refundParamDO.getRefundFee().compareTo(vouchPayDO.getRealPayMentamount()) ==0){
				isAll = true;
			}
			
			//更新 退款单状态 ，子主订单状态 , 退款日志状态 和插入 子订单修改插入日志
			result.setSuccess(platformRefundManager.updateAllRefundNotifyForSuccess(vouchPayDO.getOrderId(),isAll,"手工退款成功"));
		} catch (ManagerException e) {
			log.error("Event=[TenPlatformRefundAOImpl#platformRefundNotify]", e);
			result.setResultCode(RefundResultCode.PLATFORM_REFUND_EXCEPTION); // 退款失败
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 退款回调 ——> 财付通返回失败  不是指定的错误code 的处理流程  ——> 
	 * 1.插入接受日志 
	 * 2.判断订单是否存在 
	 * 3.判断是否被处理
	 * 4.手工插单
	 */
	@Override
	public Result platformRefundNotifyIsError(PlatformRefundParamDO paramDO) {

		Result result = new ResultSupport();
		try {
			// 插入回调接受日志   仅只有返回结果的时候  才 插入接受日志
			if(StringUtils.isNotBlank(paramDO.getPayResult())){
				this.createPayBackLog(paramDO);
			}
			// 根据支付交易号 获取 父订单
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderPayId(paramDO.getOrderPayId());
			// 验证订单，退款 是否被处理
			result = this.validateRefund(paramDO, vouchPayDO);
			if (!result.isSuccess()) {
				log.error("Event=[TenPlatformRefundAOImpl.validateRefund () Invalid Refund]");
				return result;
			}
			OrderDO orderDO = orderQueryManager.getOrderDOById(vouchPayDO.getOrderId());

			// 查询该手工单 ， 如果不存在  创建手工单对象 
			TradeRefundManualDO manualDO = tradeRefundManualManager.loadRefundManualByOrderId(vouchPayDO.getOrderId());
			if(manualDO == null){
				manualDO = this.createRefundManualDO(paramDO,orderDO,vouchPayDO);
			}
			boolean isAll = false;
			if(paramDO.getRefundFee().compareTo(vouchPayDO.getRealPayMentamount()) ==0){
				isAll = true;
			}
			// 更新状态和插入工单
			result.setSuccess(platformRefundManager.updateAllRefundNotifyForManual(vouchPayDO.getOrderId(), manualDO,isAll));
			result.setResultCode(RefundResultCode.PLATFORM_REFUND_FAIL);
		} catch (ManagerException e) {
			// TODO: handle exception
			log.error("Event=[TenPlatformRefundAOImpl.platformRefundNotifyIsError() occurs exception ]",e);
			result.setResultCode(RefundResultCode.PLATFORM_REFUND_EXCEPTION); // 退款出现异常
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * <p>Description: 验证退款是否合法</p>
	 * @param refundParamDO
	 * @param vouchPayDO
	 * @param result
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Result validateRefund(PlatformRefundParamDO refundParamDO,VouchPayDO vouchPayDO){
		Result result = new ResultSupport();
		try {
			//判断支付订单是否存在
			if (vouchPayDO == null|| vouchPayDO.getPayState().compareTo(VouchPayConstant.VOUCH_PAY_STATE_UNPAID) == 0) {
				result.setSuccess(false);
				result.setResultCode(RefundResultCode.PLATFORM_REFUND_PAY_ORDER_NOTEXITS); // 支付订单不存在
				return result;
			}
			
			//判断退款订单是否被处理
			RefundLogDO refundLogDO = refundLogManager.queryRefundLog(refundParamDO.getTransactionId(),refundParamDO.getOrderPayId(),Integer.valueOf(REFUND_CMDNO));
			if(refundLogDO != null && refundLogDO.getRefundState().compareTo(RefundLogDO.REFUND_LOG_IS_SUCCESS) == 0){
				result.setSuccess(false);
				result.setResultCode(RefundResultCode.PLATFORM_REFUND_ALREADY); // 该退款已经处理
				return result;
			}
			result.setSuccess(true);
		} catch (ManagerException e) {
			result.setResultCode(RefundResultCode.PLATFORM_REFUND_EXCEPTION); // 该退款异常
			log.error("Event=[TenPlatformRefundAOImpl.validateRefund() Invalid Refund ]", e);
			result.setSuccess(false);
		}
		return result;
	}

	
	/**
	 * 回调验签
	 */
	public boolean verifySign(SortedMap<String, String> parameters,String signString){
		return super.verifySignByGbk(parameters, signString);
	}
	
	
	 /**
	  * <p>Description: 发送平台退款请求， 处理返回的接口</p>
	  * @param postUrl
	  * @param paramDO
	  * @return
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-11-1
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	@SuppressWarnings("unchecked")
	public Result platformRefundSendRequest(String postUrl,PlatformRefundParamDO paramDO) {
		Result result = new ResultSupport();
		try {
		// 通信对象
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setReqContent(postUrl);
		httpClient.setCaInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(RefundConstant.CACERT_NAME)));
		// 设置个人(商户)证书
		httpClient.setCertInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(RefundConstant.CERTIFICATE_NAME)), PinjuConstant.TENPAY_PAY_PARTNER);
		//httpClient.setTimeOut(60);  //设置超时时间 60秒			
		int requestByErrorCode = 0;
		int requestByTimeOut = 0;
		boolean needRequest = true;
		while (needRequest) {
			// 应答对象
			PlatformRefundParamDO receiveParamDO = null;
			ScriptClientResponseHandler	resHandler = new ScriptClientResponseHandler();
			if (httpClient.call()) {
				// 设置结果参数
				resHandler.setContent(httpClient.getResContent());
				// 获取返回参数
				 receiveParamDO = this.getReceiveParamDO(resHandler);
			
				String pay_result = resHandler.getParameter("pay_result");
				String sign = resHandler.getParameter("sign");
				SortedMap<String, String> allParameters = resHandler.getAllParameters();
				// 返回结果成功
				if (isTenState(pay_result)) {
					// 判断验签
					if (!verifySign(allParameters,sign)) {
						log.error("Event=[TenPlatformRefundAOImpl.platformRefundSendRequest()]@返回参数验证签名失败 ");
						result.setSuccess(false);
						return result;
					}
					//验签成功
					 result =platformRefundNotifySuccess(receiveParamDO);
					if (!result.isSuccess()) {
						log.error("Event=[TenPlatformRefundAOImpl.platformRefundSendRequest() process fail]".concat(receiveParamDO.getRefundId()));
					}
					result.setModel("refundFlag", "0");
					return result;
				}
				// 如果失败 并且是指定的 resultCode
				if (!isTenState(pay_result)&& TenpayResultCodeEnum.verifyResultCode(receiveParamDO.getPayResult())) {
					needRequest = true;
					requestByErrorCode++;
				}

				// 如果失败 并且不是指定的ResultCode
				if (!isTenState(pay_result)&& !TenpayResultCodeEnum.verifyResultCode(receiveParamDO.getPayResult())) {
					// TODO 直接插单和更改退款和子订单状态为失败
					result = platformRefundNotifyIsError(receiveParamDO);
					if (!result.isSuccess()) {
						log.error("Event=[TenPlatformRefundAOImpl.platformRefundSendRequest() is fail] ".concat(receiveParamDO.getRefundId()));
					}
					result.setModel("refundFlag", "1");
					return result;
				}
			} else {
				// 有可能因为网络原因，请求已经处理，但未收到应答。
				log.error(httpClient.getResponseCode() + ":"+ httpClient.getErrInfo().concat("后台调用通信失败"));
				// 重发 5次请求 if(times > 5) 手工插单
				needRequest = true;
				requestByTimeOut++;
			}
			//发送超时
			if(requestByTimeOut >= RefundConstant.MAX_REQUEST_NUMBER){
				//TODO 手工插入,  发送请求超时   receiveParamDO 是空值   使用发送 platformParamDO
				 result = platformRefundNotifyIsError(paramDO);
				if (!result.isSuccess()) {
					log.error("Event=[TenPlatformRefundAOImpl.platformRefundSendRequest() is fail] ".concat(paramDO.getRefundId()));
				}
				result.setModel("refundFlag", "1");
				return result;
			}
			//返回错误 次数超过5次
			if(requestByErrorCode >= RefundConstant.MAX_REQUEST_NUMBER){
				//TODO 手工插入
				 result =platformRefundNotifyIsError(receiveParamDO);
				if (!result.isSuccess()) {
					log.error("Event=[TenPlatformRefundAOImpl.platformRefundSendRequest() is fail] ".concat(receiveParamDO.getRefundId()));
				}
				result.setModel("refundFlag", "1");
				return result;
			}
		}
	} catch (Exception e) {
		log.error("Event=[TenPlatformRefundAOImpl.platformRefundSendRequest()  occurs exception]", e);
		result.setSuccess(false);
	}
	return result;
 }

	/**
	 * <p>Description: 构造接受参数</p>
	 * @param resHandler
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-27
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public PlatformRefundParamDO getReceiveParamDO(ScriptClientResponseHandler resHandler){
		PlatformRefundParamDO paramDO= new PlatformRefundParamDO();
		paramDO.setOrderPayId(resHandler.getParameter("sp_billno"));
		paramDO.setRefundId(resHandler.getParameter("refund_id"));
		paramDO.setPayResult(resHandler.getParameter("pay_result"));
		paramDO.setPayInfo(resHandler.getParameter("pay_info"));
		paramDO.setRefundFee(this.createLong(resHandler.getParameter("refund_fee")));
		paramDO.setTransactionId(resHandler.getParameter("transaction_id"));
		paramDO.setBackInfo(resHandler.getAllParameters().toString().replace(",", "&").replace(" ", "").replace("{", "").replace("}", ""));
		return paramDO;
	}
	
	/**
	 * <p>Description: 回调签名</p>
	 * @param pay_result
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-1
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isTenState(String pay_result) {
		// TODO Auto-generated method stub
		 return StringUtils.equalsIgnoreCase(pay_result,PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
	}
	
	 /**
	  * <p>Description: 平台退款AO（全额和部分）处理接口</p>
	  * @param paramDO
	  * @return
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-11-1
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	@Override
	public Result platformRefund(PlatformRefundParamDO paramDO) {
		// 组装参数
		 Result result = new ResultSupport();
		 result = createPlatformRefundParam(paramDO);
		 paramDO = (PlatformRefundParamDO) result.getModel("paramDO");
		if (result.isSuccess()) {
			String postUrl = (String) result.getModel("refundParamUrl");
			result = this.platformRefundSendRequest(postUrl, paramDO);
		} 
		return result;
	}



	 /**
	  * <p>Description:平台退款定时   处理业务接口 </p>
	  * @param refundParamDO
	  * @return
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-11-1
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	@Override
	public Result platformRefundNotifyForTiming(PlatformRefundParamDO refundParamDO) {
		Result result = new ResultSupport();
		try {
			// 插入回调接受日志
			this.createPayBackLog(refundParamDO);
			// 根据支付交易号 获取 父订单
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderPayId(refundParamDO.getOrderPayId());
			// 判断支付订单是否存在
			result = this.validateRefund(refundParamDO, vouchPayDO);
			if (!result.isSuccess()) {
				log.error("Event=[TenPlatformRefundAOImpl.platformRefundNotifyForTiming() the refund  have been processed]");
				return result;
			}
			boolean isAll = false;
			if (refundParamDO.getRefundFee().compareTo(vouchPayDO.getRealPayMentamount()) == 0) {
				isAll = true;
			}
			// 更新 退款单状态 ，子主订单状态 , 退款日志状态 和插入 子订单修改插入日志
			result.setSuccess(platformRefundManager.updateAllRefundNotifyForSuccess(vouchPayDO.getOrderId(), isAll, "定时退款成功"));
		} catch (ManagerException e) {
			log.error("Event=[TenPlatformRefundAOImpl.platformRefundNotifyForTiming() exception]", e);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public Result platformRefundForOneOrder(Long orderId) {
		Result result = new ResultSupport();
		try {
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderId(orderId);
			// 组装 发送参数    财付通交易号， 内部交易号， 总金额 ，退款金额
			PlatformRefundParamDO sendParams= PlatformRefundParamDO.createRefundParamDO(vouchPayDO.getOutPayId(), vouchPayDO.getOrderPayId(), vouchPayDO.getRealPayMentamount(), vouchPayDO.getRealPayMentamount());
			sendParams.setOrderId(orderId);
			result = this.createPlatformRefundParam(sendParams);
			if(result.isSuccess()){
				sendParams = (PlatformRefundParamDO) result.getModel("paramDO");
				String postUrl = (String) result.getModel("refundParamUrl");
				result =platformRefundSendRequest(postUrl,sendParams);
			}
		} catch (ManagerException e) {
			result.setResultCode(RefundResultCode.PLATFORM_REFUND_EXCEPTION); // 该退款异常
			log.error("Event=[TenPlatformRefundAOImpl.judgeSuccessForPlatRefund() exception]", e);
			result.setSuccess(false);
		}
		return result;
	}
	
	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setPlatformRefundManager(PlatformRefundManager platformRefundManager) {
		this.platformRefundManager = platformRefundManager;
	}

	public void setRefundLogManager(RefundLogManager refundLogManager) {
		this.refundLogManager = refundLogManager;
	}


	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}

	public void setTradeRefundManualManager(
			TradeRefundManualManager tradeRefundManualManager) {
		this.tradeRefundManualManager = tradeRefundManualManager;
	}
}


