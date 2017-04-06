package com.yuwang.pinju.core.member.manager;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstMemberRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstPermissionDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleQuery;

/**
 * <p>会员子账号 Manager</p>
 *
 * @author gaobaowen
 * @since 2011-12-16 14:42:39
 */
public interface MemberAsstManager {

	/**
	 * <p>查询子账号总数</p>
	 * @param masterMemberId 主账号会员编号
	 * @return int
	 * @throws ManagerException
	 */
	int findMemberAsstRelationCount(MemberAsstRelationQuery query) throws ManagerException;

	/**
	 * <p>查询子账号信息<p>
	 * @param masterMemberId
	 * @return
	 * @throws ManagerException
	 */
	List<MemberAsstRelationDO> findMemberAsstRealation(MemberAsstRelationQuery query) throws ManagerException;

	/**
	 * <p>查询角色信息</p>
	 * @param asstRoleId 角色编号
	 * @return
	 * @throws ManagerException
	 */
	MemberAsstRoleDO findMemberAsstRoleById(long masterMemberId, long asstRoleId) throws ManagerException;

	/**
	 * <p>新增会员子账号关联关系</p>
	 * @param memberAsstRelation
	 * @return
	 * @throws DaoException
	 */
	MemberAsstRelationDO insertMemberAsstRelation(MemberAsstRelationDO memberAsstRelation) throws DaoException;

	/**
	 * <p>新增会员子账号与角色对应关系</p>
	 * @param memberAsstMemberRole
	 * @return MemberAsstMemberRoleDO
	 * @throws DaoException
	 */
	MemberAsstMemberRoleDO insertMemberAsstMemberRole(MemberAsstMemberRoleDO memberAsstMemberRole) throws DaoException;

	/**
	 * <p>新增子账号角色信息</p>
	 * @param memberAsstRole
	 * @return Long 子账号角色ID
	 * @throws DaoException
	 */
	MemberAsstRoleDO insertMemberAsstRole(MemberAsstRoleDO memberAsstRole) throws DaoException;

	/**
	 * <p>查询子账号角色数</p>
	 * @param query
	 * @return
	 * @throws ManagerException
	 */
	int findMemberAsstRoleCount(MemberAsstRoleQuery query) throws ManagerException;

	/**
	 * <p>分页查询子账号角色信息</p>
	 *
	 * @param query
	 * @return
	 * @throws ManagerException
	 */
	List<MemberAsstRoleDO> findMemberAsstRole(MemberAsstRoleQuery query) throws ManagerException;

	/**
	 * <p>获取全部子账号角色信息</p>
	 *
	 * @param masterMemberId
	 * @return
	 * @throws ManagerException
	 */
	List<MemberAsstRoleDO> getMemberAsstRole(long masterMemberId) throws ManagerException;

	/**
	 * <p>查询子账号信息</p>
	 *
	 * @param memberAsstRelation[masterMemberId, asstMemberId]
	 * @return
	 * @throws ManagerException
	 */
	MemberAsstRelationDO getMemberAsstRation(long masterMemberId, long asstMemberId) throws ManagerException;

	/**
	 * <p>查询子账号与角色对应关系的信息</p>
	 *
	 * @param [masterMemberId, asstMemberId]
	 * @return
	 * @throws ManagerException
	 */
	List<MemberAsstMemberRoleDO> findMemberAsstMemberRole(long masterMemberId, long asstMemberId) throws ManagerException;

	/**
	 * <p>删除会员子账号与角色对应关系</p>
	 * @param [masterMemberId, asstMemberId]
	 * @return int 0=false, 1=true
	 * @throws ManagerException
	 */
	int deleteMemberAsstMemberRole(long masterMemberId, long asstMemberId) throws ManagerException;

	MemberAsstRelationDO editMemberAsstRelation(MemberAsstRelationDO memberAsstRelation, List<MemberAsstMemberRoleDO> memberAsstMemberRoles) throws ManagerException;

	/**
	 * <p>更新会员的状态值</p>
	 * @param member
	 * @return
	 * @throws ManagerException
	 */
	int updateMemberStatus(final MemberDO member, final MemberAsstRelationDO memberAsstRelation) throws ManagerException;

