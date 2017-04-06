package com.yuwang.pinju.domain.shop;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 店铺装修实体
 * 
 * @author mike
 * @version 1.0
 * @created 30-五月-2011 14:25:55
 */
@SuppressWarnings("serial")
public class ShopDesignDO extends BaseDO {

	/**
	 * 配置文件
	 */
	private String configuration;

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLeftModule1() {
		return leftModule1;
	}

	public void setLeftModule1(String leftModule1) {
		this.leftModule1 = leftModule1;
	}

	public String getLeftModule1Release() {
		return leftModule1Release;
	}

	public void setLeftModule1Release(String leftModule1Release) {
		this.leftModule1Release = leftModule1Release;
	}

	public String getLeftModule2() {
		return leftModule2;
	}

	public void setLeftModule2(String leftModule2) {
		this.leftModule2 = leftModule2;
	}

	public String getLeftModule2Release() {
		return leftModule2Release;
	}

	public void setLeftModule2Release(String leftModule2Release) {
		this.leftModule2Release = leftModule2Release;
	}

	public String getRightModule1() {
		return rightModule1;
	}

	public void setRightModule1(String rightModule1) {
		this.rightModule1 = rightModule1;
	}

	public String getRightModule1Release() {
		return rightModule1Release;
	}

	public void setRightModule1Release(String rightModule1Release) {
		this.rightModule1Release = rightModule1Release;
	}

	public String getRightModule2() {
		return rightModule2;
	}

	public void setRightModule2(String rightModule2) {
		this.rightModule2 = rightModule2;
	}

	public String getRightModule2Release() {
		return rightModule2Release;
	}

	public void setRightModule2Release(String rightModule2Release) {
		this.rightModule2Release = rightModule2Release;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	private Date gmtCreate;
	private Date gmtModified;
	private Integer id;
	/**
	 * 店铺装修-左侧导航模块1
	 */
	private String leftModule1;
	/**
	 * 店铺装修-左侧导航自定义发布模块1
	 */
	private String leftModule1Release;
	/**
	 * 店铺装修-左侧导航模块2:备用
	 */
	private String leftModule2;
	/**
	 * 店铺装修-左侧导航自定义发布模块1
	 */
	private String leftModule2Release;
	/**
	 * 店铺装修-右侧导航自定义模块1
	 */
	private String rightModule1;
	/**
	 * 店铺装修-右侧导航自定义发布模块1
	 */
	private String rightModule1Release;
	/**
	 * 店铺装修-右侧导航自定义模块2
	 */
	private String rightModule2;
	/**
	 * 店铺装修-右侧导航自定义发布模块2
	 */
	private String rightModule2Release;
	private long shopId;
	private long userId;

	public ShopDesignDO() {

	}

	public void finalize() throws Throwable {

	}

}