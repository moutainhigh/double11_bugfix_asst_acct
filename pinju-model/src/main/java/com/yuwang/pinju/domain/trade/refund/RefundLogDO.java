package com.yuwang.pinju.domain.trade.refund;

import java.util.Date;

public class RefundLogDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
	 * 退款中间日志表成功 状态
	 */
	public  static final Integer REFUND_LOG_IS_SUCCESS = 1 ;
	
	/**
	 * 退款中间表 失败状态
	 */
	
	public static final Integer REFUND_LOG_IS_FAIL = 0 ;
	
	/**
	 * 手工单操作 状态
	 */
	public static final Integer REDUND_LOG_BY_HAND = 2 ;

	
    /**
     * 退款日志单号
     */
    private Long id;

    /**
     * 买家ID
     */
    private Long sellerId;

    /**
     * 总金额
     */
    private Long totalFee;

    /**
     * 退款金额
     */
    private Long refundFee;

    /**
     * 备注
     */
    private String memo;

    /**
     * 退款ID
     */
    private String refundId;

    /**
     * 财付通交易ID
     */
    private String transactionId;

    /**
     * 内部支付订单ID
     */
    private String payorderId;

    /**
     * 退款状态  0-退款失败   1-退款成功
     */
    private Integer refundState;

    /**
     * 业务代码   平台退款-93  分账回退 - 95
     */
    private Integer cmdno;

    /**
     * 发送参数 (Base64 加密)
     */
    private String paramDetail;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
    
    private Long orderId;


    public Long getId(){
        return id;
    }

    public Long getSellerId(){
        return sellerId;
    }

    public Long getTotalFee(){
        return totalFee;
    }

    public Long getRefundFee(){
        return refundFee;
    }

    public String getMemo(){
        return memo;
    }

    public String getRefundId(){
        return refundId;
    }

    public String getTransactionId(){
        return transactionId;
    }

	public String getPayorderId() {
		return payorderId;
	}

	public void setPayorderId(String payorderId) {
		this.payorderId = payorderId;
	}

	public Integer getRefundState() {
		return refundState;
	}

	public void setRefundState(Integer refundState) {
		this.refundState = refundState;
	}

	public Integer getCmdno() {
		return cmdno;
	}

	public void setCmdno(Integer cmdno) {
		this.cmdno = cmdno;
	}

	public String getParamDetail() {
		return paramDetail;
	}

	public void setParamDetail(String paramDetail) {
		this.paramDetail = paramDetail;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public void setRefundFee(Long refundFee) {
		this.refundFee = refundFee;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
  public static void main(String args[]){
	  String str= "{bargainor_id=1900000107, cmdno=93, pay_info=ok, pay_result=0, refund_fee=2, refund_id=1091900000107201110120008950, sign=5A562A9C5C7BB3BBCE1AD7ACE1ABCA32, sp_billno=1900000107201110120000035850, transaction_id=1900000107201110120000035850, version=4}";
	  str= str.replace(",", "&").replace(" ", "").replace("{", "").replace("}", "");
	  System.out.println(str);
  }
}

