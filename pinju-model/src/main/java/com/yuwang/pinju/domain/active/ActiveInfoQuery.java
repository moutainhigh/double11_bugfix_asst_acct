package com.yuwang.pinju.domain.active;

import java.util.Date;

import org.apache.commons.lang.xwork.builder.ToStringBuilder;
import org.apache.commons.lang.xwork.builder.ToStringStyle;

import com.yuwang.pinju.domain.Paginator;

/**
 * 活动信息查询类
 * @author qiuhongming
 *
 */
public class ActiveInfoQuery extends Paginator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4639224691597047043L;
	/**
	 * 活动ID
	 */
	private Long activityId;
	/**
	 * 报名开始时间
	 */
	private Date registStartTime = new Date();
	/**
	 * 报名结束时间
	 */
	private Date registEndTime = new Date();
	/**
	 * 发布状态 0 - 待发布；1 - 已发布； 2 - 已经下架
	 */
	private Integer publishStatus = 1;
	/**
	 * 活动类型 1 - 商品；2 - 店铺
	 */
	private Integer registType;
	/**
	 * 报名总数限制 对于商品活动，是报名通过的商品的总数，如不填，默认为100; 对于店铺活动，是报名通过的店铺的总数，如不填，默认为20.
	 */
	private Integer totalNum;
	/**
	 * 单品数量限制 对于商品活动，是一个卖家报名的商品数量（无论此商品是否通过或不通过），如不填，默认为5;
	 * 对于店铺活动，是一个卖家报名的店铺数量，如不填，则默认为1.
	 */
	private Integer individualNum;
	/**
	 * 报名数量
	 */
	private Integer registNum;
	/**
	 * 审核通过数量
	 */
	private Integer reviewPassNum;
	/**
	 * 审核状态
	 */
	private Integer checkStatus;
	/**
	 * 商家昵称
	 */
	private String nickName;
	/**
	 * 会员编号
	 */
	private Long memberId;
	/**
	 * 卖家等级
	 */
	private String shopLevel;
	/**
	 * 店铺类型
	 */
	private String shopType;
	/**
	 * 消保类型
	 */
	private String cpType;
	/**
	 * 所属类目
	 */
	private String categoryId;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Date getRegistStartTime() {
		return registStartTime;
	}

	public void setRegistStartTime(Date registStartTime) {
		this.registStartTime = registStartTime;
	}

	public Date getRegistEndTime() {
		return registEndTime;
	}

	public void setRegistEndTime(Date registEndTime) {
		this.registEndTime = registEndTime;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public Integer getRegistType() {
		return registType;
	}

	public void setRegistType(Integer registType) {
		this.registType = registType;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getShopLevel() {
		return shopLevel;
	}

	public void setShopLevel(String shopLevel) {
		this.shopLevel = shopLevel;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getCpType() {
		return cpType;
	}

	public void setCpType(String cpType) {
		this.cpType = cpType;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getIndividualNum() {
		return individualNum;
	}

	public void setIndividualNum(Integer individualNum) {
		this.individualNum = individualNum;
	}

	public Integer getRegistNum() {
		return registNum;
	}

	public void setRegistNum(Integer registNum) {
		this.registNum = registNum;
	}

	public Integer getReviewPassNum() {
		return reviewPassNum;
	}

	public void setReviewPassNum(Integer reviewPassNum) {
		this.reviewPassNum = reviewPassNum;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
