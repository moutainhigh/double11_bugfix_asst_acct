package com.yuwang.pinju.core.trade.ao.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.tenpay.BaseSplitRequestHandler;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.trade.manager.VouchCreateManager;
import com.yuwang.pinju.core.trade.manager.impl.PaySequenceGenerator;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.pay.PayBackLogDO;
import com.yuwang.pinju.domain.order.pay.PaySendLogDO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;

/** <p>Description: </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-20
 */
public  class PlatformRefundBaseAO extends TenPayAbstractBaseAO {
	private PaySequenceGenerator paySequenceGenerator;
	private VouchCreateManager vouchCreateManager;
	/**
	 * <p>Description:  封装 请求参数</p>
	 * @param paramDO
	 * @param vouchPayDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-20
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected void createRefundParamDO(PlatformRefundParamDO paramDO,VouchPayDO vouchPayDO){
		//paramDO.setOrderPayId(vouchPayDO.getOrderPayId());
		//paramDO.setTransactionId(vouchPayDO.getOutPayId());
		paramDO.setTotalFee(vouchPayDO.getRealPayMentamount());
		paramDO.setBuyerId(vouchPayDO.getBuyerId());
		paramDO.setOrderId(vouchPayDO.getOrderId());
	}
	 /**
	 * <p>Description: 封装发送日志参数</p>
	 * @param paramDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-15
	 * @update:[2011-10-24] [MaYuanChao]
	 */
	protected  PaySendLogDO createPaySendLogDO(VouchPayDO vouchPayDO){
		PaySendLogDO sendLogDO = new PaySendLogDO();
		//TODO  需要封装参数
		sendLogDO.setCreationTime(new Date());
		sendLogDO.setGmtModified(new Date());
		sendLogDO.setPayType(REFUND_CMDNO);
		//sendLogDO.setSendType(VouchPayConstant.VOUCH_PLATFORM_REFUND_SEND_TYPE);
		sendLogDO.setSendType(REFUND_CMDNO);
		sendLogDO.setOrderPayId(vouchPayDO.getOrderPayId());
		sendLogDO.setPayUserId(vouchPayDO.getBuyerId());
		sendLogDO.setAcceptUserId(vouchPayDO.getSellerId());
		sendLogDO.setPayAccount(vouchPayDO.getPayAccount());
		sendLogDO.setAcceptAccount(vouchPayDO.getAcceptAccount());
		return sendLogDO;
	}

	/**
	 * <p>Description: 封装回调接受日志参数</p>
	 * @param paramDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-15
	 * @update:[2011-10-24] [MaYuanChao]
	 */
	protected PayBackLogDO createPayBackLogDO(PlatformRefundParamDO paramDO){
		PayBackLogDO backLogDO = new PayBackLogDO();
		backLogDO.setBackInfo(paramDO.getPayResult());
		backLogDO.setCreationTime(new Date());
		backLogDO.setOrderPayId(paramDO.getOrderPayId());
		backLogDO.setGmtModified(new Date());
		backLogDO.setOutPayId(paramDO.getTransactionId());
		backLogDO.setPayType(REFUND_CMDNO);
		//backLogDO.setSendType(VouchPayConstant.VOUCH_PLATFORM_REFUND_SEND_TYPE);
		backLogDO.setSendType(REFUND_CMDNO);
		backLogDO.setBackInfo(paramDO.getBackInfo());
		return backLogDO;
	}
	
