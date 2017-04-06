package com.yuwang.pinju.core.member.manager.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.cache.CacheFetcher;
import com.yuwang.pinju.core.cache.MemcachedManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.member.dao.MemberAsstDAO;
import com.yuwang.pinju.core.member.dao.MemberDao;
import com.yuwang.pinju.core.member.manager.MemberAsstManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstMemberRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleHisDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleQuery;

public class MemberAsstManagerImpl extends TransactionMemberManager implements MemberAsstManager {

	private final static Log log = LogFactory.getLog(MemberAsstManagerImpl.class);

	private MemberAsstDAO memberAsstDAO;
	private MemberDao memberDAO;
	private MemcachedManager asstPermMemcachedManager;
	private MemberManager memberManager;
	private CacheFetcher asstPermFetch;

	public void setMemberDAO(MemberDao memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setMemberAsstDAO(MemberAsstDAO memberAsstDAO) {
		this.memberAsstDAO = memberAsstDAO;
	}

	public void setAsstPermMemcachedManager(MemcachedManager asstPermMemcachedManager) {
		this.asstPermMemcachedManager = asstPermMemcachedManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setAsstPermFetch(CacheFetcher asstPermFetch) {
		this.asstPermFetch = asstPermFetch;
	}

	@Override
	public int findMemberAsstRelationCount(MemberAsstRelationQuery query) throws ManagerException {
		try {
			return memberAsstDAO.findMemberAsstRelationCount(query);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<MemberAsstRelationDO> findMemberAsstRealation(MemberAsstRelationQuery query) throws ManagerException {
		try {
			List<MemberAsstRelationDO> memberAsstList = memberAsstDAO.findMemberAsstRealation(query);
			if (memberAsstList == null) {
				return memberAsstList;
			}
			for (MemberAsstRelationDO memberAsstRelation : memberAsstList) {
				List<MemberAsstMemberRoleDO> memberAsstMemberRoleList = findMemberAsstMemberRole(memberAsstRelation.getMasterMemberId(), memberAsstRelation.getAsstMemberId());
				if (memberAsstMemberRoleList != null && memberAsstMemberRoleList.size() > 0) {
					memberAsstRelation.setMemberAsstMemberRoleList(memberAsstMemberRoleList);
				}
			}
			return memberAsstList;
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public MemberAsstRoleDO findMemberAsstRoleById(long masterMemberId, long asstRoleId)
			throws ManagerException {
		MemberAsstRoleDO memberAsstRole = new MemberAsstRoleDO();
		memberAsstRole.setMasterMemberId(masterMemberId);
		memberAsstRole.setId(asstRoleId);
		try {
			return memberAsstDAO.findMemberAsstRoleByMasterId(memberAsstRole);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public MemberAsstRelationDO insertMemberAsstRelation(MemberAsstRelationDO memberAsstRelation) throws DaoException {
		try {
			return memberAsstDAO.insertMemberAsstRelation(memberAsstRelation);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public MemberAsstMemberRoleDO insertMemberAsstMemberRole(
			MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException {
		try {
			return memberAsstDAO.insertMemberAsstMemberRole(memberAsstMemberRole);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public MemberAsstRoleDO insertMemberAsstRole(MemberAsstRoleDO memberAsstRole)
			throws DaoException {
		return memberAsstDAO.insertMemberAsstRole(memberAsstRole);
	}

	@Override
	public int findMemberAsstRoleCount(MemberAsstRoleQuery query)
			throws ManagerException {
		try {
			return memberAsstDAO.findMemberAsstRoleCount(query);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<MemberAsstRoleDO> findMemberAsstRole(MemberAsstRoleQuery query)
			throws ManagerException {
		try {
			return memberAsstDAO.findMemberAsstRole(query);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public MemberAsstRelationDO getMemberAsstRation(long masterMemberId, long asstMemberId) throws ManagerException {
		MemberAsstRelationDO memberAsstRelation = new MemberAsstRelationDO();
		memberAsstRelation.setAsstMemberId(asstMemberId);
		memberAsstRelation.setMasterMemberId(masterMemberId);
		try {
			return memberAsstDAO.getMemberAsstRation(memberAsstRelation);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<MemberAsstMemberRoleDO> findMemberAsstMemberRole(
			long masterMemberId, long asstMemberId) throws ManagerException {
		MemberAsstMemberRoleDO memberAsstMemberRole = new MemberAsstMemberRoleDO();
		memberAsstMemberRole.setMasterMemberId(masterMemberId);
		memberAsstMemberRole.setAsstMemberId(asstMemberId);
		try {
			return memberAsstDAO.findMemberAsstMemberRole(memberAsstMemberRole);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<MemberAsstRoleDO> getMemberAsstRole(long masterMemberId) throws ManagerException {
		try {
			return memberAsstDAO.getMemberAsstRole(masterMemberId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int deleteMemberAsstMemberRole(long masterMemberId, long asstMemberId) throws ManagerException {
		MemberAsstMemberRoleDO memberAsstMemberRole = new MemberAsstMemberRoleDO();
		memberAsstMemberRole.setMasterMemberId(masterMemberId);
		memberAsstMemberRole.setAsstMemberId(asstMemberId);
		try {
			return memberAsstDAO.deleteMemberAsstMemberRole(memberAsstMemberRole);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public MemberAsstRelationDO editMemberAsstRelation(
			final MemberAsstRelationDO memberAsstRelation, final List<MemberAsstMemberRoleDO> memberAsstMemberRoles) throws ManagerException {
		try {
			final MemberAsstMemberRoleDO memberAsstMemberRole = new MemberAsstMemberRoleDO(
					memberAsstRelation.getMasterMemberId(),
					memberAsstRelation.getAsstMemberId());
			return executeInTransaction(new TransactionExecutor<MemberAsstRelationDO>(){
				@Override
				public MemberAsstRelationDO execute() throws DaoException{
					memberAsstDAO.deleteMemberAsstMemberRole(memberAsstMemberRole);
					int count = memberAsstDAO.editMemberAsstRelation(memberAsstRelation);
					if (count < 1) {
						return null;
					}
					for (MemberAsstMemberRoleDO memberAsstMemberRole : memberAsstMemberRoles) {
						memberAsstDAO.insertMemberAsstMemberRole(memberAsstMemberRole);
					}
					return memberAsstRelation;
				}
			}, "editMemberAsstRelation", memberAsstRelation);
		} catch (Exception e) {
			log.warn("addMemberSecurityMobile error" + memberAsstRelation.toString(), e);
			return null;
		}
	}

	@Override
	public int updateMemberStatus(final MemberDO member, final MemberAsstRelationDO memberAsstRelation) throws ManagerException {
		try {
			return executeInTransaction(new TransactionExecutor<Integer>(){
				@Override
				public Integer execute() throws DaoException{
					int count1 = memberDAO.updateMemberStatus(member);
					if (count1 < 1) {
						return 0;
					}
					int count2 = memberAsstDAO.updateMemberAsstRelationStatus(memberAsstRelation);
					if (count2 < 1) {
						return 0;
					}
					return 1;
				}
			}, "updateMemberStatus");
		} catch (Exception e) {
			log.error("updateMemberStatus is error, member = " + member + ", memberAsstRelation=" + memberAsstRelation, e);
			return 0; 
		}
	}

	@Override
	public List<MemberAsstPermissionDO> getMemberAsstPermission()
			throws ManagerException {
		try {
			return memberAsstDAO.findAllMemberAsstPermisssion();
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public MemberAsstRoleDO getMemberMasterRoleByRoleName(
			long masterMemberId, String roleName) throws ManagerException {
		MemberAsstRoleDO memberAsstRole = new MemberAsstRoleDO();
		memberAsstRole.setMasterMemberId(masterMemberId);
		memberAsstRole.setRoleName(roleName);
		try {
			return memberAsstDAO.getMemberMasterRoleByRoleName(memberAsstRole);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int updateMemberAsstRole(MemberAsstRoleDO memberAsstRole)
			throws ManagerException {
		try {
			return memberAsstDAO.updateMemberAsstRole(memberAsstRole);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public MemberAsstRoleDO deleteMemberAsstRole(long masterMemberId, long roleId, String remoteIp) throws ManagerException {
		MemberAsstRoleDO memberAsstRole = new MemberAsstRoleDO();
		memberAsstRole.setMasterMemberId(masterMemberId);
		memberAsstRole.setId(roleId);
		final MemberAsstMemberRoleDO memberAsstMemberRole = new MemberAsstMemberRoleDO();
		memberAsstMemberRole.setMasterMemberId(masterMemberId);
		memberAsstMemberRole.setAsstRoleId(roleId);
		
		try {
			final MemberAsstRoleDO memberAsstRoleDO = memberAsstDAO.findMemberAsstRoleByMasterId(memberAsstRole);
			if (memberAsstRoleDO == null) {
				return null;
			}
			final MemberAsstRoleHisDO memberAsstRoleHis = memberAsstRoleDO.createMemberAsstRole();
			memberAsstRoleHis.setOperIp(remoteIp);
			return executeInTransaction(new TransactionExecutor<MemberAsstRoleDO>(){
				@Override
				public MemberAsstRoleDO execute() throws DaoException{
					int count = memberAsstDAO.deleteMemberAsstRole(memberAsstRoleDO);
					if (count < 1) {
						return null;
					}
					memberAsstDAO.deleteMemberAsstMemberRoleByRoleId(memberAsstMemberRole);
					memberAsstDAO.insertMemberAsstRoleHis(memberAsstRoleHis);
					return memberAsstRoleDO;
				}
			}, "deleteMemberAsstRole", memberAsstRole);
		} catch (Exception e) {
			log.warn("deleteMemberAsstRole error, masterMemberId = " + masterMemberId + ", roleId = " + roleId, e);
			return null;
		}
	}

	@Override
	public boolean initAsstPermCache(Long memberId) {
		if (!memberManager.isCorrectMemberId(memberId)) {
			log.warn("");
			return false;
		}
		Object obj = asstPermFetch.fetch(memberId);
		if (obj == null) {
			log.warn("[initAsstPerm] member id: [" + memberId + "] permission string is empty");
			return false;
		}
		return asstPermMemcachedManager.setCacheObject(memberId, obj);
	}

	@Override
	public boolean clearAsstPermCache(Long memberId) {
		return asstPermMemcachedManager.clearCacheObject(memberId);
	}
	
	@Override
	public List<MemberAsstMemberRoleDO> getAsstRoles(Long memberId) throws ManagerException {
		if (!memberManager.isCorrectMemberId(memberId)) {
			log.warn("[getAsstRoles] member id [" + memberId + "] is invalid");
			return null;
		}
		try {
			return memberAsstDAO.getAsstRoles(memberId);
		} catch (DaoException e) {
			String message = "[getAsstRoles] cause exception, member id: [" + memberId + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}
	
	@Override
	public MemberAsstRoleDO findMemberAsstRoleById(Long asstRoleId) throws ManagerException {
		if (asstRoleId == null || asstRoleId < 1) {
			log.warn("[findMemberAsstRoleById] parameter asst role id [" + asstRoleId + "]is null or empty");
			return null;
		}
		try {
			return memberAsstDAO.findMemberAsstRoleById(asstRoleId);
		} catch (DaoException e) {
			String message = "[findMemberAsstRoleById] cause exception, asst role id: [" + asstRoleId + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public MemberAsstRelationDO getMemberAsstRelationByAsst(Long assistantMemberId) throws ManagerException {
		if (!memberManager.isCorrectMemberId(assistantMemberId)) {
			log.warn("[getMemberAsstRelationByAsst] assistant member id [" + assistantMemberId + "] is invalid");
			return null;
		}
		try {
			return memberAsstDAO.getMemberAsstRalationByAsst(assistantMemberId);
		} catch (DaoException e) {
			String message = "[getMemberAsstRelationByAsst] cause exception, assistant member id [" + assistantMemberId + "]";
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public String getAsstPerm(Long asstMemberId) throws ManagerException {
		if (!memberManager.isCorrectMemberId(asstMemberId)) {
			throw new ManagerException("[getAsstPerm] parameter invalid, " + asstMemberId);
		}
		String perms = asstPermMemcachedManager.getCacheObject(asstMemberId, String.class, asstMemberId);

		if (log.isDebugEnabled()) {
			log.debug("[getAsstPerm] current member id: [" + asstMemberId + "] permission data: [" + perms + "]");
		}
		return perms;
	}

	@Override
	public int logMemberAsstLogin(MemberLoginHisDO his) throws ManagerException {
		if (his == null) {
			throw new ManagerException("[logMemberAsstLogin] parameter is null");
		}
		if (!memberManager.isCorrectMemberId(his.getMemberId())) {
			throw new ManagerException("[logMemberAsstLogin] member id [" + his.getMemberId() + "] is incorrect, " + his);
		}
		try {
			return memberAsstDAO.logMemberAsstLogin(his.getMemberId(), his.getLoginTime(), his.getLoginIp());
		} catch (DaoException e) {
			String message = "[logMemberAsstLogin] login record: " + his;
			log.error(message, e);
			throw new ManagerException(message, e);
		}
	}

	@Override
	public void logMeberAsstOperation(MemberAsstOperLogDO log) throws ManagerException {
		if (log == null) {
			throw new ManagerException("[logMeberAsstOperation] parameter is null");
		}
		if (log.hasEmpty()) {
			throw new ManagerException("[logMeberAsstOperation] parameter value is incorrect, " + log);
		}
		try {
			memberAsstDAO.insertMemberAsstOperLog(log);
		} catch (DaoException e) {
			throw new ManagerException("[logMeberAsstOperation] cause exception, " + log, e);
		}
	}

	@Override
	public List<MemberAsstOperLogDO> findMemberAsstOperLog(MemberAsstOperLogQuery query) throws ManagerException {
		try {
			return memberAsstDAO.findMemberAsstOperLog(query);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public int findMemberAsstOperLogCount(MemberAsstOperLogQuery query)
			throws ManagerException {
		try {
			return memberAsstDAO.findMemberAsstOperLogCount(query);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
}
