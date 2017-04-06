package com.yuwang.pinju.domain.shop;

import java.util.Date;

import com.yuwang.pinju.domain.ConfigurableSupport;


/**
 * 用户装修页面
 * @version 1.0
 * @created 17-六月-2011 10:39:01
 */
public class ShopUserPageDO extends ConfigurableSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8887646943277649676L;
	/**
	 * 页面描述
	 */
	private String description;
	/**
	 * 自增id
	 */
	private Long id;
	/**
	 * 页面名字
	 */
	private String name;
	/**
	 * 对应的装修page id
	 */
	private Integer pageId;
	/**
	 * 店铺装修发布时间
	 */
	private Date releaseDate;
	/**
	 * 发布后的页面xml结构
	 */
	private String releaseStructure;
	/**
	 * 保存页面xml结构
	 */
	private String saveStructure;
	/**
	 * 左侧：店铺分类
	 */
	private String shopCategory;
	/**
	 * 店铺id
	 */
	private Integer shopId;
	/**
	 * 掌柜推荐
	 */
	private String shopRecommend;
	/**
	 * 皮肤
	 */
	private String skinId;
	/**
	 * 页面当前状态
	 */
	private Integer status;
	/**
	 * 页面标题
	 */
	private String title;
	/**
	 * 用户id
	 */
	private Long userId;
	
	private Date gmtCreate;
	
	private Date gmtModified;
	/**
	 * 页面导航排序
	 */
	private Integer orderId;
	
	/**
	 * 用户保存自定义页面的类型
	 * 1：增加
	 * 2：修改
	 * 3：删除
	 */
	private int saveType = 0;

	public int getSaveType() {
		return saveType;
	}

	public void setSaveType(int saveType) {
		this.saveType = saveType;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public ShopUserPageDO(){

	}

	public void finalize() throws Throwable {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleaseStructure() {
		return releaseStructure;
	}

	public void setReleaseStructure(String releaseStructure) {
		this.releaseStructure = releaseStructure;
	}

	public String getSaveStructure() {
		return saveStructure;
	}

	public void setSaveStructure(String saveStructure) {
		this.saveStructure = saveStructure;
	}

	public String getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopRecommend() {
		return shopRecommend;
	}

	public void setShopRecommend(String shopRecommend) {
		this.shopRecommend = shopRecommend;
	}

	public String getSkinId() {
		return skinId;
	}

	public void setSkinId(String skinId) {
		this.skinId = skinId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}