package com.yuwang.pinju.domain.member.asst;

import java.util.Date;

/**
 * <p>被删除的会员子账号角色历史记录</p>
 * @author gaobaowen
 * @since 2011-12-16 14:27:40
 */
public class MemberAsstRoleHisDO implements java.io.Serializable {

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
     * 主账号会员账户名
     */
    private String masterLoginName;

    /**
     * 子账号角色名称
     */
    private String roleName;

    /**
     * 子账号角色说明
     */
    private String roleDesc;

    /**
     * 角色所拥有的权限字符串，格式：TARGET：ACTION{；TARGET：ACTION}
     */
    private String permissions;

    /**
     * 操作者会员名
     */
    private Long operMemberId;

    /**
     * 操作者IP地址
     */
    private String operIp;


    private Date gmtCreate;


    private Date gmtModified;


    public Long getId(){
        return id;
    }

    public Long getMasterMemberId(){
        return masterMemberId;
    }

    public String getMasterLoginName(){
        return masterLoginName;
    }

    public String getRoleName(){
        return roleName;
    }

    public String getRoleDesc(){
        return roleDesc;
    }

    public String getPermissions(){
        return permissions;
    }

    public Long getOperMemberId(){
        return operMemberId;
    }

    public String getOperIp(){
        return operIp;
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

    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    public void setRoleDesc(String roleDesc){
        this.roleDesc = roleDesc;
    }

    public void setPermissions(String permissions){
        this.permissions = permissions;
    }

    public void setOperMemberId(Long operMemberId){
        this.operMemberId = operMemberId;
    }

    public void setOperIp(String operIp){
        this.operIp = operIp;
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
		result = prime * result + ((gmtCreate == null) ? 0 : gmtCreate.hashCode());
		result = prime * result + ((gmtModified == null) ? 0 : gmtModified.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((masterLoginName == null) ? 0 : masterLoginName.hashCode());
		result = prime * result + ((masterMemberId == null) ? 0 : masterMemberId.hashCode());
		result = prime * result + ((operIp == null) ? 0 : operIp.hashCode());
		result = prime * result + ((operMemberId == null) ? 0 : operMemberId.hashCode());
		result = prime * result + ((permissions == null) ? 0 : permissions.hashCode());
		result = prime * result + ((roleDesc == null) ? 0 : roleDesc.hashCode());
		result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
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
		MemberAsstRoleHisDO other = (MemberAsstRoleHisDO) obj;
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
		if (operIp == null) {
			if (other.operIp != null)
				return false;
		} else if (!operIp.equals(other.operIp))
			return false;
		if (operMemberId == null) {
			if (other.operMemberId != null)
				return false;
		} else if (!operMemberId.equals(other.operMemberId))
			return false;
		if (permissions == null) {
			if (other.permissions != null)
				return false;
		} else if (!permissions.equals(other.permissions))
			return false;
		if (roleDesc == null) {
			if (other.roleDesc != null)
				return false;
		} else if (!roleDesc.equals(other.roleDesc))
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MemberAsstRoleHisDO [id=" + id + ", masterMemberId=" + masterMemberId + ", masterLoginName="
				+ masterLoginName + ", roleName=" + roleName + ", roleDesc=" + roleDesc + ", permissions="
				+ permissions + ", operMemberId=" + operMemberId + ", operIp=" + operIp + ", gmtCreate=" + gmtCreate
				+ ", gmtModified=" + gmtModified + "]";
	}
}

