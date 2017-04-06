/**
 * 
 */
package com.yuwang.pinju.domain.storage;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * @author yejingfeng
 * @Description 文件信息对象
 * 
 */
public class FileInfoDO extends BaseDO {

	private static final long serialVersionUID = -4479300708727030108L;

	/**
	 * 文件编号
	 */
	private Long id;

	/**
	 * 会员编号
	 */
	private Long memberId;

	/**
	 * 文件名称
	 */
	private String name;

	/**
	 * 文件路径
	 */
	private String path;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 会员名称
	 */
	private String memberName;
	
	/**
	 * 文件大小
	 */
	private Long size;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public Long getId() {
		return id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public Integer getType() {
		return type;
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

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
}
