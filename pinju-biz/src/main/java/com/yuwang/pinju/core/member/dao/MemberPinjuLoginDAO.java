package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.MemberPinjuLoginDO;


/**
 * <p>品聚会员登录操作</p>
 *
 * @author gaobaowen
 * @since 2011-7-28 14:58:02
 */
public interface MemberPinjuLoginDAO {

	/**
	 * <p>添加品聚会员登录数据</p>
	 *
	 * @param pinjuLogin
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 15:00:17
	 */
	MemberPinjuLoginDO addMemberPinjuLogin(MemberPinjuLoginDO pinjuLogin) throws DaoException;

	/**
	 * <p>根据登录名查找品聚会员登录数据</p>
	 *
	 * @param loginName
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 15:00:43
	 */
	MemberPinjuLoginDO getMemberPinjuLoginByLoginName(String loginName) throws DaoException;
	
	/**
	 * <p>根据会员编号查找品聚会员登录数据</p>
	 *
	 * @param memberId  会员编号
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-8-1 18:23:50
	 */
	MemberPinjuLoginDO getMemberPinjuLoginByMemberId(long memberId) throws DaoException;
	
	/**
	 * <p>根据会员编号修改会员登录密码</p>
	 *
	 * @param pinjuLogin 需要更新的数据，仅使用（ID、MEMBER_ID、LOGIN_NAME、PASSWORD）
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-7-28 16:38:56
	 */
	int updatePinjuLoginPassowrd(MemberPinjuLoginDO pinjuLogin) throws DaoException;
}
