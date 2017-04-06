package com.yuwang.pinju.core.member.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.manager.MemberAsstManager;
import com.yuwang.pinju.domain.member.asst.InputMemberAsstRoleDO;
import com.yuwang.pinju.domain.member.asst.InputMemberRelationDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstOperLogQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstRegisterDO;
import com.yuwang.pinju.domain.member.asst.MemberAsstRelationQuery;
import com.yuwang.pinju.domain.member.asst.MemberAsstRoleQuery;

/**
 * <p>会员子账号 AO</p>
 *
 * @author gaobaowen
 * @since 2011-12-16 14:44:03
 */
public interface MemberAsstAO {

	/**
	 * <p>查询子账号信息</p>
	 *
	 * @param query
	 * @return
	 */
	Result findMemberAsstRelation(MemberAsstRelationQuery query);

	/**
	 * <p>新增子账号信息</p>
	 *
     * @param asstRegister 子账号信息
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_NICKNAME_HAS_EXIST RESULT_MEMBER_NICKNAME_HAS_EXIST}：会员名称已经被使用</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_NICKNAME_WORDS_INVALID RESULT_MEMBER_NICKNAME_WORDS_INVALID}：会员名称含有争议词</li>
	 *   <li>{@link MemberResultConstant#RESULT_INSENSIVE_WORDS RESULT_INSENSIVE_WORDS}：会员名称含有敏感词</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 */
	Result registerAsstAccount(MemberAsstRegisterDO asstRegister);

	/**
	 * <p>新增子账号角色信息</p>
	 *
	 * @param inputMemberAsstRole
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 */
	Result addAsstRole(InputMemberAsstRoleDO inputMemberAsstRole);

	/**
	 * <p>编辑子账号角色信息</p>
	 * 
	 * @param inputMemberAsstRole
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 * 
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 */
	Result eidtAsstRole(InputMemberAsstRoleDO inputMemberAsstRole);
	
	Result findMemberAsstRole(MemberAsstRoleQuery query);

	/**
	 * <p>查询子账号及其角色信息 FOR 更新子账号信息</p>
	 *
	 * @param masterMemberId 主账号编号
	 * @param asstMemberId 子账号编号
	 * @return
	 */
	Result getMemberAsstRelationRole(long masterMemberId, long asstMemberId);

	/**
	 * <p>查询该主账号的角色信息</p>
	 *
	 * @param masterMemberId 主账号编号
	 * @return
	 */
	Result getMemberMasterRole(long masterMemberId);

	/**
	 * <p>更新子账号及其角色信息</p>
	 *
	 * @param masterMemberId 主账号编号
	 * @param asstMemberId 子账号编号
	 * @return
	 */
	Result eidtAsstRelationRole(InputMemberRelationDO inputMemberRelation);

	/**
	 * <p>设置会员账号是否冻结</p>
	 * @param memberId 会员编号
	 * @param status 0=冻结,1=解冻
	 * @return
	 */
	Result setMemberIsvalid(long masterMemberId, long asstMemberId, int status);

	/**
	 * <p>获取子账号角色权限</p>
	 * @return json string
	 */
	String getAsstRolePermission();

	/**
	 * <p>查询子账号角色</p>
	 * @param masterMemberId 主账号编号
	 * @param roleName 角色名称
	 * @return
	 */
	Result getMemberMasterRoleByRoleName(long masterMemberId, String roleName);
	
	/**
	 * <p>查询主账号角色信息 FOR 编辑</p>
	 * @param masterMemberId
	 * @param roleId
	 * @return
	 */
	Result getMemberAsstRoleById(long masterMemberId, long roleId);
	
	/**
	 * <p>删除子账号角色信息</p>
	 * @param inputMemberRelation
	 * @return
	 */
	Result deleteMemberAsstRole(long masterMemberId, long roleId, String remoteIp);

	/**
	 * <p>清除子账号权限缓存，在子账号退出时进行处理</p>
	 *
	 * @param memberId 子账号会员编号
	 * @return  清除状态
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 15:48:56
	 */
	boolean clearAsstPermCache(long memberId);

	/**
	 * <p>指定子账号权限数据</p>
	 *
	 * @param asstMemberId  子账号会员编号
	 * @return  子账号权限数据，详见 {@link MemberAsstManager#getAsstPerm(Long)}
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 10:54:11
	 */
	String getAsstPerm(Long asstMemberId);
	
	/**
	 * <p>查询子账号日志信息</p>
	 * 
	 * @param query
	 * @return
	 */
	Result findMemberAsstOperLog(MemberAsstOperLogQuery query);
}
