package com.yuwang.pinju.domain.distribute;

import com.yuwang.pinju.domain.Paginator;

/**
 * 分销商及供应商关联表实体
 * 
 * @author liyouguo
 * @version 1.0
 * @created 07-七月-2011 11:32:50
 */
public class DistribureChannelParamDO extends Paginator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1447241652333177494L;
	/**
	 * 供应商审核通过时间
	 */
	private java.util.Date agreeDate;
	/**
	 * 合作期限【单位：月，0：表示不过期】
	 */
	private Short cooperateMonth;
	/**
	 * 分销商申请时间
	 */
	private java.util.Date gmtCreate;
	/**
	 * 修改时间
	 */
	private java.util.Date gmtModified;
	/**
	 * 正分销的商品列表【分销商正在分销所属供应商商品】
	 */
	private String goodsList;
	/**
	 * 自增编号
	 */
	private Long id;
	/**
	 * 分销商编号
	 */
	private Long distributorId;
	/**
	 * 备忘录（审核通过的备忘录或者拒绝原因）
	 */
	private String remark;
	/**
	 * 当前状态（0：申请中，1：供应商审核通过，2：供应商审核拒绝）
	 */
	private Short status;
	/**
	 * 供应商编号
	 */
	private Integer supplierId;

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

	public String getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(String goodsList) {
		this.goodsList = goodsList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public DistribureChannelParamDO() {

	}

	public DistribureChannelParamDO(Long distributorId, Short status) {
		super();
		this.distributorId = distributorId;
		this.status = status;
	}

	public DistribureChannelParamDO(Long id) {
		super();
		this.id = id;
	}

	public DistribureChannelParamDO(Integer supplierId) {
		super();
		this.supplierId = supplierId;
	}

	public void finalize() throws Throwable {

	}

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}
}