package com.yuwang.pinju.core.member.dao;

import java.util.Date;
import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.member.asst.MemberAsstMemberRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleHisDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleQuery;

/**
 * <p>会员子账号数据库操作</p>
 *
 * @author gaobaowen
 * @since 2011-12-16 14:37:14
 */
public interface MemberAsstDAO {

	/**
	 * <p>新增子账号角色信息</p>
	 * @param memberAsstRole
	 * @return Long 子账号角色ID
	 * @throws DaoException
	 */
	MemberAsstRoleDO insertMemberAsstRole(MemberAsstRoleDO memberAsstRole) throws DaoException;

	/**
	 * <p>删除子账号角色信息</p>
	 *
	 * @param masterMemberId 主账号会员编号
	 * @return int 0=false, 1=true
	 * @throws DaoException
	 */
	int deleteMemberAsstRole(MemberAsstRoleDO memberAsstRole) throws DaoException;

	/**
	 * <p>新增会员子账号与角色对应关系</p>
	 * @param memberAsstMemberRole
	 * @return MemberAsstMemberRoleDO
	 * @throws DaoException
	 */
	MemberAsstMemberRoleDO insertMemberAsstMemberRole(MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException;

	/**
	 * <p>删除会员子账号与角色对应关系</p>
	 * @param memberAsstMemberRole[masterMemberId, asstMemberId]
	 * @return int 0=false, 1=true
	 * @throws DaoException
	 */
	int deleteMemberAsstMemberRole(MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException;
	
	/**
	 * <p>删除会员子账号与角色对应关系</p>
	 * @param memberAsstMemberRole[masterMemberId, asstRoleId]
	 * @return
	 * @throws DaoException
	 */
	int deleteMemberAsstMemberRoleByRoleId(MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException;

	/**
	 * <p>新增会员子账号关联关系</p>
	 * @param memberAsstRelation
	 * @return
	 * @throws DaoException
	 */
	MemberAsstRelationDO insertMemberAsstRelation(MemberAsstRelationDO memberAsstRelation) throws DaoException;

	/**
	 * <p>查询子账号信息</p>
	 *
	 * @param memberAsstRelation[masterMemberId, asstMemberId]
	 * @return
	 * @throws DaoException
	 */
	MemberAsstRelationDO getMemberAsstRation(MemberAsstRelationDO memberAsstRelation) throws DaoException;

	/**
	 * <p>编辑子账号信息</p>
	 *
	 * @param memberAsstRelation
	 * @return
	 * @throws DaoException
	 */
	int editMemberAsstRelation(MemberAsstRelationDO memberAsstRelation) throws DaoException;

	/**
	 * <p>删除会员子账号关联关系</p>
	 * @param memberAsstRelation[masterMemberId, asstMemberId]
	 * @return int 0=false, 1=true
	 * @throws DaoException
	 */
	int deleteMemberAsstRelation(MemberAsstRelationDO memberAsstRelation) throws DaoException;

	/**
	 * <p>新增子账号角色记录信息</p>
	 * @param memberAsstRoleHis
	 * @return
	 * @throws DaoException
	 */
	MemberAsstRoleHisDO insertMemberAsstRoleHis(MemberAsstRoleHisDO memberAsstRoleHis) throws DaoException;

	/**
	 * <p>新增子账号操作日志</p>
	 *
	 * @return 成功=MemberAsstOperLogDO, 不成功 =null
	 * @throws DaoException
	 */
	public MemberAsstOperLogDO insertMemberAsstOperLog(MemberAsstOperLogDO memberAsstOper) throws DaoException;
	
	/**
	 * <p>查询子账号日志数</p>
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	int findMemberAsstOperLogCount(MemberAsstOperLogQuery query) throws DaoException;
	
	/**
	 * <p>查询子账号操作日志信息</p>
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	List<MemberAsstOperLogDO> findMemberAsstOperLog(MemberAsstOperLogQuery query) throws DaoException;

	/**
	 * <p>查询所有权限列表</p>
	 *
	 * @return List<MemberAsstPermissionDO>
	 * @throws DaoException
	 */
	List<MemberAsstPermissionDO> findAllMemberAsstPermisssion() throws DaoException;

	/**
	 * <p>查询子账号总数</p>
	 * @param masterMemberId 主账号会员编号
	 * @return int
	 * @throws DaoException
	 */
	int findMemberAsstRelationCount(MemberAsstRelationQuery query) throws DaoException;

	/**
	 * <P><P>
	 * @param masterMemberId
	 * @return
	 * @throws DaoException
	 */
	List<MemberAsstRelationDO> findMemberAsstRealation(MemberAsstRelationQuery query) throws DaoException;

	/**
	 * <p>查询角色信息</p>
	 * @param asstRoleId 角色编号
	 * @return
	 * @throws DaoException
	 */
	MemberAsstRoleDO findMemberAsstRoleById(long roleId) throws DaoException;
	
	/**
	 * <p>查询角色信息</p>
	 * @param asstRoleId 角色编号
	 * @return
	 * @throws DaoException
	 */
	MemberAsstRoleDO findMemberAsstRoleByMasterId(MemberAsstRoleDO memberAsstRole) throws DaoException;

	/**
	 * <p>查询子账号角色数</p>
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	int findMemberAsstRoleCount(MemberAsstRoleQuery query) throws DaoException;

	/**
	 * <p>查询子账号信息</p>
	 *
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	List<MemberAsstRoleDO> findMemberAsstRole(MemberAsstRoleQuery query) throws DaoException;

	/**
	 * <p>查询子账号已选择的角色信息</p>
	 *
	 * @param memberAsstMemberRole[masterMemberId, asstMemberId]
	 * @return
	 * @throws DaoException
	 */
	List<MemberAsstMemberRoleDO> findMemberAsstMemberRole(MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException;

	/**
	 * <p>获取全部子账号角色信息</p>
	 *
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	List<MemberAsstRoleDO> getMemberAsstRole(long masterMemberId) throws DaoException;

	/**
	 * <p>查询主账号角色</p>
	 * @param masterMemberId 主账号编号
	 * @param roleName 角色名称
	 * @return
	 */
	MemberAsstRoleDO getMemberMasterRoleByRoleName(MemberAsstRoleDO memberAsstRole) throws DaoException;
	
	/**
	 * <p>编辑子账号角色信息</p>
	 * @param memberAsstRole
	 * @return
	 * @throws ManagerException
	 */
	int updateMemberAsstRole(MemberAsstRoleDO memberAsstRole) throws DaoException;

	/**
	 * <p>根据子账号会员编号查询子账号的角色数据</p>
	 *
	 * @param asstMemberId  子账号会员编号
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-12-21 17:38:45
	 */
	List<MemberAsstMemberRoleDO> getAsstRoles(long asstMemberId) throws DaoException;

	/**
	 * <p>记录子账号会员登录时间</p>
	 *
	 * @param memberId  会员编号
	 * @param loginTime  登录时间
	 * @param loginIp  登录IP地址
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 13:34:34
	 */
	int logMemberAsstLogin(long memberId, Date loginTime, String loginIp) throws DaoException;

	/**
	 * <p>根据子账号查询子账号关联数据</p>
	 *
	 * @param memberId  子账号会员编号
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 16:09:25
	 */
	MemberAsstRelationDO getMemberAsstRalationByAsst(long memberId) throws DaoException;

	/**
	 * <p>获取所有有效的子账号权限数据</p>
	 *
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-12-23 上午09:57:34
	 */
	List<MemberAsstPermissionDO> getValidAsstPermissions() throws DaoException;
	

	/**
	 * <p>更新子账号的状态值</p>
	 * @return
	 * @throws ManagerException
	 */
	int updateMemberAsstRelationStatus(MemberAsstRelationDO memberAsstRelation) throws DaoException;
}