	/**
	 * <p>Description: 封装平台退款日志参数</p>
	 * @param paramDO
	 * @param parameters
	 * @param queryString
	 * @return RefundLogDO
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-19
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected RefundLogDO createRefundLogDO(PlatformRefundParamDO paramDO, String refundId,String queryString){
		RefundLogDO refundLogDO = new RefundLogDO();
		refundLogDO.setCmdno(Integer.valueOf(PinjuConstant.TENPAY_PLATFORM_REFUND_CMDNO));
		refundLogDO.setMemo("发起平台退款");
		refundLogDO.setParamDetail(queryString);
		refundLogDO.setPayorderId(paramDO.getOrderPayId());
		refundLogDO.setRefundFee(paramDO.getRefundFee());
		refundLogDO.setRefundId(refundId);
		refundLogDO.setSellerId(paramDO.getBuyerId());
		refundLogDO.setTotalFee(paramDO.getTotalFee());
		refundLogDO.setTransactionId(paramDO.getTransactionId());
		refundLogDO.setOrderId(paramDO.getOrderId());
		return refundLogDO;
	}
	
	/**
	 * <p>Description:卖家ID 
	 *	卖家店铺ID->平台退款金额->退款总金额
	 *	申请人ID  （买家ID）->记录创造时间->修改时间
	 *	REFUND_STATUS->备注->卖家昵称
	 *	买家昵称->主订单ID</p>
	 * @param paramDO
	 * @param vouchPayDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-22
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected TradeRefundManualDO createRefundManualDO(PlatformRefundParamDO paramDO, OrderDO orderDO,VouchPayDO vouchPayDO){
		TradeRefundManualDO manualDO = new TradeRefundManualDO();
		manualDO.setBuyerId(orderDO.getBuyerId());
		manualDO.setBuyerNick(orderDO.getBuyerNick());
		manualDO.setGmtCreate(new Date());
		manualDO.setGmtModified(new Date());
		manualDO.setOrderId(orderDO.getOrderId());
		manualDO.setPlatformRefundAmount(paramDO.getRefundFee());
		manualDO.setRefundState(TradeRefundManualDO.STATUS_PAYBACK_NO);
		manualDO.setSellerId(orderDO.getSellerId());
		manualDO.setSellerNick(orderDO.getSellerNick());
		manualDO.setSellerShopId(orderDO.getShopId());
		manualDO.setOrderPayId(paramDO.getOrderPayId());
		manualDO.setOrderTenpayId(paramDO.getTransactionId());
		manualDO.setOrderRefundId(paramDO.getRefundId());
		manualDO.setRealPayMentamount(vouchPayDO.getRealPayMentamount());
		manualDO.setMemo("平台退款失败手工单");
		return manualDO;
	}
	
	/**
	 * <p>Description: 封装请求参数</p>
	 * @param refundParamDO
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-14
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected void createParameters(PlatformRefundParamDO refundParamDO,BaseSplitRequestHandler reqHandler) {
		reqHandler.setParameter("cmdno", REFUND_CMDNO);						
		reqHandler.setParameter("bargainor_id", TENPAY_PAY_PARTNER);			
		reqHandler.setParameter("transaction_id", refundParamDO.getTransactionId().toString());		
		reqHandler.setParameter("sp_billno", refundParamDO.getOrderPayId().toString());				
		reqHandler.setParameter("total_fee",refundParamDO.getTotalFee().toString());				
		reqHandler.setParameter("fee_type", CURRENCYTYPE);									
		reqHandler.setParameter("refund_fee", refundParamDO.getRefundFee().toString());				
		reqHandler.setParameter("version", REFUND_VERSION);
		
		// TODO 判断是否分账回退调用   return_url 也要改变
		if(StringUtils.isBlank(refundParamDO.getRefundId())){
			reqHandler.setParameter("refund_id",this.createRefundId(TENPAY_PAY_PARTNER));
			refundParamDO.setRefundId(reqHandler.getParameter("refund_id"));
		}else{
			reqHandler.setParameter("refund_id",refundParamDO.getRefundId());
		}
		reqHandler.setParameter("return_url", REFUND_RETURN_URL);
		reqHandler.setKey(getMD5Key());
		reqHandler.setGateUrl(REFUND_PATMENTGATEWAYURL);
		//return reqHandler;
	}
	
	/**
	 * <p>Description:
	 *  同个ID财付通认为是同一笔退款,格式为109+10位商户号+8位日期+7位序列号</p>
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-9
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected String createRefundId(String bargainorId) {
		Long flowNumber = paySequenceGenerator.next(PaySequenceGenerator.seqName,7);
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
		String refundDate = sdformat.format(new Date());
		DecimalFormat df = new DecimalFormat("0000000");
		return REFUND_CODES.concat(bargainorId).concat(refundDate).concat(df.format(flowNumber));
	}
	
	/**
	 * <p>Description: 插入回调接受日志</p>
	 * @param refundParamDO
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-15
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected void createPayBackLog(PlatformRefundParamDO refundParamDO){
		try {
			PayBackLogDO payBackLogDO = this.createPayBackLogDO(refundParamDO);
			vouchCreateManager.insertTradePayBackLog(payBackLogDO);
		} catch (ManagerException e) {
			// TODO: handle exception
			log.error("Event=[TenPlatformRefundAOImpl.createpayBackLog() error", e);
		}
	}
	
	/**
	 * <p>Description:把String 转换成Long</p>
	 * @param val
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-29
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected Long createLong(String val){
		Long _val = 0L;
		try {
			_val = Long.valueOf(val);
		} catch (NumberFormatException e) {
			log.error("PlatformRefundBaseAO.createLong() error", e);
			_val = 0L;
		}
		return _val;
	}
	
	@Override
	protected String getMD5Key() {
		return PinjuConstant.TENPAY_PAY_MD5KEY;
	}
	
/****************************************平台退款配置常量**********************************************************/
	/**
	 * 商户号
	 */
	protected final String TENPAY_PAY_PARTNER =PinjuConstant.TENPAY_PAY_PARTNER;
	/**
	 * 财付通支付网关地址
	 */
	protected final String REFUND_PATMENTGATEWAYURL=PinjuConstant.TENPAY_PLATFORM_REFUND_REFUNDURL;
	
	/**
	 * 财付通业务代码
	 */
	protected final String REFUND_CMDNO=PinjuConstant.TENPAY_PLATFORM_REFUND_CMDNO;
	
	/**
	 * 返回的地址
	 */
	protected final String REFUND_RETURN_URL=PinjuConstant.TENPAY_PLATFORM_REFUND_RETURNURL;
	/**
	 * 版本号
	 */
	protected final String REFUND_VERSION=PinjuConstant.TENPAY_PLATFORM_REFUND_VERSION;
	
	/**
	 * 退款ID的生成规则codes [109]
	 */
	protected final static String REFUND_CODES=PinjuConstant.TENPAY_PLATFORM_REFUN_CODES;
	
	public PaySequenceGenerator getPaySequenceGenerator() {
		return paySequenceGenerator;
	}
	public void setPaySequenceGenerator(PaySequenceGenerator paySequenceGenerator) {
		this.paySequenceGenerator = paySequenceGenerator;
	}
	public VouchCreateManager getVouchCreateManager() {
		return vouchCreateManager;
	}
	public void setVouchCreateManager(VouchCreateManager vouchCreateManager) {
		this.vouchCreateManager = vouchCreateManager;
	}
}


