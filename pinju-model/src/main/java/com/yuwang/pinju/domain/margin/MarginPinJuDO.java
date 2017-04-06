package com.yuwang.pinju.domain.margin;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 账户保证金domain
 */
public class MarginPinJuDO extends BaseDO {

	private static final long serialVersionUID = 1L;
	/**
	 * 当前账户编号
	 */
	private Long id;
	/**
	 * 当前账户余额
	 */
	private Long curBalAmount;
	/**
	 * 第一次创建时间
	 */
	private Date gmtCreate;
	/**
	 * 最近一次的修改时间
	 */
	private Date gmtModified;
	/**
	 * 乐观锁版本号
	 */
	private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCurBalAmount() {
		return curBalAmount;
	}

	public void setCurBalAmount(Long curBalAmount) {
		this.curBalAmount = curBalAmount;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
