package com.yuwang.pinju.domain.item;

import java.util.Date;

/**
 * 
 * 类目关系
 * 
 * @author liming
 * @since 2011-6-27
 *
 */
public class CpValueRelationDO implements java.io.Serializable {

	private static final long serialVersionUID = 1103123697436580851L;

	/**
	 * 关系编号
	 */
	private Long id;

	/**
	 * 类目属性编号
	 */
	private Long cpId;

	/**
	 * 子类目属性编号
	 */
	private Long childCpId;

	/**
	 * 类目属性值ID
	 */
	private Long cpValueId;

	/**
	 * 子类目属性值串,以\",\"分隔
	 */
	private String childCpValueIds;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public Long getChildCpId() {
		return childCpId;
	}

	public String getChildCpValueIds() {
		return childCpValueIds;
	}


	public Long getCpId() {
		return cpId;
	}


	public Long getCpValueId() {
		return cpValueId;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public Long getId() {
		return id;
	}


	public void setChildCpId(Long childCpId) {
		this.childCpId = childCpId;
	}


	public void setChildCpValueIds(String childCpValueIds) {
		this.childCpValueIds = childCpValueIds;
	}

	public void setCpId(Long cpId) {
		this.cpId = cpId;
	}

	public void setCpValueId(Long cpValueId) {
		this.cpValueId = cpValueId;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
