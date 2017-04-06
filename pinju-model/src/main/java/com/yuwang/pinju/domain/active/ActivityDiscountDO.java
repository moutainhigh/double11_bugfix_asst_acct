package com.yuwang.pinju.domain.active;

import java.util.Date;

import org.apache.commons.lang.xwork.builder.ToStringBuilder;
import org.apache.commons.lang.xwork.builder.ToStringStyle;

public class ActivityDiscountDO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 限时活动名称
	 */
	private String actName;

	/**
	 * 限时活动开始时间
	 */
	private Date startTime;

	/**
	 * 限时活动结束时间
	 */
	private Date endTime;

	/**
	 * 商品列表
	 */
	private String itemList;

	/**
	 * 活动商品数量
	 */
	private Long itemCount;

	/**
	 * 折扣列表
	 */
	private String discountList;

	/**
	 * 默认折扣
	 */
	private Long discountDefault;

	/**
	 * 限购数量
	 */
	private String limitList;
	
	/**
	 * 卖家昵称
	 */
	private String memberNick;

	/**
	 * 卖家ID
	 */
	private Long memberId;

	/**
	 * 0 未开始 1 进行中 2 已结束 3 删除
	 */
	private Long status;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public ActivityDiscountDO() {
	};

	public ActivityDiscountDO(Long id, String actName, Date startTime, Date endTime, String itemList, Long itemCount,
			String discountList, Long discountDefault, String memberNick, Long memberId, Long status, Date gmtCreate,
			Date gmtModified) {
		super();
		this.id = id;
		this.actName = actName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.itemList = itemList;
		this.itemCount = itemCount;
		this.discountList = discountList;
		this.discountDefault = discountDefault;
		this.memberNick = memberNick;
		this.memberId = memberId;
		this.status = status;
		this.gmtCreate = gmtCreate;
		this.gmtModified = gmtModified;
	}

	public Long getId() {
		return id;
	}

	public String getActName() {
		return actName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getItemList() {
		return itemList;
	}

	public Long getItemCount() {
		return itemCount;
	}

	public String getDiscountList() {
		return discountList;
	}

	public Long getDiscountDefault() {
		return discountDefault;
	}

	public String getMemberNick() {
		return memberNick;
	}

	public Long getMemberId() {
		return memberId;
	}

	public Long getStatus() {
		return status;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setItemList(String itemList) {
		this.itemList = itemList;
	}

	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}

	public void setDiscountList(String discountList) {
		this.discountList = discountList;
	}

	public void setDiscountDefault(Long discountDefault) {
		this.discountDefault = discountDefault;
	}

	public String getLimitList() {
		return limitList;
	}

	public void setLimitList(String limitList) {
		this.limitList = limitList;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	
}
