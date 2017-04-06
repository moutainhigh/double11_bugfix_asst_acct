package com.yuwang.pinju.web.module.pay.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.resultcode.MarginResultCode;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.core.margin.ao.MarginAO;
import com.yuwang.pinju.core.trade.ao.SdoDirectPayAO;
import com.yuwang.pinju.domain.trade.DirectPayReceiveParamDO;
import com.yuwang.pinju.web.message.OrderMessageName;
import com.yuwang.pinju.web.module.BaseAction;

/**
 * 
 * Created on 2011-8-11
 * <p>
 * Discription: 支付返回接Action
 * </p>
 * 
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class SdoDirectPayNotifyAction extends BaseAction implements OrderMessageName {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1849090456044152477L;

	private SdoDirectPayAO sdoDirectPayAO;
	
	private MarginAO marginAO;
	
	/**
	 * Created on 2011-8-11
	 * <p>Discription: 盛付通支付返回服务端接口</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String sdoNotify() {

		try {
			// 获取参数
			String _amount = getString("Amount");// 订单金额
			String _payAmount = getString("PayAmount");// 实际支付金额
			String _orderNo = getString("OrderNo");// 商户订单号
			String _serialNo = getString("serialno");// 支付序列号
			String _status = getString("Status");// 支付状态 "01"表示成功
			String _merchantNo = getString("MerchantNo");// 商户号
			String _payChannel = getString("PayChannel");// 实际支付渠道
			String _discount = getString("Discount");// 实际折扣率
			String _signType = getString("SignType");// 签名方式。1-RSA 2-Md5
			String _payTime = getString("PayTime");// 支付时间
			String _currencyType = getString("CurrencyType");// 货币类型
			String _productNo = getString("ProductNo");// 产品编号
			//描述
			String _productDesc = getString("ProductDesc");
			if(StringUtils.isNotEmpty(_productDesc))
				_productDesc = new String(_productDesc.getBytes(
					"ISO_8859_1"), "UTF-8");
			// 产品描述
			String _remark1 = getString("Remark1");
			if(StringUtils.isNotEmpty(_remark1))
				_remark1 = new String(_remark1.getBytes(
					"ISO_8859_1"), "UTF-8");// 产品备注1
			
			String _remark2 = getString("Remark2");
			if(StringUtils.isNotEmpty(_remark2))
				_remark2 = new String(_remark2.getBytes(
					"ISO_8859_1"), "UTF-8");// 产品备注2
			
			String _exInfo = getString("ExInfo");
			if(StringUtils.isNotEmpty(_exInfo))
				_exInfo = new String(_exInfo.getBytes(
					"ISO_8859_1"), "UTF-8");// 额外的返回信息
			
			String _mac = request.getParameter("MAC");// 签名字符串
			
			
			// 验证盛付通签名
			Result verifyResult = sdoDirectPayAO.verifySign(_amount, _payAmount,
					_orderNo, _serialNo, _status, _merchantNo, _payChannel,
					_discount, _signType, _payTime, _currencyType, _productNo,
					_productDesc, _remark1, _remark2, _exInfo, _mac);

			if (verifyResult.isSuccess()) {
				// 签名成功时通知盛付通必须输出"OK"
				response.getOutputStream().print(PinjuConstant.SHENGPAY_DIRECTPAY_SUCCESSMSG);
				
				DirectPayReceiveParamDO directPayReceiveParamDO = getPayReceiveParamDO(_productNo, _orderNo, _amount, _payAmount, _serialNo, _payTime);
				//支付成功
				boolean payState = _status.equals("01");
				//调用处理
				notifyDelivery(directPayReceiveParamDO,payState);
				
			} else {
				response.getOutputStream().print("签名失败");
			}

	
		} catch (UnsupportedEncodingException e) {
			log.error("Event=[SdoPayNotifyAction#sdoNotify] 盛付通即时到账回调通知发送失败:", e);
			return ERROR;
		} catch (IOException e) {
			log.error("Event=[SdoPayNotifyAction#sdoNotify] 盛付通即时到账回调通知发送失败:", e);
			return ERROR;
		}catch (Exception e) {
			log.error("Event=[SdoPayNotifyAction#sdoNotify] 盛付通即时到账回调处理失败:", e);
			return ERROR;
		}
		return null;
	}
	
	/**
	 * 
	 * Created on 2011-8-15
	 * <p>Discription: 处理参数封装</p>
	 * @param _productNo
	 * @param _orderNo
	 * @param _amount
	 * @param _payAmount
	 * @param _serialNo
	 * @param _payTime
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private DirectPayReceiveParamDO getPayReceiveParamDO(String _productNo,String _orderNo,String _amount,String _payAmount,String _serialNo,String _payTime){
		DirectPayReceiveParamDO payReceiveParamDO = new DirectPayReceiveParamDO();
		if(StringUtils.isNotEmpty(_productNo))
			payReceiveParamDO.setProductNo(Integer.valueOf(_productNo));
		payReceiveParamDO.setOrderNo(Long.valueOf(_orderNo));
		payReceiveParamDO.setAmount(MoneyUtil.getDollarToCent(_amount));
		payReceiveParamDO.setPayAmount(MoneyUtil.getDollarToCent(_payAmount));
		payReceiveParamDO.setSerialno(_serialNo);
		payReceiveParamDO.setPayTime(DateUtil.parse(PinjuConstant.DATE_FORMAT, _payTime));
		payReceiveParamDO.setRevDetail(request.getRequestURI());
		return payReceiveParamDO;
		
	}
	
	
	/**
	 * 
	 * Created on 2011-8-15
	 * <p>Discription: 通知发货</p>
	 * @param _productNo 产品类型
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void notifyDelivery(DirectPayReceiveParamDO directPayReceiveParamDO,boolean payState){
		Result result = null;
		switch (directPayReceiveParamDO.getProductNo()){
			case DirectPayConstant.BIZ_TYPE_MARGIN:
				log.warn("begin sdoDirectPayAO.sdoDirectPayNotify");
				result = sdoDirectPayAO.sdoDirectPayNotify(directPayReceiveParamDO,payState);
				log.warn("end sdoDirectPayAO.sdoDirectPayNotify"+result.isSuccess());
				//更新支付状态失败处理
				if(!result.isSuccess()){
					if(result.getResultCode().equals(MarginResultCode.PAYMARGIN_ORDER_ALREADY))
						log.error("notifyDelivery".concat(ResultCodeMsg.getResultMessage(MarginResultCode.PAYMARGIN_ORDER_ALREADY, directPayReceiveParamDO.getOrderNo())));
					else if(result.getResultCode().equals(MarginResultCode.PAYMARGIN_EXCEPTION)){
						sdoDirectPayAO.upPayStateFailure(directPayReceiveParamDO,payState);	
					}
					break;
				}
				//通知消保发货
				log.warn("begin marginAO.receiveMargin");
				result = marginAO.receiveMargin(directPayReceiveParamDO,payState);
				log.warn("end marginAO.receiveMargin"+result.isSuccess());
				
				//发货失败处理
				if(!result.isSuccess()){
					log.warn("begin sdoDirectPayAO.deliveryFailure"+result.getResultCode());
					sdoDirectPayAO.deliveryFailure(directPayReceiveParamDO,payState);
					log.warn("end sdoDirectPayAO.deliveryFailure"+result.getResultCode());
				}
				break;
				
			default:
				sdoDirectPayAO.no_productNo(directPayReceiveParamDO,payState);
		}
	}
	
	
	public void setSdoDirectPayAO(SdoDirectPayAO sdoDirectPayAO) {
		this.sdoDirectPayAO = sdoDirectPayAO;
	}

	public void setMarginAO(MarginAO marginAO) {
		this.marginAO = marginAO;
	}

}
