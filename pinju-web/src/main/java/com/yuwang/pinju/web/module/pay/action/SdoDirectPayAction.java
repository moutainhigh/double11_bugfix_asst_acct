package com.yuwang.pinju.web.module.pay.action;


import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.trade.ao.SdoDirectPayAO;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * 
 * Created on 2011-8-11
 * <p>
 * Discription: 支付Action
 * </p>
 * 
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class SdoDirectPayAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1849090456044152477L;
	// 商户号
	private final String Version = PinjuConstant.SHENGPAY_DIRECTPAY_VERSION;
	private final String MerchantNo = PinjuConstant.SHENGPAY_DIRECTPAY_MERCHANTNO;
	private final String PostBackURL = PinjuConstant.SHENGPAY_DIRECTPAY_POSTBACKURL;
	private final String NotifyURL = PinjuConstant.SHENGPAY_DIRECTPAY_NOTIFYURL;
	private final String CurrencyType = PinjuConstant.SHENGPAY_DIRECTPAY_CURRENCYTYPE;
	private final String NotifyURLType = PinjuConstant.SHENGPAY_DIRECTPAY_NOTIFYURLTYPE;
	private final String CharSet = PinjuConstant.DEFAULT_CHARSET;
	private final String SignType = PinjuConstant.SHENGPAY_DIRECTPAY_SIGNTYPE;
	private final String paymentGateWayURL = PinjuConstant.SHENGPAY_DIRECTPAY_PAYURL;
	private final String PayChannel = PinjuConstant.SHENGPAY_DIRECTPAY_PAYCHANNEL;
	private String ProductNo;
	private String OrderTime;
	private String OrderNo;
	private String MAC;
	private String Amount;
	private DirectPayParamDO directPayParamDO;
	private SdoDirectPayAO sdoDirectPayAO;

	/**
	 * 
	 * Created on 2011-8-11
	 * <p>
	 * Discription: 盛付通支付
	 * </p>
	 * 
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String sdoSendOrder() {
		try {
			try {
				directPayParamDO.setBuyerIp(ServletUtil.getRemoteNumberIp()); //Add By ShiXing@2011.08.26 Cause:下单数字IP
			} catch (Exception e) {//未获得IP继续后续业务
			}
			Result result = sdoDirectPayAO.sdoDirectPay(directPayParamDO);
			// 生成内部支付订单失败
			if (!result.isSuccess()) {
				/**
				 * TODO 失败处理
				 */
				return ERROR;
			}
			OrderTime = (String) result.getModel("OrderTime");
			MAC = (String) result.getModel("MAC");
			OrderNo = (String) result.getModel("OrderNo");
			Amount = (String) result.getModel("Amount");
			ProductNo = String.valueOf(directPayParamDO.getBizType());
		} catch (Exception e) {
			log.error("Event=[SdoPayAction#sdoSendOrder] 盛付通即时到账支付生成失败:", e);
			return ERROR;
		}
		return SUCCESS;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getVersion() {
		return Version;
	}

	public String getMerchantNo() {
		return MerchantNo;
	}

	public String getPostBackURL() {
		return PostBackURL;
	}

	public String getNotifyURL() {
		return NotifyURL;
	}

	public String getCurrencyType() {
		return CurrencyType;
	}

	public String getNotifyURLType() {
		return NotifyURLType;
	}

	public String getCharSet() {
		return CharSet;
	}

	public String getSignType() {
		return SignType;
	}

	public String getPaymentGateWayURL() {
		return paymentGateWayURL;
	}

	public String getOrderTime() {
		return OrderTime;
	}

	public String getOrderNo() {
		return OrderNo;
	}


	public void setOrderTime(String orderTime) {
		OrderTime = orderTime;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getPayChannel() {
		return PayChannel;
	}

	public DirectPayParamDO getDirectPayParamDO() {
		return directPayParamDO;
	}


	public void setDirectPayParamDO(DirectPayParamDO directPayParamDO) {
		this.directPayParamDO = directPayParamDO;
	}


	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getProductNo() {
		return ProductNo;
	}

	public void setProductNo(String productNo) {
		ProductNo = productNo;
	}


	public void setSdoDirectPayAO(SdoDirectPayAO sdoDirectPayAO) {
		this.sdoDirectPayAO = sdoDirectPayAO;
	}



}
