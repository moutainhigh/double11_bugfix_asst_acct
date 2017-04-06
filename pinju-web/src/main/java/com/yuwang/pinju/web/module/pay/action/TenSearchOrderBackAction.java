package com.yuwang.pinju.web.module.pay.action;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.core.trade.ao.TenSearchOrderAO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.web.module.BaseAction;

public class TenSearchOrderBackAction extends BaseAction {

	private static final long serialVersionUID = 4109764123357142460L;

	private TenSearchOrderAO tenSearchOrderAO;
	private OrderQueryAO orderQueryAO;

	// 任务代码 96
	private String cmdno;
	// 查询结果 如果返回 0—成功 (至少表示此订单支付已经成功)
	private String pay_result;
	// 支付结果信息，支付成功时为空
	private String pay_info;
	// 订单最后修改时间
	private String datetime;
	// 卖方账号（商户spid）
	private String bargainor_id;
	// 财付通交易号(订单号)
	private String transaction_id;
	// 商户系统内部的定单号，此参数仅在对账时提供。
	private String sp_billno;
	// 订单总金额，以分为单位
	private String total_fee;
	// 现金支付币种
	private String fee_type;
	// Md5签名
	private String sign;
	// 业务类型，整数值，代表分账请求时填写的业务类型。如果不为空，则表示分账成功。
	private String bus_type;
	// 与原始分账的请求参数一致 如果bus_type不为空，为分账的参数，且分账成功
	private String bus_args;
	// 支付时间，格式为2010-01-01 00:00:00
	private String pay_time;
	// 如果不为空，表示分账回退成功。有分账回退时，必填。无分账回退时可为空。
	private String bus_split_refund_args;
	// 如果不为空，表示平台退款成功。有平台退款时，必填。无平台退款时可为空。
	private String bus_platform_refund_args;
	// 如果不为空，表示冻结成功（冻结成功，已经解冻的单也出现）。有冻结时，必填。无冻结时可为空。
	private String bus_freeze_args;
	// 如果不为空，表示解冻成功
	private String bus_thaw_args;
	// 版本号必须填4
	private String version;

	private List<OrderItemDO> orderItemList;
	private OrderDO orderDO;
	private String errorMessage;

	public String tenSearchOrderBack() {
		SortedMap<String, String> createParameters = new TreeMap<String, String>();
		createParameters.put("cmdno", cmdno);
		createParameters.put("pay_result", pay_result);
		createParameters.put("pay_info", pay_info);
		createParameters.put("datetime", datetime);
		createParameters.put("bargainor_id", bargainor_id);
		createParameters.put("transaction_id", transaction_id);
		createParameters.put("sp_billno", sp_billno);
		createParameters.put("total_fee", total_fee);
		createParameters.put("fee_type", fee_type);
		createParameters.put("bus_type", bus_type);
		createParameters.put("bus_args", bus_args);
		createParameters.put("pay_time", pay_time);
		createParameters.put("bus_split_refund_args", bus_split_refund_args);
		createParameters.put("bus_platform_refund_args",
				bus_platform_refund_args);
		createParameters.put("bus_freeze_args", bus_freeze_args);
		createParameters.put("bus_thaw_args", bus_thaw_args);
		createParameters.put("version", version);

		Result result = new ResultSupport();
		result = tenSearchOrderAO.getOrderInfo(createParameters, sign, Integer.valueOf(cmdno), Integer.valueOf(version), Long.valueOf(sp_billno));
		if(!result.isSuccess()){
			errorMessage = "系统繁忙，请稍后重试！";
			return "error";
		}
		orderDO = (OrderDO) result.getModel("orderDO");
		orderItemList = (List<OrderItemDO>) result.getModel("orderItemList");
		if(orderDO == null) orderDO = new OrderDO();
		/*if (!tenSearchOrderAO.verifySign(createParameters, sign))
			return "error";
		
		int comdnos = Integer.parseInt(cmdno);
		int versions = Integer.parseInt(version);
		if (comdnos != 96 && versions != 4) 
			return "error";
		
		Result orderResult = orderQueryAO.getOrderDOById(Long
				.valueOf(sp_billno),null,null);
		orderDO = (OrderDO) orderResult.getModel("orderDO");
		Result result = orderQueryAO
				.queryOrderItemList(Long.valueOf(sp_billno));
		orderItemList = (List<OrderItemDO>) result.getModel("list");*/
		
		return "success";
	}

	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
		this.orderQueryAO = orderQueryAO;
	}

	public String getCmdno() {
		return cmdno;
	}

	public void setCmdno(String cmdno) {
		this.cmdno = cmdno;
	}

	public String getPay_result() {
		return pay_result;
	}

	public void setPay_result(String pay_result) {
		this.pay_result = pay_result;
	}

	public String getPay_info() {
		return pay_info;
	}

	public void setPay_info(String pay_info) {
		this.pay_info = pay_info;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getBargainor_id() {
		return bargainor_id;
	}

	public void setBargainor_id(String bargainor_id) {
		this.bargainor_id = bargainor_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getSp_billno() {
		return sp_billno;
	}

	public void setSp_billno(String sp_billno) {
		this.sp_billno = sp_billno;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBus_type() {
		return bus_type;
	}

	public void setBus_type(String bus_type) {
		this.bus_type = bus_type;
	}

	public String getBus_args() {
		return bus_args;
	}

	public void setBus_args(String bus_args) {
		this.bus_args = bus_args;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getBus_split_refund_args() {
		return bus_split_refund_args;
	}

	public void setBus_split_refund_args(String bus_split_refund_args) {
		this.bus_split_refund_args = bus_split_refund_args;
	}

	public String getBus_platform_refund_args() {
		return bus_platform_refund_args;
	}

	public void setBus_platform_refund_args(String bus_platform_refund_args) {
		this.bus_platform_refund_args = bus_platform_refund_args;
	}

	public String getBus_freeze_args() {
		return bus_freeze_args;
	}

	public void setBus_freeze_args(String bus_freeze_args) {
		this.bus_freeze_args = bus_freeze_args;
	}

	public String getBus_thaw_args() {
		return bus_thaw_args;
	}

	public void setBus_thaw_args(String bus_thaw_args) {
		this.bus_thaw_args = bus_thaw_args;
	}

	public List<OrderItemDO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setTenSearchOrderAO(TenSearchOrderAO tenSearchOrderAO) {
		this.tenSearchOrderAO = tenSearchOrderAO;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
