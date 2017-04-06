package com.yuwang.pinju.core.member.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;
import com.yuwang.pinju.core.filter.FilterResult;
import com.yuwang.pinju.core.filter.manager.FilterManager;
import com.yuwang.pinju.core.member.ao.MemberAsstAO;
import com.yuwang.pinju.core.member.manager.MemberAsstManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.member.manager.PinjuMemberManager;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstPermManager;
import com.yuwang.pinju.core.member.manager.register.AsstRegisterCallback;
import com.yuwang.pinju.core.member.manager.register.RegisterCallback;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberRegisterDO;
import com.yuwang.pinju.domain.member.asst.InputMemberAsstRoleDO;
import com.yuwang.pinju.domain.member.asst.InputMemberRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstMemberRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionTreeVO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRegisterDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleQuery;

public class MemberAsstAOImpl implements MemberAsstAO, MemberResultConstant {

	private final static Log log = LogFactory.getLog(MemberAsstAOImpl.class);

	private MemberAsstManager memberAsstManager;
	private FilterManager loginNameFilterManager;
	private MemberManager memberManager;
	private PinjuMemberManager pinjuMemberManager;
	private MemberAsstPermManager memberAsstPermManager;

	public void setMemberAsstPermManager(MemberAsstPermManager memberAsstPermManager) {
		this.memberAsstPermManager = memberAsstPermManager;
	}

	public void setPinjuMemberManager(PinjuMemberManager pinjuMemberManager) {
		this.pinjuMemberManager = pinjuMemberManager;
	}

