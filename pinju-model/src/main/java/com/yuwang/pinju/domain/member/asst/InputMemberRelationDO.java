package com.yuwang.pinju.domain.member.asst;

import java.util.Arrays;

import com.yuwang.pinju.common.NumberUtil;

public class InputMemberRelationDO {

	private Long[] roleId;
	private String[] inputRoleId;
	
	private String asstAcctDesc;
	private String inputAsstMemberId;
	private Long asstMemberId;
	private Long masterMemberId;
	private String loginName;
	
	public MemberAsstRelationDO createMemberAsstRelation() {
		MemberAsstRelationDO memberAsstRelation = new MemberAsstRelationDO();
		memberAsstRelation.setAsstAcctDesc(asstAcctDesc);
		memberAsstRelation.setAsstMemberId(asstMemberId);
		memberAsstRelation.setMasterMemberId(masterMemberId);
		return memberAsstRelation;
	}
	
	public MemberAsstMemberRoleDO createMemberAsstMemberRole(MemberAsstRoleDO memberAsstRole) {
		MemberAsstMemberRoleDO memberAsstMemberRole = new MemberAsstMemberRoleDO();
		memberAsstMemberRole.setAsstMemberId(asstMemberId);
		memberAsstMemberRole.setMasterMemberId(masterMemberId);
		memberAsstMemberRole.setAsstRoleId(memberAsstRole.getId());
		memberAsstMemberRole.setAsstRoleName(memberAsstRole.getRoleName());
		return memberAsstMemberRole;
	}
	
	public boolean validateAsstMemberId(long dAsstMemberId) {
		if (dAsstMemberId == -1) {
			return false;
		}
		asstMemberId = dAsstMemberId;
		if (inputRoleId != null && inputRoleId.length > 0) {
			roleId = new Long[inputRoleId.length];
			for (int i = 0; i < inputRoleId.length; i++) {
				if (!NumberUtil.isLong(inputRoleId[i])) {
					return false;
				}
				roleId[i] = Long.parseLong(inputRoleId[i]);
			}
		} 
		return true;
	}
	
	public Long getMasterMemberId() {
		return masterMemberId;
	}
	public void setMasterMemberId(Long masterMemberId) {
		this.masterMemberId = masterMemberId;
	}
	public Long getAsstMemberId() {
		return asstMemberId;
	}
	public void setAsstMemberId(Long asstMemberId) {
		this.asstMemberId = asstMemberId;
	}
	public Long[] getRoleId() {
		return roleId;
	}
	public void setRoleId(Long[] roleId) {
		this.roleId = roleId;
	}
	public String getAsstAcctDesc() {
		return asstAcctDesc;
	}
	public void setAsstAcctDesc(String asstAcctDesc) {
		this.asstAcctDesc = asstAcctDesc;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getInputAsstMemberId() {
		return inputAsstMemberId;
	}
	public void setInputAsstMemberId(String inputAsstMemberId) {
		this.inputAsstMemberId = inputAsstMemberId;
	}

	public String[] getInputRoleId() {
		return inputRoleId;
	}

	public void setInputRoleId(String[] inputRoleId) {
		this.inputRoleId = inputRoleId;
	}

	@Override
	public String toString() {
		return "InputMemberRelationDO [roleId=" + Arrays.toString(roleId)
				+ ", inputRoleId=" + Arrays.toString(inputRoleId)
				+ ", asstAcctDesc=" + asstAcctDesc + ", inputAsstMemberId="
				+ inputAsstMemberId + ", asstMemberId=" + asstMemberId
				+ ", masterMemberId=" + masterMemberId + ", loginName="
				+ loginName + "]";
	}
}
