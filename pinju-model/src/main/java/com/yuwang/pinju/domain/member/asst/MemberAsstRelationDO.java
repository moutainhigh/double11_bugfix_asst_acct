package com.yuwang.pinju.domain.member.asst;

import java.util.Date;
import java.util.List;

/**
 * <p>会员子账号关联关系</p>
 * @author gaobaowen
 * @since 2011-12-16 13:30:09
 */
public class MemberAsstRelationDO implements java.io.Serializable {

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
     * 主账号登录名
     */
    private String masterLoginName;

    /**
     * 子账号会员编号
     */
    private Long asstMemberId;

    /**
     * 子账号登录名
     */
    private String asstLoginName;

    /**
     * 子账号描述
     */
    private String asstAcctDesc;
    
    /**
     * 子账号状态
     */
    private Integer status;

	/**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    private Date gmtCreate;

    private Date gmtModified;
    
    private List<MemberAsstMemberRoleDO> memberAsstMemberRoleList;

	public Long getId(){
        return id;
    }

    public Long getMasterMemberId(){
        return masterMemberId;
    }

    public String getMasterLoginName(){
        return masterLoginName;
    }

    public Long getAsstMemberId(){
        return asstMemberId;
    }

    public String getAsstLoginName(){
        return asstLoginName;
    }

    public String getAsstAcctDesc(){
        return asstAcctDesc;
    }

    public Date getLastLoginTime(){
        return lastLoginTime;
    }

    public String getLastLoginIp(){
        return lastLoginIp;
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

    public void setMasterLoginName(String masterLoginName){
        this.masterLoginName = masterLoginName;
    }

    public void setAsstMemberId(Long asstMemberId){
        this.asstMemberId = asstMemberId;
    }

    public void setAsstLoginName(String asstLoginName){
        this.asstLoginName = asstLoginName;
    }

    public void setAsstAcctDesc(String asstAcctDesc){
        this.asstAcctDesc = asstAcctDesc;
    }

    public void setLastLoginTime(Date lastLoginTime){
        this.lastLoginTime = lastLoginTime;
    }

    public void setLastLoginIp(String lastLoginIp){
        this.lastLoginIp = lastLoginIp;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }
    
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<MemberAsstMemberRoleDO> getMemberAsstMemberRoleList() {
		return memberAsstMemberRoleList;
	}

	public void setMemberAsstMemberRoleList(
			List<MemberAsstMemberRoleDO> memberAsstMemberRoleList) {
		this.memberAsstMemberRoleList = memberAsstMemberRoleList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asstAcctDesc == null) ? 0 : asstAcctDesc.hashCode());
		result = prime * result + ((asstLoginName == null) ? 0 : asstLoginName.hashCode());
		result = prime * result + ((asstMemberId == null) ? 0 : asstMemberId.hashCode());
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastLoginIp == null) ? 0 : lastLoginIp.hashCode());
		result = prime * result + ((lastLoginTime == null) ? 0 : lastLoginTime.hashCode());
		result = prime * result + ((masterLoginName == null) ? 0 : masterLoginName.hashCode());
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
		MemberAsstRelationDO other = (MemberAsstRelationDO) obj;
		if (asstAcctDesc == null) {
			if (other.asstAcctDesc != null)
				return false;
		} else if (!asstAcctDesc.equals(other.asstAcctDesc))
			return false;
		if (asstLoginName == null) {
			if (other.asstLoginName != null)
				return false;
		} else if (!asstLoginName.equals(other.asstLoginName))
			return false;
		if (asstMemberId == null) {
			if (other.asstMemberId != null)
				return false;
		} else if (!asstMemberId.equals(other.asstMemberId))
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
		if (lastLoginIp == null) {
			if (other.lastLoginIp != null)
				return false;
		} else if (!lastLoginIp.equals(other.lastLoginIp))
			return false;
		if (lastLoginTime == null) {
			if (other.lastLoginTime != null)
				return false;
		} else if (!lastLoginTime.equals(other.lastLoginTime))
			return false;
		if (masterLoginName == null) {
			if (other.masterLoginName != null)
				return false;
		} else if (!masterLoginName.equals(other.masterLoginName))
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
		return "MemberAsstRelationDO [id=" + id + ", masterMemberId=" + masterMemberId + ", masterLoginName="
				+ masterLoginName + ", asstMemberId=" + asstMemberId + ", asstLoginName=" + asstLoginName
				+ ", asstAcctDesc=" + asstAcctDesc + ", lastLoginTime=" + lastLoginTime + ", lastLoginIp="
				+ lastLoginIp + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + "]";
	}
}

