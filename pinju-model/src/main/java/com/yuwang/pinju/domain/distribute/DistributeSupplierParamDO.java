package com.yuwang.pinju.domain.distribute;

import java.util.Date;

import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * 供应商实体表
 * 
 * @author liyouguo
 * @version 1.0
 * @created 07-七月-2011 11:32:50
 */
public class DistributeSupplierParamDO extends Paginator  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7510582730491407644L;
	/**
	 * 招募书内容
	 */
	private String applyContent;
	/**
	 * 招募书名称
	 */
	private String applyTitle;
	/**
	 * 申请时间
	 */
	private java.util.Date gmtCreate;
	/**
	 * 修改时间
	 */
	private java.util.Date gmtModified;
	/**
	 * 自增字段
	 */
	private Integer id;
	/**
	 * 自增字段
	 */
	private Integer shopId;
	/**
	 * 供应商状态【0：可用】
	 */
	private Short status = 0;
	
	/**
	 * 最少返点比例
	 */
	private Double minReward;
	/**
	 * 最高返点比例
	 */
	private Double maxReward;
	/**
	 * 出售商品数量
	 */
	private Integer count;
	/**
	 * 供应商商铺信息[供应商展示页面专用]
	 */
	private ShopInfoDO shopInfoAllDO;
	/**
	 * 供应商商铺信息[通用]
	 */
	private ShopBusinessInfoDO shopBusinessInfoDO;
	/**
	 * 申请中的供应商
	 */
	private Integer applyCount;
	/**
	 * 合作中的供应商
	 */
	private Integer alignmentCount;
	/**
	 * 历史供应商
	 */
	private Integer historyCount;
	/**
	 * 供应商审核通过时间
	 */
	private java.util.Date agreeDate;
	/**
	 * 合作期限【单位：月，0：表示不过期】
	 */
	private Short cooperateMonth;
	/**
	 * 合作终止期限
	 */
	private Date endDate;
	/**
	 * 分销商ID
	 */
	private Long channelId;
	
	/**
	 * 会员编号
	 */
	private Long memberId;
	
	/**
	 * 会员昵称
	 */
	private String nickName;
	
	private Long itemId;
	
	public DistributeSupplierParamDO(Integer items, Integer currentPage) {
		super(10, items);
		setPage(currentPage);
	}
	
	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	public String getApplyTitle() {
		return applyTitle;
	}

	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
	}

	public java.util.Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public java.util.Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(java.util.Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public DistributeSupplierParamDO() {

	}

	public DistributeSupplierParamDO(Long itemId, Short status) {
		super();
		this.status = status;
		this.itemId = itemId;
	}

	public DistributeSupplierParamDO(Long memberId) {
	    super();
	    this.memberId = memberId;
	}

	public DistributeSupplierParamDO(Integer shopId, Long memberId) {
	    super();
	    this.shopId = shopId;
	    this.memberId = memberId;
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {

	}

	public Double getMinReward() {
		return minReward;
	}

	public void setMinReward(Double minReward) {
		this.minReward = minReward;
	}

	public Double getMaxReward() {
		return maxReward;
	}

	public void setMaxReward(Double maxReward) {
		this.maxReward = maxReward;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public ShopInfoDO getShopInfoAllDO() {
		return shopInfoAllDO;
	}

	public void setShopInfoAllDO(ShopInfoDO shopInfoAllDO) {
		this.shopInfoAllDO = shopInfoAllDO;
	}

	public ShopBusinessInfoDO getShopBusinessInfoDO() {
		return shopBusinessInfoDO;
	}

	public void setShopBusinessInfoDO(ShopBusinessInfoDO shopBusinessInfoDO) {
		this.shopBusinessInfoDO = shopBusinessInfoDO;
	}

	public Integer getApplyCount() {
		return applyCount;
	}

	public void setApplyCount(Integer applyCount) {
		this.applyCount = applyCount;
	}

	public Integer getAlignmentCount() {
		return alignmentCount;
	}

	public void setAlignmentCount(Integer alignmentCount) {
		this.alignmentCount = alignmentCount;
	}

	public Integer getHistoryCount() {
		return historyCount;
	}

	public void setHistoryCount(Integer historyCount) {
		this.historyCount = historyCount;
	}

	public java.util.Date getAgreeDate() {
		return agreeDate;
	}

	public void setAgreeDate(java.util.Date agreeDate) {
		this.agreeDate = agreeDate;
	}

	public Short getCooperateMonth() {
		return cooperateMonth;
	}

	public void setCooperateMonth(Short cooperateMonth) {
		this.cooperateMonth = cooperateMonth;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}