	/**
	 * <p>查询功能权限</p>
	 *
	 * @return
	 * @throws ManagerException
	 */
	List<MemberAsstPermissionDO> getMemberAsstPermission() throws ManagerException;

	/**
	 * <p>查询主账号角色</p>
	 * @param masterMemberId 主账号编号
	 * @param roleName 角色名称
	 * @return
	 */
	MemberAsstRoleDO getMemberMasterRoleByRoleName(long masterMemberId, String roleName) throws ManagerException;
	/**
	 * <p>编辑子账号角色信息</p>
	 * @param memberAsstRole
	 * @return
	 * @throws ManagerException
	 */
	int updateMemberAsstRole(MemberAsstRoleDO memberAsstRole) throws ManagerException;
	
	/**
	 * <p>删除子账号角色信息</p>
	 * @param memberAsstRole
	 * @throws ManagerException
	 */
	MemberAsstRoleDO deleteMemberAsstRole(long masterMemberId, long roleId, String remoteIp) throws ManagerException;

	/**
	 * <p>初始化子账号权限数据缓存，在子账号登录时进行处理</p>
	 *
	 * @param memberId 登录会员编号
	 * @return  初始化结果
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 15:21:47
	 */
	boolean initAsstPermCache(Long memberId);

	/**
	 * <p>清除子账号权限缓存，在子账号退出时进行处理</p>
	 *
	 * @param memberId  需要清除的子账号会员编号
	 * @return  清除结果
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 15:22:29
	 */
	boolean clearAsstPermCache(Long memberId);

	/**
	 * <p>根据子账号会员编号查询子账号拥有的数据角色</p>
	 *
	 * @param memberId  会员编号
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 下午04:21:12
	 */
	List<MemberAsstMemberRoleDO> getAsstRoles(Long memberId) throws ManagerException;

	/**
	 * <p>根据子账号查询账号关联数据</p>
	 *
	 * @param assistantMemberId
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 下午04:15:17
	 */
	MemberAsstRelationDO getMemberAsstRelationByAsst(Long assistantMemberId) throws ManagerException;

	/**
	 * <p>根据子账号角色 ID 查询子账号角色数据</p>
	 *
	 * @param asstRoleId  子账号角色 ID
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 16:25:28
	 */
	MemberAsstRoleDO findMemberAsstRoleById(Long asstRoleId) throws ManagerException;

	/**
	 * <p>获取子账号的权限数据</p>
	 *
	 * @param asstMemberId  子账号会员编号
	 * @return  子账号的权限数据，格式：&lt;master_member_id&gt;&lt;|&gt;{;&lt;action&gt;:&lt;target&gt;}*;
	 * @throws ManagerException 会员编号格式不正确；权限目标、权限行为为空或者 null 值
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 10:54:11
	 */
	String getAsstPerm(Long asstMemberId) throws ManagerException;

	/**
	 * <p>记录子账号会员最后的登录时间、IP等登录信息</p>
	 *
	 * @param memberId  子账号会员编号
	 * @param loginTime  登录时间
	 * @param loginIp  登录IP地址
	 * @return
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 下午01:53:04
	 */
	int logMemberAsstLogin(MemberLoginHisDO his) throws ManagerException;

	/**
	 * <p>记录子账号会员的操作日志</p>
	 *
	 * @param log
	 * @throws ManagerException
	 *
	 * @author gaobaowen
	 * @since 2011-12-23 14:22:00
	 */
	void logMeberAsstOperation(MemberAsstOperLogDO log) throws ManagerException;
	
	/**
	 * <p>查询子账号操作日志信息</p>
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	List<MemberAsstOperLogDO> findMemberAsstOperLog(MemberAsstOperLogQuery query) throws ManagerException;
	

	/**
	 * <p>查询子账号日志数</p>
	 * @param query
	 * @return
	 * @throws DaoException
	 */
	int findMemberAsstOperLogCount(MemberAsstOperLogQuery query) throws ManagerException;
}
