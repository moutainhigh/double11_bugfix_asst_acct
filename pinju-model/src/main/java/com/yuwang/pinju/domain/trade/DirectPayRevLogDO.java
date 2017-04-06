package com.yuwang.pinju.domain.trade;

import java.util.Date;
import com.yuwang.pinju.domain.BaseDO;

/**
 * @Project: pinju-model
 * @Description: 保证金保证金报文接收记录DO
 * @author 石兴 shixing@zba.com
 * @date 2011-8-1 下午02:09:59
 * @update 2011-8-1 下午02:09:59
 * @version V1.0
 */
public class DirectPayRevLogDO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5217425401948950366L;

	private Long id;	
	private Long payOrderId;
	/**
	 * 业务类型
	 */
	private Integer bizType;
	private String detail;
	private Date receiveTime;
	private Date gmtCreate;
	private Date gmtModified;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getDetail(){
		return detail;
	}

	public void setDetail(String detail){
		this.detail = detail;
	}

	public Date getReceiveTime(){
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime){
		this.receiveTime = receiveTime;
	}

	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtCreate(){
		return gmtCreate;
	}

	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public Date getGmtModified(){
		return gmtModified;
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
	 * Created on 2011-8-16
	 * <p>Discription: 封装回调更新DirectPaySendLogDO</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static DirectPayRevLogDO createPayRevLogDODirectPayRevLogDO(DirectPayReceiveParamDO directPayReceiveParamDO){
		DirectPayRevLogDO directPayRevLogDO = new DirectPayRevLogDO();
		directPayRevLogDO.setPayOrderId(directPayReceiveParamDO.getOrderNo());
		directPayRevLogDO.setReceiveTime(directPayReceiveParamDO.getEndTime());
		directPayRevLogDO.setDetail(directPayReceiveParamDO.getRevDetail());
		directPayRevLogDO.setBizType(directPayReceiveParamDO.getProductNo());
		return directPayRevLogDO;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bizType == null) ? 0 : bizType.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((payOrderId == null) ? 0 : payOrderId.hashCode());
		result = prime * result
				+ ((receiveTime == null) ? 0 : receiveTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectPayRevLogDO other = (DirectPayRevLogDO) obj;
		if (bizType == null) {
			if (other.bizType != null)
				return false;
		} else if (!bizType.equals(other.bizType))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModified == null) {
			if (other.gmtModified != null)
				return false;
		} else if (!gmtModified.equals(other.gmtModified))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (payOrderId == null) {
			if (other.payOrderId != null)
				return false;
		} else if (!payOrderId.equals(other.payOrderId))
			return false;
		if (receiveTime == null) {
			if (other.receiveTime != null)
				return false;
		} else if (!receiveTime.equals(other.receiveTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirectPayRevLogDO [bizType=" + bizType + ", detail=" + detail
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
				+ ", id=" + id + ", payOrderId=" + payOrderId
				+ ", receiveTime=" + receiveTime + "]";
	}
	
	
	
}
