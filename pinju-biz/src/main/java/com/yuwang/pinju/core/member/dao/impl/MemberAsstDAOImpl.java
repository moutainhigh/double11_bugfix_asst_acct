package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;
import java.util.List;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.member.dao.MemberAsstDAO;
import com.yuwang.pinju.domain.member.asst.MemberAsstMemberRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleHisDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleQuery;

public class MemberAsstDAOImpl extends BaseDAO implements MemberAsstDAO {

	private ReadBaseDAO readBaseDAOForMySql;

	public void setReadBaseDAOForMySql(ReadBaseDAO readBaseDAOForMySql) {
		this.readBaseDAOForMySql = readBaseDAOForMySql;
	}

	/**
	 * 命名空间：会员子账号与角色对应关系
	 */
	private final static String NAMESPACE_ASST_MEMBER_ROLE = "member_asst_member_role.";

	/**
	 * 命名空间：会员子账号操作日志
	 */
	private final static String NAMESPACE_ASST_OPER_LOG = "member_asst_oper_log.";

	/**
	 * 命名空间：会员子账号权限
	 */
	private final static String NAMESPACE_ASST_PERMISSION = "member_asst_permission.";

	/**
	 * 命名空间：会员子账号关联关系
	 */
	private final static String NAMESPACE_ASST_RELATION = "member_asst_relation.";

	/**
	 * 命名空间：被删除的会员子账号角色历史记录
	 */
	private final static String NAMESPACE_ASST_ROLE_HIS = "member_asst_role_his.";

	/**
	 * 命名空间：会员子账号角色
	 */
	private final static String NAMESPACE_ASST_ROLE = "member_asst_role.";

	@Override
	public MemberAsstRoleDO insertMemberAsstRole(MemberAsstRoleDO memberAsstRole) throws DaoException {
		Date curr = DateUtil.current();
		memberAsstRole.setGmtCreate(curr);
		memberAsstRole.setGmtModified(curr);
		Long asstRoleId = (Long)executeInsert(NAMESPACE_ASST_ROLE + "insertMemberAsstRole", memberAsstRole);
		if (asstRoleId == null) {
			return null;
		}
		memberAsstRole.setId(asstRoleId);
		return memberAsstRole;
	}

	@Override
	public int deleteMemberAsstRole(MemberAsstRoleDO memberAsstRole) throws DaoException {
		return executeUpdate(NAMESPACE_ASST_ROLE + "deleteMemberAsstRoleByMasterMemberIdRole", memberAsstRole);
	}

