package com.yuwang.pinju.domain.trade;

/**
 * @Project: pinju-model
 * @Description: 财付通单笔支付接收参数DO
 * @author shihongbo
 */
public class TenpaySinglepayRecvParamDO {

	/**
	 * 业务代码, 财付通支付支付接口填  1  
	 */
	private String cmdno;
	
	/**
	 * 支付结果(0为支付成功,其它为失败)
	 */
	private String payResult;
	
	/**
	 * 支付结果信息
	 */
	private String payInfo;
	
	/**
	 * 商户号
	 */
	private String bargainorId;
	
	/**
	 * 财付通交易单号
	 */
	private String transactionId;
	
	/**
	 * 商户系统内部的定单号
	 */
	private String spBillno;
	
	/**
	 * 支付总金额
	 */
	private String totalFee;
	
	/**
	 * 现金支付币种，目前只支持人民币,默认为1。
	 */
	private String feeType;

	/**
	 * 商户附加信息
	 */
	private String attach;

	/**
	 * 用户支付时间戳
	 */
	private String payTime;

	/**
	 * 现金支付金额
	 */
	private String fee1;
	
	/**
	 * 现金券支付金额
	 */
	private String fee2;
	
	/**
	 * 其它金额
	 */
	private String fee3;
	
	/**
	 * 折扣金额
	 */
	private String vfee;

	/**
	 * 返回内容
	 */
	private String backInfo;
	
	public String getBackInfo() {
		return backInfo;
	}

	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}

	public String getCmdno() {
		return cmdno;
	}

	public void setCmdno(String cmdno) {
		this.cmdno = cmdno;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}

	public String getBargainorId() {
		return bargainorId;
	}

	public void setBargainorId(String bargainorId) {
		this.bargainorId = bargainorId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSpBillno() {
		return spBillno;
	}

	public void setSpBillno(String spBillno) {
		this.spBillno = spBillno;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getFee1() {
		return fee1;
	}

	public void setFee1(String fee1) {
		this.fee1 = fee1;
	}

	public String getFee2() {
		return fee2;
	}

	public void setFee2(String fee2) {
		this.fee2 = fee2;
	}

	public String getFee3() {
		return fee3;
	}

	public void setFee3(String fee3) {
		this.fee3 = fee3;
	}

	public String getVfee() {
		return vfee;
	}

	public void setVfee(String vfee) {
		this.vfee = vfee;
	}
	
	
}
