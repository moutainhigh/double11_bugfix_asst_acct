package com.yuwang.pinju.web.module.pay.action;

import java.util.SortedMap;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.core.trade.ao.TenDirectPayAO;
import com.yuwang.pinju.domain.trade.DirectPayReceiveParamDO;
import com.yuwang.pinju.web.message.OrderMessageName;

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
public class TenDirectPayNotifyAction extends TenPayNotifyBaseAction implements
		OrderMessageName {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1849090456044152477L;

	private TenDirectPayAO tenDirectPayAO;

	private DirectPayReceiveParamDO payReceiveParamDO;

	private String priceAmount;

	private Long orderId;

	/**
	 * 
	 * Created on 2011-9-9
	 * <p>
	 * Discription: 页面回调
	 * </p>
	 * 
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String tenPostBack() {
		try {
			//this.tenNotify();  //Add By ShiXing 暂改为前台调后台供郭磊和谭喜生测试

			// 效验签名
			if (!verifySign()) {
				super.errorMessage = "返回参数签名异常！";
				return ERROR;
			} else {
				Result result = tenDirectPayAO
						.queryOrderId(getPayReceiveParamDO().getOrderNo());
				if (result.isSuccess())
					this.orderId = (Long) result.getModel("orderId");
			}
			return super.notifyJump();
		} catch (Exception e) {
			log.error("direct pay debug tenPostBack exception-->", e);
		}
		return ERROR;
	}

	/**
	 * 
	 * Created on 2011-8-15
	 * <p>
	 * Discription: 通知发货
	 * </p>
	 * 
	 * @param _productNo
	 *            产品类型
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	protected boolean notifyDelivery() {
		boolean flag = false;
		switch (getBizType()) {
		case DirectPayConstant.BIZ_TYPE_MARGIN:
			flag = margin();
			break;
		case DirectPayConstant.BIZ_TYPE_GROUP:
			flag = group();
			break;
		case DirectPayConstant.BIZ_TYPE_LIMITDISCOUNT:
			flag = limitdiscount();
			break;
		case DirectPayConstant.BIZ_TYPE_PRICE:
			flag = price();
			break;
		default:
			no_productNo();
		}
		return flag;
	}

	@Override
	protected boolean isTenState() {
		return StringUtils.equalsIgnoreCase(getParameters().get("trade_state"),
				PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
	}

	/**
	 * 
	 * Created on 2011-9-6
	 * <p>
	 * Discription: 验证签名
	 * </p>
	 * 
	 * @param parameters
	 * @param _mac
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	protected boolean verifySign() {
		String _sign = getString("sign");// 签名字符串
		return tenDirectPayAO.verifySign(getParameters(), _sign);
	}

	@Override
	protected Integer getBizType() {
		if (super.bizType != null) {
			return super.bizType;
		}
		return getInteger("attach");
	}

	@Override
	protected SortedMap<String, String>  setParameters(SortedMap<String, String> parameters) {
		parameters.put("attach", getString("attach"));// 商家数据包，原样返回
		parameters.put("bank_type", getString("bank_type"));// 银行类型
		parameters.put("discount", getString("discount"));// 折扣金额单位为分
		parameters.put("fee_type", getString("fee_type"));// 现金支付币种
		parameters.put("input_charset", getString("input_charset"));// 字符编码
		parameters.put("notify_id", getString("notify_id"));// 支付结果通知id
		parameters.put("out_trade_no", getString("out_trade_no"));// 商户系统的订单号
		parameters.put("partner", getString("partner"));// 商户号
		parameters.put("product_fee", getString("product_fee"));// 物品费用
		parameters.put("sign_type", getString("sign_type"));// 签名方式
		parameters.put("time_end", getString("time_end"));// 支付完成时间，格式为yyyyMMddhhmmss
		parameters.put("total_fee", getString("total_fee"));// 支付金额，单位为分，如果discount有值，通知的total_fee
		parameters.put("trade_mode", getString("trade_mode"));// 1-即时到账
		parameters.put("trade_state", getString("trade_state"));// 支付结果：0—成功
		parameters.put("transaction_id", getString("transaction_id"));// 财付通交易号
		parameters.put("transport_fee", getString("transport_fee"));// 财付通交易号
		parameters.put("pay_info", getString("pay_info"));// 支付结果信息，支付成功时为空
		parameters.put("bank_billno", getString("bank_billno"));// 银行订单号，若为财付通余额支付则为空
		return parameters;
	}

	/**
	 * 
	 * Created on 2011-8-15
	 * <p>
	 * Discription: 处理参数封装
	 * </p>
	 * 
	 * @param _total_fee
	 *            支付金额
	 * @param _transaction_id
	 *            外部订单号
	 * @param _out_trade_no
	 *            品聚支付订单号
	 * @param _time_end
	 *            支付时间
	 * @param _attach
	 *            支付业务类型
	 * @param _discount
	 *            收银台折扣
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名] TODO
	 *                        财富通这边还有回调通知编号,某些查询接口需要。当前没记录后续是否根据需求添加
	 */
	private DirectPayReceiveParamDO getPayReceiveParamDO() {
		if (payReceiveParamDO != null
				&& payReceiveParamDO.getProductNo() != null
				&& payReceiveParamDO.getProductNo().compareTo(0) > 0)
			return payReceiveParamDO;
		SortedMap<String, String> parameters = getParameters();
		String _discount = parameters.get("discount");
		String _attach = parameters.get("attach");
		String _time_end = parameters.get("time_end");
		String _out_trade_no = parameters.get("out_trade_no");
		String _transaction_id = parameters.get("transaction_id");
		String _total_fee = parameters.get("total_fee");

		payReceiveParamDO = new DirectPayReceiveParamDO();
		if (StringUtils.isNotEmpty(_attach))
			payReceiveParamDO.setProductNo(Integer.valueOf(_attach));
		payReceiveParamDO.setOrderNo(Long.valueOf(_out_trade_no));
		payReceiveParamDO.setPayAmount(Long.valueOf(_total_fee)
				+ Long.valueOf((StringUtils.isEmpty(_discount)) ? "0"
						: _discount));
		payReceiveParamDO.setSerialno(_transaction_id);
		payReceiveParamDO.setPayTime(DateUtil.parse(PinjuConstant.DATE_FORMAT,
				_time_end));
		/** ducheng@zba.com 回传报文 2011-10-11 */
		payReceiveParamDO.setRevDetail(super.getRevDetail());
		return payReceiveParamDO;
	}

	/**
	 * Created on 2011-9-9
	 * <p>Discription: 消保回调处理</p>
	 * @param result
	 * @param directPayReceiveParamDO
	 * @param payState
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期2011-10-17] [石兴] Cause:将发货合并到与订单同一个事务中,减少手工对账
	 */
	private boolean margin() {
		log.warn("begin margin tenDirectPayAO.tenDirectPayNotify");
		final DirectPayReceiveParamDO directPayReceiveParamDO = getPayReceiveParamDO();
		final boolean tenState = isTenState();
		Result result = tenDirectPayAO.tenDirectPayNotify(directPayReceiveParamDO, tenState);
		log.warn("end margin tenDirectPayAO.tenDirectPayNotify"+ result.isSuccess());
		// 更新业务订单与支付状态并处理保证金后续业务失败处理
		if (!result.isSuccess()) {
			upPayFail(result);
			return result.isSuccess();
		}
		return result.isSuccess();
	}

	/**
	 * 
	 * Created on 2011-9-9
	 * <p>
	 * Discription: 团购发货
	 * </p>
	 * 
	 * @param result
	 * @param directPayReceiveParamDO
	 * @param payState
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean group() {
		return limitdiscount();
	}

	/**
	 * 
	 * Created on 2011-9-9
	 * <p>
	 * Discription: 限时折扣
	 * </p>
	 * 
	 * @param result
	 * @param directPayReceiveParamDO
	 * @param payState
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean limitdiscount() {
		log.warn("begin limitdiscount tenDirectPayAO.tenDirectPayNotify");
		final DirectPayReceiveParamDO payReceiveParamDO = getPayReceiveParamDO();
		final boolean tenState = isTenState();
		log.warn("limitdiscount 财付通收银台支付状态:".concat(String.valueOf(tenState)));
		Result result = tenDirectPayAO.vouchTenDirectPayNotify(
				payReceiveParamDO, tenState);
		log.warn("end limitdiscount tenDirectPayAO.tenDirectPayNotify"
				+ result.isSuccess());
		/** 财付通支付处理成功 ducheng@zba.com 2011-10-11 */
		if (tenState && !result.isSuccess()) {
			log.warn("limitdiscount 财付通收银台支付成功,并且业务更新失败");
			upPayFail(result);
		}
		this.orderId = (Long) result.getModel("orderId");
		return result.isSuccess();
	}

	/**
	 * 
	 * Created on 2011-9-9
	 * <p>
	 * Discription: 一口价
	 * </p>
	 * 
	 * @param result
	 * @param directPayReceiveParamDO
	 * @param payState
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean price() {
		return limitdiscount();
	}

	public void setTenDirectPayAO(TenDirectPayAO tenDirectPayAO) {
		this.tenDirectPayAO = tenDirectPayAO;
	}

	public String getPriceAmount() {
		if (StringUtil.isNotEmpty(getString("total_fee"))
				&& StringUtil.isNumeric(getString("total_fee")))
			this.priceAmount = MoneyUtil.getCentToDollar(getLong("total_fee"));
		return this.priceAmount;
	}

	public void setPriceAmount(String priceAmount) {
		this.priceAmount = priceAmount;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

}
