package com.yuwang.pinju.core.trade.ao.impl;


import com.shanda.security.MD5;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.MarginResultCode;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.core.trade.ao.SdoDirectPayAO;
import com.yuwang.pinju.core.trade.manager.DirectManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.trade.DirectOrderDO;
import com.yuwang.pinju.domain.trade.DirectPayDO;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.domain.trade.DirectPayReceiveParamDO;
import com.yuwang.pinju.domain.trade.DirectPayRevLogDO;
import com.yuwang.pinju.domain.trade.DirectPaySendLogDO;

/**
 * Created on 2011-8-10
 * <p>Discription: 盛付通即时到账AO</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class SdoDirectPayAOImpl extends BaseAO implements SdoDirectPayAO {

	private DirectManager directManager;


	@Override
	public Result sdoDirectPay(DirectPayParamDO directPayParamDO) {

		Result result = new ResultSupport();
		try {
			final String orderTime = DateUtil
					.formatDate(PinjuConstant.DATE_FORMAT, directPayParamDO
							.getCreateTime());

			final String amount = MoneyUtil.getCentToDollar(getOrderAmount(
					directPayParamDO.getItemPrice(), directPayParamDO
							.getBuyAmount()));

			final String productNo = String.valueOf(directPayParamDO
					.getBizType());
			
			final Long payOrderId = directManager.getPayOrderId();

			final Long orderId = directManager.getOrderId();
			
			final String sendDetail = getSendDetail(amount, payOrderId, orderTime, String.valueOf(directPayParamDO.getBizType()));
			
			DirectOrderDO directOrderDO = DirectOrderDO.createDirectOrderDO(directPayParamDO,orderId,payOrderId);

			DirectPayDO directPayDO = DirectPayDO.createDirectPayDO(directPayParamDO,payOrderId,orderId);

			DirectPaySendLogDO marginSendLogDO = DirectPaySendLogDO.createDirectPaySendLogDO(directPayParamDO,payOrderId,sendDetail);
	
			directManager.insertDirectOrderRecord(directOrderDO,directPayDO, marginSendLogDO);

			result.setModel("MAC", stringToMD5(amount, payOrderId, orderTime,productNo));

			result.setModel("OrderTime", orderTime);

			result.setModel("OrderNo", String.valueOf(payOrderId));

			result.setModel("Amount", amount);

			result.setModel("ProductNo", productNo);

		} catch (ManagerException e) {
			log.error("Event=[SdoDirectPayAOImpl#sdoDirectPay] 盛付通生成支付出错:", e);
			result.setSuccess(false);
		} 

		return result;
	}

	@Override
	public Result sdoDirectPayNotify(
			DirectPayReceiveParamDO directPayReceiveParamDO,boolean payState) {
		Result result = new ResultSupport();
		try {
			
			Integer orderState = DirectPayConstant.DIRECTPAY_ORDER_STATUS_FAIL;
			Integer orderPayState = DirectPayConstant.DIRECTPAY_PAY_STATUS_FAIL;
			if(payState){
				orderState = DirectPayConstant.DIRECTPAY_ORDER_STATUS_SUCCESS;
				orderPayState = DirectPayConstant.DIRECTPAY_PAY_STATUS_SUCCESS;
			}
			final DirectOrderDO  directOrderDO = DirectOrderDO.upRevDirectOrderDO(directPayReceiveParamDO,orderState);
			final DirectPayDO  directPayDO = DirectPayDO.upRevDirectPayDO(directPayReceiveParamDO,orderPayState);
			final DirectPayRevLogDO directPayRevLogDO = DirectPayRevLogDO.createPayRevLogDODirectPayRevLogDO(directPayReceiveParamDO);
			DirectOrderDO directOrderDOQuery = directManager.getDirectOrderDOById(directOrderDO.getPayOrderId());
			//判断本订单是否已正常处理,如果正常处理,则不做任何操作
			if (directOrderDOQuery!=null && directOrderDOQuery.getOrderState()==DirectPayConstant.DIRECTPAY_ORDER_STATUS_SUCCESS) {
				result.setSuccess(false);
				result.setResultCode(MarginResultCode.PAYMARGIN_ORDER_ALREADY);
				return result;
			}
			result.setSuccess(directManager.updateDirectReceiveOrderRecord(directOrderDO, directPayDO, directPayRevLogDO));
		} catch (ManagerException e) {
			log.error("Event=[SdoDirectPayAOImpl#sdoDirectPayNotify] 盛付通支付回调更新状态出错:", e);
			result.setResultCode(MarginResultCode.PAYMARGIN_EXCEPTION);
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public Result verifySign(String amount, String payAmount, String orderNo,
			String serialNo, String status, String merchantNo,
			String payChannel, String discount, String signType,
			String payTime, String currencyType, String productNo,
			String productDesc, String remark1, String remark2, String exInfo,
			String mac) {
		Result result = new ResultSupport();
		StringBuffer toSignString = new StringBuffer();
		toSignString.append(amount).append("|");
		toSignString.append(payAmount).append("|");
		toSignString.append(orderNo).append("|");
		toSignString.append(serialNo).append("|");
		toSignString.append(status).append("|");
		toSignString.append(merchantNo).append("|");
		toSignString.append(payChannel).append("|");
		toSignString.append(discount).append("|");
		toSignString.append(signType).append("|");
		toSignString.append(payTime).append("|");
		toSignString.append(currencyType).append("|");
		toSignString.append(productNo).append("|");
		toSignString.append(productDesc).append("|");
		toSignString.append(remark1).append("|");
		toSignString.append(remark2).append("|");
		toSignString.append(exInfo);
		final boolean flag = _verifySign(toSignString.toString(), mac, "|" + getMD5Key());
		result.setSuccess(flag);
		return result;
	}
	@Override
	public void no_productNo(DirectPayReceiveParamDO directPayReceiveParamDO,
			boolean payState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upPayStateFailure(
			DirectPayReceiveParamDO directPayReceiveParamDO, boolean payState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliveryFailure(
			DirectPayReceiveParamDO directPayReceiveParamDO, boolean payState) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * Created on 2011-8-15
	 * <p>
	 * Discription: 获取订单金额
	 * </p>
	 * 
	 * @param itemPrice
	 * @param buyAmount
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Long getOrderAmount(Long itemPrice, Integer buyAmount) {
		return itemPrice * buyAmount;
	}
	/**
	 * 
	 * Created on 2011-8-15
	 * <p>Discription: 得到发送报文</p>
	 * @param amount
	 * @param payOrderId
	 * @param dateString
	 * @param productNo
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String getSendDetail(String amount, Long payOrderId,
			String dateString, String productNo){
		StringBuffer parm = new StringBuffer();
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_PAYURL).append("?");
		parm.append("Version=");
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_VERSION).append("&");
		parm.append("Amount=");
		parm.append(amount).append("&");
		parm.append("OrderNo=");
		parm.append(payOrderId).append("&");
		parm.append("MerchantNo=");
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_MERCHANTNO).append("&");
		parm.append("PayChannel=");
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_PAYCHANNEL).append("&");
		parm.append("PostBackURL=");
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_POSTBACKURL).append("&");
		parm.append("NotifyURL=");
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_NOTIFYURL).append("&");
		parm.append("OrderTime=");
		parm.append(dateString).append("&");
		parm.append("CurrencyType=");
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_CURRENCYTYPE).append("&");
		parm.append("NotifyURLType=");
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_NOTIFYURLTYPE).append("&");
		parm.append("SignType=");
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_SIGNTYPE).append("&");
		parm.append("ProductNo=");
		parm.append(productNo).append("&");
		parm.append("CharSet=");
		parm.append(PinjuConstant.DEFAULT_CHARSET).append("&");
		parm.append("MAC=");
		parm.append(stringToMD5(amount,payOrderId,dateString,productNo));
		return parm.toString();
	}
	

	
	/**
	 * 
	 * Created on 2011-8-10
	 * <p>
	 * Discription: 使用盛付通MD5加密
	 * </p>
	 * 
	 * @param origin
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private static String stringToMD5(String amount,Long payOrderId,
			String dateString, String productNo) {
		final String sign = setSign(amount, payOrderId, dateString, productNo) + getMD5Key();
		return MD5.getMD5(sign.getBytes());
	}

	/**
	 * TODO Created on 2011-8-11
	 * <p>
	 * Discription: sdo.md5.key 后续在配置文件中提供加密串,使用前在这里进行解密
	 * </p>
	 * 
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private static String getMD5Key() {
		return PinjuConstant.SHENGPAY_DIRECTPAY_MD5KEY;
	}

	/**
	 * 
	 * Created on 2011-8-11
	 * <p>
	 * Discription: 封装签名串 封装顺序需按盛付通文档要求
	 * 签名顺序 Origin＝Version + Amount + OrderNo + MerchantNo + MerchantUserId
	 * + PayChannel + PostBackUrl + NotifyUrl + BackUrl + OrderTime +
	 * CurrencyType + NotifyUrlType + SignType + ProductNo + ProductDesc +
	 * Remark1 + Remark2 + BankCode + DefaultChannel + CharSet
	 * +ExterInvokeIp；
	 * 非必须 MerchantUserId + PayChannel BackUrl ProductNo ProductDesc +
	 * Remark1 + Remark2 + BankCode + DefaultChannel +ExterInvokeIp；
	 * </p>
	 * @param amount
	 * @param orderId
	 * @param dateString
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private static String setSign(String amount,Long payOrderId,
			String dateString, String productNo) {
		StringBuffer parm = new StringBuffer();
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_VERSION);
		parm.append(amount);
		parm.append(payOrderId);
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_MERCHANTNO);
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_PAYCHANNEL);
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_POSTBACKURL);
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_NOTIFYURL);
		parm.append(dateString);
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_CURRENCYTYPE);
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_NOTIFYURLTYPE);
		parm.append(PinjuConstant.SHENGPAY_DIRECTPAY_SIGNTYPE);
		parm.append(productNo);
		parm.append(PinjuConstant.DEFAULT_CHARSET);
		return parm.toString();
	}
	
	
	/**
	 * 
	 * Created on 2011-8-11
	 * <p>
	 * Discription:
	 * </p>
	 * 
	 * @param signString
	 * @param mac
	 * @param signTypeCode
	 * @param key
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private static boolean _verifySign(String signString, String mac,
			String md5Key) {
		String signResult = signString + md5Key;
		signResult = MD5.getMD5(signResult.getBytes());
		return signResult.equalsIgnoreCase(mac);
	}

	public void setDirectManager(DirectManager directManager) {
		this.directManager = directManager;
	}

	

}
