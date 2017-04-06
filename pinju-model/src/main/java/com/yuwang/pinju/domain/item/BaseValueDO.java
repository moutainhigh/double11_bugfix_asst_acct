package com.yuwang.pinju.domain.item;

import java.util.Date;

/**
 * 基本属性
 * 
 * @author liming
 *
 */
public class BaseValueDO implements java.io.Serializable {


	private static final long serialVersionUID = 5891159078368710896L;
	
	/**
	 * 值ID
	 */
	private Long id;
	
	/**
	 * 值
	 */
	private String value;
	
	/**
	 * hashcode
	 */
	private Long hashcode;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	/**
	 * From CustomProValueDO
	 * 值类型
	 * @return
	 */
	private Integer valueType;
	/**
	 * From CustomProValueDO
	 * 图片地址
	 * @return
	 */
	private String imgUrl;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getHashcode() {
		return hashcode;
	}

	public void setHashcode(Long hashcode) {
		this.hashcode = hashcode;
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

	public Integer getValueType() {
		return valueType;
	}

	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result
				+ ((hashcode == null) ? 0 : hashcode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseValueDO other = (BaseValueDO) obj;
		if (gmtCreate == null) {
			if (other.gmtCreate != null)
				return false;
		} else if (!gmtCreate.equals(other.gmtCreate))
			return false;
		if (gmtModified == null) {
			if (other.gmtModified != null)
				return false;
		} else if (!gmtModified.equals(other.gmtModified))
			return false;
		if (hashcode == null) {
			if (other.hashcode != null)
				return false;
		} else if (!hashcode.equals(other.hashcode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseValueDO [gmtCreate=" + gmtCreate + ", gmtModified="
				+ gmtModified + ", hashcode=" + hashcode + ", id=" + id
				+ ", value=" + value + "]";
	}
}