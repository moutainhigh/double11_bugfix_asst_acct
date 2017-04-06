package com.yuwang.pinju.domain.member;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.yuwang.pinju.common.RelationEntity;
import com.yuwang.pinju.domain.BaseDO;

public class MemberSnsInfoDO extends BaseDO implements RelationEntity<Long>{

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 会员编号（T_MEMBER.MEMBER_ID）
	 */
	private Long memberId;
	
	/**
	 * 会员版本
	 */
	private Long version;
	
	/**
	 * 会员名称
	 */
	private String nickName;

	/**
	 * 会员头像（大：120*120）URL
	 */
	private String avatarsBasePath;

	/**
	 * 个人简介
	 */
	private String biography;

	private Date gmtCreate;

	private Date gmtModified;

	@Override
	public boolean isNew() {
		return ( id == null );
	}

	public Long getId() {
		return id;
	}

	public Long getMemberId() {
		return memberId;
	}

	@Length(max = 300, message = "memberSnsInfo.biography.length")
	public String getBiography() {
		return biography;
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

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getAvatarsBasePath() {
		return avatarsBasePath;
	}

	public void setAvatarsBasePath(String avatarsBasePath) {
		this.avatarsBasePath = avatarsBasePath;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((avatarsBasePath == null) ? 0 : avatarsBasePath.hashCode());
		result = prime * result
				+ ((biography == null) ? 0 : biography.hashCode());
		result = prime * result
				+ ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result
				+ ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((memberId == null) ? 0 : memberId.hashCode());
		result = prime * result
				+ ((nickName == null) ? 0 : nickName.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberSnsInfoDO other = (MemberSnsInfoDO) obj;
		if (avatarsBasePath == null) {
			if (other.avatarsBasePath != null)
				return false;
		} else if (!avatarsBasePath.equals(other.avatarsBasePath))
			return false;
		if (biography == null) {
			if (other.biography != null)
				return false;
		} else if (!biography.equals(other.biography))
			return false;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (memberId == null) {
			if (other.memberId != null)
				return false;
		} else if (!memberId.equals(other.memberId))
			return false;
		if (nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!nickName.equals(other.nickName))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberSnsInfoDO [memberId=" + memberId + ", version=" + version
				+ ", nickName=" + nickName + ", avatarsBasePath="
				+ avatarsBasePath + ", biography=" + biography + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + "]";
	}

}