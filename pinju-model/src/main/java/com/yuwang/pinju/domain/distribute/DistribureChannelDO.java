package com.yuwang.pinju.domain.distribute;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.domain.ConfigurableSupport;

/**
 * 分销商及供应商关联表实体
 * 
 * @author liyouguo
 * @version 1.0
 * @created 07-七月-2011 11:32:50
 */
public class DistribureChannelDO extends ConfigurableSupport {

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
	 * 自增编号
	 */
	private Long id;

	/**
	 * 会员编号
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

	/**
	 * 记录版本号
	 */
	private String version;

	/**
	 * 分销商实体类
	 */
	private DistributeDistributorDO distributeDistributorDO;
	/**
	 * 更新前状态（0：申请中，1：供应商审核通过，2：供应商审核拒绝）
	 */
	private Short oldStatus;
	/**
	 * 页面checked 1:选中 2:未选中
	 */
	private Integer checked;

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

	public DistribureChannelDO() {

	}

	public DistribureChannelDO(Long distributorId, Short status) {
		super();
		this.distributorId = distributorId;
		this.status = status;
	}

	public DistribureChannelDO(Long id) {
		super();
		this.id = id;
	}

	public void finalize() throws Throwable {

	}

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}

	public DistributeDistributorDO getDistributeDistributorDO() {
		return distributeDistributorDO;
	}

	public void setDistributeDistributorDO(DistributeDistributorDO distributeDistributorDO) {
		this.distributeDistributorDO = distributeDistributorDO;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 分销商申请时间
	 */
	public String getApplyDate() {
		if (null == this.gmtCreate) {
			return "";
		} else {
			return new SimpleDateFormat("yyyy-MM-dd").format(this.gmtCreate);
		}
	}

	/**
	 * 分销商开始合作时间
	 * 
	 * @return
	 */
	public String getStartDate() {
		if (null == this.agreeDate) {
			return "";
		} else {
			return new SimpleDateFormat("yyyy-MM-dd").format(this.agreeDate);
		}
	}

	/**
	 * 分销商合同到期时间
	 * 
	 * @return
	 */
	public String getEndDate() {
		if (null == this.agreeDate || null == this.cooperateMonth) {
			return "";
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(this.agreeDate);
			c.add(Calendar.MONTH, this.cooperateMonth);
			return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getDisplayStatus() {
		String display;
		switch (this.status) {
		case 0:
			display = "申请中";
			break;
		case 1:
			display = "审核通过";
			Date end = DateUtils.addMonths(this.agreeDate, this.cooperateMonth);
			if (null != end && DateUtil.current().after(end)) {
				display = "合同过期";
			}
			break;
		case 2:
			display = "审核拒绝";
			break;
		case 3:
			display = "主动撤销";
			break;
		default:
			display = "";
			break;
		}
		return display;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public String getNewVersion() {
		return UUID.randomUUID().toString();
	}

	public Short getOldStatus() {
	    return oldStatus;
	}

	public void setOldStatus(Short oldStatus) {
	    this.oldStatus = oldStatus;
	}
}