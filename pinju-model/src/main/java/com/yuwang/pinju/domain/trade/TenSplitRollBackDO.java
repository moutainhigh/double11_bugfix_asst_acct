package com.yuwang.pinju.domain.trade;


public class TenSplitRollBackDO {
	
	/**
	 * 财付通交易号(订单号)
	 */
	private String transaction_id;
	
	/**
	 * 内部支付id号
	 */
	private String orderId;
	
	/**
	 * 订单总金额
	 */
	private Long orderAmount;
	/**
	 * 退款总额
	 */
	private Long refundTotalSum;
	
	/**
	 * 退款id
	 */
	private String refundId;
	
	/**
	 * 退款总额| (账户^退款金额)[|(账户^退款金额)]*
	 */
	private String accountRefund;
	
	/**
	 * 分账回退结果(返回码 0-成功)
	 */
	private String payResult;
	
	/**
	 * 分账回退结果信息
	 */
	private String payInfo;

	
	/**
	 * 需要从消保扣的退款金额;
	 */
	private Long deMargin;
	
	/**
	 * 插入工单表的退款记录
	 */
	private String rightsWorkFeatures;
	
	/**
	 * 操作ip
	 */
	private Long buyerIP;

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getRefundTotalSum() {
		return refundTotalSum;
	}

	public void setRefundTotalSum(Long refundTotalSum) {
		this.refundTotalSum = refundTotalSum;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getAccountRefund() {
		return accountRefund;
	}

	public void setAccountRefund(String accountRefund) {
		this.accountRefund = accountRefund;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public Long getBuyerIP() {
		return buyerIP;
	}

	public void setBuyerIP(Long buyerIP) {
		this.buyerIP = buyerIP;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public String getRightsWorkFeatures() {
		return rightsWorkFeatures;
	}

	public void setRightsWorkFeatures(String rightsWorkFeatures) {
		this.rightsWorkFeatures = rightsWorkFeatures;
	}

	public Long getDeMargin() {
		return deMargin;
	}

	public void setDeMargin(Long deMargin) {
		this.deMargin = deMargin;
	}




	
}
