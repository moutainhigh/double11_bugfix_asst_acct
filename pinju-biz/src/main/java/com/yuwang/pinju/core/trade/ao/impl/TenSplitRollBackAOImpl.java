package com.yuwang.pinju.core.trade.ao.impl;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.tenpay.BaseSplitRequestHandler;
import com.yuwang.pinju.core.common.tenpay.ScriptClientResponseHandler;
import com.yuwang.pinju.core.common.tenpay.TenpayHttpClient;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.TenpayResultCodeEnum;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.trade.ao.TenPlatformRefundAO;
import com.yuwang.pinju.core.trade.ao.TenSplitRollBackAO;
import com.yuwang.pinju.core.trade.manager.RefundLogManager;
import com.yuwang.pinju.core.trade.manager.TenSplitRollBackManager;
import com.yuwang.pinju.core.trade.manager.VouchCreateManager;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.trade.manager.impl.PaySequenceGenerator;
import com.yuwang.pinju.core.util.DateUtil;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.domain.order.pay.PayBackLogDO;
import com.yuwang.pinju.domain.order.pay.PaySendLogDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.trade.TenSplitRollBackDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;


/**
 * Created on 2011-8-31
 * 
 * @see <p>Discription:财富通分账回退</p>
 * @return
 * @author:[曹晓]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class TenSplitRollBackAOImpl extends TenPayAbstractBaseAO implements TenSplitRollBackAO {
	
	//财付通签名类型
	private final String CMDNO = PinjuConstant.TENPAY_SPLITREFUND_CMDNO;
	private final String VERSION = PinjuConstant.TENPAY_SPLITREFUND_VERSION;
	private final String BUSTYPE = PinjuConstant.TENPAY_SPLITREFUND_BUSTYPE;
	private final String REFUNDGATEWAYURL = PinjuConstant.TENPAY_SPLITREFUND_REFUNDGATEWAYURL;
	private final String SPLITREFUNDURL = PinjuConstant.TENPAY_SPLITREFUND_URL;
	private final String TENPAYPINJUACCOUNT = PinjuConstant.TENPAY_PAY_PINJU_ACCOUNT;
	private final String REFUNDID = PinjuConstant.TENPAY_SPLITREFUND_REFUNDID;
	private final String TENPAY_PAY_PARTNER =PinjuConstant.TENPAY_PAY_PARTNER;
	
	private RefundManager refundManager;
	private VouchQueryManager vouchQueryManager;
	private RefundLogManager refundLogManager;
	private VouchCreateManager vouchCreateManager;
	private PaySequenceGenerator paySequenceGenerator;
	private MemberManager memberManager;
	private TenSplitRollBackManager tenSplitRollBackManager;
	private TenPlatformRefundAO tenPlatformRefundAO;
	
	/**
	 *  CA证书名
	 */
	public final static String CACERT_NAME = PinjuConstant.TENPAY_CACERT_NAME;
	/**
	 * 个人(商户)证书
	 */
	public final static String CERTIFICATE_NAME = PinjuConstant.TENPAY_PAY_PARTNER.concat(".pfx");
	
	
	public Result tenSplitRollBack(TenSplitRollBackDO tenSplitRollBackDO) {
		PlatformRefundParamDO sendParamDO = null;
		TenSplitRollBackDO receiveParamDO = null;
		Result result = new ResultSupport();
		
		TenpayHttpClient httpClient = new TenpayHttpClient();
		httpClient.setCaInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(CACERT_NAME)));
		httpClient.setCertInfo(new File(PinjuConstant.TENPAY_CERTIFICATE_PATH.concat(CERTIFICATE_NAME)), PinjuConstant.TENPAY_PAY_PARTNER);
		ScriptClientResponseHandler resHandler = new ScriptClientResponseHandler();
		
		try {
			Result rlt = this.createSplitRollBackParam(tenSplitRollBackDO);
			String postUrl = ((String) rlt.getModel("parametersUrl"));
			tenSplitRollBackDO = ((TenSplitRollBackDO) rlt.getModel("tenSplitRollBackDO"));
			
			// 设置请求内容
			httpClient.setReqContent(postUrl);
			int reQuestNum = 0;
			int reQuestNum1 = 0;
			boolean isRequest = true;
			while (isRequest) {
				if (httpClient.call()) {
					// 设置结果参数
					resHandler.setContent(httpClient.getResContent());
					// 获取返回参数
					receiveParamDO = this.getReceiveParamDO(resHandler);

					if (isTenState(resHandler.getParameter("pay_result"))) {
						// 返回结果成功的话,再判断验签
						if (!verifySign(resHandler.getAllParameters(),resHandler.getParameter("sign"))) {
							isRequest = false;
							log.warn("验证签名失败 "+receiveParamDO.getPayResult()+receiveParamDO.getPayInfo());
						}
						//更新日志 退款日志为成功
						sendParamDO = this.updateTenSplitRollbackSuccess(receiveParamDO);
						//调用到平台退款
						tenPlatformRefundAO.platformRefund(sendParamDO);
						result.setSuccess(true);
						return result;
					} else {
						// 如果失败 并且是指定的 resultCode
						if (TenpayResultCodeEnum.verifyResultCode(receiveParamDO.getPayResult())) {
							isRequest = true;
							reQuestNum++;
						} else {
							// 如果失败 并且不是指定的ResultCode,直接插单和更改退款和子订单状态为失败
							boolean flag = this.updateTenSplitRollbackFail(receiveParamDO);
							result.setSuccess(flag);
							if (!flag)
								log.warn("insert refund  manual order is error "+receiveParamDO.getPayResult()+receiveParamDO.getPayInfo());
							
							return result;
						}
					}
				} else {
					// 有可能因为网络原因，请求已经处理，但未收到应答。
					log.warn(httpClient.getResponseCode() + ":"+ httpClient.getErrInfo()+"后台调用通信失败");
					isRequest = true;
					reQuestNum1++;
				}
				if(reQuestNum1 > 5){
					isRequest = false;
					//发送超时手工插入
					boolean flag = this.updateTenSplitRollbackFail(tenSplitRollBackDO);
					result.setSuccess(flag);
					if (!flag)
						log.warn("send timeout refundId is a "+receiveParamDO.getRefundId());
					return result;
				}
				if(reQuestNum > 5 ){
					isRequest = false;
					//返回错误手工插入
					boolean flag = this.updateTenSplitRollbackFail(receiveParamDO);
					result.setSuccess(flag);
					if (!flag)
						log.warn("insert refund  manual order is error "+receiveParamDO.getPayResult()+receiveParamDO.getPayInfo());
					return result;
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			log.error("TenSplitRollBackAction#tenSplitRollBack#Exception HTML="+ resHandler.getContent(), e);
		}
		
		return result;
	}
	
	/**
	 * 	向财付通发送分账回退请求
	 *	1、根据支付id查询支付表获得主订单id
	 *	2、根据主订单id查询退款表获得需要退款的子订单id和退款金额 
	 *	3、根据子订单id查询子订单表获得平台分账费率和金额并计算退款金额  ??平台帐户=auto配置文件
	 *	4、根据子订单id查询分销商表获得分销商分账费率和金额并计算退款金额 ??分销商帐户=暂为分销商id
	 *	5、卖家退款金额=退款总额-平台退款金额-分销商退款金额 ??卖家帐户=卖家编号
	 *	6、插入发送日志
	 *	7、插入退款日志
	 *	7、拼接发送参数,向财付通发送请求
	 *	8、接收财付通返回参数，失败的话插入工单表，成功的话调用平台退款
	 *	9、成功的话更新退款日志
	 *	
	 *	update by 2011-10-31
	 *	分账回退退款不再计算分账费率，直接退款总金额从卖家账户扣除 
	 */
	public Result createSplitRollBackParam(TenSplitRollBackDO tenSplitRollBackDO){
		Result result = new ResultSupport();
		BaseSplitRequestHandler reqHandler = new BaseSplitRequestHandler(null, null);
		
		try{
			VouchPayDO orderPay = queryOrderPayById(tenSplitRollBackDO.getOrderId());
			
			tenSplitRollBackDO.setOrderAmount(orderPay.getOrderAmount());
			
			RefundLogDO refundLogDO = refundLogManager.queryRefundLog(tenSplitRollBackDO.getTransaction_id(), tenSplitRollBackDO.getOrderId(), Integer.parseInt(CMDNO));
			
			tenSplitRollBackDO = refundAccountInfo(orderPay, tenSplitRollBackDO);
			
			if (refundLogDO != null) {
				tenSplitRollBackDO.setRefundId(refundLogDO.getRefundId());
			} else {
				tenSplitRollBackDO.setRefundId(this.getRefundId(TENPAY_PAY_PARTNER));
			}
		
			reqHandler.init();
			reqHandler = createParameters(tenSplitRollBackDO,reqHandler);
			
//			SortedMap<String, String> parameters = createParameters(tenSplitRollBackDO);
//			this.createSignByGbk(parameters);
//			final String sendDetail = this.parametersToString(parameters);
//			String parametersUrl = REFUNDGATEWAYURL.concat("?").concat(sendDetail);
			String	parametersUrl = reqHandler.getRequestURL();
			
			//插入发送日志、退款日志
//			if (refundLogDO == null) {
			insertRefundLog(orderPay, reqHandler.getRequestURL(), tenSplitRollBackDO, refundLogDO);
//			}
			
			result.setModel("tenSplitRollBackDO", tenSplitRollBackDO);
			result.setModel("parametersUrl", parametersUrl);
			result.setSuccess(true);
		} catch (ManagerException e) {
			log.error("Event=[TenSplitRollBackAOImpl#createSplitRollBackParam]:", e);
			result.setSuccess(false);
		} catch (UnsupportedEncodingException e) {
			log.error("Event=[TenSplitRollBackAOImpl#createSplitRollBackParam]:", e);
			result.setSuccess(false);
		}
		return result;
	}
	
	
	/**
	 * 插入回调流水日志，更新退款日志状态
	 */
	public PlatformRefundParamDO updateTenSplitRollbackSuccess(TenSplitRollBackDO tenSplitRollbackDO) {
		PlatformRefundParamDO prd = new PlatformRefundParamDO();
		
		VouchPayDO orderPay = queryOrderPayById(tenSplitRollbackDO.getOrderId());
		
		PayBackLogDO payBackLogDO = new PayBackLogDO();
		payBackLogDO.setSendType(CMDNO);
		payBackLogDO.setOrderPayId(orderPay.getOrderPayId());
		payBackLogDO.setOutPayId(orderPay.getOutPayId());
		payBackLogDO.setBackInfo(tenSplitRollbackDO.getPayInfo());
		payBackLogDO.setPayType(CMDNO);
		payBackLogDO.setCreationTime(new Date());
		insertSplitRollbackBackLog(payBackLogDO);
		
		try{
			//更新退款日志表
			RefundLogDO refundLogDO = refundLogManager.queryRefundLogByRefundAndCmdnoId(tenSplitRollbackDO.getRefundId(), Integer.parseInt(CMDNO));
			refundLogDO.setRefundState(RefundLogDO.REFUND_LOG_IS_SUCCESS);
			refundLogManager.updateTradeRefundLog(refundLogDO);
			
			//传递发送参数
			prd.setOrderPayId(orderPay.getOrderPayId());
			prd.setRefundId(tenSplitRollbackDO.getRefundId());
			prd.setRefundFee(tenSplitRollbackDO.getRefundTotalSum());
			prd.setTransactionId(tenSplitRollbackDO.getTransaction_id());
			
		} catch (ManagerException e) {
			log.error("Event=[TenSplitRollBackAOImpl#isSplitRollBackStatusDispose]:", e);
		}
		return prd;
	}
	
	/**
	 * 更新退款状态，更新子订单状态，插入子订单日志等
	 */
	public boolean updateTenSplitRollbackFail(TenSplitRollBackDO tenSplitRollbackDO) {
		boolean flag = false;
		VouchPayDO orderPay = queryOrderPayById(tenSplitRollbackDO.getOrderId());
		try {
			flag = tenSplitRollBackManager.updateSplitRollbackStatus(orderPay,tenSplitRollbackDO,CMDNO);
		} catch (ManagerException e) {
			log.error("Event=[TenSplitRollBackAOImpl#updateTenSplitRollbackFail]:", e);
		}
		return flag;
	}
	
	/**
	 * 根据支付id获取支付信息 
	 * @param id
	 * @return
	 */
	private VouchPayDO queryOrderPayById(String orderPayId) {
		VouchPayDO reOrderPay = new VouchPayDO();
		VouchPayDO orderPay = new VouchPayDO();
		orderPay.setOrderPayId(orderPayId.toString());
		try {
			reOrderPay = vouchQueryManager.selectOrderPay(orderPay);
		} catch (ManagerException e) {
			log.error("Event=[TenSplitRollBackAOImpl#queryOrderPayById]:", e);
		}
		return reOrderPay;
	}
	
	/**
	 * 获取订单总金额、退款总金额、退款信息(退款总额| (账户^退款金额)[|(账户^退款金额)]*)
	 * @param tenSplitRollBackDO
	 * @return
	 */
	private TenSplitRollBackDO refundAccountInfo(VouchPayDO orderPay, TenSplitRollBackDO tenSplitRollBackDO) {
		try {
			//买家申请退款总金额
			long refundSum = 0L;
			
			//卖家财付通账户
			String sellerTenId = null;
//			String sellerTenId = "yuanliyan@zba.com";
			
			//财付通退款参数信息
			StringBuffer refundInfo = new StringBuffer();
			
			List<RefundDO> refundList = refundManager.queryRefundByOrderId(orderPay.getOrderId());
			for (RefundDO rd : refundList) {
				if(rd.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE) != 0)
					continue;
				
				//根据卖家id查询卖家财付通账户
				MemberPaymentDO buyPaymentDO = memberManager.findBoundMemberPayment(new MemberPaymentDO(rd.getSellerId(), MemberPaymentDO.INSTITUTION_TENPAY));
				if(buyPaymentDO != null && StringUtil.isNotEmpty(buyPaymentDO.getAccountNO())){
					sellerTenId = buyPaymentDO.getAccountNO();
				}
				
				//累加退款总金额
				refundSum += rd.getApplySum();
			}
			
			refundInfo.append(refundSum).append("|").append(sellerTenId).append("^").append(refundSum);
			
			tenSplitRollBackDO.setRefundTotalSum(refundSum);
			tenSplitRollBackDO.setAccountRefund(refundInfo.toString());
		} catch (ManagerException e) {
			log.error("TenSplitRollBackAOImpl#refundAccountInfo 计算分账金额出错：",e);
		}
		
		return tenSplitRollBackDO;
	}
	
	/**
	 * 生成退款id
	 * 生成规则:109＋spid+YYYYMMDD+7位流水号
	 * @return
	 */
	private  String getRefundId(String bargainorId) {
		String date = DateUtil.formatDateTime("yyyyMMdd");
		
		Long l = paySequenceGenerator.next(paySequenceGenerator.seqName,7);
		DecimalFormat df = new DecimalFormat("0000000");
		
		String serialNumber = df.format(l);
		
		String refundId = REFUNDID+bargainorId+date+serialNumber;
		
		return refundId;
	}

	/**
	 * 插入流水日志以及退款日志
	 */
	private void insertRefundLog(VouchPayDO orderPay, String paramString, TenSplitRollBackDO tenSplitRollBackDO, RefundLogDO refundLogDOold) {
		PaySendLogDO paySendLogDO = new PaySendLogDO();
		paySendLogDO.setPayUserId(orderPay.getBuyerId());		//支付用户内部账号
		paySendLogDO.setAcceptUserId(orderPay.getSellerId());	//收款用户内部账号
		paySendLogDO.setPayType(CMDNO); 	// 支付平台类型
		paySendLogDO.setOrderPayId(orderPay.getOrderPayId()); 		// 内部支付订单编号
		paySendLogDO.setSendInfo(paramString);
		paySendLogDO.setPayAccount(orderPay.getOutPayId());		//付款第三方账户
		paySendLogDO.setAcceptAccount(orderPay.getAcceptAccount()); 	//收款第3方账户
		paySendLogDO.setSendType(CMDNO); 	// 发送类型
		paySendLogDO.setCreationTime(new Date());
		
		RefundLogDO refundLogDO = new RefundLogDO();
		refundLogDO.setCmdno(Integer.parseInt(CMDNO));
		refundLogDO.setMemo("发送分账回退参数");
		refundLogDO.setParamDetail(paramString);
		refundLogDO.setPayorderId(orderPay.getOrderPayId());
		refundLogDO.setRefundFee(tenSplitRollBackDO.getRefundTotalSum());
		refundLogDO.setRefundId(tenSplitRollBackDO.getRefundId());
		refundLogDO.setSellerId(orderPay.getBuyerId());
		refundLogDO.setTotalFee(tenSplitRollBackDO.getOrderAmount());
		refundLogDO.setTransactionId(tenSplitRollBackDO.getTransaction_id());
		refundLogDO.setOrderId(orderPay.getOrderId());
		refundLogDO.setRefundState(RefundLogDO.REFUND_LOG_IS_FAIL);
		
		try {
			vouchCreateManager.insertTradePaySendLog(paySendLogDO);
			if (refundLogDOold == null) {
				refundLogManager.insertTradeRefundLog(refundLogDO);
			}
		} catch (ManagerException e) {
			log.error("TenSplitRollBackAOImpl#insertRefundLog 插入退款日志出错：",e);
		}
	}
	
	/**
	 * 插入回调接收日志
	 */
	public void insertSplitRollbackBackLog(PayBackLogDO payBackLogDO) {
		try {
			vouchCreateManager.insertTradePayBackLog(payBackLogDO);
		} catch (ManagerException e) {
			log.error("TenSplitRollBackAOImpl#insertSplitRollbackBackLog 插入退款接收日志出错：",e);
		}
	}
	
	/**
	 * 
	 * Created on 2011-9-13
	 * <p>Discription:封装财付通分账回退发送接口参数
	 * </p>
	 * @author:[曹晓]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private BaseSplitRequestHandler createParameters(TenSplitRollBackDO tenSplitRollBackDO,BaseSplitRequestHandler reqHandler) throws ManagerException {
		reqHandler.setParameter("cmdno", CMDNO);
		reqHandler.setParameter("bargainor_id", TENPAY_PAY_PARTNER);
		reqHandler.setParameter("transaction_id", tenSplitRollBackDO.getTransaction_id());
		reqHandler.setParameter("sp_billno",tenSplitRollBackDO.getOrderId());
		reqHandler.setParameter("total_fee", tenSplitRollBackDO.getOrderAmount().toString());
		reqHandler.setParameter("fee_type", CURRENCYTYPE);
		reqHandler.setParameter("sign", "");
		reqHandler.setParameter("refund_id", tenSplitRollBackDO.getRefundId());
		reqHandler.setParameter("return_url", SPLITREFUNDURL);
		reqHandler.setParameter("bus_type", BUSTYPE);
		reqHandler.setParameter("bus_args", tenSplitRollBackDO.getAccountRefund());
		reqHandler.setParameter("version", VERSION);
		reqHandler.setKey(getMD5Key());
		reqHandler.setGateUrl(REFUNDGATEWAYURL);
		return reqHandler;
	}
	
	private TenSplitRollBackDO getReceiveParamDO(ScriptClientResponseHandler resHandler){
		TenSplitRollBackDO paramDO= new TenSplitRollBackDO();
		paramDO.setRefundId(resHandler.getParameter("refund_id"));
		paramDO.setRefundTotalSum(Long.parseLong(resHandler.getParameter("bus_args").split("\\|")[0]));
		paramDO.setAccountRefund(resHandler.getParameter("bus_args"));
		paramDO.setTransaction_id(resHandler.getParameter("transaction_id"));
		paramDO.setOrderId(resHandler.getParameter("sp_billno"));
		paramDO.setPayResult(resHandler.getParameter("pay_result"));
		paramDO.setPayInfo(resHandler.getParameter("pay_info"));
		
		return paramDO;
	}
	
	protected boolean isTenState(String payResult) {
		return StringUtils.equalsIgnoreCase(payResult,PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
	}
	
	
	@Override
	public boolean verifySign(SortedMap<String, String> parameters,String signString){
		return super.verifySignByGbk(parameters, signString);
	}
	
	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}

	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}
	
	public void setRefundLogManager(RefundLogManager refundLogManager) {
		this.refundLogManager = refundLogManager;
	}


	public void setVouchCreateManager(VouchCreateManager vouchCreateManager) {
		this.vouchCreateManager = vouchCreateManager;
	}
	
	public PaySequenceGenerator getPaySequenceGenerator() {
		return paySequenceGenerator;
	}

	public void setPaySequenceGenerator(PaySequenceGenerator paySequenceGenerator) {
		this.paySequenceGenerator = paySequenceGenerator;
	}

	public void setTenSplitRollBackManager(
			TenSplitRollBackManager tenSplitRollBackManager) {
		this.tenSplitRollBackManager = tenSplitRollBackManager;
	}

	@Override
	protected String getMD5Key() {
		return PinjuConstant.TENPAY_PAY_MD5KEY;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setTenPlatformRefundAO(TenPlatformRefundAO tenPlatformRefundAO) {
		this.tenPlatformRefundAO = tenPlatformRefundAO;
	}


	




	





	
	

}
