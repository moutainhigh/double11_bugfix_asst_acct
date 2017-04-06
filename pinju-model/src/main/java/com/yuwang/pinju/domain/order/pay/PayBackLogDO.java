package com.yuwang.pinju.domain.order.pay;

import java.util.Date;

/**
 * @Discription: 接受日志
 * @Project: pinju-model
 * @Package: com.yuwang.pinju.domain.order.pay
 * @Title: PayBackLogDO.java
 * @author: [贺泳]
 * @date 2011-9-15 下午01:15:47
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class PayBackLogDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发送类型
     */
    private String sendType;

    /**
     * 支付返回日志编号
     */
    private Long id;

    /**
     * 内部支付订单编号
     */
    private String orderPayId;

    /**
     * 外部支付订单编号
     */
    private String outPayId;

    /**
     * 返回内容
     */
    private String backInfo;

    /**
     * 日志生成时间
     */
    private Date creationTime;

    /**
     * 支付平台类型
     */
    private String payType;

    /**
     * 后期根据需求扩展
     */
    private String payBackAttributes;

    /**
     * 记录生成时间,数据维护
     */
    private Date gmtCreate;

    /**
     * 记录修改时间,数据维护
     */
    private Date gmtModified;
    
    public PayBackLogDO(){}
    
    public static PayBackLogDO createPayBackLogDO(String orderPayId,String outPayId,String backInfo,Date creationTime,String payType,String sendType){
    	PayBackLogDO payBackLogDO = new PayBackLogDO();
    	payBackLogDO.setOrderPayId(orderPayId);
    	payBackLogDO.setOutPayId(outPayId);
    	payBackLogDO.setBackInfo(backInfo);
    	payBackLogDO.setCreationTime(creationTime);
    	payBackLogDO.setPayType(payType);
    	payBackLogDO.setSendType(sendType);
    	return payBackLogDO;
    }


    public String getSendType(){
        return sendType;
    }

    public Long getId(){
        return id;
    }

    public String getOutPayId(){
        return outPayId;
    }

    public String getBackInfo(){
        return backInfo;
    }

    public Date getCreationTime(){
        return creationTime;
    }

    public String getPayType(){
        return payType;
    }

    public String getPayBackAttributes(){
        return payBackAttributes;
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

    public String getOrderPayId() {
		return orderPayId;
	}

	public void setOrderPayId(String orderPayId) {
		this.orderPayId = orderPayId;
	}

	public void setOutPayId(String outPayId){
        this.outPayId = outPayId;
    }

    public void setBackInfo(String backInfo){
        this.backInfo = backInfo;
    }

    public void setCreationTime(Date creationTime){
        this.creationTime = creationTime;
    }

    public void setPayType(String payType){
        this.payType = payType;
    }

    public void setPayBackAttributes(String payBackAttributes){
        this.payBackAttributes = payBackAttributes;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

}