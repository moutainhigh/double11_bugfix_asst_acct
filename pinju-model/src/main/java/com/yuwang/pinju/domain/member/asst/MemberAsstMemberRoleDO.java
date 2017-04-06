package com.yuwang.pinju.domain.member.asst;

import java.util.Date;

/**
 * <p>会员子账号与角色对应关系</p>
 *
 * @author gaobaowen
 * @since 2011-12-16 13:22:13
 */
public class MemberAsstMemberRoleDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 主账号会员编号（MEMBER_MEMBER.MEMBER_ID）
     */
    private Long masterMemberId;

    /**
     * 子账号会员编号
     */
    private Long asstMemberId;

    /**
     * 关联的角色ID（MEMBER_ASST_ROLE.ID）
     */
    private Long asstRoleId;

    /**
     * 关联的角色名（MEMBER_ASST_ROLE.ROLE_NAME）
     */
    private String asstRoleName;


    private Date gmtCreate;


    private Date gmtModified;
    
    public MemberAsstMemberRoleDO(){}

    public MemberAsstMemberRoleDO(Long masterMemberId, Long asstMemberId) {
    	this.masterMemberId = masterMemberId;
    	this.asstMemberId = asstMemberId;
    }

    public Long getId(){
        return id;
    }

    public Long getMasterMemberId(){
        return masterMemberId;
    }

    public Long getAsstMemberId(){
        return asstMemberId;
    }

    public Long getAsstRoleId(){
        return asstRoleId;
    }

    public String getAsstRoleName(){
        return asstRoleName;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setMasterMemberId(Long masterMemberId){
        this.masterMemberId = masterMemberId;
    }

    public void setAsstMemberId(Long asstMemberId){
        this.asstMemberId = asstMemberId;
    }

    public void setAsstRoleId(Long asstRoleId){
        this.asstRoleId = asstRoleId;
    }

    public void setAsstRoleName(String asstRoleName){
        this.asstRoleName = asstRoleName;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asstMemberId == null) ? 0 : asstMemberId.hashCode());
		result = prime * result + ((asstRoleId == null) ? 0 : asstRoleId.hashCode());
		result = prime * result + ((asstRoleName == null) ? 0 : asstRoleName.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((masterMemberId == null) ? 0 : masterMemberId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberAsstMemberRoleDO other = (MemberAsstMemberRoleDO) obj;
		if (asstMemberId == null) {
			if (other.asstMemberId != null)
				return false;
		} else if (!asstMemberId.equals(other.asstMemberId))
			return false;
		if (asstRoleId == null) {
			if (other.asstRoleId != null)
				return false;
		} else if (!asstRoleId.equals(other.asstRoleId))
			return false;
		if (asstRoleName == null) {
			if (other.asstRoleName != null)
				return false;
		} else if (!asstRoleName.equals(other.asstRoleName))
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
		if (masterMemberId == null) {
			if (other.masterMemberId != null)
				return false;
		} else if (!masterMemberId.equals(other.masterMemberId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberAsstMemberRoleDO [id=" + id + ", masterMemberId=" + masterMemberId + ", asstMemberId="
				+ asstMemberId + ", asstRoleId=" + asstRoleId + ", asstRoleName=" + asstRoleName + ", gmtCreate="
				+ gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}

