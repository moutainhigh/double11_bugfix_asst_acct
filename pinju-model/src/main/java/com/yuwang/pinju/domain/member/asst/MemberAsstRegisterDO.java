package com.yuwang.pinju.domain.member.asst;

import java.util.Arrays;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.yuwang.pinju.common.NumberUtil;
import com.yuwang.pinju.domain.annotation.SecurityTransmission;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberRegisterDO;
import com.yuwang.pinju.validation.annotation.MemberName;

public class MemberAsstRegisterDO {

	private long masterMemberId;
	private String sid;
	private String masterLoginName;
	private String loginName;
	private String password;
	private String confirm;
	private String tid;
	private String registerIp;

	private Long[] roleId;
	private String[] inputRoleId;

	private String asstAcctDesc;
	
	public boolean validateInputRole() {
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
	

	public MemberAsstRelationDO createMemberAsstRelation() {
		MemberAsstRelationDO memberAsstRelation = new MemberAsstRelationDO();
		memberAsstRelation.setAsstLoginName(loginName);
		memberAsstRelation.setMasterLoginName(masterLoginName);
		memberAsstRelation.setAsstAcctDesc(asstAcctDesc);
		memberAsstRelation.setMasterMemberId(masterMemberId);
		return memberAsstRelation;
	}

	public MemberAsstMemberRoleDO createMemberAsstMemberRole(MemberAsstRoleDO memberAsstRole) {
		MemberAsstMemberRoleDO memberAsstMemberRole = new MemberAsstMemberRoleDO();
		memberAsstMemberRole.setAsstRoleId(memberAsstRole.getId());
		memberAsstMemberRole.setAsstRoleName(memberAsstRole.getRoleName());
		memberAsstMemberRole.setMasterMemberId(masterMemberId);
		return memberAsstMemberRole;
	}

	public MemberRegisterDO createMemberRegister() {
		MemberRegisterDO register = MemberRegisterDO.newPinjuRegister();
		register.setLoginName(loginName);
		register.setPassword(password);
		register.setConfirm(confirm);
		register.setRegisterIp(registerIp);
		register.setAccoutType(MemberDO.ACCOUNT_TYPE_ASSISTANT);
    	return register;
	}

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	@MemberName
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@SecurityTransmission
	@NotEmpty(message = "{memberRegister.password.notempty}")
	@Length(min = 6, max = 16, message = "{memberRegister.password.length}")
	@Pattern(regexp = "[\\x21-\\x7e]+", message = "{memberRegister.password.pattern}")
	@Pattern.List({
		@Pattern(regexp = "[\\x21-\\x7e]+", message = "{memberRegister.password.pattern}"),
		@Pattern(regexp = "(?!^[0-9]+$).*", message = "{memberRegister.password.number}"),
		@Pattern(regexp = "(?!^[a-zA-Z]+$).*", message = "{memberRegister.password.letter}")
	})
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@SecurityTransmission
	@NotEmpty(message = "{memberRegister.confirm.notempty}")
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
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
	public String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}

	public long getMasterMemberId() {
		return masterMemberId;
	}

	public void setMasterMemberId(long masterMemberId) {
		this.masterMemberId = masterMemberId;
	}
	
	public String getMasterLoginName() {
		return masterLoginName;
	}

	public void setMasterLoginName(String masterLoginName) {
		this.masterLoginName = masterLoginName;
	}
	
	public String[] getInputRoleId() {
		return inputRoleId;
	}

	public void setInputRoleId(String[] inputRoleId) {
		this.inputRoleId = inputRoleId;
	}
	
	@Override
	public String toString() {
		return "MemberAsstRegisterDO [sid=" + sid + ", loginName=" + loginName
				+ ", password=" + password + ", confirm=" + confirm + ", tid="
				+ tid + ", roleId=" + Arrays.toString(roleId)
				+ ", asstAcctDesc=" + asstAcctDesc + "]";
	}
}