	@Override
	public MemberAsstMemberRoleDO insertMemberAsstMemberRole(
			MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException {
		Date curr = DateUtil.current();
		memberAsstMemberRole.setGmtCreate(curr);
		memberAsstMemberRole.setGmtModified(curr);
		Long id = (Long)super.executeInsert(NAMESPACE_ASST_MEMBER_ROLE + "insertMemberAsstMemberRole", memberAsstMemberRole);
		memberAsstMemberRole.setId(id);
		return memberAsstMemberRole;
	}

	@Override
	public int deleteMemberAsstMemberRole(MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException {
		return executeUpdate(NAMESPACE_ASST_MEMBER_ROLE + "deleteMemberAsstMemberRoleByRole", memberAsstMemberRole);
	}

	@Override
	public MemberAsstRelationDO insertMemberAsstRelation(
			MemberAsstRelationDO memberAsstRelation) throws DaoException {
		Date curr = DateUtil.current();
		memberAsstRelation.setGmtCreate(curr);
		memberAsstRelation.setGmtModified(curr);
		Long id = (Long)super.executeInsert(NAMESPACE_ASST_RELATION + "insertMemberAsstRelation", memberAsstRelation);
		if (id == null) {
			return null;
		}
		memberAsstRelation.setId(id);
		return memberAsstRelation;
	}

	@Override
	public int deleteMemberAsstRelation(MemberAsstRelationDO memberAsstRelation) throws DaoException {
		return executeUpdate(NAMESPACE_ASST_RELATION + "deleteMemberAsstRelation", memberAsstRelation);
	}

	@Override
	public MemberAsstRoleHisDO insertMemberAsstRoleHis(MemberAsstRoleHisDO memberAsstRoleHis) throws DaoException {
		Date curr = DateUtil.current();
		memberAsstRoleHis.setGmtCreate(curr);
		memberAsstRoleHis.setGmtModified(curr);
		Long id = (Long)super.executeInsert(NAMESPACE_ASST_ROLE_HIS + "insertMemberAsstRoleHis", memberAsstRoleHis);
		memberAsstRoleHis.setId(id);
		return memberAsstRoleHis;
	}

	@Override
	public MemberAsstOperLogDO insertMemberAsstOperLog(MemberAsstOperLogDO memberAsstOper) throws DaoException {
		Date curr = DateUtil.current();
		memberAsstOper.setGmtCreate(curr);
		memberAsstOper.setGmtModified(curr);
		Long id = (Long)super.executeInsert(NAMESPACE_ASST_OPER_LOG + "insertMemberAsstOperLog", memberAsstOper);
		if (id == null) {
			return null;
		}
		memberAsstOper.setId(id);
		return memberAsstOper;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAsstPermissionDO> findAllMemberAsstPermisssion() throws DaoException {
		return executeQueryForList(NAMESPACE_ASST_PERMISSION + "selectAllMemberAsstPermission", null);
	}

	@Override
	public int findMemberAsstRelationCount(MemberAsstRelationQuery query) throws DaoException {
		return (Integer)readBaseDAOForMySql.executeQueryForObject(NAMESPACE_ASST_RELATION + "selectMemberAsstRelationCount", query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAsstRelationDO> findMemberAsstRealation(MemberAsstRelationQuery query) throws DaoException {
		return readBaseDAOForMySql.executeQueryForList(NAMESPACE_ASST_RELATION + "selectMemberAsstRelation", query);
	}

	@Override
	public int findMemberAsstRoleCount(MemberAsstRoleQuery query) throws DaoException {
		return (Integer) readBaseDAOForMySql.executeQueryForObject(NAMESPACE_ASST_ROLE + "selectMemberAsstRoleCount", query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAsstRoleDO> findMemberAsstRole(MemberAsstRoleQuery query) throws DaoException {
		return readBaseDAOForMySql.executeQueryForList(NAMESPACE_ASST_ROLE + "selectMemberAsstRole", query);
	}

	@Override
	public MemberAsstRelationDO getMemberAsstRation(MemberAsstRelationDO memberAsstRelation) throws DaoException {
		return (MemberAsstRelationDO) executeQueryForObject(NAMESPACE_ASST_RELATION + "getMemberRalation", memberAsstRelation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAsstMemberRoleDO> findMemberAsstMemberRole(
			MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException {
		return executeQueryForList(NAMESPACE_ASST_MEMBER_ROLE + "selectMemberAsstMemberRole", memberAsstMemberRole);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAsstRoleDO> getMemberAsstRole(long masterMemberId) throws DaoException {
		return executeQueryForList(NAMESPACE_ASST_ROLE + "getMemberAsstRole", masterMemberId);
	}

	@Override
	public int editMemberAsstRelation(MemberAsstRelationDO memberAsstRelation)
			throws DaoException {
		memberAsstRelation.setGmtModified(new Date());
		return (Integer) executeUpdate(NAMESPACE_ASST_RELATION + "editMemberAsstRelation", memberAsstRelation);
	}

	@Override
	public MemberAsstRoleDO getMemberMasterRoleByRoleName(MemberAsstRoleDO memberAsstRole) throws DaoException {
		return (MemberAsstRoleDO) executeQueryForObject(NAMESPACE_ASST_ROLE + "getMemberAsstRoleByMastIdName", memberAsstRole);
	}

	@Override
	public int updateMemberAsstRole(MemberAsstRoleDO memberAsstRole)
			throws DaoException {
		memberAsstRole.setGmtModified(new Date());
		return executeUpdate(NAMESPACE_ASST_ROLE + "updateMemberAsstRole", memberAsstRole);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAsstMemberRoleDO> getAsstRoles(long asstMemberId) throws DaoException {
		return (List<MemberAsstMemberRoleDO>)readBaseDAOForMySql.executeQueryForList(NAMESPACE_ASST_MEMBER_ROLE + "getAsstRoles", asstMemberId);
	}

	@Override
	public int logMemberAsstLogin(long memberId, Date loginTime, String loginIp) throws DaoException {
		MemberAsstRelationDO mar = new MemberAsstRelationDO();
		mar.setAsstMemberId(memberId);
		mar.setLastLoginTime(loginTime);
		mar.setLastLoginIp(loginIp);
		mar.setGmtModified(DateUtil.current());
		return executeUpdate(NAMESPACE_ASST_RELATION + "logMemberAsstLogin", mar);
	}

	@Override
	public MemberAsstRelationDO getMemberAsstRalationByAsst(long memberId) throws DaoException {
		return (MemberAsstRelationDO)readBaseDAOForMySql.executeQueryForObject(NAMESPACE_ASST_RELATION + "getMemberAsstRalationByAsst", memberId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAsstPermissionDO> getValidAsstPermissions() throws DaoException {
		return (List<MemberAsstPermissionDO>)readBaseDAOForMySql.executeQueryForList(NAMESPACE_ASST_PERMISSION + "getValidAsstPermissions", null);
	}

	@Override
	public MemberAsstRoleDO findMemberAsstRoleById(long roleId)
			throws DaoException {
		return (MemberAsstRoleDO) executeQueryForObject(NAMESPACE_ASST_ROLE + "selectMemberAsstRoleById", roleId);
	}

	@Override
	public MemberAsstRoleDO findMemberAsstRoleByMasterId(MemberAsstRoleDO memberAsstRole) throws DaoException {
		return (MemberAsstRoleDO) executeQueryForObject(NAMESPACE_ASST_ROLE + "selectMemberAsstRoleByMasterId", memberAsstRole);
	}

	@Override
	public int updateMemberAsstRelationStatus(MemberAsstRelationDO memberAsstRelation) throws DaoException {
		return (Integer) executeUpdate(NAMESPACE_ASST_RELATION + "editMemberAsstRelationStatus", memberAsstRelation);
	}

	@Override
	public int findMemberAsstOperLogCount(MemberAsstOperLogQuery query)
			throws DaoException {
		return (Integer) readBaseDAOForMySql.executeQueryForObject(NAMESPACE_ASST_OPER_LOG + "selectMemberAsstOperLogCount", query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberAsstOperLogDO> findMemberAsstOperLog(MemberAsstOperLogQuery query) throws DaoException {
		return (List<MemberAsstOperLogDO>) readBaseDAOForMySql.executeQueryForList(NAMESPACE_ASST_OPER_LOG + "selectMemberAsstOperLog", query);
	}

	@Override
	public int deleteMemberAsstMemberRoleByRoleId(MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException {
		return executeUpdate(NAMESPACE_ASST_MEMBER_ROLE + "deleteMemberAsstMemberRoleByRoleId", memberAsstMemberRole);
	}
}