	public void setLoginNameFilterManager(FilterManager loginNameFilterManager) {
		this.loginNameFilterManager = loginNameFilterManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setMemberAsstManager(MemberAsstManager memberAsstManager) {
		this.memberAsstManager = memberAsstManager;
	}

	@Override
	public Result findMemberAsstRelation(MemberAsstRelationQuery query) {
		if (query == null) {
			log.error("findMemberAsstRelation, parameter query is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		Result result = null;
		try {
			Integer count = memberAsstManager.findMemberAsstRelationCount(query);
			if (count == null || count == 0) {
				query.setItems(0);
				result = ResultSupportExt.createSuccess();
				return result;
			}
			query.setItems(count);
			List<MemberAsstRelationDO> memberAsstRelationList = memberAsstManager.findMemberAsstRealation(query);
			result = ResultSupportExt.createSuccess();
			result.setModel(memberAsstRelationList);
			return result;
		} catch (Exception e) {
			log.error("findMemberAsstRelation, process error, param: MemberAsstRelationQuery=" + query, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result registerAsstAccount(MemberAsstRegisterDO asstRegister) {
		if (asstRegister == null) {
			log.warn("register member, parameter is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			FilterResult filterResult = loginNameFilterManager.doFilter(asstRegister.getLoginName());
			if (!filterResult.isSuccess()) {
				log.warn("register login name [" + asstRegister.getLoginName() + "] contains invalid words");
				return ResultSupportExt.createError(RESULT_MEMBER_NICKNAME_WORDS_INVALID);
			}

			if (WordFilterFacade.scan(asstRegister.getLoginName(), SensitiveWordConstants.SENSITIVE_WORD_TYPE_MEMBER)) {
				log.warn("register login name [" + asstRegister.getLoginName() + "] contains insensive words");
				return ResultSupportExt.createError(RESULT_INSENSIVE_WORDS);
			}

			// 检查会员名是否存在
			MemberDO member = memberManager.findMemberByNickname(asstRegister.getLoginName());
			if (member != null) {
				log.warn("register member, login name [" + asstRegister.getLoginName() + "] has bean used by member id: "
						+ member.getMemberId());
				return ResultSupportExt.createError(RESULT_MEMBER_NICKNAME_HAS_EXIST);
			}

			Long[] roleIds = asstRegister.getRoleId();
			MemberAsstMemberRoleDO[] memberAsstMemberRoles = null;
			if (roleIds != null && roleIds.length > 0) {
				memberAsstMemberRoles = new MemberAsstMemberRoleDO[roleIds.length];
				for(int i = 0; i < roleIds.length; i++) {
					MemberAsstRoleDO memberAsstRole = memberAsstManager.findMemberAsstRoleById(asstRegister.getMasterMemberId(), roleIds[i]);
					if(memberAsstRole == null) {
						return ResultSupportExt.createError(RESULT_ASST_ACCOUNT_ROLE_NO_EXIST);
					}
					memberAsstMemberRoles[i] = asstRegister.createMemberAsstMemberRole(memberAsstRole);
				}
			}
			MemberAsstRelationDO memberAsstRelation = asstRegister.createMemberAsstRelation();
			MemberRegisterDO memberRegister = asstRegister.createMemberRegister();

			RegisterCallback callback = new AsstRegisterCallback(memberAsstManager, memberAsstRelation, memberAsstMemberRoles);
			return pinjuMemberManager.registerPinjuMember(memberRegister, callback);
		} catch (Exception e) {
			log.error("registerAsstAccount error, parameter: " + asstRegister, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result addAsstRole(InputMemberAsstRoleDO inputMemberAsstRole) {
		if(inputMemberAsstRole == null) {
			log.warn("addAsstRole inputMemberAsstRole, parameter is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			MemberAsstRoleDO memberAsstRoleDO = memberAsstManager.getMemberMasterRoleByRoleName(
							inputMemberAsstRole.getMasterMemberId(), inputMemberAsstRole.getRoleName());
			if (memberAsstRoleDO != null) {
				log.warn("addAsstRole role name is exist");
				return ResultSupportExt.createError(RESULT_ASST_ROLE_EXIST);
			}
			MemberAsstRoleDO memberAsstRole = inputMemberAsstRole.createMemberAsstRole();
			memberAsstRole = memberAsstManager.insertMemberAsstRole(memberAsstRole);
			if (memberAsstRole == null) {
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel(memberAsstRole);
			return result;
		} catch (Exception e) {
			log.error("addAsstRole error, parameter:" + inputMemberAsstRole, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result findMemberAsstRole(MemberAsstRoleQuery query) {
		if (query == null) {
			log.error("findMemberAsstRelation, parameter query is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		Result result = null;
		try {
			Integer count = memberAsstManager.findMemberAsstRoleCount(query);
			if (count == null || count == 0) {
				query.setItems(0);
				result = ResultSupportExt.createSuccess();
				result.setModel(query);
				return result;
			}
			query.setItems(count);
			List<MemberAsstRoleDO> memberAsstRoleList = memberAsstManager.findMemberAsstRole(query);
			result = ResultSupportExt.createSuccess();
			result.setModel(memberAsstRoleList);
			result.setModel(query);
			return result;
		} catch (Exception e) {
			log.error("findMemberAsstRole, process error, param: MemberAsstRoleQuery=" + query, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result getMemberAsstRelationRole(long masterMemberId, long asstMemberId) {
		Result result = null;
		try {
			MemberAsstRelationDO memberAsstRelation = memberAsstManager.getMemberAsstRation(masterMemberId, asstMemberId);
			if (memberAsstRelation == null) {
				log.warn("getMemberAsstRation, memberAsstRelation is null");
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			List<MemberAsstRoleDO> memberAsstRoleList = memberAsstManager.getMemberAsstRole(masterMemberId);
			if (memberAsstRoleList == null || memberAsstRoleList.size() == 0) {
				result = ResultSupportExt.createSuccess();
				result.setModel(memberAsstRelation);
				return result;
			}
			List<MemberAsstMemberRoleDO> memberAsstMemberRoleList = memberAsstManager.findMemberAsstMemberRole(masterMemberId, asstMemberId);
			if (memberAsstMemberRoleList == null || memberAsstMemberRoleList.size() == 0) {
				result = ResultSupportExt.createSuccess();
				result.setModel(memberAsstRelation);
				result.setModel(memberAsstRoleList);
				return result;
			}

			for (MemberAsstRoleDO memberAsstRole : memberAsstRoleList) {
				for (MemberAsstMemberRoleDO memberAsstMemberRole : memberAsstMemberRoleList) {
					String roleName = memberAsstRole.getRoleName();
					if (roleName != null && roleName.equals(memberAsstMemberRole.getAsstRoleName())) {
						memberAsstRole.setChecked(true);
						break;
					}
				}
			}

			result = ResultSupportExt.createSuccess();
			result.setModel(memberAsstRelation);
			result.setModel(memberAsstRoleList);
			return result;
		} catch (Exception e) {
			log.error("getMemberAsstRelationRole, process error, param: masterMemberId=" + masterMemberId + ", asstMemberId = " + asstMemberId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result eidtAsstRelationRole(InputMemberRelationDO inputMemberRelation) {
		try {
			if (inputMemberRelation == null) {
				log.warn("eidtAsstRelationRole inputMemberRelation, parameter is null");
				return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
			}
			long masterMemberId = inputMemberRelation.getMasterMemberId();
			long asstMemberId = inputMemberRelation.getAsstMemberId();
			MemberAsstRelationDO memberAsstRelation = memberAsstManager.getMemberAsstRation(masterMemberId, asstMemberId);
			if (memberAsstRelation == null) {
				log.warn("getMemberAsstRation, memberAsstRelation is null");
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			memberAsstRelation.setAsstAcctDesc(inputMemberRelation.getAsstAcctDesc());
			Long[] roleIds = inputMemberRelation.getRoleId();
			List<MemberAsstMemberRoleDO> memberAsstMemberRoles = new ArrayList<MemberAsstMemberRoleDO>();
			if (roleIds != null) {
				for(int i = 0; i < roleIds.length; i++) {
					MemberAsstRoleDO memberAsstRole = memberAsstManager.findMemberAsstRoleById(masterMemberId, roleIds[i]);
					if(memberAsstRole == null) {
						return ResultSupportExt.createError(RESULT_ASST_ACCOUNT_ROLE_NO_EXIST);
					}
					memberAsstMemberRoles.add(inputMemberRelation.createMemberAsstMemberRole(memberAsstRole));
				}
			}
			memberAsstRelation = memberAsstManager.editMemberAsstRelation(memberAsstRelation, memberAsstMemberRoles);
			if (memberAsstRelation == null) {
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel(memberAsstMemberRoles);
			return result;
		} catch (ManagerException e) {
			log.error("getMemberAsstRelationRole, process error, param: inputMemberRelation=" + inputMemberRelation, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result setMemberIsvalid(long masterMemberId, long asstMemberId, int status) {
		if (status != 0 && status != 2) {
			log.error("setMemberIsvalid, status error, status = " + status);
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		status = (status == 0)?2:0;
		MemberDO member = new MemberDO();
		member.setMemberId(asstMemberId);
		member.setStatus(status);
		MemberAsstRelationDO memberAsstRelation = new MemberAsstRelationDO();
		memberAsstRelation.setMasterMemberId(masterMemberId);
		memberAsstRelation.setStatus(status);
		memberAsstRelation.setAsstMemberId(asstMemberId);
		memberAsstRelation.setGmtModified(new Date());
		try {
			int count = memberAsstManager.updateMemberStatus(member, memberAsstRelation);
			if (count < 1) {
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			return ResultSupportExt.createSuccess();
		} catch (ManagerException e) {
			log.error("setMemberIsvalid, process error, param: memberId=" + asstMemberId + ", status = " + status, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result getMemberMasterRole(long masterMemberId) {
		if (masterMemberId == 0) {
			log.error("getMemberMasterRole, masterMemberId = " + masterMemberId);
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			List<MemberAsstRoleDO> memberAsstRoleList = memberAsstManager.getMemberAsstRole(masterMemberId);
			Result result = ResultSupportExt.createSuccess();
			result.setModel(memberAsstRoleList);
			return result;
		} catch (Exception e) {
			log.error("getMemberMasterRole, masterMemberId=" + masterMemberId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public String getAsstRolePermission() {
		try {
			return memberAsstPermManager.getJsonPermission();
		} catch (Exception e) {
			log.error("getAsstRolePermission error", e);
			return null;
		}
	}

	@Override
	public Result getMemberMasterRoleByRoleName(long masterMemberId, String roleName) {
		try {
			MemberAsstRoleDO memberAsstRoleDO = memberAsstManager.getMemberMasterRoleByRoleName(masterMemberId, roleName);
			Result result = ResultSupportExt.createSuccess();
			result.setModel(memberAsstRoleDO);
			return result;
		} catch (Exception e) {
			log.error("getMemberMasterRoleByRoleName, masterMemberId=" + masterMemberId + ", roleName = " + roleName, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result getMemberAsstRoleById(long masterMemberId, long roleId) {
		if (masterMemberId == 0 || roleId == 0) {
			log.error("getMemberMasterRole, masterMemberId = " + masterMemberId + ", roleId = " + roleId);
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			MemberAsstRoleDO memberAsstRole = memberAsstManager.findMemberAsstRoleById(masterMemberId, roleId);
			if (memberAsstRole == null) {
				log.warn("findMemberAsstRoleById memberAsstRole is null");
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}

			StringBuffer idsBbuffer = new StringBuffer();
			String permissions = memberAsstRole.getPermissions();
			if (!StringUtil.isBlank(permissions)) {
				String[] targetAction = permissions.split(";");
				List<MemberAsstPermissionDO> memberAsstPermissionList = memberAsstManager.getMemberAsstPermission();
				for (MemberAsstPermissionDO memberAsstPermission : memberAsstPermissionList) {
					for (int i = 0; i < targetAction.length; i++) {
						String[] targetActionSplit = targetAction[i].split(":");
						if (targetActionSplit[0] != null && targetActionSplit[0].equals(memberAsstPermission.getTarget())
								&& targetActionSplit[1] != null && targetActionSplit[1].equals(memberAsstPermission.getAction())) {
							idsBbuffer.append(memberAsstPermission.getId()).append(",");
						} 
					}
				}
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel(memberAsstRole);
			result.setModel(getAsstRolePermission());
			result.setModel("PIDS", idsBbuffer.toString());
			return result;
		} catch (Exception e) {
			log.error("getMemberAsstRoleById, masterMemberId=" + masterMemberId + ", roleId = " + roleId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result eidtAsstRole(InputMemberAsstRoleDO inputMemberAsstRole) {
		if(inputMemberAsstRole == null) {
			log.warn("eidtAsstRole inputMemberAsstRole, parameter is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		MemberAsstRoleDO memberAsstRole = inputMemberAsstRole.createMemberAsstRole();
		try {
			int count = memberAsstManager.updateMemberAsstRole(memberAsstRole);
			if (count < 1) {
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			return ResultSupportExt.createSuccess();
		} catch (ManagerException e) {
			log.error("eidtAsstRole, inputMemberAsstRole=" + inputMemberAsstRole, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result deleteMemberAsstRole(long masterMemberId, long roleId, String remoteIp) {
		if (masterMemberId == 0 || roleId == 0) {
			log.error("getMemberMasterRole, masterMemberId = " + masterMemberId + ", roleId = " + roleId);
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			MemberAsstRoleDO memberAsstRole = memberAsstManager.deleteMemberAsstRole(masterMemberId, roleId, remoteIp);
			if (memberAsstRole == null) {
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
			return ResultSupportExt.createSuccess();
		} catch (ManagerException e) {
			log.error("eidtAsstRole, masterMemberId=" + masterMemberId + ", roleId =" + roleId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public String getAsstPerm(Long asstMemberId) {
		if (!memberManager.isCorrectMemberId(asstMemberId)) {
			log.warn("[hasAsstPerm] assistant member id is invalid, [" + asstMemberId + "]");
			return null;
		}
		if (log.isDebugEnabled()) {
			log.debug("[hasAsstPerm] assistant member id: [" + asstMemberId + "]");
		}
		try {
			return memberAsstManager.getAsstPerm(asstMemberId);
		} catch (Exception e) {
			log.error("[hasAsstPerm] cause exception, " + asstMemberId, e);
			return null;
		}
	}

	public Long getMasterMemberId(long asstMemberId) {
		return null;
	}

	@Override
	public boolean clearAsstPermCache(long memberId) {
		try {
			return memberAsstManager.clearAsstPermCache(memberId);
		} catch (Exception e) {
			log.error("[clearAsstPermCache] cause exception, member id: [" + memberId + "]", e);
			return false;
		}
	}

	@Override
	public Result findMemberAsstOperLog(MemberAsstOperLogQuery query) {
		if (query == null) {
			log.error("findMemberAsstRelation, parameter query is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		Result result = null;
		List<MemberAsstPermissionTreeVO> treeVoList = memberAsstPermManager.getMemberAsstPermission();
		try {
			Integer count = memberAsstManager.findMemberAsstOperLogCount(query);
			if (count == null || count == 0) {
				query.setItems(0);
				result = ResultSupportExt.createSuccess();
				result.setModel("TREE_PER_LIST", treeVoList);
				result.setModel(query);
				return result;
			}
			query.setItems(count);
			List<MemberAsstOperLogDO> memberAsstOperLogList = memberAsstManager.findMemberAsstOperLog(query);
			result = ResultSupportExt.createSuccess();
			result.setModel(memberAsstOperLogList);
			result.setModel("TREE_PER_LIST", treeVoList);
			return result;
		} catch (Exception e) {
			log.error("findMemberAsstRole, process error, param: MemberAsstRoleQuery=" + query, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}
}
