package com.yuwang.pinju.domain.trade;

import com.yuwang.pinju.common.MoneyUtil;

/**
 * @Project: pinju-model
 * @Description: 财付通支付参数DO
 * @author shihongbo
 */
public class TenpaySinglepayParamDO {

	public TenpaySinglepayParamDO(){}
	
	/**
	 * 版本号,1
	 */
	private String ver = "1";
	
	/**
	 * 业务代码, 财付通支付支付接口填  1  
	 */
	private Integer cmdno = 1;
	
	/**
	 * 商户日期：如20051212
	 */
	private String date;
	
	/**
	 * 银行类型:财付通支付填0
	 */
	private Integer bankType = 0;

	/**
	 * 交易的商品名称,32个字符16汉字内,不包含特殊符号
	 */
	private String desc;
	
	/**
	 * 用户(买方)的财付通帐户(QQ或EMAIL)
	 */
	private String purchaserId;
	
	/**
	 * 商家的商户号,有腾讯公司唯一分配
	 */
	private String bargainorId;
	
	/**
	 * 交易号(订单号)，由商户网站产生(建议顺序累加)，一对请求和应答的交易号必须相同）。
	 * transaction_id 为28位长的数值，其中前10位为商户网站编号(SPID)，由财付通统一分配；
	 * 之后8位为订单产生的日期，如20050415；最后10位商户需要保证一天内不同的事务（用户订购一次商品或购买一次服务），其ID不相同。
	 * 此财付通订单号必须保持唯一，不能重复,财付通根据此定单号通知商户发货和数据更新等。
	 */
	private String transactionId;
	
	/**
	 * 商户系统内部的定单号，此参数仅在对账时提供,28个字符内、可包含字母。 
	 */
	private String spBillno;
	
	/**
	 * 总金额，以分为单位,不允许包含任何字符
	 */
	private Long totalFee;
	
	/**
	 * 现金支付币种，目前只支持人民币1
	 */
	private Integer feeType = 1;
	
	/**
	 * 接收财付通返回结果的URL(推荐使用ip)
	 */
	private String returnUrl;

	/**
	 * 商家数据包，原样返回
	 */
	private String attach;
	
	/**
	 * desc字符编码名称: “gb2312”, “utf-8”
	 */
	private String cs;
	
	/**
	 * 用户IP（非商户服务器IP），为了防止欺诈，支付时财付通会校验此IP
	 */
	private String spbillCreateIp;
	
	/**
	 * 卖家id
	 */
	private Long sellerId;
	
	/**
	 * 卖家昵称
	 */
	private String sellerNick;

	/**
	 * 买家id
	 */
	private Long buyerId;
	
	
	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public Integer getCmdno() {
		return cmdno;
	}

	public void setCmdno(Integer cmdno) {
		this.cmdno = cmdno;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getBankType() {
		return bankType;
	}

	public void setBankType(Integer bankType) {
		this.bankType = bankType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
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

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	
	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getCs() {
		return cs;
	}

	public void setCs(String cs) {
		this.cs = cs;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	
	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getTotalFeeByYuan(){
		return  MoneyUtil.getCentToDollar(totalFee);
	}
	
	public TenpaySinglepayParamDO(String date, String desc,
			String transactionId, String spBillno,
			Long totalFee, String attach,
			String spbillCreateIp, 
			Long buyerId, Long sellerId){
		super();
		this.date = date;
		this.desc = desc;
		this.transactionId = transactionId;
		this.spBillno = spBillno;
		this.totalFee = totalFee;
		this.attach = attach;
		this.spbillCreateIp = spbillCreateIp;
		this.sellerId = sellerId;
		this.buyerId = buyerId;
	}

}
