package com.yuwang.pinju.domain.refund;

import java.util.Date;

public class TradeRefundManualDO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	  * 该笔手工退款已经返还
	  */
	 public static final Integer STATUS_PAYBACK_YES = 1;
	 
	 /**
	  * 该笔手工退款未返还
	  */
	 public static final Integer STATUS_PAYBACK_NO = 0;
	 
	/**
	 * 手工退款工单号
	 */
	private Long id;

	/**
	 * 卖家昵称
	 */
	private String sellerNick;

	/**
	 * 卖家ID
	 */
	private Long sellerId;

	/**
	 * 卖家店铺ID
	 */
	private Long sellerShopId;

	/**
	 * 平台退款总金额
	 */
	private Long platformRefundAmount;

	/**
	 * 买家ID
	 */
	private Long buyerId;

	/**
	 * 业务操作人ID
	 */
	private Long operatorId;

	/**
	 * 记录创建时间
	 */
	private Date gmtCreate;

	/**
	 * 记录修改时间
	 */
	private Date gmtModified;

	/**
	 * 0：未返还；1：已返还
	 */
	private Integer refundState;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 财付通订单号
	 */
	private String orderTenpayId;

	/**
	 * 买家昵称
	 */
	private String buyerNick;

	/**
	 * 主订单ID
	 */
	private Long orderId;

	/**
	 * 发起人（客服介入就是客服，没有客服介入即空）
	 */
	private String sponsorNick;

	/**
	 * 发起人ID
	 */
	private Long sponsorId;

	/**
	 * 业务操作人名称
	 */
	private String operatorNick;

	/**
	 * 商户订单号
	 */
	private String orderPayId;

	/**
	 * 财付通退款ID
	 */
	private String orderRefundId;

	/**
	 * 订单实付款金额
	 */
	private Long realPayMentamount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getSellerShopId() {
		return sellerShopId;
	}

	public void setSellerShopId(Long sellerShopId) {
		this.sellerShopId = sellerShopId;
	}

	public Long getPlatformRefundAmount() {
		return platformRefundAmount;
	}

	public void setPlatformRefundAmount(Long platformRefundAmount) {
		this.platformRefundAmount = platformRefundAmount;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getRefundState() {
		return refundState;
	}

	public void setRefundState(Integer refundState) {
		this.refundState = refundState;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOrderTenpayId() {
		return orderTenpayId;
	}

	public void setOrderTenpayId(String orderTenpayId) {
		this.orderTenpayId = orderTenpayId;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getSponsorNick() {
		return sponsorNick;
	}

	public void setSponsorNick(String sponsorNick) {
		this.sponsorNick = sponsorNick;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getOperatorNick() {
		return operatorNick;
	}

	public void setOperatorNick(String operatorNick) {
		this.operatorNick = operatorNick;
	}

	public String getOrderPayId() {
		return orderPayId;
	}

	public void setOrderPayId(String orderPayId) {
		this.orderPayId = orderPayId;
	}
	
	public String getOrderRefundId() {
		return orderRefundId;
	}

	public void setOrderRefundId(String orderRefundId) {
		this.orderRefundId = orderRefundId;
	}

	public Long getRealPayMentamount() {
		return realPayMentamount;
	}

	public void setRealPayMentamount(Long realPayMentamount) {
		this.realPayMentamount = realPayMentamount;
	}
}
