package com.yuwang.pinju.domain.word;

import java.util.Date;

public class SensitiveWordDO extends SensitiveWordBaseDO {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 记录 ID
	 */
	private Integer id;
	
	/**
	 * 屏蔽关键词批次
	 */
	private String batchId;
	
	/**
	 * 屏蔽关键词
	 */
	private String word;
	
	/**
	 * 屏蔽关键词创建人
	 */
	private String createUser;
	
	/**
	 * 屏蔽关键词创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 屏蔽关键词状态[0:无效、1：有效]
	 */
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Integer getStatus() {
	    return status;
	}

	public void setStatus(Integer status) {
	    this.status = status;
	}
}
