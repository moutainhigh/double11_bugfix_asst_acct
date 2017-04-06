package com.yuwang.pinju.domain.active;

import java.util.Date;

import org.apache.commons.lang.xwork.builder.ToStringBuilder;
import org.apache.commons.lang.xwork.builder.ToStringStyle;

/**
 * 限时折扣中间表（供搜索引擎调用）
 * @author qiuhongming
 * @create 2011-12-07
 *
 */
public class ActivityDiscountMapDO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 原来活动ID
	 */
	private Long actId;

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
	 * 商品ID
	 */
	private Long itemId;

	/**
	 * 当前商品折扣
	 */
	private Integer discount;

	/**
	 * 默认折扣
	 */
	private Integer discountDefault;

	/**
	 * 限购数量
	 */
	private Integer limitList;
	
	/**
	 * 卖家昵称
	 */
	private String memberNick;

	/**
	 * 卖家ID
	 */
	private Long memberId;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	public ActivityDiscountMapDO() {
		
	};

	public Long getActId() {
		return actId;
	}

	public void setActId(Long actId) {
		this.actId = actId;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getDiscountDefault() {
		return discountDefault;
	}

	public void setDiscountDefault(Integer discountDefault) {
		this.discountDefault = discountDefault;
	}

	public Integer getLimitList() {
		return limitList;
	}

	public void setLimitList(Integer limitList) {
		this.limitList = limitList;
	}

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
