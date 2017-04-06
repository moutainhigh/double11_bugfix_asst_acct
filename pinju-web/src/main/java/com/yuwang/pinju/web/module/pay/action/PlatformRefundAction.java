package com.yuwang.pinju.web.module.pay.action;

import java.io.File;
import java.util.SortedMap;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.resultcode.RefundResultCode;
import com.yuwang.pinju.core.common.tenpay.ScriptClientResponseHandler;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.RefundConstant;
import com.yuwang.pinju.core.constant.trade.TenpayResultCodeEnum;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.RefundTimingAO;
import com.yuwang.pinju.core.trade.ao.TenPlatRefundTimingAO;
import com.yuwang.pinju.core.trade.ao.TenPlatformRefundAO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;
import com.yuwang.pinju.web.struts2.PinjuAction;

/**
 * <p>Description: 平台退款Action  发送退款请求和接受退款结果</p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-13
 */
public class PlatformRefundAction extends TenPayNotifyBaseAction implements PinjuAction{
	          
	private static final long serialVersionUID = 1900443502634844426L;
	
	private TenPlatformRefundAO tenPlatformRefundAO;
	private String postUrl;
	private PlatformRefundParamDO sendParamDO;
	//private PlatformRefundParamDO receiveParamDO;
	private ScriptClientResponseHandler resHandler;
	
	private RefundApplyAO refundApplyAO;
	
	private OrderDO orderDO;
	private Long orderId;
	public String  platformRefundRequest() {
		String returnString = SUCCESS;
		Result result = null;
		try {
			
		/*	sendParamDO = new PlatformRefundParamDO();
			sendParamDO.setTransactionId("1900000107201110120000036850");	//财付通交易单号    
			sendParamDO.setTotalFee(6L);    	    	    				//商品金额,以分为单位
			sendParamDO.setRefundFee(6L);
			sendParamDO.setOrderPayId("1900000107201110120000036850");
			sendParamDO.setRefundId("1091900000107201110120009150");*/
			
			//sendParamDO.setBuyerIp("" + ServletUtil.getRemoteNumberIp());
			 result = tenPlatformRefundAO.createPlatformRefundParam(sendParamDO);
			 sendParamDO = (PlatformRefundParamDO) result.getModel("paramDO");
			 orderId = sendParamDO.getOrderId();
			if (result.isSuccess()) {
				postUrl = (String) result.getModel("refundParamUrl");
				returnString =this.platformRefund();
			} else {
				// 错误的跳转和处理
				log.error("Create platform refund request params is error");
				 this.setErrorMessage(result);
				return ERROR;
			}
		} catch (Exception e) {
			log.error("Create platform Refund request params is occurs Exception",e);
			errorMessage = ResultCodeMsg.getResultMessage(RefundResultCode.PLATFORM_REFUND_EXCEPTION);
			return ERROR;
		}
		return returnString;
	}
	
