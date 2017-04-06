package com.yuwang.pinju.domain.trade;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * @Project: pinju-model
 * @Description: 保证金报文发送记录DO
 * @author 石兴 shixing@zba.com
 * @date 2011-8-1 下午02:08:56
 * @update 2011-8-1 下午02:08:56
 * @version V1.0
 */
public class DirectPaySendLogDO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 332419697200260651L;

	private Long id;
	
	private Long payOrderId;
	
	private Long fromMemberId;
	
	private String fromMemberPayment;
	
	private Long toMemberId;
	
	private String toMemberPayment;
	
	private String detail;
	
	/**
	 * 业务类型
	 */
	private Integer bizType;
	
	private Date sendTime;
	
	private Date gmtCreate;
	
	private Date gmtModified;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getFromMemberId(){
		return fromMemberId;
	}

	public void setFromMemberId(Long fromMemberId){
		this.fromMemberId = fromMemberId;
	}

	public String getFromMemberPayment(){
		return fromMemberPayment;
	}

	public void setFromMemberPayment(String fromMemberPayment){
		this.fromMemberPayment = fromMemberPayment;
	}

	public Long getToMemberId(){
		return toMemberId;
	}

	public void setToMemberId(Long toMemberId){
		this.toMemberId = toMemberId;
	}

	public String getToMemberPayment(){
		return toMemberPayment;
	}

	public void setToMemberPayment(String toMemberPayment){
		this.toMemberPayment = toMemberPayment;
	}

	public String getDetail(){
		return detail;
	}

	public void setDetail(String detail){
		this.detail = detail;
	}

	public Date getSendTime(){
		return sendTime;
	}

	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}

	public Date getGmtCreate(){
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified(){
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public void setBizType(Integer bizType){
		this.bizType = bizType;
	}

	public Integer getBizType(){
		return bizType;
	}

	public void setPayOrderId(Long payOrderId) {
		this.payOrderId = payOrderId;
	}

	public Long getPayOrderId() {
		return payOrderId;
	}
	/**
	 * 
	 * Created on 2011-8-15
	 * <p>
	 * Discription:　封装即时到帐发送日志
	 * </p>
	 * 
	 * @param directPayParamDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static DirectPaySendLogDO createDirectPaySendLogDO(
			DirectPayParamDO directPayParamDO,Long payOrderId,String sendDetail) {
		DirectPaySendLogDO marginSendLogDO = new DirectPaySendLogDO();
		marginSendLogDO.setBizType(directPayParamDO.getBizType());
		marginSendLogDO.setFromMemberId(directPayParamDO.getBuyerId());
		marginSendLogDO.setSendTime(directPayParamDO.getCreateTime());
		marginSendLogDO.setToMemberId(directPayParamDO.getSellerId());
		marginSendLogDO.setPayOrderId(payOrderId);
		marginSendLogDO.setDetail(sendDetail);
		return marginSendLogDO;
	}
}
