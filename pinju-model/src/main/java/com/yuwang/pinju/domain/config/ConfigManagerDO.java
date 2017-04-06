package com.yuwang.pinju.domain.config;

import java.io.Serializable;
import java.util.Date;


/**  
* @Project: pinju-model
* @Package com.yuwang.pinju.domain.config
* @Description: 配置信息DO
* @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
* @date 2011-10-27 上午9:18:29
* @version V1.0  
*/
public class ConfigManagerDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -550082916138946443L;

	/**
	 * 约束ID
	 */
	private Long id;
	/**
	 * 配置名称(仅用于识别和CRM查询)
	 */
	private String name;
	/**
	 * 配置关键KEY,同一个工程不会重复,
	 */
	private String key;
	/**
	 * 配置值
	 */
	private String value;
	/**
	 * 备注信息
	 */
	private String memo;
	/**
	 * 工程类型(该配置用于哪个工程)
	 */
	private Integer projectType;
	/**
	 * 状态 0 无效 1 使用中
	 */
	private Integer status;

	private Date gmtModified;
	private Date gmtCreate;
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getProjectType() {
		return projectType;
	}
	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	
}
