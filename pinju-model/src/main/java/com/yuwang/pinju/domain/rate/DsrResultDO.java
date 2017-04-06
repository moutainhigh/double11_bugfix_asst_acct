package com.yuwang.pinju.domain.rate;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.yuwang.pinju.common.RelationEntity;
import com.yuwang.pinju.domain.BaseDO;

public class DsrResultDO extends BaseDO implements RelationEntity<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 动态评论的最小分值 -- 1 分
	 */
	public final static Integer RATE_MIN = 1;

	/**
	 * 动态评论的最大分值 -- 5 分
	 */
	public final static Integer RATE_MAX = 5;

	/**
	 * 是否匿名评价 -- 否（0），默认值
	 */
	public final static Integer ANONYMOUS_NO = 0;

	/**
	 * 是否匿名评价 -- 是（1）
	 */
	public final static Integer ANONYMOUS_YES = 1;

	/**
	 * 评价状态 -- 无效（0）
	 */
	public final static Integer STATUS_INVALID = 0;

	/**
	 * 评价状态 -- 有效（1），默认值
	 */
	public final static Integer STATUS_VALID = 1;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 评价者编号
	 */
	private Long senderId;

	/**
	 * 评价者昵称
	 */
	private String senderNick;

	/**
	 * 接收者编号
	 */
	private Long receiverId;

	/**
	 * 接收者昵称
	 */
	private String receiverNick;

	/**
	 * 订单编号
	 */
	private Long orderId;

	/**
	 * 商品编号
	 */
	private Long itemId;

	/**
	 * 商品标题
	 */
	private String itemTitle;

	/**
	 * 维度编号
	 */
	private Integer dimensionId;

	/**
	 * 维度名称
	 */
	private String dimensionName;

	/**
	 * 评分
	 */
	private Integer rate;

	/**
	 * 评分时间
	 */
	private Date rateTime;

	/**
	 * 是否匿名评价（0：否；1：是）
	 */
	private Integer anonymous = ANONYMOUS_NO;

	/**
	 * 状态（0：无效；1：有效）
	 */
	private Integer status = STATUS_VALID;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	private String comment;

	@Override
	public boolean isNew() {
		return (id == null);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSenderNick() {
		return senderNick;
	}
	public void setSenderNick(String senderNick) {
		this.senderNick = senderNick;
	}

	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverNick() {
		return receiverNick;
	}
	public void setReceiverNick(String receiverNick) {
		this.receiverNick = receiverNick;
	}

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@NotNull(message = "{dsrResultDO.itemId.notnull}")
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemTitle() {
		return itemTitle;
	}
	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	@NotNull(message = "{dsrResultDO.dimensionId.notnull}")
	public Integer getDimensionId() {
		return dimensionId;
	}
	public void setDimensionId(Integer dimensionId) {
		this.dimensionId = dimensionId;
	}

	@NotEmpty(message = "{dsrResultDO.dimensionName.notempty}")
	public String getDimensionName() {
		return dimensionName;
	}
	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
	}

	@NotNull(message = "{dsrResultDO.rate.notnull}")
	@Range(min = 1, max = 5, message = "{dsrResultDO.rate.range}")
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Date getRateTime() {
		return rateTime;
	}
	public void setRateTime(Date rateTime) {
		this.rateTime = rateTime;
	}

	@NotNull(message = "{dsrResultDO.anonymous.notnull}")
	@Range(min = 0, max = 1, message = "{dsrResultDO.anonymous.range}")
	public Integer getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((anonymous == null) ? 0 : anonymous.hashCode());
		result = prime * result + ((dimensionId == null) ? 0 : dimensionId.hashCode());
		result = prime * result + ((dimensionName == null) ? 0 : dimensionName.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((itemTitle == null) ? 0 : itemTitle.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((rateTime == null) ? 0 : rateTime.hashCode());
		result = prime * result + ((receiverId == null) ? 0 : receiverId.hashCode());
		result = prime * result + ((receiverNick == null) ? 0 : receiverNick.hashCode());
		result = prime * result + ((senderId == null) ? 0 : senderId.hashCode());
		result = prime * result + ((senderNick == null) ? 0 : senderNick.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		DsrResultDO other = (DsrResultDO) obj;
		if (anonymous == null) {
			if (other.anonymous != null)
				return false;
		} else if (!anonymous.equals(other.anonymous))
			return false;
		if (dimensionId == null) {
			if (other.dimensionId != null)
				return false;
		} else if (!dimensionId.equals(other.dimensionId))
			return false;
		if (dimensionName == null) {
			if (other.dimensionName != null)
				return false;
		} else if (!dimensionName.equals(other.dimensionName))
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
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (itemTitle == null) {
			if (other.itemTitle != null)
				return false;
		} else if (!itemTitle.equals(other.itemTitle))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (rateTime == null) {
			if (other.rateTime != null)
				return false;
		} else if (!rateTime.equals(other.rateTime))
			return false;
		if (receiverId == null) {
			if (other.receiverId != null)
				return false;
		} else if (!receiverId.equals(other.receiverId))
			return false;
		if (receiverNick == null) {
			if (other.receiverNick != null)
				return false;
		} else if (!receiverNick.equals(other.receiverNick))
			return false;
		if (senderId == null) {
			if (other.senderId != null)
				return false;
		} else if (!senderId.equals(other.senderId))
			return false;
		if (senderNick == null) {
			if (other.senderNick != null)
				return false;
		} else if (!senderNick.equals(other.senderNick))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DsrResultDO [id=" + id + ", senderId=" + senderId + ", senderNick=" + senderNick + ", receiverId="
				+ receiverId + ", receiverNick=" + receiverNick + ", orderId=" + orderId + ", itemId=" + itemId
				+ ", itemTitle=" + itemTitle + ", dimensionId=" + dimensionId + ", dimensionName=" + dimensionName
				+ ", rate=" + rate + ", rateTime=" + rateTime + ", anonymous=" + anonymous + ", status=" + status
				+ ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", comment=" + comment + "]";
	}
}
