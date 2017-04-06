package com.yuwang.pinju.web.module.pay.action;

import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.web.message.OrderMessageName;

public class TenpaySingleNotifyAction extends TenPayNotifyBaseAction {
	
	protected boolean verifySign() {
		String _sign = getString("sign");// 签名字符串
		//return tenDirectPayAO.verifySign(parameters, _sign);
		return true;
	}
	
	@Override
	protected boolean isTenState() {
		return StringUtils
		.equalsIgnoreCase(getParameters().get("trade_state"),
				PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
	}
	
	@Override
	protected boolean notifyDelivery() {
		return true;
	}
	
	@Override
	protected Integer getBizType() {
		if (super.bizType!=null) {
			return super.bizType;
		}
		return getInteger("attach");
	}
	
	@Override
	protected SortedMap<String, String> setParameters(SortedMap<String, String> parameters) {
		parameters.put("trade_mode", getString("trade_mode"));// 1-即时到账
		parameters.put("trade_state", getString("trade_state"));// 支付结果：0—成功
		parameters.put("partner", getString("partner"));// 商户号
		parameters.put("bank_type", getString("bank_type"));// 银行类型
		parameters.put("total_fee", getString("total_fee"));// 支付金额，单位为分，如果discount有值，通知的total_fee
		parameters.put("fee_type", getString("fee_type"));// 现金支付币种
		parameters.put("notify_id", getString("notify_id"));// 支付结果通知id
		parameters.put("transaction_id", getString("transaction_id"));// 财付通交易号
		parameters.put("out_trade_no", getString("out_trade_no"));// 商户系统的订单号
		parameters.put("time_end", getString("time_end"));// 支付完成时间，格式为yyyyMMddhhmmss
		parameters.put("sign_type", getString("sign_type"));// 签名方式
		parameters.put("input_charset", getString("input_charset"));// 字符编码
		parameters.put("pay_info", getString("pay_info"));// 支付结果信息，支付成功时为空
		parameters.put("bank_billno", getString("bank_billno"));// 银行订单号，若为财付通余额支付则为空
		parameters.put("attach", getString("attach"));// 商家数据包，原样返回
		parameters.put("discount", getString("discount"));// 折扣金额单位为分
		return parameters;
	}
//	
//	@Override
//	protected void setBizType() {
//		 String stringBizType =  parameters.get("attach");
//		 if(StringUtil.isNumeric(stringBizType)){
//			 super.bizType = Integer.getInteger(stringBizType);
//		 }else {
//			 super.bizType = 0;
//		}
//	}
//	
//	@Override
//	protected boolean payState() {
//		return StringUtils
//		.equalsIgnoreCase(parameters.get("trade_state"),
//				PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
//	}
}
