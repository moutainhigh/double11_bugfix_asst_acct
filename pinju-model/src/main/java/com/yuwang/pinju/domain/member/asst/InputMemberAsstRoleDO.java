package com.yuwang.pinju.domain.member.asst;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class InputMemberAsstRoleDO {

	private long masterMemberId;
	
	private String loginName;

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
    
    private Long id;
    
    private String permissionIds;
    
	public MemberAsstRoleDO createMemberAsstRole() {
    	MemberAsstRoleDO memberAsstRole = new MemberAsstRoleDO();
    	memberAsstRole.setId(id);
    	memberAsstRole.setMasterLoginName(loginName);
    	memberAsstRole.setRoleName(roleName);
    	memberAsstRole.setRoleDesc(roleDesc);
    	memberAsstRole.setMasterMemberId(masterMemberId);
    	memberAsstRole.setPermissions(permissions);
    	return memberAsstRole;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public long getMasterMemberId() {
		return masterMemberId;
	}

	public void setMasterMemberId(long masterMemberId) {
		this.masterMemberId = masterMemberId;
	}

    @NotEmpty(message = "{memberAsstRole.roleName.notempty}")
    @Length(min=1, max=20, message = "{memberAsstRole.roleName.length}")
	@Pattern(regexp = "[\u4e00-\u9faf_0-9a-zA-Z-]+", message = "{memberAsstRole.roleName.pattern}")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	@Override
	public String toString() {
		return "InputMemberAsstRoleDO [roleName=" + roleName + ", roleDesc="
				+ roleDesc + ", permissions=" + permissions + "]";
	}
}
