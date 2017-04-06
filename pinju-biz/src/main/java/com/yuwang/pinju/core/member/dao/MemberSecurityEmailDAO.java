package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailDO;

/**
 * <p>
 * 会员安全邮件地址数据库操作
 * </p>
 *
 * @author gaobaowen
 * @since 2011-9-1 10:36:17
 */
public interface MemberSecurityEmailDAO {

	/**
	 * <p>根据 Email 地址获取会员安全邮件地址数据</p>
	 *
	 * @param email 会员设定的安全邮件地址
	 * @return 安全邮件地址信息，若不存在则返回 null 值
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-9-1 10:40:19
	 */
	MemberSecurityEmailDO getMemberSecurityEmailByEmail(String email) throws DaoException;

	/**
	 * <p>添加会员 Email 安全邮箱地址数据</p>
	 *
	 * @param memberSecurityEmail
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-9-1 上午10:42:18
	 */
	MemberSecurityEmailDO addMemberSecurityEmail(MemberSecurityEmailDO memberSecurityEmail) throws DaoException;

	/**
	 * <p>根据会员编号查询会员安全邮箱</p>
	 *
	 * @param memberId
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-10-20 10:18:15
	 */
	MemberSecurityEmailDO findMemberSecurityEmailByMemberId(long memberId) throws DaoException;
	
	/**
	 * <p>删除会员的安全邮箱</p>
	 * 
	 * @param memberId 会员编号
	 * @throws DaoException
	 */
	void deleteMemberSecurityEmail(long memberId) throws DaoException;
}