	public String platformRefund() {
		try {
			// 通信对象
			TenpayHttpClient httpClient = new TenpayHttpClient();
			//httpClient.setCharset(PinjuConstant.TENPAY_DIRECTPAY_INPUT_CHARSET);  //设置编码格式 UTF-8
			httpClient.setReqContent(postUrl);
			// httpClient.setCaInfo(new File("F:/cacert.pem"));
			httpClient.setCaInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(RefundConstant.CACERT_NAME)));
			// 设置个人(商户)证书
			// httpClient.setCertInfo(new File("F:/1900000107.pfx"),"1900000107");
			httpClient.setCertInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(RefundConstant.CERTIFICATE_NAME)), PinjuConstant.TENPAY_PAY_PARTNER);
			
			// 设置请求内容  默认是30秒
			//httpClient.setTimeOut(60);  //设置超时时间 60秒			
			int requestByErrorCode = 0;
			int requestByTimeOut = 0;
			boolean needRequest = true;
			while (needRequest) {
				PlatformRefundParamDO receiveParamDO = null;
				// 应答对象
				ScriptClientResponseHandler	_resHandler = new ScriptClientResponseHandler();
				if (httpClient.call()) {
					// 设置结果参数
					this.resHandler = _resHandler;
					resHandler.setContent(httpClient.getResContent());
					// 获取返回参数
					receiveParamDO = tenPlatformRefundAO.getReceiveParamDO(resHandler);
				
					// 返回结果成功
					if (isTenState()) {
						// 判断验签
						if (!verifySign()) {
							log.error("验证签名失败 ");
							errorMessage = ResultCodeMsg.getResultMessage(RefundResultCode.PLATFORM_REFUND_SING_ERROR);
							return ERROR;
						}
						//验签成功
						Result result = tenPlatformRefundAO.platformRefundNotifySuccess(receiveParamDO);
						if (!result.isSuccess()) {
							log.error("Event=[PlatformRefundAction.platformRefund() process Fail] refund id is  ".concat(receiveParamDO.getRefundId()));
							this.setErrorMessage(result);
							return ERROR;
						}
						// 获取订单信息
						return SUCCESS;
					}
					
					// 如果失败 并且是指定的 resultCode
					if (!isTenState()&& TenpayResultCodeEnum.verifyResultCode(receiveParamDO.getPayResult())) {
						needRequest = true;
						requestByErrorCode++;
					}

					// 如果失败 并且不是指定的ResultCode
					if (!isTenState()&& !TenpayResultCodeEnum.verifyResultCode(receiveParamDO.getPayResult())) {
						// TODO 直接插单和更改退款和子订单状态为失败
						Result result = tenPlatformRefundAO.platformRefundNotifyIsError(receiveParamDO);
						if (!result.isSuccess()) {
							log.error("Event=[PlatformRefundAction.platformRefund() process Fail] refund id is ".concat(receiveParamDO.getRefundId()));
							this.setErrorMessage(result);
							return ERROR;
						}
						errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode());
						return INPUT;
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
					if(receiveParamDO == null) receiveParamDO = sendParamDO;
					Result result = tenPlatformRefundAO.platformRefundNotifyIsError(receiveParamDO);
					if (!result.isSuccess()) {
						log.error("Event=[PlatformRefundAction.platformRefund() process Fail] refund id is ".concat(receiveParamDO.getRefundId()));
						this.setErrorMessage(result);
						return ERROR;
					}
					errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode());
					return INPUT;
				}
				//返回错误 次数超过5次
				if(requestByErrorCode >= RefundConstant.MAX_REQUEST_NUMBER){	
					//TODO 手工插入
					Result result = tenPlatformRefundAO.platformRefundNotifyIsError(receiveParamDO);
					if (!result.isSuccess()) {
						log.error("Event=[PlatformRefundAction.platformRefund() process Fail] refund id is ".concat(receiveParamDO.getRefundId()));
						this.setErrorMessage(result);
						return ERROR;
					}
					errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode());
					return INPUT;
				}
			}
		} catch (Exception e) {
			log.error("Event=[PlatformRefundAction.platformRefund() occurs exception]", e);
			errorMessage = ResultCodeMsg.getResultMessage(RefundResultCode.PLATFORM_REFUND_SEND_EXCEPTION);
			return ERROR;
		}
		return INPUT;
	}
	
	
	/**
	 * 设置错误信息
	 * @param result
	 */
	private void setErrorMessage(Result result){
		if(result.getResultCode().compareTo(RefundResultCode.PLATFORM_REFUND_ALREADY) == 0){
			errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode(),sendParamDO.getOrderId().toString());
		}else{
			errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode());
		}
	}
	
	@Override
	protected Integer getBizType() {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected boolean verifySign() {
		// TODO Auto-generated method stub
		String _sign = resHandler.getParameter("sign");// 签名字符串
		return tenPlatformRefundAO.verifySign(resHandler.getAllParameters(), _sign);
	}
	
	
	@Override
	protected boolean isTenState() {
		// TODO Auto-generated method stub
		 return StringUtils
			.equalsIgnoreCase(resHandler.getParameter("pay_result"),
					PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
	}

	@Override
	protected boolean notifyDelivery() {
		// TODO Auto-generated method stub
		return false;
	}

	public TenPlatformRefundAO getTenPlatformRefundAO() {
		return tenPlatformRefundAO;
	}
	public void setTenPlatformRefundAO(TenPlatformRefundAO tenPlatformRefundAO) {
		this.tenPlatformRefundAO = tenPlatformRefundAO;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public PlatformRefundParamDO getSendParamDO() {
		return sendParamDO;
	}

	public void setSendParamDO(PlatformRefundParamDO sendParamDO) {
		this.sendParamDO = sendParamDO;
	}
	
	public ScriptClientResponseHandler getResHandler() {
		return resHandler;
	}

	public void setResHandler(ScriptClientResponseHandler resHandler) {
		this.resHandler = resHandler;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Override
	protected SortedMap<String, String>  setParameters(SortedMap<String, String> parameters) {
		return null;
	}
	/**
	 * 平台处理成功跳到响应的action
	 * <p>Discription: </p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-11
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String refundredirectAction(){
		Long od = super.getLong("OId");
		if(od != null){
			orderDO = refundApplyAO.getOrderInfo(od);
		}
		
		return SUCCESS;
	}
	
	private RefundTimingAO refundTimingAO;
	public String excuteTiming(){
		
		tenPlatRefundTimingAO.platformRefund();
		errorMessage = "手工退款成功了";
		//refundTimingAO.buyerNoReturnGoods();
		
		log.debug("定时开始执行了");
		return SUCCESS;
	}
	
	private TenPlatRefundTimingAO tenPlatRefundTimingAO;
	public void setTenPlatRefundTimingAO(TenPlatRefundTimingAO tenPlatRefundTimingAO) {
		this.tenPlatRefundTimingAO = tenPlatRefundTimingAO;
	}

	public void setRefundTimingAO(RefundTimingAO refundTimingAO) {
		this.refundTimingAO = refundTimingAO;
	}
}


