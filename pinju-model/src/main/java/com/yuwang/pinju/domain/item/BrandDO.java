package com.yuwang.pinju.domain.item;

import java.util.Date;

public class BrandDO implements java.io.Serializable {

	private static final long serialVersionUID = -3898436895842891593L;

	/**
	 * 品牌ID
	 */
	private Long id;

	/**
	 * 品牌名称
	 */
	private String name;

	/**
	 * 所属分组
	 */
	private Long groupId;

	/**
	 * 状态,0代表删除,1代表已审核,2代表审核未通过
	 */
	private Long status;

	/**
	 * LOGO地址
	 */
	private String logoUrl;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 排序值
	 */
	private Long sortOrder;

	/**
	 * 排序类型
	 */
	private Long sortType;

	/**
	 * 品牌编码
	 */
	private String brandCode;

	/**
	 * 商家编码
	 */
	private String businessCode;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Long getGroupId() {
		return groupId;
	}

	public Long getStatus() {
		return status;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public String getRemark() {
		return remark;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public Long getSortType() {
		return sortType;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public String getBusinessCode() {
		return businessCode;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setSortType(Long sortType) {
		this.sortType = sortType;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

}