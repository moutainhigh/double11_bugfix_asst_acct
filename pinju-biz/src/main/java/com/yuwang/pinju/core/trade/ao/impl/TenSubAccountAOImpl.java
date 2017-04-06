package com.yuwang.pinju.core.trade.ao.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.TenPayResultCode;
import com.yuwang.pinju.core.common.tenpay.BaseSplitRequestHandler;
import com.yuwang.pinju.core.common.tenpay.ScriptClientResponseHandler;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.servicefee.manager.ServiceFeeManager;
import com.yuwang.pinju.core.trade.ao.TenSplitRollBackAO;
import com.yuwang.pinju.core.trade.ao.TenSubAccountAO;
import com.yuwang.pinju.core.trade.manager.VouchCreateManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.pay.PayBackLogDO;
import com.yuwang.pinju.domain.order.pay.PaySendLogDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.trade.TenSplitRollBackDO;
import com.yuwang.pinju.domain.trade.TenSubAccountDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.points.constants.PointsConstants;
import com.yuwang.points.mina.client.PointsClient;

/**
 * @Discription: 分账AO实现类
 * @Project: pinju-biz
 * @Package: com.yuwang.pinju.core.trade.ao.impl
 * @Title: TenSubAccountAOImpl.java
 * @author: [贺泳]
 * @date 2011-9-13 上午10:12:22
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class TenSubAccountAOImpl extends TenPayAbstractBaseAO implements TenSubAccountAO {
	/**
	 * 日志操作，插入回调日志
	 */
	private VouchCreateManager vouchCreateManager;
	/**
	 * 订单操作，修改订单状态
	 */
	private OrderUpDateManager orderUpDateManager;
	/**
	 * 支付订单记录操作
	 */
	private VouchQueryManager vouchQueryManager;
	/**
	 * 订单退款查询操作
	 */
	private RefundManager refundManager;
	/**
	 * 订单查询
	 */
	private OrderQueryManager orderQueryManager;
	
	/**
	 * 卖家角色
	 */
	private final static Long WRITER = 1L;
	
	/**
	 * 平台服务方角色
	 */
	private final static Long PINJU = 2L;
	
	/**
	 * 独立分润方角色
	 */
	private final static Long SPLITTING = 4L;
	
	/**
	 * 订单状态描述
	 */
	private final static String BUYBUSINESS = "交易成功";
	
	/**
	 * 平台费率管理
	 */
	private ServiceFeeManager serviceFeeManager;
	
	/**
	 * 应答对象
	 */
	private ScriptClientResponseHandler resHandler;
	
	/**
	 * 订单DO
	 */
	private OrderDO orderDO;
	
	/**
	 * 分账回退AO
	 */
	private TenSplitRollBackAO tenSplitRollBackAO;

	/**
	 * @Description: 1、封装分账参数 2、生成签名 3、参数报文带签名 4、生成财富通分账请求地址 
	 * 				 5、插入发送日志信息  6、提供分账请求地址和参数
	 * @author [贺泳]
	 * @date 2011-9-09 下午14:21:23
	 * @version 1.0
	 * @param tenSubAccountDO
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public Result createSubAccountParam(List<TenSubAccountDO> tenSubAccountList) {
		Result result = new ResultSupport();
		try {
			//判断 tenSubAccountList是否有值
			if(tenSubAccountList == null || tenSubAccountList.size() == 0){
				log.debug("Event=[TenSubAccountAOImpl#createSubAccountParam] sub account tenSubAccountList is null");
				return this.setResult(false,result,TenPayResultCode.SUBACCOUNT_ORDER_NOTEXITS);
			}
			boolean flag = true;
			for(TenSubAccountDO tenSubAccountDO:tenSubAccountList){
				//创建分账请求对象
				BaseSplitRequestHandler reqHandler = new BaseSplitRequestHandler(null, null);
				
				VouchPayDO vouchPayDO = new VouchPayDO();
				vouchPayDO.setOrderPayId(tenSubAccountDO.getSpBillno());
				//根据内部支付编号查询支付记录
				VouchPayDO vouchPay = vouchQueryManager.selectOrderPay(vouchPayDO);
			    if(vouchPay == null){
			    	log.debug("Event=[TenSubAccountAOImpl#createSubAccountParam] 分账发起前，根据OrderPayId:"+tenSubAccountDO.getSpBillno()+"查询支付记录表，查无此记录");
			    	this.setResult(false,result,TenPayResultCode.SUBACCOUNT_ORDER_NOTEXITS);
			    	continue;
			    }
			    
			    //判断改订单是否被支付：  已支付 101
			    if(vouchPay.getPayState() == VouchPayConstant.VOUCH_PAY_STATE_UNPAID){
			    	log.debug("Event=[TenSubAccountAOImpl#createSubAccountParam]  分账发起前，根据订单id：" + vouchPay.getOrderId() + " 查询订单信息，未支付");
			    	result.setModel("errorOrderId", vouchPay.getOrderId().toString());
			    	this.setResult(false,result,TenPayResultCode.SUBACCOUNT_ORDER_PAY_FAIL);
			    	continue;
			    }
			    
			    //判断改订单是否被分账：  已分账 102
			    if(vouchPay.getPayState() == VouchPayConstant.VOUCH_PAY_STATE_SPLIT){
			    	log.debug("Event=[TenSubAccountAOImpl#createSubAccountParam] 分账发起前，根据订单id：" + vouchPay.getOrderId() + " 查询订单信息，已经处理分账");
			    	result.setModel("errorOrderId", vouchPay.getOrderId().toString());
			    	this.setResult(false,result,TenPayResultCode.SUBACCOUNT_ORDER_SUBACCOUNT);
			    	continue;
			    }
			    
			    // 设置请求参数
			    reqHandler.init();
			    reqHandler = createParameters(tenSubAccountDO,reqHandler);
			    
			    // 得到请求内容
			    String parametersUrl = reqHandler.getRequestURL();
			    
			    // 业务操作(插入发送日志信息)
			    PaySendLogDO paySendLogDO = PaySendLogDO.createPaySendLogDOParam(vouchPay.getBuyerId(),vouchPay.getSellerId(),CMDNO,tenSubAccountDO.getSpBillno(),parametersUrl,new Date(),CMDNO);
				vouchCreateManager.insertTradePaySendLog(paySendLogDO);
				
				if(log.isDebugEnabled()){
					log.error("Event=[TenSubAccountAOImpl#createSubAccountParam]  财付通分账请求地址和参数:"+parametersUrl);
				}
				//提供财付通分账请求地址和参数
				result = this.splitCall(parametersUrl,tenSubAccountDO);
				if(!result.isSuccess()){
					flag = false;
				}
			
			}
		
			if(!flag){
				return this.setResult(false,result,result.getResultCode());
			}
		} catch (ManagerException e) {
			log.error("Event=[TenSubAccountAOImpl#createSubAccountParam] 财付通分账插入发送日志异常",e);
			result.setSuccess(false);
			result.setResultCode(TenPayResultCode.SUBACCOUNT_INSERT_SENFLOG_EXCEPTION);
		} catch (Exception e) {
			log.error("Event=[TenSubAccountAOImpl#createSubAccountParam] 财付通分账组装参数失败",e);
			result.setSuccess(false);
			result.setResultCode(TenPayResultCode.SUBACCOUNT_EXCEPTION);
		}
		return result;
	}
	
	/**
	 * @Description: 发送财付通分账请求
	 * @author [贺泳]
	 * @date 2011-9-09 上午10:58:20
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param sendUrl
	 * @return
	 */
	private Result splitCall(String sendUrl,TenSubAccountDO tenSubAccountDO){
		Result result = new ResultSupport();
		try {
			if(StringUtil.isBlank(sendUrl)){
				this.setResult(false,result,TenPayResultCode.SUBACCOUNT_EXCEPTION);
				return result;
			}
			
			/**
			 * 平台分账金额
			 */
			Long paltFormFee = 	0l;
			if(tenSubAccountDO.getPaltFormFee()!= null && tenSubAccountDO.getPaltFormFee() > 0){
				paltFormFee = tenSubAccountDO.getPaltFormFee();
			}
			
			// 通信对象
			TenpayHttpClient httpClient = new TenpayHttpClient();
			// 设置CA证书 (设置通信参数)
			httpClient.setCaInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(PinjuConstant.TENPAY_CACERT_NAME)));
			// 设置个人(商户)证书
			httpClient.setCertInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(PinjuConstant.TENPAY_PAY_PARTNER).concat(".pfx")), PinjuConstant.TENPAY_PAY_PARTNER);
			// 应答对象
			resHandler = new ScriptClientResponseHandler();
			// 设置请求内容
			httpClient.setReqContent(sendUrl);
			// 后台调用
			if (httpClient.call()) {
				// 设置结果参数
				resHandler.setContent(httpClient.getResContent());
				// 1、检验分账状态
				if (!isTenState()) {
					String strMsg = ResultCodeMsg.getResultMessage(TenPayResultCode.SUBACCOUNT_TEN_BACK_FAIL) + "，pay_result: " + resHandler.getParameter("pay_result") + "，pay_info: " + resHandler.getParameter("pay_info");
					log.error("Event=[TenSubAccountAOImpl#splitCall] " + strMsg);
					return this.setResult(false,result,TenPayResultCode.SUBACCOUNT_TEN_BACK_FAIL);
				}
				// 2、验证签名
				if (!verifySign()) {
					log.error("Event=[TenSubAccountAOImpl#splitCall] " + ResultCodeMsg.getResultMessage(TenPayResultCode.SUBACCOUNT_TEN_BACK_CHECKSIGN_FAIL));
					return this.setResult(false,result,TenPayResultCode.SUBACCOUNT_TEN_BACK_CHECKSIGN_FAIL);
				}
				// 3、插入接收日志
				this.subAccountBackNotify(resHandler.getAllParameters());
				
				// 4、判断该订单是否已被处理，如果没有处理则修改订单状态
				if(!this.orderIsDisposal(resHandler.getParameter("sp_billno"))){
					log.error("Event=[TenSubAccountAOImpl#splitCall] 财付通分账成功后修改订单状态失败!");
					return this.setResult(false,result,TenPayResultCode.SUBACCOUNT_EXCEPTION);
				}
				
				//根据outPayId 查询订单详情，用于成功页面显示订单信息
				this.queryOrderByOrderId(resHandler.getParameter("sp_billno"));
				
				if(orderDO != null && paltFormFee > 0 ){
					// 调用积分接口
					this.integralDeal(paltFormFee);
				}
				// 判断是否有退款
				if (this.orderWhetherRefund(resHandler.getParameter("sp_billno"))) {
					TenSplitRollBackDO tenSplitRollBackDO = new TenSplitRollBackDO();
					tenSplitRollBackDO.setTransaction_id(resHandler.getParameter("transaction_id"));
					tenSplitRollBackDO.setOrderId(resHandler.getParameter("sp_billno"));
					//调用曹晓AO
					tenSplitRollBackAO.tenSplitRollBack(tenSplitRollBackDO);
				}
				result.setModel("orderDO", orderDO);
			}else {
				log.error("Event=[TenSubAccountAOImpl#splitCall] 有可能因为网络原因，请求已经处理，但未收到财付通应答，err_code:"+ httpClient.getResponseCode() + "，errmsg:" + httpClient.getErrInfo());
				return this.setResult(false,result,TenPayResultCode.SUBACCOUNT_POST_EXCEPTION);
			}
		} catch (Exception e) {
			log.error("Event=[TenSubAccountAtion#splitCall] 向财付通发起分账或回调异常:",e);
			result.setSuccess(false);
			result.setResultCode(TenPayResultCode.SUBACCOUNT_EXCEPTION);
		}
		return result;
	}
	
	/**
	 * @Description: 校验签名
	 * @author [贺泳]
	 * @date 2011-11-1 上午11:29:53
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean verifySign() {
		String _sign = resHandler.getParameter("sign"); // 签名字符串
		log.debug("Event=[TenSubAccountAOImpl#verifySign] 分账回调sign：" + _sign);
		return super.verifySignByGbk(resHandler.getAllParameters(), _sign);
	}
	
	/**
	 * @Description: 校验分账结果
	 * @author [贺泳]
	 * @date 2011-11-1 上午11:30:00
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return
	 */
	private boolean isTenState() {
		String _result = resHandler.getParameter("pay_result");
		log.debug("Event=[TenSubAccountAOImpl#isTenState] 分账回调结果pay_result：" + _result);
		return StringUtils.equalsIgnoreCase(_result,PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
	}
	
	/**
	 * @Description: 积分处理(订单无退款的情况下使用)
	 * @author [贺泳]
	 * @date 2011-10-14 下午04:16:29
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void integralDeal(Long dealAmount){
		try {
			//1.判断子订单上是否有退款
			//2.有退款
			//3.子订单退款金额取出 ,平台费率,店铺费率
			//4.调serviceFeeManager.calcServiceFee(platformAmount, orderItemDO.getDealRates(), orderItemDO.getDealShopRates())
			//5.平台分账金额-子订单退款1计算后的平台手续退款金额-子订单退款2计算后的平台手续退款金额--子订单退款N计算后的平台手续退款金额
			log.warn("start PointsClient.updatePointsAccount 调用平台积分接口开始!");
			// 调用平台积分接口
			PointsClient.updatePointsAccount(
					orderDO.getSellerId(),
					PointsConstants.ACCOUNT_TYPE,
					PointsConstants.POINTS_TRADE_CHANNEL_TRADE_GET,
					PointsConstants.POINTS_ADD, Double.valueOf(MoneyUtil.getCentToDollar(dealAmount)));
			log.warn("end PointsClient.updatePointsAccount 调用平台积分接口结束");
		} catch (Exception e) {
			log.error("Event=[TenSubAccountAtion#integralDeal] PointsClient.updatePointsAccount exception 调用积分接口异常.当前参数---->".concat(orderDO.toString()));
			log.error("dealAmount 平台分账金额--->".concat(String.valueOf(dealAmount)));
			log.error("Event=[TenSubAccountAtion#integralDeal] 积分异常:",e);
		}
	}
	
	/**
	 * @Description: 取得财富通请求分账的业务描述，特定格式的字符串，
	 * 				 格式为：
	 *			 		  卖家的id^联系人姓名^联系电话^业务描述1^业务描述2^业务描述3
	 * @author [贺泳]
	 * @date 2011-9-23 上午10:42:09
	 * @version 1.0
	 * @param tenSubAccountDO
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private  String getBusdesc(TenSubAccountDO tenSubAccountDO){
		return "^^^^^";
	}
	
	/**
	 * @Description: 封装财付通分账的业务参数
	 *              [业务参数，特定格式的字符串，格式为：
	 * 				(账户^金额^角色)[|(账户^金额^角色)]
	 *    key:分账账户
	 *    value:金额
	 * @author [贺泳]
	 * @date 2011-9-20 下午06:08:14
	 * @version 1.0
	 * @param map
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private  String getBusArgs(TenSubAccountDO tenSubAccountDO){
		StringBuffer sb = new StringBuffer();
		if (tenSubAccountDO.getMap() != null) {
			for (Map.Entry<String, Long> entry : tenSubAccountDO.getMap().entrySet()) {
				String key = entry.getKey();
				Long value = entry.getValue();
				// 分润方(账户^金额^角色)[|(账户^金额^角色)]
				if(value > 0 ){
					sb.append(key).append("^").append(value).append("^").append(SPLITTING).append("|");
				}
			}
		}
		if(tenSubAccountDO.getPaltFormFee() > 0){
			// 平台服务方(账户^金额^角色)
			sb.append(PLATFORMACCOUNT).append("^").append(tenSubAccountDO.getPaltFormFee()).append("^").append(PINJU).append("|");
		}
		if(tenSubAccountDO.getSellerFee() > 0){
			// 卖家(账户^金额^角色)
			sb.append(tenSubAccountDO.getSellerAccount()).append("^").append(tenSubAccountDO.getSellerFee()).append("^").append(WRITER);
		}
		
		if(log.isDebugEnabled()){
			log.debug("Event=[TenSubAccountAtion#getBusArgs] sub-account biz business parameter: "+sb.toString());
		}
		String account = sb.toString();
		if(account.endsWith("|")){
			account = account.substring(0, account.length()-1);
		}
		if(log.isDebugEnabled()){
			log.debug("Event=[TenSubAccountAtion#getBusArgs] sub-account biz business parameter: "+account);
		}
		return account;
	}
	
	/**
	 * @Description: 封装财付通分账参数
	 * @author [贺泳]
	 * @date 2011-9-09 下午15:02:28
	 * @version 1.0
	 * @param subAccountDO
	 * @return
	 * @throws ManagerException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private BaseSplitRequestHandler createParameters(TenSubAccountDO tenSubAccountDO,BaseSplitRequestHandler reqHandler) {
		// 业务代码, 财付通分账接口填"3"
		reqHandler.setParameter("cmdno", CMDNO);

		// 商家的商户号,由腾讯公司唯一分配
		reqHandler.setParameter("bargainor_id", PINJUMERCHANTNO);

		// 已经成功支付的财付通交易号(订单号)
		reqHandler.setParameter("transaction_id", tenSubAccountDO.getTransactionId().toString());

		// 商户系统内部的定单号，此参数仅在对账时提供。
		reqHandler.setParameter("sp_billno", tenSubAccountDO.getSpBillno().toString());

		// 总金额，以分为单位
		reqHandler.setParameter("total_fee", tenSubAccountDO.getTotalFee().toString());

		// 现金支付币种，目前只支持人民币
		reqHandler.setParameter("fee_type", CURRENCYTYPE);

		// 后台调用，填写为http://127.0.0.1/
		reqHandler.setParameter("return_url", RETURNURL);

		// 业务类型，整数值，代表分账处理规则与业务参数编码规则，暂固定为97
		reqHandler.setParameter("bus_type", BUSTYPE);

		// 业务参数，特定格式的字符串，格式为：(账户^金额^角色)[|(账户^金额^角色)]*
		reqHandler.setParameter("bus_args", getBusArgs(tenSubAccountDO));

		// 业务描述，特定格式的字符串
		reqHandler.setParameter("bus_desc", getBusdesc(tenSubAccountDO));

		// 版本号必须填 "4"
		reqHandler.setParameter("version", VERSION);
		
		// MD5Key
		reqHandler.setKey(getMD5Key());
		
		// 请求网关地址
		reqHandler.setGateUrl(PATMENTGATEWAYURL);

		return reqHandler;
	}
	
	/**
	 * @Description: 财付通分账回调处理，插入接收日志。
	 * @author [贺泳]
	 * @date 2011-9-16 下午01:31:03
	 * @version 1.0
	 * @param tenSubAccountDO
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result subAccountBackNotify(SortedMap<String, String> sortedMap) {
		Result result = new ResultSupport();
		PayBackLogDO payBackLogDO = PayBackLogDO.createPayBackLogDO(sortedMap.get("sp_billno"),sortedMap.get("transaction_id"),parametersToString(sortedMap),new Date(),CMDNO,CMDNO);
		try {
			vouchCreateManager.insertTradePayBackLog(payBackLogDO);
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[TenSubAccountAOImpl#SubAccountBackNotify] 财付通分账成功后插入回调日志异常",e);
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("Event=[TenSubAccountAOImpl#SubAccountBackNotify] 财付通分账成功后回调插入回调日志失败",e);
		}
		return result;
	}
	
	/**
	 * @Description: 取得担保交易的MD5Key
	 * @author [贺泳]
	 * @date 2011-9-23 上午10:40:33
	 * @version 1.0
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	protected String getMD5Key() {
		return PinjuConstant.TENPAY_PAY_MD5KEY;
	}
	
	/**
	 * @Description: 根据的订单id 查询该订单是否有无退款
	 * @author [贺泳]
	 * @date 2011-9-23 下午01:55:11
	 * @version 1.0
	 * @param orderId
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean orderWhetherRefund(String orderId) {
		boolean isRefund = false;
		try {
			VouchPayDO vouchPayDO = new VouchPayDO();
			vouchPayDO.setOrderPayId(orderId);
			//根据订单号查询查询支付记录，判断是否为空以及是否已经被处理分账
			VouchPayDO vouchPay = vouchQueryManager.selectOrderPay(vouchPayDO);
			
			log.debug("财付通分账成功后根据的订单id："+orderId+"，查询该订单是否有无退款");
			
			List<RefundDO> refundDOList = refundManager.queryRefundByOrderId(vouchPay.getOrderId());
			
			if(refundDOList != null && refundDOList.size() >0){
				for (RefundDO refundDO : refundDOList) {
					if(refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE) == 0){
						isRefund = true;
					}
				}
			}
		} catch (ManagerException e) {
			log.error("Event=[TenSubAccountAOImpl#orderWhetherRefund] 分账成功后根据的orderid查询该订单是否有无退款异常",e);
		} catch (Exception e) {
			log.error("Event=[TenSubAccountAOImpl#orderWhetherRefund] 分账成功后根据的orderid查询该订单是否有无退款异常",e);
		}
		return isRefund;
	}
	
	/**
	 * @Description: 根据的订单id 查询该订单是否已经被处理分账，如果没有，则修改订单状态，反之。
	 * @author [贺泳]
	 * @date 2011-9-23 下午03:26:49
	 * @version 1.0
	 * @param orderId
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("unchecked")
	private boolean orderIsDisposal(final String orderId) {
		Boolean isBoolean = (Boolean) getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				boolean isDisposal = true;
				try {
					VouchPayDO vouchPayDO = new VouchPayDO();
					vouchPayDO.setOrderPayId(orderId);
					//根据订单号查询查询支付记录，判断是否为空以及是否已经被处理分账
					VouchPayDO vouchPay = vouchQueryManager.selectOrderPay(vouchPayDO);
					if(vouchPay == null){
						isDisposal = false;
						return isDisposal;
				    }
					
					//判断是否已经被处理分账
					if(vouchPay.getPayState() != VouchPayConstant.VOUCH_PAY_STATE_SPLIT){
						isDisposal = true;
					}
					
					//如果没处理则修改订单状态
					if(isDisposal){
						//根据订单orderId查询订单表，得到操作员ID
						OrderDO orderDO = orderQueryManager.getOrderDOById(vouchPay.getOrderId());
						//修改 订单表 订单状态：5-交易成功
						orderUpDateManager.upOrderState(orderDO.getBuyerId(),vouchPay.getOrderId() ,OrderDO.ORDER_STATE_5,BUYBUSINESS);
						
						//修改支付记录表订单支付状态：102-已分账
						vouchPayDO = null;
						vouchPayDO = new VouchPayDO();
						vouchPayDO.setOrderId(vouchPay.getOrderId());
						vouchPayDO.setPayState(VouchPayConstant.VOUCH_PAY_STATE_SPLIT);
						vouchPayDO.setOrderPayId(orderId);
						vouchPayDO.setEndModifyTime(new Date());
						vouchCreateManager.updateOrderPay(vouchPayDO);
					}
				} catch (ManagerException e) {
					status.setRollbackOnly();
					isDisposal = false;
					log.error("Event=[TenSubAccountAOImpl#orderIsDisposal] 财付通分账成功后根据订单号查询，判断订单是否被处理分账和修改订单状态异常",e);
				} catch (Exception e) {
					status.setRollbackOnly();
					isDisposal = false;
					log.error("Event=[TenSubAccountAOImpl#orderIsDisposal] 财付通分账成功后根据订单号查询，判断订单是否被处理分账和修改订单状态异常",e);
				}
				return isDisposal;
			}
		});
		return isBoolean;
	}
	
	/**
	 * @Description:  根据 outPayId查询订单详情
	 * @author [heyong]
	 * @date 2011-10-8 下午03:14:31
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param outPayId
	 * @return
	 */
	private Result queryOrderByOrderId(String outPayId) {
		Result result = new ResultSupport();
		try {
			log.debug("outPayId: " + outPayId);
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderPayId(outPayId);
			if(vouchPayDO == null){
				result.setSuccess(false);
				return result;
			}
			orderDO = orderQueryManager.getOrderDOById(vouchPayDO.getOrderId());
		} catch (ManagerException e) {
			result.setSuccess(false);
			log.error("Event=[TenSubAccountAOImpl#queryOrderByOrderId] 财付通分账成功后根据outPayId查询订单信息错误",e);
		} catch (Exception e){
			result.setSuccess(false);
			log.error("Event=[TenSubAccountAOImpl#queryOrderByOrderId] 财付通分账成功后根据outPayId查询查询订单信息异常",e);
		}
		return result;
	}

	
	/**
	 * @Description:根据orderId查询订单信息 
	 * @author [贺泳]
	 * @date 2011-10-21 下午04:11:08
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param orderId：订单Id
	 *        mumberId：会员Id
	 * @return
	 */
	@Override
	public Result queryOrderByOrderId(Long orderId,Long mumberId) {
		Result result = new ResultSupport();
		try {
			log.debug("财付通分账成功后根据订单orderId查询订单信息，订单Id为: " + orderId);
			OrderDO orderDO = orderQueryManager.getOrderDOById(orderId);
			if(orderDO == null){
				result.setSuccess(false);
				result.setResultCode(TenPayResultCode.SUBACCOUNT_QUERY_ORDER_EXCEPTION);
				return result;
			}
			if(!mumberId.equals(orderDO.getBuyerId())){
				result.setSuccess(false);
				result.setResultCode(TenPayResultCode.SUNACCOUNT_POST_ADDRESS_FAIL);
				return result;
			}
			if(orderDO.getOrderState() == OrderDO.ORDER_STATE_1 ||
			   orderDO.getOrderState() == OrderDO.ORDER_STATE_2 ||
			   orderDO.getOrderState() == OrderDO.ORDER_STATE_3 ||
			   orderDO.getOrderState() == OrderDO.ORDER_STATE_4 
			){
				result.setSuccess(false);
				result.setResultCode(TenPayResultCode.SUNACCOUNT_POST_ADDRESS_FAIL);
				return result;
			}
			result.setModel("orderDO",orderDO);
		} catch (ManagerException e) {
			result.setResultCode(TenPayResultCode.SUBACCOUNT_QUERY_ORDER_EXCEPTION);
			result.setSuccess(false);
			log.error("Event=[TenSubAccountAOImpl#queryOrderByOrderId] 财付通分账成功后根据订单orderId查询订单信息错误",e);
		} catch (Exception e){
			result.setResultCode(TenPayResultCode.SUBACCOUNT_QUERY_ORDER_EXCEPTION);
			result.setSuccess(false);
			log.error("Event=[TenSubAccountAOImpl#queryOrderByOrderId] 财付通分账成功后根据订单orderId查询订单信息异常",e);
		}
		return result;
	}
	
	/**
	 * @Description: 封装返回信息
	 * @author [贺泳]
	 * @date 2011-11-1 上午11:57:08
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param flag
	 * @param result
	 * @param resultCode
	 * @return
	 */
	private Result setResult(boolean flag, Result result, String resultCode) {
		result.setSuccess(flag);
		result.setResultCode(resultCode);
		return result;
	}
	
	/**
	 * 品聚在财富通的商务号
	 */
	private final String PINJUMERCHANTNO = PinjuConstant.TENPAY_PAY_PARTNER;
	/**
	 * 品聚在财付通分账账户
	 */
	private final String PLATFORMACCOUNT = PinjuConstant.TENPAY_PAY_PINJU_ACCOUNT;
	/**
	 * 财付通分账网关地址
	 */
	private final String PATMENTGATEWAYURL = PinjuConstant.TENPAY_SPLIT_PATMENTGATEWAYURL;
	/**
	 * 财付通分账业务代码
	 */
	private final String CMDNO = PinjuConstant.TENPAY_SPLIT_CMDNO;
	/**
	 * 财付通分账页面回调地址
	 */
	private final String RETURNURL = PinjuConstant.TENPAY_SPLIT_RETURNURL;
	/**
	 * 财付通分账业务类型
	 */
	private final String BUSTYPE = PinjuConstant.TENPAY_SPLIT_BUSTYPE;
	/**
	 * 财付通分账版本号
	 */
	private final String VERSION = PinjuConstant.TENPAY_SPLIT_VERSION;
	
	public void setVouchCreateManager(VouchCreateManager vouchCreateManager) {
		this.vouchCreateManager = vouchCreateManager;
	}
	
	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}
	
	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}
	
	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}
    
	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}
	
	public ServiceFeeManager getServiceFeeManager() {
		return serviceFeeManager;
	}

	public void setServiceFeeManager(ServiceFeeManager serviceFeeManager) {
		this.serviceFeeManager = serviceFeeManager;
	}
	
	public ScriptClientResponseHandler getResHandler() {
		return resHandler;
	}

	public void setResHandler(ScriptClientResponseHandler resHandler) {
		this.resHandler = resHandler;
	}
	
	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}
	
	public void setTenSplitRollBackAO(TenSplitRollBackAO tenSplitRollBackAO) {
		this.tenSplitRollBackAO = tenSplitRollBackAO;
	}
}