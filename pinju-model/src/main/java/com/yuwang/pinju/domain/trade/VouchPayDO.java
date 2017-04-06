package com.yuwang.pinju.domain.trade;

import java.util.Date;

import com.yuwang.pinju.Constant.VouchPayConstant;

/**
 * @Discription: 担保交易订单支付记录DO
 * @Project: pinju-model
 * @Package: com.yuwang.pinju.domain.order.pay
 * @Title: OrderPayDO.java
 * @author: [贺泳]
 * @date 2011-9-15 下午03:50:53
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class VouchPayDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;
    
    /**
     * 支付订单号
     */
    private String orderPayId;
    
    /**
     * 支付用户账号
     */
    private Long buyerId;

    /**
     * 收款用户账号
     */
    private Long sellerId;

    /**
     * 支付账户(第3方账户)
     */
    private String payAccount;

    /**
     * 收款账户(第3方账户)
     */
    private String acceptAccount;

    /**
     * 第三方支付编号
     */
    private String outPayId;

    /**
     * 支付状态 对应字典表ID
     */
    private Long payState;

    /**
     * 支付订单创建时间
     */
    private Date creationTime;

    /**
     * 订单金额 到分
     */
    private Long orderAmount;

    /**
     * 实付款金额
     */
    private Long realPayMentamount;

    /**
     * 手续费 到分
     */
    private Long dealAmount;

    /**
     * 关闭状态 对应字典表ID
     */
    private Long closeState;

    /**
     * 最后一次修改时间
     */
    private Date endModifyTime;

    /**
     * 支付报文
     */
    private String signMsg;

    /**
     * 财富通交易回传时间
     */
    private Date dealTime;

    /**
     * key\:value;(格式)
     */
    private String payAttributes;
    /**
     * 内部订单编号
     */
    private Long orderId;

    /**
     * 记录生成时间,数据维护
     */
    private Date gmtCreate;

    /**
     * 记录修改时间,数据维护
     */
    private Date gmtModified;


    
    /**
     * 
     * Created on 2011-9-16
     * <p>Discription: [生成订单时生成支付订单]</p>
     * @param buyerId 买家id
     * @param sellerId 卖家id
     * @param payAccount 支付账户
     * @param acceptAccount 收款账户
     * @param creationTime 生成时间
     * @param orderAmount 支付订单金额
     * @param dealAmount 平台手续费
     * @param payAttributes 分账参数
     * @return
     * @author:[杜成]
     * @version 1.0
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static VouchPayDO createVouchPayDO(String orderPayId,Long buyerId,Long sellerId,String acceptAccount,Date creationTime,Long orderAmount){
    	VouchPayDO vouchPayDO = new VouchPayDO();
    	vouchPayDO.setBuyerId(buyerId);
    	vouchPayDO.setSellerId(sellerId);
    	vouchPayDO.setAcceptAccount(acceptAccount);
    	vouchPayDO.setCreationTime(creationTime);
    	vouchPayDO.setOrderAmount(orderAmount);
    	vouchPayDO.setPayState(VouchPayConstant.VOUCH_PAY_STATE_UNPAID);
    	vouchPayDO.setEndModifyTime(creationTime);
    	vouchPayDO.setOrderPayId(orderPayId);
    	return vouchPayDO;
    }

    /**
     * 
     * Created on 2011-9-29
     * <p>Discription: 支付成功后调用更新分账金额</p>
     * @param orderId
     * @param orderAmount
     * @param dealAmount
     * @param payAttributes
     * @return
     * @author:[杜成]
     * @version 1.0
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public static VouchPayDO upSplitAssistantVouchPayDO(Long orderId,Long orderAmount,Long dealAmount,String payAttributes){
    	VouchPayDO vouchPayDO = new VouchPayDO();
    	vouchPayDO.setOrderId(orderId);
    	vouchPayDO.setOrderAmount(orderAmount);
    	vouchPayDO.setPayAttributes(payAttributes);
    	vouchPayDO.setDealAmount(dealAmount);
    	vouchPayDO.setGmtModified(new Date());
    	return vouchPayDO;
    }
    
    
    public Long getId(){
        return id;
    }

    public Long getBuyerId(){
        return buyerId;
    }

    public Long getSellerId(){
        return sellerId;
    }

    public String getPayAccount(){
        return payAccount;
    }

    public String getAcceptAccount(){
        return acceptAccount;
    }

    public String getOutPayId(){
        return outPayId;
    }

    public Long getPayState(){
        return payState;
    }

    public Date getCreationTime(){
        return creationTime;
    }

    public Long getOrderAmount(){
        return orderAmount;
    }

    public Long getRealPayMentamount(){
        return realPayMentamount;
    }

    public Long getDealAmount(){
        return dealAmount;
    }

    public Long getCloseState(){
        return closeState;
    }

    public Date getEndModifyTime(){
        return endModifyTime;
    }

    public String getSignMsg(){
        return signMsg;
    }

    public Date getDealTime(){
        return dealTime;
    }

    public String getPayAttributes(){
        return payAttributes;
    }

    public Long getOrderId(){
        return orderId;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setBuyerId(Long buyerId){
        this.buyerId = buyerId;
    }

    public void setSellerId(Long sellerId){
        this.sellerId = sellerId;
    }

    public void setPayAccount(String payAccount){
        this.payAccount = payAccount;
    }

    public void setAcceptAccount(String acceptAccount){
        this.acceptAccount = acceptAccount;
    }

    public void setOutPayId(String outPayId){
        this.outPayId = outPayId;
    }

    public void setPayState(Long payState){
        this.payState = payState;
    }

    public void setCreationTime(Date creationTime){
        this.creationTime = creationTime;
    }

    public void setOrderAmount(Long orderAmount){
        this.orderAmount = orderAmount;
    }

    public void setRealPayMentamount(Long realPayMentamount){
        this.realPayMentamount = realPayMentamount;
    }

    public void setDealAmount(Long dealAmount){
        this.dealAmount = dealAmount;
    }

    public void setCloseState(Long closeState){
        this.closeState = closeState;
    }

    public void setEndModifyTime(Date endModifyTime){
        this.endModifyTime = endModifyTime;
    }

    public void setSignMsg(String signMsg){
        this.signMsg = signMsg;
    }

    public void setDealTime(Date dealTime){
        this.dealTime = dealTime;
    }

    public void setPayAttributes(String payAttributes){
        this.payAttributes = payAttributes;
    }

    public void setOrderId(Long orderId){
        this.orderId = orderId;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

	public String getOrderPayId() {
		return orderPayId;
	}

	public void setOrderPayId(String orderPayId) {
		this.orderPayId = orderPayId;
	}

	@Override
	public String toString() {
		return "VouchPayDO [acceptAccount=" + acceptAccount + ", buyerId="
				+ buyerId + ", closeState=" + closeState + ", creationTime="
				+ creationTime + ", dealAmount=" + dealAmount + ", dealTime="
				+ dealTime + ", endModifyTime=" + endModifyTime
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", id=" + id + ", orderAmount=" + orderAmount + ", orderId="
				+ orderId + ", orderPayId=" + orderPayId + ", outPayId="
				+ outPayId + ", payAccount=" + payAccount + ", payAttributes="
				+ payAttributes + ", payState=" + payState
				+ ", realPayMentamount=" + realPayMentamount + ", sellerId="
				+ sellerId + ", signMsg=" + signMsg + "]";
	}

}

