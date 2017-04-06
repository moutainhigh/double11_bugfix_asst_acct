package com.yuwang.pinju.domain.active;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.xwork.builder.ToStringBuilder;
import org.apache.commons.lang.xwork.builder.ToStringStyle;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 活动信息
 * @author qiuhongming
 *
 */
public class ActiveInfoDO extends BaseDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9152634213577391104L;
	/**
	 * 编号
	 */
	private Long id;
	/**
	 * 活动名称
	 */
	private String name;
	/**
	 * 活动开始时间
	 */
	private Date activeStartTime;
	/**
	 * 活动结束时间
	 */
	private Date activeEndTime;
	/**
	 * 报名开始时间
	 */
	private Date registStartTime;
	/**
	 * 报名结束时间
	 */
	private Date registEndTime;
	/**
	 * 消耗积分
	 */
	private Integer points;
	/**
	 * 活动简介
	 */
	private String brief;
	/**
	 * 活动说明
	 */
	private String memo;
	/**
	 * 活动图标
	 */
	private String logoUrl;
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
	 * 店铺类型
	 */
	private String shopType;
	/**
	 * 店铺等级
	 */
	private String shopLevel;
	/**
	 * 消保类型
	 */
	private String cpType;
	/**
	 * 参加类目ID集合
	 */
	private String categoryIds;
	/**
	 * 折扣率
	 */
	private Integer discountRate;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	/**
	 * 报名数量
	 */
	private Integer registNum;
	/**
	 * 审核通过数量
	 */
	private Integer reviewPassNum;
	/**
	 * 发布状态 0 - 待发布；1 - 已发布； 2 - 已经下架
	 */
	private Integer publishStatus;
	/**
	 * 报名类型 1 - 商品；2 - 店铺
	 */
	private Integer registType;

	public ActiveInfoDO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getActiveStartTime() {
		return activeStartTime;
	}

	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}

	public Date getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
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

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
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

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getShopLevel() {
		return shopLevel;
	}

	public void setShopLevel(String shopLevel) {
		this.shopLevel = shopLevel;
	}

	public String getCpType() {
		return cpType;
	}

	public void setCpType(String cpType) {
		this.cpType = cpType;
	}

	public String getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Integer getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
