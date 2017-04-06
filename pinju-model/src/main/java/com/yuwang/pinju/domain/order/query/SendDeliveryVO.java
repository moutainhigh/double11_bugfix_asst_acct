package com.yuwang.pinju.domain.order.query;

import java.util.Date;

import com.yuwang.pinju.Constant.OrderTimeEnum;

/**
 * 
 * Created on 2011-7-14
 * 
 * @see <p>
 *      Discription: 卖家发货
 *      </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class SendDeliveryVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 934769772413318874L;

	/**
	 * 订单主键
	 */
	private long orderId;
	/**
	 * 外部物流编号
	 */
	private String outLogisticsId;
	/**
	 * 物流方式 1为 未选 2为 未知,其它根据第三方物流接口的物流公司编号
	 */
	private String logisticsType;
	/**
	 * 物流类型
	 */
	private String consignmentMode ;

	/**
	 * 发货地址编号
	 */
	private long memberDeliveriesId;
	/**
	 * 发货人姓名
	 */
	private String sendName;
	/**
	 * 发货地址
	 */
	private String sendAddress;
	/**
	 * 记录修改时间
	 */
	private Date gmtModified = new Date();
	/**
	 * 发货时间
	 */
	private Date consingTime = new Date();
	/**
	 * 发件人会员编号
	 */
	private Long senderMemberId;
	/**
	 * 发货人邮编
	 */
	private String sendPost;
	/**
	 * 发货人电话
	 */
	private String sendPhone;
	/**
	 * 发货人手机
	 */
	private String sendMobilePhone;
	/**
	 * 发送人ip
	 */
	private String sendIp;
	
	/**
	 * 物流公司名称
	 */
	private String logisticsName;
	
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getOutLogisticsId() {
		return outLogisticsId;
	}

	public void setOutLogisticsId(String outLogisticsId) {
		this.outLogisticsId = outLogisticsId;
	}

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public long getMemberDeliveriesId() {
		return memberDeliveriesId;
	}

	public void setMemberDeliveriesId(long memberDeliveriesId) {
		this.memberDeliveriesId = memberDeliveriesId;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Date getConsingTime() {
		return consingTime;
	}

	public void setConsingTime(Date consingTime) {
		this.consingTime = consingTime;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getSendAddress() {
		return sendAddress;
	}

	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}

	public String getConsignmentMode() {
		return consignmentMode;
	}

	public void setConsignmentMode(String consignmentMode) {
		this.consignmentMode = consignmentMode;
	}

	public Long getSenderMemberId() {
		return senderMemberId;
	}

	public void setSenderMemberId(Long senderMemberId) {
		this.senderMemberId = senderMemberId;
	}

	public Integer getAutoConfirmTime(String consignmentMode) {
		OrderTimeEnum ot = OrderTimeEnum.getValueByType(Integer.parseInt(consignmentMode));
		return Integer.parseInt(ot.getFeatureName());
	}

	public String getSendPost() {
		return sendPost;
	}

	public void setSendPost(String sendPost) {
		this.sendPost = sendPost;
	}

	public String getSendPhone() {
		return sendPhone;
	}

	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}

	public String getSendMobilePhone() {
		return sendMobilePhone;
	}

	public void setSendMobilePhone(String sendMobilePhone) {
		this.sendMobilePhone = sendMobilePhone;
	}

	public String getSendIp() {
		return sendIp;
	}

	public void setSendIp(String sendIp) {
		this.sendIp = sendIp;
	}

	@Override
	public String toString() {
		return "SendDeliveryVO [consignmentMode=" + consignmentMode
				+ ", consingTime=" + consingTime + ", gmtModified="
				+ gmtModified + ", logisticsType=" + logisticsType
				+ ", memberDeliveriesId=" + memberDeliveriesId + ", orderId="
				+ orderId + ", outLogisticsId=" + outLogisticsId
				+ ", sendAddress=" + sendAddress + ", sendIp=" + sendIp
				+ ", sendMobilePhone=" + sendMobilePhone + ", sendName="
				+ sendName + ", sendPhone=" + sendPhone + ", sendPost="
				+ sendPost + ", senderMemberId=" + senderMemberId + "]";
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}



	
}
