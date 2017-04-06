package com.yuwang.pinju.domain.refund;

import java.util.Date;

public class TradeRefundLogisticsDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 买家未发货
     */
	public  static final Long BUYER_NOT_DELIVERGOODS = 0L;
    
    /**
     * 买家已经退货
     */
	public static final Long BUYER_DELIVERGOODS = 1L;
	
    /**
     * 卖家已经确认收货
     */
	public static final Long SELLER_CONFIRMRECEIVEGOODS = 2L;

    /**
     * 记录创建时间
     */
    private Date gmtCreate;

    /**
     * 记录修改时间
     */
    private Date gmtModified;

    /**
     * 买家退货id
     */
    private Long id;

    /**
     * 退款id
     */
    private Long refundId;

    /**
     * 退货物流id
     */
    private String logisticsId;

    /**
     * 退货物流名称
     */
    private String logisticsName;

    /**
     * 运单号码
     */
    private String logisticsNumber;

    /**
     * 买家id
     */
    private Long buyerId;

    /**
     * 买家备注说明
     */
    private String buyerMemo;

    /**
     * 买家发货时间
     */
    private Date sendDate;

    /**
     * 卖家id
     */
    private Long sellerId;

    /**
     * 卖家确认收货时间
     */
    private Date confirmDate;

    /**
     * 买家退货状态
	 * 0-买家未发货
	 * 1-买家已发货，等待卖家收货
	 * 2-卖家已经确认收货
     */
    private Long logisticsState;

    /**
     * 物流类型
     */
    private Long logisticsType;

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public Long getId(){
        return id;
    }

    public Long getRefundId(){
        return refundId;
    }

    public String getLogisticsId(){
        return logisticsId;
    }

    public String getLogisticsName(){
        return logisticsName;
    }

    public String getLogisticsNumber(){
        return logisticsNumber;
    }

    public Long getBuyerId(){
        return buyerId;
    }

    public String getBuyerMemo(){
        return buyerMemo;
    }

    public Date getSendDate(){
        return sendDate;
    }

    public Long getSellerId(){
        return sellerId;
    }

    public Date getConfirmDate(){
        return confirmDate;
    }

    public Long getLogisticsState(){
        return logisticsState;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setRefundId(Long refundId){
        this.refundId = refundId;
    }

    public void setLogisticsId(String logisticsId){
        this.logisticsId = logisticsId;
    }

    public void setLogisticsName(String logisticsName){
        this.logisticsName = logisticsName;
    }

    public void setLogisticsNumber(String logisticsNumber){
        this.logisticsNumber = logisticsNumber;
    }

    public void setBuyerId(Long buyerId){
        this.buyerId = buyerId;
    }

    public void setBuyerMemo(String buyerMemo){
        this.buyerMemo = buyerMemo;
    }

    public void setSendDate(Date sendDate){
        this.sendDate = sendDate;
    }

    public void setSellerId(Long sellerId){
        this.sellerId = sellerId;
    }

    public void setConfirmDate(Date confirmDate){
        this.confirmDate = confirmDate;
    }

    public void setLogisticsState(Long logisticsState){
        this.logisticsState = logisticsState;
    }

	public Long getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(Long logisticsType) {
		this.logisticsType = logisticsType;
	}
}

