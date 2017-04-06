package com.yuwang.pinju.domain.order.pay;

import java.util.Date;

/**
 * @Discription: 发送日志
 * @Project: pinju-model
 * @Package: com.yuwang.pinju.domain.order.pay
 * @Title: PaySendLogDO.java
 * @author: [贺泳]
 * @date 2011-9-15 上午10:42:49
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class PaySendLogDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发送类型
     */
    private String sendType;

    /**
     * 支付发送日志编号
     */
    private Long id;

    /**
     * 支付用户内部账号
     */
    private Long payUserId;

    /**
     * 收款用户内部账号
     */
    private Long acceptUserId;

    /**
     * 支付平台类型
     */
    private String payType;

    /**
     * 内部支付订单编号
     */
    private String orderPayId;

    /**
     * 发送内容 (加密后)
     */
    private String sendInfo;

    /**
     * 日志生成时间
     */
    private Date creationTime;

    /**
     * 付款第3方账户
     */
    private String payAccount;

    /**
     * 收款第3方账户
     */
    private String acceptAccount;

    /**
     * 后期需求扩展
     */
    private String paySendAttributes;

    /**
     * 记录生成时间,数据维护
     */
    private Date gmtCreate;

    /**
     * 记录修改时间,数据维护
     */
    private Date gmtModified;
    
    public PaySendLogDO(){}
    
    public static PaySendLogDO createPaySendLogDOParam(Long payUserId,Long acceptUserId,String payType,String orderPayId,String sendInfo,Date creationTime,String sendType){
    	PaySendLogDO paySendLogDO = new PaySendLogDO();
    	paySendLogDO.setPayUserId(payUserId);
    	paySendLogDO.setAcceptUserId(acceptUserId);
    	paySendLogDO.setPayType(payType);
    	paySendLogDO.setOrderPayId(orderPayId);
    	paySendLogDO.setSendInfo(sendInfo);
    	paySendLogDO.setCreationTime(creationTime);
    	paySendLogDO.setSendType(sendType);
    	return paySendLogDO;
    }

    public String getSendType(){
        return sendType;
    }

    public Long getId(){
        return id;
    }

    public Long getPayUserId(){
        return payUserId;
    }

    public Long getAcceptUserId(){
        return acceptUserId;
    }

    public String getPayType(){
        return payType;
    }


    public String getSendInfo(){
        return sendInfo;
    }

    public Date getCreationTime(){
        return creationTime;
    }

    public String getPayAccount(){
        return payAccount;
    }

    public String getAcceptAccount(){
        return acceptAccount;
    }

    public String getPaySendAttributes(){
        return paySendAttributes;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public void setSendType(String sendType){
        this.sendType = sendType;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setPayUserId(Long payUserId){
        this.payUserId = payUserId;
    }

    public void setAcceptUserId(Long acceptUserId){
        this.acceptUserId = acceptUserId;
    }

    public void setPayType(String payType){
        this.payType = payType;
    }


    public String getOrderPayId() {
		return orderPayId;
	}

	public void setOrderPayId(String orderPayId) {
		this.orderPayId = orderPayId;
	}

	public void setSendInfo(String sendInfo){
        this.sendInfo = sendInfo;
    }

    public void setCreationTime(Date creationTime){
        this.creationTime = creationTime;
    }

    public void setPayAccount(String payAccount){
        this.payAccount = payAccount;
    }

    public void setAcceptAccount(String acceptAccount){
        this.acceptAccount = acceptAccount;
    }

    public void setPaySendAttributes(String paySendAttributes){
        this.paySendAttributes = paySendAttributes;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

}

