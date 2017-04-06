package com.yuwang.pinju.domain.trade.refund;
import com.yuwang.pinju.domain.BaseDO;

/**
 * <p>Description: 平台退款参数DO </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-9
 */
public class PlatformRefundParamDO extends BaseDO{
	private static final long serialVersionUID = -7670215107496710129L;

	/**
	 * 买家ID
	 */
	private Long buyerId;
	/**
	 * 财付通交易号
	 */
	private String transactionId;  //财付通交易号 transaction_id
	
	/**
	 * 退款金额，以分为单位
	 */
	private Long refundFee;   // 退款金额  refund_fee
	
	/**
	 * 内部支付订单编号
	 */
	private String orderPayId;   //sp_billno
	
	/**
	 * 订单号
	 */
	private Long orderId;     // 订单号 

	
	private Long totalFee;   //总金额    total_fee 

	
	
	private String refundId;   // 退款交易  ID
	
	/**
	 * 退款返回结果
	 */
	private String payResult;  
	/**
	 * 退款返回的信息
	 */
	private String payInfo;
	
	/**
	 * IP
	 */
	private String buyerIp;
	
	private String backInfo;
	
	public PlatformRefundParamDO(){}
	
	
	/**
	 * <p>Description: 分账回退</p>
	 * @param orderPayId
	 * @param refundFee
	 * @param transactionId
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-20
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static PlatformRefundParamDO createRefundParamDO(String orderPayId,Long totalFee,Long refundFee,String transactionId,String refundId  ){
		PlatformRefundParamDO parmDO = new PlatformRefundParamDO();
		parmDO.setOrderPayId(orderPayId);
		parmDO.setRefundFee(refundFee);
		parmDO.setTransactionId(transactionId);
		parmDO.setRefundId(refundId);
		parmDO.setTotalFee(totalFee);
		return parmDO;
	}
	
	/**
	 * <p>Description: 全额退款 DO</p>
	 * @param orderPayId
	 * @param refundFee
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-20
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static PlatformRefundParamDO createRefundParamDO(String transactionId,String orderPayId,Long refundFee,Long totalFee){
		PlatformRefundParamDO parmDO = new PlatformRefundParamDO();
		parmDO.setOrderPayId(orderPayId);
		parmDO.setRefundFee(refundFee);
		parmDO.setTransactionId(transactionId);
		parmDO.setTotalFee(totalFee);
		return parmDO;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Long getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Long refundFee) {
		this.refundFee = refundFee;
	}


	public String getOrderPayId() {
		return orderPayId;
	}


	public void setOrderPayId(String orderPayId) {
		this.orderPayId = orderPayId;
	}


	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
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

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerIp() {
		return buyerIp;
	}

	public void setBuyerIp(String buyerIp) {
		this.buyerIp = buyerIp;
	}


	public String getBackInfo() {
		return backInfo;
	}


	public void setBackInfo(String backInfo) {
		this.backInfo = backInfo;
	}
}


